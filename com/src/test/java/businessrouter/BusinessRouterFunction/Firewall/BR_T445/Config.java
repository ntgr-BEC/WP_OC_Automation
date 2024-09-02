
package businessrouter.BusinessRouterFunction.Firewall.BR_T445;
import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;
public interface Config {
    public static Map<String, String> PortForwardingRule1 = new HashMap<String, String>() {
        {
          
            put("Rule Name", "test1");
            put("Protocol", "TCP");
            put("ExternalPort", "30000");
            put("Internal IP Address", WebportalParam.serverUrl);
        
            
        }
    };
    public static Map<String, String> PortForwardingRule2 = new HashMap<String, String>() {
        {
          
            put("Rule Name", "test2");
            put("Protocol", "TCP");
            put("ExternalPort", "65534");
            put("Internal IP Address", WebportalParam.serverUrl);
        
            
        }
    };

}
