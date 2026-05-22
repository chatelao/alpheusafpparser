import com.mgz.afp.enums.*;
import java.lang.reflect.Field;

public class VerifySFTypeIDs {
    public static void main(String[] args) throws Exception {
        for (SFTypeID id : SFTypeID.values()) {
            if (id == SFTypeID.Undefined) continue;

            // Get private fields via reflection to be sure
            Field fClass = SFTypeID.class.getDeclaredField("sfClass");
            fClass.setAccessible(true);
            SFClass sfClass = (SFClass) fClass.get(id);

            Field fType = SFTypeID.class.getDeclaredField("sfType");
            fType.setAccessible(true);
            SFType sfType = (SFType) fType.get(id);

            Field fCategory = SFTypeID.class.getDeclaredField("sfCategory");
            fCategory.setAccessible(true);
            SFCategory sfCategory = (SFCategory) fCategory.get(id);

            if (sfClass == SFClass.Undefined && id != SFTypeID.Undefined) {
                System.out.println("ID " + id + " has Undefined Class");
            }
            if (sfType == SFType.Undefined) {
                System.out.println("ID " + id + " has Undefined Type");
            }
            if (sfCategory == SFCategory.Undefined) {
                System.out.println("ID " + id + " has Undefined Category");
            }
        }
        System.out.println("Verification complete");
    }
}
