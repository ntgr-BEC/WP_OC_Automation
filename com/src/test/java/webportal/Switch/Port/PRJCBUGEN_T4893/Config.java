package webportal.Switch.Port.PRJCBUGEN_T4893;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;

/**
 * 
 * @author zheli
 *
 */
public interface Config {
    public String[]                           DEVICE_LIST       = {
            WebportalParam.sw1deveiceName, WebportalParam.sw2deveiceName
    };
    public String[]                           SW1PORT           = {
            "1", "2", "3", "4", "5", "6", "7"
    };
    public String[]                           SW2PORT           = {
            "1", "2", "3", "4", "5", "6", "7"
    };
    public final static Map<String, String[]> SWITCH1_PORTARRAY = new HashMap<String, String[]>() {
                                                                    {
                                                                        put(WebportalParam.sw1deveiceName, SW1PORT);
                                                                    }
                                                                };
    public final static Map<String, String[]> SWITCH2_PORTARRAY = new HashMap<String, String[]>() {
                                                                    {
                                                                        put(WebportalParam.sw2deveiceName, SW2PORT);
                                                                    }
                                                                };
    public final static Map<String, String>   BATTCHSETTING1    = new HashMap<String, String>() {
                                                                    {
                                                                        put("Enable_Port", "ON");
                                                                    }
                                                                };
    public final static Map<String, String>   BATTCHSETTING2    = new HashMap<String, String>() {
                                                                    {
                                                                        put("Enable_Port", "OFF");
                                                                    }
                                                                };
}
