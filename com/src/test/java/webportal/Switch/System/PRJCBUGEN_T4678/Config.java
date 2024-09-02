package webportal.Switch.System.PRJCBUGEN_T4678;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author zheli
 *
 */
public interface Config {
    public final static Map<String, String> IPINFO1 = new HashMap<String, String>() {
        {
            put("IP Address", "192.168.1.100");
            put("Subnet Mask", "255.255.255.0");
            put("Gateway Address", "192.168.0.1");
        }
    };

}
