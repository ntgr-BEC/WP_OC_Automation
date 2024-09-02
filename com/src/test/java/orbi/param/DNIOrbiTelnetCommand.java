package orbi.param;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author bingke.xue
 *
 */
public class DNIOrbiTelnetCommand {
    //Device details info:
    public final static Map<String, String> DNIOrbiTelnetCommandMap = new HashMap<String, String>() {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        {
            put("Orbi_factory_default", "config default"+";"+"reboot");
            put("Orbi_enable_remote_management","config set remote_endis=1"+";"+"config commit"+";"+"reboot");
            put("Orbi_reboot", "reboot");
            put("Orbi_get_region", "artmtd -r region");
        }
    };
    public String DNIOrbiSetRegion (String region) {
        return "artmtd -w region " + region ;
    }
    
    
    

}
