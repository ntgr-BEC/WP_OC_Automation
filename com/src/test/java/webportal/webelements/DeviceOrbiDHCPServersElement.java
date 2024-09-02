package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;

import java.util.logging.Logger;

import org.openqa.selenium.Keys;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class DeviceOrbiDHCPServersElement extends MyCommonAPIs {
    Logger logger = Logger.getLogger("DeviceOrbiDHCPServersElement");

    public SelenideElement btnAdd     = $("#showNotification+div span");
    public String          sDhcpTable = "table tbody";
    
    public void dumpInfo() {
        if ($(sDhcpTable).exists()) {
            getTexts(sDhcpTable + " tr");
        }
    }
    
    public SelenideElement lbVlan     = $("#selectedVlanId");
    public SelenideElement txtIp      = $("#inpTextIpAddrOrbiIpSett");
    public SelenideElement cbDhcp     = $("#inpCheckBoxOrbiIpSett");
    public SelenideElement txtIpStart = $("#inpNameOrbiIpSett");
    public SelenideElement txtIpEnd   = $x("//input[@name=\"endIP\"]");
    
    public void setIp(SelenideElement se, String ip) {
        String last = ip.substring(ip.lastIndexOf("."));
        se.doubleClick();
        for (int i = 0; i < 5; i++) {
            se.sendKeys(Keys.BACK_SPACE);
        }
        se.sendKeys(last);
    }
    
}
