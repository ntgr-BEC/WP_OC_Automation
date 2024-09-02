/**
 *
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

/**
 * @author Netgear
 */
public class DevicesSwitchIpSettingsPageElement extends MyCommonAPIs {
    public SelenideElement ipAddress         = $("#inpTextIpAddrSwitchIpSett");
    public SelenideElement subnetMask        = $("#inpSubNetSwitchIpSett");
    public SelenideElement gatewayAddress    = $("#inpNameSwitchIpSett");
    public SelenideElement dnsServer1        = $("#inpIpDenServer1SwitchIpSett");
    public SelenideElement dnsServer2        = $("#inpDenServSwitchIpSett");
    public SelenideElement saveBtn           = $("#btnSaveSwitchIpSett");
    public SelenideElement alert             = $(".alert-danger");
    public SelenideElement dhcpAutomatically = $("#lblSwitchSwitchIpSett");
    public String          SwitchIpSet       = "spnSliderSwitchIpSett";
}
