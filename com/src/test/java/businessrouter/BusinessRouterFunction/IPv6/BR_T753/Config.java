package businessrouter.BusinessRouterFunction.IPv6.BR_T753;

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
    public static Map<String, String> Ipv6StaticIPInfo= new HashMap<String, String>() {
        {
            
          
            put("WAN IPv6", "2222::2");
            put("WAN IPv6 Length", "64");
            put ("Gateway","2222::1");
            put("LAN IPv6", "3333::1");
            put("Primary DNS", "2222::1");
            put("LAN IPv6 Length", "64");
        
            
        }
    };
    public static Map<String, String> TmsIPv6AddrCom = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Backend");
            put("Dut IP", WebportalParam.brlanclientip);
            put("Host IP", WebportalParam.brlanclientip);
            put("Protocol", "ifconfig eth0 inet6 add 3333::11/64");
   
            
        }
    };
    public static Map<String, String> TmsIPv6RouterCom = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Backend");
            put("Dut IP", WebportalParam.brlanclientip);
            put("Host IP", WebportalParam.brlanclientip);
            put("Protocol", "route -A inet6 add default gw 3333::1");
   
            
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
    public static Map<String, String> TmsPingLanInterfaceCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Backend");
            put("Dut IP", WebportalParam.brlanclientip);
            put("Host IP", WebportalParam.brlanclientip);
            put("Protocol", "ping6 -s 31 -c 5 3333::1");
   
            
        }
    };
    public static Map<String, String> TmsPingWanInterfaceCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Backend");
            put("Dut IP", WebportalParam.brlanclientip);
            put("Host IP", WebportalParam.brlanclientip);
            put("Protocol", "ping6 -s 31 -c 5 2222::1");
   
            
        }
    };
    public static Map<String, String> Ipv6DHCPServerdefaultInfo = new HashMap<String, String>() {
        {
            
       
            put("DHCP Status", "0");
         
        
            
        }
    };
}
