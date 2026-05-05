Appendix B. Generic CMR Name Registry
Generic CMRs are allowed for instruction Halftone CMRs and instruction T one Transfer Curve CMRs. This
appendix defines the currently registered generic CMR names. All presentation devices that support
downloaded Halftone and T one Transfer Curve CMRs must support all names defined in this registry. The
device must substitute a device-specific CMR that fits as best it can. The device will not always have an
accurate match but should not NACK if it recognizes the name. If the device does not recognize the generic
CMR name, it uses exception ID X'025E..04' to indicate that this name is not supported.
No device or media information may be included in generic names. Only the fields describing the generic
property are filled in. Other fields are not specified. Note that, for improved readability, spaces are left between
fields of the CMR name in the examples below.
The CMR names specified in Figure 12 and Figure 13 on page 122 are the only valid generic CMRs.
Registered Generic Halftone CMRs
The registered generic Halftone CMRs are shown in Figure 12. Halftone Property 3 (Line Screen Frequency)
or Property 2 (Halftone Type) are used to describe the generic quality of the halftone.
Figure 12. Generic Halftone CMRs
Prop # ...............................................1......2.....3....4....5..........
@@@@@@@@ HT generic @@@@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ @@@@@@ 71@@ @@@@ @@@@ @@@@@@@@
@@@@@@@@ HT generic @@@@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ @@@@@@ 85@@ @@@@ @@@@ @@@@@@@@
@@@@@@@@ HT generic @@@@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ @@@@@@ 106@ @@@@ @@@@ @@@@@@@@
@@@@@@@@ HT generic @@@@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ @@@@@@ 120@ @@@@ @@@@ @@@@@@@@
@@@@@@@@ HT generic @@@@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ @@@@@@ 141@ @@@@ @@@@ @@@@@@@@
@@@@@@@@ HT generic @@@@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ @@@@@@ 150@ @@@@ @@@@ @@@@@@@@
@@@@@@@@ HT generic @@@@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ @@@@@@ 170@ @@@@ @@@@ @@@@@@@@
@@@@@@@@ HT generic @@@@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ @@@@@@ 190@ @@@@ @@@@ @@@@@@@@
@@@@@@@@ HT generic @@@@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ @@@@@@ 202@ @@@@ @@@@ @@@@@@@@
@@@@@@@@ HT generic @@@@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ @@@@@@ 300@ @@@@ @@@@ @@@@@@@@
@@@@@@@@ HT generic @@@@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ @@@@@@ 600@ @@@@ @@@@ @@@@@@@@
@@@@@@@@ HT generic @@@@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ sto@@@ @@@@ @@@@ @@@@ @@@@@@@@
@@@@@@@@ HT generic @@@@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ dsp@@@ @@@@ @@@@ @@@@ @@@@@@@@
@@@@@@@@ HT generic @@@@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ erd@@@ @@@@ @@@@ @@@@ @@@@@@@@
@@@@@@@@ HT generic @@@@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ f-d@@@ @@@@ @@@@ @@@@ @@@@@@@@
@@@@@@@@ HT generic @@@@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ jjn@@@ @@@@ @@@@ @@@@ @@@@@@@@
@@@@@@@@ HT generic @@@@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ stu@@@ @@@@ @@@@ @@@@ @@@@@@@@
@@@@@@@@ HT generic @@@@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ brk@@@ @@@@ @@@@ @@@@ @@@@@@@@
@@@@@@@@ HT generic @@@@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ sra@@@ @@@@ @@@@ @@@@ @@@@@@@@
@@@@@@@@ HT generic @@@@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ s-a@@@ @@@@ @@@@ @@@@ @@@@@@@@
The first eleven generic Halftone CMRs are used for clustered-dot halftones and indicate the line screen
frequency of the halftone. The next three generic Halftone CMRs are used to specify a halftone of type
stochastic, dispersed, or error diffusion. The last six are specific error diffusion halftones.

## Page 138

122 CMOCA Reference
Registered Generic Tone Transfer Curve CMRs
Four generic T one Transfer Curve (TTC) CMRs are registered. The generic TTC CMRs control the “look-and-
feel” or appearance (Property 2) of the image that is output. The four appearances that are supported are:
• Dark
• Accutone
• Highlight Midtone
• Standard
The registered generic T one Transfer Curve CMR names are shown in the figure below.
Figure 13. Generic Tone Transfer Curve CMRs
Prop # ...............................................1......2.....3....4....5..........
@@@@@@@@ TC generic @@@@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ dark@@ @@@@ @@@@ @@@@ @@@@@@@@
@@@@@@@@ TC generic @@@@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ accutn @@@@ @@@@ @@@@ @@@@@@@@
@@@@@@@@ TC generic @@@@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ hilmid @@@@ @@@@ @@@@ @@@@@@@@
@@@@@@@@ TC generic @@@@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ standd @@@@ @@@@ @@@@ @@@@@@@@
These appearances were designed for black/white printers and allow the user to specify the general look of the
output. When one of these generic TTC CMRs is targeted for a color printer, the expected result is not clearly
defined and the default TTC curve (the identity) can be substituted.
Generic Tone Transfer Curve Appearance Definition
A T one Transfer Curve (color calibration) alters the darkness of image data, accounting for the dot gain
characteristics of the printer. A TTC might be used to allow one device to emulate the output of another device.
For example, to emulate an offset press, it might be desired that a 50% tint should be printed as a 65% tint
after calibration. T o accomplish this, a T one Transfer Curve compensates for the printer's dot gain so that when
a 50% tint is specified in the image, a 65% (Murray-Davies apparent dot area) tint is printed.
The actual appearance depends on a combination of the printer model, the halftone screen, and the
appearance selected (the TTC).
The dot gain curves for the final output appearance when using the generic TTC CMRs are shown in Figure 14
on page 123. They represent the effect of the combined TTC, the halftone, and the dot gain from the printer.
This means, for instance, that the Standard generic TTC is exactly undoing the dot gain of the printer. For a
given generic TTC, each printer model must supply device-specific TTCs for each of the standard halftones
that it supports.
Dot gain is frequently quoted as a single number, for example “15 percent dot gain”. If the dot gain is specified
without a corresponding percent dot where it is measured, a 50% dot is assumed. The appearances Dark,
Accutone, Highlight Midtone, and Standard have dot gains of 33, 22, 14, and 0 percent respectively, measured
at the 50% dot. Accutone approximates linear L* tone characteristics.
Figure 15 on page 123 shows the lightness as a function of percent dot for each of the generic T one Transfer
Curves. The lightness curve is the primary reference.
Generic CMR Name Registry

## Page 139

CMOCA Reference 123
Figure 14. Dot Gain as a Function of Percent Dot
0 20 40 60 80 100
0
5
10
15
20
25
30
35Dot Gain (percent)
Percent Dot
dark
highlight midtone
accutone
standard
The dot gain represents what the output looks like in terms commonly understood. It is not necessarily an
accurate representation.
Figure 15. Lightness as a Function of Percent Dot
0 20 40 60 80 100
0
10
20
30
40
50
60
70
80
90
100L* (paper reference)
Percent Dot
dark
highlight midtone
accutone
standard
Generic CMR Name Registry

## Page 140

124 CMOCA Reference
Using Generic Halftones and TTCs Together
The tone transfer curve (TTC) and halftone are interrelated. The TTC depends on the halftone that is selected.
So, when mapping generic to device-specific, the halftone is mapped first and then the TTC mapping is done,
taking into consideration the halftone that is being used.
A device-specific TTC would specify both the Look-and-Feel and the Halftone Characterization. Note that, in
the following example, other @@@ fields would actually be filled in since this is a device-specific CMR.
Prop # ...............................................1......2.....3....4....5..........
@@@@@@@@ TC 001.200 IBM@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ accutn 141@ @@@@ @@@@ @@@@@@@@
Assume that a particular printer has the following 3 device-specific halftones available: [Other @@@ fields
would actually be filled in since these CMRs are device specific.]
Prop # ...............................................1......2.....3....4....5..........
@@@@@@@@ HT 001.200 IBM@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ @@@@@@ 75@@ @@@@ @@@@ @@@@@@@@
@@@@@@@@ HT 001.200 IBM@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ @@@@@@ 104@ @@@@ @@@@ @@@@@@@@
@@@@@@@@ HT 001.200 IBM@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ @@@@@@ 141@ @@@@ @@@@ @@@@@@@@
Then it would need to have 12 device-specific TTCs available to cover all combinations. Note that some of the
TTCs could be duplicates. Here are five of them.
Prop # ...............................................1......2.....3....4....5..........
@@@@@@@@ TC 001.200 IBM@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ hilmid 75@@ @@@@ @@@@ @@@@@@@@
@@@@@@@@ TC 001.200 IBM@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ hilmid 104@ @@@@ @@@@ @@@@@@@@
@@@@@@@@ TC 001.200 IBM@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ hilmid 141@ @@@@ @@@@ @@@@@@@@
@@@@@@@@ TC 001.200 IBM@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ standd 75@@ @@@@ @@@@ @@@@@@@@
@@@@@@@@ TC 001.200 IBM@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ standd 104@ @@@@ @@@@ @@@@@@@@
Now suppose the user selects a generic halftone of 106 lpi and a generic TTC for Highlight Midtone by
specifying the following:
Prop # ...............................................1......2.....3....4....5..........
@@@@@@@@ HT generic @@@@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ @@@@@@ 106@ @@@@ @@@@ @@@@@@@@
@@@@@@@@ TC generic @@@@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ hilmid @@@@ @@@@ @@@@ @@@@@@@@
So, given the printer assumed above, when the device-specific 104 lpi halftone is selected, the device-specific
TTC for hilmid / 104 would be selected. These are the two device-specific CMRs that would be used:
Prop # ...............................................1......2.....3....4....5..........
@@@@@@@@ HT 001.200 IBM@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ @@@@@@ 104@ @@@@ @@@@ @@@@@@@@
@@@@@@@@ TC 001.200 IBM@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ hilmid 104@ @@@@ @@@@ @@@@@@@@
Now, that was simple because both the halftone and TTC were generic. If the halftone is not one of the device-
specific halftones that the printer has available for conversion from generic, then the printer might not have an
exact device-specific match for the generic TTC. In this case, the device will select the device-specific TTC that
matches as best it can.
Generic CMR Name Registry

## Page 141

Copyright © AFP Consortium 2006, 2025 125
