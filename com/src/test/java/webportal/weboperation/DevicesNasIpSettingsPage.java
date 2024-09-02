/**
 * 
 */
package webportal.weboperation;

import java.util.logging.Logger;

import webportal.publicstep.WebCheck;

/**
 * @author zheli
 *
 */
public class DevicesNasIpSettingsPage extends webportal.webelements.DevicesNasIpSettingsPageElement {
    Logger logger;

    /**
     * 
     */
    public DevicesNasIpSettingsPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon("#/devices/nas/ipSettings");
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
    }

    public String getipaddress() {
        return ipaddress.getText();
    }

    public String getsubnet() {
        return subnet.getText();
    }

    public String getgetaway() {
        return getaway.getText();
    }

    public String getdns() {
        return dns.getText();
    }
}
