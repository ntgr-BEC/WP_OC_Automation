package webportal.SwitchManaged.CFD_VLAN.PRJCBUGEN_T41622;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;

/**
 * 
 * @author Tejeshwinin K V
 *
 */
public interface Config {
    public boolean enablemirror  = true;
    public boolean disablemirror = false;
    public String  dut1Name      = WebportalParam.sw1deveiceName;
    public String  dut2Name      = WebportalParam.sw2deveiceName;

    public String[]                           sw1port           = {
            "2", "6","9"
    };
    public String[]                           sw2port           = {
            "5"
    };
  
    public final static Map<String, String[]> SWITCH1_PORTARRAY = new HashMap<String, String[]>() {
                                                                    {
                                                                        put(WebportalParam.sw1deveiceName, sw1port);
                                                                        
                                                                    }
                                                                };

    public final static Map<String, String[]> SWITCH1_PORTARRAY2 = new HashMap<String, String[]>() {
        {
//            put(WebportalParam.sw1deveiceName, sw1port2);
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
