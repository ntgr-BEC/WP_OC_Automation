package businessrouter.BusinessRouterFunction.Firewall.BR_T454;

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
    public static Map<String, String> Firewallinboundftp = new HashMap<String, String>() {
        {
          
            put("Rule Name", "FTP");
            put("Protocol", "TCP");
            put("ExternalPort", "21");
            put("Internal IP Address", WebportalParam.brlanclientip);
            
        }
    };
    public static Map<String, String> Firewallinboundtelnet = new HashMap<String, String>() {
        {
         
            put("Rule Name", "TELNET");
            put("Protocol", "TCP");
            put("ExternalPort", "23");
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
    public static Map<String, String> TmsTCPFTPCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Net Stat");
            put( "Command", "Outbound");
            put("Dut IP", WebportalParam.brwanclientip);
            put("Host IP", WebportalParam.brdutwanip);
            put("WAN connetc IP", WebportalParam.brwanconnectip);
            put("Protocol", "tcp");
            put("WAN port", "21");
            
        }
    };
    public static Map<String, String> TmsTCPTELNETCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Net Stat");
            put( "Command", "Outbound");
            put("Dut IP", WebportalParam.brwanclientip);
            put("Host IP", WebportalParam.brdutwanip);
            put("WAN connetc IP", WebportalParam.brwanconnectip);
            put("Protocol", "tcp");
            put("WAN port", "23");
            
        }
    };
}