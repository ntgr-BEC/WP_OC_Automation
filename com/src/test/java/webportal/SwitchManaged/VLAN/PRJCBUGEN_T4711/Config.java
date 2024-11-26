package webportal.SwitchManaged.VLAN.PRJCBUGEN_T4711;

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
    public String  dut1Mode      = WebportalParam.sw1Model;

    public String[] sw1port  = {
            WebportalParam.sw1LagPort1, WebportalParam.sw1LagPort2
    };
    public String[] sw1port2 = {
            "deselectall"
    };
    public String[] sw1port3 = {
            "selectall"
    };
    public String[] sw1port4 = {
            WebportalParam.sw1LagPort2 + "T"
    };
    public String[] sw1port5 = {
            "6"
    };

    public final static Map<String, String[]> SWITCH1_PORTARRAY = new HashMap<String, String[]>() {
        {
            put(WebportalParam.sw1deveiceName, sw1port);
        }
    };

    public final static Map<String, String[]> SWITCH1_PORTARRAY5 = new HashMap<String, String[]>() {
        {
            put(WebportalParam.sw1deveiceName, sw1port5);
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

    public final static Map<String, String> BATTCHSETTING3 = new HashMap<String, String>() {
        {
            put("Enable_Port", "ON");
            put("Default_VLAN", "vlan200 (200)");
        }
    };
}
