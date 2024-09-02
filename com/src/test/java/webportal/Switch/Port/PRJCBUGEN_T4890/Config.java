package webportal.Switch.Port.PRJCBUGEN_T4890;

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
                                                                  put("emailAdress", TimeUtils.getMillisecond() + "@mailcatch.com");
                                                                  put("password", "Netgear#1");
                                                              }
                                                          };
    public final static Map<String, String> LOCATION_INFO = new HashMap<String, String>() {
                                                              {
                                                                  put("Location Name", WebportalParam.location1);
                                                                  put("Device Admin Password", "password");
                                                                  put("Street", "Street");
                                                                  put("City", "City");
                                                                  put("State", "State");
                                                                  put("Zip Code", "412200");
                                                                  put("Country", "China");
                                                                  put("Time Zone", "UTC+08:00 (Asia/Shanghai)");
                                                              }
                                                          };
    public final static Map<String, String> DEVICE_INFO   = new HashMap<String, String>() {
                                                              {
                                                                  put("Serial Number", WebportalParam.sw1serialNo);
                                                                  put("Device Name", WebportalParam.sw1deveiceName);
                                                              }
                                                          };
    public static String                    DESCRIPTION_1 = "Abcdfgbdbsfsdbfdsfhsdfsdfsdfsdjfbsjdasdsasdfsferwerfsdfdsfdsfsfsd";
    public static String                    DESCRIPTION_2 = ",./<>?;'\\\"[]{}\\\\|_-=+()*&^%$@1Qaa~a aaaaaaaaaaaaaaaaaa111111111#";
}
