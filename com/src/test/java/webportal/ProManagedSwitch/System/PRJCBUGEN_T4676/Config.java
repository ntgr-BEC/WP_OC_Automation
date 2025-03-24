package webportal.ProManagedSwitch.System.PRJCBUGEN_T4676;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;

/**
 * 
 * @author sumanta
 *
 */
public interface Config {
    public final static Map<String, String> DEVICEINFO = new HashMap<String, String>() {
        {
            put("Device Name", WebportalParam.sw1deveiceName);
            put("Serial Number", WebportalParam.sw1serialNo);
            put("MAC Address", WebportalParam.sw1MacAddress);
        }
    };

}
