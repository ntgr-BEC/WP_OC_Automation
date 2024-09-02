package businessrouter.BusinessRouterFunction.FirewallBasicSetup.BR_T357;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;
public interface Config {
    public static Map<String, String> DmzInfo = new HashMap<String, String>() {
        {          
            put("DMZ Status","Enable");
            put("DMZ IP", WebportalParam.brlanclientip);
        }
    };
    public static Map<String, String> TmsTCPFtpCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Net Stat");
            put( "Command", "Outbound");
            put("Dut IP", WebportalParam.brwanclientip);
            put("Host IP", WebportalParam.brdutwanip);
            put("WAN connetc IP", WebportalParam.brlanconnectip);
            put("Protocol", "tcp");
            put("WAN port", "21");
            
        }
    };
    public static Map<String, String> DisabeDmzInfo = new HashMap<String, String>() {
        {          
            put("DMZ Status","Disable");
            put("DMZ IP", WebportalParam.brlanclientip);
        }
    };

}
