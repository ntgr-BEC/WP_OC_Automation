/**
 * 
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;

/**
 * @author zheli
 *
 */
public class AccountPageElementNas {
    //
    public SelenideElement addNetWorkButton         = $("[data-tooltip=\"Add Network\"]");
    public SelenideElement addNetLocationName       = $("#addNetLocationName");
    public SelenideElement addNetPassword           = $("#locationPass");
    public SelenideElement addNetStreet             = $("#addNetStreet");
    public SelenideElement addNetCity               = $("#addNetCity");
    public SelenideElement addNetState              = $("#addNetState");
    public SelenideElement addNetZipcode            = $("#addNetZipcode");
    public SelenideElement netCountryList           = $("#netCountryList");
    public SelenideElement timeZone                 = $("#timeZone");
    public SelenideElement alert                    = $(".alert alert-danger alert-dismissable");
    public SelenideElement firstLocationMoreIcon    = $("#_ancliLocDiv0");
    public SelenideElement firstLocationEditIcon    = $x("//div[@id='_divlocDiv0']//a[@class='colorGray']");
    public SelenideElement firstLocationDeleteIcon  = $x("//div[@id='_divlocDiv0']//a[@class='colorRed']");
    public SelenideElement secondLocationMoreIcon   = $("#_ancliLocDiv1");
    public SelenideElement secondLocationEditIcon   = $x("//div[@id='_divlocDiv1']//a[@class='colorGray']");
    public SelenideElement secondLocationDeleteIcon = $x("//div[@id='_divlocDiv1']//a[@class='colorRed']");
    public SelenideElement confirmLocationDelete    = $x("//button[text()='Yes, delete location']");

    public SelenideElement locationName(String text) {
        SelenideElement location = $("[data-name='" + text + "']");
        return location;
    }

}
