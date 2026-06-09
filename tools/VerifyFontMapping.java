
import com.itextpdf.io.font.constants.StandardFonts;
import com.mgz.pdf.PdfFontRegistry;
import java.lang.reflect.Method;

public class VerifyFontMapping {
    public static void main(String[] args) throws Exception {
        PdfFontRegistry registry = new PdfFontRegistry();

        String[] testNames = {
            "X0H20008",
            "X0H20012",
            "X0H20024",
            "X0H30012"
        };

        Method mapMethod = PdfFontRegistry.class.getDeclaredMethod("mapToStandardFont", String.class);
        mapMethod.setAccessible(true);

        for (String name : testNames) {
            Object result = mapMethod.invoke(registry, name);
            System.out.println("Mapping " + name + " -> " + (result != null ? result.getClass().getName() : "null"));

            // We can't easily get the name from PdfFont without more reflection or knowing iText internals
            // but we can check the size from extractSizeFromName
            System.out.println("  Extracted size: " + PdfFontRegistry.extractSizeFromName(name));
        }
    }
}
