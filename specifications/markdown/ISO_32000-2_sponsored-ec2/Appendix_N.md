Annex N
(informative)
Best practice for halftones

N.1      General

As stated in 10.6.5.1 "General" the use of halftones specified in a PDF file for rendering is optional. In
complex areas, such as those where some graphics objects are transparent, this could lead to
discrepancies between the outputs from different PDF processors. Therefore two alternative
approaches are defined as best practices for different use cases:

| • | Classic approach: For cases where little variation in halftones is expected between graphics |

objects or where there is a desire to match the behaviour specified in ISO 32000-1 and earlier
revisions of the PDF Reference.
| • | Object-based approach: For cases where many objects are expected to specify halftones, where |

those halftones may be rather different in appearance and behaviour on the output device or
where application of the specified halftone is important for the correct output.
The authors of a PDF processor might choose to adopt one of these approaches if it is rendering for an
output device that requires halftoned output, and if that halftoning needs to take account of halftones
specified within the PDF file.

N.2      Classic approach

When objects are transparent, rendering of an object does not occur when the object is specified but at
some later time. Hence, the implementation might be expected to keep track of the halftone and
transfer function parameters at each point on the page from the time they are specified until the time
rendering actually occurs. This means that these rendering parameters would need to be associated
with regions of the page rather than with individual objects.

The halftone and transfer function to be used at any given point on the page would normally be those
in effect at the time of painting the last (topmost) elementary graphics object enclosing that point if the
object is fully opaque. Only elementary objects are taken into account; the rendering parameters
associated with a group object are ignored. The topmost object at any point is defined to be the topmost
elementary object in the entire page stack that has a non-zero object shape value (fj) at that point (that
is, for which the point is inside the object). An object is considered to be fully opaque if all of the
following conditions hold at the time the object is painted:

| • | the current alpha constant in the graphics state (stroking or nonstroking, depending on the |

painting operation) is 1.0; and
| • | the current blend mode in the graphics state is Normal (or Compatible, which is treated as |

equivalent to Normal); and
| • | the current soft mask in the graphics state is None. If the object is an image XObject, there is not |

an SMask entry in its image dictionary; and
| • | the foregoing three conditions were also true at the time the Do operator was invoked for the |

group containing the object, as well as for any direct ancestor groups; and


## Page 990

| • | if the current colour is a tiling pattern, all objects in the definition of its pattern cell also satisfy the |

foregoing conditions.
Together, these conditions ensure that only the object shall contribute to the colour at the given point,
completely obscuring the backdrop. For portions of the page whose topmost object is not fully opaque
or that are never painted at all, the default halftone and transfer function for the page is used instead
(see "Table 52 — Device-dependent graphics state parameters").

If a graphics object is painted with overprinting enabled — that is, if the applicable (stroking or
nonstroking) overprint parameter in the graphics state is true — the halftone and transfer function to
use at a given point is determined independently for each colour component. An object is considered
opaque for a given component only if overprinting yields the source colour (not the backdrop colour)
for that component. See 11.7.4, "Overprinting and transparency".

N.3      Object-based approach

This approach applies halftone and transfer parameters that are specified in a PDF graphics state, even
for those regions of the page where transparency is in use.

PDF versions prior to ISO 32000-2 specified that the default halftone be used for regions where the
topmost graphic object is not fully opaque. Using the approach set out in this clause will mean that
some PDF files will be rendered differently by an ISO 32000-2 renderer to their previous appearance.

When overprinting is not enabled the halftone and transfer function to be used at any given point on
the page are those in effect at the time of painting the last (topmost) elementary graphics object
enclosing that point that is not fully transparent. An object is considered to be fully transparent if any
of these conditions are true:

| • | the current shape in the graphics state is 0.0 at the time the object was painted; or |
| --- | --- |
| • | the shape was 0.0 at the time the Do operator was invoked for the group containing the object, or |

for any direct ancestor group; or
| • | the current alpha constant in the graphics state (stroking or nonstroking, depending on the |

painting operation) was 0.0 at the time the object was painted and all direct ancestor groups
containing the object are non-knockout groups.
NOTE         Subclause 11.6.4 describes the shape and opacity of different graphical object types.
When a soft mask is in use and/or the current colour is a tiling pattern, a PDF processor that is
rendering the page shall make the determination as to whether the topmost object is fully transparent
(i.e. whether it has a non-zero shape), for every rendered pixel individually.

Soft masks are used when an SMask key is present in a graphics state parameter dictionary, an image
has an SMask entry in the image dictionary, or an image using the JPXDecode filter has an
SMaskInData key with a non-zero value in the image dictionary.

Determining transparency and shape at the pixel resolution at which the page is rendered avoids
artefacts where a fringe using an unexpected halftone might appear in situations such as around a nonrectangular image with soft edges, produced using a soft mask.

Together, these conditions ensure that any object that is fully transparent does not contribute to the
colour at the given point and vice versa. For portions of the page that are only painted with fully


## Page 991

ISO 32000-2:2020

transparent objects or that are never painted at all, the default halftone and transfer function for the
page is used (see "Table 52 — Device-dependent graphics state parameters").

| If a graphics object is painted with overprinting enabled | — that is, if the applicable (stroking or |
| --- | --- |
| nonstroking) overprint parameter in the graphics state is | true — the halftone and transfer function to |
| use at a given point is determined independently for each colour co | mponent by using the values from |

the topmost object that marks that component and is not fully transparent. An object is considered
fully transparent when overprinting is enabled for a given component only if overprinting yields the
backdrop colour (not the source colour) for that component.

If an object would be regarded as fully transparent under the rules for when overprinting is not
enabled then all components will be fully transparent when overprinting is enabled.

| If there are areas of the page that a | re not marked by any graphical object in one or more colour |

components and those areas intersect with an overprinting graphics object that marks other colour
| components, the screening and transfer function to use for the intersection of those unmarked area | s |

and the topmost overprinting object is taken from that overprinting object.

In some cases the rendered result could have marks on it that are not derived from the current page,
e.g. from an imposition process or because of the emulation of paper colour for an absolute
colorimetric colour rendering.

If the topmost graphics object is painted without overprinting enabled the halftone and transfer
function to use for all colour components is determined by that object, even if it does not mark those
components directly itself.

EXAMPLE            if a 100% Cyan object is painted with 50% Normal transparency but no overprint over a 100% Magenta
object, the halftone and transfer function specified for the Cyan object will be used for those parts of the
Magenta object that intersect it.

This approach applies to transfer functions from the value of the TR or TR2 keys in a graphics state
parameter dictionary and to the value of the TransferFunction key in a halftone dictionary. The
addition of a partially transparent object, such as a watermark, over an object which has a transfer
function attached will lead to the transfer function being ignored. As a result it is important to use
transfer functions with care.


## Page 992

