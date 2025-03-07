package webportal.SwitchManaged.Port.PRJCBUGEN_T4882;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;

/**
 *
 * @author zheli
 *
 */
public interface Config {
    public final static Map<String, String>   LOCATION_INFO       = new HashMap<String, String>() {
                                                                      {
                                                                          put("Location Name", WebportalParam.location1);
                                                                          put("Device Admin Password", "password");
                                                                          put("Street", "Street");
                                                                          put("City", "City");
                                                                          put("State", "State");
                                                                          put("Zip Code", "412200");
                                                                          put("Country", "China");
                                                                          put("Time Zone", "UTC+08:00 (Asia/Shanghai)");
                                                                      }
                                                                  };
    public String[]                           sw1port             = {
            "1"
    };
    public String[]                           sw2port             = {
            "1"
    };
    public final static Map<String, String[]> SWITCH_PORTARRAY    = new HashMap<String, String[]>() {
                                                                      {
                                                                          put(WebportalParam.sw1deveiceName, sw1port);
                                                                          put(WebportalParam.sw2deveiceName, sw2port);
                                                                      }
                                                                  };
    public final static Map<String, String>   BATTCHSETTING1      = new HashMap<String, String>() {
                                                                      {
                                                                          put("Enable_Port", "ON");
                                                                          put("Duplex_Mode", DUPLEX_MODE);
                                                                          put("Port_Speed", PORT_SPEED);
                                                                      }
                                                                  };
    public static String                      PORT_SPEED          = "10 Mbps";
    public static String                      DUPLEX_MODE         = "Half";
    public static String                      PORT_SPEED_RESTORE  = "Auto";
    public static String                      DUPLEX_MODE_RESTORE = "Auto";

}
