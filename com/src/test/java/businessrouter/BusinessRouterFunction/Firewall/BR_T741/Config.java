package businessrouter.BusinessRouterFunction.Firewall.BR_T741;
import java.util.HashMap;
import java.util.Map;
public interface Config {
    public static Map<String, String> Firewallrule1 = new HashMap<String, String>() {
        {
          
            put("Rule Name", "test1");
            put("Protocol", "UDP");
            put("Source Zone", "LAN");
            put("Destination Zone", "WAN");
            put("Action", "ACCEPT");
            
            
        }
    };
    public static Map<String, String> Firewallrule2 = new HashMap<String, String>() {
        {
          
            put("Rule Name", "test2");
            put("Destination Zone", "WAN");
            put("Action", "DROP");
            
        }
    };
   
}