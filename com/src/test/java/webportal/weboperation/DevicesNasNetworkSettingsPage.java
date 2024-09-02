/**
 * 
 */
package webportal.weboperation;

import java.util.logging.Logger;

import com.codeborne.selenide.Selenide;

import util.MyCommonAPIs;
import webportal.publicstep.WebCheck;
import webportal.webelements.DevicesNasNetworkSettingsPageElement;

/**
 * @author zheli
 *
 */
public class DevicesNasNetworkSettingsPage extends DevicesNasNetworkSettingsPageElement {
    Logger logger;

    /**
     * 
     */
    public DevicesNasNetworkSettingsPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon("#/devices/nas/networkSettings");
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
    }

    public String getipaddress() {
        return ipaddress.getText();
    }

    public void restNetworkSettings() {
        Selenide.sleep(5 * 1000);
        resetnetwork.click();
        resetyes.click();
        MyCommonAPIs.waitReady();
    }

    public String getMtuValue() {
        return mtu.getText();
    }

    public String getMacValue() {
        return macAddress.getText();
    }
}
