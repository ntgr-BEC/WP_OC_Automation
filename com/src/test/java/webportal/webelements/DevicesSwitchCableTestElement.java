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
 */
public class DevicesSwitchCableTestElement extends MyCommonAPIs {
    public SelenideElement testModCabeltest = $("#btnTestModCabeltest");
    public SelenideElement cancel           = $("#ancUpdateCabeltest");
    public SelenideElement yesTestTheCable  = $("#btnTestCabelTest");
    public SelenideElement cabletestAlert   = $(".alert-dismissable");

    public SelenideElement portChoice(String text) {
        String sPort = String.format("//li[contains(@id, 'liulcableTest')]//span[text()='%s']", text);
        // String sPort = "#liulcableTest" + String.format("%d", Integer.parseInt(text) - 1);
        if ($x(sPort).exists())
            return $x(sPort);

        SelenideElement port = $x("//span[text()='" + text + "'][contains(@id, 'spnEtgercableTest')]");
        return port;
    }
    
    public SelenideElement txtNoResult = $("#showNotification div");

    public SelenideElement testResult(String text) {
        SelenideElement testStatus = $x("//td[text()='" + text + "'][contains(@id, 'tdcabeltstStaus')]/../td[2]");
        return testStatus;
    }

    public static SelenideElement reloadIcon  = $("[data-tooltip=\"" + WebportalParam.getLocText("Reload") + "\"]");
    public static SelenideElement yesReload   = $(Selectors.byText(WebportalParam.getLocText("Yes, reload")));
    public SelenideElement        applyButton = $("#btnSaveBtnPrtMirr");

    public String cabletestAlertString = cabletestAlert.getSearchCriteria();
}
