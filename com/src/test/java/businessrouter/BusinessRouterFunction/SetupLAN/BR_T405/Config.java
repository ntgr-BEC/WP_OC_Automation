package businessrouter.BusinessRouterFunction.SetupLAN.BR_T405;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;

public interface Config {
    public static Map<String, String> TmsGeteth0MacCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Backend");
            put("Dut IP", WebportalParam.brlanclientip);
            put("Host IP", WebportalParam.brlanclientip);
            put("Protocol", "ifconfig eth0");
   
            
        }
    };
    public static Map<String, String> TmsGeteth2MacCommands = new HashMap<String, String>() {
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
            //put("IP Address", WebportalParam.brlanclientip);
            put("MAC Address", "00:1B:21:A1:82:01");
            
            
        }
    };
    public static Map<String, String> IPReservation2 = new HashMap<String, String>() {
        {
          
            put("Exist IP Reservation Number", "1");
            //put("IP Address", WebportalParam.brlanclientip);
            put("MAC Address", "00:1B:21:A1:82:01");
            
            
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
    
    public static Map<String, String> TmsUpAdpaterCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Backend");
            put("Dut IP", WebportalParam.brlanclientip);
            put("Host IP", WebportalParam.brlanclientip);
            put("Protocol", "ifconfig eth2 up");
   
            
        }
    };
}
