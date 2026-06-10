package com.mgz.xml;

import com.mgz.util.FastIntConverter;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FastIntConverterTest {
    @Test
    public void testWriteLong() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FastIntConverter.writeLong(123456789012345L, baos);
        assertEquals("123456789012345", baos.toString(StandardCharsets.UTF_8));

        baos.reset();
        FastIntConverter.writeLong(-987654321098765L, baos);
        assertEquals("-987654321098765", baos.toString(StandardCharsets.UTF_8));

        baos.reset();
        FastIntConverter.writeLong(0L, baos);
        assertEquals("0", baos.toString(StandardCharsets.UTF_8));

        baos.reset();
        FastIntConverter.writeLong(Long.MIN_VALUE, baos);
        assertEquals("-9223372036854775808", baos.toString(StandardCharsets.UTF_8));

        baos.reset();
        FastIntConverter.writeLong(Long.MAX_VALUE, baos);
        assertEquals("9223372036854775807", baos.toString(StandardCharsets.UTF_8));
    }

    @Test
    public void testLongToUtf8() {
        byte[] buffer = new byte[32];
        int len = FastIntConverter.longToUtf8(123456789012345L, buffer, 0);
        assertEquals("123456789012345", new String(buffer, 0, len, StandardCharsets.UTF_8));

        len = FastIntConverter.longToUtf8(-987654321098765L, buffer, 2);
        assertEquals("-987654321098765", new String(buffer, 2, len, StandardCharsets.UTF_8));
    }
}
