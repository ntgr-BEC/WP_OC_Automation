package webportal.ProManagedSwitch.Port.PRJCBUGEN_T4888;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;

/**
 *
 * @author Sumanta
 *
 */
public interface Config {
    public String[]                           sw1port           = {
            "1"
    };
    public String[]                           sw2port           = {
            "1"
    };
    public final static Map<String, String[]> SWITCH1_PORTARRAY = new HashMap<String, String[]>() {
                                                                    {
                                                                        put(WebportalParam.sw1deveiceName, sw1port);
                                                                    }
                                                                };
    public final static Map<String, String[]> SWITCH2_PORTARRAY = new HashMap<String, String[]>() {
                                                                    {
                                                                        put(WebportalParam.sw2deveiceName, sw2port);
                                                                    }
                                                                };

    public static String                    PORT_SPEED          = "10 Mbps";
    public static String                    DUPLEX_MODE         = "Full";
    public static String                    PORT_SPEED_RESTORE  = "Auto";
    public static String                    DUPLEX_MODE_RESTORE = "Auto";
    public static String                    PORTSPEED_CLI       = "10 full";
    public final static Map<String, String> BATTCHSETTING1      = new HashMap<String, String>() {
                                                                    {
                                                                        put("Enable_Port", "ON");
                                                                        put("Duplex_Mode", DUPLEX_MODE);
                                                                        ;
                                                                        put("Port_Speed", PORT_SPEED);
                                                                    }
                                                                };
    public final static Map<String, String> BATTCHSETTING2      = new HashMap<String, String>() {
                                                                    {
                                                                        put("Enable_Port", "ON");
                                                                        put("Duplex_Mode", PORT_SPEED_RESTORE);
                                                                        ;
                                                                        put("Port_Speed", DUPLEX_MODE_RESTORE);
                                                                    }
                                                                };
}
