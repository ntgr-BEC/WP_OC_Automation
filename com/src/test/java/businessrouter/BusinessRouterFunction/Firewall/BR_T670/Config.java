package businessrouter.BusinessRouterFunction.Firewall.BR_T670;
import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;
public interface Config {
    public static Map<String, String> Firewallrule = new HashMap<String, String>() {
        {
          
            put("Rule Name", "test");
            put("Source Zone", "LAN");
            put("Destination Zone", "WAN");
            put("Source address", WebportalParam.brlanclientip);
            
        }
    };

}
