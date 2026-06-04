package com.mgz.xml;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializer;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.mgz.afp.foca.FND_FontDescriptor;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BlackbirdVerificationTest {

    @Test
    public void verifyBlackbirdIsActive() throws Exception {
        XmlMapper mapper = JacksonXmlMapperProvider.getMapper();

        FND_FontDescriptor fnd = new FND_FontDescriptor();
        fnd.setTypefaceDescription("Test Font");

        mapper.writeValueAsString(fnd);

        DefaultSerializerProvider provider = (DefaultSerializerProvider) mapper.getSerializerProviderInstance();
        JsonSerializer<Object> serializer = provider.findValueSerializer(FND_FontDescriptor.class, null);

        assertNotNull(serializer, "Serializer for FND_FontDescriptor should not be null");

        Field propsField = com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.class.getDeclaredField("_props");
        propsField.setAccessible(true);
        BeanPropertyWriter[] properties = (BeanPropertyWriter[]) propsField.get(serializer);

        boolean foundBlackbirdWriter = false;
        for (BeanPropertyWriter writer : properties) {
            System.err.println("Property: " + writer.getName() + " | Writer class: " + writer.getClass().getName());
            if (writer.getClass().getName().contains("blackbird") || writer.getClass().getName().contains("Blackbird")) {
                foundBlackbirdWriter = true;
            }

            // In some versions, the writer might be standard but it uses a Blackbird-generated accessor internally.
            // Check _accessorMethod or _field
            try {
                Field accessorMethodField = BeanPropertyWriter.class.getDeclaredField("_accessorMethod");
                accessorMethodField.setAccessible(true);
                Object accessor = accessorMethodField.get(writer);
                if (accessor != null) {
                    System.err.println("  Accessor Method: " + accessor.getClass().getName());
                    if (accessor.getClass().getName().contains("Lambda") || accessor.getClass().getName().contains("$$Lambda")) {
                        // Blackbird uses LambdaMetafactory
                        foundBlackbirdWriter = true;
                    }
                }
            } catch (Exception e) { /* ignore */ }
        }

        assertTrue(foundBlackbirdWriter, "Blackbird optimized writers or accessors should be used");
    }
}
