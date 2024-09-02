/**
 *
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;

/**
 * @author zheli
 *
 */
public class DevicesSwitchConnectedNeighboursPortConfiqSummaryPageElement extends MyCommonAPIs {
    public static SelenideElement settings = $(Selectors.byText(WebportalParam.getLocText("Settings")));

    public SelenideElement BytesSent     = $("#divByte2SentDevSumm span");
    public SelenideElement BytesReceived = $("#divByt2RecDevSumm span");

    public SelenideElement NeighborName         = $("#divNdispTblDevSumm span");
    public SelenideElement NeightborDescription = $("#divNdisp2TblDevSumm span");
    public SelenideElement MNGTIPAddress        = $("#divMgntIp2DevSumm span");
    public SelenideElement ChassisID            = $("#divMgntChidDevSumm span");
    public SelenideElement portId               = $("#divPrtDevSumm span");
    public SelenideElement PortDescription      = $("#divPrtDescSumm span");

    public String          PortConfigMode = "#divDefaultVlanDevSumm span";
    public SelenideElement vlanList       = $("#divDlanDevSumm");

    public String getPortConfigMode() {
        return getText($$(PortConfigMode).last());
    }

    /**
     * @return true port is auth, false is unauth
     */
    static String sAuthorizedText = WebportalParam.getLocText("Authorized");

    public boolean isPortAuth() {
        if (getPortConfigMode().contains(sAuthorizedText))
            return true;
        else
            return false;
    }
}
