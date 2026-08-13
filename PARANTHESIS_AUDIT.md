# GOCA Parenthesis Representation and XML Serialization Audit

This document provides a comprehensive audit of how LEFT PARENTHESIS `(` and RIGHT PARENTHESIS `)` characters originating from GOCA (Graphics Object Content Architecture) structured fields are encoded, decoded, serialized, and why they might not be found in the plain text of raw AFP files or the generated XML.

---

## 1. Executive Summary

In GOCA, string data is presented within `GAD_GraphicsData` structured fields using specific character string drawing orders. Searching for literal ASCII parentheses `(` (`0x28`) and `)` (`0x29`) fails in binary/plain text of AFP files because:
1. **EBCDIC Encoding**: Characters are encoded in EBCDIC (such as code page Cp500, Cp273, or Cp1141). In EBCDIC Cp500, LEFT PARENTHESIS `(` is encoded as `0x4D` and RIGHT PARENTHESIS `)` is encoded as `0x5D`.
2. **XML Serialization Behavior & Silent Suppression**: In the parsed XML, human-readable strings are written directly under `<text>` child elements. However, if Alpheus AFP Parser's heuristic determines that the string is *not* "human-readable" (less than 90% printable characters as evaluated by `UtilCharacterEncoding.isHumanReadable()`), the `text` field is set to `null`. When `text` is `null`, `AfpJacksonXmlWriter` completely suppresses writing the `<text>` element. Because there is no raw or hexadecimal fallback written for these drawing orders, the parentheses are completely omitted from the XML.

---

## 2. GOCA Drawing Orders for Strings

GOCA defines two primary drawing orders to represent and render text:
- **`GCCHST_CharacterStringAtCurrentPosition` (Opcode `0x83`)**: Draws a character string starting from the active graphics state's current position.
- **`GCHST_CharacterStringAtGivenPosition` (Opcode `0xC3`)**: Draws a character string starting from a specified coordinate origin.

### Parsing Logic in `GAD_DrawingOrder.java`
When these drawing orders are decoded in `GAD_DrawingOrder.java`, they extract a sequence of bytes (code points) and attempt to resolve them into a string:

```java
drawingOrderType = UtilBinaryDecoding.parseShort(sfData, offset, 1);
lengthOfFollowingData = UtilBinaryDecoding.parseShort(sfData, offset + 1, 1);

if (lengthOfFollowingData > 0) {
    codePoints = new byte[lengthOfFollowingData];
    System.arraycopy(sfData, offset + 2, codePoints, 0, codePoints.length);
    if (UtilCharacterEncoding.isHumanReadable(codePoints, config)) {
        text = new String(codePoints, config.getAfpCharSet());
    }
} else {
    codePoints = null;
    text = null;
}
```

---

## 3. EBCDIC Encoding of Parentheses

AFP files store text in EBCDIC. In standard ASCII and UTF-8, parentheses have the following hexadecimal representations:
- `(` = `0x28`
- `)` = `0x29`

However, in **EBCDIC Cp500**, they are encoded as:
- **LEFT PARENTHESIS `(`** = `0x4D`
- **RIGHT PARENTHESIS `)`** = `0x5D`

When examining raw binary AFP streams or doing a plain-text search on the binary file, searching for `(` and `)` (or hex `28`/`29`) will yield no results. Instead, one must search for the EBCDIC hex values `4D` and `5D` within the payload of GAD structured fields.

---

## 4. XML Serialization and Element Suppression

`AfpJacksonXmlWriter.java` serializes GOCA structured fields and their associated drawing orders using a manual, high-performance StAX2 fast-path.

### Serialization Logic in `AfpJacksonXmlWriter.java`
For `GCCHST` and `GCHST` orders, the serialization code is:

```java
} else if (order instanceof GAD_DrawingOrder.GCCHST_CharacterStringAtCurrentPosition gcchst) {
  writer.writeStartElement("GCCHST_CharacterStringAtCurrentPosition");
  writeElement(writer, childLevel, "text", gcchst.getText());
  writeIndent(writer, level);
  writer.writeEndElement();
} else if (order instanceof GAD_DrawingOrder.GCHST_CharacterStringAtGivenPosition gchst) {
  writer.writeStartElement("GCHST_CharacterStringAtGivenPosition");
  if (gchst.getOriginPoint() != null) {
    writeIndent(writer, childLevel);
    writer.writeStartElement("originPoint");
    writeElement(writer, childLevel + 1, "xCoordinate", gchst.getOriginPoint().xCoordinate());
    writeElement(writer, childLevel + 1, "yCoordinate", gchst.getOriginPoint().yCoordinate());
    writeIndent(writer, childLevel);
    writer.writeEndElement();
  }
  writeElement(writer, childLevel, "text", gchst.getText());
  writeIndent(writer, level);
  writer.writeEndElement();
```

### The `writeElement` Null-Safety Logic
The `writeElement` helper checks if the string value is non-null before writing the element:

```java
private void writeElement(XMLStreamWriter2 writer, int level, String name, String value) throws Exception {
  if (value != null) {
    writeIndent(writer, level);
    writer.writeStartElement(name);
    writer.writeCharacters(value);
    writer.writeEndElement();
  }
}
```

### Consequences for Missing Parentheses
If `gcchst.getText()` or `gchst.getText()` is `null`, **the `<text>` element is completely omitted**.

The text field becomes `null` under either of the following conditions:
1. **Empty String Payload**: The drawing order has `lengthOfFollowingData` equal to 0.
2. **Heuristic Filter Failure**: The `codePoints` are parsed, but `UtilCharacterEncoding.isHumanReadable` determines that the string is binary/non-printable (i.e., less than 90% of the characters are printable as defined by EBCDIC translation rules).

Because the raw `codePoints` array is **not** written to the XML as a fallback (unlike other GOCA elements that output base64-encoded binary), if a string containing parentheses is marked as non-human-readable, **the parentheses completely vanish from the XML output and cannot be found in plain text.**

---

## 5. How to Find GOCA Parentheses in the XML

When parentheses *are* human-readable and successfully decoded, they are written to the XML as literal characters without any XML escaping (since `(` and `)` are not special characters in XML syntax).

### Expected XML Output for Human-Readable Strings
```xml
<!-- Character String at Current Position -->
<GCCHST_CharacterStringAtCurrentPosition>
  <text>(My GOCA Text)</text>
</GCCHST_CharacterStringAtCurrentPosition>

<!-- Character String at Given Position -->
<GCHST_CharacterStringAtGivenPosition>
  <originPoint>
    <xCoordinate>1200</xCoordinate>
    <yCoordinate>3400</yCoordinate>
  </originPoint>
  <text>(Some Coordinates)</text>
</GCHST_CharacterStringAtGivenPosition>
```

### Summary of Where/How to Search
1. **In Human-Readable XML**: Perform a standard plain-text search for `(` or `)` inside the generated XML. They will appear as standard text child elements `<text>(` and `</text>`.
2. **In Binary AFP Stream**: Look for the structured field `GAD_GraphicsData` containing the drawing orders `0x83` (GCCHST) or `0xC3` (GCHST). Look within their payloads for the EBCDIC hex values `0x4D` for `(` and `0x5D` for `)`.
3. **If Omitted from XML**: If a GOCA character string order is present in the binary stream but lacks a `<text>` element in the XML, it is due to the `isHumanReadable` heuristic failing. To resolve this, run the parser with an alternate code page configuration (e.g., `-Dafp.charset=Cp500` or via Java API) or examine the raw bytes of the `GAD` structured field.
