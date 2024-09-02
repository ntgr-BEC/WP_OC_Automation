package businessrouter.BusinessRouterFunction.Firewall.BR_T451;
import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;
public interface Config {
    public static Map<String, String> PortForwardingRule = new HashMap<String, String>() {
        {
          
            put("Rule Name", "test");
            put("ExternalPort", "1024");
            put("Internal IP Address", WebportalParam.serverUrl);
        
            
        }
    };

}
