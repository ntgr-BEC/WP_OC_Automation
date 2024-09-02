package businessrouter.BusinessRouterFunction.IPSecVPN.BR_T1045;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;

public interface Config {
    public static Map<String, String> DUT1IPSecPolicy = new HashMap<String, String>() {
        {
            put("Rule Status","enable");
            put("Policy Name", "T123");
            put("Remote Gateway", "Test1234567890123347329132hkhfksajhdfakdfoqiweurqoweurqwoieurqoweruqoweruqwoeurqoiewurqowieurqoiweurqoieruqoeruqoierqeiurff5.com");
            put("Remote Subnet", WebportalParam.bripsecoppositelangateway);
            put("Remote Mask", "255.255.255.0");
            put("Local Subnet", WebportalParam.brlangateway);
            put("Local Mask", "255.255.255.0");
            put("PreShared Key", "12345678");
        
            
        }
    };

}
