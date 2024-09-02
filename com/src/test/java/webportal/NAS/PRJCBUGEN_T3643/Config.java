package webportal.NAS.PRJCBUGEN_T3643;


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
            put("Location Name", WebportalParam.location1);// zhen@mailcatch.com
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
            put("Serial Number", WebportalParam.nas1SerialNo);// zhen@mailcatch.com
            put("Device Name", WebportalParam.nas1DeviceName);
        }
    };
    public static String DESCRIPTION_1 ="Abcdfgbdbsfsdbfdsfhsdfsdfsdfsdjfbsjdasdsasdfsferwerfsdfdsfdsfsfsd";
    public static String DESCRIPTION_2 =",./<>?;':\"[]{}\\|_-=+()*&^%$#@1Qaa~·a aaaaaaaaaaaaaaaaaa111111111";
    public static String diskinfo="298.12 GB";
    public static String SerialNo = "4VC16AEL00005";
    public static String curfwinfo = "Current Version: 6.9.3 (RC2)";
    public static String upfwinfo = "Update Version: NA";
    public static String name = "rongqing628";
    public static String model = "ReadyNAS 628X";
    public static String firmware = "6.9.3 (RC2)";
    public static String tempaturedisk = "Temperature Disk";
    public static String antivirus="Disabled";
    public static String systemtemp = "56°C";
    public static String cputemp = "62°C";
    public static String storageused="Storage Used";
    public static String ipvalue="10.40.1.133";
    public static String getawayvalue="10.40.0.13";
    public static String subnetvalue="255.255.254.0";
    public static String dnsvalue="10.40.0.7";
    
}
