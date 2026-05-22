import com.mgz.afp.enums.*;
import java.lang.reflect.Field;
import java.util.*;

public class CheckDuplicates {
    public static void main(String[] args) throws Exception {
        Map<Integer, List<String>> map = new HashMap<>();
        for (SFTypeID id : SFTypeID.values()) {
            // Use reflection to get the private val from sfClass, sfType, sfCategory
            int cV = getVal(id, "sfClass");
            int tV = getVal(id, "sfType");
            int catV = getVal(id, "sfCategory");

            int key = (cV << 16) | (tV << 8) | catV;
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(id.name());
        }

        for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
            if (entry.getValue().size() > 1 && entry.getKey() != 0) {
                System.out.println("Duplicate key " + Integer.toHexString(entry.getKey()) + ": " + entry.getValue());
            }
        }
    }

    private static int getVal(SFTypeID id, String fieldName) throws Exception {
        Field f = SFTypeID.class.getDeclaredField(fieldName);
        f.setAccessible(true);
        Object obj = f.get(id);
        Field fVal = obj.getClass().getDeclaredField("val");
        fVal.setAccessible(true);
        return (int) fVal.get(obj);
    }
}
