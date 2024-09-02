package businessrouter.BusinessRouterFunction.Security_BlockServices.BR_T556;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;

public interface Config {
    public static Map<String, String> blockservicesconfig = new HashMap<String, String>() {
        {

            put("option", "alw");
            put("servicestype", "HTTPS");
            put("onlyipaddress", "");
            put("ipaddressfrom", "");
            put("ipaddressto", "");
        }
    };
    public static Map<String, String> TmsHttpsCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Net Stat");
            put( "Command", "Outbound");
            put("Dut IP", WebportalParam.brlanclientip);
            put("Host IP", WebportalParam.brwanclientip);
            put("WAN connetc IP", WebportalParam.brwanconnectip);
            put("Protocol", "tcp");
            put("LAN port", "102");
            put("WAN port", "443");
            
        }
    };
    public static Map<String, String> TmsHttpCommands = new HashMap<String, String>() {
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
}
