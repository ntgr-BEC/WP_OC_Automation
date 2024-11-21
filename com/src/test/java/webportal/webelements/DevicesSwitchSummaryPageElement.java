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
public class DevicesSwitchSummaryPageElement extends MyCommonAPIs {
    public static SelenideElement connectedNeighbors   = $(Selectors.byText(WebportalParam.getLocText("Connected Neighbors")));
    public String[]               btnActionIndexString = {
            "delete", "reboot", "reset", "share"
    };
    
    
    public String portInfo = "//span[@class='ethernet-count' and text()='%s']/ancestor::li[@class='colorBlack']//div[@class='tooltipblock']/p[%d]";
    public String getPortVlanId(String port) {
        portChoice(port).hover();
        SelenideElement ele = $x(String.format(String.format(portInfo, port, 5)));
        return ele.getText();
    }
    
    public SelenideElement portChoice(String text) {
        SelenideElement port = $(Selectors.byXpath("//span[text()='" + text + "'][@class='ethernet-count']"));
        return port;
    }

    public static SelenideElement reloadIcon     = $("[data-tooltip=\"" + WebportalParam.getLocText("Reload") + "\"]");
    public static SelenideElement yesReload      = $(Selectors.byText(WebportalParam.getLocText("Yes, reload")));
    String                        deviceDetails  = "#divPgeHtml .row div:nth-child(%d) div:last-child";
    public SelenideElement        name           = $(String.format(deviceDetails, 1));
    public SelenideElement        serialNumber   = $(String.format(deviceDetails, 2));
    public SelenideElement        model          = $(String.format(deviceDetails, 3));
    public SelenideElement        baseMACAddress = $(String.format(deviceDetails, 4));
    public SelenideElement        upTime         = $(String.format(deviceDetails, 5));
    public SelenideElement        vlanInUse      = $(String.format(deviceDetails, 6));
    public SelenideElement        traffic        = $(String.format(deviceDetails, 7));
    public SelenideElement        iPAddress      = $(String.format(deviceDetails, 8));
    public SelenideElement        firmware       = $(String.format(deviceDetails, 9));
    public SelenideElement        bootcode       = $(String.format(deviceDetails, 10));
    public SelenideElement        dateAndTime    = $(String.format(deviceDetails, 11));
    public SelenideElement reboot          = $("//*[@id=\\\"aModalTwoSwitchBtn\\\"]/span");
    public SelenideElement rebootconfirm   = $x("/button[@class = 'btn btn-danger'][text() = 'Yes, reboot']']");
    public SelenideElement btnActions(int index) {
        return $(String.format("#divColMdSwitchBtn [class*=%s] span", btnActionIndexString[index]));
    }
}
