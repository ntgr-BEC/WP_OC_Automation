/**
 *
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;

import java.util.logging.Logger;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

/**
 * @author lavi
 */
public class DevicesOrbiWANIPPageElement extends MyCommonAPIs {
    Logger logger;

    public DevicesOrbiWANIPPageElement() {
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
    }

    public SelenideElement cbDhcp     = $("#inpCheckBoxOrbiIpSett");
    public SelenideElement txtIp      = $("#orbiWanIpAddress");
    public SelenideElement txtGateway = $("#orbiDefaultGateway");
    public SelenideElement txtMask    = $("#orbiSubnetMask");
    public SelenideElement txtDNS1    = $("#dnsServer1");
    public SelenideElement txtDNS2    = $("#dnsServer2");

    public boolean isDHCP() {
        return $(cbDhcp).isSelected();
//        if (txtIp.isEnabled()) {
//            return false;
//        }else {
//            return true;
//        }
    }

    public void setDHCP(boolean todhcp) {
        setSelected($(cbDhcp), todhcp, true);
        clickButton(0);
        sleepsyncorbi();
    }

    public void setDHCP(String ip, String gw, String mask, String dns1, String dns2) {
        setSelected($(cbDhcp), false, true);
        if (!ip.isEmpty()) {
            txtIp.clear();
            txtIp.sendKeys(ip);
        }
        if (!gw.isEmpty()) {
            txtGateway.clear();
            txtGateway.sendKeys(gw);
        }
        if (!mask.isEmpty()) {
            txtMask.clear();
            txtMask.sendKeys(mask);
        }
        if (!dns1.isEmpty()) {
            
            txtDNS1.clear();
            txtDNS1.sendKeys(dns1);
        }
        if (!dns2.isEmpty()) {
            
            txtDNS2.clear();
            txtDNS2.sendKeys(dns2);
            
        }
        clickButton(0);
        sleepsync();
    }
}
