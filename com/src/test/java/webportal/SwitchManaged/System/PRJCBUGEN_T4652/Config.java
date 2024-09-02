package webportal.SwitchManaged.System.PRJCBUGEN_T4652;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author zheli
 *
 */
public interface Config {
    public final static Map<String, String> IPINFO1 = new HashMap<String, String>() {
                                                        {
                                                            put("IP Address", "192.168.1.256");
                                                            put("Subnet Mask", "255.255.255.0");
                                                            put("Gateway Address", "192.168.1.1");
                                                        }
                                                    };
    public final static Map<String, String> IPINFO2 = new HashMap<String, String>() {
                                                        {
                                                            put("IP Address", "192.168.1.0");
                                                            put("Subnet Mask", "255.255.255.0");
                                                            put("Gateway Address", "192.168.1.1");
                                                        }
                                                    };
    public final static Map<String, String> IPINFO3 = new HashMap<String, String>() {
                                                        {
                                                            put("IP Address", "192.168.1.-1");
                                                            put("Subnet Mask", "255.255.255.0");
                                                            put("Gateway Address", "192.168.1.1");
                                                        }
                                                    };
    public final static Map<String, String> IPINFO4 = new HashMap<String, String>() {
                                                        {
                                                            put("IP Address", "192.168.256.100");
                                                            put("Subnet Mask", "255.255.255.0");
                                                            put("Gateway Address", "192.168.1.1");
                                                        }
                                                    };
    public final static Map<String, String> IPINFO5 = new HashMap<String, String>() {
                                                        {
                                                            put("IP Address", "192.168.256.100");
                                                            put("Subnet Mask", "255.255.255.0");
                                                            put("Gateway Address", "192.168.1.1");
                                                        }
                                                    };
    public final static Map<String, String> IPINFO6 = new HashMap<String, String>() {
                                                        {
                                                            put("IP Address", "10.256.1.1");
                                                            put("Subnet Mask", "255.255.255.0");
                                                            put("Gateway Address", "192.168.1.1");
                                                        }
                                                    };
}
