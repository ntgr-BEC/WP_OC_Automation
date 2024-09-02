package businessrouter.BusinessRouterFunction.Firewall.BR_T442;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;
public interface Config {
    public static Map<String, String> Firewallinboundhttp = new HashMap<String, String>() {
        {
          
            put("Rule Name", "HTTP");
            put("Protocol", "TCP");
            put("ExternalPort", "80");
            put("Internal IP Address", WebportalParam.brlanclientip);
            
            
        }
    };
    
    public static Map<String, String> TmsTCPHTTPCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Net Stat");
            put( "Command", "Outbound");
            put("Dut IP", WebportalParam.brwanclientip);
            put("Host IP", WebportalParam.brdutwanip);
            put("WAN connetc IP", WebportalParam.brlanconnectip);
            put("Protocol", "tcp");
            put("WAN port", "80");
            
        }
    };
    
    
}