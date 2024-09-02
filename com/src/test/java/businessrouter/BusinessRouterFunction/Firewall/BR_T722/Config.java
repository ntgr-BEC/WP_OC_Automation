package businessrouter.BusinessRouterFunction.Firewall.BR_T722;

import java.util.HashMap;
import java.util.Map;
public interface Config {
    public static Map<String, String> Firewallrule1 = new HashMap<String, String>() {
        {
          
            put("Rule Name", "test1");
            put("Protocol", "TCP");
            put("Source Zone", "LAN");
            put("Destination Zone", "WAN");
            put("Destination Port", "0");        
            put("Action", "ACCEPT");
            
            
        }
    };
    public static Map<String, String> Firewallrule2 = new HashMap<String, String>() {
        {
          
            put("Rule Name", "test2");
            put("Protocol", "TCP");
            put("Source Zone", "LAN");
            put("Destination Zone", "WAN");
            put("Destination Port", "1");       
            put("Action", "ACCEPT");
            
            
        }
    };
    public static Map<String, String> Firewallrule3 = new HashMap<String, String>() {
        {
          
            put("Rule Name", "test3");
            put("Protocol", "TCP");
            put("Source Zone", "LAN");
            put("Destination Zone", "WAN");
            put("Destination Port", "65535");      
            put("Action", "ACCEPT");
            
            
        }
    };
    public static Map<String, String> Firewallrule4 = new HashMap<String, String>() {
        {
          
            put("Rule Name", "test4");
            put("Protocol", "TCP");
            put("Source Zone", "LAN");
            put("Destination Zone", "WAN");
            put("Destination Port", "65536");      
            put("Action", "ACCEPT");
            
            
        }
    };

}
