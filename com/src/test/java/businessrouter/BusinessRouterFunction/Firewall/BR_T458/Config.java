package businessrouter.BusinessRouterFunction.Firewall.BR_T458;
import java.util.HashMap;
import java.util.Map;
public interface Config {
    public static Map<String, String> TriggeringRule = new HashMap<String, String>() {
        {
          
            put("Rule Name", "test");
            put("Triggering Port", "1");
            put("Starting Port", "200");
            put("Ending Port", "300");
        
            
        }
    };

}
