package businessrouter.BusinessRouterFunction.Advanced_DDNS.BR_T228;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;

public interface Config {
    public static Map<String, String> NewDDNSAccountInfo = new HashMap<String, String>() {
        {            
            put("Exist Account","Yes");
            put("Host Name","Lisj");
            put("Email", "sujuan.li@netgear.com");
            put("Password", "Shanoon011");
            put("DDNS Flag","Enable");
                    
        }
    };

    public static Map<String, String> TmsICMPCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Backend");
            put("Dut IP", WebportalParam.brlanclientip);
            put("Host IP", WebportalParam.brlanclientip);
            put("Protocol", "ping -s 31 -c 5 Lisj.mynetgear.com");
   
            
        }
    };
    public static Map<String, String> DefaultDDNSAccountInfo = new HashMap<String, String>() {
        {            
            //put("Exist Account","No");
            //put("Host Name","Lisj");
            //put("Email", "sujuan.li@netgear.com");
            //put("Password", "Shanoon01");
            put("DDNS Flag","Disable");
                    
        }
    };

}
