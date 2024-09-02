package businessrouter.BusinessRouterFunction.IPSecVPN.BR_T1071;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;

public interface Config {
    public static Map<String, String> DUT1IPSecPolicy = new HashMap<String, String>() {
        {
            put("Rule Status","enable");
            put("Policy Name", "Tunnel1");
            put("Remote Gateway", WebportalParam.bripsecoppositewanip);
            put("Remote Subnet", WebportalParam.bripsecoppositelanclient);
            put("Remote Mask", "255.255.255.255");
            put("Local Subnet", WebportalParam.brlanclientip);
            put("Local Mask", "255.255.255.255");
            put("PreShared Key", "12345678");
        
            
        }
    };
    public static Map<String, String> DUT2IPSecPolicy = new HashMap<String, String>() {
        {
            put("Rule Status","enable");
            put("Policy Name", "Tunnel1");
            put("Remote Gateway", WebportalParam.brdutwanip);
            put("Remote Subnet", WebportalParam.brlanclientip);
            put("Remote Mask", "255.255.255.255");
            put("Local Subnet", WebportalParam.bripsecoppositelanclient);
            put("Local Mask", "255.255.255.255");
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
            put("LAN port", "106");
            put("WAN port", "108");
            
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
    public static Map<String, String> TmsNomacthICMPCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Backend");
            put("Dut IP", WebportalParam.bripsecoppositelanclient);
            put("Host IP", WebportalParam.bripsecoppositelanclient);
            put("Protocol", "ping -s 31 -c 5 "+ WebportalParam.brlanclient2ip);
   
            
        }
    };
    public static Map<String, String> TmsICMP2Commands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Backend");
            put("Dut IP", WebportalParam.brlanclientip);
            put("Host IP", WebportalParam.brlanclientip);
            put("Protocol", "ping -s 31 -c 5 "+ WebportalParam.bripsecoppositelanclient);
   
            
        }
    };

}