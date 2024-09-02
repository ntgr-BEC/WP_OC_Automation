package businessrouter.BusinessRouterFunction.Firewall.BR_T483;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;
public interface Config {
    public static Map<String, String> NewDeviceInfo = new HashMap<String, String>() {
        {

            put("MAC", "00:0C:29:C6:AD:56");
            put("Device Name", "test1");
        
            
        }
    };
    public static Map<String, String> NewDeviceInfo1 = new HashMap<String, String>() {
        {

            put("MAC", "00:0C:29:C6:AD:78");
            put("Device Name", "test2");
        
            
        }
    };
    public static Map<String, String> TmsGetMacCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Backend");
            put("Dut IP", WebportalParam.brlanclientip);
            put("Host IP", WebportalParam.brlanclientip);
            put("Protocol", "ifconfig eth0");
   
            
        }
    };
    public static Map<String, String> TmsDownAdpaterCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Backend");
            put("Dut IP", WebportalParam.brlanclientip);
            put("Host IP", WebportalParam.brlanclientip);
            put("Protocol", "ifconfig eth0 down");
   
            
        }
    };
    
    public static Map<String, String> TmsUpAdpaterCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Backend");
            put("Dut IP", WebportalParam.brlanclientip);
            put("Host IP", WebportalParam.brlanclientip);
            put("Protocol", "ifconfig eth0 up");
   
            
        }
    };
    public static Map<String, String> TmsTCPCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Net Stat");
            put( "Command", "Outbound");
            put("Dut IP", WebportalParam.brlanclientip);
            put("Host IP", WebportalParam.brwanclientip);
            put("WAN connetc IP", WebportalParam.brwanconnectip);
            put("Protocol", "tcp");
            put("LAN port", "102");
            put("WAN port", "103");
            
        }
    };
}
