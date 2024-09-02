package businessrouter.BusinessRouterFunction.IPSecVPN.BR_T1062;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;

public interface Config {
    public static Map<String, String> DUT1IPSecPolicy = new HashMap<String, String>() {
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
    public static Map<String, String> DUT1IPSecPolicy2 = new HashMap<String, String>() {
        {
            put("Rule Status","enable");
            put("Policy Name", "Tunnel2");
            put("Remote Gateway", WebportalParam.brwanclientip);
            put("Remote Subnet", WebportalParam.brlangateway);
            put("Remote Mask", "255.255.255.0");
            put("Local Subnet", WebportalParam.brpptpl2tpserversub);
            put("Local Mask", "255.255.255.0");
            put("PreShared Key", "12345678");
            
        }
    };


}