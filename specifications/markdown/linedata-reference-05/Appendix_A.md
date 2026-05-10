Appendix A. Document and Resource Object Diagrams
This appendix contains diagrams of the elements that make up a data stream accepted by presentation
services program servers in the AFP environment. Some presentation services programs might accept
additional elements and objects. For the formal definition of all valid AFP (MO:DCA) object structures, see the
Mixed Object Document Content Architecture (MO:DCA) Reference.
Unless otherwise noted, the objects and structured fields are presented from left to right in the order in which
they must appear.
The following symbols apply to the syntax structures in this appendix:
* An asterisk indicates optional structured fields or objects. Those not marked are required.
S The letter “S” indicates structured fields or objects that can appear more than once in the
document. Those not marked can appear only once in the document.
xx object
A shaded box indicates objects that contain structured fields or other objects.
Other All other symbols are explained in the figures.


Figure 30. Structure of a Print File
*  =  optional
s  =  can appear more than once
Note: can be any valid Resource Object, for example, an
Overlay, a Page Segment, a Form Definition.
These items can
be in any order.
EDT
IPG
* s
Presentation
Page
* s
* s
IMMInternal
Medium Map
* s
Resource
Environment
Group
* s
BDT
Repeated for each
Resource Object.
ERG
ERS
Resource
Object
(see note)
BRS
BRG
Mixed Line-Page
Documents
* s
*
Print File
BPF
*
EPF
*
Inline
Resource
Group
Document
* s*
Notes:
1. The BPF/EPF structured fields are optional as a pair; if one is specified, the other must be specified as
well.
2. The mixed line-page documents and composed documents can occur in any order following the inline
resource group.
3. Each AFP (MO:DCA) document may optionally be preceded by a single document index that is implicitly
tied to the document and that indexes the document. For the formal definition of the MO:DCA document
index see the Mixed Object Document Content Architecture (MO:DCA) Reference.
4. An AFP (MO:DCA) document may contain Link Logical Element (LLE) structured fields following the BDT
and may also group presentation pages into named page groups. MO:DCA page groups may in turn
contain Tag Logical Element (TLE) structured fields following BNG. These structures do not affect the
presentation of the document. For the formal definition of these structures, see the Mixed Object Document
Content Architecture (MO:DCA) Reference.
5. If a Medium Map is included internal (inline) to the document, it is activated by immediately following it with
an IMM that explicitly invokes it, otherwise the internal Medium Map is ignored. An IMM that does not follow
an internal Medium Map may not invoke an internal Medium Map elsewhere in the document and is
assumed to reference a Medium Map in the current Form Definition.


Figure 31. Structure of a Mixed Line-Page Document
Mixed Line-Page
Documents
Presentation
Page
Line Format
Data
IMM
EDTBDT
IDM
* = optional
s = can appear more than once
† = an IMM or IDM may be specified before
any page; multiple IMMs and IDMs are allowed
*
* † s * † s * s
*
Note: The No Operation (NOP) structured field may appear anywhere in a mixed document and thus is not
listed in the structured field groupings.
Figure 32. Structure of a Presentation Page Object
These items can be in any order.
EAG
PTD
*
OBP
(text)
*
OBD
(text)
*
PGDMPS
* s
MPO
* s†
MDR
* s
MCF
* sBAG
*   = optional
s   = can appear more than once
† = required for every IPO specified in a page
@ = without OEG
+   = with OEG
IOB IPS IPO
EPGBPG
IPG
* * * * * * * ** s s s s s sss
MPG
*
PEC
*
@ +
Object
Container
Bar Code
Object
Graphics
Object
Image
Object
Presentation
Text Object
Presentation
Page
Active
Environment
Group
Note: An AFP (MO:DCA) presentation page can contain one or more Tag Logical Element (TLE) or Link
Logical Element (LLE) structured fields following the AEG. These structures do not affect the


presentation of the page. For the formal definition of these structures, see the Mixed Object Document
Content Architecture (MO:DCA) Reference.
Figure 33. Structure of Line Format Data
*  =  optional
s  =  can appear more than once
These items can appear in any order
Graphic Object
Line
Data
PTX IPS IPO IOB
Image Object Presentation Text
Object (with OEG)
* * s
* s * s * s
* s * s * s
Line-Format
DataBar Code Object
* s
Figure 34. Structure of a Presentation Text Data Object
Presentation
Text Objects
*  =  optional
s  =  can appear more than once
Note: A Presentation Text Descriptor is required in an Active
Environment Group when a text object is used in a page.
PTX
BPT EPT
PTDMDRMCFMPTOBPOBDPEC
BOG EOG
Object
Environment
Group
* s
* s* s**
*


Figure 35. Structure of an IM Image Data Object
IRD
s
s  =  can appear more than once
Image Object (IM)
Image Cells
s
IRD
s
ICP
EIM
IIDIOC
BIM
Simple or Complex
Figure 36. Structure of an IO Image Data Object
*  =  optional
s  =  can appear more than once
† =  allowed in FS45 only
IDDMDRMIOOBPOBDPEC
BOG EOG
IPD
* s
Object
Environment
Group
BIM EIM
Image Object (IO)
* * * † s


Figure 37. Structure of a Graphics Data Object
*  =  optional
s  =  can appear more than once
GDDMDRMCFMGOOBPOBDPEC
BOG EOG
GADObject
Environment
Group
BGR EGR
Graphics Object
* s
* s*** s
Figure 38. Structure of a Bar Code Data Object
*  =  optional
s  =  can appear more than once
BDDMDRMCFMBCOBPOBD
BOG EOG
BDAObject
Environment
Group
BBC EBC
Bar Code
Object
* s
* s** s


Figure 39. Structure of a Page Segment Resource Object
Graphic ObjectsImage Objects Presentation
Text Object
* s * s *
Page Segment
Object
EPSBPS
*    =  optional
s    =  can appear more than once
@  =  without OEG
@
Note: This is the structure of an AFP page segment. This structure is supported but is replaced strategically
with the MO:DCA page segment. For more information, see the Mixed Object Document Content
Architecture (MO:DCA) Reference.
Figure 40. Structure of an Overlay Resource Object
Notes:
1. An AFP (MO:DCA) overlay object may contain one or more Tag Logical Element (TLE) or Link Logical
Element (LLE) structured fields following the AEG. These structures do not affect the presentation of the
overlay. For the formal definition of these structures, see the Mixed Object Document Content Architecture
(MO:DCA) Reference.
2. The MPG and MPO structured fields are not supported in the AEG for an overlay.


Figure 41. Structure of a Form Definition Resource Object
*   =  optional
S  =  can appear more than once
† = the structured field is required in either the
Document Environment Group or the Medium Map
Group
EMM*
PEC
* s
MFC
s*
PMC
s*
MMCMCC
†
MDD
†
PGP
s*
MDR
s*
MMT
s*
MPO
*
MMO
BMM
EDG* s
MDR
* s
MFC
†
MDD
†
PGP
*
MSU
*
MMO
s*
PEC
s*
PFC
BDG
s
Medium Maps
*
Document
Environment
Group
EFMBFM
Form Definition


Figure 42. Structure of a Page Definition Resource Object
*  =  optional
s  =  can appear more than once
+  = the Data Map Transmission Subcase can contain LNDs, RCDs, or XMDs but not a mixture
† = required for every IPO specified in a page
EPM
EDM
EDX
FDX
* s
FDS
*
LND
RCD
XMD
s+
LNCDXD
*BDX
Data Map
Transmission
Subcase
EAG
PTD
*
OBP
(text)
*
OBD
(text)
*
PGD
* s
MPO
* s†
MDR
* s
MCF
* s
PEC
*
BAG
Active
Environment
Group
BDM
Data Map
s
IOB
* s*
CCP
s
ESG
PPO
* s
MPO
* s
MDR
* s
BSG
Resource
Environment
Group
*
BPM
Page Definition
MPS
Notes:
1. The Data Map Transmission Subcase may contain RCDs or XMDs instead of LNDs.
2. The Data Maps in a Page Definition must all contain LNDs, RCDs, or XMDs. A mixture is not allowed.
3. A Presentation Text Descriptor (PTD) is required in the AEG when a presentation text object is used on a
page.




Copyright © AFP Consortium 1994, 2018 179
