package businessrouter.BusinessRouterFunction.SetupWAN.BR_T538;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;

public interface Config {
    public static Map<String, String> WanPortModeInfo = new HashMap<String, String>() {
        {
          
            put("Mode", "DHCP");
            put("DNS Mode", "Automatic");
           
        }
    };
    public static Map<String, String> WanPorInfo = new HashMap<String, String>() {
        {
          
            put("WAN IP", WebportalParam.brwanclientip);
            put("WAN Mask", "255.255.255.0");
            put("WAN Gateway", WebportalParam.brwangateway);
            put("Fisrt DNS", WebportalParam.brwangateway);
           
        }
    };

}
