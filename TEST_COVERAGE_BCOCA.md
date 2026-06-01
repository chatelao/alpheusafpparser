# Granular Test Coverage - BCOCA

| Requirement ID | Summary | Coverage |
| :--- | :--- | :---: |
| BCOCA-1-001 | AFP architectures define interchange formats that are system independent and are independent of any particular format used for physically transmitting or storing data. | ❓ |
| BCOCA-1-002 | The strategic presentation data stream architectures include Mixed Object Document Content Architecture (MO:DCA). | ❓ |
| BCOCA-1-003 | The strategic presentation data stream architectures include Intelligent Printer Data Stream (IPDS) Architecture. | ❓ |
| BCOCA-1-004 | The IPDS architecture defines bidirectional command protocols for query, resource management, and error recovery. | ❓ |
| BCOCA-1-005 | Presentation Text Object Content Architecture (PTOCA) describes text objects formatted for all-points-addressable presentations. | ❓ |
| BCOCA-1-006 | Image Object Content Architecture (IOCA) describes resolution-independent image objects. | ❓ |
| BCOCA-1-007 | Graphics Object Content Architecture for Advanced Function Presentation (AFP GOCA) describes vector graphics picture objects. | ❓ |
| BCOCA-1-008 | Bar Code Object Content Architecture (BCOCA) describes bar code objects using a number of different symbologies. | ❓ |
| BCOCA-1-009 | Font Object Content Architecture (FOCA) describes the structure and content of fonts. | ❓ |
| BCOCA-1-010 | Color Management Object Content Architecture (CMOCA) carries color management information required to render presentation data. | ❓ |
| BCOCA-1-011 | Metadata Object Content Architecture (MOCA) carries metadata in an AFP environment. | ❓ |
| BCOCA-1-012 | Documents can contain combinations of text, image, graphics, and bar code objects. | ❓ |
| BCOCA-2-001 | BCOCA provides a brief overview of bar codes. | ❓ |
| BCOCA-2-002 | BCOCA describes the basic elements of a bar code system. | ❓ |
| BCOCA-2-003 | BCOCA describes how bar code system performance is measured. | ❓ |
| BCOCA-2-004 | A bar code system consists of the bar code symbology used to encode the data. | ❓ |
| BCOCA-2-005 | A bar code system consists of the physical media on which the bar code is printed. | ❓ |
| BCOCA-2-006 | A bar code system consists of the type of printing device used to print the bar code. | ❓ |
| BCOCA-2-007 | A bar code system consists of the scanning device used to read the bar code. | ❓ |
| BCOCA-2-008 | These elements are described in greater detail in the following sections. | ❓ |
| BCOCA-2-009 | A bar code symbol consists of six parts: start margin, start character, data, optional check-digit, stop character, and stop margin. | ❓ |
| BCOCA-2-010 | Figure 5 shows examples of linear bar code symbols. | ❓ |
| BCOCA-2-011 | Example: Intelligent Mail Container Barcode (Code 128, Modifier X'05'). | ❓ |
| BCOCA-2-012 | Example: Intelligent Mail Package Barcode (Code 128, Modifier X'06'). | ❓ |
| BCOCA-2-013 | Example encoding for Intelligent Mail Package Barcode. | ❓ |
| BCOCA-2-014 | Example: UPC Version A. | ❓ |
| BCOCA-2-015 | Example: UPC Version E. | ❓ |
| BCOCA-2-016 | Example: UPC A + Two-digit Supplemental. | ❓ |
| BCOCA-2-017 | Example: UPC A + Five-digit Supplemental. | ❓ |
| BCOCA-2-018 | Example encoding for UPC A + Five-digit Supplemental. | ❓ |
| BCOCA-2-019 | Example: EAN 13. | ❓ |
| BCOCA-2-020 | Example: EAN + 2 Digit Supplemental. | ❓ |
| BCOCA-2-021 | Example: EAN + 5 Digit Supplemental. | ❓ |
| BCOCA-2-022 | Example encoding for EAN + 5 Digit Supplemental. | ❓ |
| BCOCA-2-023 | Figure 6 shows examples of 2D Matrix bar code symbols (e.g., QR Code, Data Matrix, Aztec Code, Han Xin Code). | ❓ |
| BCOCA-2-024 | Figure 7 shows an example of a 2D stacked symbology (e.g., PDF417). | ❓ |
| BCOCA-2-025 | PDF417 symbols must contain at least 3 rows. | ❓ |
| BCOCA-2-026 | Step 1 of generating a bar code symbol: Identify the bar code symbology to be used and the data to be encoded. | ❓ |
| BCOCA-2-027 | Step 2: Translate the data characters into a binary sequence for encoding. | ❓ |
| BCOCA-2-028 | Step 3: Create the bar and space pattern that represents each character. | ❓ |
| BCOCA-2-029 | Step 4: Format the individual characters into a completed bar code symbol. | ❓ |
| BCOCA-2-030 | Factors influencing bar code density: code structure and module width. | ❓ |
| BCOCA-2-031 | Factor influencing print quality: Marking agent spread/shrink. | ❓ |
| BCOCA-2-032 | Factor influencing print quality: Marking agent voids/specks. | ❓ |
| BCOCA-2-033 | Factor influencing print quality: Marking agent smearing. | ❓ |
| BCOCA-2-034 | Factor influencing print quality: Marking agent non-uniformity. | ❓ |
| BCOCA-2-035 | Factor influencing print quality: Bar and space width tolerances. | ❓ |
| BCOCA-2-036 | Factor influencing print quality: Bar edge roughness. | ❓ |
| BCOCA-2-037 | Bar code system performance parameters: first read rate and substitution error rate. | ❓ |
| BCOCA-3-001 | BCOCA Overview: General BCOCA concepts. | ✅ |
| BCOCA-3-002 | BCOCA Overview: Bar code object processor concepts. | ❓ |
| BCOCA-3-003 | BCOCA Overview: Bar code presentation space concepts. | ❓ |
| BCOCA-3-004 | BCOCA objects can exist in the MO:DCA environment. | ❓ |
| BCOCA-3-005 | BCOCA objects can exist in the IPDS environment. | ❓ |
| BCOCA-3-006 | BCOCA objects can exist in the AFP Line Data environment. | ❓ |
| BCOCA-3-007 | Input to the bar code object processor: Data to be encoded. | ✅ |
| BCOCA-3-008 | Input to the bar code object processor: Bar code symbology to be used. | ✅ |
| BCOCA-3-009 | Input to the bar code object processor: Bar code presentation space size parameters. | ✅ |
| BCOCA-3-010 | Input to the bar code object processor: Bar code symbol location within the bar code presentation space. | ✅ |
| BCOCA-3-011 | Input to the bar code object processor: Module width of the narrow bar code element (or desired symbol width). | ✅ |
| BCOCA-3-012 | Input to the bar code object processor: Total element height of the bar code symbol. | ✅ |
| BCOCA-3-013 | Input to the bar code object processor: Check digit generation option. | ✅ |
| BCOCA-3-014 | Input to the bar code object processor: Wide-to-narrow element ratio. | ✅ |
| BCOCA-3-015 | Input to the bar code object processor: HRI presence, location, and type style. | ✅ |
| BCOCA-3-016 | Input to the bar code object processor: Color of the bar code symbol elements. | ✅ |
| BCOCA-3-017 | Input to the bar code object processor: For 2D symbologies, special functions (escape sequences, error correction, etc.). | ❓ |
| BCOCA-3-018 | The bar code object processor validates all input parameters and generates exception conditions as appropriate. | ❓ |
| BCOCA-3-019 | The bar code object processor generates the bar and space patterns according to the rules of the specified symbology. | ❓ |
| BCOCA-3-020 | The bar code object processor generates, uses, and encodes check digit(s). | ❓ |
| BCOCA-3-021 | The check-digit option is specified in the modifier field. | ❓ |
| BCOCA-3-022 | For 2D matrix symbologies, the processor encodes and compacts data, inserts special function codewords, generates ECC, etc. | ❓ |
| BCOCA-3-023 | For 2D stacked symbologies, the processor generates codewords using compaction schemes, indicators, etc. | ❓ |
| BCOCA-3-024 | The bar code object processor generates appropriate start and stop bar and space patterns for all bar code types. | ❓ |
| BCOCA-3-025 | The bar code object processor generates HRI text characters and positions them as directed. | ❓ |
| BCOCA-3-026 | The bar code object processor suppresses presentation of the bar code symbol if directed by the flag. | ❓ |
| BCOCA-3-027 | The bar code object processor places the symbol and HRI in the presentation space at the location specified. | ❓ |
| BCOCA-3-028 | For QR Code with Image, the processor places the desired image in the presentation space. | ❓ |
| BCOCA-3-029 | The object generator is responsible for ensuring sufficient empty space for quiet zones. | ❓ |
| BCOCA-3-030 | All bar code symbols must be presented in their entirety. | ❓ |
| BCOCA-3-031 | Partial bar code patterns must be obscured to make them unscannable. | ❓ |
| BCOCA-3-032 | L-unit Range Conversion Algorithm: Calculate the number of supported units per inch. | ❓ |
| BCOCA-3-033 | If measurement base is ten inches, divide supported units by ten. | ❓ |
| BCOCA-3-034 | If measurement base is ten centimeters, multiply supported units per decimeter by 0.254. | ❓ |
| BCOCA-3-035 | L-unit Range Conversion Algorithm: Calculate number of supported units per BCOCA unit. | ❓ |
| BCOCA-3-036 | Divide supported units per inch by 1440. | ❓ |
| BCOCA-3-037 | BCOCA assumes 1440 units per inch for its base unit. | ✅ |
| BCOCA-3-038 | L-unit Range Conversion Algorithm: Calculate required value in supported unit of measure. | ❓ |
| BCOCA-3-039 | Multiply BCOCA-specified subset range values by the calculated ratio. | ❓ |
| BCOCA-3-040 | Round off the product to the nearest integer. | ❓ |
| BCOCA-3-041 | Adjust the new range so that it is a subset of the BCOCA-specified subset range. | ❓ |
| BCOCA-3-042 | Example calculation: Supported units per inch = 2400 / 10 = 240. | ❓ |
| BCOCA-3-043 | Example calculation: Supported units per BCOCA unit = 240 / 1440 = 1/6. | ❓ |
| BCOCA-3-044 | Example calculation: Range at 2400 units per 10 inches. | ❓ |
| BCOCA-3-045 | Table 6 shows BCOCA-required ranges for commonly supported measurement bases (14,400 per 10in, 5670 per 10cm, etc.). | ❓ |
| BCOCA-3-046 | For QR Code with Image, linear measurements can be specified as percentages (unit base X'64'). | ❓ |
| BCOCA-3-047 | Table 7 shows HRI recommendations for various bar code types. | ❓ |
| BCOCA-3-048 | Check digits are not always presented in the HRI. | ❓ |
