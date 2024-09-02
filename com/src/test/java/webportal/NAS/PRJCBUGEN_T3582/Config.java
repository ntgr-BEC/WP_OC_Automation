package webportal.NAS.PRJCBUGEN_T3582;


import java.util.HashMap;
import java.util.Map;

import util.TimeUtils;
import webportal.param.WebportalParam;
/**
 * 
 * @author zheli
 *
 */
public interface Config {
    public final static Map<String, String> REGISTER_INFO = new HashMap<String, String>() {
        {
            put("emailAdress", TimeUtils.getMillisecond() + "@mailcatch.com");// zhen@mailcatch.com
            put("password", "Netgear1");
        }
    };
    public final static Map<String, String> LOCATION_INFO = new HashMap<String, String>() {
        {
            put("Location Name", "Test");// location的information
            put("Device Admin Password", "password");
            put("Street", "Street");
            put("City", "City");
            put("State", "State");
            put("Zip Code", "412200");
            put("Country", "China");
            put("Time Zone", "UTC+08:00 (Asia/Shanghai)");
        }
    };
    public final static Map<String, String> DEVICE_INFO = new HashMap<String, String>() {
        {
            put("Serial Number", WebportalParam.nas1SerialNo);// device的information
            put("Device Name", WebportalParam.nas1DeviceName);
        }
    };
    public static String DESCRIPTION_1 ="Abcdfgbdbsfsdbfdsfhsdfsdfsdfsdjfbsjdasdsasdfsferwerfsdfdsfdsfsfsd";
    public static String DESCRIPTION_2 =",./<>?;':\"[]{}\\|_-=+()*&^%$#@1Qaa~·a aaaaaaaaaaaaaaaaaa111111111";
    public static String errorinfo ="Device is already in same account";
}
