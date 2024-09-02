package businessrouter.BusinessRouterFunction.Firewall.BR_T661;
import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;
public interface Config {
    public static Map<String, String> Firewallrule1 = new HashMap<String, String>() {
        {
          
            put("Rule Name", "test1");
            put("Protocol", "TCP");
            put("Source Zone", "LAN");
            put("Destination Zone", "WAN");
            put("Source address range", "enable");
            put("Source address start", WebportalParam.brlanclientip);
            put("Source address end", WebportalParam.brlanclientip);               
            put("Action", "ACCEPT");
            
            
        }
    };
    public static Map<String, String> Firewallrule2 = new HashMap<String, String>() {
        {
          
            put("Rule Name", "test2");
            put("Source Zone", "LAN");
            put("Source LAN", "LAN");
            put("Destination Zone", "WAN");
            put("Action", "DROP");
            
            
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
            put("WAN port", "220");
            
        }
    };
    public static Map<String, String> TmsChangeIPCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Backend");
            put("Dut IP", WebportalParam.brlanconnectip);
            put("Host IP", WebportalParam.brlanconnectip);
            put("Protocol", "ifconfig eth0 up");
   
            
        }
    };
}