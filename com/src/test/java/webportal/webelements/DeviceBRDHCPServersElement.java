package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;

import java.util.logging.Logger;

import org.openqa.selenium.Keys;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

public class DeviceBRDHCPServersElement extends MyCommonAPIs {
    Logger logger = Logger.getLogger("DeviceBRDHCPServersElement");

    public SelenideElement btnAdd     = $("#showNotification+div span");
    public String          sDhcpTable = "table tbody";

    public void dumpInfo() {
        if ($(sDhcpTable).exists()) {
            getTexts(sDhcpTable + " tr");
        }
    }

    public SelenideElement lbVlan     = $("#DHCPServerVLANInput");
    public SelenideElement txtIp      = $("#inpTextIpAddrSwitchIpSett");
    public SelenideElement cbDhcp     = $("#inpCheckBoxSwitchIpSett");
    public SelenideElement txtIpStart = $("#inpNameSwitchIpSett");
    public SelenideElement txtIpEnd   = $("#inpIpDenServer1SwitchIpSett");

    public void setIp(SelenideElement se, String ip) {
        String last = ip.substring(ip.lastIndexOf("."));
        se.doubleClick();
        for (int i = 0; i < 5; i++) {
            se.sendKeys(Keys.BACK_SPACE);
        }
        se.sendKeys(last);
    }
}
