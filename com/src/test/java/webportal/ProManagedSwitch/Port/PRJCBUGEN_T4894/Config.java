package webportal.ProManagedSwitch.Port.PRJCBUGEN_T4894;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;

/**
 *
 * @author Sumanta
 *
 */
public interface Config {
    public String                             FRAME_SIZE_MAX    = "9198";
    public String                             FRAME_SIZE_MIN    = "1500";
    public String[]                           sw1port           = {
            WebportalParam.sw1LagPort1
    };
    public String[]                           sw2port           = {
            WebportalParam.sw2LagPort1
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

    public static String                    PORT_SPEED          = "100 Mbps";
    public static String                    DUPLEX_MODE         = "Half";
    public static String                    PORT_SPEED_RESTORE  = "Auto";
    public static String                    DUPLEX_MODE_RESTORE = "Auto";
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
