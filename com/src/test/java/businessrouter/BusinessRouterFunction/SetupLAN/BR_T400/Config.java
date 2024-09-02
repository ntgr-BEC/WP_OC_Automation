package businessrouter.BusinessRouterFunction.SetupLAN.BR_T400;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;

public interface Config {
    public static Map<String, String> LANIPInfo = new HashMap<String, String>() {
        {
        
            put("LAN IP", WebportalParam.brwanclientip);
            put("Subnet Mask", "255.255.255.0");
            
            
        }
    };
    public static Map<String, String> LANIPInfo1 = new HashMap<String, String>() {
        {
        
            put("LAN IP", WebportalParam.brlanclientip);
            put("Subnet Mask", "255.255.255.0");
            
            
        }
    };
    public static Map<String, String> LANIPInfodefault = new HashMap<String, String>() {
        {
        
            put("LAN IP", WebportalParam.brlangateway);
            put("Subnet Mask", "255.255.255.0");
            
            
        }
    };


}
