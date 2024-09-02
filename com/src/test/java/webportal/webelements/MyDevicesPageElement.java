package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class MyDevicesPageElement extends MyCommonAPIs {

    public SelenideElement managerdropdown = $x("//img[contains(@src,'icon-setting')]");

    public String                 mydevicesbar      = "//h3[@id='hNwDevMyDevices0']";
    public SelenideElement        plusmydevices     = $x(mydevicesbar + "//i[@id='iconPlusNwDevMyDevices0']");
    public SelenideElement        filterdevices     = $("#liMyDevices");
    public SelenideElement        filterswitch      = $("#mySwitch");
    public static SelenideElement filterswitchnew   = $x("//a[text()='" + WebportalParam.getLocText("Switch") + "']");
    public SelenideElement        filternas         = $("#myNas");
    public static SelenideElement filternasnew      = $x("//a[text()='" + WebportalParam.getLocText("NAS") + "']");
    public SelenideElement        filterap          = $("#myAP");
    public static SelenideElement filterapnew       = $x("//a[text()='" + WebportalParam.getLocText("Access Point") + "']");
    public SelenideElement        applyfilterdevice = $("#btnApplyMyDevices");

    public SelenideElement checkdevicesno = $("#hSNoDevMyDevices0");

}
