package webportal.ProManagedSwitch.VLAN.PRJCBUGEN_T4731;

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

    public String[]                           sw1port           = {
            WebportalParam.sw1LagPort1, WebportalParam.sw1LagPort2, WebportalParam.sw1Port6
    };
    public String[]                           sw1port2          = {
            WebportalParam.sw1LagPort1
    };
    public String[]                           sw1port3          = {
            WebportalParam.sw1LagPort2
    };
    public String[]                           sw1port4          = {
            WebportalParam.sw1Port6
    };
    public final static Map<String, String[]> SWITCH1_PORTARRAY = new HashMap<String, String[]>() {
                                                                    {
                                                                        put(WebportalParam.sw1deveiceName, sw1port);
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
