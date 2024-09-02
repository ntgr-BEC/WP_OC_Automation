package businessrouter.BusinessRouterFunction.Firewall.BR_T740;

import java.util.HashMap;
import java.util.Map;
public interface Config {
    public static Map<String, String> Firewallrule1 = new HashMap<String, String>() {
        {
          
            put("Rule Name", "test1");
            put("Protocol", "All");
            put("Source Zone", "LAN");       
            put("Action", "ACCEPT");
            
            
        }
    };
    public static Map<String, String> Firewallrule2 = new HashMap<String, String>() {
        {
          
            put("Rule Name", "test1");
            put("Protocol", "All");
            put("Source Zone", "LAN");        
            put("Action", "ACCEPT");
            
            
        }
    };
    

}
