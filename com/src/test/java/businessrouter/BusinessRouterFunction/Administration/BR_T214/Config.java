package businessrouter.BusinessRouterFunction.Administration.BR_T214;

import java.util.HashMap;
import java.util.Map;

public interface Config {
    public static String DefaultFilepath = "C:\\tftpd32\\NETGEAR_BR500_T214.cfg";
    public static String ChangeFilepath  = "C:\\tftpd32\\NETGEAR_BR500_change.cfg";
    public static String ChangeName      = "testT214";

    public static Map<String, String> VLAN = new HashMap<String, String>() {
        {

            put("VLAN ID", "3");
            put("Name", "vlan3");
            put("Ports", "1:TAG");
            put("Description", "add vlan");

        }
    };
}
