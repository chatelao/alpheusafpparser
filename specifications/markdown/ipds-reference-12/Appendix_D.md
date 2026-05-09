# Appendix D. Retired Items
This appendix lists each retired item that is mentioned within the body of this book and also lists those items
that have been unretired.
Retired item 1 (1991): This retired item was unretired in 1993.
X'FF' in the acknowledge type field (byte 0) of the Acknowledge Reply had previously been retired for use
by the 370 channel-attached printers to indicate a Null ACK. This value is now used to cover the error case
where the communications protocol that carries the IPDS commands attempts to obtain a positive
Acknowledge Reply (ACK) without first sending an IPDS command with the ARQ bit set to B'1'.
Retired item 2 (1991): X'02' in the unit base field (byte 1) of the Define User Area command is retired for
relative units.
Retired item 3 (1990): X'FF' in the font local ID field (byte 0) of the Load Font Equivalence command is
retired for selecting a default font.
Retired item 4 (1991): X'02' in the unit base field (byte 0) of the Logical Page Descriptor command is retired
for relative units.
Retired item 5 (1991): Byte 1 of the Logical Page Descriptor command is retired for the IBM 3820 printer
(unit base for Y coordinate).
Retired item 6 (1991): Byte 18 of the Logical Page Descriptor command is retired for the IBM 3820 printer
(escape code).
Retired item 7 (1991): Byte 20 of the Logical Page Descriptor command is retired for the IBM 3820 printer
(bit controls).
Retired item 8 (1991): Byte 21 of the Logical Page Descriptor command is retired for the IBM 3820 printer
(data checks).
Retired item 9 (1991): X'02' in the resource type field (Byte 2) of the Activate Resource command is retired
for double-byte coded font.
Retired item 10 (1991): This retired item was unretired in 1991.
X'05' in the resource type field (Byte 2) of the Activate Resource command had previously been retired
because there was no method of refreshing nested resource HAIDs in an overlay. If resident overlays are
supported, nested HAIDs must be managed by the presentation services program.
Retired item 11 (1991): This retired item was unretired in 1993.
X'06' in the resource type field (Byte 2) of the Activate Resource command had previously been retired for
code page. This value was unretired for use with outline fonts.
Retired item 12 (1991): This retired item was unretired in 1993.
X'07' in the resource type field (Byte 2) of the Activate Resource command had previously been retired for
font character set. This value was unretired for use with outline fonts.
Retired item 13 (1991): This retired item was unretired in 1992.
X'06' in the resource ID format field (Byte 6) of the Activate Resource command had previously been
retired for MVS Host Unalterable Font Environment. The value was unretired because it is used by some
IPDS products.
Retired item 14 (1991): Bit 1 of the resource class flags field (byte 11) in the Activate Resource command is
retired for the IBM RPM MVS product (used as a save/no save flag).


Retired item 15 (1991): X'20nn' property pair in the IO-Image command-set vector of the Sense Type and
Model reply is retired as a resolution-correction support property ID.
Where “nn” is a bit-mapped byte:
Bits 0–2 Reserved
Bit 3 Resolution correction algorithms to minimize information loss (for example, via nearest
neighbor averaging techniques as opposed to discarding pels)
Bit 4 Resolution correction ratio may be any positive real number
Bit 5 Integer fraction (1/2, 1/3, ...) resolution correction supported (for example, 1/2 = discard
every other pel)
Bit 6 Integer (x2, x3, ...) resolution correction supported for example, x2 = double every pel)
Bit 7 Resolution correction supported
Retired item 16 (1991): X'30nn' property pair in the IO-Image command-set vector of the Sense Type and
Model reply is retired as a scaling-support property ID.
Where “nn” is a bit-mapped byte:
Bits 0–2 Reserved
Bit 3 Scaling algorithms to minimize information loss (for example, via nearest neighbor averaging
techniques as opposed to discarding pels)
Bit 4 Scaling ratio may be any positive real number
Bit 5 Integer fraction (1/2, 1/3, ...) scaling supported (for example, 1/2 = discard every other pel)
Bit 6 Integer (x2, x3, ...) scaling supported (for example, x2 = double every pel)
Bit 7 Scaling supported
Retired item 17 (1991): The range of values X'06'–X'FF' in property pair X'C0nn' in the Loaded-Font
command-set vector of the Sense Type and Model reply is retired for outline-font pattern-technology IDs.
The values X'1E' and X'1F' were defined and unretired in 1993.
Retired item 18 (1991): X'0000' order code of the Execute Order Anystate command is retired for IBM 3820-
0, 3825-1, 3827-1, 3835-1, 3831, and 3827E printers as a NOP .
Retired item 19 (1991): X'0001' order code of the Execute Order Anystate command is retired for IBM 3812
model 2 and IBM 3816 models 11 and 12 printers as a method for printing the environment when a special
debug port is attached to the printer.
Retired item 20 (1991): X'0002' order code of the Execute Order Anystate command is retired for IBM 3812
model 2 and IBM 3816 models 11 and 12 printers as a method for ringing the bell on the printer.
Retired item 21 (1991): X'0200' order code of the Execute Order Anystate command is retired for the IBM
3800 printer Read Font List order. Refer to Reference Manual for the IBM 3800 Printing Subsystem Model 3
for a description of this order.
Retired item 22 (1991): X'0600' order code of the Execute Order Anystate command is retired for the IBM
3800 printer Mark Form Carrier Strip order. Refer to Reference Manual for the IBM 3800 Printing Subsystem
Model 3 for a description of this order.
Retired item 23 (1991): X'7BF5' order code of the Execute Order Anystate command is retired for IBM 3816
model 11 and 12 printers as a method for setting soft switches in configuration mode.
Retired item 24 (1991): X'CACA' order code of the Execute Order Anystate command is retired for IBM
3812-2, 3816-011, and 3816-012 printers as a method for escaping from IPDS to Page Map Primitives
(PMP); PMP is the machine-level language for the IBM 3812 printer.
Retired item 25 (1991): X'F100' order code of the Execute Order Anystate command is retired for the IBM
3800 printer Display Operator Panel Message order. Refer to Reference Manual for the IBM 3800 Printing
Subsystem Model 3 for a description of this order.


Retired item 26 (1991): X'F300' order code of the Execute Order Anystate command is retired for the IBM
3800 printer and IBM 3820 printer Request Printer Information order.
The syntax of this order is different for the two printers; refer to the specific printer Reference Manual
(GA32-0050 and S544-3175) for a description of this command.
In addition, the IBM Print Services Facility (PSF) has defined a meaning for several of the reserved bytes in
the IBM 3820 printer version of the XOA-RPI reply. These include:
◦ Byte 2, bit 2, B'1' means that the IBM 3820 printer is connected through IBM Remote PrintManager.
◦ Byte 2, bit 3, B'1' means that the IBM 3820 is an IBM 3820-ROM printer.
◦ Byte 2, bit 4, B'1' means IBM Remote PrintManager group operations.
◦ Byte 2, bit 5, B'1' means that an intermediate device is attached using the IBM Distributed Print Feature
(DPF).
◦ Byte 2, bit 6, B'1' means that an intermediate device is attached using IBM PSF Direct.
◦ Bytes 4–5 contain a hexadecimal number (either X'0200' or X'0280') describing the amount of control
store in kilobytes.
◦ Bytes 6–7 contain a hexadecimal number (from X'0100' to X'1000' in X'0100' or greater increments)
describing the amount of pattern store in kilobytes.
Retired item 27 (1991): This retired item was unretired in 1991.
X'05' in the ordering field (byte 2) of the XOA-Request Resource List command had previously been retired
for the IBM Remote PrintManager product. The product used this value before it was defined in the
architecture and, once discovered, it was added to the architecture.
Retired item 28 (1991): X'01' in the resource ID format field (byte 7) of the XOA-Request Resource List
command is retired for the IBM 3820 printer.
The IBM 3820 printer allows only the value X'00' for the RIDF in an XOA-RRL request, but returns one of
the following values in the reply:
X'00' Downloaded resource; not present in the printer
X'01' Downloaded resource; in control storage
X'02' Downloaded resource; in pattern storage
Retired item 29 (1991): X'02' in the resource ID format field (byte 7) of the XOA-Request Resource List
command is retired for the IBM 3820 printer. Refer to retired item 28 for more information.
Retired item 30 (1991): X'06' in the resource ID format field (byte 7) of the XOA-Request Resource List
command is retired for MVS Host Unalterable Font Environment.
Retired item 31 (1991): X'0000' order code of the Execute Order Home State command is retired for IBM
3820-0, 3825-1, 3827-1, 3835-1, 3831, and 3827E printers as a NOP .
Retired item 32 (1991): X'0B00' order code of the Execute Order Home State command is retired for the
IBM 3800 printer Set X Adjustment Range order. Refer to Reference Manual for the IBM 3800 Printing
Subsystem Model 3 for a description of this order.
Retired item 33 (1991): X'F400' order code of the Execute Order Home State command is retired for the
IBM 3800 printer Inhibit Automatic WCGM Load order. Refer to Reference Manual for the IBM 3800 Printing
Subsystem Model 3 for a description of this order.
Retired item 34 (1991): X'02' in the unit base field (byte 6) of the Printable-Area self-defining field in the
XOH-Obtain Printer Characteristics reply is retired for relative units.
Retired item 35 (1991): X'02' in the unit base field (byte 2) of the Variable-Box Size values in the Symbol-Set
Support self-defining field in the XOH-Obtain Printer Characteristics reply is retired for relative units.
Retired item 36 (1991): X'02' in the unit base field (byte 4) of the IM-Image and Coded-Font Resolution self-
defining field in the XOH-Obtain Printer Characteristics reply is retired for relative units.


Retired item 37 (1991): This retired item was unretired in 1991.
X'0200' in the Installed Features self-defining field in the XOH-Obtain Printer Characteristics reply had
previously been retired for channel-attached printers. It is used to indicate a manual two-channel switch.
Retired item 38 (1991): This retired item was unretired in 1991.
X'0201' in the Installed Features self-defining field in the XOH-Obtain Printer Characteristics reply had
previously been retired for channel-attached printers. It is used to indicate a tightly coupled two-channel
switch.
Retired item 39 (1991): X'0202' in the Installed Features self-defining field in the XOH-Obtain Printer
Characteristics reply is retired for a loosely coupled two-channel switch.
Retired item 40 (1991): X'0400' in the Installed Features self-defining field in the XOH-Obtain Printer
Characteristics reply is retired for stapler.
Retired item 41 (1991): This retired item was unretired in 1991.
X'0200' in the Available Features self-defining field in the XOH-Obtain Printer Characteristics reply had
previously been retired for channel-attached printers. It is used to indicate a manual two-channel switch.
Retired item 42 (1991): This retired item was unretired in 1991.
X'0201' in the Available Features self-defining field in the XOH-Obtain Printer Characteristics reply had
previously been retired for channel-attached printers. It is used to indicate a tightly coupled two-channel
switch.
Retired item 43 (1991): X'0202' in the Available Features self-defining field in the XOH-Obtain Printer
Characteristics reply is retired for a loosely coupled two-channel switch.
Retired item 44 (1991): X'0400' in the Available Features self-defining field in the XOH-Obtain Printer
Characteristics reply is retired for stapler.
Retired item 45 (1991): X'02' in the resource-type byte of the Activate Resource RT & RIDF Support self-
defining field in the XOH-Obtain Printer Characteristics reply is retired for double-byte coded fonts.
Retired item 46 (1991): This retired item was unretired in 1991.
X'05' in the resource-type byte of the Activate Resource RT & RIDF Support self-defining field in the XOH-
Obtain Printer Characteristics reply had previously been retired for overlay. This value was unretired when
retired item 10 was unretired.
Retired item 47 (1991): This retired item was unretired in 1993.
X'06' in the resource-type byte of the Activate Resource RT & RIDF Support self-defining field in the XOH-
Obtain Printer Characteristics reply had previously been retired for code page. This value was unretired for
use with outline fonts.
Retired item 48 (1991): This retired item was unretired in 1993.
X'07' in the resource-type byte of the Activate Resource RT & RIDF Support self-defining field in the XOH-
Obtain Printer Characteristics reply had previously been retired for font character set. This value was
unretired for use with outline fonts.
Retired item 49 (1991): This retired item was unretired in 1992.
X'06' in the Resource-ID-Format byte of the Activate Resource RT & RIDF Support self-defining field in the
XOH-Obtain Printer Characteristics reply had previously been retired for IBM MVS Host Unalterable
Remote Font Environments. This value was unretired when retired item 13 was unretired.
Retired item 50 (1991): X'0000' self-defining product-identifier parameter ID in the Product Identifier self-
defining field in the XOH-Obtain Printer Characteristics reply is retired for IBM 4224 and IBM 4234 printers.
This product-identifier parameter ID indicates that the stack page counter is implemented as specified by the
IPDS Architecture. There is no data in bytes 3–end, that is, byte 0 = X'03'.
Retired item 51 (1991): X'02' in the unit base field (byte 2) of the XOH-Set Media Size command is retired
for relative units.


Retired item 52 (1991): X'0200' in the type field (bytes 0–1) of the Load Equivalence command is retired for
the IBM Remote PrintManager product. It is used to map the current value of Host-Assigned IDs of page
segments included within previously stored overlays onto the value in effect at the time they were first
downloaded to IBM Remote PrintManager.
Retired item 53 (1991): X'02' in the unit base field (byte 4) of the Text Output Control self-defining field in the
Write Text Control command is retired for relative units.
Retired item 54 (1991): X'02' in the unit base field (byte 6) of the Text Data Descriptor self-defining field in
the Write Text Control command is retired for relative units.
Retired item 55 (1991): X'02' in the unit base field (byte 4) of the Image Output Control self-defining field in
the Write Image Control 2 command is retired for relative units.
Retired item 56 (1991): X'02' in the unit base field (byte 6) of the Image Data Descriptor self-defining field in
the Write Image Control 2 command is retired for relative units.
Retired item 57 (1991): X'02' in the unit base field (byte 4) of the Graphics Output Control self-defining field
in the Write Graphics Control command is retired for relative units.
Retired item 58 (1991): X'02' in the unit base field (byte 4) of the Graphics Data Descriptor self-defining field
in the Write Graphics Control command is retired for relative units.
Retired item 59 (1991): Bytes 22–25 of the Graphics Data Descriptor self-defining field in the Write Graphics
Control command is retired for the Z
g coordinates of the graphics presentation space window.
Retired item 60 (1991): Bytes 26–27 of the Graphics Data Descriptor self-defining field in the Write Graphics
Control command is retired for graphic data flags.
Retired item 61 (1991): X'02' in the unit base field (byte 4) of the Bar Code Output Control self-defining field
in the Write Bar Code Control command is retired for relative units.
Retired item 62 (1991): X'02' in the unit base field (byte 4) of the Bar Code Data Descriptor self-defining field
in the Write Bar Code Control command is retired for relative units.
Retired item 63 (1991): Bit 4 of the flags byte (byte 0) in the Write Bar Code command is retired in the
BCOCA architecture for PC ASCII data stream use; this flag indicates support for ASCII data in a BCOCA
object.
Retired item 64 (1991): Bit 7 of the flags byte (byte 0) in the Write Bar Code command is retired in the
BCOCA architecture for PC ASCII data stream use; in particular this flag is used by the IBM Personal Printer
Data Stream (PPDS).
Retired item 65 (1991): X'02' in the unit base for pel-units field (byte 26) of the Load Font Control command
is retired for relative units.
Retired item 66 (1991): Bit 0 of the flags1 field (byte 0) in the Load Symbol Set command is retired for the
IBM 3270 architecture. This bit indicates “Extended Form” in the IBM 3270 architecture. That architecture
allows a shorter form of this command, indicated by a value of B'0' in this bit.


Retired item 67 (1991): Bit 1 of the flags1 field (byte 0) in the Load Symbol Set (LSS) command is retired for
the IBM 3270 architecture.
This is the Clear bit in IBM 3270 and IBM GOCA architectures. This value indicates that the LSS data
loaded with this command overwrites the font data for any existing matching code points with the same
Loaded Font ID. There are only two cases:
◦ If no previous LSS font or coded font with a font identifier matching bytes 15–16 exists in the printer, this
command establishes a new LSS font.
◦ If a previous LSS font or coded font with a font identifier matching bytes 15–16 exists in the printer, the
information transmitted by this command replaces some or all of the existing control and all of the
existing raster information about the matching code points, regardless of whether the existing information
was loaded by a previous Load Symbol Set command or via the coded font commands (Load Font
Control, Load Font Index, and Load Font).
The IBM GOCA and IBM 3270 architectures allow font deletion via a value of B'1' for this bit. IPDS font
deletion is done only via the Deactivate Font command.
Retired item 68 (1991): Bit 2 of the flags1 field (byte 0) in the Load Symbol Set command is retired for the
IBM 3270 architecture. A value of B'1' is used by the IBM GOCA architecture and IBM 3270 architecture to
request a function known as “skip suppression” that, for IPDS printers, is better done via control sequences
or, implicitly, by the font design.
Retired item 69 (1991): Byte 1 in the Load Symbol Set command is retired for the IBM 3270 architecture.
The local ID specified in this byte has no significance for IPDS printers since the one-byte local identifier
used by the Graphics Set Character Set, Push and Set Character Set, Set Marker Set, and Push and Set
Marker Set orders; by the Bar Code LID; and by the Text SCFL are mapped to the Font Host-Assigned ID
(LSS bytes 15–16) via the Load Font Equivalence command.
Retired item 70 (1991): Byte 3 in the Load Symbol Set command is retired for the IBM 3270 architecture.
This byte is the read/write storage (RWS) number in IBM GOCA and IBM 3270 architectures; it has no
significance for IPDS printers.
Retired item 71 (1991): Bits 0–2 of the flags2 field (byte 5) in the Load Symbol Set command are retired for
the IBM 3270 architecture. These flag bits are used to control the all points addressable (APA), LCID
compare bit (CB), and operator selectable by PS key (OB) functions respectively for IBM GOCA and IBM
3270 architectures. These functions are not currently supported by IPDS printers hence the bit values B'011'
effectively disable these functions.
Retired item 72 (1991): Bit 3 of the flags2 field (byte 5) in the Load Symbol Set command is retired for the
IBM 3270 architecture; this bit has no significance for IPDS printers. This bit specifies the multiple LCID
(MULTID) setting in IBM GOCA and IBM 3270 architectures.
Retired item 73 (1991): Bit 4 of the flags2 field (byte 5) in the Load Symbol Set command is retired for the
IBM 3270 architecture; this bit has no significance for IPDS printers. Retired for “Use Symbol Envelope
Table”. A value of B'1' indicates that the Symbol Envelope Table (SET) information (from a triplet specified in
another LSS field) is to be applied to this symbol set when these symbols are used within graphics data. It is
an exception if this value is specified and no SET exists in the triplet field. Refer to the IBM GOCA
specification for a description of the SET . A value of B'0' indicates that the SET , if present, is not to be used.
Retired item 74 (1991): Byte 9 in the Load Symbol Set command is retired for the IBM 3270 architecture
“Color plane”. This field has no significance for IPDS printers.
The bits of this byte identify the color planes to be loaded with the raster patterns that follow:
Bits 0–4 Reserved
Bit 5 Green plane
Bit 6 Red plane
Bit 7 Blue plane
For example, a value of X'03' indicates that the blue and red planes are to be loaded. A value of X'00'
indicates that all color planes are to be loaded.


Retired item 75 (1991): Byte 10 in the Load Symbol Set command is retired for the IBM 3270 architecture.
This byte specifies the Starting Subsection Identifier in the IBM 3270 architecture. It has no significance for
IPDS printers.
Retired item 76 (1991): Byte 12 in the Load Symbol Set command is retired for the IBM 3270 architecture.
Retired for “Width pairs”; this byte indicates the number of pairs of width-indentation values specified in the
Symbol Envelope Table (SET) parameter. IBM GOCA architecture specifies the form of the SET . If the SET is
not present or not supported (byte 5, bit 4), this field can be ignored. This field has no significance for IPDS
printers.
Retired item 77 (1991): Byte 13 in the Load Symbol Set command is retired for the IBM 3270 architecture.
Retired for “Height pairs”; this byte indicates the number of pairs of height-indentation values specified in the
Symbol Envelope Table (SET) parameter. The IBM GOCA architecture specifies the form of the SET . If the
SET is not present or not supported (byte 5, bit 4), this field can be ignored. This field has no significance for
IPDS printers.
Retired item 78 (1991): Byte 14 in the Load Symbol Set command is retired for the IBM 3270 architecture.
This byte has been reserved for future function by IBM GOCA. This field has no significance for IPDS
printers.
Retired item 79 (1991): Bytes 17–i in the Load Symbol Set command are retired for the IBM 3270
architecture and are intended to be reserved for future expansion by all architectures. These are an arbitrary
number of reserved bytes derived from the value in byte 4. These bytes have no significance for IPDS
printers.
Retired item 80 (1991): Format 3 sense byte information as specified in sense byte 5 is retired for channel-
attached printers. This format is used for control-unit sensed exceptions when an Outboard Recorder Record
(OBR) is required. This format is used in channel-level sense data and is not used with IPDS sense data.
Refer to “Non-IPDS Sense Data” on page 941 for more information about channel-level sense data.
Byte 4 Reserved
Byte 5 Format identifier, X'03'
Bytes 6–7 Channel adapter trace register
Bytes 8–9 Channel Adapter Status register
Bytes 10–11 Channel adapter error log register
Bytes 12–13 Channel configuration register 1
Byte 14 Channel command register
Byte 15 Channel (host) status register
Byte 16 Channel adapter request register
Byte 17 Device status table entry
Bytes 18–19 Reserved
Bytes 20–21 Channel buffer pointer register
Bytes 22–23 Reserved
Retired item 81 (1991): Format 4 sense byte information as specified in sense byte 5 is retired for channel-
attached printers. This format is used for control-unit sensed exceptions when no Outboard Recorder Record
(OBR) is required. This format is used in channel-level sense data and is not used with IPDS sense data.
Refer to “Non-IPDS Sense Data” on page 941 for more information about channel-level sense data.
Byte 4 Zero
Byte 5 Format identifier, X'04'
Bytes 6–23 Zero
Retired item 82 (1993): Format 5 sense byte information as specified in sense byte 5 is retired for serial-
channel-attached printers.
This format is used in channel-level sense data and is not used with IPDS sense data. Refer to “Non-IPDS
Sense Data” on page 941 for more information about channel-level sense data.


Format 5 provides detailed information for channel-level exceptions on a serial-channel-attached printer.
This format applies to X'8006..00', X'2001..01', X'2001..02', X'2002..01', X'2002..02', X'10E2..01',
X'10E2..02', X'0401..01', X'0401..02', X'01A0..00', X'01A1..00', X'01A2..00', and X'01A3..00'.
Byte 4 Reserved
Byte 5 Format identifier, X'05'
Byte 6 Physical-interface identifier
Bytes 7–8 Link adapter A basic status register
Byte 9 Link adapter A error log register byte 1
Bytes 10–12 Link adapter A link error log
Bytes 13–14 Link adapter B basic status register
Byte 15 Link adapter B error log register byte 1
Bytes 16–18 Link adapter B link error log
Byte 19 Link adapter indicator
Byte 20 Reserved
Byte 21 VCU ID (0–15 Link A, 16–31 Link B)
Bytes 22–23 Virtual Error Log for VCU ID
Retired item 83 (1990): Action code X'00' (no error outstanding) as specified in sense byte 2 is retired for
channel-attached printers. This action code is not used with IPDS sense data; refer to “Non-IPDS Action
Codes” on page 941 for more information about this action code.
Retired item 84 (1990): Action code X'02' (operator intervention with OBR record) as specified in sense byte
2 is retired for channel-attached and TCP/IP-attached printers. This action code is not used with IPDS sense
data; refer to “Non-IPDS Action Codes” on page 941 for more information about this action code.
Retired item 85 (1990): Action code X'03' (operator intervention without OBR record) as specified in sense
byte 2 is retired for channel-attached and TCP/IP-attached printers. This action code is not used with IPDS
sense data; refer to “Non-IPDS Action Codes” on page 941 for more information about this action code.
Retired item 86 (1990): Action code X'04' (channel error) as specified in sense byte 2 is retired for channel-
attached printers. This action code is not used with IPDS sense data; refer to “Non-IPDS Action Codes” on
page 941 for more information about this action code.
Retired item 87 (1991): Action code X'18' (transparent error) as specified in sense byte 2 is retired for
channel-attached printers. This action code is not used with IPDS sense data; refer to “Non-IPDS Action
Codes” on page 941 for more information about this action code.
Retired item 88 (1990): Action code X'1C' (Sense Extended CCW required) as specified in sense byte 2 is
retired for channel-attached printers. This action code is not used with IPDS sense data; refer to “Non-IPDS
Action Codes” on page 941 for more information about this action code.
Retired item 89 (1993): Action code X'24' (printer not assigned) as specified in sense byte 2 is retired for
serial-channel-attached printers. This action code is not used with IPDS sense data; refer to “Non-IPDS
Action Codes” on page 941 for more information about this action code.
Retired item 90: (1993): Action code X'25' (printer assigned elsewhere) as specified in sense byte 2 is
retired for serial-channel-attached printers. This action code is not used with IPDS sense data; refer to “Non-
IPDS Action Codes” on page 941 for more information about this action code.
Retired item 91 (1993): Action code X'4D' (resetting event) as specified in sense byte 2 is retired for serial-
channel-attached printers. This action code is not used with IPDS sense data; refer to “Non-IPDS Action
Codes” on page 941 for more information about this action code.
Retired item 92 (1991): Exception class X'20' as specified in sense byte 0 is retired for Bus-Out Parity
Check, reserved for compatibility with channel-attached printers.
Retired item 93 (1991): IPDS command code X'D600' is retired for the IBM 3800 printer T est I/O command.
Refer to Reference Manual for the IBM 3800 Printing Subsystem Model 3 for a description of this command.


Retired item 94 (1991): IPDS command code X'D604' is retired for the IBM 3800 printer Sense I/O
command. Refer to Reference Manual for the IBM 3800 Printing Subsystem Model 3 for a description of this
command.
Retired item 95 (1991): IPDS command code X'D60D' is retired for the IBM 3800 printer Write Factored Text
Control command. Refer to Reference Manual for the IBM 3800 Printing Subsystem Model 3 for a
description of this command.
Retired item 96 (1991): IPDS command code X'D614' is retired for the IBM 3800 printer Sense Intermediate
Buffer command. Refer to Reference Manual for the IBM 3800 Printing Subsystem Model 3 for a description
of this command.
Retired item 97 (1991): IPDS command code X'D624' is retired for the IBM 3800 printer Sense Error Log
command. Refer to Reference Manual for the IBM 3800 Printing Subsystem Model 3 for a description of this
command.
Retired item 98 (1990): This retired item was unretired in 1991.
Bit 0 of byte 37 in the Load Font Control command had previously been retired for use by the IBM 3835
printer with a particular MICR device. The flag was later unretired and defined as the “intended for MICR
printing” flag.
Retired item 99 (1991): Action code X'07' (retry error log full) as specified in sense byte 2 is retired for IBM
3800-3,6,8 printers. This action code is not used with IPDS sense data; refer to “Non-IPDS Action Codes” on
page 941 for more information about this action code.
Retired item 100 (1991): Action code X'0B' (process power error) as specified in sense byte 2 is retired for
IBM 3800-3,6,8 printers. This action code is not used with IPDS sense data; refer to “Non-IPDS Action
Codes” on page 941 for more information about this action code.
Retired item 101 (1991): Action code X'0E' (not enough storage, page printed using the accumulator
feature) as specified in sense byte 2 is retired for IBM 3800-3,6,8 printers. This action code is not used with
IPDS sense data; refer to “Non-IPDS Action Codes” on page 941 for more information about this action
code.
Retired item 102 (1991): Action code X'0F' (accumulator read check) as specified in sense byte 2 is retired
for IBM 3800-3,6,8 printers. This action code is not used with IPDS sense data; refer to “Non-IPDS Action
Codes” on page 941 for more information about this action code.
Retired item 103 (1991): Action code X'10' (reload electronic overlay or base page) as specified in sense
byte 2 is retired for IBM 3800-3,6,8 printers. This action code is not used with IPDS sense data; refer to “Non-
IPDS Action Codes” on page 941 for more information about this action code.
Retired item 104 (1991): Action code X'11' (count continuous-forms stacker fold wrong errors) as specified
in sense byte 2 is retired for IBM 3800-3,6,8 printers. This action code is not used with IPDS sense data;
refer to “Non-IPDS Action Codes” on page 941 for more information about this action code.
Retired item 105 (1991): Action code X'12' (count burster input checks) as specified in sense byte 2 is
retired for IBM 3800-3,6,8 printers. This action code is not used with IPDS sense data; refer to “Non-IPDS
Action Codes” on page 941 for more information about this action code.
Retired item 106 (1991): Action code X'13' (count no burst checks) as specified in sense byte 2 is retired for
IBM 3800-3,6,8 printers. This action code is not used with IPDS sense data; refer to “Non-IPDS Action
Codes” on page 941 for more information about this action code.
Retired item 107 (1991): Action code X'14' (count burster-trimmer-stacker stacker/trimmer checks) as
specified in sense byte 2 is retired for IBM 3800-3,6,8 printers. This action code is not used with IPDS sense
data; refer to “Non-IPDS Action Codes” on page 941 for more information about this action code.
Retired item 108 (1991): X'8000' property pair in the Device-Control vector of the Sense Type & Model reply
is retired for XOA-NOP; see retired item 18.
Retired item 109 (1991): X'8001' property pair in the Device-Control vector of the Sense Type & Model reply
is retired for environment printing; see retired item 19.


Retired item 110 (1991): X'8002' property pair in the Device-Control vector of the Sense Type & Model reply
is retired for ringing a bell and for IBM 3800 printer Read Font List; see retired items 20 and 21.
Retired item 111 (1991): X'8006' property pair in the Device-Control vector of the Sense Type & Model reply
is retired for IBM 3800 printer XOA-Mark Form Carrier Strip; see retired item 22.
Retired item 112 (1991): X'80F1' property pair in the Device-Control vector of the Sense Type & Model reply
is retired for IBM 3800 printer XOA-Display Operator Panel Message; see retired item 25.
Retired item 113 (1991): X'80F3' property pair in the Device-Control vector of the Sense Type & Model reply
is retired for IBM 3800 printer XOA-Request Printer Information; see retired item 26.
Retired item 114 (1991): X'9000' property pair in the Device-Control vector of the Sense Type & Model reply
is retired for XOH-NOP; see retired item 31.
Retired item 115 (1991): X'900B' property pair in the Device-Control vector of the Sense Type & Model reply
is retired for IBM 3800 printer XOH-Set X Adjustment Range; see retired item 32.
Retired item 116 (1991): X'90F4' property pair in the Device-Control vector of the Sense Type & Model reply
is retired for IBM 3800 printer XOH-Inhibit Automatic WCGM Load; see retired item 33.
Retired item 117 (1991): LPD byte 15, bit 1 (ordered blocks flag) was not used by any implementation and is
therefore retired.
Bit 1 Ordered blocks flag
A value of B'1' indicates that all text, image, graphics, or bar code blocks are in sequential order
(the presentation of sequentially received blocks as opposed to data within blocks). No backward
movement is required. A value of B'0' indicates that backward movement might be required.
Retired item 118 (1991): LPD byte 15, bit 2 (ordered text flag) was not used by any implementation and is
therefore retired.
Bit 2 Ordered text flag
A value of B'1' indicates that all text data within every page, either in text blocks or as text data, is
in sequential order. No backward movement is required. A value of B'0' indicates that some text
data on a page might require backward movement.
Retired item 119 (1991): Bit 4 of XOH-OPC Printable-Area self-defining field byte 22 is retired.
This flag is defined as follows:
B'1' The media size in bytes 10–13 and printable-area size in bytes 18–21 might be invalid.
B'0' The media size in bytes 10–13 and printable-area size in bytes 18–21 are valid.
Retired item 120 (1993): Byte 36 of the Load Font Control command is retired as a non-stageable indicator.
A value of X'00' indicates that the font is stageable. A value of X'01' indicates that the font is non-stageable.
A stageable font is a font that is to be stored on a non-volatile media, such as a floppy disk, assuming such
a storage means is available.
Retired item 121 (1991): Intermediate Device Type code X'0003' in XOH-OPC Product Identifier self-
defining field parameter ID X'0002' is retired to indicate that the intermediate device is an IBM 4019 IPDS -
PPDS/HPCL Protocol Converter.
Retired item 122 (1993): Byte 5 of the LFCSC command is retired for version of pattern technology.
Retired item 123 (1994): This retired item was unretired in 2011.
This item covers the Write Text Control (WTC) command, which had been in the IPDS architecture, but
was retired until a product implementation became available.


Retired item 124 (1994): This retired item was unretired in 2011.
X'2001' property pair in the text command-set vector of the Sense Type and Model reply had previously
been retired for text block support using the Write Text Control command. The property pair now indicates
support for text objects and the Write Text Control (WTC) command.
Retired item 125 (1992): Sense byte 3 is retired for use with IBM S/370 sense data. This byte is not used in
the IPDS architecture.
Sense byte 3 normally defines the printer environment at the time of the exception and not the printer
environment at the time the exception is reported to the host. There are five exceptions to this rule. Sense
byte 3 for action codes X'01', X'0C', X'0D', X'15', and X'18' defines the printer environment at the time the
exception is reported to the host. The bits are defined as follows:
Bit 0 If B'1', indicates that the printer is in the ready state. If B'0', indicates that the printer is in the
not-ready state.
Bits 1–6 Reserved
Bit 7 Set to B'1' to indicate that the printer operates in page mode only.
Retired item 126 (1993): X'90D0' property pair in the Device-Control vector of the Sense Type & Model reply
is retired for XOH-Select Output Stacker; see retired item 127.
Retired item 127 (1993): X'D000' order code of the Execute Order Home State command is retired for
Select Output Stacker (SOS); see retired item 126.
Retired item 128 (1994): The following resource types from byte 2 of the XOA-RRL reply are retired:
X'81' Resident single-byte LF1-type or LF2-type coded-font components
X'82' Resident double-byte LF1-type coded-font components
X'83' Resident double-byte LF1-type coded-font-section components
X'84' Resident page segment
X'85' Resident overlay
X'86' Resident code page
X'87' Resident font character set
X'88' Resident single-byte coded-font index
X'89' Resident double-byte coded-font section index
Retired item 129 (1994): StampType X'01' in the Local Date and Time Stamp (X'62') triplet is retired for use
by the IBM RMARK utility programs. This StampType indicates that a resource object was marked by the
IBM RMARK utility.


Retired item 130 (1996): The Standard OCA Color Value Support self-defining field (formerly in the XOH-
OPC reply) is retired for use by older IPDS printers. New IPDS printers should not return this self-defining
field.
The Standard OCA Color Value Support self-defining field specifies the set of color values that are
supported for all multiple-color data types supported. The STM reply indicates for each of the data types
(text, image, graphics, or bar code) whether or not multiple-color values are supported for that data type.
This field must be returned by printers that indicate multiple-color support in the STM reply. The absence of
this self-defining field indicates that the printer supports the single color black along with the color values
X'0008' (black), and X'FF07' (printer default). Refer to the STM command description for command-set
vectors containing the Color Support Property Identifier used to indicate support of multiple colors.
Offset Type Name Range Meaning
0–1 UBIN SDF
length
X'0006' –
X'7FFE'
Length of this self-defining field, including itself
2–3 CODE SDF ID X'0005' Standard OCA Color Value Support self-defining field ID
One or more color-table values in the following format:
+0–1 CODE Color
value X'0001'
X'0002'
X'0003'
X'0004'
X'0005'
X'0006'
X'0007'
X'0008'
X'0009'
X'000A'
X'000B'
X'000C'
X'000D'
X'000E'
X'000F'
X'0010'
True colors supported by the printer:
Blue
Red
Pink/magenta
Green
Turquoise/cyan
Yellow
White or black (black for printers, see implementation note 1)
Black
Dark blue (see implementation note 1)
Orange (see implementation note 1)
Purple (see implementation note 1)
Dark green (see implementation note 1)
Dark turquoise (see implementation note 1)
Mustard (see implementation note 1)
Gray (see implementation note 1)
Brown
Implementation Notes:
1. These colors are listed for compatibility with the IBM GOCA architecture and are unsupported.
2. The current architecture does not define color-support self-defining fields in the XOH-OPC response
for specific data types even though the STM color-support property-pairs are generated for specific
data types.


Retired item 131 (1998): XOA-RRL query type X'FE' is retired for a single-entry general query with resource
ID triplets.
X'FE' General query with resource ID triplets, single entry only
A value of X'FE' identifies this as a general query identical to query type X'FF' except that resource
ID triplets are also returned as part of the resource ID in the reply. These resource ID triplets are
not returned with a X'FF'-type query.
This is an optional query type. Support for this query type is indicated by property pair X'F402' in
the Device-Control command-set vector of the STM reply. Query type X'FE' may only be used with
single-entry queries. Both list and individual queries are supported with query type X'FE' and both
kinds of query can return multiple reply entries; each resource that matches the query is returned
in a separate resource-list reply entry that includes all triplets specific to that version of the
resource.
In the XOA-RRL reply:
If the query type in the XOA-RRL request was X'FE', and if the printer has one or more resource ID
triplets associated with the resource, these triplets are appended at the end of the fixed portion of the
resource ID value. Resource ID triplets are described in the AR command bytes 12–end of entry
description; refer to the Local Date and Time Stamp (X'62') triplet and the Font Resolution and Metric
T echnology (X'84') triplet. Not all printers support this function; support for specific triplets is indicated by
X'F2xx' property pairs in the Device-Control command-set vector in an STM reply.
In the STM reply:
Property pair X'F402' is retired for "XOA RRL query type X'FE' supported".
Retired item 132 (1999): Media ID type X'01' in the XOH-OPC reply Printable-Area self-defining field is
retired for ISO/DPA registered media values.
X'01' ISO/DPA registered media values.
This is an integer corresponding to the leaf element of the DPA Standard Object ID (OID) for the
physical medium identified under the medium object class. The input media ID (in bytes 27–end)
contains only the characters 0–9 using the code points assigned in IBM code page 500.
These values are defined in ISO Draft International Standard 10175-1 “Information T echnology -
Text and Office Systems - Document Printing Application (DPA)”.
Note: Type X'10' is recommended when an OID is used.
Retired item 133 (2000): Bit 4 of LCPC byte n+13 (processing flags for the default GCGID) is retired for a
flag to distinguish some code page types.
This flag was set to B'1' in outline code pages for Japanese, Korean, Simplified Chinese, and Traditional
Chinese; it is set to B'0' for raster code pages and Unicode code pages. The reason for the flag being set to
B'1' is unknown. The FOCA architecture does not assign a meaning to this flag.
Retired item 134 (2000): IPDS command code X'D61C' is retired for the Load HAID List (LHL) command.
This command permits resources that are nested in a nesting resource via Include commands or local IDs to
be referenced externally using a new Host-Assigned ID (HAID). The LHL command allows the replacement
of an old HAID with a new HAID in the Include or LFE command for each nested resource.


Retired item 135 (2001): Replicate-and-trim mapping option for graphics (property pair X'F300') is retired.
This option was originally defined for both IO Image and for graphics, but was later restricted to just IO Image
because no IPDS printer implemented this mapping option for graphics. This mapping option is described as
follows:
With replicate-and-trim mapping, the top-left corner of the graphics presentation space window is
positioned coincident with the origin of the graphics object area, and the graphics presentation space
window is presented at the size specified in bytes 4–21 of the GDD self-defining field. The graphics
presentation space window is then replicated in the X and Y directions of the graphics object area until the
object area is filled. Each new replicate of the window in the X direction is precisely aligned with the
window previously placed in the X direction. Each new replicate of the window in the Y direction is precisely
aligned with the window previously placed in the Y direction. If the last window in either the X or Y direction
fits only partially into the graphics object area, the portion of the window that falls outside the graphics
object area is trimmed (not printed). This type of trimming does not cause an exception. All data that falls
within the graphics object area extents is presented, but data that falls outside of the graphics object area
is not presented.
Note: Not all printers support the replicate-and-trim mapping option; the X'F300' property pair is returned in
the STM reply by those printers that do support the mapping option.
Figure 122. Example of GOC Replicate-and-Trim Mapping (retired item 135)
Replicate-and-trim
mapping specified in
the Graphics Output
## Control (GOC)
Graphics Presentation Space Window
Logical Page
Graphics Object Area


Retired item 136 (2006): E-mail Setup File object OID (used by IBM Infoprint Manager)
Table 74. Object Containers Used in the IPDS Environment (retired item 136)
Registered
Object-Type OID Object Description
Internal
Resource ID
Object
Download
State Object Usage
X'0607 2B12
0004 0101
2500 0000
0000 0000'
E-mail Setup File
Non-presentation
Not applicable Home state Setup file
Retired item 137 (2006): Fax Setup File object OID (used by IBM Infoprint Manager)
Table 75. Object Containers Used in the IPDS Environment (retired item 137)
Registered
Object-Type OID Object Description
Internal
Resource ID
Object
Download
State Object Usage
X'0607 2B12
0004 0101
2400 0000
0000 0000'
Fax Setup File
Non-presentation
Not applicable Home state Setup file
Retired item 138 (2006): Infoprint 2000 Setup File object OID (used by IBM Infoprint Manager)
Table 76. Object Containers Used in the IPDS Environment (retired item 138)
Registered
Object-Type OID Object Description
Internal
Resource ID
Object
Download
State Object Usage
X'0607 2B12
0004 0101
2600 0000
0000 0000'
Infoprint 2000 printer
Setup File
Non-presentation
Not applicable Home state Setup file
Retired item 139 (2006): STM Bar Code property pair X'1301' is retired for “ASCII-based code pages
supported”.
Retired item 140 (2008): Bytes 8–11 in the Object Offset (X'5A') triplet are retired for a high-order extension
parameter.
This parameter is not used within IPDS data streams, but is defined in the MO:DCA architecture to provide
a means to allow a larger object offset value to be specified. IPDS printers ignore the value in this field. In
MO:DCA, this optional parameter allows a bigger object-offset value to be specified. When the high-order
extension field is present, the object offset value is effectively 8 bytes long; bytes 8–11 provide the high-
order 4 bytes and bytes 4–7 provide the low-order 4 bytes.
Retired item 141 (2011): XOA order X'0700' is retired for use in Océ printers and server software; used for
extended features control.
Retired item 142 (2011): XOA order X'0900' is retired for use in Océ printers and server software; used to
enable two-up.
Retired item 143 (2011): XOA order X'CE00' is retired for use in Océ printers and server software; used for
cancel synchronization.


Retired item 144 (2011): XOH order X'1C00' is retired for use in Océ printers and server software; used for
two-up control.
Retired item 145 (2011): XOH order X'1D00' is retired for use in Océ printers and server software; used to
select media destination.
Retired item 146 (2011): XOH order X'4C00' is retired for use in Océ printers and server software; used for
cut-sheet emulation (CSE) controls.
Retired item 147 (2011): XOH order X'4D00' is retired for use in Océ printers and server software; used for
the PoD HD option.
Retired item 148 (2013): XOH order X'4E00' is retired for use in Océ printers and server software; used for
configurations.
Retired item 149 (2021): Property pair X'120B' is retired because it reports support of a functionality that
cannot actually work.
Property pair X'120B' indicates support for mapping TrueType/OpenType Fonts in the DORE command,
for all presentation objects supported by the printer and for which TrueType/OpenType Fonts are valid
secondary resources.
The DORE command cannot be used for this purpose, since in IPDS, a TrueType/OpenType font used as a
secondary resource uses a HAID in the data-object-font-component HAID pool, but the HAIDs in the DORE
are only searched for in the data-object-resource HAID pool. Instead, the DORE2 command must be used
for this purpose, and that functionality is reported with the X'120D' property pair.


Notices
The AFP Consortium or consortium member companies might have patents or pending patent applications
covering subject matter described in this document. The furnishing of this document does not give you any
license to these patents.
The following statement does not apply to the United Kingdom or any other country where such
provisions are inconsistent with local law: AFP Consortium PROVIDES THIS PUBLICATION “AS IS”
WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED
TO, THE IMPLIED WARRANTIES OF NON-INFRINGEMENT , MERCHANTABILITY OR FITNESS FOR A
PARTICULAR PURPOSE. Some states do not allow disclaimer of express or implied warranties in certain
transactions, therefore, this statement might not apply to you.
This publication could include technical inaccuracies or typographical errors. Changes are periodically made to
the information herein; these changes will be incorporated in new editions of the publication. The AFP
Consortium might make improvements and/or changes in the architecture described in this publication at any
time without notice.
Any references in this publication to Web sites are provided for convenience only and do not in any manner
serve as an endorsement of those Web sites. The materials at those Web sites are not part of the materials for
this architecture and use of those Web sites is at your own risk.
The AFP Consortium may use or distribute any information you supply in any way it believes appropriate
without incurring any obligation to you.
This information contains examples of data and reports used in daily business operations. To illustrate them in
a complete manner, some examples include the names of individuals, companies, brands, or products. These
names are fictitious and any similarity to the names and addresses used by an actual business enterprise is
entirely coincidental.


Trademarks
These terms are trademarks or registered trademarks of Adobe Systems Incorporated in the United States, in
other countries, or both:
Acrobat
Adobe
Photoshop
PostScript
AFPC and AFP Consortium are trademarks of the AFP Consortium.
These terms are trademarks or registered trademarks of Hewlett-Packard Company in the United States, in
other countries, or both:
Hewlett-Packard
PCL
These terms are trademarks or registered trademarks of the International Business Machines Corporation in
the United States, other countries, or both:
AIX
FICON
IBM
IBM Registry
MVS
OS/400
PrintManager
Print Services Facility
System/390
Systems Application Architecture
These terms are registered trademarks of Microsoft Corporation in the United States, other countries, or both:
Microsoft
Windows
PANTONE is a registered trademark of Pantone LLC.
These terms are trademarks or registered trademarks of Ricoh Co., Ltd., in the United States, other countries,
or both:
ACMA
Advanced Function Presentation
AFP
AFPCC
AFP Color Consortium
AFP Color Management Architecture
Bar Code Object Content Architecture
BCOCA
CMOCA
Color Management Object Content Architecture
InfoPrint
Intelligent Printer Data Stream
IPDS
Mixed Object Document Content Architecture
MO:DCA
Ricoh


Intelligent Mail is a registered trademark of the United States Postal Service.
UP3I is a trademark of UP 3I Limited.
Other company, product, or service names might be trademarks or service marks of others.




Glossary
This glossary contains terms that apply to the
Advanced Function Presentation (AFP) Architecture
and also terms that apply to other related
presentation architectures.
If you do not find the term that you are looking for,
please refer to the IBM Dictionary of Computing,
document number ZC20-1699 or the InfoPrint
Dictionary of Printing.
The following definitions are provided as supporting
information only, and are not intended to be used as
a substitute for the semantics described in the body
of this reference.
A
absolute coordinate. One of the coordinates that identify
the location of an addressable point with respect to the
origin of a specified coordinate system. Contrast with
relative coordinate.
absolute move. A method used to designate a new
presentation position by specifying the distance from the
designated axes to the new presentation position. The
reference for locating the new presentation position is a
fixed position as opposed to the current presentation
position.
absolute positioning. The establishment of a position
within a coordinate system as an offset from the coordinate
system origin. Contrast with relative positioning.
abstract profile. An ICC profile that represents abstract
transforms and does not represent any device model.
Color transformations using abstract profiles are performed
from PCS to PCS. Abstract profiles cannot be embedded in
images.
Abstract Syntax Notation One (ASN.1). A notation for
defining data structures and data types. The notation is
defined in international standard ISO/IEC 8824(E). See
also object identifier.
ACK. See Positive Acknowledge Reply.
Acknowledge Reply. A printer-to-host reply that returns
printer information or reports exceptions. An Acknowledge
Reply can be positive or negative. See also Positive
Acknowledge Reply and Negative Acknowledge Reply.
Acknowledgment Request. A request from the host for
information from the printer. An example of an
Acknowledgment Request is the use of the
acknowledgment-required flag by a host system to request
an Acknowledge Reply from an attached printer.
acknowledgment-required flag (ARQ). A flag that
requests a printer to return an Acknowledge Reply. The
acknowledgment-required flag is bit zero of an IPDS
command's flag byte.
active coded font. The coded font that is currently being
used by a product to process text.
additive primary colors. Red, green, and blue light,
transmitted in video monitors and televisions. When used
in various degrees of intensity and variation, they create all
other colors of light; when superimposed equally, they
create white. Contrast with subtractive primary colors.
addressable position. A position in a presentation space
or on a physical medium that can be identified by a
coordinate from the coordinate system of the presentation
space or physical medium. See also picture element.
Synonymous with position.
Advanced Function Presentation (AFP). An open
architecture for the management of presentable
information that is developed by the AFP Consortium
(AFPC). AFP comprises a number of data stream and data
object architectures:
• Mixed Object Document Content Architecture (MO:DCA);
formerly referred to as AFPDS
• Intelligent Printer Data Stream (IPDS)
• AFP Line Data Architecture
• Bar Code Object Content Architecture (BCOCA)
• Color Management Object Content Architecture
(CMOCA)
• Font Object Content Architecture (FOCA)
• Graphics Object Content Architecture for AFP (AFP
GOCA)
• Image Object Content Architecture (IOCA)
• Metadata Object Content Architecture (MOCA)
• Presentation Text Object Content Architecture (PTOCA)
AEA. See alternate exception action.
AFM file. A file containing the metric information required
for positioning the characters of a font. The metric
information contained in this file was extracted from a PFB
file, in an ASCII file format defined by Adobe Systems Inc.,
and used for character positioning and page formatting.
AFP . See Advanced Function Presentation.
AFP archive. See AFP/A.
AFP Consortium (AFPC). A formal open standards body
that develops and maintains AFP architecture. Information
about the consortium can be found at
www.afpconsortium.org
.


AFP data stream. A presentation data stream that is
processed in AFP environments. The MO:DCA
architecture defines the strategic AFP interchange data
stream. The IPDS architecture defines the strategic AFP
printer data stream.
AFPDS. A term formerly used to identify the composed-
page MO:DCA-based data stream interchanged in AFP
environments. See also MO:DCA and AFP data stream.
AFP environment. Wherever the AFP architecture is
used in any way; by an AFP vendor, an AFP customer, or
any combination thereof.
AFP GOCA. A subset of the GOCA architecture, originally
defined by IBM, specifically designed for AFP
environments. See Graphics Object Content Architecture
(GOCA).
AFP Line Data Architecture. An AFP architecture that
controls formatting of line data using a Page Definition
(PageDef).
AFP Tagging. (1) Associating extra information,
contained in a metadata object, with a given piece of AFP
data. Among other uses, such information could enable
users with vision impairments or other restrictions to make
full use of the content provided by an AFP system. (2) In
MOCA, a known format of a metadata object.
AFP/A. A constrained version of the general MO:DCA
architecture aimed at interoperability for AFP documents in
an archiving system. Refer to the ISO 18565:2015
“Document management – AFP/Archive” standard for a
complete definition of AFP/A.
AIAG. See Automotive Industry Action Group.
AIM. See Automatic Identification Manufacturers, Inc.
all points addressable (APA). The capability to address,
reference, and position data elements at any addressable
position in a presentation space or on a physical medium.
Contrast with character cell addressable, in which the
presentation space is divided into a fixed number of
character-size rectangles in which characters can appear.
Only the cells are addressable. An example of all points
addressable is the positioning of text, graphics, and
images at any addressable point on the physical medium.
See also picture element.
alternate exception action (AEA). In the IPDS
architecture, a defined action that a printer can take when
a clearly defined, but unsupported, request is received.
Control over alternate exception actions is specified by an
Execute Order Anystate Exception-Handling Control
command.
American National Standards Institute (ANSI). An
organization consisting of producers, consumers, and
general interest groups. ANSI establishes the procedures
by which accredited organizations create and maintain
voluntary industry standards in the United States. It is the
United States constituent body of the International
Organization for Standardization (ISO).
anamorphic scaling. Scaling an object differently in the
vertical and horizontal directions. See also scaling,
horizontal font size, and vertical font size.
annotation. (1) A process by which additional data or
attributes, such as highlighting, are associated with a page
or a position on a page. Application of this data or
attributes to the page is typically under the control of the
user. Common functions such as applying adhesive
removable notes to paper documents or using a
transparent highlighter are emulated electronically by the
annotation process. (2) A comment or explanation
associated with the contents of a document component. An
example of an annotation is a string of text that represents
a comment on an image object on a page.
annotation link. In MO:DCA, a link type that specifies the
linkage from a source document component to a target
document component that contains an annotation.
annotation object. In MO:DCA, an object that contains
an annotation. Objects that are targets of annotation links
are annotation objects.
ANSI. See American National Standards Institute.
APA. See all points addressable.
append. In MO:DCA, an addition to or continuation of the
contents of a document component. An example of an
append is a string of text that is an addition to an existing
string of text on a page.
append link. In MO:DCA, a link type that specifies the
linkage from the end of a source document component to a
target document component that contains an append.
append object. In MO:DCA, an object that contains an
append. Objects that are targets of append links are
append objects.
application. (1) The use to which an information system
is put. (2) A collection of software components used to
perform specific types of work on a computer.
application program. A program written for or by a user
that applies to the user's work.
arc. A continuous portion of the curved line of a circle or
ellipse. See also full arc.
architected. Identifies data that is defined and controlled
by an architecture. Contrast with unarchitected.
archive interchange set. A constrained version of the
general MO:DCA architecture aimed at interoperability for
AFP documents in an archiving system. For archive
systems, the key requirement is to make each page stand
AFP data stream • archive interchange set


alone by eliminating the use of resolution-dependent fonts
and images, device-default fonts, and external resources.
See AFP/A.
arc parameters. Variables that specify the curvature of
an arc.
area. In GOCA, a set of closed figures that can be filled
with a pattern or a color.
area filling. A method used to fill an area with a pattern or
a color.
ARQ. See acknowledgment-required flag.
array. A structure that contains an ordered group of data
elements. All elements in an array have the same data
type.
article. The physical item that a bar code identifies.
ascender. The parts of certain lowercase letters, such as
b, d, or f, that at zero-degree character rotation rise above
the top edge of other lowercase letters such as a, c, and e.
Contrast with descender.
ascender height. The character shape's most positive
character coordinate system Y-axis value.
ASCII. Acronym for American Standard Code for
Information Interchange. A standard code used for
information exchange among data processing systems,
data communication systems, and associated equipment.
ASCII uses a coded character set consisting of 7-bit coded
characters.
ASN.1. See Abstract Syntax Notation One.
A space. The distance from the character reference point
to the least positive character coordinate system X-axis
value of the character shape. A-space can be positive,
zero, or negative. See also B space and C space.
aspect ratio. (1) The ratio of the horizontal size of a
picture to the vertical size of the picture. (2) In a bar code
symbol, the ratio of bar height to symbol length.
asynchronous exception. Any exception other than
those used to report a synchronous data-stream defect
(action code X'01' or X'1F'), function no longer achievable
(action code X'06'), or synchronous resource-storage
problem (action code X'0C'). Asynchronous exceptions
occur after the received page station. An example of an
asynchronous exception is a paper jam. See also data-
stream exception. Contrast with synchronous exception.
attribute. A property or characteristic of one or more
constructs. See also character attribute, color attribute,
current drawing attributes, default drawing attributes, line
attributes, marker attributes, and pattern attributes.
audit CMR. A color management resource that reflects
processing that has been done on an object.
Automatic Identification Manufacturers, Inc. (AIM). A
trade organization consisting of manufacturers, suppliers,
and users of bar codes.
Automotive Industry Action Group (AIAG). The
coalition of automobile manufacturers and suppliers
working to standardize electronic communications within
the auto industry.
B
+B. Positive baseline direction.
B. See baseline direction.
background. (1) The part of a presentation space that is
not occupied with object data. Contrast with foreground.
(2) In GOCA, that portion of a graphics primitive that is
mixed into the presentation space under the control of the
current values of the background mix and background
color attributes. (3) In GOCA, that portion of a character
cell that does not represent a character. (4) In bar codes,
the spaces, quiet zones, and area surrounding a printed
bar code symbol.
background color. The color of a background. Contrast
with foreground color.
background mix. (1) An attribute that determines how
the color of the background of a graphics primitive is
combined with the existing color of the graphics
presentation space. (2) An attribute that determines how
the points in overlapping presentation space backgrounds
are combined. Contrast with foreground mix.
band. An arbitrary layer of an image. An image can
consist of one or more bands of data.
bar. In bar codes, the darker element of a printed bar
code symbol. See also element. Contrast with space.
bar code. An array of elements, such as bars, spaces,
and two-dimensional modules that together represent data
elements or characters in a particular symbology. The
elements are arranged in a predetermined pattern
following unambiguous rules defined by the symbology.
See also bar code symbol.
Bar Code command set. In the IPDS architecture, a
collection of commands used to present bar code symbols
in a page, page segment, or overlay.
bar code density. The number of characters per inch
(cpi) in a bar code symbology. In most cases, the range is
three to ten cpi. See also character density, density, and
information density.
arc parameters • bar code density


bar code object area. The rectangular area on a logical
page into which a bar code presentation space is mapped.
Bar Code Object Content Architecture (BCOCA). An
architected collection of constructs used to interchange
and present bar code data.
bar code presentation space. A two-dimensional
conceptual space in which bar code symbols are
generated.
bar code symbol. A combination of characters including
start and stop characters, quiet zones, data characters,
and check characters required by a particular symbology,
that form a complete, scannable entity. See also bar code.
bar code symbology. A bar code language. Bar code
symbologies are defined and controlled by various industry
groups and standards organizations. Bar code
symbologies are described in public domain bar code
specification documents. Synonymous with symbology.
See also Canadian Grocery Product Code (CGPC),
European Article Numbering (EAN), Japanese Article
Numbering (JAN), and Universal Product Code (UPC).
bar height. In bar codes, the bar dimension perpendicular
to the bar width. Synonymous with bar length and height.
bar length. In bar codes, the bar dimension perpendicular
to the bar width. Synonymous with bar height and height.
bar width. In bar codes, the thickness of a bar measured
from the edge closest to the symbol start character to the
trailing edge of the same bar.
bar width reduction. In bar codes, the reduction of the
nominal bar width dimension on film masters or printing
plates to compensate for systematic errors in some printing
processes.
base-and-towers concept. A conceptual illustration of an
architecture that shows the architecture as a base with
optional towers. The base and the towers represent
different degrees of function achieved by the architecture.
baseline. A conceptual line with respect to which
successive characters are aligned. See also character
baseline. Synonymous with printing baseline and
sequential baseline.
baseline coordinate. One of a pair of values that identify
the position of an addressable position with respect to the
origin of a specified I,B coordinate system. This value is
specified as a distance in addressable positions from the I
axis of an I,B coordinate system. Synonymous with B
coordinate.
baseline direction (B). The direction in which successive
lines of text appear on a logical page. Synonymous with
baseline progression and B direction.
baseline extent. A rectangular space oriented around the
character baseline and having one dimension parallel to
the character baseline. The space is measured along the Y
axis of the character coordinate system. For bounded
character boxes, the baseline extent at any rotation is its
character coordinate system Y-axis extent. Baseline extent
varies with character rotation. See also maximum baseline
extent.
baseline increment. The distance between successive
baselines.
baseline offset. The perpendicular distance from the
character baseline to the character box edge that is parallel
to the baseline and has the more positive character
coordinate system Y-axis value. For characters entirely
within the negative Y-axis region, the baseline offset can be
zero or negative. An example is a subscript character.
Baseline offset can vary with character rotation.
baseline presentation origin (B
o). The point on the B
axis where the value of the baseline coordinate is zero.
baseline progression (B). The direction in which
successive lines of text appear on a logical page.
Synonymous with baseline direction and B direction.
base LND. The first Line Descriptor (LND) used to
process an input line-data record. See also reuse LND.
base support level. Within the base-and-towers concept,
the smallest portion of architected function that is allowed
to be implemented. This is represented by a base with no
towers. Synonymous with mandatory support level.
B axis. The axis of the I,B coordinate system that extends
in the baseline or B direction. The B axis does not have to
be parallel to the Yp axis of its bounding Xp,Yp coordinate
space.
Bc. See current baseline presentation coordinate.
bc. See current baseline print coordinate.
BCOCA. See Bar Code Object Content Architecture.
B coordinate. One of a pair of values that identify the
position of an addressable position with respect to the
origin of a specified I,B coordinate system. This value is
specified as a distance in addressable positions from the I
axis of an I,B coordinate system. Synonymous with
baseline coordinate.
B direction (B). The direction in which successive lines of
text appear on a logical page. Synonymous with baseline
direction and baseline progression.
Bearer Bars. Bars that surround an Interleaved 2-of-5 bar
code to prevent misreads and short scans that might occur
when a skewed scanning beam enters or exits the bar
code symbol through its top or bottom edge. When plates
are used in the printing process, Bearer Bars help equalize
bar code object area • Bearer Bars


the pressure exerted by the printing plate over the entire
surface of the symbol to improve print quality. There are
two styles: 1) four bars that completely surround the
bar/space pattern and 2) two bars that are placed at the top
and the bottom of the bar/space pattern.
Begin Segment Introducer (BSI). An IPDS graphics
self-defining field that precedes all of the drawing orders in
a graphics segment.
between-the-pels. The concept of pel positioning that
establishes the location of a pel's reference point at the
edge of the pel nearest to the preceding pel rather than
through the center of the pel.
B extent. The extent in the B-axis direction of an I,B
coordinate system. The B extent must be parallel to one of
the axes of the coordinate system that contains the I,B
coordinate system. The B extent is parallel to the Yp extent
when the B axis is parallel to the Yp axis or to the Xp extent
when the B axis is parallel to the Xp axis.
bi. See initial baseline print coordinate.
big endian. A format for storage or transmission of binary
data in which the most significant bit (or byte) is placed
first. Contrast with little endian.
bilevel. Having two levels; for example, every point in a
bilevel image has the value 1 or 0, representing a colored
image point or empty space. Contrast with multilevel.
bilevel custom pattern. In GOCA, a custom pattern that
is uncolored at definition time, then has a single color
assigned to it when it is used to fill an area. Contrast with
full-color custom pattern.
bilevel device. A device that is used in a manner that
permits it to process two-level color data. Contrast with
multilevel device.
BITS. A data type for architecture syntax, indicating one
or more bytes to be interpreted as bit string information.
blend. A mixing rule in which the intersection of part of a
new presentation space P
new with part of an existing
presentation space P existing changes to a new color attribute
that represents a color-mixing of the color attributes of P new
with the color attributes of P existing. For example, if P new has
foreground color-attribute blue and P existing has foreground
color-attribute yellow, the area where the two foregrounds
intersect changes to a color attribute of green. See also
mixing rule. Contrast with overpaint and underpaint.
Bo. See baseline presentation origin.
body. (1) On a printed page, the area between the top
and bottom margins that can contain data. (2) In a book,
the portion between the front matter and the back matter.
boldface. A heavy-faced type weight. Printing in a heavy-
faced type weight.
boundary alignment. A method used to align image data
elements by adding padding bits to each image data
element.
bounded character box. A conceptual rectangular box,
with two sides parallel to the character baseline, that
circumscribes a character and is just large enough to
contain the character, that is, just touching the shape on all
four sides.
brightness. Attribute of a visual sensation according to
which an area appears to exhibit more or less light.
BSI. See Begin Segment Introducer.
B space. The distance between the character coordinate
system X-axis values of the two extremities of a character
shape. See also A space and C space.
buffered pages. Pages and copies of pages that have
been received but not yet reflected in committed page
counters and copy counters.
BYTE. A data type for architecture syntax consisting of 8
bits and indicating that each byte has no predefined
interpretation. Therefore, in CMOCA, each byte is
interpreted as defined in the tag explanation.
C
calibration. To adjust the correct value of a reading by
comparison to a standard.
Canadian Grocery Product Code (CGPC). The bar
code symbology used to code grocery items in Canada.
cap-M height. The average height of the uppercase
characters in a font. This value is specified by the designer
of a font and is usually the height of the uppercase M.
Cartesian coordinate system. In a plane, an image
coordinate system that has positive values for the X and Y
axis in the top-right quadrant. The origin is the upper left-
hand corner of the bottom-right quadrant. A pair of (x,y)
values corresponds to one image point. Each image point
is described by an image data element.
CCSID. See Coded Character Set Identifier .
CGCSGID. See Coded Graphic Character Set Global
Identifier.
CGPC. See Canadian Grocery Product Code.
CHAR. A data type for architecture syntax, indicating one
or more bytes to be interpreted as character information.
character. (1) A member of a set of elements used for the
organization, control, or representation of data. A character
can be either a graphic character or a control character.
See also graphic character and control character. (2) In
Begin Segment Introducer (BSI) • character


bar codes, a single group of bar code elements that
represent an individual number, letter, punctuation mark, or
other symbol.
character angle. The angle that is between the baseline
of a character string and the horizontal axis of a
presentation space or physical medium.
character attribute. A characteristic that controls the
appearance of a character or character string.
character baseline. A conceptual reference line that is
coincident with the X axis of the character coordinate
system.
character box. A conceptual rectangular box with two
sides parallel to the character baseline. A character's
shape is formed within a character box by a presentation
process, and the character box is then positioned in a
presentation space or on a physical medium. The
character box can be rotated before it is positioned.
character-box reference edges. The four edges of a
character box.
character cell addressable. Allowing characters to be
addressed, referenced, and positioned only in a fixed
number of character-size rectangles into which a
presentation space is divided. Contrast with all points
addressable.
character cell size. The size of a rectangle in a drawing
space used to scale font symbols into the drawing space.
character code. An element of a code page or a cell in a
code table to which a character can be assigned. The
element is associated with a binary value. The assignment
of a character to an element of a code page determines the
binary value that will be used to represent each occurrence
of the character in a character string.
character coordinate system. An orthogonal coordinate
system that defines font and character measurement
distances. The origin is the character reference point. The
X axis coincides with the character baseline.
character density. The number of characters per inch
(cpi) in a bar code symbology. In most cases, the range is
three to ten cpi. See also bar code density, density, and
information density.
character direction. In GOCA, an attribute controlling the
direction in which a character string grows relative to the
inline direction. Values are: left-to-right, right-to-left, top-to-
bottom, and bottom-to-top. Synonymous with direction.
character escapement point. The point where the next
character reference point is usually positioned. See also
character increment and presentation position.
character identifier. The unique name for a graphic
character.
character increment. The distance from a character
reference point to a character escapement point. For each
character, the increment is the sum of a character's A
space, B space, and C space. A character's character
increment is the distance the inline coordinate is
incremented when that character is placed in a
presentation space or on a physical medium. Character
increment is a property of each graphic character in a font
and of the font's character rotation.
character increment adjustment. In a scaled font, an
adjustment to character increment values. The adjustment
value is derived from the kerning track values for the font
used to present the characters.
character metrics. Measurement information that
defines individual character values such as height, width,
and space. Character metrics can be expressed in specific
fixed units, such as pels, or in relative units that are
independent of both the resolution and the size of the font.
Often included as part of the more general term font
metrics. See also character set metrics and font metrics.
character origin. The point within the graphic pattern of a
character that is to be aligned with the presentation
position. See also character reference point.
character pattern. The scan pattern for a graphic
character of a particular size, style, and weight.
character-pattern descriptor. Information that the printer
needs to separate font raster patterns. Each character
pattern descriptor is eight bytes long and specifies both the
character box size and an offset value; the offset value
permits the printer to find the beginning of the character
raster pattern within the character raster pattern data for
the complete coded font.
character positioning. A method used to determine
where a character is to appear in a presentation space or
on a physical medium.
character precision. The acceptable amount of variation
in the appearance of a character on a physical medium
from a specified ideal appearance, including no acceptable
variation. Examples of appearance characteristics that can
vary for a character are character shape and character
position.
character reference point. The origin of a character
coordinate system. The X axis is the character baseline.
See also character origin.
character rotation. The alignment of a character with
respect to its character baseline, measured in degrees in a
clockwise direction. Examples are 0°, 90°, 180°, and 270°.
Zero-degree character rotation exists when a character is
in its customary alignment with the baseline. Character
rotation and font inline sequence are related in that
character rotation is a clockwise rotation; font inline
sequence is a counter-clockwise rotation. Contrast with
rotation.
character angle • character rotation


character set. A finite set of different graphic characters
or control characters that is complete for a given purpose.
For example, the character set in ISO Standard 646, 7-Bit
Coded Character Set for Information Processing
Interchange.
character set attribute. An attribute used to specify a
coded font.
character set metrics. The measurements used in a
font. Examples are height, width, and character increment
for each character of the font. See also character metrics
and font metrics.
character shape. The visual representation of a graphic
character.
character shape presentation. A method used to form a
character shape on a physical medium at an addressable
position.
character shear. The angle of slant of a character cell
that is not perpendicular to a baseline. Synonymous with
shear.
character string. A sequence of characters.
check character. In bar codes, a character included
within a bar code message whose value is used to perform
a mathematical check to ensure the accuracy of that
message. Synonymous with check digit.
check digit. In bar codes, a character included within a
bar code message whose value is used to perform a
mathematical check to ensure the accuracy of that
message. Synonymous with check character.
CID file. A file containing the font information required for
presenting the characters of a font. The shape information
(glyph procedures) contained in this file is in a binary
encoded format defined by Adobe Systems Inc., optimized
for large character set fonts (for example, Japanese
ideographic fonts having several thousand characters).
CIE. See Commission Internationale d'Éclairage.
CIELAB color space. Internationally accepted color
space model used as a standard to define color within the
graphic arts industry, as well as other industries. L*, a*, and
b* are plotted at right angles to one another. Equal
distances in the space represent approximately equal color
difference.
CIEXYZ color space. The fundamental CIE-based color
space that allows colors to be expressed as a mixture of
the three tristimulus values X, Y , and Z.
CJK fonts. Fonts that contain a set of unified ideographic
characters used in the written Chinese, Japanese, and
Korean languages. The character encoding is the same for
each language, but there might be glyph variants between
languages.
clear area. A clear space that contains no machine-
readable marks preceding the start character of a bar code
symbol or following the stop character. Synonymous with
quiet zone. Contrast with intercharacter gap and space.
clipping. Eliminating those parts of a picture that are
outside of a clipping boundary such as a viewing window or
presentation space. See also viewing window.
Synonymous with trimming.
cluster-dot screening. A halftone method that uses
multiple pixels that vary from small to large dots as the
color gets darker. It is characterized by a polka-dot look.
CMAP file. A file containing the mapping of code points to
the character index values used in a CID file. The code
points conform to a particular character coding system that
is used to identify the characters in a document data
stream. The character index values are assigned in a CID
file for identification of the glyph procedure used to define
the character shape. The mapping information in this file is
in an ASCII file format defined by Adobe Systems Inc.
CMOCA. See Color Management Object Content
Architecture.
CMR. See color management resource.
CMY . Cyan, magenta, and yellow, the subtractive primary
colors.
CMYK color space. (1) The color model used in four-
color printing. Cyan, magenta, and yellow, the subtractive
primary colors, are used with black to effectively create a
multitude of other colors. (2) The primary colors used
together in printing to effectively create a multitude of other
colors: cyan, magenta, yellow, and black. Based on the
subtractive color theory; the primary colors used in four-
color printing processes.
Codabar. A bar code symbology characterized by a
discrete, self-checking, numeric code with each character
represented by a standalone group of four bars and the
three spaces between them.
CODE. A data type for architecture syntax that indicates
an architected constant to be interpreted as defined by the
architecture.
Code 39. A bar code symbology characterized by a
variable-length, bidirectional, discrete, self-checking,
alphanumeric code. Three of the nine elements are wide
and six are narrow. It is the standard for LOGMARS (the
Department of Defense) and the AIAG.
Code 128. A bar code symbology characterized by a
variable-length, alphanumeric code with 128 characters.
Coded Character Set Identifier (CCSID). A 16-bit
number identifying a specific set consisting of an encoding
scheme identifier, character set identifiers, code page
character set • Coded Character Set Identifier (CCSID)


identifiers, and other relevant information that uniquely
identifies the coded graphic character representation used.
coded font. (1) A resource containing elements of a code
page and a font character set, used for presenting text,
graphics character strings, and bar code HRI. See also
code page and font character set. (2) In FOCA, a resource
containing the resource names of a valid pair of font
character set and code page resources. The graphic
character set of the font character set must match the
graphic character set of the code page for the coded font
resource pair to be valid. (3) In the IPDS architecture, a
raster font resource containing code points that are directly
paired to font metrics and the raster representation of
character shapes, for a specific graphic character set. (4)
In the IPDS architecture, a font resource containing
descriptive information, a code page, font metrics, and a
digital-technology representation of character shapes for a
specific graphic character set.
coded font local identifier. A binary identifier that is
mapped by the controlling environment to a named
resource to identify a coded font. See also local identifier.
coded graphic character. A graphic character that has
been assigned one or more code points within a code
page.
coded graphic character set. A set of graphic
characters with their assigned code points.
Coded Graphic Character Set Global Identifier
(CGCSGID). A four-byte binary or a ten-digit decimal
identifier consisting of the concatenation of a GCSGID and
a CPGID. The CGCSGID identifies the code point
assignments in the code page for a specific graphic
character set, from among all the graphic characters that
are assigned in the code page.
code page. (1) A resource object containing descriptive
information, graphic character identifiers, and code points
corresponding to a coded graphic character set. Graphic
characters can be added over time; therefore, to
specifically identify a code page, both a GCSGID and a
CPGID should be used. See also coded graphic character
set. (2) A set of assignments, each of which assigns a
code point to a character. Each code page has a unique
name or identifier. Within a given code page, a code point
is assigned to one character. More than one character set
can be assigned code points from the same code page.
See also code point and section.
Code Page Global Identifier (CPGID). A unique code
page identifier that can be expressed as either a two-byte
binary or a five-digit decimal value.
code point. A unique bit pattern that can serve as an
element of a code page or a site in a code table, to which a
character can be assigned. The element is associated with
a binary value. The assignment of a character to an
element of a code page determines the binary value that
will be used to represent each occurrence of the character
in a character string. Code points are one or more bytes
long. See also code table and section.
code table. A table showing the character allocated to
each code point in a code. See also code page and code
point.
color. A visual attribute of things that results from the light
they emit, transmit, or reflect.
colorants. Colors (pigments, dyes, inks) used by a
device, primarily a printer, to reproduce colors.
color attribute. An attribute that affects the color values
provided in a graphics primitive, a text control sequence, or
an IPDS command. Examples of color attributes are
foreground color and background color.
color calibration. The process of altering the behavior of
an input or output device to make it conform to an
established state, specified by a manufacturer, user,
industry specification, or standard.
color component. A dimension of a color value
expressed as a numeric value. For example, a color value
might consist of one, two, three, four, or eight components,
also referred to as channels.
color conversion. The process of converting colors from
one color space to another.
color image. Images whose image data elements are
represented by multiple bits or whose image data element
values are mapped to color values. Constructs that map
image-data-element values to color values are look-up
tables and image-data-element structure parameters.
Examples of color values are screen color values for
displays and color toner values for printers.
colorimetric intent. A gamut mapping method that is
intended to preserve the relationships between in-gamut
colors at the expense of out-of-gamut colors.
colorimetry. The science of measuring color and color
appearance. Classical colorimetry deals primarily with
color matches rather than with color appearance as such.
The main focus of colorimetry has been the development
of methods for predicting perceptual matches on the basis
of physical measurements.
color management. The technology to calibrate the color
of input devices (such as scanners or digital cameras),
display devices, and output devices (such as printers or
offset presses).
Color Management Object Content Architecture
(CMOCA). An architected collection of constructs used for
the interchange and presentation of the color management
information required to render a print file, document, group
of pages or sheets, page, overlay, or data object with color
fidelity.
coded font • Color Management Object Content Architecture (CMOCA)


color management resource. An object that provides
color management in presentation environments.
color management system. A set of software designed
to increase the accuracy and consistency of color between
color devices like a scanner, display, and printer.
color model. The method by which a color is specified.
For example, the RGB color space specifies color in terms
of three intensities for red (R), green (G), and blue (B). Also
referred to as color space.
color of medium. The color of a presentation space
before any data is added to it. Synonymous with reset
color.
color palette. A system of designated colors that are
used in conjunction with each other to achieve visual
consistency.
Color Rendering Dictionary. A PostScript language
construct for converting colors from the CIEXYZ color
space to the device color space. It is analogous to the
“from PCS” part of an ICC printer profile with one rendering
intent; that is, the part used when the profile is a
destination profile.
color space. The method by which a color is specified.
For example, the RGB color space specifies color in terms
of three intensities for red (R), green (G), and blue (B). Also
referred to as color model.
ColorSpace conversion profile. An ICC profile that
provides the relevant information to perform a color space
transformation between the non-device color spaces and
the Profile Connection Space. It does not represent any
device model. ColorSpace conversion profiles can be
embedded in images.
color table. A collection of color element sets. The table
can also specify the method used to combine the intensity
levels of each element in an element set to produce a
specific color. Examples of methods used to combine
intensity levels are the additive method and the subtractive
method. See also color model.
column. A subarray consisting of all elements that have
an identical position within the low dimension of a regular
two-dimensional array.
command. (1) In the IPDS architecture, a structured field
sent from a host to a printer. (2) In GOCA, a data-stream
construct used to communicate from the controlling
environment to the drawing process. The command
introducer is environment dependent. (3) A request for
system action.
command set. A collection of IPDS commands.
command-set vector. Information that identifies an IPDS
command set and data level supported by a printer.
Command-set vectors are returned with an Acknowledge
Reply to an IPDS Sense Type and Model command.
Commission Internationale d'Éclairage (CIE). An
association of international color scientists who produced
the standards that are used as the basis of the description
of color.
complex text layout. The typesetting of writing systems
that require complex transformations between text input
and text display for proper rendering on the screen or the
printed page.
compression algorithm. An algorithm used to compress
image data. Compression of image data can decrease the
volume of data required to represent an image.
construct. An architected set of data such as a structured
field or a triplet.
continuous code. A bar code symbology characterized
by designating all spaces within the symbol as parts of
characters, for example, Interleaved 2 of 5. There is no
intercharacter gap in a continuous code. Contrast with
discrete code.
continuous-form media. Connected sheets. An example
of connected sheets is sheets of paper connected by a
perforated tear strip. Contrast with cut-sheet media.
control character. (1) A character that denotes the start,
modification, or end of a control function. A control
character can be recorded for use in a subsequent action,
and it can have a graphic representation. See also
character. (2) A control function the coded representation
of which consists of a single code point.
control instruction. A data construct transmitted from
the controlling environment and interpreted by the
environment interface to control the operation of the
graphics processor.
controlled white space. White space caused by
execution of a control sequence. See also white space.
controlling environment. The environment in which an
object is embedded, for example, the IPDS and MO:DCA
data streams.
control sequence. A sequence of bytes that specifies a
control function. A control sequence consists of a control
sequence introducer and zero or more parameters.
control sequence chaining. A method used to identify a
sequential string of control sequences so they can be
processed efficiently.
control sequence class. An assigned coded character
that identifies a control sequence's syntax and how that
syntax is to be interpreted. An example of a control
sequence class is X'D3', that identifies presentation text
object control sequences.
color management resource • control sequence class


control sequence function type. The coded character
occupying the fourth byte of an unchained control
sequence introducer. This code defines the function whose
semantics can be prescribed by succeeding control
sequence parameters.
control sequence introducer. The information at the
beginning of a control sequence. An unchained control
sequence introducer consists of a control sequence prefix,
a class, a length, and a function type. A chained control
sequence introducer consists of a length and a function
type.
control sequence length. The number of bytes used to
encode a control sequence excluding the control sequence
prefix and class.
control sequence prefix. The escape character used to
identify a control sequence. The control sequence prefix is
the first byte of a control sequence. An example of a
control sequence prefix is X'2B'.
coordinates. A pair of values that specify a position in a
coordinate space. See also absolute coordinate and
relative coordinate.
coordinate system. A Cartesian coordinate system. An
example is the image coordinate system that uses the
fourth quadrant with positive values for the Y axis. The
origin is the upper left-hand corner of the fourth quadrant.
A pair of (x,y) values corresponds to one image point. Each
image point is described by an image data element. See
also character coordinate system.
copy control. A method used to specify the number of
copies for a presentation space and the modifications to be
made to each copy.
copy counter. Bytes in an Acknowledge Reply that
identify the number of copies of a page that have passed a
particular point in the logical paper path.
copy group. A set of copy subgroups that specify all
copies of a sheet. In the IPDS architecture, a copy group is
specified by a Load Copy Control command. In MO:DCA, a
copy group is specified within a Medium Map. See also
copy subgroup.
copy modification. The process of adding, deleting, or
replacing data on selected copies of a presentation space.
copy set. A collection of pages intended to be printed
multiple times. For example, when multiple copies of a
book or booklet is printed, each copy of the book or booklet
is a copy set. This term was originally used with copy
machines to identify collections of copies that are delivered
as sets or stapled as sets. The term was also used when
printing multiple copies of an MVS data set.
copy subgroup. A part of a copy group that specifies a
number of identical copies of a sheet and all modifications
to those copies. Modifications include the media source,
the media destination, medium overlays to be presented
on the sheet, text suppressions, the number of pages on
the sheet, and either simplex or duplex presentation. In the
IPDS architecture, copy subgroups are specified by Load
Copy Control command entries. In MO:DCA, copy
subgroups are specified by repeating groups in the
Medium Copy Count structured field in a Medium Map. See
also copy group.
correlation. A method used in the IPDS architecture to
match exceptions with commands.
correlation ID. A two-byte value that specifies an
identifier of an IPDS command. The correlation ID is
optional and is present only if bit one of the command's flag
byte is B'1'.
CPGID. See Code Page Global Identifier .
cpi. Characters per inch.
C space. The distance from the most positive character
coordinate system X-axis value of a character shape to the
character escapement point. C-space can be positive,
zero, or negative. See also A space and B space.
current baseline coordinate. The baseline presentation
position at the present time. The baseline presentation
position is the summation of the increments of all baseline
controls since the baseline was established in the
presentation space. The baseline presentation position is
established in a presentation space either as part of the
initialization procedures for processing an object or by an
Absolute Move Baseline control sequence. Synonymous
with current baseline presentation coordinate.
current baseline presentation coordinate (B
c). The
baseline presentation position at the present time. The
baseline presentation position is the summation of the
increments of all baseline controls since the baseline was
established in the presentation space. The baseline
presentation position is established in a presentation space
either as part of the initialization procedures for processing
an object or by an Absolute Move Baseline control
sequence. Synonymous with current baseline coordinate.
current baseline print coordinate (b c). In the IPDS
architecture, the baseline coordinate corresponding to the
current print position on a logical page. The current
baseline print coordinate is a coordinate in an I,B
coordinate system. See also I,B coordinate system.
current drawing attributes. The set of attributes used at
the present time to direct a drawing process. Contrast with
default drawing attributes.
current drawing controls. The set of drawing controls
used at the present time to direct a drawing process.
Contrast with default drawing controls.
current inline coordinate. The inline presentation
position at the present time. This inline presentation
control sequence function type • current inline coordinate


position is the summation of the increments of all inline
controls since the inline coordinate was established in the
presentation space. An inline presentation position is
established in a presentation space either as part of the
initialization procedures for processing an object or by an
Absolute Move Inline control sequence. Synonymous with
current inline presentation coordinate.
current inline presentation coordinate (I c). The inline
presentation position at the present time. This inline
presentation position is the summation of the increments of
all inline controls since the inline coordinate was
established in the presentation space. An inline
presentation position is established in a presentation space
either as part of the initialization procedures for processing
an object or by an Absolute Move Inline control sequence.
Synonymous with current inline coordinate.
current inline print coordinate (i
c). In the IPDS
architecture, the inline coordinate corresponding to the
current print position on a logical page. The current inline
print coordinate is a coordinate in an I,B coordinate
system. See also I,B coordinate system.
current logical page. The logical page presentation
space that is currently being used to process the data
within a page object or an overlay object.
current position. The position identified by the current
presentation space coordinates. For example, the
coordinate position reached after the execution of a
drawing order. See also current baseline presentation
coordinate and current inline presentation coordinate.
Contrast with given position.
custom line type value. A user-defined line type, defined
by a series of pairs of a dash/dot length followed by a move
length. Contrast with standard line type value.
custom pattern. In GOCA, a user-defined pattern,
defined by the picture drawn by a series of drawing orders
between a Begin Custom Pattern drawing order and an
End Custom Pattern drawing order. Custom patterns can
be either bilevel custom patterns or full-color custom
patterns. Contrast with patterns in the default pattern set.
custom pattern mode. In GOCA, a mode that is entered
when a Begin Custom Pattern drawing order is executed
and exited when an End Custom Pattern drawing order is
executed. While in this mode, drawing is done in a
separate, temporary graphics presentation space rather
than in the graphics presentation space of the current
GOCA object.
cut-sheet media. Unconnected sheets. Contrast with
continuous-form media.
D
data block. A deprecated term for object area.
data element. A unit of data that is considered indivisible.
data frame. A rectangular division of computer output on
microfilm.
Data Map. A print control object in a Page Definition
(PageDef) that establishes the page environment and
specifies the mapping of line data to the page.
Synonymous with Page Format.
data mask. A sequence of bits that can be used to
identify boundary alignment bits in image data.
data object. In the IPDS architecture, a presentation-form
object that is either specified within a page or overlay or is
activated as a resource and later included in a page or
overlay via the IDO command. Examples include: PDF
single-page objects, Encapsulated PostScript objects, and
IO Images. See also resource and data object resource.
data-object font. (1) In the IPDS architecture, a
complete-font resource that is a combination of font
components at a particular size, character rotation, and
encoding. A data-object font can be used in a manner
analogous to a coded font. The following useful
combinations can be activated into a data-object font:
• A TrueType/OpenType font, an optional code page, and
optional linked TrueType/OpenType objects; activated at
a particular size, character rotation, and encoding
• A TrueType/OpenType collection, either an index value
or a full font name to identify the desired font within the
collection, an optional code page, and optional linked
TrueType/OpenType objects; activated at a particular
size, character rotation, and encoding
See also data-object-font component. (2) In the MO:DCA
architecture, a complete non-FOCA font resource object
that is analogous to a coded font. Examples of data-object
fonts are TrueType fonts and OpenType fonts.
data-object-font component. In the IPDS architecture, a
font resource that is either printer resident or is
downloaded using object container commands. Data-
object-font components are used as components of a data-
object font. Examples of data-object-font components
include TrueType/OpenType fonts and TrueType/
OpenType collections. See also data-object font.
data object resource. In the IPDS architecture, an
object-container resource or IO-Image resource that is
either printer resident or downloaded. Data object
resources can be:
• Used to prepare for the presentation of a data object;
such as with a color management resource (CMR) or
Resident Color Profile Resource
• Included in a page or overlay via the Include Data Object
command; examples include: PDF single-page objects,
Encapsulated PostScript objects, and IO Images
current inline presentation coordinate (I c) • data object resource


• Invoked from within a data object; examples
include: PDF Resource objects and Non-OCA Resource
objects
See also data object and resource.
data stream. A continuous stream of data that has a
defined format. An example of a defined format is a
structured field.
data-stream exception. In the IPDS architecture, a
condition that exists when the printer detects an invalid or
unsupported command, order, control, or parameter value
from the host. Data-stream exceptions are those whose
action code is X'01', X'19', or X'1F'. See also asynchronous
exception and synchronous exception.
DBCS. See double-byte character set.
decoder. In bar codes, the component of a bar code
reading system that receives the signals from the scanner,
performs the algorithm to interpret the signals into
meaningful data, and provides the interface to other
devices. See also reader and scanner.
decryption. The process of taking encrypted data and
converting it back into data that a human or a computer
can read and understand. See also encryption.
default. A value, attribute, or option that is assumed when
none has been specified and one is needed to continue
processing. See also default drawing attributes and default
drawing controls.
default drawing attributes. The set of drawing attributes
adopted at the beginning of a drawing process and usually
at the beginning of each root segment that is processed.
See also root segment. Contrast with current drawing
attributes.
default drawing controls. The set of drawing controls
adopted at the start of a drawing process and usually at the
start of each root segment that is processed. See also root
segment. Contrast with current drawing controls.
default indicator. A field whose bits are all B'1' indicating
that a hierarchical default value is to be used. The value
can be specified by an external parameter. See also
external parameter.
default pattern set. In GOCA, a set of predefined
patterns, like solid, dots, or horizontal lines. Contrast with
custom pattern.
density. The number of characters per inch (cpi) in a bar
code symbology. In most cases, the range is three to ten
cpi. See also bar code density, character density, and
information density.
deprecated. An architected construct is marked as
“deprecated” to indicate that it should no longer be used
because it has been superseded by a newer construct.
Use or support of a deprecated construct is permitted but
no longer recommended. Constructs are deprecated rather
than immediately removed to provide backward
compatibility.
descender. The part of the character that extends into the
character coordinate system negative Y-axis region.
Examples of letters with descenders at zero-degree
character rotation are g, j, p, q, y, and Q. Contrast with
ascender.
descender depth. The character shape's most negative
character coordinate system Y-axis value.
design metrics. A set of quantitative values,
recommended by a font designer, to describe the
characters in a font.
design size. The size of the unit Em for a font. All relative
font measurement values are expressed as a proportion of
the design size. For example, the width of the letter I can
be specified as one-fourth of the design size.
device attribute . A property or characteristic of a device.
Device-Control command set. In the IPDS architecture,
a collection of commands used to set up a page,
communicate device controls, and manage printer
acknowledgment protocol.
device dependent. Dependent upon one or more device
characteristics. An example of device dependency is a font
whose characteristics are specified in terms of addressable
positions of specific devices. See also system-level font
resource.
device independent. Not dependent upon device
characteristics.
device-independent color space. A CIE-based color
space that allows color to be expressed in a device-
independent way. It ensures colors to be predictably and
accurately matched among various color devices.
device level font resource. A device-specific font object
from which a presentation device can obtain the font
information required to present character images.
device profile. A structure that provides a means of
defining the color characteristics of a given device in a
particular state.
device resolution. The number of pels that can be
printed in an inch, both horizontally and vertically. This is
the resolution that the printer uses when printing. Some
printers can be configured to print with a variety of
resolutions that can be selected by the operator. The
device resolution can be different in the two directions (for
example, a resolution of 360 by 720).
device-version code page. In the IPDS architecture, a
device version of a code page contains all of the
data stream • device-version code page


characters that were registered for the CPGID at the time
the printer was developed; since then, more characters
might have been added to the registry for that CPGID. A
device-version code page is identified by a CPGID. See
also code page.
digital halftoning. A method used to simulate gray levels
on a bilevel device.
digital image. An image whose image data was sampled
at regular intervals to produce a digital representation of
the image. The digital representation is usually restricted to
a specified set of values.
dimension. The attribute of size given to arrays and
tables.
direction. In GOCA, an attribute that controls the
direction in which a character string grows relative to the
inline direction. Values are: left-to-right, right-to-left, top-to-
bottom, and bottom-to-top. Synonymous with character
direction.
discrete code. A bar code symbology characterized by
placing spaces that are not a part of the code between
characters, that is, intercharacter gaps.
dispersed-dot halftone. Any halftone algorithm that
turns on binary pixels individually without grouping them
into clusters. The “smallest available” dots are scattered in
a pseudorandom manner to print varying densities.
Commonly contrasted with cluster-dot screening.
dither. An intentional form of noise added to an image to
randomize quantization error. Dithering an image can
prevent unwanted patterns from appearing within the
image.
DOCS. See drawing order coordinate space.
document. (1) A machine-readable collection of one or
more objects that represents a composition, a work, or a
collection of data. (2) A publication or other written
material.
document component. An architected part of a
document data stream. Examples of document
components are documents, pages, page groups, indexes,
resource groups, objects, and process elements.
document-component hierarchy. In MO:DCA, an
ordering of the document in terms of its lower-level
components. The components are ordered by decreasing
level as follows:
• Print file (highest level)
• Document
• Page group
• Page
• Data object (lowest level)
document content architecture. A family of
architectures that define the syntax and semantics of the
document component. See also document component and
structured field.
document editing. A method used to create or modify a
document.
document element. A self-identifying, variable-length,
bounded record, that can have a content portion that
provides control information, data, or both. An application
or device does not have to understand control information
or data to parse a data stream when all the records in the
data stream are document elements. See also structured
field.
document fidelity. The degree to which a document
presentation preserves the creator's intent.
document formatting. A method used to determine
where information is positioned in presentation spaces or
on physical media.
document presentation. A method used to produce a
visible copy of formatted information on physical media.
dot gain. The phenomenon that occurs when ink is
transferred from the plate to the blanket of the press and
finally to the paper on which it is being printed. A dot for a
halftone or a screen gets larger because of the mechanical
process of transferring ink.
dots per inch. (1) The number of dots that will fit in an
inch. (2) A unit of measure for output resolution. (3) Dots
per inch (dpi) is also used to measure the quality of input
when using a scanner. In this case, dpi becomes a square
function measuring the dots both vertically as well as
horizontally. Consequently, when an image is scanned in at
300 dpi, there are 90,000 dots or bits of electronic data
(300 x 300) in every square inch.
double-byte character set (DBCS). A character set that
can contain up to 65536 characters.
double-byte coded font. A coded font in which the code
points are two bytes long.
downloaded resource. In the IPDS architecture, a
resource in a printer that is installed and removed under
control of a host presentation services program. A
downloaded resource is referenced by a host-assigned
name that is valid for the duration of the session between
the presentation services program and the printer. Contrast
with resident resource.
dpi. See dots per inch.
drag. To use a pointing device to move an object. For
example, clicking on a window border, and dragging it to
make the window larger.
digital halftoning • drag


draw functions. Functions that can be done during the
drawing of a picture. Examples of draw functions are
displaying a picture, boundary computation, and erasing a
graphics presentation space.
drawing control. A control that determines how a picture
is drawn. Examples of drawing controls are arc
parameters, transforms, and the viewing window.
drawing defaults. In GOCA, the set of attributes adopted
at the start of each segment that is processed. These
attributes are set either from standard defaults defined by
the controlling environment or from the Set Current
Defaults instruction that is contained in the Graphics Data
Descriptor. Synonymous with default drawing attributes.
Contrast with current drawing attributes.
drawing order. In GOCA, a graphics construct that the
controlling environment builds to instruct a drawing
processor about what to draw and how to draw it. The
order can specify, for example, that a graphics primitive be
drawn, a change to drawing attributes or drawing controls
be effected, or a segment be called. One or more graphics
primitives can be used to draw a picture. Drawing orders
can be included in a structured field. See also order.
drawing order coordinate space (DOCS). A two-
dimensional conceptual space in which graphics primitives
are drawn, using drawing orders, to create pictures.
drawing process control. In GOCA, a control used by
the graphics processor that determines how a picture is
drawn. Examples of drawing process controls are arc
parameters.
drawing processor. A graphics processor component
that executes segments to draw a picture in a presentation
space. See also segment, graphics presentation space,
and image presentation space.
drawing units. Units of measurement used within a
graphics presentation space to specify absolute and
relative positions.
draw rule. A method used to construct a line, called a
rule, between two specified presentation positions. The line
that is constructed is either parallel to the inline I axis or
baseline B axis.
duplex. A method used to print data on both sides of a
sheet. Normal-duplex printing occurs when the sheet is
turned over the Y
m axis. Tumble-duplex printing occurs
when the sheet is turned over the Xm axis.
duplex printing. A method used to print data on both
sides of a sheet. Contrast with simplex printing.
dynamic segment. A segment whose graphics primitives
can be redrawn in different positions by dragging them
from one position to the next across a picture without
destroying the traversed parts of the picture.
E
EAN. See European Article Numbering.
EBCDIC. See Extended Binary-Coded Decimal
Interchange Code.
Efficient XML Interchange (EXI). A format that allows
XML documents to be encoded as binary data, rather than
as plain text.
element. (1) A bar or space in a bar code character or a
bar code symbol. (2) A structured field in a document
content architecture data stream. (3) In GOCA, a portion of
a segment consisting of either a single order or a group of
orders enclosed in an element bracket, in other words,
between a begin element and an end element. (4) A basic
member of a mathematical or logical class or set.
Em. In printing, a unit of linear measure referring to the
baseline-to-baseline distance of a font, in the absence of
any external leading.
embedded ICC profile. ICC profiles that are embedded
within graphic documents and images. An embedded ICC
profile allows users to transparently move color data
between different computers, networks and even operating
systems without having to worry if the necessary profiles
are present on the destination systems.
Em square. A square layout space used for designing
each of the characters of a font.
encoding scheme. A set of specific definitions that
describe the philosophy used to represent character data.
The number of bits, the number of bytes, the allowable
ranges of bytes, the maximum number of characters, and
the meanings assigned to some generic and specific bit
patterns, are some examples of specifications to be found
in such a definition.
Encoding Scheme Identifier (ESID). A 16-bit number
assigned to uniquely identify a particular encoding scheme
specification. See also encoding scheme.
encryption. A process to manipulate data to achieve data
security. To read an encrypted data string, access to key
information that enables decryption of the data is required.
See also decryption.
environment interface. The part of the graphics
processor that interprets commands and instructions from
the controlling environment.
EPS. Acronym for Encapsulated PostScript. A standard
file format for importing and exporting PostScript language
files among applications in a variety of heterogeneous
environments.
draw functions • EPS


error diffusion halftone. A specific halftone method in
which quantization errors are diffused spatially in a quasi-
random manner.
escapement direction. In FOCA, the direction from a
character reference point to the character escapement
point, that is, the font designer's intended direction for
successive character shapes. See also character direction
and inline direction.
escape sequence. (1) In the IPDS architecture, the first
two bytes of a control sequence. An example of an escape
sequence is X'2BD3'. (2) A string of bit combinations that
is used for control in code extension procedures. The first
of these bit combinations represents the control function
Escape.
ESID. See Encoding Scheme Identifier.
established baseline coordinate. The current baseline
presentation coordinate when no temporary baseline exists
or the last current baseline presentation coordinate that
existed before the first active temporary baseline was
created. If temporary baselines are created, the current
baseline presentation coordinate coincides with the
presentation coordinate of the most recently created
temporary baseline.
European Article Numbering (EAN). The bar code
symbology used to code grocery items in Europe.
exception. (1) An invalid or unsupported data-stream
construct. (2) In the IPDS architecture, a condition
requiring host notification. (3) In the IPDS architecture, a
condition that requires the host to resend data. See also
data-stream exception, asynchronous exception, and
synchronous exception.
exception action. Action taken when an exception is
detected.
exception condition. The condition that exists when a
product finds an invalid or unsupported construct.
exchange. The predictable interpretation of shared
information by a family of system processes in an
environment where the characteristics of each process
must be known to all other processes. Contrast with
interchange.
EXI. See Efficient XML Interchange.
expanded. A type width that widens all characters of a
typeface.
Extended Binary-Coded Decimal Interchange Code
(EBCDIC). A coded character set that consists of eight-bit
coded characters.
Extensible Markup Language (XML). A set of rules for
encoding documents in a format that is both human-
readable and machine-readable.
Extensible Metadata Platform (XMP). An ISO standard,
originally created by Adobe Systems Incorporated, for the
creation, processing, and interchange of standardized and
custom metadata for all kinds of resources.
external leading. The amount of white space, in addition
to the internal leading, that can be added to interline
spacing without degrading the aesthetic appearance of a
font. This value is usually specified by a font designer.
Contrast with internal leading.
external parameter. A parameter for which the current
value can be provided by the controlling environment, for
example, the data stream, or by the application itself.
Contrast with internal parameter.
F
factoring. The movement of a parameter value from one
state to a higher-level state. This permits the parameter
value to apply to all of the lower-level states unless
specifically overridden at the lower level.
FGID. See Font Typeface Global Identifier.
filename map file. A file containing the mapping of object
names to file names for use in establishing a font file
system. Object names and file names do not conform to
the same naming requirements, so it is necessary to
provide a mapping between them. The mapping
information in this file is in an ASCII file format defined by
Adobe Systems Inc.
fillet. A curved line drawn tangential to a specified set of
straight lines. An example of a fillet is the concave junction
formed where two lines meet.
final form data. Data that has been formatted for
presentation.
first read rate. In bar codes, the ratio of the number of
successful reads on the first attempt to the total number of
attempts made to obtain a successful read. Synonymous
with read rate.
fixed medium information. Information that can be
applied to a sheet by a printer or printer-attached device
that is independent of data provided through the data
stream. Fixed medium information does not mix with the
data provided by the data stream and is presented on a
sheet either before or after the text, image, graphics, or bar
code data provided within the data stream. Fixed medium
information can be used to create preprinted forms, or
other types of printing, such as colored logos or
letterheads, that cannot be created conveniently within the
data stream.
fixed metrics. Graphic character measurements in
physical units such as pels, inches, or centimeters.
error diffusion halftone • fixed metrics


FNN linked. In FOCA, the FNN (Font Name map)
structured field permits the mapping of a set of IBM
GCGIDs to the character index values that occur in either a
CMAP file or a rearranged file. Because the set of GCGIDs
and the set of character index values must correspond to
the same set of characters, it is necessary to identify which
CMAP or rearranged file (among the many that could be
located in a font file system) is associated (linked) with the
FNN structured field. Note that the Font Name Map is
known as the Character ID Map in IPDS.
FOCA. See Font Object Content Architecture.
font. A set of graphic characters that have a characteristic
design, or a font designer's concept of how the graphic
characters should appear. The characteristic design
specifies the characteristics of its graphic characters.
Examples of characteristics are character shape, graphic
pattern, style, size, weight class, and increment. Examples
of fonts are fully described fonts, symbol sets, and their
internal printer representations. See also coded font and
symbol set.
font baseline extent. In the IPDS architecture, the sum
of the uniform or maximum baseline offset and the
maximum baseline descender of all characters in the font.
font character set. A FOCA resource containing
descriptive information, font metrics, and the digital
representation of character shapes for a specified graphic
character set.
font control record. The record sent in an IPDS Load
Font Control command to specify a font ID and other font
parameters that apply to the complete font.
font height (FH). (1) A characteristic value, perpendicular
to the character baseline, that represents the size of all
graphic characters in a font. Synonymous with vertical font
size. (2) In a font character set, nominal font height is a
font-designer defined value corresponding to the nominal
distance between adjacent baselines when character
rotation is zero degrees and no external leading is used.
This distance represents the baseline-to-baseline
increment that includes the font's maximum baseline extent
and the designer's recommendation for internal leading.
The font designer can also define a minimum and a
maximum vertical font size to represent the limits of
scaling. (3) In font referencing, the specified font height is
the desired size of the font when the characters are
presented. If this size is different from the nominal vertical
font size specified in a font character set, the character
shapes and character metrics might need to be scaled
prior to presentation.
font index. (1) The mapping of a descriptive font name to
a font member name in a font library. An example of a font
member in a font library is a font resource object.
Examples of attributes used to form a descriptive font
name are typeface, family name, point size, style, weight
class, and width class. (2) In the IPDS architecture, an
LF1-type raster-font resource containing character metrics
for each code point of a raster font or raster-font section for
a particular font inline sequence. There can be a font index
for 0 degree, 90 degree, 180 degree, and 270 degree font
inline sequences. A font index can be downloaded to a
printer using the Load Font Index command. An LF1-type
coded font or coded-font section is the combination of one
fully described font and one font index. See also fully
described font.
font inline sequence. The clockwise rotation of the inline
direction relative to a character pattern. Character rotation
and font inline sequence are related in that character
rotation is a clockwise rotation; font inline sequence is a
counter-clockwise rotation.
font local identifier. A binary identifier that is mapped by
the controlling environment to a named resource to identify
a font. See also local identifier.
font metrics. Measurement information that defines
individual character values such as height, width, and
space, as well as overall font values such as averages and
maximums. Font metrics can be expressed in specific fixed
units, such as pels, or in relative units that are independent
of both the resolution and the size of the font. See also
character metrics and character set metrics.
font modification parameters. Parameters that alter the
appearance of a typeface.
font object. A resource object that contains some or all of
the description of a font.
Font Object Content Architecture (FOCA). An
architected collection of constructs used to describe fonts
and to interchange those font descriptions.
font production. A method used to create a font. This
method includes designing each character image,
converting the character images to a digital-technology
format, defining parameter values for each character,
assigning appropriate descriptive and identifying
information, and creating a font resource that contains the
required information in a format that can be used by a text
processing system. Digital-technology formats include bit
image, vector drawing orders, and outline algorithms.
Parameter values include such attributes as height, width,
and escapement.
font referencing. A method used to identify or
characterize a font. Examples of processes that use font
referencing are document editing, document formatting,
and document presentation.
Font Typeface Global Identifier (FGID). A unique font
identifier that can be expressed as either a two-byte binary
or a five-digit decimal value. The FGID is used to identify a
type style and the following characteristics: posture, weight
class, and width class.
font width (FW). (1) A characteristic value, parallel to the
character baseline, that represents the size of all graphic
FNN linked • font width (FW)


characters in a font. Synonymous with horizontal font
size. (2) In a font character set, nominal font width is a
font-designer defined value corresponding to the nominal
character increment for a font character set. The value is
generally the width of the space character and is defined
differently for fonts with different spacing characteristics.
• For fixed-pitch, uniform character increment fonts: the
fixed character increment, that is also the space
character increment
• For PSM fonts: the width of the space character
• For typographic, proportionally spaced fonts: one-third of
the vertical font size, that is also the default size of the
space character.
The font designer can also define a minimum and a
maximum horizontal font size to represent the limits of
scaling. (3) In font referencing, the specified font width is
the desired size of the font when the characters are
presented. If this size is different from the nominal
horizontal font size specified in a font character set, the
character shapes and character metrics might need to be
scaled prior to presentation.
foreground. (1) The part of a presentation space that is
occupied by object data. (2) In GOCA, the portion of a
graphics primitive that is mixed into the presentation space
under the control of the current value of the mix and color
attributes. See also pel. Contrast with background.
foreground color. A color attribute used to specify the
color of the foreground of a primitive. Contrast with
background color.
foreground mix. An attribute used to determine how the
foreground color of data is combined with the existing color
of a graphics presentation space. An example of data is a
graphics primitive. Contrast with background mix.
form. A division of the physical medium; multiple forms
can exist on a physical medium. For example, a roll of
paper might be divided by a printer into rectangular pieces
of paper, each representing a form. Envelopes are an
example of a physical medium that comprises only one
form. The IPDS architecture defines four types of
forms: cut-sheet media, continuous-form media,
envelopes, and computer output on microfilm. Each type of
form has a top edge. A form has two sides, a front side and
a back side. Synonymous with sheet.
format. The arrangement or layout of data on a physical
medium or in a presentation space.
formatter. A process used to prepare a document for
presentation.
formblend. (1) In IPDS, this mixing rule is only used
when a preprinted form overlay (PFO) is merged as
presentation space P
PFO with other presentation data
(presentation space P data). The intersection of P PFO and
Pdata is assigned the following color attribute:
• Wherever the color attribute of P PFO is either color of
medium, or “white” (CMYK = X'00000000' for a printer,
RGB = X'FFFFFF' for an RGB display), the intersection
is assigned the color attribute of P data. Likewise,
wherever the color attribute of P data is either color of
medium, or “white” (CMYK = X'00000000' for a printer,
RGB = X'FFFFFF' for an RGB display), the intersection
is assigned the color attribute of P PFO.
• With other overlapping color values, the intersection
assumes a new color attribute that is generated in a
device-specific manner to simulate how the P
data color
attribute would mix onto a preprinted form that has the
color attribute of P
PFO. In general, this mixing is a
blending of the color attributes of P data and PPFO that is
determined by the two color attributes and by the print
media and the print technology.
See also mixing rule. (2) In MO:DCA, this mixing rule is
only used when a simulated preprinted form, which is
simulated as either a Medium Preprinted Form overlay (M-
PFO) or a PMC Preprinted Form overlay (PMC-PFO), is
merged as a new presentation space P
n, onto an existing
presentation space P e. The intersection of the foregrounds
of Pn and Pe is assigned the following color attribute:
• Wherever the color attribute of P e is either the color of
medium, or the color white (CMYK = X'00000000' or
RGB = X'FFFFFF'), the intersection is assigned the color
attribute of P n.
• Wherever the color attribute of P e is not the color of
medium and not the color white, the intersection
assumes a new color attribute that is generated in a
device-specific manner to simulate how the P e color
attribute would mix onto a preprinted form that has the
color attribute of P
n. In general, this mixing is a blending
of the color attributes of P n and Pe that is determined by
the two color attributes and by the print media and the
print technology.
Formdef. See Form Definition.
Form Definition (Formdef). A print control object that
contains an environment definition and one or more
Medium Maps. Synonymous with Form map.
Form Map. A print control object that contains an
environment definition and one or more Medium Maps.
Synonymous with Form Definition. See also Medium Map.
full arc. A complete circle or ellipse. See also arc.
full-color custom pattern. In GOCA, a custom pattern
that has its colors completely assigned during its definition,
and can therefore contain any number of colors. Contrast
with bilevel custom pattern.
fully described font. In the IPDS architecture, an LF1-
type raster-font resource containing font metrics,
descriptive information, and the raster representation of
character shapes, for a specific graphic character set. A
fully described font can be downloaded to a printer using
the Load Font Control and Load Font commands. An LF1-
type coded font or coded-font section is the combination of
one fully described font and one font index. See also font
index.
foreground • fully described font


function set. (1) A collection of architecture constructs
and associated values. Function sets can be defined
across or within subsets. (2) In the MO:DCA architecture,
a formal extension to a MO:DCA interchange set that
provides additional capabilities beyond those provided by
the interchange set.
FW. See font width.
G
gamma. A measure of contrast in photographic images.
More precisely, a parameter that describes the shape of
the transfer function for one or more stages in an imaging
pipeline. The transfer function is given by the expression
output = input
gamma where both input and output are scaled
to the range 0 to 1.
gamut. In color reproduction, the subset of colors that can
be accurately represented in a given circumstance, such
as within a given color space or by a certain output device.
GCGID. See Graphic Character Global Identifier.
GCSGID. See Graphic Character Set Global Identifier .
GCUID. See Graphic Character UCS Identifier.
generic. Relating to, or characteristic of, a whole group or
class.
GID. See global identifier.
GIF . See Graphic Interchange Format.
given position. The coordinate position at which drawing
is to begin. A given position is specified in a drawing order.
Contrast with current position.
GLC chain. The set of glyph layout control sequences
used to present a set of glyphs. It consists of a GLC control
sequence followed by one or more GIR/GAR/GOR control
sequence groupings, wherein the GOR is always optional.
These control sequences must be chained together using
PTOCA chaining rules. No other control sequences can be
interspersed within the GIR/GAR/GOR groupings or
between the groupings. The GLC chain may be terminated
by an optional UCT control sequence that carries the code
points of the glyphs rendered by the GLC chain.
Global Identifier (GID). Any of the following:
• Coded Character Set Identifier (CCSID).
• Coded Graphic Character Set Global Identifier
(CGCSGID)
• Code Page Global ID (CPGID)
• Font Typeface Global Identifier (FGID)
• Global Resource Identifier (GRID)
• Graphic Character Global Identifier (GCGID)
• Graphic Character Set Global Identifier (GCSGID)
• Graphic Character UCS Identifier (GCUID)
• An identifier used by a data object to reference a
resource
• In MO:DCA, an encoded graphic character string that
provides a reference name for a document element.
• Object identifier (OID)
• A Uniform Resource Locator (URL), as defined in RFC
1738, Internet Engineering T ask Force (IETF),
December, 1994
Global Resource Identifier (GRID). An eight-byte
identifier that identifies a coded font resource. A GRID
contains the following fields in the order shown:
1. GCSGID of a minimum set of graphic characters
required for presentation. It can be a character set that
is associated with the code page, or with the font
character set, or with both.
2. CPGID of the associated code page
3. FGID of the associated font character set
4. Font width in 1440ths of an inch.
glyph. (1) A member of a set of symbols that represent
data. Glyphs can be letters, digits, punctuation marks, or
other symbols. Synonymous with graphic character. See
also character. (2) In typography, a glyph is a particular
graphical representation of a grapheme, or sometimes
several graphemes in combination (a composed glyph), or
only a part of a grapheme. In computing as well as
typography, the term character refers to a grapheme or
grapheme-like unit of text, as found in natural language
writing systems (scripts). A character or grapheme is a unit
of text, whereas a glyph is a graphical unit. TrueType/
OpenType fonts describe glyphs as a set of paths.
glyph advance. A glyph advance is the absolute
displacement of a glyph's origin on the baseline in the
inline direction from a specific point. In the context of
complex text rendering using GLC chains, the specific
point is the current text position at the beginning of the
GLC chain.
glyph ID. A glyph ID is an index to a table entry in a
TrueType/OpenType font that allows an application to
retrieve the glyph's shape data.
glyph offset. A glyph offset is the offset of the glyph's
origin from the current baseline in the baseline direction. In
the context of complex text rendering using GLC chains,
the current baseline is the baseline defined at the
beginning of the GLC chain.
GOCA. See Graphics Object Content Architecture.
GPS. See graphics presentation space.
gradient. In GOCA, an area fill where one color gradually
changes to another. A gradient is a type of pattern.
function set • gradient


grapheme. (1) A minimally distinctive unit of writing in the
context of a particular writing system. For example, å (“a +
Combining Ring Above” or “Latin Small Letter A with Ring
Above”) is a grapheme in the Danish writing system.
(2) What an end-user thinks of as a character. (3) In
typography, a grapheme is the fundamental unit in written
language. Graphemes include alphabetic letters, Chinese
characters, numerals, punctuation marks, and all the
individual symbols of any of the world's writing systems. In
a typeface each character typically corresponds to a single
glyph, but there are exceptions, such as a font used for a
language with a large alphabet or complex writing system,
where one character may correspond to several glyphs, or
several characters to one glyph.
graphic arts. Image rich, customized content that is
typically used for brochures and marketing documents.
graphic character. A member of a set of symbols that
represent data. Graphic characters can be letters, digits,
punctuation marks, or other symbols. Synonymous with
glyph. See also character.
Graphic Character Global Identifier (GCGID). An
alphanumeric character string used to identify a specific
graphic character. A GCGID can be from four bytes to eight
bytes long.
graphic character identifier. The unique name for a
graphic character in a font or in a graphic character set.
See also character identifier.
Graphic Character Set Global Identifier (GCSGID). A
unique graphic character set identifier that can be
expressed as either a two-byte binary or a five-digit
decimal value.
Graphic Character UCS Identifier (GCUID). An
alphanumeric character string used to identify a specific
graphic character. The GCUID naming scheme is used for
additional characters and sets of characters that exist in
UNICODE; each GCUID begins with the letter U and ends
with a UNICODE code point. The Unicode Standard is fully
compatible with the earlier Universal Character Set (UCS)
Standard.
Graphic Interchange Format (GIF). An image format
type generated specifically for computer use. Its resolution
is usually very low (72 dpi, or that of your computer
screen), making it undesirable for printing purposes.
Graphics command set. In the IPDS architecture, a
collection of commands used to present GOCA data in a
page, page segment, or overlay.
graphics data. Data containing lines, arcs, markers, and
other constructs that describe a picture.
graphics model space. A two-dimensional conceptual
space in which a picture is constructed. All model
transforms are completed before a picture is constructed in
a graphics model space. Contrast with graphics
presentation space. Synonymous with model space.
graphics object. An object that contains graphics data.
See also object.
graphics object area. A rectangular area on a logical
page into which a graphics presentation space window is
mapped.
Graphics Object Content Architecture (GOCA). An
architected collection of constructs used to interchange
and present graphics data. GOCA was originally defined by
IBM; this architecture is no longer used in AFP. Instead, a
subset of GOCA was defined for use in AFP environments,
called AFP GOCA. Usually when the term “GOCA” is used
in AFP documentation, it means AFP GOCA.
graphics presentation space. A two-dimensional
conceptual space in which a picture is constructed. In this
space graphics drawing orders are defined. The picture
can then be mapped onto an output medium. All viewing
transforms are completed before the picture is generated
for presentation on an output medium. An example of a
graphics presentation space is the abstract space
containing graphics pictures defined in an IPDS Write
Graphics Control command. Contrast with graphics model
space.
graphics presentation space window. The portion of a
graphics presentation space that can be mapped to a
graphics object area on a logical page.
graphics primitive. A basic construct used by an output
device to draw a picture. Examples of graphics primitives
are arc, line, fillet, character string, and marker.
graphics processor. The processing capability required
to interpret a GOCA object, that is, to present the picture
represented by the object. It includes the environment
interface, that interprets commands and instructions, and
the drawing processor, that interprets the drawing orders.
graphics segment. A set of graphics drawing orders
contained within a Begin Segment command. See also
segment.
grayscale. A means of specifying color using only one
color component in shades of gray ranging from black to
white.
grayscale image. Images whose image data elements
are represented by multiple bits and whose image data
element values are mapped to more than one level of
brightness through an image data element structure
parameter or a look-up table.
GRID. See Global Resource Identifier.
guard bars. The bars at both ends and the center of an
EAN, JAN, or UPC symbol, that provide reference points
for scanning.
grapheme • guard bars


gzip. A widely-used, free software compression
algorithm.
H
HAID. See Host-Assigned ID.
halftone. A method of generating, on a press or laser
printer, an image that requires varying densities or shades
to accurately render the image. This is achieved by
representing the image as a pattern of dots of varying size.
Larger dots represent darker areas, and smaller dots
represent lighter areas of an image.
hard object. An object that is mapped with a Map
structured field in the environment group of a Form Map,
page, or overlay, that causes the server to retrieve the
object and send it to the presentation device. The object is
then referenced for inclusion at a later time. Contrast with
soft object.
height. In bar codes, the bar dimension perpendicular to
the bar width. Synonymous with bar height and bar length.
hexadecimal. A number system with a base of sixteen.
The decimal digits 0 through 9 and characters A through F
are used to represent hexadecimal digits. The hexadecimal
digits A through F correspond to the decimal numbers 10
through 15, respectively. An example of a hexadecimal
number is X'1B', that is equal to the decimal number 27.
hierarchy. A series of elements that have been graded or
ranked in some useful manner.
highlight color. A spot color that is used to accentuate or
contrast monochromatic areas. See also spot color.
highlighting. The emphasis of displayed or printed
information. Examples are increased intensity of selected
characters on a display screen and exception highlighting
on an IPDS printer.
hollow font. A font design in which the graphic character
shapes include only the outer edges of the strokes.
home state. An initial IPDS operating state. A printer
returns to home state at the end of each page, and after
downloading a font, overlay, or page segment.
horizontal bar code. A bar code pattern presenting the
axis of the symbol in its length dimension parallel to the X
bc
axis of the bar code presentation space. Synonymous with
picket fence bar code.
horizontal font size. (1) A characteristic value, parallel to
the character baseline, that represents the size of all
graphic characters in a font. Synonymous with font
width. (2) In a font character set, nominal horizontal font
size is a font-designer defined value corresponding to the
nominal character increment for a font character set. The
value is generally the width of the space character and is
defined differently for fonts with different spacing
characteristics.
• For fixed-pitch, uniform character increment fonts: the
fixed character increment, that is also the space
character increment
• For PSM fonts: the width of the space character
• For typographic fonts and proportionally spaced fonts:
one-third of the vertical font size, that is also the default
size of the space character.
The font designer can also define a minimum and a
maximum horizontal font size to represent the limits of
scaling. (3) In font referencing, the specified horizontal font
size is the desired size of the font when the characters are
presented. If this size is different from the nominal
horizontal font size specified in a font character set, the
character shapes and character metrics might need to be
scaled prior to presentation.
horizontal scale factor. (1) In outline-font referencing,
the specified horizontal adjustment of the Em square. The
horizontal scale factor is specified in 1440ths of an inch.
When the horizontal and vertical scale factors are different,
anamorphic scaling occurs. See also vertical scale
factor. (2) In FOCA, the numerator of a scaling ratio,
determined by dividing the horizontal scale factor by the
vertical font size. If the value specified is greater or less
than the specified vertical font size, the graphic characters
and their corresponding metric values are stretched or
compressed in the horizontal direction relative to the
vertical direction by the scaling ratio indicated.
host. (1) In the IPDS architecture, a computer that drives
a printer. (2) In IOCA, the host is the controlling
environment.
Host-Assigned ID (HAID). A two-byte ID in the range
X'0001'–X'7EFF' that is assigned to an IPDS resource by a
presentation-services program in the host. This ID uniquely
identifies a resource until that resource is deactivated, in
which case the HAID can be reused. HAIDs are used in
IPDS resource management commands.
Host-Assigned Resource ID. The combination of a
Host-Assigned ID with a section identifier, or a font inline
sequence, or both. The section identifier and font inline
sequence values are ignored for both page segments and
overlays. See also section identifier and font inline
sequence.
HRI. See human-readable interpretation.
HSV color space. (1) A transformation of the RGB color
space that allow colors to be described in terms more
natural to an artist. The name HSV stands for hue,
saturation, and value. (2) Abbreviation for hue, saturation,
and value (a color model used in some graphics
programs). HSV must be translated to another model for
color printing or for forming screen colors.
human-readable interpretation (HRI). The printed
translation of bar code characters into equivalent Latin
alphabetic characters, Arabic numeral decimal digits, and
gzip • human-readable interpretation (HRI)


common special characters normally used for printed
human communication.
hypermedia. Interlinked pieces of information consisting
of a variety of data types such as text, graphics data,
image, audio, and video.
hypertext. Interlinked pieces of information consisting
primarily of text.
I
+I. Positive inline direction.
I. See inline direction.
I axis. The axis of an I,B coordinate system that extends
in the inline direction. The I axis does not have to be
parallel to the Xp axis of its bounding Xp,Yp coordinate
space.
I,B coordinate system. The coordinate system used to
present graphic characters. This coordinate system is used
to establish the inline direction and baseline direction for
the placement of successive graphic characters within a
presentation space. See also Xp,Yp coordinate system.
Ic. See current inline presentation coordinate.
ic. See current inline print coordinate.
ICC. See International Color Consortium.
ICC-absolute colorimetric. A rendering intent in which
the chromatically adapted tristimulus values of the in-
gamut colors are unchanged. It is useful for spot colors and
when simulating one medium on another (proofing). Note
that this definition of ICC-absolute colorimetry is actually
called “relative colorimetry” in CIE terminology, since the
data has been normalized relative to the perfect diffuser
viewed under the same illumination source as the sample.
ICC DeviceLink profile. An ICC profile that provides a
mechanism in which to save and store a series of device
profiles and non-device profiles in a concatenated format
as long as the series begins and ends with a device profile.
This is useful for workflows where a combination of device
profiles and non-device profiles are used repeatedly.
ICC profile. A file in the International Color Consortium
profile format, containing information about the color
reproduction capabilities of a device such as a scanner, a
digital camera, a monitor, or a printer. An ICC profile
includes three elements: 128-byte file header, tag table,
and tagged element data. The intent of this format is to
provide a cross-platform device profile format. Such device
profiles can be used to translate color data created on one
device into another device's native color space.
ID. Identifier. See also Host-Assigned ID (HAID),
correlation ID, font control record, and overlay ID.
IDE. See image data element.
I direction. (1) The direction in which successive
characters appear in a line of text. (2) In GOCA, the
direction specified by the character angle attribute.
Synonymous with inline direction.
IDP . See image data parameter.
IEEE. Institute of Electrical and Electronics Engineers.
I extent. The Xp extent when the I axis is parallel to the Xp
axis or the Yp extent when the I axis is parallel to the Yp
axis. The definition of the I extent depends on the Xp or Yp
extent because the I,B coordinate system is contained
within an Xp,Yp coordinate system.
ii. See initial inline print coordinate.
illuminant. Something that can serve as a source of light.
image. An electronic representation of a picture produced
by means of sensing light, sound, electron radiation, or
other emanations coming from the picture or reflected by
the picture. An image can also be generated directly by
software without reference to an existing picture.
image block. A deprecated term for image object area.
image content. Image data and its associated image
data parameters.
image coordinate system. An X,Y Cartesian coordinate
system using only the fourth quadrant with positive values
for the Y axis. The origin of an image coordinate system is
its upper left hand corner. An X,Y coordinate specifies a
presentation position that corresponds to one and only one
image data element in the image content.
image data. Rectangular arrays of raster information that
define an image.
image data element (IDE). A basic unit of image
information. An image data element expresses the
intensity of a signal at a corresponding image point. An
image data element can use a look-up table to introduce a
level of indirection into the expression of grayscale image
or color image.
image data parameter (IDP). A parameter that describes
characteristics of image data.
image distortion. Deformation of an image such that the
original proportions of the image are changed and the
original balance and symmetry of the image are lost.
image object. An object that contains image data. See
also object.
image object area. A rectangular area on a logical page
into which an image presentation space is mapped.
hypermedia • image object area


Image Object Content Architecture (IOCA). An
architected collection of constructs used to interchange
and present images.
image point. A discrete X,Y coordinate in the image
presentation space. See also addressable position.
image presentation space (IPS). A two-dimensional
conceptual space in which an image is generated.
image segment. Image content bracketed by Begin
Segment and End Segment self-defining fields. See also
segment.
IM Image. A migration image object that is resolution
dependent, bi level, and cannot be compressed or scaled.
Contrast with IO Image.
IM-Image command set. In the IPDS architecture, a
collection of commands used to present IM-Image data in a
page, page segment, or overlay.
immediate mode. The mode in which segments are
executed as they are received and then discarded.
Contrast with store mode.
indexed color. A color image format that contains a
palette of colors to define the image. Indexed color can
reduce file size while maintaining visual quality.
indexed object. An object in a MO:DCA document that is
referenced by an Index Element structured field in a
MO:DCA index. Examples of indexed objects are pages
and page groups.
information density. The number of characters per inch
(cpi) in a bar code symbology. In most cases, the range is
three to ten cpi. See also bar code density, character
density, and density.
initial addressable position. The values assigned to I
c
and Bc by the data stream at the start of object state. The
standard action values are I o and Bo.
initial baseline print coordinate (b i). The baseline
coordinate of the first print position on a logical page. See
also initial inline print coordinate.
initial inline print coordinate (i i). The inline coordinate
of the first print position on a logical page. See also initial
baseline print coordinate.
inline-baseline coordinate system. See I,B coordinate
system.
inline coordinate. The first of a pair of values that
identifies the position of an addressable position with
respect to the origin of a specified I,B coordinate system.
This value is specified as a distance in addressable
positions from the B axis of an I,B coordinate system.
inline direction (I). (1) The direction in which successive
characters appear in a line of text. (2) In GOCA, the
direction specified by the character angle attribute.
Synonymous with I direction.
inline margin. The inline coordinate that identifies the
initial addressable position for a line of text.
inline presentation origin (I o). The point on the I axis
where the value of the inline coordinate is zero.
inline resource. A resource object carried in a resource
group that precedes all documents in an AFP print file.
input profile. An ICC profile that is associated with the
image and describes the characteristics of the device on
which the image was created.
instruction CMR. A color management resource that
identifies processing that is to be done to an object.
Intelligent Printer Data Stream (IPDS). An architected
host-to-printer data stream that contains both data and
controls defining how the data is to be presented.
intensity. The extreme strength, degree, or amount of
ink.
interchange. The predictable interpretation of shared
information in an environment where the characteristics of
each process need not be known to all other processes.
Contrast with exchange.
interchange set. A defined set of MO:DCA function that
describes a level of interchange.
intercharacter adjustment. Additional distance applied
to a character increment that increases or decreases the
distance between presentation positions, effectively
modifying the amount of white space between graphic
characters. The amount of white space between graphic
characters is changed to spread the characters of a word
for emphasis, distribute excess white space on a line
among the words of that line to achieve right justification, or
move the characters on the line closer together as in
kerning. Examples of intercharacter adjustment are
intercharacter increment and intercharacter decrement.
intercharacter decrement. Intercharacter adjustment
applied in the negative I direction from the current
presentation position. See also intercharacter adjustment.
intercharacter gap. In bar codes, the space between two
adjacent bar code characters in a discrete code, for
example, the space between two characters in Code 39.
Synonymous with intercharacter space. Contrast with clear
area, element, and space.
intercharacter increment. Intercharacter adjustment
applied in the positive I direction from the current
presentation position. See also intercharacter adjustment.
Image Object Content Architecture (IOCA) • intercharacter increment


intercharacter space. In bar codes, the space between
two adjacent bar code characters in a discrete code, for
example, the space between two characters in Code 39.
Synonymous with intercharacter gap. Contrast with
element and space.
interleaved bar code. A bar code symbology in which
characters are paired, using bars to represent the first
character and spaces to represent the second. An example
is Interleaved 2 of 5.
intermediate device. In the IPDS architecture, a device
that operates on the data stream and is situated between a
printer and a presentation services program in the host.
Examples include devices that capture and cache
resources and devices that spool the data stream.
internal leading. A font design parameter referring to the
space provided between lines of type to keep ascenders
separated from descenders and to provide an aesthetically
pleasing interline spacing. The value of this parameter
usually equals the difference between the vertical font size
and the font baseline extent. Contrast with external
leading.
internal parameter. In PTOCA, a parameter whose
current value is contained within the object. Contrast with
external parameter.
International Color Consortium (ICC). A group of
companies chartered to develop, use, and promote cross-
platform standards so that applications and devices can
exchange color data without ambiguity.
International Organization for Standardization
(ISO). An organization of national standards bodies from
various countries established to promote development of
standards to facilitate international exchange of goods and
services, and develop cooperation in intellectual, scientific,
technological, and economic activity.
interoperability. The capability to communicate, execute
programs, or transfer data among various functional units
in a way that requires the user to have little or no
knowledge of the unique characteristics of those units.
introducer. In GOCA, that part of the data stream passed
from a controlling environment to a communication
processor that indicates whether entities are to be
processed in immediate mode or store mode. See also
immediate mode and store mode.
Io. See inline presentation origin.
IOCA. See Image Object Content Architecture.
IO Image. An image object containing IOCA constructs.
Contrast with IM Image.
IO-Image command set. In the IPDS architecture, a
collection of commands used to present IOCA data in a
page, page segment, or overlay.
IPDS. See Intelligent Printer Data Stream.
IPDS dialog. A series of IPDS commands and IPDS
Acknowledge Replies. An IPDS dialog begins with the first
IPDS command that an IPDS device receives and ends
either when an IPDS command explicitly ends the dialog or
when the carrying-protocol session ends. There can be
multiple independent sessions each with an IPDS dialog.
See also session.
IPS. See image presentation space.
ISO. See International Organization for Standardization.
italics. A typeface with characters that slant upward to
the right. In FOCA, italics is the common name for the
defined inclined typeface posture attribute or parameter.
J
JAN. See Japanese Article Numbering.
Japanese Article Numbering (JAN). The bar code
symbology used to code grocery items in Japan.
JFIF . See JPEG File Interchange Format.
jog. To cause printedsheets to be stacked in an output
stacker offset from previously stacked sheets. Jogging is
requested by using an IPDS Execute Order Anystate
Alternate Offset Stacker command.
Joint Photographic Experts Group (JPEG). The Joint
Photographic Experts Group (JPEG) is a standards
committee that designed an image compression format.
The compression format they designed is lossy, in that it
deletes information from an image that it considers
unnecessary. JPEG files can range from small amounts of
lossless compression to large amounts of lossy
compression.
JPEG. An image compression standard. See Joint
Photographic Experts Group.
JPEG File Interchange Format (JFIF). (1) JPEG File
Interchange Format (JFIF) is the most common file format
for JPEG images. (TIFF is another file format that can be
used to store JPEG images, and JNG is a third.) JFIF is not
a formal standard; it was designed by a group of
companies (though it is most often associated with C-Cube
Microsystems, one of whose employees published it) and
became a de facto industry standard. (2) Three-
component JPEG images. RGB data is assumed without
gamma correction and the APP0 marker is used to specify
the resolution and optionally the thumbnail.
K
Kanji. A graphic character set for symbols used in
Japanese ideographic alphabets.
intercharacter space • Kanji


kerning. The design of graphic characters so that their
character boxes overlap, resulting in the reduction of space
between characters. This allows characters to be designed
for cursive languages, ligatures, and proportionally spaced
fonts. An example of kerning is the printing of adjacent
graphic characters so they overlap on the left or right side.
kerning track. A straight-line graph that associates
vertical font size with white space adjustment. The result of
this association is used to scale fonts.
kerning track intercept. The X-intercept of a kerning
track for a given vertical font size or white space
adjustment value.
kerning track slope. The slope of a kerning track.
key information. Bytes used by the decryption system to
decrypt data that has been encrypted.
keyword. A two-part self-defining parameter consisting of
a one-byte identifier and a one-byte value.
L
ladder bar code. A bar code pattern presenting the axis
of the symbol in its length dimension parallel to the Ybc axis
of the bar code presentation space. Synonymous with
vertical bar code.
LAN. See local area network.
landscape. A presentation orientation in which the Xm
axis is parallel to the long sides of a rectangular physical
medium. Contrast with portrait.
language. A set of symbols, conventions, and rules that
is used for conveying information. See also pragmatics,
semantics, and syntax.
LCID. See Local Character Set Identifier .
leading. A printer's term for the amount of space between
lines of a printed page. Leading refers to the lead slug
placed between lines of type in traditional typesetting. See
also internal leading and external leading.
leading edge. (1) The edge of a character box that in the
inline direction precedes the graphic character. (2) The
front edge of a sheet as it moves through a printer.
legibility. Characteristics of presented characters that
affect how rapidly, easily, and accurately one character can
be distinguished from another. The greater the speed,
ease, and accuracy of perception, the more legible the
presented characters. Examples of characteristics that
affect legibility are character shape, spacing, and
composition.
LID. See local identifier.
ligature. A single glyph representing two or more
characters. Examples of characters that can be presented
as ligatures are ff and ffi.
linear gradient. In GOCA, a gradient where the color
change takes place along a line. Contrast with radial
gradient.
line art. An image that contains only black and white with
no shades of gray.
line attributes. Those attributes that pertain to straight
and curved lines. Examples of line attributes are line type
and line width.
line data. Unformatted text data. Line data can be
formatted using a Page Definition (PageDef).
line screen frequency. The measure of distance
between the rows of dots that make up a halftone screen.
Lower line screens are used on rougher, low quality
printing substrates (such as newsprint), while higher line
screens are used for high quality print jobs on smooth art
papers.
lines per inch (lpi). (1) The number of lines per inch on a
halftone screen. (2) Units used when measuring line
screen frequency.
line type. A line attribute that controls the appearance of
a line. The line type can either be a standard line type
value or a custom line type value. Contrast with line width.
line width. A line attribute that controls the appearance of
a line. Examples of line width are normal and thick.
Contrast with line type.
link. A logical connection from a source document
component to a target document component.
little endian. A bit or byte ordering where the right-most
bits or bytes (those with a higher address) are most
significant. Contrast with big endian.
Loaded-Font command set. In the IPDS architecture, a
collection of commands used to load font information into a
printer and to deactivate font resources.
local area network (LAN). A data network located on a
user's premises in which serial transmission is used for
direct data communication among data stations.
Local Character Set Identifier (LCID). A local identifier
used as a character, marker, or pattern set attribute.
local identifier (LID). An identifier that is mapped by the
controlling environment to a named resource.
location. A site within a data stream. A location is
specified in terms of an offset in the number of structured
fields from the beginning of a data stream, or in the number
of bytes from another location within the data stream.
kerning • location


logical page. A presentation space. One or more object
areas can be mapped to a logical page. A logical page has
specifiable characteristics, such as size, shape,
orientation, and offset. The shape of a logical page is the
shape of a rectangle. Orientation and offset are specified
relative to a medium coordinate system.
logical unit. A unit of linear measurement expressed with
a unit base and units per unit-base value. For example, in
MO:DCA and IPDS architectures, the following logical units
are used:
• 1 logical unit = 1/1440 inch
(unit base = 10 inches,
units per unit base = 14,400)
• 1 logical unit = 1/240 inch
(unit base = 10 inches,
units per unit base = 2400)
Synonymous with L unit.
look-up table (LUT). (1) A table used to map one or more
input values to one or more output values. (2) A logical list
of colors or intensities. The list has a name and can be
referenced to select a color or intensity. See also color
table.
lossless. A form of image transformation in which all of
the data is retained. Contrast with lossy.
lossy. A form of image transformation in which some of
the data is lost. Contrast with lossless.
lowercase. Pertaining to small letters as distinguished
from capital letters. Examples of small letters are a, b, and
g. Contrast with uppercase.
lpi. See lines per inch.
L unit. A unit of linear measurement expressed with a unit
base and units per unit-base value. For example, in
MO:DCA and IPDS architectures, the following L units are
used:
• 1 L unit = 1/1440 inch
(unit base = 10 inches,
units per unit base = 14,400)
• 1 L unit = 1/240 inch
(unit base = 10 inches,
units per unit base = 2400)
Synonymous with logical unit.
LUT . See look-up table.
Luv color space. The CIE color space in which L*, u* and
v* are plotted at right angles to one another. Equal
distances in the space represent approximately equal color
difference.
M
magnetic ink character recognition
(MICR). Recognition of characters printed with ink that
contains particles of a magnetic material.
mainframe interactive (MFI). Pertaining to systems in
which nonprogrammable terminals are connected to a
mainframe.
mandatory support level. Within the base-and-towers
concept, the smallest portion of architected function that is
allowed to be implemented. This is represented by a base
with no towers. Synonymous with base support level.
marker. A symbol with a recognizable appearance that is
used to identify a particular location. An example of a
marker is a symbol that is positioned by the center point of
its cell.
marker attributes. The characteristics that control the
appearance of a marker. Examples of marker attributes are
size and color.
marker cell. A conceptual rectangular box that can
include a marker symbol and the space surrounding that
symbol.
marker precision. A method used to specify the degree
of influence that marker attributes have on the appearance
of a marker; this method has been made obsolete.
marker set. In GOCA, an attribute used to access a
coded font.
marker symbol. A symbol that is used for a marker.
maximum ascender height. The maximum of the
individual character ascender heights. A value for
maximum ascender height is specified for each supported
character rotation. Contrast with maximum descender
depth.
maximum baseline extent. In FOCA, the sum of the
maximum of the individual character baseline offsets and
the maximum of the individual character descender depths,
for a given font.
maximum descender depth. The maximum of the
individual character descender depths. A value for
maximum descender depth is specified for each supported
character rotation. Contrast with maximum ascender
height.
meaning. A table heading for architecture syntax. The
entries under this heading convey the meaning or purpose
of a construct. A meaning entry can be a long name, a
description, or a brief statement of function.
logical page • meaning


measurement base. A base unit of measure from which
other units of measure are derived.
media. Plural of medium. See also medium.
media destination. The destination to which sheets are
sent as the last step in the print process. Some printers
support several media destinations to allow options such
as print job distribution to one or more specific
destinations, collated copies without having to resend the
document to the printer multiple times, and routing output
to a specific destination for security reasons. Contrast with
media source.
media-relative colorimetric. This rendering intent
rescales the in-gamut, chromatically-adapted tristimulus
values such that the white point of the actual medium is
mapped to the PCS white point (for either input or output).
It is useful for colors that have already been mapped to a
medium with a smaller gamut than the reference medium
(and therefore need no further compression).
media source. The source from which sheets are
obtained for printing. Some printers support several media
sources so that media with different characteristics (such
as size, color, and type) can be selected when desired.
Contrast with media destination.
medium. A two-dimensional conceptual space with a
base coordinate system from which all other coordinate
systems are either directly or indirectly derived. A medium
is mapped onto a physical medium in a presentation-
system
-dependent manner. Synonymous with medium
presentation space. See also logical page, physical
medium, and presentation space.
Medium Map. A print control object in a Form Map that
defines resource mappings and controls modifications to a
form, page placement on a form, and form copy
generation. See also Form Map.
medium preprinted form overlay (M-PFO). In MO:DCA,
a PFO that is designed to simulate a preprinted form for a
sheet-side. An M-PFO is invoked with the MMC structured
field and is applied last to the medium presentation space
after all other data for the sheet-side has been applied.
medium presentation space. A two-dimensional
conceptual space with a base coordinate system from
which all other coordinate systems are either directly or
indirectly derived. A medium presentation space is mapped
onto a physical medium in a presentation-system
-
dependent manner. Synonymous with medium. See also
logical page, physical medium, and presentation space.
metadata. Descriptive information that is associated with
and augments other data.
Metadata command set. In the IPDS architecture, a
collection of commands used to associate MOCA data with
objects.
metadata object. In AFP, the resource object that carries
metadata.
Metadata Object Content Architecture (MOCA). A
resource object architecture to carry metadata that serves
to provide context or additional information about an AFP
object or other AFP data.
MFI. See mainframe interactive.
MICR. See magnetic ink character recognition.
Microfilm frame. A rectangular area on the microfilm
bounded by imaginary, intersecting grid lines within which a
data frame may be recorded. The grid lines are part of
gauges used for checking microfilm, but they do not
actually appear on the microfilm.
mil. 1/1000 inch.
mix. A method used to determine how the color of a
graphics primitive is combined with the existing color of a
graphics presentation space. See also foreground mix and
background mix.
Mixed Object Document Content Architecture
(MO:DCA). An architected, presentation-system-
independent data stream for interchanging documents.
mixing. (1) Combining foreground and background of one
presentation space with foreground and background of
another presentation space in areas where the
presentation spaces intersect. (2) Combining foreground
and background of multiple intersecting object data
elements in the object presentation space.
mixing rule. A method for specifying the color attributes
of the resulting foreground and background in areas where
two presentation spaces intersect.
M/O. A table heading for architecture syntax. The entries
under this heading indicate whether the construct is
mandatory (M) or optional (O).
MOCA. See Metadata Object Content Architecture.
MO:DCA. See Mixed Object Document Content
Architecture.
MO:DCA GA. A MO:DCA function set that supports levels
of PDF used in graphic arts printing.
MO:DCA IS/1. MO:DCA Interchange Set 1. A subset of
MO:DCA that defines an interchange format for
presentation documents.
MO:DCA IS/2. MO:DCA Interchange Set 2. A retired
subset of MO:DCA that defines an interchange format for
presentation documents.
measurement base • MO:DCA IS/2


MO:DCA IS/3. MO:DCA Interchange Set 3. A subset of
MO:DCA that defines an interchange format for print files
that supersedes MO:DCA IS/1.
MO:DCA-L. A MO:DCA subset that defines the OS/2
Presentation Manager (PM) metafile. This format is also
known as a .met file. The definition of this MO:DCA subset
is stabilized and is no longer being developed as part of the
MO:DCA architecture. It is defined in the document
MO:DCA-L: The OS/2 Presentation Manager Metafile
(.met) Format, available at www.afpconsortium.org.
MO:DCA-P . A subset of the MO:DCA architecture that
defines presentation documents. This term is now
synonymous with the term MO:DCA.
model space. A two-dimensional conceptual space in
which a picture is constructed. All model transforms are
completed before a picture is constructed in a graphics
model space. Contrast with graphics presentation space.
Synonymous with graphics model space.
model transform. A transform that is applied to drawing-
order coordinates. Contrast with viewing transform.
module. In a bar code symbology, the nominal width of
the smallest element of a bar or space. Actual bar code
symbology bars and spaces can be a single module wide
or some multiple of the module width. The multiple need
not be an integer.
modulo-N check. A check in which an operand is divided
by a modulus to generate a remainder that is retained and
later used for checking. An example of an operand is the
sum of a set of digits. See also modulus.
modulus. In a modulo check, the number by which an
operand is divided. An example of an operand is the sum
of a set of digits. See also modulo-N check.
monochrome. A single color. Monochrome usually refers
to a black-and-white image. Also referred to as line art or
bitmap mode in Adobe Photoshop
®. See also bilevel.
monospaced font. A font with graphic characters having
a uniform character increment. The distance between
reference points of adjacent graphic characters is constant
in the escapement direction. The blank space between the
graphic characters can vary. Synonymous with uniformly
spaced font. Contrast with proportionally spaced font and
typographic font.
move order. A drawing order that specifies or implies
movement from the current position to a given position.
See also current position and given position.
M-PFO. See medium preprinted form overlay (M-PFO).
multilevel. Having multiple levels; for example, every
point in a multilevel image can have values from 0 to n,
where n is greater than 1. Contrast with bilevel.
multilevel device. A device that is used in a manner that
permits it to process color data of more than two levels.
Contrast with bilevel device.
N
NACK. See Negative Acknowledge Reply.
name. A table heading for architecture syntax. The
entries under this heading are short names that give a
general indication of the contents of the construct.
named color. A color that is specified with a descriptive
name. An example of a named color is “green”.
navigation. The traversing of a document based on links
between contextually related document components.
navigation link. A link type that specifies the linkage from
a source document component to a contextually related
target document component. Navigation links can be used
to support applications such as hypertext and hypermedia.
nColor color space. The color model used in IOCA
images that contain color components that typically do not
match any of the standard AFP color spaces, such as RGB
and CMYK. Often such images contain more than four
color components, although the number of color
components can be anywhere from two to fifteen, inclusive.
Negative Acknowledge Reply (NACK). In the IPDS
architecture, a reply from a printer to a host, indicating that
an exception has occurred. Contrast with Positive
Acknowledge Reply.
neighborhood-operation halftone. Halftone algorithm
that transfers the quantization error due to thresholding to
the unhalftoned neighbors of the current pixel. Error
diffusion is a neighborhood operation since it operates not
only on the input pixel, but also its neighbors. Contrast with
point-operation halftone.
nested resource. A resource that is invoked within
another resource using either an Include command or a
local ID. See also nesting resource.
nesting coordinate space. A coordinate space that
contains another coordinate space. Examples of
coordinate spaces are medium, overlay, page, and object
area.
nesting resource. A resource that invokes nested
resources. See also nested resource.
neutral white. A color attribute that gives a presentation-
system
-dependent default color, typically white on a screen
and black on a printer. Note that neutral white and color of
medium are two different colors.
non-presentation object. An object that is not a
presentation object.
MO:DCA IS/3 • non-presentation object


nonprocess runout (NPRO). An operation that moves
sheets of physical media through the printer without
printing on them. This operation is used to stack the last
printed sheet.
no operation (NOP). A construct whose execution
causes a product to proceed to the next instruction to be
processed without taking any other action.
NOP . See no operation.
normal-duplex printing. Duplex printing that simulates
the effect of physically turning the sheet around the Ym
axis.
NPRO. See nonprocess runout.
N-up. The partitioning of a side of a sheet into a fixed
number of equal size partitions. For example, 4-up divides
each side of a sheet into four equal partitions.
O
object. (1) A collection of structured fields. The first
structured field provides a begin-object function, and the
last structured field provides an end-object function. The
object can contain one or more other structured fields
whose content consists of one or more data elements of a
particular data type. An object can be assigned a name,
that can be used to reference the object. Examples of
objects are presentation text, font, graphics, and image
objects. (2) Something that a user works with to perform a
task.
object area. A rectangular area in a presentation space
into which a data object is mapped. The presentation
space can be for a page or an overlay. Examples are a
graphics object area, an image object area, and a bar code
object area.
object data. A collection of related data elements
bundled together. Examples of object data include graphic
characters, image data elements, and drawing orders.
object identifier (OID). (1) A notation that assigns a
globally unambiguous name to an object or a document
component. The notation is defined in international
standard ISO/IEC 8824(E). (2) A variable length (2-bytes
long to 129-bytes long) binary ID that uniquely identifies an
object. OIDs use the ASN.1 definite-short-form object
identifier format defined in the ISO/IEC 8824:1990(E)
international standard and described in the MO:DCA
Registry Appendix of the Mixed Object Document Content
Architecture Reference. An OID consists of a one-byte
identifier (X'06'), followed by a one-byte length (between
X'00' and X'7F'), followed by 0–127 content bytes.
obsolete. Removed from the architecture, and thus
ignored by receivers.
OCR A. See Optical Character Recognition A.
OCR B. See Optical Character Recognition B.
offline. A device state in which the device is not under the
direct control of a host. Contrast with online.
offset. A table heading for architecture syntax. The
entries under this heading indicate the numeric
displacement into a construct. The offset is measured in
bytes and starts with byte zero. Individual bits can be
expressed as displacements within bytes.
OID. See object identifier.
online. A device state in which the device is under the
direct control of a host. Contrast with offline.
opacity. In bar codes, the optical property of a substrate
material that minimizes showing through from the back
side or the next sheet.
Optical Character Recognition A (OCR A). A font
containing the character set in ANSI standard X3.17-1981,
that contains characters that are both human readable and
machine readable.
Optical Character Recognition B (OCR B). A font
containing the character set in ANSI standard X3.49-1975,
that contains characters that are both human readable and
machine readable.
order. (1) In GOCA, a graphics construct that the
controlling environment builds to instruct a drawing
processor about what to draw and how to draw it. The
order can specify, for example, that a graphics primitive be
drawn, a change to drawing attributes or drawing controls
be effected, or a segment be called. One or more graphics
primitives can be used to draw a picture. Orders can be
included in a structured field. Synonymous with drawing
order. (2) In the IPDS architecture, a construct within an
execute-order command. (3) In IOCA, a functional
operation that is performed on the image content.
ordered page. In the IPDS architecture, a logical page
that does not contain any page segments or overlays, and
in which all text data and all image, graphics, and bar code
objects are ordered. The order of the data objects is such
that physical pel locations on the physical medium are
accessed by the printer in a sequential left-to-right and top-
to-bottom manner, where these directions are relative to
the top edge of the physical medium. Once a physical pel
location has been accessed by the printer, the page data
does not require the printer to access that same physical
pel location again.
orientation. The angular distance a presentation space
or object area is rotated in a specified coordinate system,
expressed in degrees and minutes. For example, the
orientation of printing on a physical medium, relative to the
X
m axis of the Xm,Ym coordinate system. See also
presentation space orientation and text orientation.
nonprocess runout (NPRO) • orientation


origin. The point in a coordinate system where the axes
intersect. Examples of origins are the addressable position
in an Xm,Ym coordinate system where both coordinate
values are zero and the character reference point in a
character coordinate system.
orthogonal. Intersecting at right angles. An example of
orthogonal is the positional relationship between the axes
of a Cartesian coordinate system.
outline font. A shape technology in which the graphic
character shapes are represented in digital form by a
series of mathematical expressions that define the outer
edges of the strokes. The resultant graphic character
shapes can be either solid or hollow.
output profile. An ICC profile that describes the
characteristics of the output device for which the image is
destined. The profile is used to color match the image to
the device's gamut.
overhead. In a bar code symbology, the fixed number of
characters required for starting, stopping, and checking a
bar code symbol.
overlay. (1) A resource object that contains presentation
data such as, text, image, graphics, and bar code data.
Overlays define their own environment and are often used
as pre-defined pages or electronic forms. Overlays are
classified according to how they are presented with other
presentation data: a medium overlay is positioned at the
origin of the medium presentation space before any pages
are presented, and a page overlay is positioned at a
specified point in a page's logical page. A Page
Modification Control (PMC) overlay is a special type of
page overlay used in MO:DCA environments. (2) The final
representation of such an object on a physical medium.
Contrast with page segment.
Overlay command set. In the IPDS architecture, a
collection of commands used to load, deactivate, and
include overlays.
overlay ID. A one-byte ID assigned by a host to an
overlay. Overlay IDs are used in IPDS Begin Overlay,
Deactivate Overlay, Include Overlay, and Load Copy
Control commands.
overlay state. An operating state that allows overlay data
to be downloaded to a product. For example, a printer
enters overlay state from home state when the printer
receives an IPDS Begin Overlay command.
overpaint. A mixing rule in which the intersection of part
of a new presentation space Pnew with an existing
presentation space P existing keeps the color attribute of Pnew.
This is also referred to as “opaque” mixing. See also mixing
rule. Contrast with blend and underpaint.
overscore. A line parallel to the baseline and placed
above the character.
overstrike. In PTOCA, the presentation of a designated
character as a string of characters in a specified text field.
The intended effect is to make the resulting presentation
appear as though the text field, whether filled with
characters or blanks, has been marked out with the
overstriking character.
overstriking. The method used to merge two or more
graphic characters at the same addressable position in a
presentation space or on a physical medium.
P
page. (1) A data stream object delimited by a Begin Page
structured field and an End Page structured field. A page
can contain presentation data such as text, image,
graphics, and bar code data. (2) The final representation
of a page object on a physical medium.
page counter. Bytes in an IPDS Acknowledge Reply that
specify the number of pages that have passed a particular
point in a logical paper path.
PageDef. See Page Definition.
Page Definition (PageDef). A print control object used to
format line data into page data. A Page Definition contains
one or more Data Maps and may optionally specify
conditional processing of the line data. Synonymous with
Page Map. See also Data Map.
Page Format. Synonymous with Data Map.
page group. A named group of sequential pages. A page
group is delimited by a Begin Named Page Group
structured field and an End Named Page Group structured
field. A page group can contain nested page groups. All
pages in the page group inherit the attributes and
processing characteristics that are assigned to the page
group.
Page Map. A print control object used to format line data
into page data. A Page Map contains one or more Data
Maps and may optionally specify conditional processing of
the line data. Synonymous with Page Definition. See also
Data Map.
page segment. (1) In the IPDS architecture, a resource
object that can contain text, image, graphics, and bar code
data. Page segments do not define their own environment,
but are processed in the existing environment. (2) In
MO:DCA, a resource object that can contain any mixture of
bar code objects, graphics objects, and IOCA image
objects. A page segment does not contain an active
environment group. The environment for a page segment
is defined by the active environment group of the including
page or overlay. (3) The final representation of such an
object on a physical medium. Contrast with overlay.
origin • page segment


Page-Segment command set. In the IPDS architecture,
a collection of commands used to load, deactivate, and
include page segments.
page-segment state. An operating state that makes
page-segment data available to a product. For example, a
printer enters page-segment state from home state when it
receives an IPDS Begin Page Segment command.
page state. In the IPDS architecture, an operating state
that makes page data available to a product. For example,
a printer enters page state from home state when it
receives an IPDS Begin Page command.
paginated object. A data object that can be rendered on
a single page or overlay. An example of a paginated object
is a single image in a multi-image TIFF file.
palette. The collection of colors or shades available to a
graphics system or program.
PANTONE®. The proprietary PANTONE color matching
system is the most popular method of specifying extra
colors—not out of the CMYK four color process—for print.
PANTONE colors are numbered and mixed from a base set
of colors. By specifying a specific PANTONE color, a
designer knows that there is little chance of color variance
on the presses.
parameter. (1) A variable that is given a constant value
for a specified application. (2) A variable used in
conjunction with a command to affect its result.
partition. Dividing the medium presentation space into a
specified number of equal-sized areas in a manner
determined by the current physical media.
partitioning. A method used to place parts of a control
into two or more segments or structured fields. Partitioning
can cause difficulties for a receiver if one of the segments
or structured fields is not received or is received out of
order.
pattern. An array of symbols used to fill an area.
pattern attributes. The characteristics that specify the
appearance of a pattern.
pattern reference point. In GOCA, a position in the
graphics presentation space to be used as the origin of a
custom pattern; the pattern is tiled in all directions from this
position.
pattern set. An attribute in GOCA used to access a
symbol set or coded font.
pattern symbol. The geometric construct that is used
repetitively to generate a pattern. Examples of pattern
symbols are dots, squares, and triangles.
PCL. A set of printer commands, developed by Hewlett-
Packard
®, that provide access to printer features.
PCS. (1) See Print Contrast Signal. (2) See Profile
Connection Space.
PDF . An acronym for Acrobat ® Portable Document
Format. PDF files are cross platform and contain all of the
image and font data. Design attributes are retained in a
compressed single package.
pel. The smallest printable or displayable unit on a
physical medium. In computer graphics, the smallest
element of a physical medium that can be independently
assigned color and intensity. Pels per inch is often used as
a measurement of presentation granularity. Synonymous
with picture element and pixel.
perceptual rendering intent. The exact gamut mapping
of the perceptual rendering intent is vendor specific and
involves compromises such as trading off preservation of
contrast in order to preserve detail throughout the tonal
range. It is useful for general reproduction of images,
particularly pictorial or photographic-type images.
PFB file. A file containing the font information required for
presenting the characters of a font. The shape information
(glyph procedures) contained in this file is in a binary
encoded format defined by Adobe Systems Inc., optimized
for small character set fonts having one to two hundred
characters (for example, English, Greek, and Cyrillic).
PFO. See preprinted form overlay (PFO).
physical file. A single operating system file intended for
presentation. The format of the file, and its delineation, is
defined by the operating system.
physical medium. A physical entity on which information
is presented. Examples of a physical medium are a sheet
of paper, a roll of paper, an envelope, and a display screen.
See also medium presentation space and sheet.
physical printable area. A bounded area defined on a
side of a sheet within which printing can take place. The
physical printable area is an attribute of sheet size and
printer capabilities, and cannot be altered by the host. The
physical printable area is mapped to the medium
presentation space, and is used in user printable area and
valid printable area calculations. Contrast with user
printable area and valid printable area.
picket fence bar code. A bar code pattern presenting the
axis of the symbol in its length dimension parallel to the X
bc
axis of the bar code presentation space. Synonymous with
horizontal bar code.
picture chain. A string of segments that defines a picture.
Synonymous with segment chain.
picture element. The smallest printable or displayable
unit on a physical medium. In computer graphics, the
smallest element of a physical medium that can be
independently assigned color and intensity. Picture
Page-Segment command set • picture element


elements per inch is often used as a measurement of
presentation granularity. Synonymous with pel and pixel.
pixel. The smallest printable or displayable unit on a
physical medium. In computer graphics, the smallest
element of a physical medium that can be independently
assigned color and intensity. Picture elements per inch is
often used as a measurement of presentation granularity.
Synonymous with pel and picture element.
PMC-PFO. See PMC preprinted form overlay (PMC-
PFO).
PMC preprinted form overlay (PMC-PFO). In MO:DCA,
a PFO that is designed to simulate a preprinted form for a
page. A PMC-PFO is invoked with the PMC structured field
and is applied last to the page presentation space after all
other data for the page has been applied.
PNG. See Portable Network Graphics.
point. (1) A unit of measure used mainly for measuring
typographical material. There are seventy-two points to an
inch. (2) In GOCA, a parameter that specifies the position
within the drawing order coordinate space. See also
drawing order coordinate space.
point-operation halftone. Any halftone algorithm that
produces output for a given location based only on the
single input pixel at that location, independent of its
neighbors. Thus, it is accomplished by a simple point-wise
comparison of the input image against a predetermined
threshold array or mask. Contrast with neighborhood-
operation halftone.
polyline. A sequence of connected lines.
Portable Network Graphics (PNG). A lossless image
format.
portrait. A presentation orientation in which the X
m axis is
parallel to the short sides of a rectangular physical
medium. Contrast with landscape.
position. A position in a presentation space or on a
physical medium that can be identified by a coordinate
from the coordinate system of the presentation space or
physical medium. See also picture element. Synonymous
with addressable position.
Positive Acknowledge Reply (ACK). In the IPDS
architecture, a reply to an IPDS command that has its
acknowledgment-required flag on and in which no
exception is reported. Contrast with Negative Acknowledge
Reply.
PostScript. A page description programming language
created by Adobe Systems Inc. that is a presentation-
system-independent industry standard for outputting
documents and graphics. It describes pages to any output
device with a PostScript interpreter.
posture. Inclination of a letter with respect to a vertical
axis. Examples of inclination are upright and inclined. An
example of upright is Roman. An example of inclined is
italics.
pragmatics. Information related to the usage of a
construct. See also semantics and syntax.
preprinted form. A form or sheet that is not blank when it
is selected as input media for presentation.
preprinted form overlay (PFO). An overlay and
associated processing designed to simulate a preprinted
form.
presentation data stream. A presentation data stream
that is processed in AFP environments. The MO:DCA
architecture describes the AFP interchange data stream.
The IPDS architecture describes the AFP printer data
stream.
presentation device. A device that produces character
shapes, graphics pictures, images, or bar code symbols on
a physical medium. Examples of a physical medium are a
display screen and a sheet of paper.
presentation object. An object that describes
presentation data such as text, image, and graphics, in a
paginated, final-form format suitable for presentation on a
page. Contrast with non-presentation object.
presentation position. An addressable position that is
coincident with a character reference point. See also
addressable position and character reference point.
presentation process. Synonymous with presentation
system.
presentation services. In printing, a software component
that communicates with a printer using a printer data
stream, such as the IPDS data stream, to print pages,
download and manage print resources, and handle
exceptions.
presentation space. A conceptual address space with a
specified coordinate system and a set of addressable
positions. The coordinate system and addressable
positions can coincide with those of a physical medium.
Examples of presentation spaces are medium, logical
page, and object area. See also graphics presentation
space, image presentation space, logical page, medium
presentation space, and text presentation space.
presentation space orientation. The number of degrees
and minutes a presentation space is rotated in a specified
coordinate system. For example, the orientation of printing
on a physical medium, relative to the X
m axis of the Xm,Ym
coordinate system. See also orientation and text
orientation.
presentation system. A system for presenting data. In
AFP environments such a system normally contains at
pixel • presentation system


least a formatting application, a print server, and a printer.
Synonymous with presentation process.
presentation text object. An object that contains
presentation text data. See also object.
Presentation Text Object Content Architecture
(PTOCA). An architected collection of constructs used to
interchange and present presentation text data.
print contrast. A measurement of the ratio of the
reflectivities between the bars and spaces of a bar code
symbol, commonly expressed as a percent. Synonymous
with Print Contrast Signal.
Print Contrast Signal (PCS). A measurement of the ratio
of the reflectivities between the bars and spaces of a bar
code symbol, commonly expressed as a percent.
Synonymous with print contrast.
print control object. A resource object that contains
layout, finishing, and resource mapping information used to
present a document on physical media. Examples of print
control objects are Form Maps and Medium Maps.
print direction. In FOCA, the direction in which
successive characters appear in a line of text.
print file. A file that is created for the purpose of printing
data. The print file is the highest level of the MO:DCA data-
stream document-component hierarchy.
printing baseline. A conceptual line with respect to which
successive characters are aligned. See also character
baseline. Synonymous with baseline and sequential
baseline.
print quality. In bar codes, the measure of compliance of
a bar code symbol to the requirements of dimensional
tolerance, edge roughness, spots, voids, reflectance, PCS,
and quiet zones defined within a bar code symbology.
print unit. In the IPDS architecture, a group of pages
bounded by XOH-DGB commands and subject to the
group operation keep group together as a print unit. A print
unit is commonly referred to as a print job.
process color. A color that is specified as a combination
of the components, or primaries, of a color space. A
process color is rendered by mixing the specified amounts
of the primaries. An example of a process color is C=0.1,
M=0.8, Y=0.2, K=0.1 in the cyan/magenta/yellow/black
(CMYK) color space. Contrast with spot color.
process element. In MO:DCA, a document component
that is defined by a structured field and that facilitates a
form of document processing that does not affect the
presentation of the document. Examples of process
elements are Tag Logical Elements (TLEs) that specify
document attributes and Link Logical Elements (LLEs) that
specify linkages between document components.
Profile Connection Space (PCS). The reference color
space defined by ICC, in which colors are encoded in order
to provide an interface for connecting source and
destination transforms. The PCS is based on the CIE 1931
standard colorimetric observer.
prolog. The first portion of a segment's data. Prologs are
optional. They contain attribute settings and drawing
controls. Synonymous with segment prolog.
propagation. A method used to retain a segment's
properties through other segments that it calls.
proper subset. A set whose members are also members
of a larger set.
proportion. Relationship of the width of a letter to its
height.
proportionally spaced font. A font with graphic
characters that have varying character increments.
Proportional spacing can be used to provide the
appearance of even spacing between presented
characters and to eliminate excess blank space around
narrow characters. An example of a narrow character is the
letter i. Synonymous with typographic font. Contrast with
monospaced font and uniformly spaced font.
proportional spacing. The spacing of characters in a
printed line so that each character is allotted a space
based on the character's width.
Proportional Spacing Machine font (PSM font). A font
originating with the electric typewriter and having character
increment values that are integer multiples of the narrowest
character width.
PSM font. See Proportional Spacing Machine font.
PTOCA. See Presentation Text Object Content
Architecture.
Q
quantization. The process of reducing an image with
many colors to one with fewer colors, usually in preparation
for its conversion to a palette-based image. As a result,
most parts of the image (that is, most of its pixels) are
given slightly different colors that amounts to a certain level
of error at each location. Since photographic images
usually have extended regions of similar colors that get
converted to the same quantized color, a quantized image
tends to have a flat or banded (contoured) appearance
unless it is also dithered.
quiet zone. A clear space that contains no machine-
readable marks preceding the start character of a bar code
presentation text object • quiet zone


symbol or following the stop character. Synonymous with
clear area. Contrast with intercharacter gap and space.
R
radial gradient. In GOCA, a gradient where the color
change takes place between two full arcs. Contrast with
linear gradient.
range. A table heading for architecture syntax. The
entries under this heading give numeric ranges applicable
to a construct. The ranges can be expressed in binary,
decimal, or hexadecimal. The range can consist of a single
value.
raster. (1) The area of the video display that is covered by
sweeping the electron beam of the display horizontally and
vertically. Normally the electronics of the display sweep
each line horizontally from top to bottom and return to the
top during the vertical retrace interval. (2) In computer
graphics, a predetermined pattern of lines that provides
uniform coverage of a display space. (3) In nonimpact
printers, an on-or-off pattern of electrostatic images
produced by the laser print head under control of the
character generator.
raster direction. An attribute that controls the direction in
which a character string grows relative to the inline
direction. Values are: left-to-right, right-to-left, top-to-
bottom, and bottom-to-top.
rasterize. To convert presentation data into raster
(bitmap) form for display or printing.
raster pattern. A rectangular array of pels arranged in
rows called scan lines.
readability. The characteristics of visual material that
determine the degree of comfort with which it can be read
over a sustained period of time. Examples of
characteristics that influence readability are type quality,
spacing, and composition.
reader. In bar code systems, the scanner or combination
of scanner and decoder. See also decoder and scanner.
read rate. In bar codes, the ratio of the number of
successful reads on the first attempt to the total number of
attempts made to obtain a successful read. Synonymous
with first read rate.
rearranged file. A file containing the mapping of code
points to the character index values used in a CID file and
to the character names used in one or more PFB files. This
is a special case of the CMAP file that
permits linking of
multiple font files and formats together. The code points
conform to a particular character coding system that is
used to identify the characters in a document data stream.
The mapping information in this file is in an ASCII file
format defined by Adobe Systems Inc.
record-format line data. A form of line data where each
record is preceded by a 10-byte identifier. The record is
presented by matching its ID to the ID specified on a
Record Descriptor in the Data Map of a Page Definition.
recording algorithm. An algorithm that determines the
relationship between the physical location and logical
location of image points in image data.
recovery-unit group. (1) In the IPDS architecture, a
group of pages identified by the XOH Define Group
Boundary command and controlled by the Keep-Group-
Together-as-a-Recovery-Unit group operation specified by
the XOH Specify Group Operation command. The
recovery-unit group also includes all copies specified by
the Load Copy Control command. (2) In the MO:DCA
architecture, a group of pages identified as a unit for error
recovery purposes, such as in cases of a printer recovery
from an error that occurs in the middle of the group. A
recovery-unit group is identified by a Begin Named Group
(BNG) and End Named Group (ENG) pair that contains a
Keep Group Together (X'9D') triplet.
redaction. The process of applying an opaque mask over
a page so that a selected portion of the page is visible.
Since this function is typically used to prevent unauthorized
viewing of data, an associated security level is also
provided.
reflectance. In bar codes, the ratio of the amount of light
of a specified wavelength or series of wavelengths
reflected from a test surface to the amount of light reflected
from a barium oxide or magnesium oxide standard under
similar illumination conditions.
relative coordinate. One of the coordinates that identify
the location of an addressable point by means of a
displacement from some other addressable point. Contrast
with absolute coordinate.
relative line. A straight line developed from a specified
point by a given displacement.
relative metrics. Graphic character measurements
expressed as fractions of a square, called the Em square,
whose sides correspond to the vertical size of the font.
Because the measurements are relative to the size of the
Em square, the same metrics can be used for different
point sizes and different raster pattern resolutions. Relative
metrics require defining the unit of measure for the Em
square, the point size of the font, and, if applicable, the
resolution of the raster pattern.
relative move. A method used to establish a new current
position. Distance and direction from the current position
are used to establish the new current position. The
direction of displacement is inline along the I axis in the I
direction, or baseline along the B axis in the B direction, or
both.
radial gradient • relative move


relative positioning. The establishment of a position
within a coordinate system as an offset from the current
position. Contrast with absolute positioning.
rendering intent. A particular gamut-mapping style or
method of converting colors in one gamut to colors in
another gamut. ICC profiles support four different
rendering intents: perceptual, media-relative colorimetric,
saturation, and ICC-absolute colorimetric.
repeating group. A group of parameter specifications
that can be repeated.
repeat string. A method used to repeat the character
content of text data until a given number of characters has
been processed. Any control sequences in the text data
are ignored. This method provides the functional
equivalence of a Transparent Data control sequence when
the given number of repeated characters is equal to the
number of characters in the text data.
reserved. Having no assigned meaning and put aside for
future use. The content of reserved fields is not used by
receivers, and should be set by generators to a specified
value, if given, or to binary zeros. A reserved field or value
can be assigned a meaning by an architecture at any time.
reset color. The color of a presentation space before any
data is added to it. Synonymous with color of medium.
resident resource. In the IPDS architecture, a resource
in a printer or in a resource-caching intermediate device. A
resident resource can be installed manually or can be
captured by the device if it is intended for public use. A
resident resource is referenced by a global ID that is valid
for the duration of the resource's presence in the device.
Contrast with downloaded resource.
resolution. (1) A measure of the sharpness of an input or
output device capability, as given by some measure
relative to the distance between two points or lines that can
just be distinguished. (2) The number of addressable pels
per unit of length.
resolution correction. A method used to present an
image on a printer without changing the physical size or
proportions of the image when the resolutions of the printer
and the image are different.
resolution-correction ratio. The ratio of a device
resolution to an image presentation space resolution.
resolution modification. A method used to write an
image on an image presentation space without changing
the physical size of the image when the resolutions of the
presentation space and the image are different.
resource. An object that is referenced by a data stream
or by another object to provide data or information.
Resource objects can be stored in libraries. In MO:DCA,
resource objects can be contained within a resource group.
Examples of resources are fonts, overlays, and page
segments. See also downloaded resource, resident
resource, and secondary resource.
resource caching. In the IPDS architecture, a function in
a printer or intermediate device whereby downloaded
resources are captured and made resident in the printer or
intermediate device.
retired. Set aside for a particular purpose, and not
available for any other purpose. Retired fields and values
are specified for compatibility with existing products and
identify one of the following:
• Fields or values that have been used by a product in a
manner not compliant with the architected definition
• Fields or values that have been removed from an
architecture
reuse LND. A Line Descriptor (LND) in a chain of LNDs,
also called a reuse chain, where all LNDs process fields in
the same line-data record. See also base LND.
RGB. Red, green and blue, the additive primary colors.
RGB color space. The basic additive color model used
for color video display, as on a computer monitor.
RIP . A raster image processor (RIP) is a hardware or
software tool that processes a presentation data stream
and converts it—rasterizes it—to a printable format.
RM4SCC. See Royal Mail 4 State Customer Code.
Roman. Relating to a type style with upright letters.
root segment. A segment in the picture chain that is not
called by any other segment. If a single segment that is not
in a segment chain is drawn, it is treated as a root segment
for the duration of the drawing process.
rotating. In computer graphics, turning all or part of a
picture about an axis perpendicular to the presentation
space.
rotation. The orientation of a presentation space with
respect to the coordinate system of a containing
presentation space. Rotation is measured in degrees in a
clockwise direction. Zero-degree rotation exists when the
angle between a presentation space's positive X axis and
the containing presentation space's positive X axis is zero
degrees. Contrast with character rotation.
row. A subarray that consists of all elements that have an
identical position within the high dimension of a regular
two-dimensional array.
Royal Mail 4 State Customer Code (RM4SCC). A two-
dimensional bar code symbology developed by the United
Kingdom's Royal Mail postal service for use in automated
mail-sorting processes.
relative positioning • Royal Mail 4 State Customer Code (RM4SCC)


rule. A solid line of any line width.
S
sans serif. A type style characterized by strokes that end
with no flaring or crossing of lines at the stroke ends.
Contrast with serif.
saturation rendering intent. The exact gamut mapping
of the saturation rendering intent is vendor specific and
involves compromises such as trading off preservation of
hue in order to preserve the vividness of pure colors. It is
useful for images that contain objects such as charts or
diagrams.
SBCS. See single-byte character set.
SBIN. A data type for architecture syntax, that indicates
that one or more bytes be interpreted as a signed binary
number, with the sign bit in the high-order position of the
leftmost byte. Positive numbers are represented in true
binary notation with the sign bit set to B'0'. Negative
numbers are represented in twos-complement binary
notation with a B'1' in the sign-bit position.
Scalable Vector Graphics (SVG). An XML-based vector
image format.
scaling. Making all or part of a picture smaller or larger by
multiplying the coordinate values of the picture by a
constant amount. If the same multiplier is applied along
both dimensions, the scaling is uniform, and the
proportions of the picture are unaffected. Otherwise, the
scaling is anamorphic, and the proportions of the picture
are changed. See also anamorphic scaling.
scaling ratio. (1) The ratio of an image-object-area size
to its image-presentation-space size. (2) In FOCA, the
ratio of horizontal to vertical scaling of the graphic
characters. See also horizontal scale factor.
scan line. A series of picture elements. Scan lines in
raster patterns form images. See also picture element and
raster pattern.
scanner. In bar codes, an electronic device that converts
optical information into electrical signals. See also reader.
screen. (1) A halftone-threshold array. (2) The display
surface of a display device such as a computer monitor.
scrolling. A method used to move a displayed image
vertically or horizontally so that new data appears at one
edge as old data disappears at the opposite edge. Data
disappears at the edge toward which an image is moved
and appears at the edge away from which the data is
moved.
SDA. See special data area.
secondary resource. A resource for an object that is
itself a resource.
section. A portion of a double-byte code page that
consists of 256 consecutive entries. The first byte of a two-
byte code point is the section identifier. A code-page
section is also called a code-page ward in some
environments. See also code page and code point.
section identifier. A value that identifies a section.
Synonymous with section number.
section number. A value that identifies a section.
Synonymous with section identifier.
secure overlay. An overlay that can be printed anywhere
within the physical printable area. A secure overlay is not
affected by an IPDS Define User Area command.
segment. (1) In GOCA, a set of graphics drawing orders
contained within a Begin Segment command. See also
graphics segment. (2) In IOCA, image content bracketed
by Begin Segment and End Segment self-defining fields.
See also image segment.
segment chain. A string of segments that defines a
picture. Synonymous with picture chain.
segment exception condition. An architecture-provided
classification of the errors that can occur in a segment.
Segment exception conditions are raised when a segment
error is detected. Examples of segment errors are segment
format, parameter content, and sequence errors.
segment offset. A position within a segment, measured
in bytes from the beginning of the segment. The beginning
of a segment is always at offset zero.
segment prolog. The first portion of a segment's data.
Prologs are optional. They contain attribute settings and
drawing controls. Synonymous with prolog.
segment properties. The segment characteristics used
by a drawing process. Examples of segment properties are
segment name, segment length, chained, dynamic,
highlighted, propagated, and visible.
segment transform. A model transform that is applied to
a whole segment.
self checking. In bar codes, using a checking algorithm
that can be applied to each character independently to
guard against undetected errors.
semantics. The meaning of the parameters of a
construct. See also pragmatics and syntax.
sequential baseline. A conceptual line with respect to
which successive characters are aligned. See also
character baseline. Synonymous with baseline and printing
baseline.
rule • sequential baseline


sequential baseline position. The current addressable
position for a baseline in a presentation space or on a
physical medium. See also baseline coordinate and current
baseline presentation coordinate.
serif. A short line angling from or crossing the free end of
a stroke. Examples are horizontal lines at the tops and
bottoms of vertical strokes on capital letters, for example, I
and H, and the decorative strokes at the ends of the
horizontal members of a capital E. Contrast with sans serif.
server. In a network, hardware or software that provides
facilities to other stations. Examples include: a file server,
a printer server, and a mail server.
session. In the IPDS architecture, the period of time
during which a presentation services program has a two-
way communication with an IPDS device. The session
consists of a physical attachment and a communications
protocol; the communications protocol carries an IPDS
dialog by transparently transmitting IPDS commands and
Acknowledge Replies. See also IPDS dialog.
setup file. In the IPDS architecture, an object container
that provides setup information for a printer. Setup files are
downloaded in home state and take effect immediately.
Setup files are not managed as resources.
setup name. A user-created name for a set of specific
settings on a device. There is at most one setup name
active on a device at any time, and it is allowed for there to
be no active setup name on a device.
shade. Variation of a color produced by mixing it with
black.
shape compression. A method used to compress
digitally encoded character shapes using a specified
algorithm.
shape technology. A method used to encode character
shapes digitally using a specified algorithm.
shear. The angle of slant of a character cell that is not
perpendicular to a baseline. Synonymous with character
shear.
shearline direction. In GOCA, the direction specified by
the character shear and character angle attributes.
sheet. A division of the physical medium; multiple sheets
can exist on a physical medium. For example, a roll of
paper might be divided by a printer into rectangular pieces
of paper, each representing a sheet. Envelopes are an
example of a physical medium that comprises only one
sheet. The IPDS architecture defines four types of
sheets: cut-sheet media, continuous-form media,
envelopes, and computer output on microfilm. Each type of
sheet has a top edge. A sheet has two sides, a front side
and a back side. Synonymous with form.
show through. In bar codes, the generally undesirable
property of a substrate that permits underlying markings to
be seen.
side. A physical surface of a sheet. A sheet has a front
side and a back side. See also sheet.
signed integers. The positive natural numbers (1, 2, 3,
...), their negatives (-1, -2, -3, ...) and the number zero. The
set of all integers is usually denoted in mathematics by Z,
which stands for Zahlen (German for “numbers”).
simplex printing. A method used to print data on one
side of a sheet; the other side is left blank. Contrast with
duplex printing.
single-byte character set (SBCS). A character set that
can contain up to 256 characters.
single-byte coded font. A coded font in which the code
points are one byte long.
slope. The posture, or incline, of the main strokes in the
graphic characters of a font. Slope is specified in degrees
by a font designer.
soft object. An object that is not mapped in an
environment group and is therefore not sent to the
presentation device until it is referenced within a page or
overlay. Contrast with hard object.
space. In bar codes, the lighter element of a printed bar
code symbol, usually formed by the background between
bars. See also element. Contrast with bar, clear area,
intercharacter gap, and quiet zone.
space width. In bar codes, the thickness of a bar code
symbol space measured from the edge closest to the
symbol start character to the trailing edge of the same
space.
spanning. In the IPDS architecture, a method in which
one command is used to start a sequence of constructs.
Subsequent commands continue and terminate that
sequence. See also control sequence chaining.
special data area (SDA). The data area in an IPDS
Acknowledge Reply that contains data requested by the
host or generated by a printer as a result of an exception.
Specifications for Web Offset Publications (SWOP). A
standard set of specifications for color separations, proofs,
and printing to ensure consistency of color printing.
spot. In bar codes, the undesirable presence of ink or dirt
in a bar code symbol space.
spot color. A color that is specified with a unique
identifier such as a number. A spot color is normally
rendered with a custom colorant instead of with a
combination of process color primaries. See also highlight
color. Contrast with process color.
sequential baseline position • spot color


sRGB. One of the standard RGB color spaces, a means
of specifying precisely how any given RGB value should
appear on a display or printed paper or any other output
device. sRGB was promoted by the ICC and submitted for
standardization by the International Electrotechnical
Commission (IEC).
stack. A list that is constructed and maintained so that the
next item to be retrieved and removed is the most recently
stored item still in the list. This is sometimes called last-in-
first-out (LIFO).
standard action. The architecture-defined action to be
taken on detecting an exception condition, when the
controlling environment specifies that processing should
continue.
standard line type value. A predefined line type, like
solid, invisible, or dash dot. Contrast with custom line type
value.
start-stop character or pattern. In bar codes, a special
bar code character that provides the scanner with start and
stop reading instructions as well as a scanning direction
indicator. The start character is normally at the left end and
the stop character at the right end of a horizontally oriented
symbol.
stochastic. A method that uses a pseudo-random dot
size and/or frequency to create halftone images, but
without the visible regularity in the dot patterns found in
traditional screening.
store mode. A mode in which segments are stored for
later execution. Contrast with immediate mode.
stroke. A straight or curved line used to create the shape
of a letter.
structured field. A self-identifying, variable-length,
bounded record, that can have a content portion that
provides control information, data, or both. See also
document element.
structured field introducer. In MO:DCA, the header
component of a structured field that provides information
that is common for all structured fields. Examples of
information that is common for all structured fields are
length, function type, and category type. Examples of
structured field function types are begin, end, data, and
descriptor. Examples of structured field category types are
presentation text, image, graphics, and page.
subordinate object. An object that is lower in the
document-component hierarchy than a given object. For
example, a page is a subordinate object to a page group,
and a page group is a subordinate object to a document.
subpage. A part of a logical page on which line data may
be placed. A line data record is identified as belonging to a
particular subpage with the subpage identifier byte in the
Line Descriptor (LND) structured field. Conditional
processing can be used with a Page Definition to select a
new Data Map and/or Medium Map to take effect before or
after the current subpage is printed.
subset. Within the base-and-towers concept, a portion of
architecture represented by a particular level in a tower or
by a base. See also subsetting tower.
subsetting tower. Within the base-and-towers concept, a
tower representing an aspect of function achieved by an
architecture. A tower is independent of any other towers. A
tower can be subdivided into subsets. A subset contains all
the function of any subsets below it in the tower. See also
subset.
substrate. In bar codes, the surface on which a bar code
symbol is printed.
subtractive primary colors. Cyan, magenta, and yellow
colorants used to subtract a portion of the white light that is
illuminating an object. Subtractive colors are reflective on
paper and printed media. When used together with various
degrees of coverage and variation, they have the ability to
create billions of other colors. Contrast with additive
primary colors.
suppression. A method used to prevent presentation of
specified data. Examples of suppression are the
processing of text data without placing characters on a
physical medium and the electronic equivalent of the “spot
carbon,” that prevents selected data from being presented
on certain copies of a presentation space or a physical
medium.
surrogate pair. A sequence of two Unicode code points
that allow for the encoding of as many as 1 million
additional characters without any use of escape codes.
surrogates. A way to refer to one or more surrogate
pairs.
SVG. See Scalable Vector Graphics.
SWOP . See Specifications for Web Offset Publications.
symbol. (1) A visual representation of something by
reason of relationship, association, or convention. (2) In
GOCA, the subpicture referenced as a character definition
within a font character set and used as a character, marker,
or fill pattern. A bitmap can also be referenced as a symbol
for use as a fill pattern. See also bar code symbol.
symbol length. In bar codes, the distance between the
outside edges of the quiet zones of a bar code symbol.
symbology. A bar code language. Bar code symbologies
are defined and controlled by various industry groups and
standards organizations. Bar code symbologies are
described in public domain bar code specification
documents. Synonymous with bar code symbology. See
also Canadian Grocery Product Code (CGPC), European
sRGB • symbology


Article Numbering (EAN), Japanese Article Numbering
(JAN), and Universal Product Code (UPC).
symbol set. A coded font that is usually simpler in
structure than a fully described font. Symbol sets are used
where typographic quality is not required. Examples of
devices that might not provide typographic quality are dot-
matrix printers and displays. See also character set,
marker set, and pattern set.
synchronous exception. In the IPDS architecture, a
data-stream, function no longer achievable, or resource-
storage exception that must be reported to the host before
a printer can return a Positive Acknowledge Reply or can
increment the received-page counter for a page containing
the exception. Synchronous exceptions are those with
action code X'01', X'06', X'0C', or X'1F'. See also data-
stream exception. Contrast with asynchronous exception.
syntax. The rules governing the structure of a construct.
See also pragmatics and semantics.
system-level font resource. A common-source font from
which:
• Document-processing applications can obtain resolution-
independent formatting information.
• Device-service applications can obtain device-specific
presentation information.
T
tag. A data structure that is used within the data portion of
a color management resource (CMR). A CMR tag consists
of TagID, FieldType, Count, and ValueOffset.
Tagged Image File Format (TIFF). A rich and flexible
graphics image format.
temporary baseline. The shifted baseline used for
subscript and superscript.
temporary baseline coordinate. The B value of the I,B
coordinate pair of an addressable position on the
temporary baseline.
temporary baseline increment. A positive or negative
value that is added to the current baseline presentation
coordinate to specify the position of a temporary baseline
in a presentation space or on a physical medium. Several
increments might have been used to place a temporary
baseline at the current baseline presentation coordinate.
tertiary resource. A resource for an object that is itself a
secondary resource to another resource.
text. A graphic representation of information. Text can
consist of alphanumeric characters and symbols arranged
in paragraphs, tables, columns, and other shapes. An
example of text is the data sent in an IPDS Write Text
command.
Text command set. In the IPDS architecture, a collection
of commands used to present PTOCA text data in a page,
page segment, or overlay.
text major. A description for text where the Presentation
Text Data Descriptor (PTD) is specified in page controls. In
MO:DCA, the PTD is in the Active Environment Group
(AEG) for the page; in IPDS, the PTD is specified as initial
text-major conditions in the Logical Page Descriptor
command.
text object. (1) An object that contains text data. (2) A
presentation-system-independent, self-defining
representation of a two-dimensional presentation space,
called the text object space, that contains presentation text
data.
text object space. Synonymous with text presentation
space.
text orientation. A description of the appearance of text
as a combination of inline direction and baseline direction.
See also baseline direction, inline direction, orientation,
and presentation space orientation.
text presentation. The transformation of document
graphic character content and its associated font
information into a visible form. An example of a visible form
of text is character shapes on a physical medium.
text presentation space. A two-dimensional conceptual
space in which text is generated for presentation on an
output medium.
throughscore. A line parallel to the baseline and placed
through the character.
TIFF . See Tagged Image File Format.
tint. Variation of a color produced by mixing it with white.
toned. Containing marking agents such as toner or ink.
Contrast with untoned.
tone transfer curve. A mathematical representation of
the relationship between the input and output of a system,
subsystem, or equipment. The function is normally one
dimensional consisting of a single channel of input
corresponding to a single channel of output. In imaging
systems, it is mainly used for contrast adjustments. In
printing, the tone transfer curve is also used to modify
images to compensate for dot gain.
transform. A modification of one or more characteristics
of a picture. Examples of picture characteristics that can be
transformed are position, orientation, and size. See also
model transform, segment transform, and viewing
transform.
transform matrix. A matrix that is applied to a set of
coordinates to produce a transform.
symbol set • transform matrix


translating. In computer graphics, moving all or part of a
picture in the presentation space from one location to
another without rotating.
transparent data. A method used to indicate that any
control sequences occurring in a specified portion of data
can be ignored.
trimming. Eliminating those parts of a picture that are
outside of a clipping boundary such as a viewing window or
presentation space. See also viewing window.
Synonymous with clipping.
triplet. A three-part self-defining variable-length
parameter consisting of a length byte, an identifier byte,
and parameter-value bytes.
triplet identifier. A one-byte type identifier for a triplet.
tristimulus values. Three values that together are used
to describe a specific color. These values are the amounts
of three reference colors (such as red, green, and blue)
that can be mixed to give the same visual sensation as the
specific color.
truncation. Planned or unplanned end of a presentation
space or data presentation. This can occur when the
presentation space extends beyond one or more
boundaries of its containing presentation space or when
there is more data than can be contained in the
presentation space.
tumble-duplex printing. A method used to simulate the
effect of physically turning a sheet around the X
m axis.
twip. A unit of measure equal to 1/20 of a point. There are
1440 twips in one inch.
type. A table heading for architecture syntax. The entries
under this heading indicate the types of data present in a
construct. Examples include: BITS, CHAR, CODE, SBIN,
UBIN, UNDF.
typeface. All characters of a single type family or style,
weight class, width class, and posture, regardless of size.
For example, Helvetica Bold Condensed Italics, in any
point size.
type family. All characters of a single design, regardless
of attributes such as width, weight, posture, and size.
Examples are Courier and Gothic.
type structure. Attributes of characters other than type
family or typeface. Examples are solid shape, hollow
shape, and overstruck.
type style. The form of characters within the same font,
for example, Courier or Gothic.
type weight. A parameter indicating the degree of
boldness of a typeface. A character's stroke thickness
determines its type weight. Examples are light, medium,
and bold. Synonymous with weight class.
type width. A parameter indicating a relative change from
the font's normal width-to-height ratio. Examples are
normal, condensed, and expanded. Synonymous with
width class.
typographic font. A font with graphic characters that
have varying character increments. Proportional spacing
can be used to provide the appearance of even spacing
between presented characters and to eliminate excess
blank space around narrow characters. An example of a
narrow character is the letter i. Synonymous with
proportionally spaced font. Contrast with monospaced font
and uniformly spaced font.
U
UBIN. A data type for architecture syntax, indicating one
or more bytes to be interpreted as an unsigned binary
number.
unarchitected. Identifies data that is neither defined nor
controlled by an architecture. Contrast with architected.
unbounded character box. A character box that can
have blank space on any sides of the character shape.
underpaint. A mixing rule in which the intersection of part
of a new presentation space Pnew with part of an existing
presentation space P existing keeps the color attribute of
Pexisting. This is also referred to as “transparent” or “leave
alone” mixing. See also mixing rule. Contrast with blend
and overpaint.
underscore. A method used to create an underline
beneath the characters in a specified text field. An example
of underscore is the line presented under one or more
characters. Also a special graphic character used to
implement the underscoring function.
UNDF . A data type for architecture syntax, indicating one
or more bytes that are undefined by the architecture.
Unicode. A character encoding standard for information
processing that includes all major scripts of the world.
Unicode defines a consistent way of encoding multilingual
text. Unicode specifies a numeric value, a name, and other
attributes, such as directionality, for each of its characters;
for example, the name for $ is “dollar sign” and its numeric
value is X'0024'. This Unicode value is called a Unicode
code point and is represented as U+nnnn. Unicode
provides for three encoding forms (UTF-8, UTF-16, and
UTF-32), described as follows:
UTF-8 A byte-oriented form that is designed for
ease of use in traditional ASCII
environments. Each UTF-8 code point
contains from one to four bytes. All
Unicode code points can be encoded in
translating • UTF-8


UTF-8 and all 7-bit ASCII characters can
be encoded in one byte.
UTF-16 The default Unicode encoding. A fixed,
two-byte Unicode encoding form that
can contain surrogates and identifies the
byte order of each UTF-16 code point via
a Byte Order Mark in the first 2 bytes of
the data. Surrogates are pairs of
Unicode code points that allow for the
encoding of as many as 1 million
additional characters without any use of
escape codes.
UTF-16BE UTF-16 that uses big endian byte order;
this is the byte order for all multi-byte
data within AFP data streams. The Byte
Order Mark is not necessary when the
data is externally identified as UTF-16BE
(or UTF-16LE).
UTF-16LE UTF-16 that uses little endian byte order.
UTF-32 A fixed, four-byte Unicode encoding form
in which each UTF-32 code point is
precisely identical to the Unicode code
point.
UTF-32BE UTF-32 serialized as bytes in most-
significant-byte-first order (big endian).
UTF-32BE is structurally the same as
UCS-4.
UTF-32LE UTF-32 serialized as bytes in least-
significant-byte-first order (little endian).
uniformly spaced font. A font with graphic characters
having a uniform character increment. The distance
between reference points of adjacent graphic characters is
constant in the escapement direction. The blank space
between the graphic characters can vary. Synonymous
with monospaced font. Contrast with proportionally spaced
font and typographic font.
Uniform Symbol Specification (USS). A series of bar
code symbology specifications published by AIM; currently
included are USS-Interleaved 2 of 5, USS-39, USS-93,
USS-Codabar, and USS-128.
unit base. A one-byte code that represents the length of
the measurement base. For example, X'00' might specify
that the measurement base is ten inches.
Universal Product Code (UPC). A standard bar code
symbology, commonly used to mark the price of items in
stores, that can be read and interpreted by a computer.
untoned. Unmarked portion of a physical medium.
Contrast with toned.
UP³I. Universal Printer Pre- and Post-Processing
Interface; an industry standard interface designed for use
in complex printing systems. A specification for this
interface can be obtained at www.afpconsortium.org
.
UPA. See user printable area.
UPC. See Universal Product Code.
uppercase. Pertaining to capital letters. Examples of
capital letters are A, B, and C. Contrast with lowercase.
upstream data. IPDS commands that exist in a logical
path from a specific point in a printer back to, but not
including, host presentation services.
usable area. An area on a physical medium that can be
used to present data. See also viewport.
user printable area (UPA). The portion of the physical
printable area to which user-generated data is restricted.
See also logical page, physical printable area, and valid
printable area.
USS. See Uniform Symbol Specification.
UTC. Coordinated Universal Time, the standard time
reference for Earth and the human race. Knowing the UTC
time and one's time zone offset from it, makes it possible to
calculate the local time; for example, 1:00 PM UTC
corresponds to 5:00 AM Pacific Standard Time (on the
same day). UTC is almost the same thing as Greenwich
Mean Time (GMT), that was originally used as the standard
time reference.
V
valid printable area (VPA). The intersection of a logical
page with the area of the medium presentation space in
which printing is allowed. If the logical page is a secure
overlay, the area in which printing is allowed is the physical
printable area. If the logical page is not a secure overlay
and if a user printable area is defined, the area in which
printing is allowed is the intersection of the physical
printable area with the user printable area. If a user
printable area is not defined, the area in which printing is
allowed is the physical printable area. See also logical
page, physical printable area, secure overlay, and user
printable area.
variable space. A method used to assign a character
increment dimension of varying size to space characters.
The space characters are used to distribute white space
within a text line. The white space is distributed by
expanding or contracting the dimension of the variable
space character's increment dependent upon the amount
of white space to be distributed. See also variable space
character and variable space character increment.
variable space character. The code point assigned by
the data stream for which the character increment varies
according to the semantics and pragmatics of the variable
space function. This code point is not presented, but its
character increment parameter is used to provide spacing.
See also variable space character increment.
UTF-16 • variable space character


variable space character increment. The variable value
associated with a variable space character. The variable
space character increment is used to calculate the
dimension from the current presentation position to a new
presentation position when a variable space character is
found. See also variable space character.
vector graphics. A vector has a defined starting point, a
designated direction, and a specified distance. Vector
graphics is line-based graphics data, where vectors
determine how straight and curved lines are shaped
between specific points. A picture consists of lines and
colors to fill the areas enclosed by the lines.
verifier. In bar code systems, a device that measures the
bars, spaces, quiet zones, and optical characteristics of a
bar code symbol to determine if the symbol meets the
requirements of a bar code symbology, specification, or
standard.
vertical bar code. A bar code pattern that presents the
axis of the symbol in its length dimension parallel to the Y
bc
axis of the bar code presentation space. Synonymous with
ladder bar code.
vertical font size. (1) A characteristic value,
perpendicular to the character baseline, that represents the
size of all graphic characters in a font. Synonymous with
font height. (2) In a font character set, nominal vertical font
size is a font-designer defined value corresponding to the
nominal distance between adjacent baselines when
character rotation is zero degrees and no external leading
is used. This distance represents the baseline-to-baseline
increment that includes the font's maximum baseline extent
and the designer's recommendation for internal leading.
The font designer can also define a minimum and a
maximum vertical font size to represent the limits of
scaling. (3) In font referencing, the specified vertical font
size is the desired size of the font when the characters are
presented. If this size is different from the nominal vertical
font size specified in a font character set, the character
shapes and character metrics might need to be scaled
prior to presentation.
vertical scale factor. In outline-font referencing, the
specified vertical adjustment of the Em square. The vertical
scale factor is specified in 1440ths of an inch. When the
horizontal and vertical scale factors are different,
anamorphic scaling occurs. See also horizontal scale
factor.
viewing transform. A transform that is applied to model-
space coordinates. Contrast with model transform.
viewing window. That part of a model space that is
transformed, clipped, and moved into a graphics
presentation space.
viewport. The portion of a usable area that is mapped to
the graphics presentation space window . See also
graphics model space and graphics presentation space.
visibility. The property of a segment that declares
whether the part of a picture defined by the segment is to
be displayed or not displayed during the drawing process.
void. In bar codes, the undesirable absence of ink in a
bar code symbol bar element.
VPA. See valid printable area.
W
ward. A deprecated term for section.
weight class. A parameter indicating the degree of
boldness of a typeface. A character's stroke thickness
determines its weight class. Examples are light, medium,
and bold. Synonymous with type weight.
white point. One of a number of reference illuminants
used in colorimetry that serve to define the color “white”.
Depending on the application, different definitions of white
are needed to give acceptable results. For example,
photographs taken indoors might be lit by incandescent
lights, that are relatively orange compared to daylight.
Defining “white” as daylight will give unacceptable results
when attempting to color correct a photograph taken with
incandescent lighting.
white space. The portion of a line that is not occupied by
characters when the characters of all the words that can be
placed on a line and the spaces between those words are
assembled or formatted on a line. When a line is justified,
the white space is distributed among the words,
characters, or both on the line in some specified manner.
See also controlled white space.
width class. A parameter indicating a relative change
from the font's normal width-to-height ratio. Examples are
normal, condensed, and expanded. Synonymous with type
width.
window. A predefined part of a graphics presentation
space. See also graphics presentation space window .
writing mode. An identified mode for the setting of text in
a writing system, usually corresponding to a nominal
escapement direction of the graphic characters in that
mode; for example, left-to-right, right-to-left, top-to-bottom.
X
Xbc extent. The size of a bar code presentation space in
the Xbc dimension. See also bar code presentation space.
Xbc,Ybc coordinate system. The bar code presentation
space coordinate system.
X dimension. In bar codes, the nominal dimension of the
narrow bars and spaces in a bar code symbol.
variable space character increment • X dimension


Xg,Yg coordinate system. In the IPDS architecture, the
graphics presentation space coordinate system.
X height. The nominal height above the baseline,
ignoring the ascender, of the lowercase characters in a
font. X height is usually the height of the lowercase letter x.
See also lowercase and ascender.
Xio,Yio coordinate system. The IO-Image presentation
space coordinate system.
XML. See Extensible Markup Language.
XMP . See Extensible Metadata Platform.
Xm,Ym coordinate system. (1) In the IPDS architecture,
the medium presentation space coordinate system. (2) In
MO:DCA, the medium coordinate system.
Xoa,Yoa coordinate system. The object area coordinate
system.
X
ol,Yol coordinate system. The overlay coordinate
system.
X
p extent. The size of a presentation space or logical
page in the Xp dimension. See also presentation space and
logical page.
Xpg,Ypg coordinate system. The coordinate system of a
page presentation space. This coordinate system
describes the size, position, and orientation of a page
presentation space. Orientation of an X pg,Ypg coordinate
system is relative to an environment specified coordinate
system, for example, an Xm,Ym coordinate system.
Xp,Yp coordinate system. The coordinate system of a
presentation space or a logical page. This coordinate
system describes the size, position, and orientation of a
presentation space or a logical page. Orientation of an Xp,
Yp coordinate system is relative to an environment-
specified coordinate system. An example of an
environment-specified coordinate system is the Xm,Ym
coordinate system. The Xp,Yp coordinate system origin is
specified by an IPDS Logical Page Position command. See
also logical page, medium presentation space, and
presentation space.
Y
Ybc extent. The size of a bar code presentation space in
the Ybc dimension. See also bar code presentation space.
YCbCr. A three-component color space that
approximately models how color is interpreted by the
human visual system, with an intensity value and two color
values. YCbCr and YCrCb use the same three values, but
in a different order.
YCCK. CMYK data carried in the luminance-chrominance
form. YCC are computed from CMY , while K is the black
channel carried in the reverse-video form (K = 255 - K).
See Appendix B, “Adobe APP14 JPEG Marker” in
Presentation Object Subsets for AFP.
YCrCb. A three-component color space that
approximately models how color is interpreted by the
human visual system, with an intensity value and two color
values. YCbCr and YCrCb use the same three values, but
in a different order.
Yp extent. The size of a presentation space or logical
page in the Yp dimension. See also presentation space and
logical page.
Yxy color space. A color space belonging to the XYZ
base family that expresses the XYZ values in terms of x
and y chromaticity coordinates, somewhat analogous to
the hue and saturation coordinates of the HSV color space.
Xg,Yg coordinate system • Yxy color space


Index
A
A-space . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 636
accordion-fold in . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 368, 745
accordion-fold out. .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 368, 745
ACK (Positive Acknowledge Reply). . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .71
Acknowledge Reply
acknowledgment required flag. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 124
ARQ flag . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 70–71
data field . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 127
ending a command sequence . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .66
negative Acknowledge Reply . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .71
rules for. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 132
special data area . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 128, 130
acknowledgment required bit (ARQ)
ending a command sequence . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .66
in command format .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .70
rules for. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .71
action codes .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 802, 941
Activate Printer Alarm order .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 270
Activate Resource (AR) command.. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 134
Activate Resource RT & RIDF Support self-defining field . . . .. . . 346
Activate Setup Name (ASN) command. . .. . . .. . .. . . .. . .. . . .. . . .. . . 157
Acknowledge Reply to .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 158
Active Setup Name self-defining field. .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 380
advanced function presentation . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 9
AEA (Alternate Exception Action) . .. . . .. . .. . . .. . .. . . .. . .. . . .. 792–793
defined . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 279
AFO (Apply Finishing Operations) command . . .. . . .. . .. . . .. . . .. . . 160
AFPC. .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . iii, 4
AFPC JPEG Subset object containers . . .. . 91, 100, 104, 120, 220,
226, 362, 426, 444–445, 452–453, 575, 585, 733, 863, 888, 984
AFPC PNG Subset object containers. .. . .. . 91, 101, 104, 120, 220,
226, 363, 426, 444–445, 452–453, 575, 585, 733, 863, 888
AFPC SVG Subset object containers. .. . .. . 91, 101, 104, 120, 220,
226, 363, 426, 444–445, 452–453, 575, 585, 733, 863, 888, 925
AFPC TIFF Subset object containers. .. . .. . 91, 104, 120, 220, 226,
363, 376, 426, 444–445, 452–453, 575, 586, 733, 863, 888
algorithms
ABIC (Bilevel Q-Coder) compression algorithm .. . .. . . .. . . .. .1003
Concatenated ABIC compression algorithm .. . . .. . .. . . .. . . .. .1003
IBM MMR compression algorithm . . .. . .. . . .. . .. . . .. . .. . . .. . . .. .1001
Image Compression algorithms .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. .1001
ITU—TSS T.4 Facsimile Coding Scheme (G3 MH, one-
dimensional).. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. .1003
ITU—TSS T.4 Facsimile Coding Scheme (G3 MR, two
dimensional).. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. .1003
ITU—TSS T.6 Facsimile Coding Scheme (G4 MMR)
compression algorithm . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. .1003
RIDIC Recording Algorithm . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. .1005
RL4 compression algorithm . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. .1002
Unpadded RIDIC Recording Algorithm . . . .. . .. . . .. . .. . . .. . . .. .1005
Alternate Exception Action (AEA) . .. . . .. . .. . . .. . .. . . .. . .. . . .. 792–793
defined . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 279
Alternate Offset Stacker order . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 271
Anacomp object containers . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . .99, 361
anystate. .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .82
Apply Finishing Operations (AFO) command . . .. . . .. . .. . . .. . . .. . . 160
AR (Activate Resource) command.. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 134
architectures
BCOCA. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 4, 18, 561–562
CMOCA . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 4
FOCA. .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 4, 635
GOCA .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . . 4, 18, 543
IOCA. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . . 4, 18, 517
IPDS . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 2
MO:DCA . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 2
MOCA . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 4
PTOCA . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. 4, 18, 472
ARQ (acknowledgment required bit)
ending a command sequence . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .66
in command format . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .70
rules for. . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .71
ASN (Activate Setup Name) command. .. . .. . . .. . . .. . .. . . .. . .. . . .. 157
Acknowledge Reply to . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 158
asynchronous data-stream exception . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 804
Available Features self-defining field . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 341
B
background .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .27
bar code
bar code type/modifier support .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 350
mapping . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 555
object area . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 551, 559
Bar Code Area Position (BCAP) self-defining field . . .. . . .. . .. . . .. 551
bar code commands
Write Bar Code command. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 562
Write Bar Code Control command . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 550
Bar Code Data Descriptor (BCDD) self-defining field . . . .. . .. . . .. 559
bar code exceptions .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 807, 835
bar code object areas . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .18
Bar Code Object Content Architecture
See architectures
Bar Code Output Control (BCOC) self-defining field .. . . .. . .. . . .. 555
Bar Code Type/Modifier self-defining field .. . . .. . . .. . .. . . .. . .. . . .. 350
baseline increment . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .55
baseline offset.. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 640
B
c (current baseline coordinate). . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .55
BCAP (Bar Code Area Position) self-defining field . . .. . . .. . .. . . .. 551
BCDD (Bar Code Data Descriptor) self-defining field . . . .. . .. . . .. 559
BCOC (Bar Code Output Control) self-defining field .. . . .. . .. . . .. 555
BCOCA
See architectures
Begin Overlay (BO) command . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . ..75, 623
Begin Page (BP) command.. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . ..74, 162
Begin Page Segment (BPS) command. .. . .. . . .. . . .. . .. . . .. . ..76, 632
Begin Segment Introducer (BSI) . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 543
B
i (initial baseline coordinate) .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .55
bin identification . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 331
BO (Begin Overlay) command . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . ..75, 623
bold. . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 203
bounded-box fonts . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 678
BP (Begin Page) command.. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . ..74, 162
BPS (Begin Page Segment) command. .. . .. . . .. . . .. . .. . . .. . ..76, 632
BSI (Begin Segment Introducer) . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 543
buffered data
discarding . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 275
printing . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 382
C
c-fold in. .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 368, 745
c-fold out . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 368, 745
calculating current text position . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 61–62
cancel . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 803
captured resources . .. . .92, 135, 145, 152, 302, 659, 674, 680, 724
center-and-trim mapping . . . .. . .. . . .. . .. . . .. . .496, 507, 524, 538, 604
center-fold in . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 368, 745
center-fold out .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 368, 745
character box. .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 636, 678


character increment . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .55
character pattern descriptors . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 684
character raster patterns .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 702
character reference point . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 636
character set ID .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 201
CMR Tag Fidelity (X'96') triplet .. . . .. . . .. . .. . . .. . . 218–219, 366, 777
code page . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .46
code page global ID . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 202
code page state .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .78
coded fonts . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .46
bounded-box fonts . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 678
character box .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 678
character pattern data .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 647
character pattern descriptors . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 647, 684
command summary .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 647
control record .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 647
Deactivate Font command . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 163
font identifier . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 647
font types. .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 678
fully described font . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . .46, 635
inline sequence directions.. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 640
Load Code Page command . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 652
Load Code Page Control command.. . .. . . .. . .. . . .. . .. . . .. . . .. . . 656
Load Font Character Set Control command. .. . . .. . .. . . .. . . .. . . 671
Load Font command . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 662
Load Font Control command.. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 675
Load Font Index command. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 687
long-format font index .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 648
parts of . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 649
resolution. .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 337
short-format font index.. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 648
symbol-set coded font .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . .46, 635
Coded Graphic Character Set Global Identifier (X'01') triplet . . 137,
154, 321–322, 593, 709
color . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .33
IM Images .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 489
IO Images .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 515
Color Fidelity (X'75') triplet . . .. . .. . . .. . . .. . .. . . .. . . 218–219, 366, 734
color management .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .35
Color Management Resource (CMR) object containers . .. . .99, 362
Color Management Resource Descriptor (X'91') triplet . . . 137, 155,
240, 609–610, 769
Color Mapping Table (CMT) .. . .. . 44, 100, 120, 223, 362, 497, 716
Color Simulation Guidelines .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 119
Color Specification (X'4E') triplet . . .. . 206, 209, 221, 225, 230, 235,
466, 469, 504, 512, 532–534, 555, 557–558, 561, 585–586, 600–
602, 609–610, 713
Colorant-Identification self-defining field .. . . .. . .. . . .. . .. . . .. . . .. . . 370
coloring . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .32
command
command code field . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .70
command correlation ID field . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .70
command data field .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .70
command flags field.. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .70
example command sequence . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 993
example overlay sequence. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 997
example page sequence . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 999
example page-segment sequence . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 996
initialization sequence .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 995
length field . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .70
processing . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .66
sequence. .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .66
summary . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .83
command sequences. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 993
command sets
bar code . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . .14, 547
device control .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . .14, 123
graphics . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . .14, 521
IM Image . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . .14, 479
IO Image . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . .14, 493
loaded font . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . ..14, 635
metadata .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . ..14, 615
object container . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . ..14, 563
overlay. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . ..14, 621
page segment . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . ..14, 631
text .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . ..14, 457
command-reject exceptions . . .. . . .. . .. . . .. . .. . . .. . . .. . . 807–808, 944
command-set vectors . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 233
committed copy counter.. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 129
committed page counter . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 129
compression algorithms.. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .1001
Control Edge Marks order . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 272
control sequences
summary .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 476
coordinate systems
graphics . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 522
I,B coordinate system.. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .55
relationships . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .61
used to locate points . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .61
user-printable area (UPA) . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .63
valid printable area (VPA) . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .62
X
bc,Ybc coordinate system. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .60
Xg,Yg coordinate system. .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .60
Xio,Yio coordinate system .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .60
Xm,Ym coordinate system.. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .48
Xoa,Yoa coordinate system. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .60
Xoc,Yoc coordinate system . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .60
Xp,Yp coordinate system. .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .53
Xt,Yt coordinate system. . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .60
copy counters .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 128
copy subgroup . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 176
corner staple . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 368, 746
correlation ID . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 124
field format . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .70
in the ACK or NACK . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 125
counters
committed copy.. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 129
committed page . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 129
copy. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 128
jam recovery copy.. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 130
jam recovery page . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 130
operator-viewing copy . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 130
operator-viewing page . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 130
page . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 128
received page . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 129
stacked copy. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 130
stacked page . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 130
crease . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 378, 743, 748
current baseline coordinate (B
c). . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .55
current inline coordinate (I c) . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .55
cut-sheet emulation mode . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .22
D
data field . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .70
data object . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 577
Data Object Area Position self-defining field . . . .. . .. . . .. . .. . . .. 577
Data Object Data Descriptor self-defining field .. . .. . . .. . .. . . .. 585
Data Object object area . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 577
Data Object Output Control self-defining field. . .. . .. . . .. . .. . . .. 580
Data Object Area Position (DOAP) self-defining field. . . .. . .. . . .. 577
Data Object Data Descriptor (DODD) self-defining field .. . .. . . .. 585
data object error codes. .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 922
Data Object Font Descriptor (X'8B') triplet .. . . ..137, 149, 151, 154,
239, 759
Data Object Output Control (DOOC) self-defining field . .. . .. . . .. 580
data object resource .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. 88, 91, 99
Data Object Resource Equivalence (DORE) command .. . .. . . .. 569
Data Object Resource Equivalence 2 (DORE2) command . . . .. 571


Data Object Resource equivalence entries . .. . .. . . .. . .. . . .. . . .. . . 565
data security .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 326–327
data storage .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 338
data types. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .18
data-check exceptions . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 807, 820, 960
data-object-font component .. . .. . . .. . . .. . .. . . .. . .. . 45–46, 88, 91, 99
data-related print exception. .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 803
data-stream exception . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 802
data-stream exception in a secure overlay. . .. . .. . . .. . .. . . .. . . .. . . 805
DDOFC (Deactivate Data-Object-Font Component)
command . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 573
DDOR (Deactivate Data Object Resource) command . . . .. . . .. . . 574
Deactivate Data Object Resource (DDOR) command . . . .. . . .. . . 574
Deactivate Data-Object-Font Component (DDOFC)
command . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 573
Deactivate Font (DF) command . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 163
Deactivate Overlay (DO) command. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 625
Deactivate Page Segment (DPS) command.. . .. . . .. . .. . . .. . . .. . . 633
Deactivate Saved Page Group order . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 315
defaults
handling . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 121
rules for. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 121
Define Group Boundary order . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 317
Define User Area (DUA) command . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 168
Delete Font (DF) command. .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 163
Delete Home-State Metadata (DHM) command. . . .. . .. . . .. . . .. . . 616
Delete Overlay (DO) command . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 625
Delete Page Segment (DPS) command. .. . . .. . .. . . .. . .. . . .. . . .. . . 633
descriptors, character pattern . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 684
Device Appearance (X'97') triplet . .. . . .. . .. .239, 266–267, 372, 779
device control commands
Activate Resource command . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 134
Activate Setup Name command .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 157
Apply Finishing Operations command .. . . .. . .. . . .. . .. . . .. . . .. . . 160
Begin Page command .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 162
Deactivate Font command . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 163
Define User Area command .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 168
End command . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 170
End Page command . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 171
Execute Order Anystate command. .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 268
Execute Order Home State command .. . . .. . .. . . .. . .. . . .. . . .. . . 313
Include Saved Page command . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 172
Invoke CMR command . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 174
Load Copy Control command. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 176
Load Font Equivalence command. . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 196
Logical Page Descriptor command. .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 204
Logical Page Position command . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 210
Manage IPDS Dialog command .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 216
No Operation command . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 217
Presentation Fidelity Control command . . .. . .. . . .. . .. . . .. . . .. . . 218
Rasterize Presentation Object command .. . .. . . .. . .. . . .. . . .. . . 220
Sense Type and Model command. . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 233
Set Home State command . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 265
Set Presentation Environment command .. . .. . . .. . .. . . .. . . .. . . 266
device information .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 328, 340–341
Device-Appearance self-defining field .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 372
DF (Deactivate Font) command . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 163
DF Deactivation Types Supported self-defining field. .. . . .. . . .. . . 365
DHM (Delete Home-State Metadata) command. . . .. . .. . . .. . . .. . . 616
Discard Buffered Data order .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 275
Discard Unstacked Pages order . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 276
discarding buffered data .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 275
discarding printed, but unstacked pages .. . . .. . .. . . .. . .. . . .. . . .. . . 276
DO (Deactivate Overlay) command. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 625
DOAP (Data Object Area Position) self-defining field.. . . .. . . .. . . 577
DODD (Data Object Data Descriptor) self-defining field . .. . . .. . . 585
don't capture flag. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 152
DOOC (Data Object Output Control) self-defining field . . .. . . .. . . 580
DORE (Data Object Resource Equivalence) command . .. . . .. . . 569
DORE2 (Data Object Resource Equivalence 2) command . .. . . 571
double gate-fold in . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 368, 746
double gate-fold out .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 368, 746
double parallel-fold in. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 368, 746
double parallel-fold out . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 368, 746
double strike. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 203
DPS (Deactivate Page Segment) command. . .. . . .. . .. . . .. . .. . . .. 633
drawing orders . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 522
coordinate system for, . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 522
sequence of, . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 521
drawing units . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 522
DUA (Define User Area) command . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 168
duplex printing. . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 176
E
edge marks
controlling . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 272
edge stitch . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 368, 747
Eject to Front Facing order. .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 325
embedded text-control summary. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 476
emulation mode . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .22
Encapsulated PostScript (EPS) object containers . . .. 91, 100, 104,
119, 220, 226, 362, 376, 426, 444, 452, 575, 585, 733, 863, 888
Encoding Scheme ID (X'50') triplet. . .. 137, 149, 151, 154, 242, 720
End command.. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . 73, 77–81, 170
End Page (EP) command . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 74–76, 171
EP (End Page) command . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 74–76, 171
equipment check with intervention required . . .. . . .. . . 807, 809, 945
equipment-check exceptions . .. . . .. . .. . . .. . .. . . .. . . .. . . 807, 819, 952
Erase Residual Font Data order. . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 326
Erase Residual Print Data order . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 327
erasing data . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 327
Error Codes for Other Data Objects .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 922
exception formats .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 798
exception handling. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 277
Exception IDs
0100..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 216, 356, 918
0101..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 328, 373, 918
0102..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 328, 918
0103..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 328, 918
0104..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 328, 918
0105..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 181, 328, 918
0106..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 328, 918
0108..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 328, 367, 919
0109..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . ..328, 368–369, 378, 768, 919
010A..00 .. . .. . . .. . .. . . .. . . .. . .. . 158–159, 307, 310, 328, 918–919
010D..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 328, 919
0110..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 919
0111..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 132, 791, 919
0113..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 409, 451, 919
0114..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 919
0115..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .734, 799, 918, 920, 922
0120..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 328, 370, 920
0140..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 405–407, 920
0141..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 406, 920
017E..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. 241, 328, 768, 801, 918, 921
0180..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 216, 921
018F ..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 321, 402, 921
01A0..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . ..976, 1014
01A1..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . ..976, 1014
01A2..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . ..976, 1014
01A3..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . ..976, 1014
01E4..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 817, 921
01E8..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 921
0200..01 . .. . .. . . .. . .. . . .. . . .. . .. . 471–472, 474, 477, 755, 799, 857
0200..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 969
0200..03 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 969
0201..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 969
0201..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 969


0201..03. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 64, 457, 857
0202..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 477, 857
0202..02. . .. . . . 71, 136, 158, 160, 162–163, 168, 170–171, 173–
174, 178, 198, 204–205, 209, 213, 216–218, 220, 233, 265–
266, 270–272, 275–277, 289–290, 293–294, 307, 315, 317,
325–328, 381–383, 385–386, 388–389, 397, 401, 408–409,
460, 462, 472, 481, 491, 498, 517, 526, 543, 550, 562, 569,
571, 573–575, 590, 592, 595, 613, 616–617, 619, 623–626,
628, 630, 632–634, 652, 656, 659–660, 663, 671, 674–675,
688, 699, 808, 857–858
0202..05. . .. . . .463, 467, 470, 500, 504, 514, 528, 532, 541, 552,
556, 561, 578, 581, 585, 597, 600, 609, 618, 858
0203..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 969
0203..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 71, 808, 858
0203..05. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . 464, 501, 529, 553, 597, 858
0204..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 477, 858
0204..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . .71, 858
0204..03. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 969
0204..05. . .. . . .. . .. . . .. . .. . . .. . .. 465, 502, 530, 554, 579, 598, 858
0205..01. . .. . . .. . .. . . .. . .. . . .. . .. 462, 472, 498, 526, 595, 617, 858
0205..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 45, 859, 868
0205..05. . .. . . .467, 470, 504, 514, 532, 541, 556, 561, 581, 600,
859
0206..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 477, 859
0206..05. . .. . . .467, 471, 504, 515, 532, 541, 556, 561, 581, 600,
859
0207..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 969
0207..05. . .. . . 467, 471, 505, 515, 532–533, 542, 556, 561, 581–
582, 600–601, 859
0208..05. . .. . . .. . .. . . .. . .. . 468, 505, 533, 557, 582, 601, 607, 860
0209..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 969
0209..05. . .. . . .. . .. . . .. . .. . 468–469, 512, 533, 557, 583, 601, 860
020A..01 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 969
020A..05 . .. 62–64, 279, 798, 800–801, 822, 843–844, 857, 860
020A..06 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 64, 459, 468, 861
020B..05 . .. . . .. . .. . . 462, 498, 526, 550, 576, 595, 617–618, 861
020C..01 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 208, 477, 861
020C..05 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 578, 861
020D..01 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . 595, 613, 763, 799, 861, 922
020D..02 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . . 403, 564, 609, 861
020D..05 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 64, 595, 613, 799, 862, 922
020D..06 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 64, 606, 862
020D..07 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . . 232, 588, 612, 862
020D..0F . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 570, 862
020D..10 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . . 227, 568, 788, 862
020D..11. . .. . . .. . .. . . 175, 514, 569, 571, 573–574, 585, 610, 863
020D..12 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 569, 571, 863
020D..13 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 585, 863
020D..14 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 573–574, 863
020D..15 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 585, 863
020D..16 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 514, 610, 864
020D..17 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 566, 568, 864
020D..18 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 163, 573, 864
020D..19 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 571, 864
020D..20 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 175, 772, 864
020D..30 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 591, 864
020D..31 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 590, 864
020D..32 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 591, 864
020E..01 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 713, 731, 865
020E..02 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .208, 477, 546, 714, 865
020E..03 . .. . . .. . .. . . .. . .. . . .. . .. 208, 477, 546, 716, 718, 734, 865
020E..04 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . 208, 477, 546, 715, 734, 866
020E..05 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . .208, 477, 546, 717–718, 866
020F ..01 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 477, 866
0210..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 208, 477, 867
0211..01 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 208, 477, 867
0211..02 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 969
0212..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 208, 477, 867
0212..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .867, 876, 911
0213..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 477, 867
0213..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 969
0214..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 477, 867
0214..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 163, 166, 868
0214..03 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 477, 859, 868
0215..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 477, 821, 868
0215..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 167, 868
0216..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 477, 821, 868
0216..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 970
0217..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 477, 868
0217..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 165, 869
0218..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 970
0218..02 . .. . .. . . .. . .. . . .. . . 200, 208, 477, 678, 690, 702, 835, 869
0219..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 477, 869
0219..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 200, 869
021A..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 477, 869
021A..02 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 970
021A..03 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 477, 799, 870, 984
021B..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 477, 870
021B..02 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 679, 870
021C..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 477, 870
021C..02 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 680, 870
021D..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 970
021D..02 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 202, 871
021E..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 477, 871
021E..02 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 293, 871
021F ..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 477, 871
021F ..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 197, 200, 871
0220..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 684, 871
0220..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 683, 871
0220..03 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 969–970
0221..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 678, 871
0222..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 678, 872
0223..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 970
0223..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 678, 872
0224..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 970
0224..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 970
0225..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 970
0225..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 970
0226..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 970
0226..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 678, 685, 701, 872
0227..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 970
0227..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 679, 685, 701, 872
0228..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 970
0228..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 700, 872
0229..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 701, 872
022A..02 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 679, 872
022B..02 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 680, 873
022C..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 970
022C..02 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 970
022D..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 971
022D..02 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 680, 873
022E..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 971
022E..02 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 662, 673, 873
022F ..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 969, 971
0230..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 969, 971
0230..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 971
0231..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 179, 873
0231..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 971
0232..01 . .. . .. . . .. . .. . . .. . . .. . .. . 179, 184, 188, 190–191, 873–874
0232..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 662, 673, 873
0233..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 873, 877
0234..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 179, 873
0235..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 971
0235..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 971
0236..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 182, 874
0236..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 971
0237..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 185, 874
0237..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 971
0237..03 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 180–181, 874
0237..04 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 181, 798–799, 874


0237..05. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 177, 874
0238..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 190, 874–875
0238..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 971
0238..03. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 190, 875
0238..04. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 190–191, 875
0238..10. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 189, 875
0238..11 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 189, 875
0239..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 188, 874–875
0239..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 678, 876
023A..01 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 971
023A..02 . .. . . .. . .. . . 136, 200, 656, 671, 675, 698, 867, 876, 911
023B..01 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 876
023B..02 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 696, 876
023C..02 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . .687, 691–694, 696, 873, 876
023D..02 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 971
023E..02 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 686, 877
023F ..02 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . . 208, 477, 877, 879
0240..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 167, 690, 877
0241..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 971
0242..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 483, 877
0242..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 971
0243..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 483, 877
0243..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 678, 690, 878
0244..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 483, 878
0244..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 675, 688, 878
0245..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 483, 878
0246..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 484, 878
0246..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 691, 878
0246..03. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . . 680, 683–684, 878
0247..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 485, 879
0247..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 201, 877, 879
0248..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 486, 879
0248..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 701, 879
0249..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 486, 879
0249..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 700, 879
024A..01 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 487–488, 879
024A..02 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 702, 879
024B..01 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 971
024B..02 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 700–701, 879
024C..01 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 972
024C..02 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 702, 880
024C..03 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 969, 972
024D..01 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 972
024D..02 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 880, 911
024E..01 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 972
024F ..01 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 972
024F ..02 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 972
0250..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 972
0251..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 972
0252..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 972
0252..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 972
0253..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 489, 734, 880
0253..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 972
0254..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 969, 972
0254..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 735, 880
0254..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 735, 880
0254..03. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 736, 880
0254..04. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 736, 880
0254..05. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 219, 881
0254..10. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 969, 972
0254..31. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 732, 881
0254..32. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 733, 881
0254..33. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 732, 881
0254..41. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 757, 881
0254..42. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 758, 881
0254..43. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 758, 881
0254..51. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 755, 881
0254..52. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 755, 882
0254..53. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 756, 882
0254..71. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 777, 882
0254..72 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 777, 882
0254..73 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 778, 882
0255..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 321, 403, 882
0255..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 173, 882
0255..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 173, 883
0255..03 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 173, 883
0255..04 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 172, 883
0255..05 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 172, 404, 883
0255..06 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 172, 883
0255..07 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 316, 883
0255..08 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 316, 883
0255..09 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 162, 883
0255..0A .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 383, 884
0255..0B .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 172, 884
0256..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 710, 884
0256..11 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 766, 884
0256..12 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 765, 884
0256..13 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 766, 884
0256..14 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 766, 884
0256..21 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 712, 885
0256..22 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 712, 885
0256..23 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 712, 885
0256..24 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 712, 885
0256..31 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 720, 885
0256..51 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 770, 885
0256..61 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 772, 885
0256..71 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 776, 886
0256..81 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 734, 780, 886
0256..91 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 782, 886
0256..92 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 782, 886
0256..A1 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 722, 886
0256..A2 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 722, 886
0256..B1 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 784, 886
0256..B2 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 785, 887
0256..B3 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 785, 887
0256..B4 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 785, 887
0256..C1 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 787, 887
0256..C2 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 788, 887
0256..C3 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 788, 887
0256..C4 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 788, 887
0256..C5 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 788, 888
0256..C6 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 788, 888
0257..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 226, 888
0257..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 226, 888
0257..03 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 226, 888
0257..04 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 226, 888
0257..05 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 228, 889
0257..06 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 229, 889
0257..07 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 229, 889
0257..08 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 229, 889
0257..09 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 230, 889
0258..03 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 208, 477, 734, 889
0259..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 969, 973
0259..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 969, 973
025A..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 969, 973
025A..02 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 969, 973
025B..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 216, 890
025C..02 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 168, 890
025D..04 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 448, 734, 777, 890
025D..ee .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 613, 734, 799, 890
025E..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . 35, 734, 890
025E..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 156, 610, 734, 890
025E..02 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . 41, 734, 769, 890
025E..03 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . 39, 734, 891
025E..04 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . 39, 734, 891
025E..05 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 240–241, 734, 891
025F ..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 411, 891
025F ..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 413, 891
0260..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 207, 477, 892
0261..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 207, 477, 892


0262..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . . 207, 399, 892, 894
0263..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 892, 911
0263..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . . 207, 399, 892, 894
0264..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .491, 892, 911
0264..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 207, 477, 892
0266..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 969, 973
0267..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 973
0268..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 208, 477, 893
0269..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 208, 477, 893
026A..01 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 491, 893
026A..02 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 208, 477, 893
026B..01 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 491, 893
026B..02 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 208, 477, 893
026E..01 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 386, 893
026F ..02 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 395, 893
0270..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 398, 893
0271..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 973
0272..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 973
0272..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 399, 892, 894
0273..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 973
0273..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 399, 892, 894
0274..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 973
0274..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 398, 894
0275..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 973
0277..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 321, 894
0278..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 321, 894
0278..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 973
0279..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 973
027A..01 . .. . . . 160, 173, 267, 322, 705, 709, 711, 720, 722, 727,
744, 761, 765–766, 768, 770, 772, 775, 779, 781, 784, 786–
787, 894
027B..01 . .. . . . 267, 705, 709, 711, 720, 722, 727, 744, 761, 765,
768, 770, 772, 775, 779, 781, 784, 786–787, 894
027C..01 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .323, 742, 757, 767, 895
027C..02 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . . 323, 757, 768, 896
027C..03 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 744, 757, 896
027C..04 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 753, 757, 896
027C..05 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 754, 757, 897
027C..06 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 754, 757, 897
027C..07 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . . 753–754, 757, 897
027C..08 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 754, 757, 897
027C..09 . .. . . .. . .. . . .. . .. . . .. . .. 181, 742, 748, 757, 767–768, 898
027C..0A . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . . 748, 757, 768, 898
027C..0B . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 748, 768, 898
027C..0C . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 768, 898
027C..0D . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 748, 757, 898
027E..00 . .. . . . 241, 586, 607, 609, 757, 768, 798, 801, 857, 899
0280..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 477, 900
0281..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 900, 911
0281..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 974
0282..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 477, 900
0283..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 974
0284..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 974
0285..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 625–626, 900
0285..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 974
0287..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 681, 900
0288..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 681, 900
0289..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 681, 900
028A..01 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 633, 900
028A..02 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 681, 901
028F ..01 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 136, 901
028F ..02 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . . 134, 136, 153, 901
028F ..03 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . . 725, 737, 740, 901
028F ..04 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 741, 901
028F ..10 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 738, 901
028F ..11 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 738–739, 902
028F ..20 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 156, 760, 902
028F ..21 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 761, 902
028F ..22 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 761, 902
028F ..23 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 761, 902
028F ..24 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 762, 902
028F ..25 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 763, 902
028F ..26 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 763, 902
028F ..30 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 149, 903
028F ..31 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 151, 765, 903
028F ..50 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 150, 279, 799, 903
0290..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .190, 624, 629, 805, 903
0290..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 974
0290..03 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 969, 974
0291..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 624, 903
0291..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 295–297, 299, 903
0291..03 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 969, 974
0292..01 . .. . .. . . .. . .. . . .. . . .. . .. . 189–190, 625–626, 629, 805, 904
0292..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 293, 904
0293..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 627, 904
0293..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 627, 904
0293..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 630, 904
0293..03 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 630, 904
0293..04 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 630, 905
0294..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 632, 634, 905
0295..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 632, 905
0295..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 381, 905
0296..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 633–634, 905
0297..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 262, 627, 905
0298..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 188, 477, 905
0298..03 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 477, 906
0299..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 274, 906
029A..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 477, 906
029B..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 474, 478, 906
029C..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 474, 478, 612, 906
029C..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 478, 907
029C..02 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 478, 799, 907
029C..03 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 478, 907
029C..05 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 474, 755, 907
029C..06 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 478, 907
029C..08 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 478, 908
029C..09 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 474, 478, 908
029C..0A .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 478, 908
029C..0B .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 478, 908
029D..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 478, 755, 908
029D..02 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 478, 909
029D..03 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 478, 909
02A0..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 974
02A1..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 158, 909
02A1..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 909, 974
02A2..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 307, 909
02A2..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 974
02A4..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 214, 909
02A4..02 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 169, 909
02A5..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 214, 910
02A5..02 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 169, 910
02A8..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 974
02A9..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 974
02AB..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 910–911
02AC..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 910–911
02AD..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 214, 910
02AD..02 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 214, 910
02AD..03 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 215, 910
02AE..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 629–630, 911
02AF ..01. .. . .. . 403, 406, 491, 867, 876, 880, 892, 900, 910–911
02B0..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 657, 911
02B0..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 657, 911
02B0..02 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 657, 911
02B0..03 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 659, 911
02B0..04 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 652, 657, 912
02B0..05 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 657, 912
02B0..07 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 654, 912
02B0..0A .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 672, 912
02B0..0B .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 672, 912
02B0..0C .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 672, 912


02B0..0D . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 674, 912
02B0..0E . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 673, 913
02B0..0F . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 673–674, 913
02B1..01 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 664, 913
02B1..02 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 665, 913
02B1..03 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 665, 913
02B1..04 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 663, 913
02B1..08 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 666, 913
02B1..09 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 667, 913
02B1..0A . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 667, 914
02B1..0B . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 670, 914
02B2..01 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 673, 914
02B2..02 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 673, 914
02B2..03 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 673, 914
02B2..04 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 673, 914
02B3..01 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . 341, 403, 673, 684, 761, 914
02C0..01 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 177, 182, 915
02C0..02 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 177, 184, 915
02C0..03 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 184, 915
02C0..04 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 184, 243, 915
02C0..05 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 184, 915
02C1..01 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 182, 915
02C1..02 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 460, 915
02C2..01 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 182, 915
02C2..02 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 179, 181, 915
02C3..01 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 177, 182, 916
02C4..01 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 177, 179, 916
02C5..01 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 163, 916
02C5..02 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 177, 916
02C6..01 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 163, 916
02C6..02 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 460, 916
02C8..01 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 179, 385, 916
02C8..02 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 460–461, 916
02D0..00 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 616, 916
02D0..01 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 618, 917
02EA..01 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 969, 974
02EB..00 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 969, 974
02EC..01 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 969, 974
02EC..02 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 969, 975
02FF ..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 790, 793, 917
0300..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 546, 846
0300..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 542, 546, 846
0300..03. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 546, 846
0300..04. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 546, 734, 847
0300..05. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 546, 967
0300..06. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 546, 967
0300..08. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 546, 847
0300..0B . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 546, 967
0300..0C . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 546, 847
0300..0D . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 64, 546, 847
0300..0E . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 546, 734, 847
0300..21. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 546, 734, 848
0304..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 546, 848
0307..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 546, 967
0324..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 546, 967
0327..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 546, 967
0327..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 546, 967
0327..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 546, 967
0331..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 546, 967
0331..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 546, 967
0332..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 546, 967
0332..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 546, 968
0334..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 546, 848
0335..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 546, 968
033E..00 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 546, 848
035E..00 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 546, 848
0360..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 546, 848
0360..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 546, 968
0368..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 546, 848
0368..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 546, 848
0368..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 849
0368..03 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 849
0368..04 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 849
0368..05 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 849
0368..06 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 849
0370..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 849
0370..81 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 968
0370..82 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 849
0370..83 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 968
0370..84 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 968
0370..C1 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 849
0370..C4 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 968
0370..C5 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 849
0392..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 850
0392..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 850
0393..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 850
0393..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 850
03C0..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 850
03C0..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 850
03C2..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 850
03C2..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 850
03C2..02 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 850
03C3..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 851
03C3..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 279, 546, 851
03C3..02 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 851
03C3..03 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 546, 799, 851
03C6..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 968
03C6..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 852
03C6..02 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 968
03D1..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 852
03D1..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 852
03D1..02 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 852
03D1..03 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 852
03D1..04 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 852
03DC..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 852
03DC..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 853
03DC..02 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 853
03DC..03 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 853
03DC..04 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 853
03DC..05 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 853
03DC..06 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 853
03DC..07 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 853
03DD..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 853
03DD..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 854
03DD..02 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 854
03DD..03 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 854
03DD..04 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 854
03DD..05 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 854
03DD..06 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 854
03DD..07 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 854
03DE..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 854
03DE..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 855
03DE..02 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 855
03DE..03 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 855
03DE..04 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 855
03DE..05 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 855
03DE..06 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 855
03DE..07 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 855
03DF ..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 856
03DF ..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 856
03DF ..02 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 856
03E1..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . 64, 546, 856
03E3..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . 64, 546, 856
03E3..02 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 856
03E3..03 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 546, 856
0401..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 966
0401..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . ..966, 1014
0401..02 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . ..966, 1014
0402..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 966
0403..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 561, 835


0404..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 561, 835, 869
0405..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 561, 734, 835
0406..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 561, 835
0406..10. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 561, 835
0406..11 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 561, 799, 836
0407..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 561, 836
0408..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 561, 836
0408..05. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 561, 836
0409..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 561, 836
040A..00 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 836
040B..00 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 561, 836
040C..00 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 837
040D..00 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 966
040E..00 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 561, 837
040F ..00 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 837
040F ..01 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 837
040F ..02 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 837
040F ..03 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 837
040F ..04 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 837
040F ..05 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 838
040F ..06 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 838
040F ..07 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 838
040F ..08 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 838
040F ..09 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 838
040F ..0A. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 838
040F ..0B. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 838
040F ..0C . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 839
040F ..0D . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 839
040F ..0E. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 839
040F ..0F. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 839
040F ..10 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 839
040F ..11 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 839
040F ..12 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 839
040F ..13 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 840
040F ..14 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 840
040F ..15 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 840
040F ..16 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 840
040F ..17 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 840
040F ..18 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 840
040F ..19 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 840
040F ..1A. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 840
040F ..1B. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 841
040F ..1C . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 841
040F ..1D . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 841
040F ..1E. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 841
040F ..20 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 841
040F ..30 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 841
040F ..31 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 841
040F ..32 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 841
040F ..33 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 842
040F ..34 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 842
040F ..35 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 842
040F ..36 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 842
040F ..37 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 842
040F ..38 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 842
040F ..39 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 842
040F ..3A. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 843
040F ..3B. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 843
0410..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 843
0411..00 . . ..62–64, 279, 549, 562, 798, 800–801, 822, 835, 843,
860–861
0412..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 562, 799, 844
0412..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 844
0412..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 844
0412..03. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 844
0412..04. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 844
0412..05. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 562, 845
0500..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 519, 799, 826
0500..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 965
0500..03. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . . 515, 519, 799, 826
0500..04 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 519, 799, 826
0570..0F. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 519, 826
0571..0F. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 519, 827
058C..0F .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 519, 827
058D..0F .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 519, 827
058E..0F .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 519, 827
058F ..0F. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 519, 827
0591..0F. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 519, 827
0592..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 519, 827
0592..0F. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 519, 827
0593..0F. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 519, 827
0594..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 519, 828
0594..0F. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 519, 828
0594..10 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 519, 828
0594..11 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 519, 828
0595..0F. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 519, 828
0595..10 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 519, 828
0595..11 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 519, 828
0596..0F. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 519, 829
0596..10 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 519, 829
0596..11 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 518–519, 829
0597..0F. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 519, 829
0597..10 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 519, 829
0598..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 519, 829
0598..0F. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 519, 829
0598..10 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 519, 830
0598..14 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 519, 830
0598..15 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 519, 830
059B..0F .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 520, 830
059B..10 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 520, 830
059B..18 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 520, 830
059C..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 520, 830
059C..0F .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 520, 831
059C..17 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 520, 831
059F ..01 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 520, 831
059F ..0F. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 520, 831
059F ..10 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 520, 831
059F ..11 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 520, 831
05A9..02 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . 64, 520, 831
05B3..0F .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 520, 831
05B3..10 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 520, 832
05B3..11. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 520, 832
05B5..0F .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 520, 832
05B5..10 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . 64, 520, 832
05B5..11. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 520, 832
05B6..0F .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 520, 832
05B6..10 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 520, 832
05B6..11. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 520, 833
05B7..0F .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 520, 833
05B7..10 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 520, 734, 833
05B7..11. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 520, 833
05B8..0F .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 520, 833
05B8..11. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 520, 833
05BB..0F .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 520, 833
05BB..10 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 520, 834
05BB..11 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 520, 834
05CE..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 520, 834
05CE..0F .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 520, 834
05CE..10 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 520, 834
05F4..10. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 515, 520, 734, 834
0601..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 619, 824
0602..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 619, 824
0602..10 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 619, 824
0602..20 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 619, 824
0602..30 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 619, 824
0602..40 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 619, 824
0602..50 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 619, 824
0603..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 619, 824
0821..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .151, 279, 765, 799, 820
0824..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 960


0825..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 960
0826..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 960
0827..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 960
0829..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 279, 799, 821
082A..00 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 960
082B..00 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 960
082C..00 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 960
082D..00 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 960
082E..00 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 960
082F ..00 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 960
0830..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 960
0831..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 961
0834..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 961
0835..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 961
0836..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 961
0837..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 961
0838..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 961
0839..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 961
083A..00 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 961
083B..00 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 961
083C..00 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 961
083D..00 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 961
083E..00 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 961
083F ..00 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 962
0842..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 962
0843..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 962
0846..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 962
0847..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 962
084C..00 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 962
084D..00 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 962
084E..00 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 962
084F ..00 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 962
0850..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 962
0851..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 962
0852..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 962
0853..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 962
0854..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 963
0855..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 963
0856..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 963
0857..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 963
0858..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 963
0859..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 963
085A..00 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 963
085B..00 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 963
085C..00 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 963
0860..00. . .. . . . 501, 528–529, 552–553, 578, 597, 821, 859, 868
0864..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 963
0865..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 963
0866..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 963
0867..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 963
0868..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 963
08C1..00 . . 62–64, 204, 207, 279, 398, 798, 800–801, 820–822,
857, 860–861
08C2..00 . .. . . .. . .. . . .. . .. . . .. 62–63, 172, 798, 800–801, 820, 822
08C3..00 . .. . . .. . .. . . .. . .. . . .. 62–63, 403, 798, 800–801, 820, 822
1011..nn . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 952
1014..nn. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 952
1016..nn. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 952
1017..nn. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 952
1018..nn. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 952
1021..nn. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 952
1022..nn. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 952
1023..nn. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 952
1024..nn. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 952
1026..nn. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 952
1027..nn. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 952
102A..nn . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 952
102B..nn . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 952
104B..nn . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 953
1051..nn. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 953
1052..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 953
1062..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 953
1064..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 953
1065..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 953
1066..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 953
1067..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 953
1068..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 953
1069..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 953
106A..nn .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 953
106C..nn .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 953
106D..nn .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 953
1070..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 953
1071..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 953
1072..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 953
1073..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 953
1074..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 953
1075..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 954
1076..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 954
1077..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 954
1078..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 954
1079..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 954
107A..nn .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 954
107C..nn .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 954
107D..nn .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 954
107E..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 241, 768, 801, 819
107E..nn .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 954
107F ..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 954
1080..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 954
1081..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 954
1082..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 954
1083..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 954
1084..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 954
1085..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 955
1086..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 955
1087..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 955
1088..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 955
1089..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 955
108B..nn .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 955
108D..nn .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 955
108E..nn .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 955
108F ..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 955
1090..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 955
1091..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 955
1092..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 955
1093..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 955
1094..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 955
1095..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 955
1096..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 955
1097..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 955
1098..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 956
10A0..nn .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 956
10A2..nn .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 956
10A3..nn .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 956
10A4..nn .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 956
10A5..nn .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 956
10A6..nn .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 956
10A7..nn .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 956
10A8..nn .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 956
10A9..nn .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 956
10AB..nn .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 956
10AC..nn .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 956
10AD..nn .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 956
10AE..nn .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 956
10AF ..nn. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 956
10B1..nn .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 956
10B2..nn .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 957
10B3..nn .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 957
10B4..nn .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 957
10B5..nn .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 957
10B6..nn .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 957


10B7..nn . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 957
10B8..nn . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 957
10B9..nn . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 957
10BA..nn . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 957
10BB..nn . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 957
10BC..nn . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 957
10BD..nn . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 957
10BF ..nn. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 957
10D0..nn . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 957
10D1..nn . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 957
10D2..nn . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 958
10D3..nn . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 958
10D4..nn . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 958
10D8..nn . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 958
10D9..nn . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 958
10DA..nn . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 958
10DB..nn . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 958
10DD..nn . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 958
10DF ..nn . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 958
10E0..00 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 952, 958
10E1..00 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 952, 958
10E2..01 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 952, 958, 1014
10E2..02 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 952, 958, 1014
10F1..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 819, 952, 958
10F2..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 819, 952, 958
10F3..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 819
10F4..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 819
10F5..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 819, 952, 959
10FA..00 . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 952, 959
10FF ..nn. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 959
2001..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .951, 1014
2001..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .951, 1014
2002..01. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .951, 1014
2002..02. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .951, 1014
2011..00 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 951
2012..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 951
4000..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .406, 811, 946
4000..nn. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 946
4001..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 811, 946
4001..nn. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 946
4002..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 811, 946
4002..nn. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 946
4003..nn. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 946
4004..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 811, 946
4004..nn. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 946
4005..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 811, 946
4005..nn. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 946
4006..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 812
4006..nn. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 946
4007..nn. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 946
4008..nn. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 946
4009..nn. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 946
400A..nn . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 947
400B..nn . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 947
400C..nn . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 947
400D..nn . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 947
400E..nn . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 947
400F ..nn . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 947
4010..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 812
4011..00 . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 812, 946–947
4012..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 812, 946–947
4013..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 812
4014..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 812
4015..nn. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 947
4016..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 812
4017..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 812
401C..nn . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 947
401E..nn . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 947
4020..00. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 813
4025..nn. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 947
4028..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 947
402E..nn .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 947
4030..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 947
4031..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 813, 946, 948
4031..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 948
4032..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 948
4033..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 813, 946, 948
4033..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 948
4034..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 948
4035..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 813
403B..nn .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 948
4040..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 405–407, 813
4040..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 948
4041..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 948
4042..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 948
4043..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 948
4050..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 814, 946, 948
4051..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 814, 946, 948
4052..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 814, 946, 948
4053..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 814, 946, 948
4054..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 814, 946, 948
4063..nn . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 948
407C..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 748, 814, 946, 949
407C..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 406, 748, 815
407C..02 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 323, 768, 815
407C..03 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 748, 815, 946, 949
407D..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 815–816
407D..01 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 816
407E..00 .. . .. . . .. . .. 241, 607, 768, 801, 811, 816, 900, 946, 949
40C0..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 816
40E0..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 817
40E1..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 817, 946, 949
40E2..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 817, 946, 949
40E3..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 817, 946, 949
40E4..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 817, 921
40E5..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 406, 816–817
40E6..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 818, 946, 949
40E7..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 818, 946, 949
40E8..nn .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 179, 385, 818
40E9..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 818, 946, 949
40F0..00. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 949
40F1..00. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 949
40F2..00. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 949
40F3..00. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 949
40F4..00. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 949
40F5..00. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 950
40F6..00. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 950
40F7..00. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 950
40F8..00. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 950
40F9..00. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 950
40FE..nn .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 950
5010..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 809, 945
507E..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 241, 768, 801, 809
50F2..00. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 809
50F5..00. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 809
50F6..00. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 809
50F7..00. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 810
50F8..nn. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 179, 385, 810
50F9..00. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 810
50FA..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 810
8001..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . ..71, 808
8002..00 . .. . .. . . .. . .. . . .. . . .. . .. . . 72, 170, 564, 617, 623, 632, 808
8003..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 944
8004..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . ..71, 808
8005..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 944
8006..00 . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . ..944, 1014
80E0..00 .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . 71, 808, 858
exception reporting codes . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 789, 806
Exception-Handling Control order. . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 277
exception reporting. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 278


Exception-Handling Control Flowchart. . . . .. . .. . . .. . .. . . .. . . .. . . 285
Page Continuation Actions . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 283
Skip and Continue Actions . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 281
exceptions
action codes . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 802, 941
alternate exception actions. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 279
command-reject exceptions. .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 808, 944
conditions requiring host notification . . .. . . .. . .. . . .. . .. . . .. 918, 976
data-check exceptions.. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 820, 960
equipment check with intervention required. .. . . .. . .. . . .. 809, 945
equipment-check exceptions . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 819, 952
Error Codes for Other Data Objects.. . .. . . .. . .. . . .. . .. . . .. . . .. . . 922
Exception-Handling Control. .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 117, 277
handling of output . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 115
intervention-required exceptions . . . .. . .. . . .. . .. . . .. . .. . . .. 811, 946
levels . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 115
page and copy counter adjustments . . .. . . .. . .. . . .. . .. . . .. . . .. . . 926
Page Continuation Actions . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 283
position-check exception . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 821
position-check highlight . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 279
presentation processing . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 281
reporting. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 278
reporting codes . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 789, 806
sense byte information . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 796
Skip and Continue Actions . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 281
specification-check exceptions
bar code . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 835, 966
general. .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 857, 969
graphics . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 846, 967
IO-Image . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 826, 965
metadata . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 824
Execute Order Anystate (XOA) command
Activate Printer Alarm order. .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 270
Alternate Offset Stacker order . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 271
Control Edge Marks order .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 272
Discard Buffered Data order .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 275
Discard Unstacked Pages order.. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 276
Exception-Handling Control order . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 277
Mark Form order . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 289
Obtain Additional Exception Information order. . .. . .. . . .. . . .. . . 290
Print-Quality Control order . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 293
Request Resource List order . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 294
Request Setup Name List order .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 307
Execute Order Home State (XOH) command
Deactivate Saved Page Group order . .. . . .. . .. . . .. . .. . . .. . . .. . . 315
Define Group Boundary order . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 317
Eject to Front Facing order . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 325
Erase Residual Font Data order .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 326
Erase Residual Print Data order .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 327
Obtain Printer Characteristics order.. . .. . . .. . .. . . .. . .. . . .. . . .. . . 328
Page Counters Control order . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 381
Print Buffered Data order. .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 382
Remove Saved Page Group order . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 383
Select Input Media Source order . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 385
Select Medium Modifications order .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 386
Separate Continuous Forms order . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 388
Set Media Origin order. . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 389
Set Media Size order . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 397
Specify Group Operation order . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 401
Stack Received Pages order.. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 408
Trace order. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 409
F
Finishing Fidelity (X'88') triplet. .. . . .. . . .. . .. . . .. . . 218–219, 366, 757
Finishing Operation (X'85') triplet . .. . . .. . .. . . .. . . 160, 321–323, 742
finishing operations . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 322
Finishing Operations self-defining field . . .. . . .. . .. . . .. . .. . . .. . . .. . . 368
Finishing Options self-defining field. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 378
fixed medium information . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .45
fixed metrics . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 264, 679, 681
flag byte in the ACK or NACK .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 125
FOCA
See architectures
fold in .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 368, 746
fold out . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 368, 746
font capture .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 135, 137, 152, 724
font character set .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .46
font equivalence record .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 196
font identifier. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 702
font identifiers .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 196
font inline sequence .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 152, 201
0 degrees . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 641
180 degrees . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 643
270 degrees . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 644
90 degrees . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 642
Load Font Index command. .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 687, 690
font metric technology . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 264, 679, 681
Font Object Content Architecture
See architectures
Font Resolution and Metric T echnology (X'84') triplet. . . .. 137, 154,
239, 740
font state . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .77
fonts . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .46
A-space . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 636
baseline offset . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 640
bounded-box fonts . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 678
character box . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 636
character reference point.. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 636
coded .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .46
coded fonts .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 635
coded-font command summary.. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 647
coded-font resolution .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 337
Deactivate Font command . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 163
equivalences. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 196
erasing residual data. .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 326
Font Typeface Global ID. .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 202
fully described font . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . ..46, 635
identifiers.. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 651
IDs .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 200
inline sequence.. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 636
inline sequence directions. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 640
kerning . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 645
Load Code Page command .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 652
Load Code Page Control command. . .. . .. . . .. . . .. . .. . . .. . .. . . .. 656
Load Font Character Set Control command. . . . .. . .. . . .. . .. . . .. 671
Load Font command . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 662
Load Font Control command. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 675
Load Font Index command. .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 687
Load Symbol Set command.. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 698
maximum baseline extent . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 692
monospaced . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 335
numbers . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 651
parameter relationships . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 640
parts of an LF1-type coded font . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 649
proportional .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 335
resident symbol-set support.. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 343
symbol set . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 635
symbol-set coded font . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . ..46, 635
Symbol-Set Support self-defining field. . .. . . .. . . .. . .. . . .. . .. . . .. 335
underscore . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 645–646
underscore position . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 693
underscore width . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 693
width . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 202
foreground . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .27
fully described font. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . ..46, 635
Fully Qualified Name (X'02') triplet . . . 137, 149, 151, 154, 240, 593,
609–610, 711


G
GAP (Graphics Area Position) self-defining field . . .. . .. . . .. . . .. . . 527
GCSGID .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 201
GDD (Graphics Data Descriptor) self-defining field . . .. . . .. . . .. . . 540
general exceptions . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 807, 857
generic CMR . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .39
GOC (Graphics Output Control) self-defining field .. . .. . . .. . . .. . . 531
GOCA
See architectures
GPGID . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 202
graphics. .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 119
begin segment introducer .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 543
center-and-trim mapping . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 524, 538
coordinate system. . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 522
defined . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 521
Graphics Area Position self-defining field .. . .. . . .. . .. . . .. . . .. . . 527
Graphics Data Descriptor self-defining field. .. . . .. . .. . . .. . . .. . . 540
graphics object area . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 18, 523, 527
Graphics Output Control self-defining field . . .. . . .. . .. . . .. . . .. . . 531
graphics presentation space window . .. . . .. . .. . . .. . .. . . .. . . .. . . 540
position-and-trim mapping . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 524, 539
positioning . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 524
presentation space. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 522
presentation space window . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 523
scale-to-fill mapping . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 524, 537
scale-to-fit mapping .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 524, 535
Write Graphics command .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 543
Write Graphics Control (WGC) command .. . .. . . .. . .. . . .. . . .. . . 526
Graphics Area Position (GAP) self-defining field . . .. . .. . . .. . . .. . . 527
Graphics Data Descriptor (GDD) self-defining field . . .. . . .. . . .. . . 540
graphics exceptions . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 807, 846
Graphics Interchange Format (GIF) object containers. . . .. . 91, 100,
104, 119, 220, 226, 362, 426, 444, 452, 575, 585, 733, 863, 888
graphics mapping
center-and-trim . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 538
position-and-trim . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 539
scale to fill .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 537
scale to fit .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 535
graphics object area . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 523, 527
Graphics Object Content Architecture
See architectures
Graphics Output Control (GOC) self-defining field .. . .. . . .. . . .. . . 531
graphics presentation space.. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 522
graphics presentation space window . .. . .. . . .. . .. . . .. . .. . . .. 523, 540
GRID . .. . .. . . .. 89, 92, 140, 145, 196–197, 200–201, 301, 650, 871
Group ID (X'00') triplet.. . .. . 173, 301, 315–316, 321–322, 383, 705
Group Information (X'6E') triplet. . . .. . . .. . .. . . .. . .. . . .. . 321–322, 727
H
HAID . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 89, 91
HAID extender value X'E47D'. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 261, 569
HAID pools . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . .91, 566
hardware.. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 328
hardware-related print exception. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 803
home state. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .72
horizontal format download . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 700
host notification .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 807, 918, 976
Host-Assigned ID . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 89, 91
I
I,B coordinate system
B-axis. .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .55
baseline increment . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .55
character increment.. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .55
current baseline coordinate . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .55
current inline coordinate . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .55
I-axis . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .55
initial baseline coordinate . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .55
initial inline coordinate . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .55
orientation. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .56
origin. .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .55
positive baseline direction. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .55
positive inline direction . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .55
IAP (Image Area Position) self-defining field. . .. . . .. . .. . . .. . .. . . .. 500
I
c (current inline coordinate) . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .55
ICMR (Invoke CMR) command. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 174
IDD (image data descriptor) self-defining field.. . . .. . .. . . .. . .. . . .. 514
identical copies. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 177
IDO (Include Data Object) command . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 575
I
i (initial inline coordinate) . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .55
IM Image. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 120
image color .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 489
image location . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 487
image magnification . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 485
image size . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 483
output image orientation. .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 486
Write Image command . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 491
Write Image Control command .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 481
IM-Image and Coded-Font Resolution self-defining field . . .. . . .. 337
Image Area Position (IAP) self-defining field. . .. . . .. . .. . . .. . .. . . .. 500
image commands
Write Image 2 command .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 517
Write Image command . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 491
Write Image Control 2 command . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 498
Write Image Control command .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 481
Image Data Descriptor (IDD) self-defining field. . . .. . .. . . .. . .. . . .. 514
image object areas . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .18
Image Object Content Architecture
See architectures
Image Output Control (IOC) self-defining field.. . . .. . .. . . .. . .. . . .. 503
image resolution . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 337
Image Resolution (X'9A') triplet . . .. . . 221, 225, 230, 585–586, 588,
609–610, 612, 781
image-point-to-pel mapping . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 509
image-point-to-pel-with-double-dot mapping . .. . . .. . .. . . .. . .. . . .. 509
images
enlarging in IM . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 485
image size for IM . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 483
location in IM. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 487
magnifying in IM . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 485
object area in IO Image. . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 500
Include Data Object (IDO) command . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 575
Include Overlay (IO) command. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 627
Include Page Segment (IPS) command .. . .. . . .. . . .. . .. . . .. . .. . . .. 634
identifier . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 634
Include Saved Page (ISP) command . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 172
initial baseline coordinate (B
i) .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .55
initial inline coordinate (I i) . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .55
initialization sequence . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 995
inline increment . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .55
inserter bin. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 333
Installed Features self-defining field .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 340
Intelligent Printer Data Stream
all points addressability. . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 7
command and state summary . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .83
command format . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .70
command processing .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .66
command-set commands . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .14
example of command sequence. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 993
operating states . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .72
overview. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .17
presentation environment . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .17
structured fields . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 7
system environments .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 8–9


intermediate device. . . .. . .24, 88, 92, 143–144, 233–234, 237, 328,
359
intervention-required exceptions . . .. . . .. . .. . . .. . .. . . .. . .807, 811, 946
Invoke CMR (X'92') triplet . . . .. . . 206, 209, 225, 230, 240, 466, 469,
504, 512, 532–533, 555, 557, 581, 583, 600–601, 772
Invoke CMR (ICMR) command.. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 174
Invoke Tertiary Resource (X'A2') triplet. . .. . . . 37, 98, 242, 513, 555,
557–558, 564, 567, 602, 787
IO (Include Overlay) command.. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 627
IO Image .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 119
center-and-trim mapping . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 496, 507
Image Area Position self-defining field.. . . .. . .. . . .. . .. . . .. . . .. . . 500
Image Data Descriptor self-defining field . .. . .. . . .. . .. . . .. . . .. . . 514
image object area . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 500
Image Output Control self-defining field . . .. . .. . . .. . .. . . .. . . .. . . 503
image-point-to-pel mapping. .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 509
image-point-to-pel-with-double-dot mapping.. . . .. . .. . . .. . . .. . . 509
position-and-trim mapping . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 496, 508
positioning . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 496
replicate-and-trim mapping . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 496, 510
scale-to-fill mapping . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 497, 511
scale-to-fit mapping .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 496, 506
Write Image 2 command . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 517
Write Image Control 2 command . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 498
IO-Image exceptions . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 807, 826
IO-Image resource.. . . . 91, 104, 220, 226, 426, 444, 452, 575, 585,
733, 863, 888
IO-Image resource state .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .79
IOC (Image Output Control) self-defining field. .. . . .. . .. . . .. . . .. . . 503
IOCA
See architectures
IOCA Tile Resource object containers .. . .. . . .. . .. . . .. . .. . . .. 100, 362
IPDS
See Intelligent Printer Data Stream
IPDS Commands Sorted by Command Code . .. . . .. . .. . . .. . . .. . . 989
IPDS operating states .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .72
IPDS resource overview .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .88
IPS (Include Page Segment) command . .. . . .. . .. . . .. . .. . . .. . . .. . . 634
IS/3 . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 239, 984
ISP (Include Saved Page) command . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 172
italics . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 203
J
jam recovery copy counter. . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 130
jam recovery page counter . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 130
jogging . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 271
JPEG (Joint Photographic Experts Group) object containers . . .. 91,
100, 104, 120, 220, 226, 362, 426, 444, 452, 575, 585, 733, 863,
888, 984
JPEG2000 File Format (JP2) object containers . . . .. . . 91, 100, 104,
120, 220, 226, 362, 426, 444, 452, 575, 585, 733, 863, 888
K
Keep-Group-T ogether-as-a-Recovery-Unit self-defining field. . . 373
kerning . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 645
L
L-units .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .47
LAN (Local Area Network) . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 8
LCC (Load Copy Control) command. . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 176
N-up . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 183
number of identical copies . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 177
overlay ID .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 188
simplex or duplex. .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 176
suppression ID .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 188
LCP (Load Code Page) command . . .. . . .. . .. . . .. . . .. . .. . . .. . ..78, 652
LCPC (Load Code Page Control) command. . .. . . .. . .. . . .. . ..78, 656
LE (Load Equivalence) command.. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 460
levels of exception handling . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 115
LF (Load Font) command . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . ..77, 662
LFC (Load Font Control) command. .. . . .. . .. . . .. . . .. . .. . . .. . ..77, 675
LFCSC (Load Font Character Set Control) command . . .. . ..77, 671
LFE (Load Font Equivalence) command . . .. . . .. . . .. . .. . . .. . .. . . .. 196
font equivalence record. . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 196
font identifiers. . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 196
font IDs . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 200
LFI (Load Font Index) command . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 687
linear measurements . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .47
Link CMR . . .. . .. . . .. . .. . . .. . . ..39, 174, 430, 433, 610, 769, 774, 890
Linked Font (X'8D') triplet . . .. . .. . . .. . .. 137, 149, 151, 155, 239, 765
Load Code Page (LCP) command . . .. . . .. . .. . . .. . . .. . .. . . .. . ..78, 652
Load Code Page Control (LCPC) command. . .. . . .. . .. . . .. . ..78, 656
Load Copy Control (LCC) command.. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 176
N-up . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 183
number of identical copies . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 177
overlay ID . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 188
simplex or duplex. .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 176
suppression ID .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 188
Load Equivalence (LE) command.. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 460
Load Font (LF) command . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . ..77, 662
Load Font Character Set Control (LFCSC) command . . .. . ..77, 671
Load Font Control (LFC) command. .. . . .. . .. . . .. . . .. . .. . . .. . ..77, 675
Load Font Equivalence (LFE) command . . .. . . .. . . .. . .. . . .. . .. . . .. 196
font equivalence record. . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 196
font identifiers. . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 196
font IDs . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 200
Load Font Index (LFI) command . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 687
Load Resource Equivalence (LRE) command.. . . .. . .. . . .. . .. . . .. 134
Load Symbol Set (LSS) command
character raster patterns .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 702
font identifier . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 702
horizontal download . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 700
vertical download. .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 700
loaded fonts . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .46
loaded-font commands
Deactivate Font command . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 163
ending . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 170
Load Code Page command .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 652
Load Code Page Control command. . .. . .. . . .. . . .. . .. . . .. . .. . . .. 656
Load Font Character Set Control command. . . . .. . .. . . .. . .. . . .. 671
Load Font command . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 662
Load Font Control command. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 675
Load Font Index command. .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 687
Load Symbol Set command.. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 698
## Local Area Network (LAN)
as an environment . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 8
in the PC environment . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .8, 12
Local Date and Time Stamp (X'62') triplet. .. . . .. 137, 154, 239, 724
locating the logical page . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 210
logical page
coordinate system.. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 53, 55
defined . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .17
dimensions. .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 204
offset. .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 331
origin. .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 210
size . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 331
values . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 204
logical page and object area coloring. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .32
Logical Page Descriptor (LPD) command. .. . . .. . . .. . .. . . .. . .. . . .. 204
Logical Page Position (LPP) command .. . .. . . .. . . .. . .. . . .. . .. . . .. 210
logical positioning .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .65
logical units .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .47
long format font index . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 648, 687


LPD (Logical Page Descriptor) command. . . .. . .. . . .. . .. . . .. . . .. . . 204
LPP (Logical Page Position) command . .. . . .. . .. . . .. . .. . . .. . . .. . . 210
LRE (Load Resource Equivalence) command. .. . . .. . .. . . .. . . .. . . 134
LSS (Load Symbol Set) command
character raster patterns . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 702
font identifier . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 702
horizontal download . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 700
vertical download. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 700
M
Manage IPDS Dialog (MID) command . . .. . . .. . .. . . .. . .. . . .. . . .. . . 216
mapping host-assigned resource IDs. .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 134
mapping, suppression equivalence . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 460
Mark Form order . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 289
maximum baseline extent. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 692
MDD (Metadata Data Descriptor) self-defining field . .. . . .. . . .. . . 618
measurement units . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . . 47, 68
Media-Destinations self-defining field .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 355
Medium Modification IDs Supported self-defining field . . .. . . .. . . 347
medium presentation space
coordinate system. . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .48
defined . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .17
identification . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 331
size .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 331, 397
top of the medium . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .48
metadata.. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .ix, 4
associated with which object.. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. .112–113
defined . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 112, 615
Delete Home-State Metadata (DHM) command .. . .. . . .. . . .. . . 616
level.. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .112, 616, 618
Metadata Data Descriptor self-defining field .. . . .. . .. . . .. . . .. . . 618
no hierarchy. . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 114
Write Metadata command .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 619
Write Metadata Control (WMC) command . . .. . . .. . .. . . .. . . .. . . 617
Metadata Data Descriptor (MDD) self-defining field . .. . . .. . . .. . . 618
metadata exceptions . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 807, 824
metadata state . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .81
Metric Adjustment (X'79') triplet . . . .. . . .. . .. . . .. . . 137, 154, 239, 737
metric technology . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 264, 679, 681
MID (Manage IPDS Dialog) command . . .. . . .. . .. . . .. . .. . . .. . . .. . . 216
mixing rules. .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .27
MO:DCA GA (Graphic Arts) Function Set . . . .. . .. . . .. . .. . . .. 239, 986
MO:DCA Interchange Set IS/3 .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 239, 984
monospaced fonts .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 335
multi-page resource objects .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 103
N
N-up . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 183
NACK (Negative Acknowledge Replies)
defined . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .71
priority .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 124
reporting. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 278
nColor .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .42
Negative Acknowledge (NACK) Replies
defined . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .71
priority .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 124
reporting. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 278
nesting overlays.. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 623
No Operation
No Operation command . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 217
non-IPDS sense data. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 941
Non-OCA Resource object containers . . .. . . .. . .. . 91, 100, 362, 566
NOP
No Operation command . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 217
O
object areas
bar-code. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .18
graphics . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .18
image .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .18
mixing rules .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .27
placement of . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .53
text .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .18
Object Container Area Position (OCAP) self-defining field .. . . .. 596
Object Container Data Descriptor (OCDD) self-defining field . .. 609
object container object area . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . ..18, 564
Object Container Output Control (OCOC) self-defining field . . .. 599
object container presentation space .. . . .. . .. . . .. . . .. . .. . . .. . 564, 609
Object Container Presentation Space Size (X'9C') triplet . .. . . . 221,
225, 230, 585–586, 609–610, 783
object containers
center-and-trim mapping .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 604
Data Object Resource Equivalence (DORE) command .. . . .. 569
Data Object Resource Equivalence 2 (DORE2) command. .. 571
Deactivate Data Object Resource (DDOR) command . . .. . . .. 574
Deactivate Data-Object-Font Component (DDOFC)
command . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 573
Include Data Object (IDO) command .. . .. . . .. . . .. . .. . . .. . .. . . .. 575
Object Container Area Position self-defining field .. . . .. . .. . . .. 596
Object Container Data Descriptor self-defining field . .. . .. . . .. 609
object container object area.. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 564, 596
Object Container Output Control self-defining field. . . .. . .. . . .. 599
object container presentation space . .. . .. . . .. . . .. . .. . . .. . 564, 609
position mapping . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 606
position-and-trim mapping . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 605
Rasterize Presentation Object (RPO) command . .. . . .. . .. . . .. 220
Remove Resident Resource command. .. . . .. . . .. . .. . . .. . .. . . .. 590
Request Resident Resource List command.. . . .. . .. . . .. . .. . . .. 592
scale-to-fill mapping . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 606
scale-to-fit mapping . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 603
UP³I Print Data mapping .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 607
Write Object Container (WOC) command . . .. . . .. . .. . . .. . .. . . .. 613
Write Object Container Control (WOCC) command . . .. . .. . . .. 595
Object Offset (X'5A') triplet. .. . .. . 221, 225, 230, 585–586, 609–610
object-container resource state . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .80
Object-Container Type Support self-defining field.. . .. . . .. . .. . . .. 361
Object-Container Version Support self-defining field . . . .. . .. . . .. 376
Obtain Additional Exception Information order . . . .. . .. . . .. . .. . . .. 290
Obtain Printer Characteristics order .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 328
OCAP (Object Container Area Position) self-defining field .. . . .. 596
OCDD (Object Container Data Descriptor) self-defining field . .. 609
OCOC (Object Container Output Control) self-defining field . . .. 599
operating states
anystate . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .82
bar code . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .73
code page. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .78
font.. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .77
graphics . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .73
home .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .72
IM-Image .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .73
IO-Image .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .73
IO-Image resource . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .79
metadata .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .81
object container . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .73
object-container resource . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .80
overlay. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .75
page . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .74
page segment . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .76
presentation-object . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .73
summary .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .83
text .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .73
operator-viewing copy counter . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 130
operator-viewing page counter . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 130
ordered data . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .45
ordered page . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .45


orders
XOA Activate Printer Alarm . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 270
XOA Alternate Offset Stacker. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 271
XOA Control Edge Marks .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 272
XOA Discard Buffered Data . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 275
XOA Discard Unstacked Pages .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 276
XOA Exception-Handling Control . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 277
XOA Mark Form .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 289
XOA Obtain Additional Exception Information . . .. . .. . . .. . . .. . . 290
XOA Print-Quality Control .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 293
XOA Request Resource List .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 294
XOA Request Setup Name List. .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 307
XOH Deactivate Saved Page Group . . .. . . .. . .. . . .. . .. . . .. . . .. . . 315
XOH Define Group Boundary . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 317
XOH Eject to Front Facing . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 325
XOH Erase Residual Font Data .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 326
XOH Erase Residual Print Data .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 327
XOH Obtain Printer Characteristics .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 328
XOH Page Counters Control.. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 381
XOH Print Buffered Data . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 382
XOH Remove Saved Page Group. . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 383
XOH Select Input Media Source.. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 385
XOH Select Medium Modifications . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 386
XOH Separate Continuous Forms . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 388
XOH Set Media Origin .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 389
XOH Set Media Size . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 397
XOH Specify Group Operation . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 401
XOH Stack Received Pages .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 408
XOH Trace . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 409
origin of the logical page .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 210
output handling. .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 115
overlay font equivalences . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 198
overlay ID . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 188
overlay sequence . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 997
overlay state .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .75
overlays
Begin Overlay command . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 623
Deactivate Overlay command . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 625
defined . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .24
font equivalences . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 198
ID . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 628
Include Overlay command . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 627
merging . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 627
overview of IPDS architecture . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .17
P
page . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .17
Include Save Page command. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 172
page and copy counter adjustments . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 926
Page Continuation Actions. . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 283
page counters . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 128
Page Counters Control order . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 381
page ID. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 162
page segment state. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .76
page segments
font equivalences . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 198
page sequence. .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 999
page state . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .74
page-segment sequence. . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 996
pass-through audit color-conversion CMRs .. . .. . . .. . .. . . .. . . .. . .. .41
patterns for raster characters . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 702
PCL page object (PCL) object containers . . 91, 100, 104, 120, 220,
226, 362, 426, 444, 452, 575, 585, 733, 863, 888
pels . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .65
perfect bind . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 368, 747
perforation cut . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 368, 747
PFC (Presentation Fidelity Control) command .. . . .. . .. . . .. . . .. . . 218
PFC Triplets Supported self-defining field. . . .. . .. . . .. . .. . . .. . . .. . . 366
PFO
## See Preprinted Form Overlay (PFO)
physical media jam . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 803
physical pels. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .65
Portable Document Format (PDF) object containers . . . .. . 91, 100–
101, 104, 120, 220, 226, 362–363, 376, 426, 444, 452, 575, 585,
733, 863, 888
Portable Network Graphics (PNG) object containers . . . .. . . 91, 101,
104, 120, 220, 226, 363, 426, 444–445, 452–453, 575, 585, 733,
863, 888, 924
position exceptions
## See user printable area (UPA)
position mapping. .. . .. . . .. . . .. . .. . . .. . .. . . .. . .459, 468, 549, 557, 606
position-and-trim mapping . .. . .. . . .. . .. . . .. . .496, 508, 524, 539, 605
positioning . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .65
positioning graphics .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 524
positioning IO Image . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 496
positioning text . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 459
positioning the logical page .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 210
positive Acknowledge Reply from printers .. . . .. . . .. . .. . . .. . .. . . .. . .71
pre-processor or post-processor exception . . . .. . . .. . .. . . .. . .. . . .. 803
pre-rasterizing and caching .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 104
Preprinted Form Overlay (PFO) . . .. . .. . . .. . .. . . . 26, 28, 30, 189, 629
Presentation Fidelity Control (PFC) command . . . .. . .. . . .. . .. . . .. 218
presentation services. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 8
presentation space . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 522–523
Presentation Space Reset Mixing (X'70') triplet . . .. . .206, 209, 235,
466, 469, 504, 512, 532–534, 555, 557–558, 581, 583–584, 600–
602, 731
Presentation Text Object Content Architecture
See architectures
presentation-object states . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .73
Print Buffered Data order . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 382
Print-Quality Control order . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 293
Print-Quality Support self-defining field. .. . .. . . .. . . .. . .. . . .. . .. . . .. 344
Printable-Area self-defining field . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 331
printer exception IDs . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 807
printer features, available . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 341
printer features, installed. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 340
printer inoperative.. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 805
Printer mechanism unusable . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 803
printer restart . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 803
Printer Setup self-defining field. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 367
Printer Speed self-defining field . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 379
printer storage.. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 338
printing
buffered data. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 382
continuation .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 281, 283
duplex printing . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 176
jog. .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 271
minimum print quality .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 344
normal duplex . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 182
quality control. . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 293
separator sheets . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 289
simplex printing.. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 176
tumble duplex. . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 182
X
m-axis duplex. .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 182
Ym-axis duplex. .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 182
printing media
continuous form . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 325
cut sheet .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 325
size . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 397
source . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 385
processing IPDS commands . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .66
Product Identifier self-defining field . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 357
proportional fonts .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 335
PTOCA
See architectures
punch. . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 368, 747


Q
quality of print . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 293
R
raster patterns. . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 702
Rasterize Presentation Object (RPO) command . . .. . .. . . .. . . .. . . 220
received page counter.. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 129
Recognized Group ID Formats self-defining field . .. . .. . . .. . . .. . . 374
recovery-unit group exception . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 804
redrive buffered pages exception . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 804
relative metrics . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . 264, 679, 681
Remove Resident Resource (RRR) command .. . . .. . .. . . .. . . .. . . 590
Remove Saved Page Group order .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 383
Rendering Intent (X'95') triplet . .. . . .. 206, 209, 225, 230, 240, 266–
267, 466, 469, 504, 512–513, 532–533, 558, 581, 583, 600–602,
774
replicate-and-trim mapping . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 496, 510
Request Resident Resource List (RRRL) command. .. . . .. . . .. . . 592
Request Resource List order . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 294
Request Setup Name List order. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 307
reserved .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .67
Resident Color Profile object containers .. . . .. . .. . . .. . .. . . .. 101, 363
Resident Color Profile objects . .. . . .. . . .. . .. . . .. . .. . . .. . 101, 566, 568
Resident Symbol-Set Support self-defining field . . .. . .. . . .. . . .. . . 343
residual data.. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 326–327
resource queries . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 134
resource storage exception . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 803
resources
coded fonts . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .46
IPDS resource overview. . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .88
loaded fonts. . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .46
overlay. . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .24
page segment . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .24
queries . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 134
resource states . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 265
secondary .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . . 565, 569, 571, 787
tertiary .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 565, 787
retired .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 67, 1007
ring bind. .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 368, 747
RPO (Rasterize Presentation Object) command . . .. . .. . . .. . . .. . . 220
RRR (Remove Resident Resource) command .. . . .. . .. . . .. . . .. . . 590
RRRL (Request Resident Resource List) command. .. . . .. . . .. . . 592
S
saddle-stitch in . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 368, 747
saddle-stitch out . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 368, 747
saved pages .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .105, 172, 306, 402, 708
Scalable Vector Graphics (SVG) object containers . . . 91, 101, 104,
120, 220, 226, 445, 453, 575, 585, 733, 863, 888, 925
scale-to-fill mapping . . .. . .. . . .. . .. . . .. . . .. . .. . 497, 511, 524, 537, 606
scale-to-fit mapping. . . .. . .. . . .. . .. . . .. . . .. . .. .496, 506, 524, 535, 603
secondary resource . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . . 565, 569, 571, 787
secure overlay
Include Overlay command . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 627
user-printable area . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .63
valid printable area . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .62
Select Input Media Source order . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 385
Select Medium Modifications order . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 386
selecting color-conversion CMRs . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .40
self-defining fields
Bar Code Area Position . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 551
Bar Code Data Descriptor .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 559
Bar Code Output Control . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 555
Data Object Area Position.. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 577
Data Object Data Descriptor .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 585
Data Object Output Control .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 580
Graphics Area Position . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 527
Graphics Data Descriptor . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 540
Graphics Output Control. .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 531
Image Area Position . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 500
Image Data Descriptor. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 514
Image Output Control .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 503
Metadata Data Descriptor . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 618
Object Container Area Position.. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 596
Object Container Data Descriptor .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 609
Object Container Output Control . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 599
Set Bilevel Image Color . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 514
Set Extended Bilevel Image Color.. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 514
Text Area Position .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 463
Text Data Descriptor . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 470
Text Output Control . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 466
sense bytes.. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 796
Sense Type and Model (STM) command
Acknowledge Reply to . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 130, 233
Separate Continuous Forms order. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 388
separation cut .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 368, 747
Set Home State (SHS) command .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 265
Set Media Origin order . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 389
Set Media Size order . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 397
Set Presentation Environment (SPE) command . .. . .. . . .. . .. . . .. 266
setup file . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .80, 99, 327, 361, 563, 610
setup id. .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 367
setup name .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .157, 240, 307, 380, 786
Setup Name (X'9E') triplet . .. . .. . 157–159, 240, 307–311, 380, 786
sheet .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .17
short format font index . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 648, 687
SHS (Set Home State) command .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 265
simplex printing . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 176
single gate-fold in .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 368, 747
single gate-fold out . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 368, 747
size of image . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 483
Skip and Continue Actions. .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 281
SPE (Set Presentation Environment) command . .. . .. . . .. . .. . . .. 266
special data area. .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 128, 130
specification-check exceptions
bar code . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 807, 835, 966
general . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 807, 857, 969
graphics . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 807, 846, 967
IO-Image .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 807, 826, 965
metadata .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 807, 824
Specify Group Operation order. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 401
specifying color . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .33
Stack Received Pages order . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 408
stacked copy counter. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 130
stacked page counter . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 130
Standard OCA Color-Value table. .. . .. . . .. . .. . . .. . . .. . . 489, 516, 717
staple.. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 368, 746
state summary of IPDS architecture .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .83
states
See operating states
STM (Sense Type and Model) command
Acknowledge Reply to . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 130, 233
Storage Pools self-defining field. . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 338
structured fields . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. 7
processing . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .66
summary of IPDS commands .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .83
summary of IPDS states . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .83
Supported Device Resolutions self-defining field .. . .. . . .. . .. . . .. 375
Supported Group Operations self-defining field . . .. . .. . . .. . .. . . .. 356
suppression . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 460, 622, 627
suppression ID . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 188
suspended recovery-unit group exception .. . . .. . . .. . .. . . .. . .. . . .. 805
symbol set . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 335
character raster patterns .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 702
font identifier . . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 702


horizontal download . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 700
Load Symbol Set command. .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 698
vertical download. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 700
symbol-set coded font .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . .46, 635
synchronizing commands
XOA Discard Buffered Data (DBD). .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 275
XOA Discard Unstacked Pages (DUP) . . . .. . .. . . .. . .. . . .. . . .. . . 276
XOH Erase Residual Font Data (ERFD). . .. . .. . . .. . .. . . .. . . .. . . 326
XOH Erase Residual Print Data (ERPD) . .. . .. . . .. . .. . . .. . . .. . . 327
XOH Page Counters Control (PCC).. . .. . . .. . .. . . .. . .. . . .. . . .. . . 381
XOH Print Buffered Data (PBD) .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 382
XOH Stack Received Pages (SRP) .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 408
system environments
intelligent workstation or department system environment . . .. .11
Local Area Network environment. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .12
mainframe interactive environment .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .10
spooled system environment . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 9
T
Tag Image File Format (TIFF) object containers. . . .. . . 91, 101, 104,
120, 220, 226, 363, 376, 426, 444, 452, 575, 585–586, 733, 863,
888
TAP (Text Area Position self-defining field) . .. . .. . . .. . .. . . .. . . .. . . 463
TDD (text data descriptor) . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 470
temporary hardware exception .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 805
tertiary resource.. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 565, 787
text.. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 120
position mapping . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 459, 468
positioning . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 459
Text Area Position self-defining field . . .. . . .. . .. . . .. . .. . . .. . . .. . . 463
text object area . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. 458, 463
Text Output Control self-defining field. .. . . .. . .. . . .. . .. . . .. . . .. . . 466
text presentation space. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 457
Write Text Control command.. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 462
Text Area Position (TAP) self-defining field . .. . .. . . .. . .. . . .. . . .. . . 463
text commands
Load Equivalence command.. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 460
Write Text command . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 472
Write Text Control command.. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 462
text data. .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .18
Text Data Descriptor (TDD) . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 470
Text Fidelity (X'86') triplet . . . .. . .. . . .. . . .. . .. . . .. . . 218–219, 366, 755
text orientation . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .56
Text Output Control (TOC) self-defining field . . .. . . .. . .. . . .. . . .. . . 466
TIFF LZW . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .250, 1004
TOC (Text Output Control self-defining field) . . .. . . .. . .. . . .. . . .. . . 466
Toner Saver (X'74') triplet . . . .. . .. . . .. . . .. . .. . . .. . . 218–219, 366, 732
trace entries
Begin Overlay . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 423
Begin Page . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 418
Begin Presentation Object . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 425
Begin Print Unit. .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 449
Begin Trace . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 415
CMR Activation . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 433
CMR Deactivation. . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 454
CMR Invocation .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 435
CMR Tag Fidelity . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 448
CMRs Used . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 429
Color Fidelity. .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 447
Device Appearance .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 446
End Object . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 452
Exception ID . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 438
Free Form .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 439
Include Data Object .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 444
Include Overlay. .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 442
Include Saved Page . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 441
Media Source Selection . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 437
Trace Full .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 451
Trace order .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 409
trademarks .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .1024
transparent error . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 942
trim . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 747
trim after center fold or saddle stitch .. . . .. . .. . . .. . . .. . .. . . .. . 368, 748
triplets
CMR Tag Fidelity (X'96') triplet. .. . .. . . .. . .. . . .. 218–219, 366, 777
Coded Graphic Character Set Global Identifier (X'01')
triplet . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . ..137, 154, 321–322, 593, 709
Color Fidelity (X'75') triplet . .. . . .. . .. . . .. . .. . . .. 218–219, 366, 734
Color Management Resource Descriptor (X'91') triplet . .. . . . 137,
155, 240, 609–610, 769
Color Specification (X'4E') triplet . . 206, 209, 221, 225, 230, 235,
466, 469, 504, 512, 532–534, 555, 557–558, 561, 585–586,
600–602, 609–610, 713
Data Object Font Descriptor (X'8B') triplet. . ..137, 149, 151, 154,
239, 759
Device Appearance (X'97') triplet. .. . . .. . .. . . .. 239, 266–267, 779
Encoding Scheme ID (X'50') triplet . . . .. . 137, 149, 151, 154, 242,
720
Finishing Fidelity (X'88') triplet . .. . .. . . .. . .. . . .. 218–219, 366, 757
Finishing Operation (X'85') triplet. .. . . .. . .. . . .. 160, 321–323, 742
Font Resolution and Metric T echnology (X'84') triplet .. 137, 154,
239, 740
Fully Qualified Name (X'02') triplet . . . .. . 137, 149, 151, 154, 240,
593, 609–610, 711
Group ID (X'00') triplet . . . 173, 301, 315–316, 321–322, 383, 705
Group Information (X'6E') triplet . . .. . . .. . .. . . .. . . .. . . 321–322, 727
Image Resolution (X'9A') triplet .. . . 221, 225, 230, 585–586, 588,
609–610, 612, 781
Invoke CMR (X'92') triplet . . .. 206, 209, 225, 230, 240, 466, 469,
504, 512, 532–533, 555, 557, 581, 583, 600–601, 772
Invoke Tertiary Resource (X'A2') triplet . .. . 37, 98, 242, 513, 555,
557–558, 564, 567, 602, 787
Linked Font (X'8D') triplet . . .. . . .. . .. 137, 149, 151, 155, 242, 765
Local Date and Time Stamp (X'62') triplet . . .. 137, 154, 239, 724
Metric Adjustment (X'79') triplet . . .. . . .. . .. . . .. 137, 154, 239, 737
Object Container Presentation Space Size (X'9C') triplet. . . . 221,
225, 230, 585–586, 609–610, 783
Object Offset (X'5A') triplet . .. . 221, 225, 230, 585–586, 609–610
Presentation Space Reset Mixing (X'70') triplet.. . .206, 209, 235,
466, 469, 504, 512, 532–534, 555, 557–558, 581, 583–584,
600–602, 731
Rendering Intent (X'95') triplet . .. . 206, 209, 225, 230, 240, 266–
267, 466, 469, 504, 512–513, 532–533, 558, 581, 583, 600–
602, 774
Setup Name (X'9E') triplet. . .. . 157–159, 240, 307–311, 380, 786
Text Fidelity (X'86') triplet.. . .. . . .. . .. . . .. . .. . . .. 218–219, 366, 755
Toner Saver (X'74') triplet . . .. . . .. . .. . . .. . .. . . .. 218–219, 366, 732
UP³I Finishing Operation (X'8E') triplet . .. . . ..160, 239, 321–322,
767
TrueType/OpenType object containers . .. . 45–46, 89, 91, 101, 107,
134, 149, 363–364, 376, 473, 573, 612, 651, 711, 759, 765
U
underscore .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 645–646
underscore position. .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 693
underscore width. .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 693
Unicode Complex Text (UCT) .. 246, 473, 475, 477, 756, 869–870,
906–908
Unicode support . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 473
unit base . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .47
units of measure . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . 47, 68
units per unit base . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .47
UP³I . . xv, 7, 28, 160–161, 241–242, 276, 320, 324, 355, 403–404,
563–565, 582, 586, 599, 601–602, 607, 609, 742, 757, 767, 796,
801, 809, 816, 819, 894–895, 899, 919, 921, 1066
UP³I finishing . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 322


UP³I Finishing Operation (X'8E') triplet . . .. .160, 239, 321–322, 767
UP³I object containers.. . .. . . .. . .. . . .. . . . 91, 102, 364, 575, 586, 863
UP³I Paper Input Media self-defining field. . . .. . .. . . .. . .. . . .. . . .. . . 369
UP³I Print Data mapping .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 607
UP³I Tupel self-defining field.. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 369
user printable area (UPA)
Define User Area command .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 168
user-printable area (UPA) .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .63
valid printable area (VPA) .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .62
V
valid printable area (VPA)
coordinate systems relationship .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .62
Define User Area command .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 168
logical page values. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 204
positioned by LPP coordinates . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 210
user-printable area (UPA) .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .63
valid printable area (VPA) .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. .62
variable-space code point. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 658
version . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 376
vertical format download .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 700
W
WBC (Write Bar Code) command. .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 562
WBCC (Write Bar Code Control) command. .. . .. . . .. . .. . 73–76, 550
WG (Write Graphics) command
begin segment introducer .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 543
defined . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 543
WGC (Write Graphics Control) command. . . .. . .. . . .. . .. . . .. . . . 73–76
WI (Write Image) command .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 491
WI2 (Write Image 2) command .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . .79, 517
WIC (Write Image Control) command .. . .. . . .. . .. . . .. . .. . 73–76, 481
WIC2 (Write Image Control 2) command.. . . .. . .. . . .. 73–76, 79, 498
WM (Write Metadata) command . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . .81, 619
WMC (Write Metadata Control) command . . .. . . 73–75, 79–81, 617
WOC (Write Object Container) command. . . .. . .. . . .. . .. . . .. . . .. . .. .80
WOCC (Write Object Container Control) command . .. . . 73–75, 80,
595
Write Bar Code (WBC) command. .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 562
Write Bar Code Control (WBCC) command. .. . .. . . .. . .. . 73–76, 550
Bar Code Area Position . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 551
Bar Code Data Descriptor .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 559
Bar Code Output Control . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 555
Write Graphics (WG) command
begin segment introducer .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 543
defined . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 543
ending .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 170
Write Graphics Control (WGC) command. . . .. . .. . . .. . .. . . .. . . . 73–76
defined . . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 526
Graphics Area Position (GAP) . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 527
Graphics Data Descriptor (GDD) . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 540
Graphics Output Control (GOC) .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 531
Write Image (WI) command .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 491
Write Image 2 (WI2) command .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . .79, 517
Write Image Control (WIC) command .. . .. . . .. . .. . . .. . .. . 73–76, 481
color . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 489
Write Image Control 2 (WIC2) command.. . . .. . .. . . .. 73–76, 79, 498
Image Area Position (IAP). . . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 500
Image Data Descriptor (IDD).. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 514
Image Output Control (IOC). .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 503
Write Metadata (WM) command . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . .81, 619
Write Metadata Control (WMC) command . . .. . . 73–75, 79–81, 617
Metadata Data Descriptor (MDD) . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . . 618
Write Object Container (WOC) command. . . .. . .. . . .. . .. . . .. . . .. . .. .80
Write Object Container Control (WOCC) command . .. . . 73–75, 80,
595
Object Container Area Position (OCAP).. . . .. . . .. . .. . . .. . .. . . .. 596
Object Container Data Descriptor (OCDD) . .. . . .. . .. . . .. . .. . . .. 609
Object Container Output Control (OCOC) . . .. . . .. . .. . . .. . .. . . .. 599
Write Text (WT) command . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 74–76, 472
control-sequence summary .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 476
ending . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 170
Write Text Control (WTC) command .. . . .. . .. . . .. . . .. . .. . . 73–75, 462
WT (Write Text) command . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . 74–76, 472
WTC (Write Text Control) command .. . . .. . .. . . .. . . .. . .. . . 73–75, 462
X
Xbc,Ybc coordinate system . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .60
Xg,Yg coordinate system . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .60
Xio,Yio coordinate system . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .60
Xm,Ym coordinate system . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .48
XOA (Execute Order Anystate) command .. . . .. . . .. . .. . . .. . .. . . .. 268
XOA Activate Printer Alarm .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 270
XOA Alternate Offset Stacker .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 271
XOA Control Edge Marks . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 272
XOA Discard Buffered Data . . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 275
XOA Discard Unstacked Pages . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 276
XOA Exception-Handling Control .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 277
XOA Mark Form. . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 289
XOA Obtain Additional Exception Information .. . . .. . .. . . .. . .. . . .. 290
XOA Print-Quality Control. . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 293
XOA Request Resource List. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 294
XOA Request Setup Name List . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 307
XOA-RRL RT & RIDF Support self-defining field . .. . .. . . .. . .. . . .. 345
X
oa,Yoa coordinate system . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .60
Xoc,Yoc coordinate system. . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .60
XOH (Execute Order Home State) command .. . . .. . .. . . .. . .. . . .. 313
XOH Deactivate Saved Page Group.. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 315
XOH Define Group Boundary .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 317
XOH Eject to Front Facing . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 325
XOH Erase Residual Font Data . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 326
XOH Erase Residual Print Data . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 327
XOH Obtain Printer Characteristics. .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 328
XOH Page Counters Control . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 381
XOH Print Buffered Data . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 382
XOH Remove Saved Page Group . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 383
XOH Select Input Media Source . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 385
XOH Select Medium Modifications. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 386
XOH Separate Continuous Forms . . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 388
XOH Set Media Origin. . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 389
XOH Set Media Size.. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 397
XOH Specify Group Operation . . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 401
XOH Stack Received Pages. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 408
XOH Trace. .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. 409
X
p,Yp coordinate system . . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .53
Xt,Yt coordinate system .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .60
Z
Z fold .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . .. . . .. . .. . . .. . . .. . .. . . .. . 368, 748




Advanced Function Presentation Consortium
Intelligent Printer Data Stream
Reference
AFPC-0001-12
