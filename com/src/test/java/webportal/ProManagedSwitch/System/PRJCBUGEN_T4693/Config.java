package webportal.SwitchManaged.System.PRJCBUGEN_T4693;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author zheli
 *
 */
public interface Config {
    public final static Map<String, String> DEVICEINFO = new HashMap<String, String>() {
        {
            put("Device Name", "123456789");
            put("Serial Number", "123456789");
            put("MAC Address", "aabbccddeeff"); 
        }
    };

}
