package com.mgz.acceptance;

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.parser.AFPParser;
import com.mgz.afp.parser.AFPParserConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SFILookaheadTest {
    private static File[] filesSuite = {};

    @BeforeAll
    public static void onlyOnce() throws Exception {
        filesSuite = FilesSuite.getAfpFiles();
        assertTrue(filesSuite != null && filesSuite.length > 0, "No AFP Testfiles found");
    }

    @Test
    public void testSFILookaheadParity() throws Exception {
        for (File afpFile : filesSuite) {
            // 1. Parse normally (sequential scanning)
            AFPParserConfiguration confNormal = new AFPParserConfiguration();
            confNormal.setAFPFile(afpFile);
            confNormal.setWellFormed(false);

            List<String> normalMnemonics = new ArrayList<>();
            AFPParser parserNormal = new AFPParser(confNormal);
            StructuredField sf;
            while ((sf = parserNormal.parseNextSF()) != null) {
                normalMnemonics.add(sf.getClass().getSimpleName());
                sf.release();
            }
            parserNormal.quitParsing();

            // 2. Parse with SFI Lookahead (jump logic)
            AFPParserConfiguration confLookahead = new AFPParserConfiguration();
            confLookahead.setAFPFile(afpFile);
            confLookahead.setWellFormed(true);

            List<String> lookaheadMnemonics = new ArrayList<>();
            AFPParser parserLookahead = new AFPParser(confLookahead);
            while ((sf = parserLookahead.parseNextSF()) != null) {
                lookaheadMnemonics.add(sf.getClass().getSimpleName());
                sf.release();
            }
            parserLookahead.quitParsing();

            // 3. Verify parity
            assertEquals(normalMnemonics.size(), lookaheadMnemonics.size(),
                "Mismatch in number of fields for " + afpFile.getName());
            assertEquals(normalMnemonics, lookaheadMnemonics,
                "Mismatch in field sequence for " + afpFile.getName());
        }
    }

    @Test
    public void testSFILookaheadWithByteBuffer() throws Exception {
        for (File afpFile : filesSuite) {
            // 1. Parse normally
            AFPParserConfiguration confNormal = new AFPParserConfiguration();
            confNormal.setAFPFile(afpFile);
            confNormal.getByteBuffer(); // Force mapping
            confNormal.setWellFormed(false);

            List<String> normalMnemonics = new ArrayList<>();
            AFPParser parserNormal = new AFPParser(confNormal);
            StructuredField sf;
            while ((sf = parserNormal.parseNextSF()) != null) {
                normalMnemonics.add(sf.getClass().getSimpleName());
                sf.release();
            }
            parserNormal.quitParsing();

            // 2. Parse with SFI Lookahead
            AFPParserConfiguration confLookahead = new AFPParserConfiguration();
            confLookahead.setAFPFile(afpFile);
            confLookahead.getByteBuffer(); // Force mapping
            confLookahead.setWellFormed(true);

            List<String> lookaheadMnemonics = new ArrayList<>();
            AFPParser parserLookahead = new AFPParser(confLookahead);
            while ((sf = parserLookahead.parseNextSF()) != null) {
                lookaheadMnemonics.add(sf.getClass().getSimpleName());
                sf.release();
            }
            parserLookahead.quitParsing();

            // 3. Verify parity
            assertEquals(normalMnemonics, lookaheadMnemonics,
                "Mismatch in field sequence for " + afpFile.getName() + " (ByteBuffer mode)");
        }
    }
}
