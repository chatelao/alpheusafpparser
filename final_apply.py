import sys
import os

def fix_roadmap():
    path = "XYPAGE_ROADMAP.md"
    if not os.path.exists(path): return
    with open(path, "r") as f:
        content = f.read()
    if "## Phase 7" not in content:
        addition = """
## Phase 7: Further structural coverage and completeness
- [x] Inject `page` attribute into additional structural fields:
  - `BNG_BeginNamedPageGroup` / `ENG_EndNamedPageGroup`
  - `IPS_IncludePageSegment`
  - `IPO_IncludePageOverlay`
  - `BGR_BeginGraphicsObject` / `EGR_EndGraphicsObject`
  - `BBC_BeginBarCodeObject` / `EBC_EndBarCodeObject`
  - `BIM_BeginImageObject` / `EIM_EndImageObject`
  - `BOC_BeginObjectContainer` / `EOC_EndObjectContainer`
- [x] Complete manual fast-path serialization for `IPS` and `IPO` in `AfpJacksonXmlWriter`.
"""
        content += addition
    with open(path, "w") as f:
        f.write(content)

def fix_afp_writer():
    path = "src/main/java/com/mgz/xml/AfpJacksonXmlWriter.java"
    with open(path, "r") as f:
        content = f.read()

    # 1. Update writeFieldDirectly calls to inject page or use new methods
    replacements = [
        ('writeNameAndTripletsDirectly(bng, "BNG_BeginNamedPageGroup", level);', 'writeNameAndTripletsDirectly(bng, "BNG_BeginNamedPageGroup", level, true);'),
        ('writeNameAndTripletsDirectly(bgr, "BGR_BeginGraphicsObject", level);', 'writeNameAndTripletsDirectly(bgr, "BGR_BeginGraphicsObject", level, true);'),
        ('writeNameAndTripletsDirectly(egr, "EGR_EndGraphicsObject", level);', 'writeNameAndTripletsDirectly(egr, "EGR_EndGraphicsObject", level, true);'),
        ('writeNameAndTripletsDirectly(eng, "ENG_EndNamedPageGroup", level);', 'writeNameAndTripletsDirectly(eng, "ENG_EndNamedPageGroup", level, true);'),
        ('writeNameAndTripletsDirectly(boc, "BOC_BeginObjectContainer", level);', 'writeNameAndTripletsDirectly(boc, "BOC_BeginObjectContainer", level, true);'),
        ('writeNameDirectly(eoc, "EOC_EndObjectContainer", level);', 'writeNameDirectly(eoc, "EOC_EndObjectContainer", level, true);'),
        ('writeNameAndTripletsDirectly(bim, "BIM_BeginImageObject", level);', 'writeNameAndTripletsDirectly(bim, "BIM_BeginImageObject", level, true);'),
        ('writeNameDirectly(eim, "EIM_EndImageObject", level);', 'writeNameDirectly(eim, "EIM_EndImageObject", level, true);'),
        ('writeTripletsAndTextDirectly(ipg, "IPG_IncludePage", level);', 'writeTripletsAndTextDirectly(ipg, "IPG_IncludePage", level, true);'),
        ('writeTripletsAndTextDirectly(ips, "IPS_IncludePageSegment", level);', 'writeIpsDirectly(ips, level);'),
        ('writeTripletsAndTextDirectly(ipo, "IPO_IncludePageOverlay", level);', 'writeIpoDirectly(ipo, level);')
    ]
    for old, new in replacements:
        content = content.replace(old, new)

    # 2. Update writeBbcDirectly and writeEbcDirectly
    content = content.replace(
        '  private void writeBbcDirectly(BBC_BeginBarCodeObject bbc, int level) throws Exception {\n    MnemonicPerformanceMonitor.startWriteWithMnemonic("BBC");\n    baseXsw.writeStartElement("BBC_BeginBarCodeObject");\n    int childLevel = level + 1;\n    if (bbc.getName() != null) {\n      baseXsw.writeAttribute("name", bbc.getName());\n    }',
        '  private void writeBbcDirectly(BBC_BeginBarCodeObject bbc, int level) throws Exception {\n    MnemonicPerformanceMonitor.startWriteWithMnemonic("BBC");\n    baseXsw.writeStartElement("BBC_BeginBarCodeObject");\n    if (currentPageNumber > 0) {\n      baseXsw.writeIntAttribute(null, null, "page", currentPageNumber);\n    }\n    int childLevel = level + 1;\n    if (bbc.getName() != null) {\n      writeElement(baseXsw, childLevel, "name", bbc.getName());\n    }'
    )
    content = content.replace(
        '  private void writeEbcDirectly(EBC_EndBarCodeObject ebc, int level) throws Exception {\n    MnemonicPerformanceMonitor.startWriteWithMnemonic("EBC");\n    baseXsw.writeStartElement("EBC_EndBarCodeObject");\n    int childLevel = level + 1;\n    if (ebc.getName() != null) {\n      baseXsw.writeAttribute("name", ebc.getName());\n    }',
        '  private void writeEbcDirectly(EBC_EndBarCodeObject ebc, int level) throws Exception {\n    MnemonicPerformanceMonitor.startWriteWithMnemonic("EBC");\n    baseXsw.writeStartElement("EBC_EndBarCodeObject");\n    if (currentPageNumber > 0) {\n      baseXsw.writeIntAttribute(null, null, "page", currentPageNumber);\n    }\n    int childLevel = level + 1;\n    if (ebc.getName() != null) {\n      writeElement(baseXsw, childLevel, "name", ebc.getName());\n    }'
    )

    # 3. Implement overloads for existing methods
    content = content.replace(
        '  private void writeNameAndTripletsDirectly(com.mgz.afp.base.StructuredFieldBaseNameAndTriplets sf, String rootName, int level) throws Exception {\n    if (MnemonicPerformanceMonitor.isEnabled()) {',
        '  /**\n   * Writes a structured field with name and triplets.\n   *\n   * @param sf the structured field\n   * @param rootName the root element name\n   * @param level the indentation level\n   * @throws Exception if writing fails\n   */\n  private void writeNameAndTripletsDirectly(com.mgz.afp.base.StructuredFieldBaseNameAndTriplets sf, String rootName, int level) throws Exception {\n    writeNameAndTripletsDirectly(sf, rootName, level, false);\n  }\n\n  /**\n   * Writes a structured field with name and triplets.\n   *\n   * @param sf the structured field\n   * @param rootName the root element name\n   * @param level the indentation level\n   * @param injectPage if true, inject page attribute\n   * @throws Exception if writing fails\n   */\n  private void writeNameAndTripletsDirectly(com.mgz.afp.base.StructuredFieldBaseNameAndTriplets sf, String rootName,\n      int level, boolean injectPage) throws Exception {\n    if (MnemonicPerformanceMonitor.isEnabled()) {'
    )
    content = content.replace(
        '    baseXsw.writeStartElement(rootName);\n    int childLevel = level + 1;',
        '    baseXsw.writeStartElement(rootName);\n    if (injectPage && currentPageNumber > 0) {\n      baseXsw.writeIntAttribute(null, null, "page", currentPageNumber);\n    }\n    int childLevel = level + 1;'
    )

    content = content.replace(
        '  private void writeTripletsAndTextDirectly(com.mgz.afp.base.StructuredFieldBaseTriplets sf, String rootName, int level) throws Exception {\n    if (MnemonicPerformanceMonitor.isEnabled()) {',
        '  /**\n   * Writes a structured field with triplets and text.\n   *\n   * @param sf the structured field\n   * @param rootName the root element name\n   * @param level the indentation level\n   * @throws Exception if writing fails\n   */\n  private void writeTripletsAndTextDirectly(com.mgz.afp.base.StructuredFieldBaseTriplets sf, String rootName, int level) throws Exception {\n    writeTripletsAndTextDirectly(sf, rootName, level, false);\n  }\n\n  /**\n   * Writes a structured field with triplets and text.\n   *\n   * @param sf the structured field\n   * @param rootName the root element name\n   * @param level the indentation level\n   * @param injectPage if true, inject page attribute\n   * @throws Exception if writing fails\n   */\n  private void writeTripletsAndTextDirectly(com.mgz.afp.base.StructuredFieldBaseTriplets sf, String rootName,\n      int level, boolean injectPage) throws Exception {\n    if (MnemonicPerformanceMonitor.isEnabled()) {'
    )
    # The writeStartElement replacement above already covers this one too.

    content = content.replace(
        '  private void writeNameDirectly(com.mgz.afp.base.StructuredFieldBaseName sf, String rootName, int level) throws Exception {\n    if (MnemonicPerformanceMonitor.isEnabled()) {',
        '  /**\n   * Writes a structured field with name.\n   *\n   * @param sf the structured field\n   * @param rootName the root element name\n   * @param level the indentation level\n   * @throws Exception if writing fails\n   */\n  private void writeNameDirectly(com.mgz.afp.base.StructuredFieldBaseName sf, String rootName, int level) throws Exception {\n    writeNameDirectly(sf, rootName, level, false);\n  }\n\n  /**\n   * Writes a structured field with name.\n   *\n   * @param sf the structured field\n   * @param rootName the root element name\n   * @param level the indentation level\n   * @param injectPage if true, inject page attribute\n   * @throws Exception if writing fails\n   */\n  private void writeNameDirectly(com.mgz.afp.base.StructuredFieldBaseName sf, String rootName,\n      int level, boolean injectPage) throws Exception {\n    if (MnemonicPerformanceMonitor.isEnabled()) {'
    )

    # 4. Insert IPS and IPO implementations
    idx = content.find('  private void writeTripletsAndText(')
    if idx != -1:
        impl = """  /**
   * Writes an IPS structured field.
   *
   * @param ips the structured field
   * @param level the indentation level
   * @throws Exception if writing fails
   */
  private void writeIpsDirectly(com.mgz.afp.modca.IPS_IncludePageSegment ips,
      int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("IPS");
    baseXsw.writeStartElement("IPS_IncludePageSegment");
    if (currentPageNumber > 0) {
      baseXsw.writeIntAttribute(null, null, "page", currentPageNumber);
    }
    int childLevel = level + 1;
    writeElement(baseXsw, childLevel, "pageSegmentName", ips.getPageSegmentName());
    writeElement(baseXsw, childLevel, "xOrigin", ips.getxOrigin());
    writeElement(baseXsw, childLevel, "yOrigin", ips.getyOrigin());
    if (ips.getTriplets() != null && !ips.getTriplets().isEmpty()) {
      for (Triplet triplet : ips.getTriplets()) {
        writeTriplet(baseXsw, triplet, childLevel);
      }
    }
    if (ips.getText() != null) {
      writeElement(baseXsw, childLevel, "text", ips.getText());
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  /**
   * Writes an IPO structured field.
   *
   * @param ipo the structured field
   * @param level the indentation level
   * @throws Exception if writing fails
   */
  private void writeIpoDirectly(com.mgz.afp.modca.IPO_IncludePageOverlay ipo,
      int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("IPO");
    baseXsw.writeStartElement("IPO_IncludePageOverlay");
    if (currentPageNumber > 0) {
      baseXsw.writeIntAttribute(null, null, "page", currentPageNumber);
    }
    int childLevel = level + 1;
    writeElement(baseXsw, childLevel, "overlayName", ipo.getOverlayName());
    writeElement(baseXsw, childLevel, "xOrigin", ipo.getxOrigin());
    writeElement(baseXsw, childLevel, "yOrigin", ipo.getyOrigin());
    if (ipo.getxRotation() != null) {
      writeElement(baseXsw, childLevel, "xRotation", ipo.getxRotation().name());
    }
    if (ipo.getTriplets() != null && !ipo.getTriplets().isEmpty()) {
      for (Triplet triplet : ipo.getTriplets()) {
        writeTriplet(baseXsw, triplet, childLevel);
      }
    }
    if (ipo.getText() != null) {
      writeElement(baseXsw, childLevel, "text", ipo.getText());
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

"""
        content = content[:idx] + impl + content[idx:]

    with open(path, "w") as f:
        f.write(content)

def fix_test():
    path = "src/test/java/com/mgz/xml/PTXAttributesTest.java"
    with open(path, "r") as f:
        lines = f.readlines()

    new_lines = []
    skip_handle = False
    for line in lines:
        if 'import com.mgz.afp.modca.IOB_IncludeObject;' in line:
            line = 'import com.mgz.afp.modca.IOB_IncludeObject;\nimport com.mgz.afp.modca.IPS_IncludePageSegment;\nimport com.mgz.afp.modca.IPO_IncludePageOverlay;\nimport com.mgz.afp.modca.BNG_BeginNamedPageGroup;\nimport com.mgz.afp.modca.ENG_EndNamedPageGroup;\nimport com.mgz.afp.bcoca.BBC_BeginBarCodeObject;\nimport com.mgz.afp.goca.BGR_BeginGraphicsObject;\n'
        elif '            iob1.setName("IOB1");' in line:
            line = """            iob1.setName("IOB1");
            writer.handle(iob1);

            IPS_IncludePageSegment ips1 = new IPS_IncludePageSegment();
            ips1.setPageSegmentName("S1SEG001");
            ips1.setxOrigin(100);
            ips1.setyOrigin(200);
            writer.handle(ips1);

            IPO_IncludePageOverlay ipo1 = new IPO_IncludePageOverlay();
            ipo1.setOverlayName("O1OVL001");
            ipo1.setxOrigin(300);
            ipo1.setyOrigin(400);
            writer.handle(ipo1);

            BNG_BeginNamedPageGroup bng1 = new BNG_BeginNamedPageGroup();
            bng1.setName("GRP001");
            writer.handle(bng1);

            BBC_BeginBarCodeObject bbc1 = new BBC_BeginBarCodeObject();
            bbc1.setName("BC001");
            writer.handle(bbc1);

            BGR_BeginGraphicsObject bgr1 = new BGR_BeginGraphicsObject();
            bgr1.setName("GR001");
            writer.handle(bgr1);

            ENG_EndNamedPageGroup eng1 = new ENG_EndNamedPageGroup();
            eng1.setName("GRP001");
            writer.handle(eng1);
"""
            skip_handle = True
        elif skip_handle and '            writer.handle(iob1);' in line:
            skip_handle = False
            continue

        if '        assertTrue(xml.contains("<IOB_IncludeObject page=\\"1\\">"), "IOB should contain page=\\"1\\"");' in line:
            line = line + """        assertTrue(xml.contains("<IPS_IncludePageSegment page=\\"1\\">"),
                "IPS should contain page=\\"1\\"");
        assertTrue(xml.contains("<pageSegmentName>S1SEG001</pageSegmentName>"),
                "IPS should contain pageSegmentName");
        assertTrue(xml.contains("<xOrigin>100</xOrigin>"), "IPS should contain xOrigin");
        assertTrue(xml.contains("<yOrigin>200</yOrigin>"), "IPS should contain yOrigin");
        assertTrue(xml.contains("<IPO_IncludePageOverlay page=\\"1\\">"),
                "IPO should contain page=\\"1\\"");
        assertTrue(xml.contains("<overlayName>O1OVL001</overlayName>"),
                "IPO should contain overlayName");
        assertTrue(xml.contains("<BNG_BeginNamedPageGroup page=\\"1\\">"),
                "BNG should contain page=\\"1\\"");
        assertTrue(xml.contains("<BBC_BeginBarCodeObject page=\\"1\\">"),
                "BBC should contain page=\\"1\\"");
        assertTrue(xml.contains("<name>BC001</name>"), "BBC should contain name");
        assertTrue(xml.contains("<BGR_BeginGraphicsObject page=\\"1\\">"),
                "BGR should contain page=\\"1\\"");
        assertTrue(xml.contains("<ENG_EndNamedPageGroup page=\\"1\\">"),
                "ENG should contain page=\\"1\\"");
"""

        # Wrap long lines in assertions
        if len(line) > 100:
            if "assertTrue(xml.contains(" in line:
                 parts = line.split('", "', 1)
                 if len(parts) == 2:
                     line = parts[0] + '",\n                "' + parts[1]
            elif "com.mgz.afp.triplets.Triplet.Comment comment" in line:
                 line = line.replace("com.mgz.afp.triplets.Triplet.Comment comment", "com.mgz.afp.triplets.Triplet.Comment\n                comment")
            elif "PTOCAControlSequence.AMI_AbsoluteMoveInline ami" in line:
                 line = line.replace("PTOCAControlSequence.AMI_AbsoluteMoveInline ami", "PTOCAControlSequence.AMI_AbsoluteMoveInline\n                ami")
            elif "PTOCAControlSequence.AMB_AbsoluteMoveBaseline amb" in line:
                 line = line.replace("PTOCAControlSequence.AMB_AbsoluteMoveBaseline amb", "PTOCAControlSequence.AMB_AbsoluteMoveBaseline\n                amb")
            elif "PTOCAControlSequence.GraphicCharacters gc" in line:
                 line = line.replace("PTOCAControlSequence.GraphicCharacters gc", "PTOCAControlSequence.GraphicCharacters\n                gc")
            elif "PTOCAControlSequence.UCT_UnicodeComplexText uct" in line:
                 line = line.replace("PTOCAControlSequence.UCT_UnicodeComplexText uct", "PTOCAControlSequence.UCT_UnicodeComplexText\n                uct")
            elif "PTOCAControlSequence.TRN_TransparentData trn" in line:
                 line = line.replace("PTOCAControlSequence.TRN_TransparentData trn", "PTOCAControlSequence.TRN_TransparentData\n                trn")

        new_lines.append(line)
    with open(path, "w") as f:
        f.writelines(new_lines)

fix_roadmap()
fix_afp_writer()
fix_test()
