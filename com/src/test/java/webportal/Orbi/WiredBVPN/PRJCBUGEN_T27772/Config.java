package webportal.Orbi.WiredBVPN.PRJCBUGEN_T27772;

import java.util.HashMap;
import java.util.Map;


public interface Config {
    public static Map<String, String> radiusinfo = new HashMap<String, String>() {
        {

            put("radiusip", "172.16.1.3");
            put("radiussecret", "12345678");
        }
    };
    

}