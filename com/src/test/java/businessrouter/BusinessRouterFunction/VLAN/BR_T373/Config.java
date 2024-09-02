package businessrouter.BusinessRouterFunction.VLAN.BR_T373;

import java.util.HashMap;
import java.util.Map;

public interface Config {
    public static Map<String, String> VLAN = new HashMap<String, String>() {
        {

            put("VLAN ID", "3");
            put("Name", "vlan3");
            put("Ports", "1:TAG;2:TAG;3:TAG;4:TAG");
            put("Description", "add vlan");

        }
    };
    public static Map<String, String> VLANBR100 = new HashMap<String, String>() {
        {

            put("VLAN ID", "3");
            put("Name", "vlan3");
            put("Ports", "1:TAG;2:TAG");
            put("Description", "add vlan");

        }
    };
}
