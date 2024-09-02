package businessrouter.BusinessRouterFunction.SetupWAN.BR_T20;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;

public interface Config {
    public static Map<String, String> WanPortModeInfo = new HashMap<String, String>() {
        {
          
            put("Mode", "Static");
            put("WAN IP", WebportalParam.brdutwanip);
            put("WAN Mask", "255.255.255.0");
            put("WAN Gateway", WebportalParam.brwangateway);
            put("DNS Mode", "Static");
            put("Fisrt DNS", WebportalParam.brwangateway);
        }
    };
    

}
