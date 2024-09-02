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
import webportal.webelements.DevicesSwitchIpSettingsPageElement;

/**
 * @author zheli
 */
public class DevicesSwitchIpSettingsPage extends DevicesSwitchIpSettingsPageElement {
    final static Logger logger = Logger.getLogger("DevicesSwitchIpSettingsPage");

    /**
     * @throws Exception
     */
    public DevicesSwitchIpSettingsPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefDevicesSwitchIpsettings);
        MyCommonAPIs.sleep(5 * 1000);
        logger.info("init...");
//        if (WebportalParam.isRltkSW1)
//            throw new RuntimeException("PRJCBUGEN-26465");
    }

    public void setIp(Map<String, String> ip) {
        
//        clickElementIdViaJs(SwitchIpSet);
        
        for (String ss : ip.keySet()) {
            logger.info(ss + ": " + ip.get(ss));
        }
        if (isDHCP()) {
            MyCommonAPIs.sleepi(10);
            dhcpAutomatically.click();
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
        if (ip.get("DNS Server1") != null) {
            dnsServer1.setValue(ip.get("DNS Server1"));
        }
        if (ip.get("DNS Server2") != null) {
            dnsServer2.setValue(ip.get("DNS Server2"));
        }
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

    public void setIpToDhcp() {
        if (isDHCP()) {
        } else {
            MyCommonAPIs.sleep(10 * 1000);
            dhcpAutomatically.click();
        }
        saveBtn.click();
        waitReady();
        MyCommonAPIs.sleep(10 * 1000);
    }

    public void setIpToStatic() {
        if (isDHCP()) {
            MyCommonAPIs.sleep(10 * 1000);
            dhcpAutomatically.click();
        } else {
        }
        saveBtn.click();
        waitReady();
        MyCommonAPIs.sleep(10 * 1000);
    }

    /**
     * @return true for dhcp, false for static
     */
    public boolean isDHCP() {
        boolean result = false;
        if (ipAddress.has(Condition.attribute("readonly"))) {
            result = true;
        } else {
            result = false;
        }
        logger.info("isDHCP: " + result);
        return result;
    }
    
    public void newsetIp(Map<String, String> ip) {
        
//      clickElementIdViaJs(SwitchIpSet);
        String ipvalue=ipAddress.getValue();        
        String[] ipParts=ipvalue.split("\\.");
        String newip=ipParts[0]+"."+ipParts[1]+"."+ipParts[2]+"."+"229";
      
      for (String ss : ip.keySet()) {
          logger.info(ss + ": " + ip.get(ss));
      }
      if (isDHCP()) {
          MyCommonAPIs.sleepi(3);
          dhcpAutomatically.click();
      } 
      if (ip.get("IP Address") != null) {
          ipAddress.setValue(newip);
      }
      if (ip.get("Subnet Mask") != null) {
          subnetMask.setValue(ip.get("Subnet Mask"));
      }
      if (ip.get("Gateway Address") != null) {
          gatewayAddress.setValue(ip.get("Gateway Address"));
      }
      if (ip.get("DNS Server1") != null) {
          dnsServer1.setValue(ip.get("DNS Server1"));
      }
      if (ip.get("DNS Server2") != null) {
          dnsServer2.setValue(ip.get("DNS Server2"));
      }
      saveBtn.click();
      MyCommonAPIs.sleep(10 * 1000);
  }
}
