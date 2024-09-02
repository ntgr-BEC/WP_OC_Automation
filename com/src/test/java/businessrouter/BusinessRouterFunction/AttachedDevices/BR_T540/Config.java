package businessrouter.BusinessRouterFunction.AttachedDevices.BR_T540;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;

public interface Config {
    public static Map<String, String> NewDeviceInfo = new HashMap<String, String>() {
        {
            
            put("IP","IP:"+ WebportalParam.brlanclientip);
            put("MAC", "MAC:00:0C:29:C6:AD:E4");
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
}
