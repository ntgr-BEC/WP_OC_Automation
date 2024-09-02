package businessrouter.BusinessRouterFunction.Advanced_DDNS.BR_T225;

import java.util.HashMap;
import java.util.Map;

public interface Config {
    public static Map<String, String> NewDDNSAccountInfo = new HashMap<String, String>() {
        {            
            put("Host Name","Lisj");
            put("Email", "sujuan.li@netgear.com");
            put("Password", "Shanoon01");
            put("DDNS Flag","Enable");        
        }
    };

}
