Chapter 6. Compliance with PTOCA
This chapter:
• Describes the base level of PTOCA
• Describes the PT1 subset of PTOCA
• Describes the PT2 subset of PTOCA
• Describes the PT3 subset of PTOCA
• Describes the PT4 subset of PTOCA
• States general requirements for compliance
Base Level
The mandatory base level contains functions and facilities required by all implementations. By itself the
mandatory base level is not sufficient; it must always be supplemented by a higher subset such as PT1, PT2,
or PT3. The base level represents the base set of functions defined within PTOCA. It is the minimum set of
functions required to be supported in any environment, and consists of the following minimum general
communication capabilities:
• Recognition of control sequences, chained or unchained
• Interpretation and validation of the control sequence
• Rejection of control sequences and parameters, including return of error data, that are not supported within
the supported subset
• Reporting, on request from the controlling environment, the supported features
• Reporting exception conditions to the controlling environment

<!-- Page 172 -->

154 PTOCA Reference
PT1 Subset
The following table shows the control sequences that are valid for a PT1 subset compliant receiver , and the
associated parameter ranges. The offset shown in the table indicates the beginning of the parameter data
within the control sequence.
Control Sequence Offset (Unchained) Parameter PT1 Range Notes
AMB 4-5 DSPLCMNT X'0000'-X'7FFF' 4
AMI 4-5 DSPLCMNT X'0000'-X'7FFF' 4
BLN 6
BSU 4 LID X'01'-X'7F'
DBR 4-5 RLENGTH X'0000'-X'7FFF' 1,4
6-8 RWIDTH X'0000'-X'00C0' 1,2,3
DIR 4-5 RLENGTH X'0000'-X'7FFF' 1,4
6-8 RWIDTH X'0000'-X'00C0' 1,2,3
ESU 4 LID X'01'-X'7F'
NOP 4-256 IGNDA T A 7
RMB 4-5 INCRMENT X'8000'-X'7FFF' 1,4
RMI 4-5 INCRMENT X'8000'-X'7FFF' 1,4
RPS 4-5 RLENGTH X'0000'-X'7FFF'
6-256 RPTDA T A 8
SBI 4-5 INCRMENT X'0000'-X'7FFF' 3,4
SCFL 4 LID X'00'-X'7F' 3
SIA 4-5 ADJSTMNT X'0000'-X'0FFF' 3,4
6 DIRCTION X'00' 9
SIM 4-5 DSPLCMNT X'0000'-X'7FFF' 3,4
STC 4-5 FRGCOLOR X'FF07' 3,5
6 PRECSION X'00'-X'01' 3,10
ST O 4-5 IORNTION X'0000', X'2D00', X'5A00', X'8700' 3
6-7 BORNTION X'0000', X'2D00', X'5A00', X'8700' 3
SVI 4-5 INCRMENT X'0000'-X'0FFF' 3,4
TRN 4-256 TRNDA T A 8
Notes:
1. This parameter is a signed binary number that may be positive or negative. Negative numbers are
expressed in twos-complement form.
2. The PTOCA range for RWI DTH is X'8000' - X'7FFF' plus a fractional value byte ranging from X'00' - X'FF'.
That is, the fractional values range from 1/2 measurement unit to 1/256 measurement unit. The PT1 subset
range for RWIDTH is X'0000' - X'00C0' in bytes 6 and 7. Byte 8 is always X'00', unless bytes 6 and 7 are
X'FFFF', in which case byte 8 is X'FF'.
3. The default indicator is allowed, meaning obtain a value from the hierarchy .
4. The range values shown assume a measurement unit of 1/1440 inch. That is, the measurement base is
assumed to be ten inches, and the X
p
-units per unit base and Y
p
-units per unit base are assumed to be
PT1 Subset

<!-- Page 173 -->

PTOCA Reference 155
14,400. If a different measurement unit is used, the correct range values can be determined using the
conversion routine described in “Interpreting Ranges” on page 47.
5. For the PTOCA range, see “Set T ext Color (STC)” on page 102. The PT1 range is X'FF07'.
6. The Begin Line (BLN) control sequence has no parameters.
7. The No Operation (NOP) control sequence may contain any data that does not exceed the field length. The
data is ignored.
8. The T ransparent Data (TRN) and Repeat String (RPS) control sequences may contain any data that does
not exceed the field length. However , the data will be presented as a character string.
9. The default indicator is not allowed for this parameter in this subset.
10. The STC PRECSION parameter has been retired; see “Retired Parameters” on page 177. New PTOCA
generators should not specify this parameter and new receivers should ignore it..
PT1 Subset

<!-- Page 174 -->

156 PTOCA Reference
PT2 Subset
The following table shows the control sequences that are valid for a PT2 subset compliant receiver , and the
associated parameter ranges. The offset shown in the table indicates the beginning of the parameter data
within the control sequence.
Control Sequence Offset (Unchained) Parameter PT2 Range Notes
AMB 4-5 DSPLCMNT X'0000'-X'7FFF' 4
AMI 4-5 DSPLCMNT X'0000'-X'7FFF' 4
BLN 6
BSU 4 LID X'01'-X'7F'
DBR 4-5 RLENGTH X'0000'-X'7FFF' 1,4
6-8 RWIDTH X'0000'-X'00C0' 1,2,3
DIR 4-5 RLENGTH X'0000'-X'7FFF' 1,4
6-8 RWIDTH X'0000'-X'00C0' 1,2,3
ESU 4 LID X'01'-'X'7F'
NOP 4-256 IGNDA T A 7
OVS 4 BYPSIDEN X'00'-X'0E' 3
5-6 OVERCHAR X'0000'-X'FFFF'
RMB 4-5 INCRMENT X'8000'-X'7FFF' 1,4
RMI 4-5 INCRMENT X'8000'-X'7FFF' 1,4
RPS 4-5 RLENGTH X'0000'-X'7FFF'
6-256 RPTDA T A 8
SBI 4-5 INCRMENT X'0000'-X'7FFF' 3,4
SCFL 4 LID X'00'-X'FE' 3
SIA 4-5 ADJSTMNT X'0000'-X'0FFF' 3,4
6 DIRCTION X'00'-X'01' 3
SIM 4-5 DSPLCMNT X'0000'-X'7FFF' 3,4
STC 4-5 FRGCOLOR X'0000', X'FF00', X'FF07', X'FFFF' 3,5
6 PRECSION X'00'-X'01' 3,9
ST O 4-5 IORNTION X'0000', X'2D00', X'5A00', X'8700' 3
6-7 BORNTION X'0000', X'2D00', X'5A00', X'8700' 3
SVI 4-5 INCRMENT X'0000'-X'0FFF' 3,4
TBM 4 DIRCTION X'00'-X'03' 3
5 PRECSION X'00'-X'01' 3
6-7 INCRMENT X'0000'-X'7FFF' 3,4
TRN 4-256 TRNDA T A 8
USC 4 BYPSIDEN X'00'-X'0E' 3
PT2 Subset

<!-- Page 175 -->

PTOCA Reference 157
Notes:
1. This parameter is a signed binary number that may be positive or negative. Negative numbers are
expressed in twos-complement form.
2. The PTOCA range for RWI DTH is X'8000' - X'7FFF' plus a fractional value byte ranging from X'00' - X'FF'.
That is, the fractional values range from 1/2 measurement unit to 1/256 measurement unit. The PT2 subset
range for RWIDTH is X'0000' - X'00C0' in bytes 6 and 7. Byte 8 is always X'00', unless bytes 6 and 7 are
X'FFFF', in which case byte 8 is X'FF'.
3. The default indicator is allowed, meaning obtain a value from the hierarchy .
4. The range values shown assume a measurement unit of 1/1440 inch. That is, the measurement base is
assumed to be ten inches, and the X
p
-units per unit base and Y
p
-units per unit base are assumed to be
14,400. If a different measurement unit is used, the correct range values can be determined using the
conversion routine described in “Interpreting Ranges” on page 47.
5. For the PTOCA range, see “Set T ext Color (STC)” on page 102. The PT2 range is X'0000', X'FF00',
X'FF07', and X'FFFF'.
6. The Begin Line (BLN) control sequence has no parameters.
7. The No Operation (NOP) control sequence may contain any data that does not exceed the field length.
8. The T ransparent Data (TRN) and Repeat String (RPS) control sequences may contain any data that does
not exceed the field length. However , the data is presented as a character string.
9. The STC PRECSION parameter has been retired; see “Retired Parameters” on page 177. New PTOCA
generators should not specify this parameter and new receivers should ignore it.
PT2 Subset

<!-- Page 176 -->

158 PTOCA Reference
PT3 Subset
The following table shows the control sequences that are valid for a PT3 subset compliant receiver , and the
associated parameter ranges. The offset shown in the table indicates the beginning of the parameter data
within the control sequence.
Control Sequence Offset (Unchained) Parameter PT3 Range Notes
AMB 4-5 DSPLCMNT X'0000'-X'7FFF' 4
AMI 4-5 DSPLCMNT X'0000'-X'7FFF' 4
BLN 6
BSU 4 LID X'01'-X'7F'
DBR 4-5 RLENGTH X'0000'-X'7FFF' 1,4
6-8 RWIDTH X'0000'-X'00C0' 1,2,3
DIR 4-5 RLENGTH X'0000'-X'7FFF' 1,4
6-8 RWIDTH X'0000'-X'00C0' 1,2,3
ESU 4 LID X'01'-'X'7F'
NOP 4-256 IGNDA T A 7
OVS 4 BYPSIDEN X'00'-X'0E' 3
5-6 OVERCHAR X'0000'-X'FFFF'
RMB 4-5 INCRMENT X'8000'-X'7FFF' 1,4
RMI 4-5 INCRMENT X'8000'-X'7FFF' 1,4
RPS 4-5 RLENGTH X'0000'-X'7FFF'
6-256 RPTDA T A 8
SBI 4-5 INCRMENT X'0000'-X'7FFF' 3,4
SCFL 4 LID X'00'-X'FE' 3
SEC 5
10
1 1
12
13
14-17
COLSPCE
COLSIZE1
COLSIZE2
COLSIZE3
COLSIZE4
COL V ALUE
X'01', X'04', X'06'
X'08', X'40'
X'01'-X'08', X'10'
X'00'-X'08'
X'00'-X'08'
X'00'-X'08'
Full range allowed by
the color space
SIA 4-5 ADJSTMNT X'0000'-X'0FFF' 3,4
6 DIRCTION X'00'-X'01' 3
SIM 4-5 DSPLCMNT X'0000'-X'7FFF' 3,4
STC 4-5 FRGCOLOR X'0000', X'FF00', X'FF07', X'FFFF' 3,5
6 PRECSION X'00'-X'01' 3,9
ST O 4-5 IORNTION X'0000', X'2D00', X'5A00', X'8700' 3
6-7 BORNTION X'0000', X'2D00', X'5A00', X'8700' 3
SVI 4-5 INCRMENT X'0000'-X'0FFF' 3,4
TBM 4 DIRCTION X'00'-X'03' 3
PT3 Subset

<!-- Page 177 -->

PTOCA Reference 159
Control Sequence Offset (Unchained) Parameter PT3 Range Notes
5 PRECSION X'00'-X'01' 3
6-7 INCRMENT X'0000'-X'7FFF' 3,4
TRN 4-256 TRNDA T A 8
USC 4 BYPSIDEN X'00'-X'0E' 3
Notes:
1. This parameter is a signed binary number that may be positive or negative. Negative numbers are
expressed in twos-complement form.
2. The PTOCA range for RWI DTH is X'8000' - X'7FFF' plus a fractional value byte ranging from X'00' - X'FF'.
That is, the fractional values range from 1/2 measurement unit to 1/256 measurement unit. The PT3 subset
range for RWIDTH is X'0000' - X'00C0' in bytes 6 and 7. Byte 8 is always X'00', unless bytes 6 and 7 are
X'FFFF', in which case byte 8 is X'FF'.
3. The default indicator is allowed, meaning obtain a value from the hierarchy .
4. The range values shown assume a measurement unit of 1/1440 inch. That is, the measurement base is
assumed to be ten inches, and the X
p
-units per unit base and Y
p
-units per unit base are assumed to be
14,400. If a different measurement unit is used, the correct range values can be determined using the
conversion routine described in “Interpreting Ranges” on page 47.
5. For the PTOCA range, see “Set T ext Color (STC)” on page 102. The PT3 range is X'0000', X'FF00',
X'FF07', and X'FFFF'.
6. The Begin Line (BLN) control sequence has no parameters.
7. The No Operation (NOP) control sequence may contain any data that does not exceed the field length.
8. The T ransparent Data (TRN) and Repeat String (RPS) control sequences may contain any data that does
not exceed the field length. However , the data is presented as a character string.
9. The STC PRECSION parameter has been retired; see “Retired Parameters” on page 177. New PTOCA
generators should not specify this parameter and new receivers should ignore it..
PT3 Subset

<!-- Page 178 -->

160 PTOCA Reference
PT4 Subset
The following table shows the control sequences that are valid for a PT4 subset compliant receiver , and the
associated parameter ranges. The offset shown in the table indicates the beginning of the parameter data
within the control sequence.
Control Sequence Offset (Unchained) Parameter PT4 Range Notes
AMB 4-5 DSPLCMNT X'0000'-X'7FFF' 3
AMI 4-5 DSPLCMNT X'0000'-X'7FFF' 3
BLN 5
BSU 4 LID X'01'-X'7F'
DBR 4-5 RLENGTH X'8000'-X'7FFF' 1, 3
6-7
8
RWIDTH X'8000'-X'7FFF'
X'00'-X'FF'
1,2
DIR 4-5 RLENGTH X'8000'-X'7FFF' 1,3
6-7
8
RWIDTH X'8000'-X'7FFF'
X'00'-X'FF'
1,2
ESU 4 LID X'01'-'X'7F'
GAR 4–253 (chained) ADV ANCE X'0000'-'X'FFFF' 3
GIR 4–253 (chained) GL YPHID X'0000'-'X'FFFF'
GLC 4-5 IADVNCE X'8000'-X'7FFF' 1, 3
6 OIDLGTH 0, 13-129
7 FFNLGTH 0-(255-(10+OIDLGTH))
12 -n FONTOID (first byte) X'06'
(n+1)–p FFONTNME valid UTF-16BE code points
GOR 4–253 (chained) OFFSET X'8000'-X'7FFF' 1
NOP 4-256 IGNDA T A 6
OVS 4 BYPSIDEN X'00'-X'0E' 2
5-6 OVERCHAR X'0000'-X'FFFF'
RMB 4-5 INCRMENT X'8000'-X'7FFF' 1,3
RMI 4-5 INCRMENT X'8000'-X'7FFF' 1,3
RPS 4-5 RLENGTH X'0000'-X'7FFF'
6-256 RPTDA T A 7
SBI 4-5 INCRMENT X'0000'-X'7FFF' 2,3
SCFL 4 LID X'00'-X'FE' 2
SEC 5
10
1 1
12
13
14-17
COLSPCE
COLSIZE1
COLSIZE2
COLSIZE3
COLSIZE4
COL V ALUE
X'01', X'04', X'06'
X'08', X'40'
X'01'-X'08', X'10'
X'00'-X'08'
X'00'-X'08'
X'00'-X'08'
Full range allowed by
the color space
PT4 Subset

<!-- Page 179 -->

PTOCA Reference 161
Control Sequence Offset (Unchained) Parameter PT4 Range Notes
SIA 4-5 ADJSTMNT X'0000'-X'7FFF' 2,3
6 DIRCTION X'00'-X'01' 2
SIM 4-5 DSPLCMNT X'0000'-X'7FFF' 2,3
STC 4-5 FRGCOLOR X'0000'-X'0010', X'FF00'-X'FF08',
X'FFFF'
2,4
6 PRECSION X'00'-X'01' 2,8
ST O 4-5 IORNTION X'0000', X'2D00', X'5A00', X'8700' 2
6-7 BORNTION X'0000', X'2D00', X'5A00', X'8700' 2
SVI 4-5 INCRMENT X'0000'-X'7FFF' 2,3
TBM 4 DIRCTION X'00'-X'03' 2
5 PRECSION X'00'-X'01' 2
6-7 INCRMENT X'0000'-X'7FFF' 2,3
TRN 4-256 TRNDA T A 7
UCT 9
USC 4 BYPSIDEN X'00'-X'0E' 2
Notes:
1. This parameter is a signed binary number that may be positive or negative. Negative numbers are
expressed in twos-complement form.
2. The default indicator is allowed, meaning obtain a value from the hierarchy .
3. The range values shown assume a measurement unit of 1/1440 inch. That is, the measurement base is
assumed to be ten inches, and the X
p
-units per unit base and Y
p
-units per unit base are assumed to be
14,400. If a different measurement unit is used, the correct range values can be determined using the
conversion routine described in “Interpreting Ranges” on page 47.
4. For the PTOCA range, see “Set T ext Color (STC)” on page 102. The PT4 range is the full Standard OCA
Color V alue T able found in the MO:DCA Reference.
5. The Begin Line (BLN) control sequence has no parameters.
6. The No Operation (NOP) control sequence may contain any data that does not exceed the field length.
7. The T ransparent Data (TRN) and Repeat String (RPS) control sequences may contain any data that does
not exceed the field length. However , the data is presented as a character string.
8. The STC PRECSION parameter has been retired; see “Retired Parameters” on page 177. New PTOCA
generators should not specify this parameter and new receivers should ignore it.
9. The UCT must be chained to a GAR or GOR and is not rendered; all parameters are ignored.
PT4 Subset

<!-- Page 180 -->

162 PTOCA Reference
General Requirements for Compliance
In claiming support as a PTOCA receiver , a product is stating that it has identified the subsets sufficient for its
needs, and has implemented all mandatory and optional control sequences, parameters, and ranges within
those subsets, including support of the PTOCA default values specified for those control sequences and
parameters. A receiver may also support additional function beyond the PTOCA subset that it claims to
support.
In claiming support as a PTOCA generator , a product is stating that it has identified which subset is suffic ient
for its needs, and that the Presentation Text object content it produces is limited to the control sequences and
parameters defined in that subset. Additionally , it is stating that it has not violated the syntactic, semantic, and
pragmatic restrictions specified in PTOCA. There is no requirement that any control sequence or parameter
must appear in the object.
A receiver need not check the Presentation Text object for compliance to the PTOCA subset beyond what the
receiver requires for the processing that represents its function. A printer , for example, normally checks all the
control sequences, parameters, and ranges within the object it is presenting. But a transform product might
only need to check the structured field introducers. A receiver may optionally provide warnings if it detects non-
compliance.
General Requirements for Compliance

<!-- Page 181 -->

Copyright © AFP Consortium 1997, 2025 163
