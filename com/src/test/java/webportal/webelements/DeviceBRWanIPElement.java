package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;

import java.util.logging.Logger;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

public class DeviceBRWanIPElement extends MyCommonAPIs {
    Logger logger = Logger.getLogger("DeviceBRWanIPElement");

    public SelenideElement cbDhcp     = $("#inpCheckBoxSwitchIpSett");
    public SelenideElement txtIp      = $("#ipAddress");
    public SelenideElement txtMask    = $("#subnetMask");
    public SelenideElement txtGateway = $("#defaultGateway");
    public SelenideElement txtDNS1    = $("#dnsServer1");
    public SelenideElement txtDNS2    = $("#dnsServer2");
    
    public boolean isDHCP() {
        return $(cbDhcp).isSelected();
    }
}
