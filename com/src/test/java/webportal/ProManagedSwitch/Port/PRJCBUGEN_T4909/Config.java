package webportal.SwitchManaged.Port.PRJCBUGEN_T4909;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;

/**
 * 
 * @author zheli
 *
 */
public interface Config {
    public String                             LAG_NAME          = "Lag1";
    public String[]                           SW1PORT           = {
            WebportalParam.sw1LagPort1, WebportalParam.sw1LagPort2
    };
    public String[]                           SW2PORT           = {
            WebportalParam.sw2LagPort1, WebportalParam.sw2LagPort2
    };
    public final static Map<String, String[]> SWITCH1_PORTARRAY = new HashMap<String, String[]>() {
                                                                    {
                                                                        put(WebportalParam.sw1deveiceName, SW1PORT);
                                                                        put(WebportalParam.sw2deveiceName, SW2PORT);
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
