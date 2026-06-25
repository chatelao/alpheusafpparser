package com.mgz.pdf;

import com.mgz.afp.ptoca.PTX_PresentationTextData;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.TRN_TransparentData;
import com.mgz.afp.parser.AFPParserConfiguration;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PdfOverlapReproductionTest {

    @Test
    public void testInlinePosNotUpdatedWithoutPGD() throws Exception {
        PdfHandler handler = new PdfHandler(new ByteArrayOutputStream());
        PdfTextState textState = handler.getTextState();

        // Initial position
        assertEquals(0, textState.getInlinePos());

        // Create PTX with some text
        PTX_PresentationTextData ptx = new PTX_PresentationTextData();
        TRN_TransparentData trn = new TRN_TransparentData();
        trn.decodeAFP("Hello".getBytes(), 0, 5, new AFPParserConfiguration());

        List<PTOCAControlSequence> csList = new ArrayList<>();
        csList.add(trn);
        ptx.setControlSequences(csList);

        // Handle PTX
        handler.handle(ptx);

        // After fix, inlinePos should be updated even if PGD was not processed
        assertTrue(textState.getInlinePos() > 0, "Inline position should have been updated after rendering text");
    }
}
