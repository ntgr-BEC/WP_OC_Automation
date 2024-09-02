package businessrouter.BusinessRouterFunction.SetupLAN.BR_T389;
import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;
public interface Config {
    public static Map<String, String> IPReservation = new HashMap<String, String>() {
        {
          
            put("Device Name", "test");
            put("IP Address", WebportalParam.brlanclientip2);
            put("Warning", "Make sure that the IP address is in the same subnet as the LAN IP address.");
            
            
        }
    };

}
