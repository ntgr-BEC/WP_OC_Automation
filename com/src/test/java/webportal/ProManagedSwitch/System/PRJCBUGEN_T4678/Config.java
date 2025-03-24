package webportal.ProManagedSwitch.System.PRJCBUGEN_T4678;

import java.util.HashMap;
import java.util.Map;

import webportal.param.WebportalParam;

/**
 * 
 * @author sumanta
 *
 */
public interface Config {
    public final static Map<String, String> IPINFO1 = new HashMap<String, String>() {
        {
            
            String ipvalue=WebportalParam.sw1IPaddress;        
            String[] ipParts=ipvalue.split("\\.");
            String newsubnet=ipParts[0]+"."+ipParts[1]+"."+"1"+"."+"0";
            
            
//            put("IP Address", newip);
            put("Subnet Mask", "255.255.255.0");
            put("Gateway Address", newsubnet);
        }
    };

}
