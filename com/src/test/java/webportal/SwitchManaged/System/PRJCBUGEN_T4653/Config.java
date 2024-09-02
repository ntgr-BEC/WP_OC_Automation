package webportal.SwitchManaged.System.PRJCBUGEN_T4653;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;

/**
 * 
 * @author zheli
 *
 */
public interface Config {
    public final static Map<String, String> IPINFO1 = new HashMap<String, String>() {
                                                        {
                                                            put("IP Address", "10.1.1.233");
                                                        }
                                                    };
    public final static Map<String, String> IPINFO2 = new HashMap<String, String>() {
                                                        {
                                                            put("IP Address", WebportalParam.sw1IPaddress);
                                                        }
                                                    };

}
