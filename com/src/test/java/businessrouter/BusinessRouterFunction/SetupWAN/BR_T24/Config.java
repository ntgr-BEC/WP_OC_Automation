package businessrouter.BusinessRouterFunction.SetupWAN.BR_T24;

import java.util.HashMap;
import java.util.Map;

public interface Config {
    public static Map<String, String> DeviceWarning = new HashMap<String, String>() {
        {
          
            put("Device Name", "!@#$%^&*");
            put("Warning", "Device name validation failed.");
           
        }
    };
    

}
