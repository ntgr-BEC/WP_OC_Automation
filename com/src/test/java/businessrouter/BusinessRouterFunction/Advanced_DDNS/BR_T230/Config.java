package businessrouter.BusinessRouterFunction.Advanced_DDNS.BR_T230;

import java.util.HashMap;
import java.util.Map;

public interface Config {
    public static Map<String, String> NewDDNSAccountInfo = new HashMap<String, String>() {
        {            
            put("Exist Account","Yes");
            put("Host Name","Lisj12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
            put("Email", "123456789012345678901234567890123456@netgear.com");
            put("Password", "12345678901234567890123456789012");
            put("DDNS Flag","Enable");
                    
        }
    };

}
