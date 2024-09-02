package businessrouter.BusinessRouterFunction.Firewall.BR_T667;
import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;
public interface Config {
    public static Map<String, String> Firewallrule1 = new HashMap<String, String>() {
        {
          
            put("Rule Name", "test1");
            put("Protocol", "UDP");
            put("Source Zone", "LAN");
            put("Destination Zone", "WAN");
            put("Destination Port", "1000-1010");         
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
   

    public static Map<String, String> TmsUDPCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Net Stat");
            put( "Command", "Outbound");
            put("Dut IP", WebportalParam.brlanclientip);
            put("Host IP", WebportalParam.brwanclientip);
            put("WAN connetc IP", WebportalParam.brwanconnectip);
            put("Protocol", "udp");
            put("LAN port", "102");
            put("WAN port", "1005");
            
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
            put("WAN port", "1000");
            
        }
    };
    public static Map<String, String> TmsChangeIPCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Backend");
            put("Dut IP", WebportalParam.brwanconnectip);
            put("Host IP", WebportalParam.brwanconnectip);
            put("Protocol", "ifconfig eth0 up");
   
            
        }
    };
}