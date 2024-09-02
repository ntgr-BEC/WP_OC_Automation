package businessrouter.BusinessRouterFunction.Firewall.BR_T469;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;
public interface Config {
    public static Map<String, String> NewAllowDeviceInfo = new HashMap<String, String>() {
        {
            put("Status","Allowed");
            put("IP", WebportalParam.brlanclientip);
            put("MAC", "00:0C:29:C6:AD:E4");
            put("Device Name", "AUTO2-ENV5-WAN1");
        
            
        }
    };
    public static Map<String, String> NewBlockedDeviceInfo = new HashMap<String, String>() {
        {
            put("Status","Blocked");
            put("IP", WebportalParam.brlanclientip);
            put("MAC", "00:0C:29:C6:AD:E4");
            put("Device Name", "AUTO2-ENV5-WAN1");
        
            
        }
    };
    public static Map<String, String> NewDeviceInfo1 = new HashMap<String, String>() {
        {
            put("Status","Block");
            put("IP", WebportalParam.brlanclientip);
            put("MAC", "00:0C:29:C6:AD:E4");
            put("Device Name", "AUTO2-ENV5-WAN1");
        
            
        }
    };
    public static Map<String, String> NewDeviceInfo2 = new HashMap<String, String>() {
        {
            put("Status","Allow");
            put("IP", WebportalParam.brlanclientip);
            put("MAC", "00:0C:29:C6:AD:E4");
            put("Device Name", "AUTO2-ENV5-WAN1");
        
            
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
    public static Map<String, String> TmsGetMacCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Backend");
            put("Dut IP", WebportalParam.brlanclientip);
            put("Host IP", WebportalParam.brlanclientip);
            put("Protocol", "ifconfig eth0");
   
            
        }
    };
}
