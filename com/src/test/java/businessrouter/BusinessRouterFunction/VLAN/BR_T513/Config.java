package businessrouter.BusinessRouterFunction.VLAN.BR_T513;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;

public interface Config {
    public static Map<String, String> VlanPortInfo = new HashMap<String, String>() {
        {
          
            put("VLAN ID", "1");
            put("Ports", WebportalParam.brcleintport2+":TAG");
            
            
        }
    };
    public static Map<String, String> NewLANInfo = new HashMap<String, String>() {
        {
          
            put("VLAN ID", "8");
            put("DHCP", "enable");
            put("Start IP", "2");
            put("End IP", "200");
            
        }
    };
    public static Map<String, String> NewVLANInfo = new HashMap<String, String>() {
        {
          
            put("VLAN ID", "8");
            put("Name", "test");
            put("Ports", WebportalParam.brcleintport2+":UNTAG");
            put("Description", "add vlan");
            
            
        }
    };
    public static Map<String, String> IPReservation = new HashMap<String, String>() {
        {
            put("LAN", "LAN2");
            put("Device Name", "test");
            put("IP Address", WebportalParam.brlanclientip);
            put("MAC Address", "00:1B:21:A1:82:01");
            
            
        }
    };
    public static Map<String, String> TmsGetIpAndMacCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Backend");
            put("Dut IP", WebportalParam.brlanclientip2);
            put("Host IP", WebportalParam.brlanclientip2);
            put("Protocol", "ifconfig eth2");
   
            
        }
    };
    public static Map<String, String> TmsRestartAdpaterCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Backend");
            put("Dut IP", WebportalParam.brlanclientip2);
            put("Host IP", WebportalParam.brlanclientip2);
            put("Protocol", "/etc/init.d/networking restart");
   
            
        }
    };
   

}
