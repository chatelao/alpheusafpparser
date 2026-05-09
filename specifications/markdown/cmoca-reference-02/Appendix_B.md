# Appendix B. Generic CMR Name Registry

Generic CMRs are allowed for instruction Halftone CMRs and instruction Tone Transfer Curve CMRs. This appendix defines the currently registered generic CMR names. All presentation devices that support downloaded Halftone and Tone Transfer Curve CMRs must support all names defined in this registry. The device must substitute a device-specific CMR that fits as best it can. The device will not always have an accurate match but should not NACK if it recognizes the name. If the device does not recognize the generic CMR name, it uses exception ID X'025E..04' to indicate that this name is not supported.

No device or media information may be included in generic names. Only the fields describing the generic property are filled in. Other fields are not specified. Note that, for improved readability, spaces are left between fields of the CMR name in the examples below.

The CMR names specified in Figure 12 and Figure 13 are the only valid generic CMRs.

## Registered Generic Halftone CMRs

The registered generic Halftone CMRs are shown in Figure 12. Halftone Property 3 (Line Screen Frequency) or Property 2 (Halftone Type) are used to describe the generic quality of the halftone.

**Figure 12. Generic Halftone CMRs**

```text
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
```

The first eleven generic Halftone CMRs are used for clustered-dot halftones and indicate the line screen frequency of the halftone. The next three generic Halftone CMRs are used to specify a halftone of type stochastic, dispersed, or error diffusion. The last six are specific error diffusion halftones.

## Registered Generic Tone Transfer Curve CMRs

Four generic Tone Transfer Curve (TTC) CMRs are registered. The generic TTC CMRs control the “look-and-feel” or appearance (Property 2) of the image that is output. The four appearances that are supported are:
*   Dark
*   Accutone
*   Highlight Midtone
*   Standard

**Figure 13. Generic Tone Transfer Curve CMRs**

```text
Prop # ...............................................1......2.....3....4....5..........
@@@@@@@@ TC generic @@@@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ dark@@ @@@@ @@@@ @@@@ @@@@@@@@
@@@@@@@@ TC generic @@@@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ accutn @@@@ @@@@ @@@@ @@@@@@@@
@@@@@@@@ TC generic @@@@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ hilmid @@@@ @@@@ @@@@ @@@@@@@@
@@@@@@@@ TC generic @@@@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ standd @@@@ @@@@ @@@@ @@@@@@@@
```

## Generic Tone Transfer Curve Appearance Definition

A Tone Transfer Curve (color calibration) alters the darkness of image data, accounting for the dot gain characteristics of the printer. A TTC might be used to allow one device to emulate the output of another device.

The actual appearance depends on a combination of the printer model, the halftone screen, and the appearance selected (the TTC).

*   **Standard:** Exactly undoing the dot gain of the printer.
*   **Dark, Accutone, Highlight Midtone, Standard:** Have dot gains of 33, 22, 14, and 0 percent respectively, measured at the 50% dot. Accutone approximates linear L* tone characteristics.

## Using Generic Halftones and TTCs Together

The tone transfer curve (TTC) and halftone are interrelated. The TTC depends on the halftone that is selected. So, when mapping generic to device-specific, the halftone is mapped first and then the TTC mapping is done, taking into consideration the halftone that is being used.

A device-specific TTC would specify both the Look-and-Feel and the Halftone Characterization.

Example of a device-specific TTC reference:
```text
Prop # ...............................................1......2.....3....4....5..........
@@@@@@@@ TC 001.200 IBM@@ @@@@@@ @@@ @@@ @@@ @@ @@@ @@@@@ accutn 141@ @@@@ @@@@ @@@@@@@@
```

If the printer has device-specific halftones at 75, 104, and 141 lpi, it would need to have 12 device-specific TTCs available to cover all combinations of the 4 generic TTC appearances and 3 halftones.
