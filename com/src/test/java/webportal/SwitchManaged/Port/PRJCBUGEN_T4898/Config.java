package webportal.SwitchManaged.Port.PRJCBUGEN_T4898;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;

/**
 * 
 * @author zheli
 *
 */
public interface Config {
    public String                             EGRESS_MAX        = "100";
    public String[]                           SW1PORT           = {
            WebportalParam.sw1LagPort1, WebportalParam.sw1LagPort2
    };
    public String[]                           SW1PORTCLI        = {
            WebportalParam.sw1LagPort1CLI, WebportalParam.sw1LagPort2CLI
    };
    public final static Map<String, String[]> SWITCH1_PORTARRAY = new HashMap<String, String[]>() {
                                                                    {
                                                                        put(WebportalParam.sw1deveiceName, SW1PORT);
                                                                    }
                                                                };
    
    public static String                    PORT_SPEED          = "100";    
    public static String                    PORT_SPEED_RESTORE  = "Auto";
    public static String                    DUPLEX_MODE_RESTORE = "Auto";
    public static String                    PORTSPEED_CLI       = "100 full-duplex";
    public final static Map<String, String> BATTCHSETTING      = new HashMap<String, String>() {
                                                                    {
                                                                        put("Enable_Port", "ON");
                                                                        put("Egress_Rate_Limit", "random");
                                                                        put("Storm_Rate_Limit", "random");
                                                                        put("Port_Speed", PORT_SPEED);
                                                                        
                                                                    }
                                                                };
                                           
                                                                
    public final static Map<String, String> BATTCHSETTING2      = new HashMap<String, String>() {
                                                                    {
                                                                        put("Enable_Port", "ON");
                                                                        put("Egress_Rate_Limit", "100");
                                                                        put("Storm_Rate_Limit", "100");
                                                                        put("Port_Speed", PORT_SPEED_RESTORE);
                                                                    }
                                                                };
                                                                
                                                                
    public final static Map<String, String> BATTCHSETTING1      = new HashMap<String, String>() {
                                                                    {
                                                                        put("Enable_Port", "ON");
                                                                        put("Egress_Rate_Limit", "random");
                                                                        put("Storm_Rate_Limit", "random");
                                                                        put("Port_Speed", "1000 Mbps");
                                                                        put("Duplex_Mode", "Full");
                                                                        
                                                                    }
                                                                };                     
}
