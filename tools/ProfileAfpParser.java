import java.io.*;
import java.nio.charset.Charset;
import com.mgz.afp.base.StructuredField;
import com.mgz.afp.parser.AFPParser;
import com.mgz.afp.parser.AFPParserConfiguration;

public class ProfileAfpParser {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Usage: ProfileAfpParser <afp-file>");
            System.exit(1);
        }
        File afpFile = new File(args[args.length - 1]);

        boolean useBuffer = false;
        for (String arg : args) {
            if ("--buffer".equals(arg)) {
                useBuffer = true;
            }
        }

        // Warmup
        for (int i = 0; i < 5; i++) {
            run(afpFile, useBuffer);
        }

        // Real runs
        long total = 0;
        int n = 10;
        for (int i = 0; i < n; i++) {
            total += run(afpFile, useBuffer);
        }

        System.out.printf("Average processing time over %d runs: %.3f ms (useBuffer=%b)%n",
            n, total / (double)n, useBuffer);
    }

    private static long run(File afpFile, boolean useBuffer) throws Exception {
        long start = System.nanoTime();
        AFPParserConfiguration config = new AFPParserConfiguration();
        config.setAfpCharSet(Charset.forName("cp273"));

        if (useBuffer) {
            config.setAFPFile(afpFile);
            config.getByteBuffer();
        } else {
            InputStream is = new BufferedInputStream(new FileInputStream(afpFile));
            config.setInputStream(is);
        }

        AFPParser parser = new AFPParser(config);
        int sfCount = 0;
        StructuredField sf;
        while ((sf = parser.parseNextSF()) != null) {
            sfCount++;
            sf.release();
        }
        long end = System.nanoTime();
        return (end - start) / 1_000_000;
    }
}
