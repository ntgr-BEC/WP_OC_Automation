package webportal.SwitchManaged.Port.PRJCBUGEN_T4886;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;

/**
 *
 * @author zheli
 *
 */
public interface Config {
    public String[]                           sw1port           = {
            WebportalParam.sw1Port1
    };
    public String[]                           sw2port           = {
            WebportalParam.sw2Port6
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

    public static String                    PORT_SPEED          = "1000 Mbps";
    public static String                    PORT_SPEEDSM          = "10 Mbps";
    public static String                    DUPLEX_MODE         = "Full";
    public static String                    PORT_SPEED_RESTORE  = "Auto";
    public static String                    DUPLEX_MODE_RESTORE = "Auto";
    public static String                    PORTSPEED_CLI       = "10 half";
    public final static Map<String, String> BATTCHSETTING      = new HashMap<String, String>() {
                                                                    {
                                                                        put("Enable_Port", "ON");
                                                                        put("Duplex_Mode", DUPLEX_MODE);
                                                                        ;
                                                                        put("Port_Speed", PORT_SPEED);
                                                                    }
                                                                };
 public final static Map<String, String> BATTCHSETTING1      = new HashMap<String, String>() {
                                                                    {
                                                                        put("Enable_Port", "ON");
                                                                        put("Duplex_Mode", DUPLEX_MODE);
                                                                        ;
                                                                        put("Port_Speed", PORT_SPEEDSM);
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
