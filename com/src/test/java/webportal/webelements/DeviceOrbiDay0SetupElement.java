package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import java.util.logging.Logger;

import org.openqa.selenium.By;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

public class DeviceOrbiDay0SetupElement extends MyCommonAPIs {
    Logger logger = Logger.getLogger("DeviceOrbiDay0SetupElement");
    
//    public static SelenideElement btnNext      = $(Selectors.byText(WebportalParam.getLocText("Next")));
    //public static SelenideElement btnNext      = $(Selectors.byText("Next"));
    public static SelenideElement btnNext      = $x("//div[contains(@style,'display: block')]//button[text()='Next']");
    public static SelenideElement radioDeviceMode = $x("//input[@name='DeviceMode']");
    public static SelenideElement radioApMode      = $x("//input[@id='apMode']/..");
    public static SelenideElement radioRouterModeInput = $("#routerMode");
    public static SelenideElement radioRouterMode    = $x("//input[@id='routerMode']/..");
//    public static SelenideElement btnSkip = $(Selectors.byText(WebportalParam.getLocText("Skip")));
    public static SelenideElement btnSkip = $(Selectors.byText("Skip"));
    public static SelenideElement inputDeviceCount    = $(Selectors.byName("device-count"));
    public static SelenideElement btnDeviceExpand  = $(".icon.icon-icon-expand");
    public static SelenideElement btnDeviceCollapse = $(".icon.icon-icon-collapse");
    public static SelenideElement inputWifiName = $x("//input[@data-type='orbiSsid']");
    public static SelenideElement inputWifiPwd = $x("//input[@data-type='ssidPassword']"); //$(By.name("password"));
//    public static SelenideElement btnFinish = $(Selectors.byText(WebportalParam.getLocText("Finish")));
    public static SelenideElement btnFinish = $(Selectors.byText("Finish"));
    public static SelenideElement popuplastmsg = $x("//*[contains(text(),'wait until initial setup')]");
}
