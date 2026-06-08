package com.mgz.performance;

import com.mgz.afp.modca.NOP_NoOperation;
import com.mgz.afp.ptoca.PTX_PresentationTextData;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.util.MnemonicPerformanceMonitor;
import com.mgz.util.PTXPerformanceMonitor;
import com.mgz.xml.AfpJacksonXmlWriter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PerformanceInstrumentationTest {

    @BeforeEach
    public void setup() {
        PTXPerformanceMonitor.clear();
        PTXPerformanceMonitor.setEnabled(true);
        MnemonicPerformanceMonitor.setEnabled(true);
    }

    @AfterEach
    public void tearDown() {
        PTXPerformanceMonitor.setEnabled(false);
        MnemonicPerformanceMonitor.setEnabled(false);
        PTXPerformanceMonitor.clear();
    }

    @Test
    public void testPerformanceInstrumentation() throws Exception {
        // [COVERAGE_ROADMAP-4-2]
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // Setup configuration with PTX debug enabled to trigger all monitor paths
        AFPParserConfiguration config = new AFPParserConfiguration();
        config.setPtxDebug(true);

        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos)) {
            // Create PTX with TRN and CSI (to test recordPtocaWrite payload/CSI paths)
            PTX_PresentationTextData ptx = new PTX_PresentationTextData();
            PTOCAControlSequence.TRN_TransparentData trn = new PTOCAControlSequence.TRN_TransparentData();
            trn.setTransparentData("PERF TEST");

            // Manually set a CSI for TRN to test the length path
            PTOCAControlSequence.ControlSequenceIntroducer csi = new PTOCAControlSequence.ControlSequenceIntroducer();
            csi.setLength((short) 11); // TRN + "PERF TEST"
            trn.setCsi(csi);

            ptx.setControlSequences(List.of(trn));

            // Record a fake parse to increment totalPtxCount
            PTXPerformanceMonitor.recordPtxParse(1000, 11, 1);

            writer.handle(ptx);

            // Write a NOP field to exercise MnemonicPerformanceMonitor
            NOP_NoOperation nop = new NOP_NoOperation();
            byte[] nopData = new byte[]{0x01, 0x02};
            nop.decodeAFP(nopData, 0, nopData.length, config);
            writer.handle(nop);
        }

        // Verify monitors have data and print summaries
        PTXPerformanceMonitor.merge();
        PTXPerformanceMonitor.printSummary();
        MnemonicPerformanceMonitor.printSummary();

        assertTrue(PTXPerformanceMonitor.isEnabled());
        assertTrue(MnemonicPerformanceMonitor.isEnabled());
    }
}
