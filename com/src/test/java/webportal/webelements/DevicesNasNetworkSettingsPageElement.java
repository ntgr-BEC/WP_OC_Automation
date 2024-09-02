/**
 * 
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

/**
 * @author ronliu
 *
 */
public class DevicesNasNetworkSettingsPageElement {
    public SelenideElement firmware     = $("#pCurrntFirmWare");
    public SelenideElement ipaddress    = $("#ip_0");
    public SelenideElement mtu          = $("#divMtuNwLinksNasNwSet0");
    public SelenideElement resetnetwork = $("#btnResetnasnwSett");
    public SelenideElement resetyes     = $(Selectors.byText("Yes, reset"));
    public SelenideElement macAddress   = $("#divOvrFlwNwLinksNasNwSet0");

}
