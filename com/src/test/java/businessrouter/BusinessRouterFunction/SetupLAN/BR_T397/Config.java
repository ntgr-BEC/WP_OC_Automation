package businessrouter.BusinessRouterFunction.SetupLAN.BR_T397;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;

public interface Config {
    public static Map<String, String> TmsGetIpAndMacCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Backend");
            put("Dut IP", WebportalParam.brlanclientip);
            put("Host IP", WebportalParam.brlanclientip);
            put("Protocol", "ifconfig eth2");
   
            
        }
    };
   
    public static Map<String, String> IPReservation = new HashMap<String, String>() {
        {
          
            put("Device Name", "AUTO2-ENV5-WAN1");
            put("IP Address", WebportalParam.brlanclientip);
            put("MAC Address", "00:0C:29:C6:AD:F8");
            
            
        }
    };
   
    public static Map<String, String> TmsRestartAdpaterCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Backend");
            put("Dut IP", WebportalParam.brlanclientip);
            put("Host IP", WebportalParam.brlanclientip);
            put("Protocol", "/etc/init.d/networking restart");
   
            
        }
    };
    
    public static Map<String, String> TmsUpAdpaterCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Backend");
            put("Dut IP", WebportalParam.brlanclientip);
            put("Host IP", WebportalParam.brlanclientip);
            put("Protocol", "ifconfig eth2 up");
   
            
        }
    };
    public static Map<String, String> TmsDownAdpaterCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Backend");
            put("Dut IP", WebportalParam.brlanclientip);
            put("Host IP", WebportalParam.brlanclientip);
            put("Protocol", "ifconfig eth2 down");
   
            
        }
    };
}
