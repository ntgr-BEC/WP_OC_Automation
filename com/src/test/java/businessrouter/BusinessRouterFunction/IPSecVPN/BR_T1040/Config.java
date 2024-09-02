package businessrouter.BusinessRouterFunction.IPSecVPN.BR_T1040;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;

public interface Config {
    public static Map<String, String> DUTIPSecPolicy = new HashMap<String, String>() {
        {
            put("Rule Status","enable");
            put("Policy Name", "Tunnel1");
            put("Remote Gateway", WebportalParam.bripsecoppositewanip);
            put("Remote Subnet", WebportalParam.bripsecoppositelangateway);
            put("Remote Mask", "255.255.255.0");
            put("Local Subnet", WebportalParam.brlangateway);
            put("Local Mask", "255.255.255.0");
            put("PreShared Key", "12345678");
        
            
        }
    };
    public static Map<String, String> DUTIPSecPolicy2 = new HashMap<String, String>() {
        {
            put("Rule Status","enable");
            put("Policy Name", "Tunnel2");
            put("Remote Gateway", WebportalParam.brwanclientip);
            put("Remote Subnet", WebportalParam.brlanclientip2);
            put("Remote Mask", "255.255.255.0");
            put("Local Subnet", WebportalParam.brlangateway);
            put("Local Mask", "255.255.255.0");
            put("PreShared Key", "1234567889");
            
        }
    };

}