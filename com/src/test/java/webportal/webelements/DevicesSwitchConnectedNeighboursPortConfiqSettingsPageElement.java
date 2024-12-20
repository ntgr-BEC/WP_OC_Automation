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
public class DevicesSwitchConnectedNeighboursPortConfiqSettingsPageElement extends MyCommonAPIs {
    public SelenideElement        portDescription       = $("#primaryPort");
    public SelenideElement        portCheck             = $("#isEnable");
    public SelenideElement        enablePortButton      = $(".on-off-slider");
    public SelenideElement        rateLimitPlusButton   = $("#hOvrFlw1Settng");
    public static SelenideElement settings              = $(Selectors.byText(WebportalParam.getLocText("Settings")));
    public SelenideElement        deplexModeSelect      = $("#selDupSettng");
    public SelenideElement        portSpeedSelect       = $("#selPrtSpedSettng");
    public SelenideElement        maxFrameSlider        = $("#maxFrameSlider");
    public SelenideElement        devicesIcon           = $("[href=\"/#/devices/dash\"]");
    public SelenideElement        frameSizeValue        = $x("//div[@id=\"maxFrameSlider\"]//div/div[1]");
    public SelenideElement        stormSlider           = $("#stormSlider");
    public SelenideElement        stormControlRateValue = $x("//div[@id=\"stormSlider\"]//div/div[1]");
    public SelenideElement        egressSlider          = $("#EgressSlider");
    public SelenideElement        egressValue           = $x("//div[@id=\"EgressSlider\"]//div/div[1]");
    
    public SelenideElement lbVlanPVID = $("#selVlanIdSettng");
    
    public String          btnPowerManagement = "#hNsaAccordHeadSettng";
    public String          btnRateLimit       = "#hOvrFlw1Settng";
    public SelenideElement cbEnablePoe        = $("#isPOE");
    public SelenideElement lbPoEStand         = $("#poeStandardField");
    
    public boolean setPoEStand(String text) {
        try {
            lbPoEStand.selectOption(WebportalParam.getLocText(text));
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public SelenideElement lbdetectionType = $("select[name*=detectionType]");
    
    public void setDetectionType(String text) {
        lbdetectionType.selectOptionContainingText(text);
        // lbdetectionType.selectOption(WebportalParam.getLocText(text));
    }
    
    public void setDetectionType(int iPos) {
        lbdetectionType.selectOption(iPos);
    }
    
    public SelenideElement lbpriority = $("select[name*=priority]");
    
    public void setPriority(String text) {
        // lbpriority.selectOption(WebportalParam.getLocText(text));
        lbpriority.selectOption(text);
    }
    
    public SelenideElement cbUseDefaultClass = $("#isUseDefaultClass");
    public SelenideElement lbClass           = $("#powerLimitTypeField");
    public String          sUserDefined      = "User Defined";
    
    public boolean hasUserDefined() {
        return getTexts(lbClass.getSearchCriteria()).contains(sUserDefined);
    }
    
    public boolean setClass(String text) {
        try {
            lbClass.selectOptionContainingText(text);
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public SelenideElement txtPowerLimitNew   = $("#divRangeSlideSettng input");
    public SelenideElement txtPowerLimitValue = $("#powerSlider .tooltip-inner");
    public SelenideElement txtPowerMaxValue   = $("#spnPulRghtRangeSlideSettng");
    public SelenideElement lbSchName          = $("#selSchNameSetng");
    public SelenideElement btnBatchConf       = $("#btnModlSettng");
    public SelenideElement btnBatchConfYes    = $("#btnBatchOfSett");
    
    public SelenideElement lbselect     = $("[name*=radiusAuthMode]");
    public SelenideElement lbPvidSelect = $("#selVlanIdSettng");
//    public String          vlanIdOption = "//span[text()='VLAN ID']/ancestor::div[contains(@class,'notigroup')]//input[@type='checkbox']/parent::*/p";
//    public SelenideElement vlanIdSelect = $x("//span[text()='VLAN ID']/ancestor::div[contains(@class,'notigroup')]//div[@data-toggle='dropdown']");
    public String          vlanIdOption = "//div[@class=\"tagUntagBlock smart-form m-b-20 d-grid\"]";
    public SelenideElement vlanIdSelect = $x("//*[@id=\"selVlanIdSettng\"]");
    
    public String          vlanIdStr = "//p[contains(@class,'') and text()=%s]/../i";
    public SelenideElement tag       = $x("//*[text()=\"Tag\"]");
    public String          vlanRadioStr = vlanIdStr + "/ancestor::div[contains(@class,'d-flex')]//img";
  //p[contains(@class,'help-txt') and text()='35']/ancestor::div[contains(@class,'d-flex')]//img
    
}
