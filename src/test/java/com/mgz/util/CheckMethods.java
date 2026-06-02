package com.mgz.util;
import org.codehaus.stax2.XMLStreamWriter2;
import java.lang.reflect.Method;
import java.util.Arrays;

public class CheckMethods {
    public static void main(String[] args) {
        try {
            Class<?> clazz = Class.forName("org.codehaus.stax2.XMLStreamWriter2");
            for (Method m : clazz.getMethods()) {
                if (m.getName().startsWith("writeRaw")) {
                    System.out.println(m.getName() + Arrays.toString(m.getParameterTypes()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
