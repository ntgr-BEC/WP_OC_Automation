package businessrouter.BusinessRouterFunction.IPv6.BR_T762;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;

public interface Config {
    public static Map<String, String> Ipv6DHCPServerInfo = new HashMap<String, String>() {
        {
            
            put("IPv6 address","2222::1");
            put("IPv6 address Len", "64");
            put("DHCP Status", "1");
            put("DHCP Mode", "1");
            put("Prefix Delegation", "Enable");
        
            
        }
    };
    public static Map<String, String> Ipv6PrifexDelegationInfo = new HashMap<String, String>() {
        {
            
            put("IPv6 Prefix","3333::");
            put("IPv6 Prefix Length", "64");
           
        
            
        }
    };
    public static Map<String, String> RADVDInfo= new HashMap<String, String>() {
        {
            
            put("RADVD Status","1");
            put("Advertise Mode", "0");
            put("RA Flags", "Managed");
           
        
            
        }
    };
    public static Map<String, String> RADVDPrefixInfo= new HashMap<String, String>() {
        {
            
            put("IPv6 Prefix Type","2");
            put("IPv6 Prefix", "2222::");
            put("IPv6 Prefix Length", "64");
            put("Prefix Lifetime", "1800");
        
            
        }
    };
    public static Map<String, String> Ipv6Info= new HashMap<String, String>() {
        {
            
            put("Connect Type","DHCP");
            put("LAN Type", "Auto Config");
            put("InterfaceID", "2222:2222:2222:2222");
        }
    };
            
       
    public static Map<String, String> Ipv6TypeAndInfo= new HashMap<String, String>() {
        {
            
            put("Connection Type","DHCP");
            put("WAN IPv6", "2222::");
            put("LAN IPv6", "3333::2222:2222:2222:2222");
            put("WAN DNS", "2222");
            put("InterfaceID", "2222:2222:2222:2222");
        
            
        }
    };
    public static Map<String, String> TmsRebootCom = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Backend");
            put("Dut IP", WebportalParam.brlanclientip);
            put("Host IP", WebportalParam.brlanclientip);
            put("Protocol", "reboot");
   
            
        }
    };
    public static Map<String, String> TmsGetIpv6AddrCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Backend");
            put("Dut IP", WebportalParam.brlanclientip);
            put("Host IP", WebportalParam.brlanclientip);
            put("Protocol", "ifconfig eth0");
   
            
        }
    };
    public static Map<String, String> Ipv6DHCPServerdefaultInfo = new HashMap<String, String>() {
        {
            
       
            put("DHCP Status", "0");
         
        
            
        }
    };
}
