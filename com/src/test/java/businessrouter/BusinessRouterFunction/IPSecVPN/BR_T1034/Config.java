package businessrouter.BusinessRouterFunction.IPSecVPN.BR_T1034;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;

public interface Config {
    public static Map<String, String> DUT1IPSecPolicy = new HashMap<String, String>() {
        {
            put("Rule Status","enable");
            put("Policy Name", "Tunnel1");
            put("Remote Gateway", WebportalParam.bripsecoppositewanip);
            put("Remote Subnet", WebportalParam.bripsecoppositelangateway);
            put("Remote Mask", "255.255.255.0");
            put("Local Subnet", WebportalParam.brlangateway);
            put("Local Mask", "255.255.255.0");
            put("PreShared Key", "12345678");
        
            
        }
    };
    public static Map<String, String> DUT2IPSecPolicy = new HashMap<String, String>() {
        {
            put("Rule Status","enable");
            put("Policy Name", "Tunnel1");
            put("Remote Gateway", WebportalParam.brdutwanip);
            put("Remote Subnet", WebportalParam.brlangateway);
            put("Remote Mask", "255.255.255.0");
            put("Local Subnet", WebportalParam.bripsecoppositelangateway);
            put("Local Mask", "255.255.255.0");
            put("PreShared Key", "12345678");
            
        }
    };
    public static Map<String, String> TmsTCPCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Net Stat");
            put( "Command", "Outbound");
            put("Dut IP", WebportalParam.bripsecoppositelanclient);
            put("Host IP", WebportalParam.brlanclientip);
            put("WAN connetc IP", WebportalParam.brlanconnectip);
            put("Protocol", "tcp");
            put("LAN port", "102");
            put("WAN port", "103");
            
        }
    };
    public static Map<String, String> TmsUDPCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Net Stat");
            put( "Command", "Outbound");
            put("Dut IP", WebportalParam.bripsecoppositelanclient);
            put("Host IP", WebportalParam.brlanclientip);
            put("WAN connetc IP", WebportalParam.brlanconnectip);
            put("Protocol", "udp");
            put("LAN port", "1061");
            put("WAN port", "1081");
            
        }
    };
    public static Map<String, String> TmsICMPCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Backend");
            put("Dut IP", WebportalParam.bripsecoppositelanclient);
            put("Host IP", WebportalParam.bripsecoppositelanclient);
            put("Protocol", "ping -s 31 -c 5 "+ WebportalParam.brlanclientip);
   
            
        }
    };

}