/**
 *
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import java.util.logging.Logger;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

/**
 * @author lavi
 */
public class DevicesOrbiLANIPPageElement extends MyCommonAPIs {
    Logger logger;
    
    public DevicesOrbiLANIPPageElement() {
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
    }
    
    public static SelenideElement txtIp   = $("#inpTextIpAddrOrbiIpSett");
    public static SelenideElement txtMask = $("#inpSubNetOrbiIpSett");
    
    public static String          cbDhcp     = "//input[@id=\"inpCheckBoxOrbiIpSett\"]";
    public static SelenideElement txtIpStart = $x("//input[@id=\"inpNameOrbiIpSett\"]");
    public static SelenideElement txtIpEnd   = $x("//input[@id=\"inpIpDenServer1OrbiIpSett\"]");
    public static SelenideElement          saveButton     = $("#btnSaveOrbiIpSett");
    public static SelenideElement          alterMsg     = $x("//div[contains(@class,'alert-danger')]");
    public boolean isDHCP() {
        return $x(cbDhcp).isSelected();
    }
    
    /**
     * @param todhcp
     *                   true to dhcp
     * @param fake
     *                   true to not apply
     */
    public void setDHCP(boolean todhcp, boolean fake) {
        setSelected($x(cbDhcp), todhcp, true);
        if (!fake) {
            clickButton(0);
        }
    }
}
