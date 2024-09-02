package businessrouter.BusinessRouterFunction.Firewall.BR_T685;
import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;
public interface Config {
    public static Map<String, String> Firewallrule1 = new HashMap<String, String>() {
        {
          
            put("Rule Name", "test1");
            put("Protocol", "All");
            put("Source Zone", "LAN");        
            put("Destination Zone", "WAN");
            put("Source address", WebportalParam.brlanclientip);
            put("Destnation Address", WebportalParam.brwanclientip);                         
            put("Action", "DROP");
            
            
        }
    };
    public static Map<String, String> Firewallrule2 = new HashMap<String, String>() {
        {
          
            put("Rule Name", "test1");
            put("Source address", WebportalParam.brlanclientip);
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
   
}