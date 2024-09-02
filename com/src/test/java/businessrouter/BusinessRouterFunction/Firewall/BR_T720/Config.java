package businessrouter.BusinessRouterFunction.Firewall.BR_T720;

import java.util.HashMap;
import java.util.Map;
public interface Config {
    public static Map<String, String> Firewallrule1 = new HashMap<String, String>() {
        {
          
            put("Rule Name", "~!@#$%^&*()_+");
            put("Protocol", "All");
            put("Source Zone", "LAN");
            put("Destination Zone", "WAN");       
            put("Action", "ACCEPT");
            
            
        }
    };
    public static Map<String, String> Firewallrule2 = new HashMap<String, String>() {
        {
          
            put("Rule Name", "12345678901234567890123456789012");
            put("Protocol", "TCP");
            put("Source Zone", "LAN");
            put("Destination Zone", "WAN");       
            put("Action", "ACCEPT");
            
            
        }
    };
    public static Map<String, String> Firewallrule3 = new HashMap<String, String>() {
        {
          
            put("Rule Name", "test16789012345678901234567890123");
            put("Protocol", "All");
            put("Source Zone", "LAN");
            put("Destination Zone", "WAN");       
            put("Action", "ACCEPT");
            
            
        }
    };

}
