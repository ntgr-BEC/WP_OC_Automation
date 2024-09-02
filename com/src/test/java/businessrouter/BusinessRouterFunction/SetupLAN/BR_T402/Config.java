package businessrouter.BusinessRouterFunction.SetupLAN.BR_T402;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;

public interface Config {
    public static Map<String, String> TmsICMPCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Backend");
            put("Dut IP", WebportalParam.brlanclientip);
            put("Host IP", WebportalParam.brlanclientip);
            put("Protocol", "ping -s 31 -c 5 "+ WebportalParam.brwanclientip);
   
            
        }
    };

}
