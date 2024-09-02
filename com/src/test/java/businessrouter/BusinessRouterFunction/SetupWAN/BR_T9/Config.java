package businessrouter.BusinessRouterFunction.SetupWAN.BR_T9;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;

public interface Config {
    public static Map<String, String> WanPortDhcpModeInfo = new HashMap<String, String>() {
        {
          
            put("Mode", "DHCP");
            put("DNS Mode", "Automatic");
           
        }
    };
    public static Map<String, String> WanPorDhcpInfo = new HashMap<String, String>() {
        {
          
            put("WAN IP", WebportalParam.brwanclientip);
            put("WAN Mask", "255.255.255.0");
            put("WAN Gateway", WebportalParam.brwangateway);
            put("Fisrt DNS", WebportalParam.brwangateway);
           
        }
    };
  
    public static Map<String, String> WanPorReleaseInfo = new HashMap<String, String>() {
        {
          
            put("WAN IP", "0.0.0.0");
            put("WAN Mask", "0.0.0.0");
            put("WAN Gateway", "0.0.0.0");
            put("Fisrt DNS", "0.0.0.0");
           
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
            put("WAN port", "80");
            
        }
    };
    public static Map<String, String> WanPortModeInfodefault = new HashMap<String, String>() {
        {
          
            put("Mode", "DHCP");
            put("DNS Mode", "Automatic");
           
        }
    };
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

}
