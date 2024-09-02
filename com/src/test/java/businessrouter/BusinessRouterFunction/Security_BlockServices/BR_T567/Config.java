package businessrouter.BusinessRouterFunction.Security_BlockServices.BR_T567;

import java.util.HashMap;
import java.util.Map;

public interface Config {
    public static Map<String, String> blockservicesconfig1 = new HashMap<String, String>() {
        {

            put("option", "alw");
            put("servicestype", "HTTPS");
            put("onlyipaddress", "");
            put("ipaddressfrom", "");
            put("ipaddressto", "");
        }
    };
    
    public static Map<String, String> blockservicesconfig2 = new HashMap<String, String>() {
        {

            put("servicestype", "HTTPS");
            put("onlyipaddress", "");
            put("ipaddressfrom", "");
            put("ipaddressto", "");
        
        }
    };
}
