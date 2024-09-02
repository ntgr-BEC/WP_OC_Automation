package businessrouter.BusinessRouterFunction.SetupWAN.BR_T15;

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
    public static Map<String, String> WanPptpInfo = new HashMap<String, String>() {
        {
          
            put("Internet Service", "L2TP");
            put("User Name", "l2tp");
            put ("Passeword", "1");
            put ("Connection Mode","Dial on Demand");
            put ("My IP",WebportalParam.brpptpl2tpserver);
            put ("Server IP",WebportalParam.brpptpl2tpserver);
            put ("Sub Mask","255.255.255.0");
            put ("Gateway",WebportalParam.brpptpl2tpserver);
           
        }
    };
    public static Map<String, String> TmsICMPCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Backend");
            put("Dut IP", WebportalParam.brlanclientip);
            put("Host IP", WebportalParam.brlanclientip);
            put("Protocol", "ping -s 31 -c 5 "+ WebportalParam.brpptpl2tpserversub);
   
            
        }
    };
    
    public static Map<String, String> L2tpIpInfo = new HashMap<String, String>() {
        {
          
            put("Status", "connected");
            put("WAN IP", "30.20.20.2");
            put ("Mask", "255.255.255.255");
           
           
        }
    };

}
