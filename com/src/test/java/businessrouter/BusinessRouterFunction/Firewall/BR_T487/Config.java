package businessrouter.BusinessRouterFunction.Firewall.BR_T487;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;
public interface Config {
    public static Map<String, String> NewDeviceInfo = new HashMap<String, String>() {
        {
            put("Status","Allowed");
            put("IP", WebportalParam.brlanclientip);
            put("MAC", "00:0C:29:C6:AD:E4");
            put("Device Name", "AUTO2-ENV5-WAN1");
        
            
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
            put("Dut IP", WebportalParam.brlanconnectip);
            put("Host IP", WebportalParam.brlanconnectip);
            put("Protocol", "ifconfig eth0 down");
   
            
        }
    };
    public static Map<String, String> TmsDownAdpater2Commands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Backend");
            put("Dut IP", WebportalParam.brlanconnectip);
            put("Host IP", WebportalParam.brlanconnectip);
            put("Protocol", "ifconfig eth2 down");
   
            
        }
    };
    public static Map<String, String> TmsRestartAdpaterCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Backend");
            put("Dut IP", WebportalParam.brlanconnectip);
            put("Host IP", WebportalParam.brlanconnectip);
            put("Protocol", "/etc/init.d/networking restart");
   
            
        }
    };
    
    public static Map<String, String> TmsUpAdpaterCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Backend");
            put("Dut IP", WebportalParam.brlanconnectip);
            put("Host IP", WebportalParam.brlanconnectip);
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
