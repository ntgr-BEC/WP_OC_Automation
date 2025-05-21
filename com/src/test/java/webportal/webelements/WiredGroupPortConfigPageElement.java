/**
 *
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;

/**
 * @author Netgear
 */
public class WiredGroupPortConfigPageElement extends MyCommonAPIs {
    public SelenideElement clickPort(String devicseName, String port) {
        SelenideElement Device = $x(
                "//h3/span[contains(text(),'" + devicseName + "')]/../../div//span[text()='" + port + "'][@class='ethernet-count']");
        return Device;
    }
    
    public SelenideElement loGo              = $(sMyDevicesLink);
    public SelenideElement defaultVlanSelect = $("#selVlanIdGroupPort");
    public SelenideElement enablePortButton  = $("#spnSliderGroupPort");
    public SelenideElement saveButton        = $("#btnSaveWiredGroupPort");
    public SelenideElement portSpeedSelect   = $("#selPrtSpdGroupPort");
    
    public void portSpeedSelectSet(String speed) {
        if (speed.toLowerCase().equals("auto")) {
            portSpeedSelect.selectOption(0);
        } else {
//            String sSet = speed.split(" ")[0];
            portSpeedSelect.selectOptionContainingText(speed);
        }
    }
    
    public String portSpeedSelectGet() {
        String sGet = portSpeedSelect.getSelectedText();
        if (sGet == "")
            return "Auto";
        else if (sGet.toLowerCase().contains("mbps"))
            return sGet;
        else
            return sGet + "Mbps";
    }
    
    public SelenideElement duplexModeSelect = $("#selDuplexGroupPort");
    
    public void duplexModeSelectSet(String mode) {
        duplexModeSelect.selectOption(WebportalParam.getLocText(mode));
    }
    
    public String duplexModeSelectGet() {
        String sGet = duplexModeSelect.getSelectedText();
        return sGet;
    }
    
    public ElementsCollection allPorts              = $$x("//i[@class=\"icon icon-port-outer\"]/../../span[@class='ethernet-count']");
    public SelenideElement    stormSlider           = $("#stormSlider");
    public SelenideElement    stormControlRateValue = $x("//div[@id=\"stormSlider\"]//div/div[1]");
    public SelenideElement    egressSlider          = $("#EgressSlider");
    public SelenideElement    egressValue           = $x("//div[@id=\"EgressSlider\"]//div/div[1]");
    
    // ports for enable/disable
    public ElementsCollection secPort4     = $$(".box-scroller li:nth-child(4)");
    public SelenideElement    cbEnablePort = $("input[id*='isPOE']");

    public String getDeviceString(String devName) {
        return String.format("//span[contains(text(),'%s')]/../../..", devName);
    }

    /**
     * @param  devName
     * @param  portId
     *                 1~n
     * @return
     */
    public SelenideElement getPort(String devName, String portId) {
        return getDevicePort(getDeviceString(devName), portId);
    }
    
    /**
     * @param devName
     * @param portId
     *                1~n
     */
    public void clickPortOnSwitch(String devName, String portId) {
        logger.info(String.format(": <%s>-%s>", devName, portId));
        getPort(devName, portId).click();
    }

    public SelenideElement clickVLAN(String VLANID) {
         SelenideElement VLAN = $x("//*[@id=\"vlanCheckbox_"+VLANID+"\"]/..");
        return VLAN;
    }

    public SelenideElement Untag            = $x("//button[text()=\"Untag\"]");
    public SelenideElement tag              = $x("//button[text()=\"Tag\"]");
    public ElementsCollection confirmtag    = $$x("//button[text()=\"Yes\"]");
    
}
