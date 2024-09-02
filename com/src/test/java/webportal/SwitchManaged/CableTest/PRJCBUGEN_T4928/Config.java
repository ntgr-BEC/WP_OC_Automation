package webportal.SwitchManaged.CableTest.PRJCBUGEN_T4928;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;

/**
 * 
 * @author xuchen
 *
 */
public interface Config {
    public boolean enablemirror  = true;
    public boolean disablemirror = false;

    public String[]                           sw1port           = {
            WebportalParam.sw1LagPort1
    };
    public String[]                           sw2port           = {
            WebportalParam.sw2LagPort1
    };
    public final static Map<String, String[]> SWITCH1_PORTARRAY = new HashMap<String, String[]>() {
                                                                    {
                                                                        put(WebportalParam.sw1deveiceName, sw1port);
                                                                        put(WebportalParam.sw2deveiceName, sw2port);
                                                                    }
                                                                };

    public static String                    PORT_SPEED1         = "10 Mbps";
    public static String                    DUPLEX_MODE1        = "Half";
    public static String                    PORT_SPEED_RESTORE  = "Auto";
    public static String                    DUPLEX_MODE_RESTORE = "Auto";
    public final static Map<String, String> BATTCHSETTING1      = new HashMap<String, String>() {
                                                                    {
                                                                        put("Enable_Port", "ON");
                                                                        put("Duplex_Mode", DUPLEX_MODE1);
                                                                        ;
                                                                        put("Port_Speed", PORT_SPEED1);
                                                                    }
                                                                };

    public static String                    PORT_SPEED2    = "10 Mbps";
    public static String                    DUPLEX_MODE2   = "Full";
    public final static Map<String, String> BATTCHSETTING2 = new HashMap<String, String>() {
                                                               {
                                                                   put("Enable_Port", "ON");
                                                                   put("Duplex_Mode", DUPLEX_MODE2);
                                                                   ;
                                                                   put("Port_Speed", PORT_SPEED2);
                                                               }
                                                           };

    public static String                    PORT_SPEED3    = "100 Mbps";
    public static String                    DUPLEX_MODE3   = "Half";
    public final static Map<String, String> BATTCHSETTING3 = new HashMap<String, String>() {
                                                               {
                                                                   put("Enable_Port", "ON");
                                                                   put("Duplex_Mode", DUPLEX_MODE3);
                                                                   ;
                                                                   put("Port_Speed", PORT_SPEED3);
                                                               }
                                                           };

    public static String                    PORT_SPEED4    = "100 Mbps";
    public static String                    DUPLEX_MODE4   = "Half";
    public final static Map<String, String> BATTCHSETTING4 = new HashMap<String, String>() {
                                                               {
                                                                   put("Enable_Port", "ON");
                                                                   put("Duplex_Mode", DUPLEX_MODE4);
                                                                   ;
                                                                   put("Port_Speed", PORT_SPEED4);
                                                               }
                                                           };

    public final static Map<String, String> BATTCHSETTING5 = new HashMap<String, String>() {
        {
            put("Enable_Port", "OFF");
        }
    };
}
