import org.codehaus.stax2.XMLStreamWriter2;
import java.lang.reflect.Method;
import java.util.Arrays;

public class TestStax2Methods {
    public static void main(String[] args) throws Exception {
        for (Method m : XMLStreamWriter2.class.getMethods()) {
            if (m.getName().startsWith("write") && m.getName().contains("Raw")) {
                System.out.println(m.getName() + Arrays.toString(m.getParameterTypes()));
            }
        }
    }
}
