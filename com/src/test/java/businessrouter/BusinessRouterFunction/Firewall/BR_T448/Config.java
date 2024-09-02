package businessrouter.BusinessRouterFunction.Firewall.BR_T448;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;
public interface Config {
    public static Map<String, String> Firewallinboundftp = new HashMap<String, String>() {
        {
          
            put("Rule Name", "FTP");
            put("Protocol", "TCP");
            put("ExternalPort", "21");
            put("Internal IP Address", WebportalParam.brlanclientip2);
            
            
        }
    };
    public static Map<String, String> Firewallinboundftp2 = new HashMap<String, String>() {
        {
          
            put("Rule Name", "FTP");
            put("Protocol", "TCP");
            put("ExternalPort", "21");
            put("attached devices", WebportalParam.brlanclientip);
            
            
        }
    };
    
    public static Map<String, String> TmsTCPFTPCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Net Stat");
            put( "Command", "Outbound");
            put("Dut IP", WebportalParam.brwanclientip);
            put("Host IP", WebportalParam.brdutwanip);
            put("WAN connetc IP", WebportalParam.brlanconnectip);
            put("Protocol", "tcp");
            put("WAN port", "21");
            
        }
    };
    
    
}