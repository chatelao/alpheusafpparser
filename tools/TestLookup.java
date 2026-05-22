import com.mgz.afp.enums.SFTypeID;
import com.mgz.afp.base.StructuredFieldIntroducer;
import java.io.ByteArrayInputStream;

public class TestLookup {
    public static void main(String[] args) throws Exception {
        System.out.println("BDT directly: " + SFTypeID.BDT_BeginDocument);
        byte[] bdtHeader = {0x00, 0x10, (byte)0xD3, (byte)0xA8, (byte)0xA8, 0x00, 0x00, 0x00};
        StructuredFieldIntroducer sfi = StructuredFieldIntroducer.parse(new ByteArrayInputStream(bdtHeader));
        System.out.println("Parsed BDT TypeID: " + sfi.getSFTypeID());
        if (sfi.getSFTypeID() == SFTypeID.Undefined) {
            System.out.println("FAILED: Got Undefined");
            System.exit(1);
        } else {
            System.out.println("SUCCESS");
        }
    }
}
