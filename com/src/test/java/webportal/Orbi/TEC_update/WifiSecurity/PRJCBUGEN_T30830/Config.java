package webportal.Orbi.TEC_update.WifiSecurity.PRJCBUGEN_T30830;

import java.util.HashMap;
import java.util.Map;


public interface Config {
    public static Map<String, String> wireless1info = new HashMap<String, String>() {
        {

            put("tab", "Wireless1");
            put("ssid","PRJCBUGEN_T30830-1");
            put("security","WPA2 Personal + WPA3 Personal");
            put("password", "12345678");
        }
    };
    
    public static Map<String, String> wireless2info = new HashMap<String, String>() {
        {

            put("tab", "Wireless2");
            put("ssid","PRJCBUGEN_T30830-2");
            put("security","WPA2 Personal + WPA3 Personal");
            put("password", "12345678");
        }
    };
    
    public static Map<String, String> wireless3info = new HashMap<String, String>() {
        {

            put("tab", "Wireless3");
            put("ssid","PRJCBUGEN_T30830-3");
            put("security","WPA2 Personal + WPA3 Personal");
            put("password", "12345678");
        }
    };

}