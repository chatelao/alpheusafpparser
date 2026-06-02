import org.codehaus.stax2.XMLOutputFactory2;
import org.codehaus.stax2.XMLStreamWriter2;
import java.io.FileOutputStream;

public class TestStax2 {
    public static void main(String[] args) throws Exception {
        XMLOutputFactory2 factory = (XMLOutputFactory2) javax.xml.stream.XMLOutputFactory.newInstance();
        XMLStreamWriter2 writer = (XMLStreamWriter2) factory.createXMLStreamWriter(new FileOutputStream("test.xml"));
        writer.writeStartDocument();
        writer.writeStartElement("root");
        try {
            java.lang.reflect.Method m = writer.getClass().getMethod("writeRawCharacters", String.class);
            System.out.println("Method exists: " + m);
        } catch (NoSuchMethodException e) {
            System.out.println("Method writeRawCharacters(String) NOT found");
        }
        writer.writeEndElement();
        writer.writeEndDocument();
        writer.close();
    }
}
