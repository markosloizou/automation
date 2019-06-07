import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Agency {
    SHUTTERSTOCK(0),
    DREAMSTIME(1),
    ALAMY(2),
    DEPOSITPHOTOS(3),
    CANSTOCKPHOTOS(4),
    POND5(4),
    ADOBESTOCK(5),
    TEST(100);

    private static final Map<Integer,Agency> lookup
            = new HashMap<Integer,Agency>();

    static {
        for(Agency a: EnumSet.allOf(Agency.class))
            lookup.put(a.getCode(), a);
    }

    private int code;

    private Agency(int code) {
        this.code = code;
    }

    public int getCode() { return code; }

    public static Agency get(int code) {
        return lookup.get(code);
    }
}
