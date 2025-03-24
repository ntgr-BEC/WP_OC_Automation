package webportal.ProManagedSwitch.System.PRJCBUGEN_T4648;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;

/**
 * 
 * @author Sumanta
 *
 */
public interface Config {
    public final static Map<String, String> DEVICEINFO = new HashMap<String, String>() {
        {
            put("Name", WebportalParam.sw1deveiceName);
            put("Serial_Number", WebportalParam.sw1serialNo);
            put("Model", WebportalParam.sw1Model);
            put("Base_MAC_Address", WebportalParam.sw1MacAddress);
            put("IP_Address", WebportalParam.sw1IPaddress);
            put("Firmware_Version", WebportalParam.sw1Firmware);
        }
    };

}
