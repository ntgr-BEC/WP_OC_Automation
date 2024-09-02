package businessrouter.BusinessRouterFunction.Administration.BR_T215;

import java.util.HashMap;
import java.util.Map;

public interface Config {
    public static String ConfigFilepath = "C:\\tftpd32\\NETGEAR_BR_T215.cfg";
    public static String ChangeName = "testT215";
    
    public static Map<String, String> VLAN = new HashMap<String, String>() {
        {

            put("VLAN ID", "3");
            put("Name", "vlan3");
            put("Ports", "1:TAG");
            put("Description", "add vlan");

        }
    };
}
