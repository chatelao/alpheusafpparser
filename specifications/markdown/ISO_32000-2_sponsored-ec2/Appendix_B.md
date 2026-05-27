Annex B (informative) Operators in Type 4 Functions

## B.1 General

This annex summarises the PostScript language operators that may appear in a Type 4 function, as discussed in 7.10.5, "Type 4 (PostScript calculator) functions". For details on these operators, see the PostScript Language Reference, Third Edition.

## B.2 Arithmetic operators

| num1 num2 | add | sum | Return num1 plus num2 |  |
| --- | --- | --- | --- | --- |
| num1 num2 | sub | difference | Return num1 minus num2 |  |
| num1 num2 | mul | product | Return num1 times num2 |  |
| num1 num2 | div | quotient | Return num1 divided by num2 |  |
| int1 int2 | idiv | quotient | Return int1 divided by int2 as an integer |  |
| int1 int2 | mod | remainder | Return remainder after dividing int1 by int2 |  |
| num1 | neg | num2 | Return negative of num1 |  |
| num1 | abs | num2 | Return absolute value of num1 |  |
| num1 | ceiling | num2 | Return ceiling of num1 |  |
| num1 | floor | num2 | Return floor of num1 |  |
| num1 | round | num2 | Round num1 to nearest integer |  |
| num1 | truncate | num2 | Remove fractional part of num1 |  |
| num | sqrt | real | Return square root of num |  |
| angle | sin | real | Return sine of angle degrees |  |
| angle | cos | real | Return cosine of angle degrees |  |
| num | den | atan | angle | Return arc tangent of num /den in degrees |
| base | exponent | exp | real | Raise base to exponent power |
| num | ln | real | Return natural logarithm (base e) |  |
| num | log | real | Return common logarithm (base 10) |  |
| num | cvi | int | Convert to integer |  |
| num | cvr | real | Convert to real |  |


## B.3 Relational, boolean, and bitwise operators

| any1  any2 | eq | bool | Test equal |
| --- | --- | --- | --- |
| any1  any2 | ne | bool | Test not equal |
| num1  num2 | gt | bool | Test greater than |
| num1  num2 | ge | bool | Test greater than or equal |
| num1  num2 | lt | bool | Test less than |
| num1  num2 | le | bool | Test less than or equal |
| bool1 | int1  bool2 | | and | bool3 | int3 | Perform logical | bitwise and |

int2

bool1 | int1 bool2 | or bool3 | int3 Perform logical | bitwise inclusive or int2

bool1 | int1 bool2 | xor bool3 | int3 Perform logical | bitwise exclusive or int2

| bool1 | int1 | not | bool2 | int2 | Perform logical | bitwise not |
| --- | --- | --- | --- |
| int1  shift | bitshift | int2 | Perform bitwise shift of int1 (positive is left) |
| – | true | true | Return boolean value true |
| – | false | false | Return boolean value false |

## B.4 Conditional operators

| bool { expr } | if | – | Execute expr if bool is true |
| --- | --- | --- | --- |
| bool { expr1 }  { expr2 } | ifelse | – | Execute expr1 if bool is true, expr2 if false |

## B.5 Stack operators

| any | pop | – | Discard top element |  |
| --- | --- | --- | --- | --- |
| any1 | any2 | exch | any2  any1 | Exchange top two elements |
| any | dup | any any | Duplicate top element |  |
| any1 … anyn  n | copy | any1 … anyn  any1 … anyn | Duplicate top n elements |  |
| anyn … any0  n | index | anyn … any0  anyn | Duplicate arbitrary element |  |
| anyn-1 … any0  n j | roll | any(j-1) mod n … any0  anyn … anyj mod n | Roll n elements up j times |  |


