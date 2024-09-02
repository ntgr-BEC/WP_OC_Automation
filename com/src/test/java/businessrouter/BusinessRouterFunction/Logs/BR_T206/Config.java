package businessrouter.BusinessRouterFunction.Logs.BR_T206;
import java.util.HashMap;
import java.util.Map;
public interface Config {
    
    public static Map<String, String> VLAN = new HashMap<String, String>() {
        {
          
            put("VLAN ID", "3");
            put("Name", "vlan3");
            put("Ports", "3:TAG");
            put("Description", "add vlan");
            
            
        }
    };
}