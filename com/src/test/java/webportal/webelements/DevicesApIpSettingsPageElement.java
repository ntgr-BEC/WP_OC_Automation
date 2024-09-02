/**
 * 
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;

/**
 * @author Netgear
 *
 */
public class DevicesApIpSettingsPageElement {
    public SelenideElement ipAddress         = $("#ipAddress");
    public SelenideElement subnetMask        = $("#subnetMask");
    public SelenideElement gatewayAddress    = $("#gatewayAddress");
    public SelenideElement dnsServer        = $("#dnsServer");
    public SelenideElement saveBtn           = $x("//*[@id=\"divMainIpSetting\"]/div[3]/button[2]");
    public SelenideElement alert             = $(".alert-danger");
    public SelenideElement dhcpAutomatically = $x("//*[@id=\"divOnOffSettIpSettng\"]/label/span");
}
