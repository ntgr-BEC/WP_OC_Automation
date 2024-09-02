package businessrouter.BusinessRouterFunction.Advanced_StaticRoutes.BR_T90;
import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;
public interface Config {
    public static Map<String, String> STATICROUTE1 = new HashMap<String, String>() {
        {
          
            put("Route Name", "test");
            put("Metric", "2");
            put("IP Subnet Mask", "255.255.255.255");
            put("Gateway IP Address", WebportalParam.brlanclientip);
            put("Destination IP Address", "0.0.0.0");
            
            
        }
    };
}
