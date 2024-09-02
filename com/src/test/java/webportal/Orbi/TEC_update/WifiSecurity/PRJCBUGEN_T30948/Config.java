package webportal.Orbi.TEC_update.WifiSecurity.PRJCBUGEN_T30948;

import java.util.HashMap;
import java.util.Map;


public interface Config {
    public static Map<String, String> wireless1info = new HashMap<String, String>() {
        {

            put("tab", "Wireless1");
            put("ssid","PRJCBUGEN_T30948-1");
            put("security","WPA3 Enterprise");
            put("radiusip", "10.10.1.25");
            put("radiussecret", "12345678");
        }
    };
    
    public static Map<String, String> wireless2info = new HashMap<String, String>() {
        {

            put("tab", "Wireless2");
            put("ssid","PRJCBUGEN_T30948-2");
            put("security","WPA3 Enterprise");
            put("radiusip", "10.10.1.25");
            put("radiussecret", "12345678");
        }
    };
    
    public static Map<String, String> wireless3info = new HashMap<String, String>() {
        {

            put("tab", "Wireless3");
            put("ssid","PRJCBUGEN_T30948-3");
            put("security","WPA3 Enterprise");
            put("radiusip", "10.10.1.25");
            put("radiussecret", "12345678");
        }
    };

}