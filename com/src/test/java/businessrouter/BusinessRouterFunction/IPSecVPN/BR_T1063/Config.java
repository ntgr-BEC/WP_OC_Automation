package businessrouter.BusinessRouterFunction.IPSecVPN.BR_T1063;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;

public interface Config {
   
        public static Map<String, String> DUTIPSecPolicy = new HashMap<String, String>() {
            {
                put("Rule Status","enable");
                put("Policy Name", "Tunnel");
                put("Remote Gateway", "test.com");
                put("Remote Subnet", WebportalParam.bripsecoppositelangateway);
                put("Remote Mask", "255.255.255.0");
                put("Local Subnet", WebportalParam.brlangateway);
                put("Local Mask", "255.255.255.0");
                put("PreShared Key", "12345678");
            
                
            }
        };

}
