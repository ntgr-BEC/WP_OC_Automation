/**
 *
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;

/**
 * @author zheli
 *
 */
public class DevicesSwitchPortMirrorElement extends MyCommonAPIs {
    public SelenideElement portMirrorMode  = $("#inpChkboxPrtMirr");
    public SelenideElement portMirrorOnOff = $("#spnCliderPrtMirr");
    public SelenideElement portMirrorAlert = $(".alert-dismissable");

    public SelenideElement srcportChoice(String text) {
        String sPort = String.format("//div[contains(@id, 'divSwitchRowsPrtMirr')]//span[text()='%s']", text);
        if ($x(sPort).exists())
            return $x(sPort);
        SelenideElement srcport = $x("//span[text()='" + text + "'][contains(@id, 'spnPrtSwitchRowsPrtMirr')]");
        SelenideElement srcport1 = $("span[text()='" + text + "'][id*='spnPrtSwitchRowsPrtMirr']");
        return srcport;
    }

    public SelenideElement destportChoice(String text) {
        String sPort = String.format("//div[contains(@id, 'divSecPrtMirr')]//span[text()='%s']", text);
        if ($x(sPort).exists())
            return $x(sPort);
        SelenideElement dstport = $x("//span[text()='" + text + "'][contains(@id, 'spnEtherDouSecPrtMirr')]");
        return dstport;
    }

    public static SelenideElement reloadIcon  = $("[data-tooltip=\"" + WebportalParam.getLocText("Reload") + "\"]");
    public static SelenideElement yesReload   = $(Selectors.byText(WebportalParam.getLocText("Yes, reload")));
    public SelenideElement        applyButton = $("#btnSaveBtnPrtMirr");

    public String portMirrorAlertString = portMirrorAlert.getSearchCriteria();
}
