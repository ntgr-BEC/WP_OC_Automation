package businessrouter.BusinessRouterFunction.Security_BlockSites.BR_T48;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;

public interface Config {
    public static Map<String, String> TmsUrlCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Backend");
            put("Dut IP", WebportalParam.brlanclientip);
            put("Host IP", WebportalParam.brlanclientip);
            put("Protocol", "curl http://renren.com");
   
            
        }
    };

}
