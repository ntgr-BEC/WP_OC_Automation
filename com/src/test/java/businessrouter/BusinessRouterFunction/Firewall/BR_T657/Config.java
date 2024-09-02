package businessrouter.BusinessRouterFunction.Firewall.BR_T657;
import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;
public interface Config {
    public static Map<String, String> Firewallrule1 = new HashMap<String, String>() {
        {
          
            put("Rule Name", "test1");           
            put("Source Zone", "LAN");
            put("Destination Zone", "WAN");
            put("Action", "ACCEPT");
            
            
        }
    };
    public static Map<String, String> Firewallrule2 = new HashMap<String, String>() {
        {
          
            put("Rule Name", "test2");
            put("Source Zone", "LAN");
            put("Source LAN", "LAN2");
            put("Destination Zone", "WAN");
            put("Action", "DROP");
            
        }
    };
    
    public static Map<String, String> VlanPortInfo = new HashMap<String, String>() {
        {
          
            put("VLAN ID", "1");
            put("Ports", WebportalParam.brcleintport2+":TAG");
            
            
        }
    };
    public static Map<String, String> VlanPortRestoreInfo = new HashMap<String, String>() {
        {
          
            put("VLAN ID", "1");
            put("Ports", WebportalParam.brcleintport2+":UNTAG");
            
            
        }
    };
    public static Map<String, String> NewVLANInfo = new HashMap<String, String>() {
        {
          
            put("VLAN ID", "8");
            put("Name", "test");
            put("Ports", WebportalParam.brcleintport2+":UNTAG");
            put("Description", "add vlan");
            
            
        }
    };
    public static Map<String, String> NewLANInfo = new HashMap<String, String>() {
        {
          
            put("VLAN ID", "8");
            put( "Command", "Outbound");
            put("Dut IP", WebportalParam.brlanclientip);
            put("Host IP", WebportalParam.brwanclientip);
            put("WAN connetc IP", WebportalParam.brwanconnectip);
            put("Protocol", "udp");
            put("LAN port", "102");
            put("WAN port", "103");
            
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
            put("WAN port", "103");
            
        }
    };
    public static Map<String, String> TmsTCPCommands2 = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Net Stat");
            put( "Command", "Outbound");
            put("Dut IP", WebportalParam.brlanclientip2);
            put("Host IP", WebportalParam.brwanclientip);
            put("WAN connetc IP", WebportalParam.brwanconnectip);
            put("Protocol", "tcp");
            put("LAN port", "102");
            put("WAN port", "103");
            
        }
    };
}