/**
 * 
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;

/**
 * @author ronliu
 *
 */
public class DevicesNasdiagnosticModeElement {
    public static String   emailaddress          = "rongqing.liu@netgear.com";
    public SelenideElement securediganosticsmode = $("#spSliderDiagMod");
    public SelenideElement okbutton              = $x("/html/body/div[3]/div/div[2]/div/div[3]/div/div/div[3]/button[2]");
    public SelenideElement portNumber            = $("#divPrtDiagMod");
    public SelenideElement shareButton           = $("#aDropDownOneNas");
    public SelenideElement emailAddressInput     = $("#share_email_id");
    public SelenideElement sendButton            = $x("//button[text()='Send']");

}
