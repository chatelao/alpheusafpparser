package com.mgz.performance;

import com.mgz.afp.base.handler.HandlerFactory;
import com.mgz.afp.base.handler.StructuredFieldHandler;
import com.mgz.pdf.PdfHandler;
import com.mgz.pdf.PdfHandlerFactory;
import com.mgz.xml.JacksonXmlMapperProvider;
import com.mgz.xml.XmlHandlerFactory;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ResourceReuseTest {

    @Test
    public void testJacksonWriterReuse() throws Exception {
        XmlHandlerFactory factory = new XmlHandlerFactory();

        // Initial state of caches might be empty or partially filled from other tests
        int initialCacheSize = getJacksonWriterCacheSize();

        try (StructuredFieldHandler handler = factory.createHandler(new ByteArrayOutputStream(), false)) {
            // Use a real SF to ensure the cache is exercised
            com.mgz.afp.modca.BPG_BeginPage bpg = new com.mgz.afp.modca.BPG_BeginPage();
            handler.handle(bpg);
        }

        int afterFirstHandler = getJacksonWriterCacheSize();

        try (StructuredFieldHandler handler = factory.createHandler(new ByteArrayOutputStream(), false)) {
        }

        int afterSecondHandler = getJacksonWriterCacheSize();

        assertEquals(afterFirstHandler, afterSecondHandler, "Jackson writer cache size should not increase after second handler creation if types are same");
    }

    @Test
    public void testPdfFontRegistryReuse() throws Exception {
        PdfHandlerFactory factory = new PdfHandlerFactory();

        PdfHandler handler1 = (PdfHandler) factory.createHandler(new ByteArrayOutputStream(), false);
        PdfHandler handler2 = (PdfHandler) factory.createHandler(new ByteArrayOutputStream(), false);

        assertSame(handler1.getFontRegistry(), handler2.getFontRegistry(), "PDF font registries should be reused");

        handler1.close();
        handler2.close();
    }

    @SuppressWarnings("unchecked")
    private int getJacksonWriterCacheSize() throws Exception {
        Field field = JacksonXmlMapperProvider.class.getDeclaredField("WRITER_CACHE");
        field.setAccessible(true);
        Map<?, ?> cache = (Map<?, ?>) field.get(null);
        return cache.size();
    }
}
