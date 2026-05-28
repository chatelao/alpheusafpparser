# Annex D (normative) Character sets and encodings

## D.1 General

This annex lists the character sets and encodings that shall be predefined in any PDF processor. Simple fonts, encompassing Latin text and some symbols, are described here. See 9.7.5.2, "Predefined CMaps" for a list of predefined CMaps for CID-keyed fonts.

“Table D.1 — Latin-text encodings” describes Latin-text encodings.

D.2, "Latin character set and encodings" describes the entire character set for the Standard Latin-text fonts. For each named character, an octal character code is defined for four different encodings: StandardEncoding, MacRomanEncoding, WinAnsiEncoding, and PDFDocEncoding (see "Table D.1 — Latin-text encodings"). Unencoded characters are indicated by a dash (—).

All characters listed in D.2, "Latin character set and encodings" shall be supported for the Times, Helvetica, and Courier font families, as listed in 9.6.2.2, "Standard Type 1 fonts (standard 14 fonts) (PDF 1.0-1.7)" by a PDF processor that supports PDF 1.0 to 1.7.

D.4, "Expert set and MacExpert encoding" describes the "expert" character set, which contains additional characters useful for sophisticated typography, such as small capitals, ligatures, and fractions. For each named character, an octal character code is given in MacExpertEncoding.

> **NOTE** The built-in encoding in an expert font program can be different from MacExpertEncoding.
D.5, "Symbol set and encoding" and D.6, "ZapfDingbats set and encoding" describe the character sets and built-in encodings for the Symbol and ZapfDingbats (ITC Zapf Dingbats) font programs, which belong to the standard 14 predefined fonts. These fonts have built-in encodings that are unique to each font. The characters for ZapfDingbats are ordered by code instead of by name, since the names in that font are meaningless.

Table D.1 — Latin-text encodings

| Encoding | Description |
| --- | --- |
| StandardEncoding | Standard Latin-text encoding. This is the built-in encoding defined in Type 1 Latin-text font programs (but generally not in TrueType font programs). PDF processors shall not have a predefined encoding named StandardEncoding. However, it is necessary to describe this encoding, since a font’s built-in encoding can be used as the base encoding from which differences may be specified in an encoding dictionary. |
| MacRomanEncoding | Mac OS standard encoding for Latin text in Western writing systems. PDF processors shall have a predefined encoding named MacRomanEncoding that may be used with both Type 1 and TrueType fonts. |


| Encoding | Description |
| --- | --- |
| WinAnsiEncoding | Windows Code Page 1252, often called the "Windows ANSI" encoding. This is the standard Microsoft WindowsTM specific encoding for Latin text in Western writing systems. PDF processors shall have a predefined encoding named WinAnsiEncoding that may be used with both Type 1 and TrueType fonts. |
| PDFDocEncoding | Encoding for text strings in a PDF document outside the document’s content streams. This is one of two encodings (the other being Unicode) that may be used to represent text strings; see 7.9.2.2, "Text string type". PDF does not have a predefined encoding named PDFDocEncoding; it is not customary to use this encoding to show text from fonts. |
| MacExpertEncoding | An encoding for use with expert fonts — ones containing the expert character set. PDF processors shall have a predefined encoding named MacExpertEncoding. Despite its name, it is not a platform-specific encoding; however, only certain fonts have the appropriate character set for use with this encoding. No such fonts are among the standard 14 predefined fonts. |

## D.2 Latin character set and encodings

Table D.2 — Latin character set and encodings

| CHAR | NAME | CHAR CODE (OCTAL) | CHAR | NAME | CHAR CODE (OCTAL) |  |  |
| --- | --- | --- | --- | --- | --- | --- | --- |
| STD | MAC | WIN | PDF | STD | MAC | WIN | PDF |

# A A 101 101 101 101 Œ OE 352 316 214 226

| Æ | AE | 341 | 256 | 306 | 306 | Ó | Oacute | — | 356 | 323 | 323 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Á | Aacute | — | 347 | 301 | 301 | Ô | Ocircumflex | — | 357 | 324 | 324 |
| Â | Acircumflex | — | 345 | 302 | 302 | Ö | Odieresis | — | 205 | 326 | 326 |
| Ä | Adieresis | — | 200 | 304 | 304 | Ò | Ograve | — | 361 | 322 | 322 |
| À | Agrave | — | 313 | 300 | 300 | Ø | Oslash | 351 | 257 | 330 | 330 |
| Å | Aring | — | 201 | 305 | 305 | Õ | Otilde | — | 315 | 325 | 325 |
| Ã | Atilde | — | 314 | 303 | 303 | P | P | 120 | 120 | 120 | 120 |

# B B 102 102 102 102 Q Q 121 121 121 121

# C C 103 103 103 103 R R 122 122 122 122

Ç Ccedilla — 202 307 307 S S 123 123 123 123

# D D 104 104 104 104 Š Scaron — — 212 227

# E E 105 105 105 105 T T 124 124 124 124

É Eacute — 203 311 311 Þ Thorn — — 336 336


| CHAR | NAME | CHAR CODE (OCTAL) | CHAR | NAME | CHAR CODE (OCTAL) |  |  |  |  |  |  |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| STD | MAC | WIN | PDF | STD | MAC | WIN | PDF |  |  |  |  |
| Ê | Ecircumflex | — | 346 | 312 | 312 | U | U | 125 | 125 | 125 | 125 |
| Ë | Edieresis | — | 350 | 313 | 313 | Ú | Uacute | — | 362 | 332 | 332 |
| È | Egrave | — | 351 | 310 | 310 | Û | Ucircumflex | — | 363 | 333 | 333 |
| Ð | Eth | — | — | 320 | 320 | Ü | Udieresis | — | 206 | 334 | 334 |
| € | Euro1 | — | — | 200 | 240 | Ù | Ugrave | — | 364 | 331 | 331 |

# F F 106 106 106 106 V V 126 126 126 126

# G G 107 107 107 107 W W 127 127 127 127

# H H 110 110 110 110 X X 130 130 130 130

# I I 111 111 111 111 Y Y 131 131 131 131

| Í | Iacute | — | 352 | 315 | 315 | Ý | Yacute | — | — | 335 | 335 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Î | Icircumflex | — | 353 | 316 | 316 | Ÿ | Ydieresis | — | 331 | 237 | 230 |
| Ï | Idieresis | — | 354 | 317 | 317 | Z | Z | 132 | 132 | 132 | 132 |
| Ì | Igrave | — | 355 | 314 | 314 | Ž | Zcaron2 | — | — | 216 | 231 |

# J J 112 112 112 112 a a 141 141 141 141

# K K 113 113 113 113 á aacute — 207 341 341

# L L 114 114 114 114 â acircumflex — 211 342 342

Ł Lslash 350 — — 225 ´ acute 302 253 264 264

# M M 115 115 115 115 ä adieresis — 212 344 344

# N N 116 116 116 116 æ ae 361 276 346 346

Ñ Ntilde — 204 321 321 à agrave — 210 340 340

# O O 117 117 117 117 & ampersand 046 046 046 046

| å | aring | — | 214 | 345 | 345 | ð | eth | — | — | 360 | 360 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| ^ | asciicircum | 136 | 136 | 136 | 136 | ! | exclam | 041 | 041 | 041 | 041 |
| ~ | asciitilde | 176 | 176 | 176 | 176 | ¡ | exclamdown | 241 | 301 | 241 | 241 |

* asterisk 052 052 052 052 f f 146 146 146 146

| @ | at | 100 | 100 | 100 | 100 | fi | fi | 256 | 336 | — | 223 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| ã | atilde | — | 213 | 343 | 343 | 5 | five | 065 | 065 | 065 | 065 |


| CHAR | NAME | CHAR CODE (OCTAL) | CHAR | NAME | CHAR CODE (OCTAL) |  |  |  |  |  |  |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| STD | MAC | WIN | PDF | STD | MAC | WIN | PDF |  |  |  |  |
| b | b | 142 | 142 | 142 | 142 | fl | fl | 257 | 337 | — | 224 |
| \ | backslash | 134 | 134 | 134 | 134 | ƒ | florin | 246 | 304 | 203 | 206 |
| | | bar | 174 | 174 | 174 | 174 | 4 | four | 064 | 064 | 064 | 064 |
| { | braceleft | 173 | 173 | 173 | 173 | ⁄ | fraction | 244 | 332 | — | 207 |
| } | braceright | 175 | 175 | 175 | 175 | g | g | 147 | 147 | 147 | 147 |
| [ | bracketleft | 133 | 133 | 133 | 133 | ß | germandbls | 373 | 247 | 337 | 337 |
| ] | bracketright | 135 | 135 | 135 | 135 | ` | grave | 301 | 140 | 140 | 140 |
| ˘ | breve | 306 | 371 | — | 030 | > | greater | 076 | 076 | 076 | 076 |
| ¦ | brokenbar | — | — | 246 | 246 | « | guillemotleft4 | 253 | 307 | 253 | 253 |

• bullet3 267 245 225 200 » guillemotright4 273 310 273 273

| c | c | 143 | 143 | 143 | 143 | ‹ | guilsinglleft | 254 | 334 | 213 | 210 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| ˇ | caron | 317 | 377 | — | 031 | › | guilsinglright | 255 | 335 | 233 | 211 |
| ç | ccedilla | — | 215 | 347 | 347 | h | h | 150 | 150 | 150 | 150 |
| ¸ | cedilla | 313 | 374 | 270 | 270 | ˝ | hungarumlaut | 315 | 375 | — | 034 |
| ¢ | cent | 242 | 242 | 242 | 242 | - | hyphen5 | 055 | 055 | 055 | 055 |
| ˆ | circumflex | 303 | 366 | 210 | 032 | i | i | 151 | 151 | 151 | 151 |
| : | colon | 072 | 072 | 072 | 072 | í | iacute | — | 222 | 355 | 355 |
| , | comma | 054 | 054 | 054 | 054 | î | icircumflex | — | 224 | 356 | 356 |
| © | copyright | — | 251 | 251 | 251 | ï | idieresis | — | 225 | 357 | 357 |
| ¤ | currency1 | 250 | 333 | 244 | 244 | ì | igrave | — | 223 | 354 | 354 |
| d | d | 144 | 144 | 144 | 144 | j | j | 152 | 152 | 152 | 152 |
| † | dagger | 262 | 240 | 206 | 201 | k | k | 153 | 153 | 153 | 153 |
| ‡ | daggerdbl | 263 | 340 | 207 | 202 | l | l | 154 | 154 | 154 | 154 |
| ° | degree | — | 241 | 260 | 260 | < | less | 074 | 074 | 074 | 074 |
| ¨ | dieresis | 310 | 254 | 250 | 250 | ¬ | logicalnot | — | 302 | 254 | 254 |
| ÷ | divide | — | 326 | 367 | 367 | ł | lslash | 370 | — | — | 233 |
| $ | dollar | 044 | 044 | 044 | 044 | m | m | 155 | 155 | 155 | 155 |


| CHAR | NAME | CHAR CODE (OCTAL) | CHAR | NAME | CHAR CODE (OCTAL) |  |  |  |  |  |  |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| STD | MAC | WIN | PDF | STD | MAC | WIN | PDF |  |  |  |  |
| ˙ | dotaccent | 307 | 372 | — | 033 | ¯ | macron | 305 | 370 | 257 | 257 |
| ı | dotlessi | 365 | 365 | — | 232 | − | minus | — | — | — | 212 |
| e | e | 145 | 145 | 145 | 145 | µ | mu | — | 265 | 265 | 265 |
| é | eacute | — | 216 | 351 | 351 | × | multiply | — | — | 327 | 327 |
| ê | ecircumflex | — | 220 | 352 | 352 | n | n | 156 | 156 | 156 | 156 |
| ë | edieresis | — | 221 | 353 | 353 | 9 | nine | 071 | 071 | 071 | 071 |
| è | egrave | — | 217 | 350 | 350 | ñ | ntilde | — | 226 | 361 | 361 |
| 8 | eight | 070 | 070 | 070 | 070 | # | numbersign | 043 | 043 | 043 | 043 |
| … | ellipsis | 274 | 311 | 205 | 203 | o | o | 157 | 157 | 157 | 157 |
| — | emdash | 320 | 321 | 227 | 204 | ó | oacute | — | 227 | 363 | 363 |
| – | endash | 261 | 320 | 226 | 205 | ô | ocircumflex | — | 231 | 364 | 364 |
| = | equal | 075 | 075 | 075 | 075 | ö | odieresis | — | 232 | 366 | 366 |
| œ | oe | 372 | 317 | 234 | 234 | s | s | 163 | 163 | 163 | 163 |
| ˛ | ogonek | 316 | 376 | — | 035 | š | scaron | — | — | 232 | 235 |
| ò | ograve | — | 230 | 362 | 362 | § | section | 247 | 244 | 247 | 247 |
| 1 | one | 061 | 061 | 061 | 061 | ; | semicolon | 073 | 073 | 073 | 073 |
| ½ | onehalf | — | — | 275 | 275 | 7 | seven | 067 | 067 | 067 | 067 |
| ¼ | onequarter | — | — | 274 | 274 | 6 | six | 066 | 066 | 066 | 066 |
| ¹ | onesuperior | — | — | 271 | 271 | / | slash | 057 | 057 | 057 | 057 |
| ª | ordfeminine | 343 | 273 | 252 | 252 | space6 | 040 | 040 | 040 | 040 |  |
| º | ordmasculine | 353 | 274 | 272 | 272 | £ | sterling | 243 | 243 | 243 | 243 |
| ø | oslash | 371 | 277 | 370 | 370 | t | t | 164 | 164 | 164 | 164 |
| õ | otilde | — | 233 | 365 | 365 | þ | thorn | — | — | 376 | 376 |
| p | p | 160 | 160 | 160 | 160 | 3 | three | 063 | 063 | 063 | 063 |
| ¶ | paragraph | 266 | 246 | 266 | 266 | ¾ | threequarters | — | — | 276 | 276 |
| ( | parenleft | 050 | 050 | 050 | 050 | ³ | threesuperior | — | — | 263 | 263 |
| ) | parenright | 051 | 051 | 051 | 051 | ˜ | tilde | 304 | 367 | 230 | 037 |


| CHAR | NAME | CHAR CODE (OCTAL) | CHAR | NAME | CHAR CODE (OCTAL) |  |  |  |  |  |  |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| STD | MAC | WIN | PDF | STD | MAC | WIN | PDF |  |  |  |  |
| % | percent | 045 | 045 | 045 | 045 | ™ | trademark | — | 252 | 231 | 222 |
| . | period | 056 | 056 | 056 | 056 | 2 | two | 062 | 062 | 062 | 062 |
| · | periodcentered | 264 | 341 | 267 | 267 | ² | twosuperior | — | — | 262 | 262 |
| ‰ | perthousand | 275 | 344 | 211 | 213 | u | u | 165 | 165 | 165 | 165 |
| + | plus | 053 | 053 | 053 | 053 | ú | uacute | — | 234 | 372 | 372 |
| ± | plusminus | — | 261 | 261 | 261 | û | ucircumflex | — | 236 | 373 | 373 |
| q | q | 161 | 161 | 161 | 161 | ü | udieresis | — | 237 | 374 | 374 |
| ? | question | 077 | 077 | 077 | 077 | ù | ugrave | — | 235 | 371 | 371 |
| ¿ | questiondown | 277 | 300 | 277 | 277 | _ | underscore | 137 | 137 | 137 | 137 |
| " | quotedbl | 042 | 042 | 042 | 042 | v | v | 166 | 166 | 166 | 166 |
| „ | quotedblbase | 271 | 343 | 204 | 214 | w | w | 167 | 167 | 167 | 167 |
| " | quotedblleft | 252 | 322 | 223 | 215 | x | x | 170 | 170 | 170 | 170 |
| " | quotedblright | 272 | 323 | 224 | 216 | y | y | 171 | 171 | 171 | 171 |
| ‘ | quoteleft | 140 | 324 | 221 | 217 | ý | yacute | — | — | 375 | 375 |
| ’ | quoteright | 047 | 325 | 222 | 220 | ÿ | ydieresis | — | 330 | 377 | 377 |
| ‚ | quotesinglbase | 270 | 342 | 202 | 221 | ¥ | yen | 245 | 264 | 245 | 245 |
| ' | quotesingle | 251 | 047 | 047 | 047 | z | z | 172 | 172 | 172 | 172 |
| r | r | 162 | 162 | 162 | 162 | ž | zcaron2 | — | — | 236 | 236 |
| ® | registered | — | 250 | 256 | 256 | 0 | zero | 060 | 060 | 060 | 060 |
| ˚ | ring | 312 | 373 | — | 036 |  |  |  |  |  |  |

| 1. | In PDF 1.3, the euro character was added to the Adobe standard Latin character set. It is encoded as 200 in WinAnsiEncoding and 240 in PDFDocEncoding, assigning codes that were previously unused. Apple changed the Mac OS Latin-text encoding for code 333 from the currency character to the euro character. However, this incompatible change has not been reflected in PDF’s MacRomanEncoding, which continues to map code 333 to currency. If the euro character is desired, an encoding dictionary can be used to specify this single difference from MacRomanEncoding. |
| --- | --- |
| 2. | In PDF 1.3, the existing Zcaron and zcaron characters were added to WinAnsiEncoding as the previously unused codes 216 and 236. |


| 3. | In WinAnsiEncoding, all unused codes greater than 40 map to the bullet character. However, only code 225 is specifically assigned to the bullet character; other codes are subject to future reassignment. |
| --- | --- |
| 4. | The character names guillemotleft and guillemotright are misspelled. The correct spelling for this punctuation character is guillemet. However, the misspelled names are the ones actually used in the fonts and encodings containing these characters. |
| 5. | The hyphen (U+002D) character is also encoded as 255 (octal) in WinAnsiEncoding. Windows Code Page 1252 associates this character code with the soft hyphen (U+00AD) character. If the PDF producer intends to map this character code to the "softhyphen" character from the Adobe Glyph List, this may be specified using a Differences array in the encoding dictionary as shown in the example below. |
| 6. | The space (U+0020) character is also encoded as 312 (octal) in MacRomanEncoding and as 240 (octal) in WinAnsiEncoding. Windows Code Page 1252 associates this character code with the non-breaking space (U+00A0) character. If the PDF producer intends to map this character code to the "nonbreakingspace" character from the Adobe Glyph List, this may be specified using a Differences array in the encoding dictionary as shown in the example below. |

> **EXAMPLE** Encoding dictionaries use decimal numbers for character codes, instead of octal values.

<< /Type /Encoding /BaseEncoding /WinAnsiEncoding /Differences [ 160 /nonbreakingspace 173 /softhyphen ] >> NOTE         This document clarifies list items 5 and 6 above (2020).

## D.3 PDFDocEncoding character set

The column titled Notes uses the following abbreviations:

• U Undefined code point in PDFDocEncoding •    SR       Unicode codepoint that may require special representation in XML in some contexts.

Table D.3 — PDFDocEncoding character set

| Character | Dec | Hex | Octal | Unicode | Unicode character name or | Notes (alternative alias) |
| --- | --- | --- | --- | --- | --- | --- |
| ^@ | 0 | 0x00 | 0000 | U+0000 | (NULL) | U |
| ^A | 1 | 0x01 | 0001 | U+0001 | (START OF HEADING) | U |
| ^B | 2 | 0x02 | 0002 | U+0002 | (START OF TEXT) | U |
| ^C | 3 | 0x03 | 0003 | U+0003 | (END OF TEXT) | U |
| ^D | 4 | 0x04 | 0004 | U+0004 | (END OF TEXT) | U |
| ^E | 5 | 0x05 | 0005 | U+0005 | (END OF TRANSMISSION) | U |
| ^F | 6 | 0x06 | 0006 | U+0006 | (ACKNOWLEDGE) | U |


| Character | Dec | Hex | Octal | Unicode | Unicode character name or | Notes (alternative alias) |
| --- | --- | --- | --- | --- | --- | --- |
| ^G | 7 | 0x07 | 0007 | U+0007 | (BELL) | U |
| ^H | 8 | 0x08 | 0010 | U+0008 | (BACKSPACE) | U |
| ^I | 9 | 0x09 | 0011 | U+0009 | (CHARACTER TABULATION) | SR |
| ^J | 10 | 0x0a | 0012 | U+000A | (LINE FEED) | SR |
| ^K | 11 | 0x0b | 0013 | U+000B | (LINE TABULATION) | U |
| ^L | 12 | 0x0c | 0014 | U+000C | (FORM FEED) | U |
| ^M | 13 | 0x0d | 0015 | U+000D | (CARRIAGE RETURN) | SR |
| ^N | 14 | 0x0e | 0016 | U+000E | (SHIFT OUT) | U |
| ^O | 15 | 0x0f | 0017 | U+000F | (SHIFT IN) | U |
| ^P | 16 | 0x10 | 0020 | U+0010 | (DATA LINK ESCAPE) | U |
| ^Q | 17 | 0x11 | 0021 | U+0011 | (DEVICE CONTROL ONE) | U |
| ^R | 18 | 0x12 | 0022 | U+0012 | (DEVICE CONTROL TWO) | U |
| ^S | 19 | 0x13 | 0023 | U+0013 | (DEVICE CONTROL THREE) | U |
| ^T | 20 | 0x14 | 0024 | U+0014 | (DEVICE CONTROL FOUR) | U |
| ^U | 21 | 0x15 | 0025 | U+0015 | (NEGATIVE ACKNOWLEDGE) | U |
| ^V | 22 | 0x16 | 0026 | U+0017 | (SYNCRONOUS IDLE) | U |
| ^W | 23 | 0x17 | 0027 | U+0017 | (END OF TRANSMISSION BLOCK) | U |
| u | 24 | 0x18 | 0030 | U+02D8 | BREVE |  |
| v | 25 | 0x19 | 0031 | U+02C7 | CARON |  |
| ^ | 26 | 0x1a | 0032 | U+02C6 | MODIFIER LETTER CIRCUMFLEX ACCENT |  |
| · | 27 | 0x1b | 0033 | U+02D9 | DOT ABOVE |  |
| ” | 28 | 0x1c | 0034 | U+02DD | DOUBLE ACUTE ACCENT |  |
| , | 29 | 0x1d | 0035 | U+02DB | OGONEK |  |
| ° | 30 | 0x1e | 0036 | U+02DA | RING ABOVE |  |
| ~ | 31 | 0x1f | 0037 | U+02DC | SMALL TILDE |  |
| 32 | 0x20 | 0040 | U+0020 | SPACE (&#32;) |  |  |
| ! | 33 | 0x21 | 0041 | U+0021 | EXCLAMATION MARK | SR |


| Character | Dec | Hex | Octal | Unicode | Unicode character name or | Notes (alternative alias) |
| --- | --- | --- | --- | --- | --- | --- |
| " | 34 | 0x22 | 0042 | U+0022 | QUOTATION MARK (&quot;) | SR |
| # | 35 | 0x23 | 0043 | U+0023 | NUMBER SIGN |  |
| $ | 36 | 0x24 | 0044 | U+0024 | DOLLAR SIGN |  |
| % | 37 | 0x25 | 0045 | U+0025 | PERCENT SIGN |  |
| & | 38 | 0x26 | 0046 | U+0026 | AMPERSAND (&amp;) |  |
| ' | 39 | 0x27 | 0047 | U+0027 | APOSTROPHE (&apos;) |  |
| ( | 40 | 0x28 | 0050 | U+0028 | LEFT PARENTHESIS |  |
| ) | 41 | 0x29 | 0051 | U+0029 | RIGHT PARENTHESIS |  |

* 42 0x2a 0052 U+002A ASTERISK

| + | 43 | 0x2b | 0053 | U+002B | PLUS SIGN |
| --- | --- | --- | --- | --- | --- |
| , | 44 | 0x2c | 0054 | U+002C | COMMA |

- 45 0x2d 0055 U+002D HYPHEN-MINUS

| . | 46 | 0x2e | 0056 | U+002E | FULL STOP (period) |  |
| --- | --- | --- | --- | --- | --- | --- |
| / | 47 | 0x2f | 0057 | U+002F | SOLIDUS (slash) |  |
| 0 | 48 | 0x30 | 0060 | U+0030 | DIGIT ZERO |  |
| 1 | 49 | 0x31 | 0061 | U+0031 | DIGIT ONE |  |
| 2 | 50 | 0x32 | 0062 | U+0032 | DIGIT TWO |  |
| 3 | 51 | 0x33 | 0063 | U+0033 | DIGIT THREE |  |
| 4 | 52 | 0x34 | 0064 | U+0034 | DIGIT FOUR |  |
| 5 | 53 | 0x35 | 0065 | U+0035 | DIGIT FIVE |  |
| 6 | 54 | 0x36 | 0066 | U+0036 | DIGIT SIX |  |
| 7 | 55 | 0x37 | 0067 | U+0037 | DIGIT SEVEN |  |
| 8 | 56 | 0x38 | 0070 | U+0038 | DIGIT EIGJT |  |
| 9 | 57 | 0x39 | 0071 | U+0039 | DIGIT NINE |  |
| : | 58 | 0x3a | 0072 | U+003A | COLON |  |
| ; | 59 | 0x3b | 0073 | U+003B | SEMICOLON |  |
| < | 60 | 0x3c | 0074 | U+003C | LESS THAN SIGN (&lt;) | SR |


| Character | Dec | Hex | Octal | Unicode | Unicode character name or | Notes (alternative alias) |
| --- | --- | --- | --- | --- | --- | --- |
| = | 61 | 0x3d | 0075 | U+003D | EQUALS SIGN |  |
| > | 62 | 0x3e | 0076 | U+003E | GREATER THAN SIGN (&gt;) |  |
| ? | 63 | 0x3f | 0077 | U+003F | QUESTION MARK |  |
| @ | 64 | 0x40 | 0100 | U+0040 | COMMERCIAL AT |  |
| A | 65 | 0x41 | 0101 | U+0041 |  |  |
| B | 66 | 0x42 | 0102 | U+0042 |  |  |
| C | 67 | 0x43 | 0103 | U+0043 |  |  |
| D | 68 | 0x44 | 0104 | U+0044 |  |  |
| E | 69 | 0x45 | 0105 | U+0045 |  |  |
| F | 70 | 0x46 | 0106 | U+0046 |  |  |
| G | 71 | 0x47 | 0107 | U+0047 |  |  |
| H | 72 | 0x48 | 0110 | U+0048 |  |  |
| I | 73 | 0x49 | 0111 | U+0049 |  |  |
| J | 74 | 0x4a | 0112 | U+004A |  |  |
| K | 75 | 0x4b | 0113 | U+004B |  |  |
| L | 76 | 0x4c | 0114 | U+004C |  |  |
| M | 77 | 0x4d | 0115 | U+004D |  |  |
| N | 78 | 0x4e | 0116 | U+004E |  |  |
| O | 79 | 0x4f | 0117 | U+004F |  |  |
| P | 80 | 0x50 | 0120 | U+0050 |  |  |
| Q | 81 | 0x51 | 0121 | U+0051 |  |  |
| R | 82 | 0x52 | 0122 | U+0052 |  |  |
| S | 83 | 0x53 | 0123 | U+0053 |  |  |
| T | 84 | 0x54 | 0124 | U+0054 |  |  |
| U | 85 | 0x55 | 0125 | U+0055 |  |  |
| V | 86 | 0x56 | 0126 | U+0056 |  |  |
| W | 87 | 0x57 | 0127 | U+0057 |  |  |


| Character | Dec | Hex | Octal | Unicode | Unicode character name or | Notes (alternative alias) |
| --- | --- | --- | --- | --- | --- | --- |
| X | 88 | 0x58 | 0130 | U+0058 |  |  |
| Y | 89 | 0x59 | 0131 | U+0059 |  |  |
| Z | 90 | 0x5a | 0132 | U+005A |  |  |
| [ | 91 | 0x5b | 0133 | U+005B | LEFT SQUARE BRACKET |  |
| \ | 92 | 0x5c | 0134 | U+005C | REVERSE SOLIDUS (backslash) |  |
| ] | 93 | 0x5d | 0135 | U+005D | RIGHT SQUARE BRACKET |  |
| ^ | 94 | 0x5e | 0136 | U+005E | CIRCUMFLEX ACCENT (hat) |  |
| _ | 95 | 0x5f | 0137 | U+005F | LOW LINE (SPACING UNDERSCORE) |  |
| ` | 96 | 0x60 | 0140 | U+0060 | GRAVE ACCENT |  |
| a | 97 | 0x61 | 0141 | U+0061 |  |  |
| b | 98 | 0x62 | 0142 | U+0062 |  |  |
| c | 99 | 0x63 | 0143 | U+0063 |  |  |
| d | 100 | 0x64 | 0144 | U+0064 |  |  |
| e | 101 | 0x65 | 0145 | U+0065 |  |  |
| f | 102 | 0x66 | 0146 | U+0066 |  |  |
| g | 103 | 0x67 | 0147 | U+0067 |  |  |
| h | 104 | 0x68 | 0150 | U+0068 |  |  |
| i | 105 | 0x69 | 0151 | U+0069 |  |  |
| j | 106 | 0x6a | 0152 | U+006A |  |  |
| k | 107 | 0x6b | 0153 | U+006B |  |  |
| l | 108 | 0x6c | 0154 | U+006C |  |  |
| m | 109 | 0x6d | 0155 | U+006D |  |  |
| n | 110 | 0x6e | 0156 | U+006E |  |  |
| o | 111 | 0x6f | 0157 | U+006F |  |  |
| p | 112 | 0x70 | 0160 | U+0070 |  |  |
| q | 113 | 0x71 | 0161 | U+0071 |  |  |
| r | 114 | 0x72 | 0162 | U+0072 |  |  |


| Character | Dec | Hex | Octal | Unicode | Unicode character name or | Notes (alternative alias) |
| --- | --- | --- | --- | --- | --- | --- |
| s | 115 | 0x73 | 0163 | U+0073 |  |  |
| t | 116 | 0x74 | 0164 | U+0074 |  |  |
| u | 117 | 0x75 | 0165 | U+0075 |  |  |
| v | 118 | 0x76 | 0166 | U+0076 |  |  |
| w | 119 | 0x77 | 0167 | U+0077 |  |  |
| x | 120 | 0x78 | 0170 | U+0078 |  |  |
| y | 121 | 0x79 | 0171 | U+0079 |  |  |
| z | 122 | 0x7a | 0172 | U+007A |  |  |
| { | 123 | 0x7b | 0173 | U+007B | LEFT CURLY BRACKET |  |
| | | 124 | 0x7c | 0174 | U+007C | VERTICAL LINE |  |
| } | 125 | 0x7d | 0175 | U+007D | RIGHT CURLY BRACKET |  |
| ~ | 126 | 0x7e | 0176 | U+007E | TILDE |  |
| 127 | 0x7f | 0177 | Undefined | U |  |  |

• 128 0x80 0200 U+2022 BULLET

| † | 129 | 0x81 | 0201 | U+2020 | DAGGER |
| --- | --- | --- | --- | --- | --- |
| ‡ | 130 | 0x82 | 0202 | U+2021 | DOUBLE DAGGER |
| … | 131 | 0x83 | 0203 | U+2026 | HORIZONTAL ELLIPSIS |
| — | 132 | 0x84 | 0204 | U+2014 | EM DASH |
| – | 133 | 0x85 | 0205 | U+2013 | EN DASH |
| ƒ | 134 | 0x86 | 0206 | U+0192 |  |
| ⁄ | 135 | 0x87 | 0207 | U+2044 | FRACTION SLASH (solidus) |
| ‹ | 136 | 0x88 | 0210 | U+2039 | SINGLE LEFT-POINTING ANGLE QUOTATION MARK |
| › | 137 | 0x89 | 0211 | U+203A | SINGLE RIGHT-POINTING ANGLE QUOTATION MARK |
| Š | 138 | 0x8a | 0212 | U+2212 |  |
| ‰ | 139 | 0x8b | 0213 | U+2030 | PER MILLE SIGN |
| „ | 140 | 0x8c | 0214 | U+201E | DOUBLE LOW-9 QUOTATION MARK (quotedblbase) |


| Character | Dec | Hex | Octal | Unicode | Unicode character name or | Notes (alternative alias) |
| --- | --- | --- | --- | --- | --- | --- |
| “ | 141 | 0x8d | 0215 | U+201C | LEFT DOUBLE QUOTATION MARK (double quote left) |  |
| ” | 142 | 0x8e | 0216 | U+201D | RIGHT DOUBLE QUOTATION MARK (quotedblright) |  |
| ‘ | 143 | 0x8f | 0217 | U+2018 | LEFT SINGLE QUOTATION MARK (quoteleft) |  |
| ’ | 144 | 0x90 | 0220 | U+2019 | RIGHT SINGLE QUOTATION MARK (quoteright) |  |
| ‚ | 145 | 0x91 | 0221 | U+201A | SINGLE LOW-9 QUOTATION MARK (quotesinglbase) |  |
| ™ | 146 | 0x92 | 0222 | U+2122 | TRADE MARK SIGN |  |
| fi | 147 | 0x93 | 0223 | U+FB01 | LATIN SMALL LIGATURE FI |  |
| fl | 148 | 0x94 | 0224 | U+FB02 | LATIN SMALL LIGATURE FL |  |
| Ł | 149 | 0x95 | 0225 | U+0141 | LATIN CAPITAL LETTER L WITH STROKE |  |
| Œ | 150 | 0x96 | 0226 | U+0152 | LATIN CAPITAL LIGATURE OE |  |
| Š | 151 | 0x97 | 0227 | U+0160 | LATIN CAPITAL LETTER S WITH CARON |  |
| Ÿ | 152 | 0x98 | 0230 | U+0178 | LATIN CAPITAL LETTER Y WITH DIAERESIS |  |
| Ž | 153 | 0x99 | 0231 | U+017D | LATIN CAPITAL LETTER Z WITH CARON |  |
| ı | 154 | 0x9a | 0232 | U+0131 | LATIN SMALL LETTER DOTLESS I |  |
| ł | 155 | 0x9b | 0233 | U+0142 | LATIN SMALL LETTER L WITH STROKE |  |
| œ | 156 | 0x9c | 0234 | U+0153 | LATIN SMALL LIGATURE OE |  |
| š | 157 | 0x9d | 0235 | U+0161 | LATIN SMALL LETTER S WITH CARON |  |
| ž | 158 | 0x9e | 0236 | U+017E | LATIN SMALL LETTER Z WITH CARON |  |
| 159 | 0x9f | 0237 | Undefined |  |  |  |
| € | 160 | 0xa0 | 0240 | U+20AC | EURO SIGN |  |
| ¡ | 161 | 0xa1 | 0241 | U+00A1 | INVERTED EXCLAMATION MARK |  |
| ¢ | 162 | 0xa2 | 0242 | U+00A2 | CENT SIGN |  |


| Character | Dec | Hex | Octal | Unicode | Unicode character name or | Notes (alternative alias) |
| --- | --- | --- | --- | --- | --- | --- |
| £ | 163 | 0xa3 | 0243 | U+00A3 | POUND SIGN (sterling) |  |
| ¤ | 164 | 0xa4 | 0244 | U+00A4 | CURRENCY SIGN |  |
| ¥ | 165 | 0xa5 | 0245 | U+00A5 | YEN SIGN |  |
| ¦ | 166 | 0xa6 | 0246 | U+00A6 | BROKEN BAR |  |
| § | 167 | 0xa7 | 0247 | U+00A7 | SECTION SIGN |  |
| ¨ | 168 | 0xa8 | 0250 | U+00A8 | DIAERESIS |  |
| © | 169 | 0xa9 | 0251 | U+00A9 | COPYRIGHT SIGN |  |
| ª | 170 | 0xaa | 0252 | U+00AA | FEMININE ORDINAL INDICATOR |  |
| « | 171 | 0xab | 0253 | U+00AB | LEFT-POINTING DOUBLE ANGLE QUOTATION MARK |  |
| ¬ | 172 | 0xac | 0254 | U+00AC | NOT SIGN |  |
| 173 | 0xad | 0255 | Undefined |  |  |  |
| ® | 174 | 0xae | 0256 | U+00AE | REGISTERED SIGN |  |
| ¯ | 175 | 0xaf | 0257 | U+00AF | MACRON |  |
| ° | 176 | 0xb0 | 0260 | U+00B0 | DEGREE SIGN |  |
| ± | 177 | 0xb1 | 0261 | U+00B1 | PLUS-MINUS SIGN |  |
| ² | 178 | 0xb2 | 0262 | U+00B2 | SUPERSCRIPT TWO |  |
| ³ | 179 | 0xb3 | 0263 | U+00B3 | SUPERSCRIPT THREE |  |
| ´ | 180 | 0xb4 | 0264 | U+00B4 | ACUTE ACCENT |  |
| µ | 181 | 0xb5 | 0265 | U+00B5 | MICRO SIGN |  |
| ¶ | 182 | 0xb6 | 0266 | U+00B6 | PILCROW SIGN |  |
| · | 183 | 0xb7 | 0267 | U+00B7 | MIDDLE DOT |  |
| ¸ | 184 | 0xb8 | 0270 | U+00B8 | CEDILLA |  |
| 1 | 185 | 0xb9 | 0271 | U+00B9 | SUPERSCRIPT ONE |  |
| º | 186 | 0xba | 0272 | U+00BA | MASCULINE ORDINAL INDICATOR |  |
| » | 187 | 0xbb | 0273 | U+00BB | RIGHT-POINTING DOUBLE ANGLE QUOTATION MARK |  |
| ¼ | 188 | 0xbc | 0274 | U+00BC | VULGAR FRACTION ONE QUARTER |  |


| Character | Dec | Hex | Octal | Unicode | Unicode character name or | Notes (alternative alias) |
| --- | --- | --- | --- | --- | --- | --- |
| ½ | 189 | 0xbd | 0275 | U+00BD | VULGAR FRACTION ONE HALF |  |
| ¾ | 190 | 0xbe | 0276 | U+00BE | VULGAR FRACTION THREE QUARTERS |  |
| ¿ | 191 | 0xbf | 0277 | U+00BF | INVERTED QUESTION MARK |  |
| À | 192 | 0xc0 | 0300 | U+00C0 |  |  |
| Á | 193 | 0xc1 | 0301 | U+00C1 |  |  |
| Â | 194 | 0xc2 | 0302 | U+00C2 |  |  |
| Ã | 195 | 0xc3 | 0303 | U+00C3 |  |  |
| Ä | 196 | 0xc4 | 0304 | U+00C4 |  |  |
| Å | 197 | 0xc5 | 0305 | U+00C5 |  |  |
| Æ | 198 | 0xc6 | 0306 | U+00C6 |  |  |
| Ç | 199 | 0xc7 | 0307 | U+00C7 |  |  |
| È | 200 | 0xc8 | 0310 | U+00C8 |  |  |
| É | 201 | 0xc9 | 0311 | U+00C9 |  |  |
| Ê | 202 | 0xca | 0312 | U+00CA |  |  |
| Ë | 203 | 0xcb | 0313 | U+00CB |  |  |
| Ì | 204 | 0xcc | 0314 | U+00CC |  |  |
| Í | 205 | 0xcd | 0315 | U+00CD |  |  |
| Î | 206 | 0xce | 0316 | U+00CE |  |  |
| Ï | 207 | 0xcf | 0317 | U+00CF |  |  |
| Ð | 208 | 0xd0 | 0320 | U+00D0 |  |  |
| Ñ | 209 | 0xd1 | 0321 | U+00D1 |  |  |
| Ò | 210 | 0xd2 | 0322 | U+00D2 |  |  |
| Ó | 211 | 0xd3 | 0323 | U+00D3 |  |  |
| Ô | 212 | 0xd4 | 0324 | U+00D4 |  |  |
| Õ | 213 | 0xd5 | 0325 | U+00D5 |  |  |
| Ö | 214 | 0xd6 | 0326 | U+00D6 |  |  |
| × | 215 | 0xd7 | 0327 | U+00D7 |  |  |


| Character | Dec | Hex | Octal | Unicode | Unicode character name or | Notes (alternative alias) |
| --- | --- | --- | --- | --- | --- | --- |
| Ø | 216 | 0xd8 | 0330 | U+00D8 |  |  |
| Ù | 217 | 0xd9 | 0331 | U+00D9 |  |  |
| Ú | 218 | 0xda | 0332 | U+00DA |  |  |
| Û | 219 | 0xdb | 0333 | U+00DB |  |  |
| Ü | 220 | 0xdc | 0334 | U+00DC |  |  |
| Ý | 221 | 0xdd | 0335 | U+00DD |  |  |
| Þ | 222 | 0xde | 0336 | U+00DE |  |  |
| ß | 223 | 0xdf | 0337 | U+00DF |  |  |
| à | 224 | 0xe0 | 0340 | U+00E0 |  |  |
| á | 225 | 0xe1 | 0341 | U+00E1 |  |  |
| â | 226 | 0xe2 | 0342 | U+00E2 |  |  |
| ã | 227 | 0xe3 | 0343 | U+00E3 |  |  |
| ä | 228 | 0xe4 | 0344 | U+00E4 |  |  |
| å | 229 | 0xe5 | 0345 | U+00E5 |  |  |
| æ | 230 | 0xe6 | 0346 | U+00E6 |  |  |
| ç | 231 | 0xe7 | 0347 | U+00E7 |  |  |
| è | 232 | 0xe8 | 0350 | U+00E8 |  |  |
| é | 233 | 0xe9 | 0351 | U+00E9 |  |  |
| ê | 234 | 0xea | 0352 | U+00EA |  |  |
| ë | 235 | 0xeb | 0353 | U+00EB |  |  |
| ì | 236 | 0xec | 0354 | U+00EC |  |  |
| í | 237 | 0xed | 0355 | U+00ED |  |  |
| î | 238 | 0xee | 0356 | U+00EE |  |  |
| ï | 239 | 0xef | 0357 | U+00EF |  |  |
| ð | 240 | 0xf0 | 0360 | U+00F0 |  |  |
| ñ | 241 | 0xf1 | 0361 | U+00F1 |  |  |
| ò | 242 | 0xf2 | 0362 | U+00F2 |  |  |


| Character | Dec | Hex | Octal | Unicode | Unicode character name or | Notes (alternative alias) |
| --- | --- | --- | --- | --- | --- | --- |
| ó | 243 | 0xf3 | 0363 | U+00F3 |  |  |
| ô | 244 | 0xf4 | 0364 | U+00F4 |  |  |
| õ | 245 | 0xf5 | 0365 | U+00F5 |  |  |
| ö | 246 | 0xf6 | 0366 | U+00F6 |  |  |
| ÷ | 247 | 0xf7 | 0367 | U+00F7 |  |  |
| ø | 248 | 0xf8 | 0370 | U+00F8 |  |  |
| ù | 249 | 0xf9 | 0371 | U+00F9 |  |  |
| ú | 250 | 0xfa | 0372 | U+00FA |  |  |
| û | 251 | 0xfb | 0373 | U+00FB |  |  |
| ü | 252 | 0xfc | 0374 | U+00FC |  |  |
| ý | 253 | 0xfd | 0375 | U+00FD |  |  |
| þ | 254 | 0xfe | 0376 | U+00FE |  |  |
| ÿ | 255 | 0xff | 0377 | U+00FF |  |  |

## D.4 Expert set and MacExpert encoding

Table D.4 — Expert set and MacExpert encoding

| CHAR | NAME | CODE | CHAR | NAME | CODE |
| --- | --- | --- | --- | --- | --- |
| ᴁ | AEsmall | 276 | ᴊ | Jsmall |  |

Á Aacutesmall 207 ᴋ Ksmall

| Â | Acircumflexsmall | 211 | ᴌ | Lslashsmall |
| --- | --- | --- | --- | --- |
| ´ | Acutesmall | 047 | ʟ | Lsmall |

Ä Adieresissmall 212 ¯ Macronsmall

À Agravesmall 210 ᴍ Msmall

| Å | Aringsmall | 214 | ɴ | Nsmall |
| --- | --- | --- | --- | --- |
| ᴀ | Asmall | 141 | Ñ | Ntildesmall |

| Ã | Atildesmall | 213 | ɶ | OEsmall |
| --- | --- | --- | --- | --- |
| ˘ | Brevesmall | 363 | Ó | Oacutesmall |


| CHAR | NAME | CODE | CHAR | NAME | CODE |
| --- | --- | --- | --- | --- | --- |
| ʙ | Bsmall | 142 | Ô | Ocircumflexsmall | 231 |
| ˇ | Caronsmall | 256 | Ö | Odieresissmall |  |

| Ç | Ccedillasmall | 215 | ˛ | Ogoneksmall |
| --- | --- | --- | --- | --- |
| ¸ | Cedillasmall | 311 | ò | Ogravesmall |
| ˆ | Circumflexsmall | 136 | ø | Oslashsmall |
| ᴄ | Csmall | 143 | ᴏ | Osmall |
| ¨ | Dieresissmall | 254 | Õ | Otildesmall |
| ˙ | Dotaccentsmall | 372 | ᴘ | Psmall |
| ᴅ | Dsmall | 144 | Q | Qsmall |

É Eacutesmall 216 ˚ Ringsmall

Ê Ecircumflexsmall 220 R Rsmall

Ë Edieresissmall 221 Š Scaronsmall

| È | Egravesmall | 217 | S | Ssmall |
| --- | --- | --- | --- | --- |
| ᴇ | Esmall | 145 | þ | Thornsmall |
| ᴆ | Ethsmall | 104 | ˜ | Tildesmall |

# F Fsmall 146 ᴛ Tsmall

` Gravesmall 140 Ú Uacutesmall

# G Gsmall 147 Û Ucircumflexsmall 236

| ʜ | Hsmall | 150 | Ü | Udieresissmall |
| --- | --- | --- | --- | --- |
| ˝ | Hungarumlautsmall | 042 | Ù | Ugravesmall |

Í Iacutesmall 222 ᴜ Usmall

Î Icircumflexsmall 224 ᴠ Vsmall

Ï Idieresissmall 225 ᴡ Wsmall

Ì Igravesmall 223 X Xsmall

# I Ismall 151 Ý Yacutesmall

Ÿ Ydieresissmall 330 4 fouroldstyle

Y Ysmall 171 ⁴ foursuperior

Ž Zcaronsmall 275 ⁄ fraction


| CHAR | NAME | CODE | CHAR | NAME | CODE |
| --- | --- | --- | --- | --- | --- |
| z | Zsmall | 172 | - | hyphen |  |

& ampersandsmall 046 - hypheninferior

a asuperior 201 - hyphensuperior

b bsuperior 365 i isuperior

| ¢ | centinferior | 251 | l | lsuperior |
| --- | --- | --- | --- | --- |
| ¢ | centoldstyle | 043 | m | msuperior |

| ¢ | centsuperior | 202 | ₉ | nineinferior |
| --- | --- | --- | --- | --- |
| : | colon | 072 | 9 | nineoldstyle |
| ₡ | colonmonetary | 173 | ⁹ | ninesuperior |
| , | comma | 054 | ⁿ | nsuperior |

, commainferior 262 ․ onedotenleader

, commasuperior 370 ⅛ oneeighth

| $ | dollarinferior | 266 | 1 | onefitted |
| --- | --- | --- | --- | --- |
| $ | dollaroldstyle | 044 | ½ | onehalf |

| $ | dollarsuperior | 045 | ₁ | oneinferior |  |
| --- | --- | --- | --- | --- | --- |
| d | dsuperior | 353 | 1 | oneoldstyle |  |
| ₈ | eightinferior | 245 | ¼ | onequarter |  |
| 8 | eightoldstyle | 070 | ¹ | onesuperior |  |
| ⁸ | eightsuperior | 241 | ⅓ | onethird |  |
| e | esuperior | 344 | O | osuperior |  |
| ¡ | exclamdownsmall | 326 | ₍ | parenleftinferior | 133 |
| ! | exclamsmall | 041 | ⁽ | parenleftsuperior | 050 |
| ﬀ | ff | 126 | ₎ | parenrightinferior | 135 |
| ﬃ | ffi | 131 | ⁾ | parenrightsuperior | 051 |
| ﬄ | ffl | 132 | . | period |  |
| ﬁ | fi | 127 | . | periodinferior |  |
| ‒ | figuredash | 320 | . | periodsuperior |  |
| ⅝ | fiveeighths | 114 | ¿ | questiondownsmall | 300 |


| CHAR | NAME | CODE | CHAR | NAME | CODE |
| --- | --- | --- | --- | --- | --- |
| ₅ | fiveinferior | 260 | ? | questionsmall |  |
| 5 | fiveoldstyle | 065 | r | rsuperior |  |
| ⁵ | fivesuperior | 336 | Rp | rupiah |  |
| ﬂ | fl | 130 | ; | semicolon |  |
| ₄ | fourinferior | 242 | ⅞ | seveneighths |  |
| ₇ | seveninferior | 246 | — | threequartersemdash | 075 |
| 7 | sevenoldstyle | 067 | ³ | threesuperior |  |
| ⁷ | sevensuperior | 340 | t | tsuperior |  |
| ₆ | sixinferior | 244 | ‥ | twodotenleader |  |
| 6 | sixoldstyle | 066 | ₂ | twoinferior |  |
| ⁶ | sixsuperior | 337 | 2 | twooldstyle |  |
| space | 040 | ² | twosuperior |  |  |

| S | ssuperior | 352 | ⅔ | twothirds |
| --- | --- | --- | --- | --- |
| ⅜ | threeeighths | 113 | ₀ | zeroinferior |
| ₃ | threeinferior | 243 | 0 | zerooldstyle |
| 3 | threeoldstyle | 063 | ⁰ | zerosuperior |
| ¾ | threequarters | 111 |  |  |

## D.5 Symbol set and encoding

Table D.5 — Symbol set and encoding

| CHAR | NAME | CODE | CHAR | NAME | CODE |
| --- | --- | --- | --- | --- | --- |
| Α | Alpha | 101 | ↔ | arrowboth |  |
| Β | Beta | 102 | ⇔ | arrowdblboth | 333 |
| Χ | Chi | 103 | ⇓ | arrowdbldown | 337 |
| ∆ | Delta | 104 | ⇐ | arrowdblleft | 334 |
| Ε | Epsilon | 105 | ⇒ | arrowdblright | 336 |
| Η | Eta | 110 | ⇑ | arrowdblup |  |
| € | Euro | 240 | ↓ | arrowdown |  |
| Γ | Gamma | 107 | ⎯ | arrowhorizex | 276 |


| CHAR | NAME | CODE | CHAR | NAME | CODE |
| --- | --- | --- | --- | --- | --- |
| ℑ | Ifraktur | 301 | ← | arrowleft |  |
| Ι | Iota | 111 | → | arrowright |  |
| Κ | Kappa | 113 | ↑ | arrowup |  |
| Λ | Lambda | 114 | ⏐ | arrowvertex |  |
| Μ | Mu | 115 | ∗ | asteriskmath |  |
| Ν | Nu | 116 | | | bar |  |
| Ω | Omega | 127 | β | beta |  |
| Ο | Omicron | 117 | { | braceleft |  |
| Φ | Phi | 106 | } | braceright |  |
| Π | Pi | 120 | ⎧ | bracelefttp |  |
| Ψ | Psi | 131 | ⎨ | braceleftmid |  |
| ℜ | Rfraktur | 302 | ⎩ | braceleftbt |  |
| Ρ | Rho | 122 | ⎫ | bracerighttp |  |
| Σ | Sigma | 123 | ⎬ | bracerightmid |  |
| Τ | Tau | 124 | ⎭ | bracerightbt |  |
| Θ | Theta | 121 | ⎪ | braceex |  |
| Υ | Upsilon | 125 | [ | bracketleft |  |
| ϒ | Upsilon1 | 241 | ] | bracketright |  |
| Ξ | Xi | 130 | ⎡ | bracketlefttp |  |
| Ζ | Zeta | 132 | ⎢ | bracketleftex |  |
| ℵ | aleph | 300 | ⎣ | bracketleftbt |  |
| α | alpha | 141 | ⎤ | bracketrighttp |  |
| & | ampersand | 046 | ⎥ | bracketrightex |  |
| ∠ | angle | 320 | ⎦ | bracketrightbt |  |
| 〈 | angleleft | 341 | • | bullet |  |
| 〉 | angleright | 361 | ↵ | carriagereturn |  |
| ≈ | approxequal | 273 | χ | chi |  |
| ⊗ | circlemultiply | 304 | ⌡ | integralbt |  |
| ⊕ | circleplus | 305 | ∩ | intersection |  |
| ♣ | club | 247 | ι | iota |  |
| : | colon | 072 | κ | kappa |  |
| , | comma | 054 | λ | lambda |  |
| ≅ | congruent | 100 | < | less |  |
| © | copyrightsans | 343 | ≤ | lessequal |  |
| © | copyrightserif | 323 | ∧ | logicaland |  |
| ° | degree | 260 | ¬ | logicalnot |  |
| δ | delta | 144 | ∨ | logicalor |  |
| ♦ | diamond | 250 | ◊ | lozenge |  |


| CHAR | NAME | CODE | CHAR | NAME | CODE |
| --- | --- | --- | --- | --- | --- |
| ÷ | divide | 270 | − | minus |  |
| ⋅ | dotmath | 327 | ′ | minute |  |
| 8 | eight | 070 | µ | mu |  |
| ∈ | element | 316 | × | multiply |  |
| … | ellipsis | 274 | 9 | nine |  |
| ∅ | emptyset | 306 | ∉ | notelement |  |
| ε | epsilon | 145 | ≠ | notequal |  |
| = | equal | 075 | ⊄ | notsubset |  |
| ≡ | equivalence | 272 | ν | nu |  |
| η | eta | 150 | # | numbersign |  |
| ! | exclam | 041 | ω | omega |  |
| ∃ | existential | 044 | ϖ | omega1 |  |
| 5 | five | 065 | ο | omicron |  |
| ƒ | florin | 246 | 1 | one |  |
| 4 | four | 064 | ( | parenleft |  |
| ⁄ | fraction | 244 | ) | parenright |  |
| γ | gamma | 147 | ⎛ | parenlefttp |  |
| ∇ | gradient | 321 | ⎜ | parenleftex |  |
| > | greater | 076 | ⎝ | parenleftbt |  |
| ≥ | greaterequal | 263 | ⎞ | parenrighttp |  |
| ♥ | heart | 251 | ⎟ | parenrightex |  |
| ∞ | infinity | 245 | ⎠ | parenrightbt |  |
| ∫ | integral | 362 | ∂ | partialdiff |  |
| ⌠ | integraltp | 363 | % | percent |  |
| ⎮ | integralex | 364 | . | period |  |
| ⊥ | perpendicular | 136 | ∼ | similar |  |
| ϕ | phi | 146 | 6 | six |  |
| φ | phi1 | 152 | / | slash |  |
| π | pi | 160 | space |  |  |
| + | plus | 053 | ♠ | spade |  |
| ± | plusminus | 261 | ∋ | suchthat |  |
| ∏ | product | 325 | ∑ | summation |  |
| ⊂ | propersubset | 314 | τ | tau |  |
| ⊃ | propersuperset | 311 | ∴ | therefore |  |
| ∝ | proportional | 265 | θ | theta |  |
| ψ | psi | 171 | ϑ | theta1 |  |
| ? | question | 077 | 3 | three |  |
| √ | radical | 326 | ™ | trademarksans |  |
| ⎯ | radicalex | 140 | ™ | trademarkserif |  |
| ⊆ | reflexsubset | 315 | 2 | two |  |


| CHAR | NAME | CODE | CHAR | NAME | CODE |
| --- | --- | --- | --- | --- | --- |
| ⊇ | reflexsuperset | 312 | _ | underscore | 137 |
| ® | registersans | 342 | ∪ | union |  |
| ® | registerserif | 322 | ∀ | universal | 042 |
| ρ | rho | 162 | υ | upsilon |  |
| ″ | second | 262 | ℘ | weierstrass | 303 |
| ; | semicolon | 073 | ξ | xi |  |
| 7 | seven | 067 | 0 | zero |  |
| σ | sigma | 163 | ζ | zeta |  |
| ς | sigma1 |  |  |  |  |

## D.6 ZapfDingbats set and encoding

Table D.6 — ZapfDingbats set and encoding

| CHAR | NAME | CODE | CHAR | NAME | CODE | CHAR | NAME | CODE | CHAR | NAME | CODE |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| space | 040 | ✣ | a30 | 103 | ❆ | a65 | 146 | ♠ | a109 | 253 |  |
| ✁ | a1 | 041 | ✤ | a31 | 104 | ❇ | a66 | 147 | ① | a120 | 254 |
| ✂ | a2 | 042 | ✥ | a32 | 105 | ❈ | a67 | 150 | ② | a121 | 255 |
| ✃ | a202 | 043 | ✦ | a33 | 106 | ❉ | a68 | 151 | ③ | a122 | 256 |
| ✄ | a3 | 044 | ✧ | a34 | 107 | ❊ | a69 | 152 | ④ | a123 | 257 |
| ☎ | a4 | 045 | ★ | a35 | 110 | ❋ | a70 | 153 | ⑤ | a124 | 260 |
| ✆ | a5 | 046 | ✩ | a36 | 111 | ● | a71 | 154 | ⑥ | a125 | 261 |
| ✇ | a119 | 047 | ✪ | a37 | 112 | ❍ | a72 | 155 | ⑦ | a126 | 262 |
| ✈ | a118 | 050 | ✫ | a38 | 113 | ■ | a73 | 156 | ⑧ | a127 | 263 |
| ✉ | a117 | 051 | ✬ | a39 | 114 | ❏ | a74 | 157 | ⑨ | a128 | 264 |
| ☛ | a11 | 052 | ✭ | a40 | 115 | ❐ | a203 | 160 | ⑩ | a129 | 265 |
| ☞ | a12 | 053 | ✮ | a41 | 116 | ❑ | a75 | 161 | ❶ | a130 | 266 |
| ✌ | a13 | 054 | ✯ | a42 | 117 | ❒ | a204 | 162 | ❷ | a131 | 267 |
| ✍ | a14 | 055 | ✰ | a43 | 120 | ▲ | a76 | 163 | ❸ | a132 | 270 |
| ✎ | a15 | 056 | ✱ | a44 | 121 | ▼ | a77 | 164 | ❹ | a133 | 271 |
| ✏ | a16 | 057 | ✲ | a45 | 122 | ◆ | a78 | 165 | ❺ | a134 | 272 |
| ✐ | a105 | 060 | ✳ | a46 | 123 | ❖ | a79 | 166 | ❻ | a135 | 273 |
| ✑ | a17 | 061 | ✴ | a47 | 124 | ◗ | a81 | 167 | ❼ | a136 | 274 |
| ✒ | a18 | 062 | ✵ | a48 | 125 | ❘ | a82 | 170 | ❽ | a137 | 275 |
| ✓ | a19 | 063 | ✶ | a49 | 126 | ❙ | a83 | 171 | ❾ | a138 | 276 |


| CHAR | NAME | CODE | CHAR | NAME | CODE | CHAR | NAME | CODE | CHAR | NAME | CODE |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| ✔ | a20 | 064 | ✷ | a50 | 127 | ❚ | a84 | 172 | ❿ | a139 | 277 |
| ✕ | a21 | 065 | ✸ | a51 | 130 | ❛ | a97 | 173 | ➀ | a140 | 300 |
| ✖ | a22 | 066 | ✹ | a52 | 131 | ❜ | a98 | 174 | ➁ | a141 | 301 |
| ✗ | a23 | 067 | ✺ | a53 | 132 | ❝ | a99 | 175 | ➂ | a142 | 302 |
| ✘ | a24 | 070 | ✻ | a54 | 133 | ❞ | a100 | 176 | ➃ | a143 | 303 |
| ✙ | a25 | 071 | ✼ | a55 | 134 | ❡ | a101 | 241 | ➄ | a144 | 304 |
| ✚ | a26 | 072 | ✽ | a56 | 135 | ❢ | a102 | 242 | ➅ | a145 | 305 |
| ✛ | a27 | 073 | ✾ | a57 | 136 | ❣ | a103 | 243 | ➆ | a146 | 306 |
| ✜ | a28 | 074 | ✿ | a58 | 137 | ❤ | a104 | 244 | ➇ | a147 | 307 |
| ✝ | a6 | 075 | ❀ | a59 | 140 | ❥ | a106 | 245 | ➈ | a148 | 310 |
| ✞ | a7 | 076 | ❁ | a60 | 141 | ❦ | a107 | 246 | ➉ | a149 | 311 |
| ✟ | a8 | 077 | ❂ | a61 | 142 | ❧ | a108 | 247 | ➊ | a150 | 312 |
| ✠ | a9 | 100 | ❃ | a62 | 143 | ♣ | a112 | 250 | ➋ | a151 | 313 |
| ✡ | a10 | 101 | ❄ | a63 | 144 | ♦ | a111 | 251 | ➌ | a152 | 314 |
| ✢ | a29 | 102 | ❅ | a64 | 145 | ♥ | a110 | 252 | ➍ | a153 | 315 |
| ➎ | a154 | 316 | ➚ | a192 | 332 | ➦ | a176 | 346 | ➳ | a184 | 363 |
| ➏ | a155 | 317 | ➛ | a166 | 333 | ➧ | a177 | 347 | ➴ | a197 | 364 |
| ➐ | a156 | 320 | ➜ | a167 | 334 | ➨ | a178 | 350 | ➵ | a185 | 365 |
| ➑ | a157 | 321 | ➝ | a168 | 335 | ➩ | a179 | 351 | ➶ | a194 | 366 |
| ➒ | a158 | 322 | ➞ | a169 | 336 | ➪ | a193 | 352 | ➷ | a198 | 367 |
| ➓ | a159 | 323 | ➟ | a170 | 337 | ➫ | a180 | 353 | ➸ | a186 | 370 |
| ➔ | a160 | 324 | ➠ | a171 | 340 | ➬ | a199 | 354 | ➹ | a195 | 371 |
| → | a161 | 325 | ➡ | a172 | 341 | ➭ | a181 | 355 | ➺ | a187 | 372 |
| ↔ | a163 | 326 | ➢ | a173 | 342 | ➮ | a200 | 356 | ➻ | a188 | 373 |
| ↕ | a164 | 327 | ➣ | a162 | 343 | ➯ | a182 | 357 | ➼ | a189 | 374 |
| ➘ | a196 | 330 | ➤ | a174 | 344 | ➱ | a201 | 361 | ➽ | a190 | 375 |
| ➙ | a165 | 331 | ➥ | a175 | 345 | ➲ | a183 | 362 | ➾ | a191 | 376 |


