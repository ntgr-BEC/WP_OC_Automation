package businessrouter.BusinessRouterFunction.VLAN.BR_T384;

import java.util.HashMap;
import java.util.Map;

public interface Config {
    public static Map<String, String> VLAN = new HashMap<String, String>() {
        {

            put("VLAN ID", "1");
            put("Name", "vlan1");
            put("Ports", "1:UNTAG;2:UNTAG;4:UNTAG");
            put("Description", "vlan for lan");

        }
    };
    public static Map<String, String> VLANBR100 = new HashMap<String, String>() {
        {

            put("VLAN ID", "1");
            put("Name", "vlan1");
            put("Ports", "2:UNTAG");
            put("Description", "vlan for lan");

        }
    };
}
