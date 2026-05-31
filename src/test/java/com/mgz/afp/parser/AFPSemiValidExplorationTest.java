package com.mgz.afp.parser;

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.enums.SFTypeID;
import com.mgz.afp.exceptions.AFPParserException;
import com.mgz.util.Constants;
import com.mgz.util.UtilBinaryDecoding;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Random;

public class AFPSemiValidExplorationTest {

    private final Random random = new Random(42);

    @Test
    public void testRandomSFSequence() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        SFTypeID[] types = SFTypeID.values();

        for (int i = 0; i < 100; i++) {
            SFTypeID type = types[random.nextInt(types.length)];
            if (type == SFTypeID.Undefined) continue;

            int payloadLen = random.nextInt(100);
            int sfLen = 8 + payloadLen;

            baos.write(Constants.AFP_BEGIN_BYTE);
            baos.write(UtilBinaryDecoding.intToByteArray(sfLen, 2));
            baos.write(type.toBytes());
            baos.write(0); // Flags
            baos.write(0); // Reserved byte 1
            baos.write(0); // Reserved byte 2

            byte[] payload = new byte[payloadLen];
            random.nextBytes(payload);
            baos.write(payload);
        }

        byte[] afpData = baos.toByteArray();
        parseAfpData(afpData);
    }

    @Test
    public void testMutatedAFP() throws Exception {
        File template = new File("src/test/resources/afp/afp-goca-reference-03/Chapter_1.afp");
        if (!template.exists()) {
            return; // Skip if file not found
        }

        AFPParserConfiguration config = new AFPParserConfiguration();
        config.setAFPFile(template);
        config.setBuildShallow(true);
        AFPParser parser = new AFPParser(config);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        StructuredField sf;
        while ((sf = parser.parseNextSF()) != null) {
            SFTypeID typeID = sf.getStructuredFieldIntroducer().getSFTypeID();
            int originalPayloadLen = sf.getStructuredFieldIntroducer().getSFLength() - 8;

            // Randomize payload length slightly or keep it
            int newPayloadLen = Math.max(0, originalPayloadLen + random.nextInt(21) - 10);
            int sfLen = 8 + newPayloadLen;

            baos.write(Constants.AFP_BEGIN_BYTE);
            baos.write(UtilBinaryDecoding.intToByteArray(sfLen, 2));
            baos.write(typeID.toBytes());
            baos.write(0); // Flags
            baos.write(0); // Reserved byte 1
            baos.write(0); // Reserved byte 2

            byte[] payload = new byte[newPayloadLen];
            random.nextBytes(payload);
            baos.write(payload);
        }

        byte[] mutatedData = baos.toByteArray();
        parseAfpData(mutatedData);
    }

    private void parseAfpData(byte[] data) throws AFPParserException {
        AFPParserConfiguration config = new AFPParserConfiguration();
        config.setInputStream(new ByteArrayInputStream(data));
        config.setEscalateParsingErrors(true);
        AFPParser parser = new AFPParser(config);

        try {
            while (true) {
                StructuredField sf = parser.parseNextSF();
                if (sf == null) break;
            }
        } catch (AFPParserException e) {
            // Exceptions are expected for random data, we just want to ensure no crashes
        } catch (Throwable t) {
            throw new RuntimeException("Crashed while parsing semi-valid data", t);
        }
    }
}
