package webportal.Switch.VLANEnhancements.PRJCBUGEN_T32021;

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
    public String  dut1Name      = WebportalParam.sw1deveiceName;
    public String  dut2Name      = WebportalParam.sw2deveiceName;

    public String[]                           sw1port           = {
            WebportalParam.sw1LagPort1, WebportalParam.sw1LagPort2
    };
    public String[]                           sw1port2          = {
            WebportalParam.sw1LagPort1, WebportalParam.sw1LagPort2
    };
    public String[]                           sw2port2          = {
            "1", "2"
    };
    public String[]                           sw1port4          = {
            WebportalParam.sw1LagPort2 + "T"
    };
    public final static Map<String, String[]> SWITCH1_PORTARRAY = new HashMap<String, String[]>() {
                                                                    {
                                                                        put(WebportalParam.sw1deveiceName, sw1port);
                                                                        put(WebportalParam.sw2deveiceName, sw2port2);
                                                                    }
                                                                };

    public final static Map<String, String[]> SWITCH1_PORTARRAY2 = new HashMap<String, String[]>() {
        {
            put(WebportalParam.sw1deveiceName, sw1port2);
        }
    };

    public final static Map<String, String> BATTCHSETTING1 = new HashMap<String, String>() {
        {
            put("Enable_Port", "ON");
        }
    };

    public final static Map<String, String> BATTCHSETTING2 = new HashMap<String, String>() {
        {
            put("Enable_Port", "OFF");
        }
    };

}
