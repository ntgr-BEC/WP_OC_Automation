/**
 * 
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

/**
 * @author ronliu
 *
 */
public class DevicesNasIpSettingsPageElement {
    public SelenideElement firmware  = $("#pCurrntFirmWare");
    public SelenideElement ipaddress = $("#ip_0");
    public SelenideElement subnet    = $("#subnet_0");
    public SelenideElement getaway   = $("#gateway_0");
    public SelenideElement dns       = $("#dns_0");
}
