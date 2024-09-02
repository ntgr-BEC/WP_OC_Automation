package businessrouter.BusinessRouterFunction.FirewallBasicSetup.BR_T364;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;
public interface Config {
    public static Map<String, String> DmzInfo = new HashMap<String, String>() {
        {          
            put("DMZ Status","Enable");
            put("DMZ IP", WebportalParam.brlanclientip);
        }
    };
    public static Map<String, String> TmsICMPCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Backend");
            put("Dut IP", WebportalParam.brwanclientip);
            put("Host IP", WebportalParam.brwanclientip);
            put("Protocol", "ping -s 31 -c 5 "+ WebportalParam.brdutwanip);
   
            
        }
    };
    public static Map<String, String> DisabeDmzInfo = new HashMap<String, String>() {
        {          
            put("DMZ Status","Disable");
            put("DMZ IP", WebportalParam.brlanclientip);
        }
    };

}
