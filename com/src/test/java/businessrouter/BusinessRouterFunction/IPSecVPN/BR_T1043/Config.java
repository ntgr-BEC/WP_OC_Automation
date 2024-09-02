package businessrouter.BusinessRouterFunction.IPSecVPN.BR_T1043;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;

public interface Config {
    public static Map<String, String> DUT1IPSecPolicy = new HashMap<String, String>() {
        {
            put("Rule Status","enable");
            put("Policy Name", "T12345678901234567890123456789012");
            put("Remote Gateway", WebportalParam.bripsecoppositewanip);
            put("Remote Subnet", WebportalParam.bripsecoppositelangateway);
            put("Remote Mask", "255.255.255.0");
            put("Local Subnet", WebportalParam.brlangateway);
            put("Local Mask", "255.255.255.0");
            put("PreShared Key", "12345678");
        
            
        }
    };
}
