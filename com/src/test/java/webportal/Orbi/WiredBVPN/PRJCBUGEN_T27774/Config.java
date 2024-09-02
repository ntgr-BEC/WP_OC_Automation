package webportal.Orbi.WiredBVPN.PRJCBUGEN_T27774;

import java.util.HashMap;
import java.util.Map;


public interface Config {
    public static Map<String, String> radiusinfo = new HashMap<String, String>() {
        {

            put("radiusip", "192.168.100.6");
            put("radiussecret", "12345678");
        }
    };
    

}