package businessrouter.BusinessRouterFunction.SetupWAN.BR_T16;

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
    
    public static Map<String, String> L2tpServerUser = new HashMap<String, String>() {
        {
          
            put("User Name", "l2tp");
            put("User Type", "7");
            put ("Password", "1");
            put ("Confirm Password", "1");
           
        }
    };
    public static Map<String, String> L2tpServerInfo = new HashMap<String, String>() {
        {
          
            put("Start IP", "30.20.20.2");
            put("End IP", "30.20.20.11");

           
        }
    };
    public static Map<String, String> WanL2tpInfo = new HashMap<String, String>() {
        {
          
            put("Internet Service", "L2TP");
            put("User Name", "l2tp");
            put ("Passeword", "1");
            put ("Connection Mode","Manually Connect");
            put ("My IP",WebportalParam.brpptpl2tpserver);
            put ("Server IP",WebportalParam.brpptpl2tpserver);
            put ("Sub Mask","255.255.255.0");
            put ("Gateway",WebportalParam.brpptpl2tpserver);
           
        }
    };
    
    public static Map<String, String> L2tpIpInfo = new HashMap<String, String>() {
        {
          
            put("Status", "connected");
            put("WAN IP", "30.20.20.2");
            put ("Mask", "255.255.255.255");
            put ("Link Connect", "Disable");
           
           
        }
    };
    public static Map<String, String> L2tpIpdownInfo = new HashMap<String, String>() {
        {
          
            put("Status", "disconnected");
            put("WAN IP", "0.0.0.0");
            put ("Mask", "0.0.0.0");
           
           
        }
    };

}
