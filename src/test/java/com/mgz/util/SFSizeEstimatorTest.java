package com.mgz.util;

import com.mgz.afp.base.StructuredFieldIntroducer;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.afp.ptoca.PTX_PresentationTextData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SFSizeEstimatorTest {

    private StructuredFieldIntroducer createSfi(int length) {
        StructuredFieldIntroducer sfi = new StructuredFieldIntroducer();
        sfi.setSFLength(length);
        return sfi;
    }

    @Test
    public void testEstimationLogic() throws Exception {
        // --- 1. Static Estimation ---
        PTXPerformanceMonitor.clear();
        PTXPerformanceMonitor.setEnabled(false);

        PTX_PresentationTextData ptx = new PTX_PresentationTextData();
        ptx.setStructuredFieldIntroducer(createSfi(16));

        long estimate = SFSizeEstimator.estimateXmlSize(ptx);
        assertEquals(192, estimate, "Static estimation should use PTX_MULTIPLIER (12.0)");

        // --- 2. Ratio-based Dynamic Estimation ---
        PTXPerformanceMonitor.clear();
        PTXPerformanceMonitor.setEnabled(true);

        // Record specific PTOCA stats for AMI (ratio 25.0)
        PTXPerformanceMonitor.recordPtocaWrite("AMI_AbsoluteMoveInline", 500, 2, 50);
        // Record global PTX stats (ratio 12.0)
        PTXPerformanceMonitor.recordPtxParse(1000, 10, 1);
        PTXPerformanceMonitor.recordPtxWrite(1000, 120);
        PTXPerformanceMonitor.merge();

        ptx = new PTX_PresentationTextData();
        ptx.setStructuredFieldIntroducer(createSfi(15));
        byte[] payload = new byte[] {
            0x2B, (byte) 0xD3, 0x04, (byte) 0xC6, 0x00, 0x01
        };
        // Decode without ptxDebug to avoid double recording
        ptx.decodeAFP(payload, 0, payload.length, new AFPParserConfiguration());

        estimate = SFSizeEstimator.estimateXmlSize(ptx);
        // AMI ratio is 25.0. Payload is 2.
        // Estimate = (2 * 25.0) + 100 = 150.
        assertEquals(150, estimate, "Dynamic estimation should use AMI-specific ratio");

        // --- 3. Global Ratio Fallback ---
        // Reuse current monitor state (Global Ratio 12.0)
        ptx = new PTX_PresentationTextData();
        ptx.setStructuredFieldIntroducer(createSfi(14));
        byte[] payloadUnknown = new byte[] {
            0x2B, (byte) 0xD3, 0x03, (byte) 0xF0, 0x01
        };
        ptx.decodeAFP(payloadUnknown, 0, payloadUnknown.length, new AFPParserConfiguration());

        estimate = SFSizeEstimator.estimateXmlSize(ptx);
        // SCFL is unknown, fallback to global ratio 12.0
        // Payload is 1. Estimate = (1 * 12.0) + 100 = 112.
        assertEquals(112, estimate, "Dynamic estimation should fall back to global PTX ratio");

        // --- 4. No Sequences Fallback ---
        ptx = new PTX_PresentationTextData();
        ptx.setStructuredFieldIntroducer(createSfi(20));
        // No decoding, sequences == null
        estimate = SFSizeEstimator.estimateXmlSize(ptx);
        // Fallback to multiplier-based using global ratio 12.0
        // 20 * 12.0 = 240
        assertEquals(240, estimate, "Null sequences should fall back to global ratio multiplier");
    }
}
