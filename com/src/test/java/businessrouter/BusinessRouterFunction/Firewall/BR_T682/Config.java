package businessrouter.BusinessRouterFunction.Firewall.BR_T682;
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
            put("Destnation address range", "enable");
            put("Destnation address start", WebportalParam.brwanclientip);
            put("Destnation address end", WebportalParam.brwanclientip);               
            put("Action", "DROP");
            
            
        }
    };
    public static Map<String, String> Firewallrule2 = new HashMap<String, String>() {
        {
          
            put("Rule Name", "test1");
            put("Destnation address start", WebportalParam.brwanclientip);
            put("Destnation address end", WebportalParam.brwanclientip); 
            put("Action", "DROP");
            
            
        }
    };
   
    public static Map<String, String> TmsGetMacCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Backend");
            put("Dut IP", WebportalParam.brlanclientip);
            put("Host IP", WebportalParam.brlanclientip);
            put("Protocol", "ifconfig eth0 ");
   
            
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
    public static Map<String, String> TmsICMPCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Backend");
            put("Dut IP", WebportalParam.brlanclientip);
            put("Host IP", WebportalParam.brlanclientip);
            put("Protocol", "ping -s 31 -c 5 "+ WebportalParam.brwanclientip);
   
            
        }
    };
}