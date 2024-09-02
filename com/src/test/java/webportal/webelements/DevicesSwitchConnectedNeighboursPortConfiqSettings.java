/**
 * 
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

/**
 * @author zheli
 *
 */
public class DevicesSwitchConnectedNeighboursPortConfiqSettings {
    public SelenideElement portDescription     = $("#primaryPort");
    public SelenideElement EnablePortButton    = $(".on-off-slider");
    public SelenideElement RateLimitPlusButton = $(".fa fa-plus");
}
