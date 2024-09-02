package businessrouter.BusinessRouterFunction.Firewall.BR_T683;
import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;
public interface Config {
    public static Map<String, String> Firewallrule1 = new HashMap<String, String>() {
        {
          
            put("Rule Name", "test1");
            put("Protocol", "TCP+UDP");
            put("Source Zone", "LAN");
            put("Destination Zone", "WAN");
            put("Destination Port", "1000");         
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
           // put("LAN port", "21");
            put("WAN port", "1000");
            
        }
    };
    public static Map<String, String> TmsTCPCommands2 = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Net Stat");
            put( "Command", "Outbound");
            put("Dut IP", WebportalParam.brlanclientip);
            put("Host IP", WebportalParam.brwanclientip);
            put("WAN connetc IP", WebportalParam.brwanconnectip);
            put("Protocol", "tcp");
            put("LAN port", "22");
            put("WAN port", "1001");
            
        }
    };
}