/**
 *
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

/**
 * @author Netgear
 */
public class DevicesSwitchStatisticsPageElement extends MyCommonAPIs {
    public SelenideElement ptempSwitchStatics      = $("#ptempSwitchStatics");
    public SelenideElement pcpu_usageSwitchStatics = $("#pcpu_usageSwitchStatics");
    public SelenideElement ptxDataSwitchStatics    = $("#ptxDataSwitchStatics");
    public SelenideElement pRxDataSwitchStatics    = $("#pRxDataSwitchStatics");
    public SelenideElement pFanStateSwitchStatics  = $("#pFanStateSwitchStatics");
    public SelenideElement clearCountersButton     = $("i small");

    public String sTable = "#poeTable tbody";
}
