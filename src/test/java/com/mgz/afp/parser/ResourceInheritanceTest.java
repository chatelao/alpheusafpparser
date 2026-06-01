package com.mgz.afp.parser;

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.base.handler.HandlerFactory;
import com.mgz.xml.XmlHandlerFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Regression test for [DECOUPLE-4.3.1.3.2] Resource Inheritance.
 * Verifies that global resources (like BDD) defined in the preamble
 * are correctly inherited by parallel page tasks.
 */
public class ResourceInheritanceTest {

    @TempDir
    Path tempDir;

    @Test
    public void testResourceInheritance() throws Exception {
        // BDT: SFLen=8 (0x0008), Type=D3A8A8, Flags=00, Reserved=0000
        byte[] bdt = {0x5A, 0x00, 0x08, (byte) 0xD3, (byte) 0xA8, (byte) 0xA8, 0x00, 0x00, 0x00};

        // BDD: SFLen=31 (0x001F), Type=D3A6EB, Flags=00, Reserved=0000, Data=(23 bytes)
        byte[] bdd = {0x5A, 0x00, 0x1F, (byte) 0xD3, (byte) 0xA6, (byte) 0xEB, 0x00, 0x00, 0x00,
                      0x01, 0x00, 0x24, 0x00, 0x24, 0x00, 0x01, 0x00, 0x01, 0x00, 0x00, 0x50,
                      0x1C, 0x00, 0x05, 0x00, 0x08, 0x0F, 0x00, 0x20, 0x01, 0x00, 0x00};

        // BPG: SFLen=8 (0x0008), Type=D3A8AF, Flags=00, Reserved=0000
        byte[] bpg = {0x5A, 0x00, 0x08, (byte) 0xD3, (byte) 0xA8, (byte) 0xAF, 0x00, 0x00, 0x00};

        // BDA: SFLen=26 (0x001A), Type=D3EEEB, Flags=00, Reserved=0000, Data=(18 bytes: 5 header + 10 params + 3 data)
        byte[] bda = {0x5A, 0x00, 0x1A, (byte) 0xD3, (byte) 0xEE, (byte) 0xEB, 0x00, 0x00, 0x00,
                      0x00, 0x00, 0x00, 0x00, 0x00, // header (5)
                      (byte) 0x80, 0x00, 0x20, 0x00, 0x30, 0x01, 0x02, 0x03, 0x04, (byte) 0x80, // params (10)
                      0x41, 0x42, 0x43}; // data (3: ABC)

        // EPG: SFLen=8 (0x0008), Type=D3A9AF, Flags=00, Reserved=0000
        byte[] epg = {0x5A, 0x00, 0x08, (byte) 0xD3, (byte) 0xA9, (byte) 0xAF, 0x00, 0x00, 0x00};

        // EDT: SFLen=8 (0x0008), Type=D3A9A8, Flags=00, Reserved=0000
        byte[] edt = {0x5A, 0x00, 0x08, (byte) 0xD3, (byte) 0xA9, (byte) 0xA8, 0x00, 0x00, 0x00};

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(bdt);
        baos.write(bdd);
        baos.write(bpg);
        baos.write(bda);
        baos.write(epg);
        // Page 2
        byte[] bpg2 = bpg.clone(); bpg2[7] = 0x01;
        byte[] bda2 = bda.clone(); bda2[23] = 0x44; bda2[24] = 0x45; bda2[25] = 0x46; // DEF
        byte[] epg2 = epg.clone(); epg2[7] = 0x01;
        baos.write(bpg2);
        baos.write(bda2);
        baos.write(epg2);
        baos.write(edt);

        File afpFile = tempDir.resolve("test.afp").toFile();
        try (FileOutputStream fos = new FileOutputStream(afpFile)) {
            fos.write(baos.toByteArray());
        }

        HandlerFactory handlerFactory = new XmlHandlerFactory();

        // 1. Sequential
        AFPParserConfiguration seqConfig = new AFPParserConfiguration();
        seqConfig.setAFPFile(afpFile);
        ByteArrayOutputStream seqOut = new ByteArrayOutputStream();
        AFPParser seqParser = new AFPParser(seqConfig);
        try (var handler = handlerFactory.createHandler(seqOut, false)) {
            StructuredField sf;
            while ((sf = seqParser.parseNextSF()) != null) {
                handler.handle(sf);
                sf.release();
            }
        }

        // 2. Parallel
        AFPParserConfiguration parConfig = new AFPParserConfiguration();
        parConfig.setAFPFile(afpFile);
        ByteArrayOutputStream parOut = new ByteArrayOutputStream();
        ParallelAfpConverter parConverter = new ParallelAfpConverter(parConfig, 2, handlerFactory);
        parConverter.convert(parOut);

        String seqXml = normalizeXml(seqOut.toString(StandardCharsets.UTF_8));
        String parXml = normalizeXml(parOut.toString(StandardCharsets.UTF_8));

        assertEquals(seqXml, parXml, "Sequential and Parallel XML output should be identical");
        assertTrue(seqXml.contains("ParametersDataMatrixBarcode"), "XML should contain ParametersDataMatrixBarcode");
    }

    private String normalizeXml(String xml) {
        return xml.replace("\r\n", "\n")
                  .replaceAll(">\\s+<", "><")
                  .trim();
    }
}
