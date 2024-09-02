/**
 *
 */
package webportal.weboperation;

import java.util.Map;
import java.util.logging.Logger;

import com.codeborne.selenide.Condition;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DevicesApIpSettingsPageElement;

/**
 * @author zheli
 *
 */
public class DevicesApIpSettingsPage extends DevicesApIpSettingsPageElement {
    final static Logger logger = Logger.getLogger("DevicesAPIpSettingsPage");

    /**
     *
     */
    public DevicesApIpSettingsPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefDevicesApIpsettings);
        MyCommonAPIs.sleep(5 * 1000);
        logger.info("init...");
    }

    public void setIp(Map<String, String> ip) {
        for (String ss : ip.keySet()) {
            logger.info(ss + ": " + ip.get(ss));
        }
        if (isDHCP()) {
            dhcpAutomatically.click();
            MyCommonAPIs.sleepi(5);
        } else {
        }
        if (ip.get("IP Address") != null) {
            ipAddress.setValue(ip.get("IP Address"));
        }
        if (ip.get("Subnet Mask") != null) {
            subnetMask.setValue(ip.get("Subnet Mask"));
        }
        if (ip.get("Gateway Address") != null) {
            gatewayAddress.setValue(ip.get("Gateway Address"));
        }
        if (ip.get("DNS Server") != null) {
            dnsServer.setValue(ip.get("DNS Server"));
        }
        MyCommonAPIs.sleepi(5);
        saveBtn.click();
        MyCommonAPIs.sleep(10 * 1000);
    }

    public String getIp() {
        String ip = ipAddress.getValue();
        return ip;
    }

    public String getErrorMessage() {
        return WebportalParam.getNLocText(alert.getText());
    }

    public void choiceDhcp() {
        if (isDHCP()) {
        } else {
            dhcpAutomatically.click();
            MyCommonAPIs.sleepi(5);
        }
        saveBtn.click();
        MyCommonAPIs.sleepsync();
    }

    public boolean isDHCP() {
        boolean result = false;
        if (ipAddress.has(Condition.attribute("disabled"))) {
            result = true;
        } else {
            result = false;
        }
        logger.info("isDHCP: " + result);
        return result;
    }
    //AddedByPratik
    public void enableAssignAutomaticallyIPAddrees() {
        MyCommonAPIs.sleepi(5);
        dhcpAutomatically.click();
        MyCommonAPIs.sleepi(5);
        saveBtn.click();
        MyCommonAPIs.sleep(10 * 1000);
    }
    
  //AddedByPratik
    public void setIp1(String ip2) {
        MyCommonAPIs.sleepi(5);
        ipAddress.setValue(ip2);
        System.out.println(ip2);
        MyCommonAPIs.sleepi(5);
        saveBtn.click();
        MyCommonAPIs.sleep(10 * 1000);
    }
}
