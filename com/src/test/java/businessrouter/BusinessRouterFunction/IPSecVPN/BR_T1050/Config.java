package businessrouter.BusinessRouterFunction.IPSecVPN.BR_T1050;

import java.util.HashMap;
import java.util.Map;

public interface Config {
    public static Map<String, String> DUT1IPSecPolicy = new HashMap<String, String>() {
        {
            put("Rule Status","enable");
            put("Policy Name", "T123");
            put("Remote Gateway", "Test123weruqw.com");
            put("Remote Subnet","192.168.1.0");
            put("Remote Mask", "255.255.255.0");
            put("Local Subnet", "192.168.1.0");
            put("Local Mask", "255.255.255.0");
            put("PreShared Key", "12345678");
        
            
        }
    };

}