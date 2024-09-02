package businessrouter.BusinessRouterFunction.IPSecVPN.BR_T1096;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;

public interface Config {
    public static Map<String, String> DUT1IPSecPolicy = new HashMap<String, String>() {
        {
            put("Rule Status","enable");
            put("Policy Name", "T123");
            put("Remote Gateway", "Test123.com");
            put("Remote Subnet", WebportalParam.bripsecoppositelangateway);
            put("Remote Mask", "255.255.255.0");
            put("Local Subnet", WebportalParam.brlangateway);
            put("Local Mask", "255.255.255.0");
            put("PreShared Key", "Test12345e");
            put ("Ph2 SA Lifetime","0");
        
            
        }
    };
    public static Map<String, String> DUT1IPSecPolicy2 = new HashMap<String, String>() {
        {
            put("Rule Status","enable");
            put("Policy Name", "T123");
            put("Remote Gateway", "Test123.com");
            put("Remote Subnet", WebportalParam.bripsecoppositelangateway);
            put("Remote Mask", "255.255.255.0");
            put("Local Subnet", WebportalParam.brlangateway);
            put("Local Mask", "255.255.255.0");
            put("PreShared Key", "Test12345e");
            put ("Ph2 SA Lifetime","999999");
        
            
        }
    };
    public static Map<String, String> DUT1IPSecPolicy3 = new HashMap<String, String>() {
        {
            put("Rule Status","enable");
            put("Policy Name", "T123");
            put("Remote Gateway", "Test123.com");
            put("Remote Subnet", WebportalParam.bripsecoppositelangateway);
            put("Remote Mask", "255.255.255.0");
            put("Local Subnet", WebportalParam.brlangateway);
            put("Local Mask", "255.255.255.0");
            put("PreShared Key", "Test12345e");
            put ("Ph2 SA Lifetime","604800");
        
            
        }
    };
    public static Map<String, String> DUT1IPSecPolicy4 = new HashMap<String, String>() {
        {
            put("Rule Status","enable");
            put("Policy Name", "T123");
            put("Remote Gateway", "Test123.com");
            put("Remote Subnet", WebportalParam.bripsecoppositelangateway);
            put("Remote Mask", "255.255.255.0");
            put("Local Subnet", WebportalParam.brlangateway);
            put("Local Mask", "255.255.255.0");
            put("PreShared Key", "Test12345e");
            put ("Ph2 SA Lifetime","600");
        
            
        }
    };
}