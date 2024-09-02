package businessrouter.BusinessRouterFunction.SetupLAN.BR_T401;
import java.util.HashMap;
import java.util.Map;
public interface Config {
    public static Map<String, String> IPRange1 = new HashMap<String, String>() {
        {
            put("Start IP", "253");
            put("End IP", "254");
            
            
        }
    };
    public static Map<String, String> IPRange2 = new HashMap<String, String>() {
        {
            put("Start IP", "10");
            put("End IP", "100");
            
            
        }
    };
    public static Map<String, String> IPRange3 = new HashMap<String, String>() {
        {
            put("Start IP", "2");
            put("End IP", "258");
            
            
        }
    };
    public static Map<String, String> IPRangedefault = new HashMap<String, String>() {
        {
            put("Start IP", "2");
            put("End IP", "253");
            
            
        }
    };
}
