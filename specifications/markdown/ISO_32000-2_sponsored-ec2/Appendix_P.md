Annex P (informative) An algorithm to determine the actual blending colour space of a transparency group

The diagram in this annex illustrates a possible algorithm to determine the blending colour space of a transparency group:

• Transparency groups that are non-isolated or isolated without CS entry always inherit their blending colour space from their parent group.
• Isolated transparency groups with CS entry that is a CIE-based colour space use that CIE-based colour space.

> **NOTE** Isolated transparency groups with a device colour space as blending colour space first apply the
default colour space mechanism.
In case of no valid default colour space remapping, the ancestor transparency groups are searched – starting with the nearest - for a possible blending colour space to inherit from.

Page transparency groups, which do not have a parent, instead inherit their blending colour space from the output device, or from the output intent.


## Page 996

Figure P.1 — A possible algorithm to determine the blending colour space of a transparency group


## Page 997

ISO 32000-2:2020
