package webportal.SwitchManaged.System.PRJCBUGEN_T4656;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author zheli
 *
 */
public interface Config {
    public final static Map<String, String> IPINFO = new HashMap<String, String>() {
        {
            put("DNS Server1", "114.114.114.114");
            put("DNS Server2", "8.8.8.8");
        }
    };

}
