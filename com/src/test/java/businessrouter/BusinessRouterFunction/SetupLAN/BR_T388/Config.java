package businessrouter.BusinessRouterFunction.SetupLAN.BR_T388;
import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;
public interface Config {
    public static Map<String, String> IPReservation = new HashMap<String, String>() {
        {
          
            put("Device Name", "test");
            put("IP Address", WebportalParam.brlanclientip);
            put("MAC Address", "00:1B:21:A1:82:01");
            
            
        }
    };

}
