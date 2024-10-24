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
public class WiredVLANForVLANElement extends MyCommonAPIs {
    public String vlanlistTable    = "#tbdywiredVlan tr";
    public String vlanlistTableCol = "#tbdywiredVlan td:nth-of-type(1) span";
    public String vlanItemEdit     = "img[src*=\"edit\"]";
    public String vlanItemDel      = "img[src*=\"del\"]";
    public static SelenideElement editnetwork                    = $x("//*[@id=\"headerLocImg\"]");
    public static SelenideElement gotoNetworksetup               = $x("//*[@id=\"divSideBarSecEditVlan\"]/div/div[3]/a");
    public SelenideElement GoToManagementVLAN         = $("#tdtbdyFooterurl0");
    public SelenideElement Next      = $x("(//button[text()='Next'])[8]");
    public SelenideElement Next1     = $("button.saveBtn");
    public SelenideElement Next2     = $x("(//button[text()='Next'])[10]");
    public SelenideElement loGo         = $(sMyDevicesLink);
    public SelenideElement vlanAdd      = $x("//a[@id='ancOpenwiredVlan']");
    public SelenideElement vlancontinue = $x("//button[text() = 'Continue']");
    public SelenideElement voiceVlan    = $("#divColSmThreeBtn");
    public SelenideElement networkname  = $x("//input[@name = 'networkName']");

    public SelenideElement txtNetName            = $("[name*=networkName]");
    public SelenideElement txtNetDesc            = $("[name*=vlanDesc]");
    public SelenideElement lbNetType             = $("[name*=networkType]");
    public SelenideElement txtvlanName           = $("[name*=vlanName]");
    public SelenideElement txtvlanId             = $("[name*=vlanId]");
    public SelenideElement btnNextOrViewNetworks = $(".actionBtnRow button:last-child");
    public SelenideElement confirm               = $x("//button[text() = 'Confirm']");
    public SelenideElement ViewNetwork           = $x("//button[text() = 'View Networks']");
    
    
    public SelenideElement dataVlan          = $("#data");
    public SelenideElement videoVlan         = $("#divColSmFourBtn");
    public SelenideElement customVlan        = $("#divColSmFiveBtn");
    public SelenideElement vlanNext          = $("#addNewVlanModal[style*=block] #buPrimaryOnClickBtn");
    public SelenideElement vlanName          = $x("//div[@id=\"divOnOfSetVlanEditVlan\"]//input[@id=\"vlanName\"]");
    public SelenideElement vlanId            = $x("//div[@id=\"divOnOffSettVlanIdEditVlan\"]//input[@id=\"vlanId\"]");
    public SelenideElement showPortMember    = $("#iconPlusEditVlan");
    public SelenideElement btnsaveVlan       = $("#saveEditVlan");
    public SelenideElement btnVideo          = $(".cstmSlider.cstmRound");
    public String          btnVideoid          = "divToggEditVlan";

    public SelenideElement guestEmployeeVlan = $x("(//div[@id='divCommontwoBtn'])[2]");
    public SelenideElement showPublicPort    = $("#iconPulPlusEditVlan");
    public SelenideElement showEmployeePort  = $("#iconPlusArspnMinColEditVlan");
    public SelenideElement showGuestPort     = $("#iconPlusTrEditVlan");

    public SelenideElement alert        = $(".alert-dismissable");
    public SelenideElement btnClickHere = $(".CustomRadioBlock .colorPurple");

    public void clickSave() {
        takess("check vlan conf");
        click(btnsaveVlan);
        getPageErrorMsg();
    }

    // 0 - new, 1 - old
    public static int isNewSwitchUI    = 99;
    public String     newSwitchUIXpath = "//h5/span[text()='%s']/../../../..//";
    public String     oldSwitchUIXpath = "//h5[text()='%s']/../../..//";
    
    public String getSwitchXpath(String dutName) {
        if (isNewSwitchUI == 99) {
            isNewSwitchUI = 1;
            if ($x(String.format("//h5/span[text()='%s']", dutName)).exists()) {
                logger.info("new ui for switch");
                isNewSwitchUI = 0;
            }
        }
        if (isNewSwitchUI == 1)
            return String.format(oldSwitchUIXpath, dutName);
        else
            return String.format(newSwitchUIXpath, dutName);
    }
    
    public SelenideElement portChoice(String dutName, String text) {
        SelenideElement srcport = $x(String.format("%sspan[text()='%s']", getSwitchXpath(dutName), text));
        return srcport;
    }

    public SelenideElement selectAllClick(String dutName) {
        SelenideElement button = $x(String.format("(%sbutton)[1]", getSwitchXpath(dutName)));
        return button;
    }

    public SelenideElement deSelectAllClick(String dutName) {
        SelenideElement button = $x(String.format("(%sbutton)[1]", getSwitchXpath(dutName)));
        return button;
    }

    public SelenideElement untagClick(String dutName) {
        SelenideElement button = $x(String.format("(%sbutton)[2]", getSwitchXpath(dutName)));
        return button;
    }

    public SelenideElement tagClick(String dutName) {
        SelenideElement button = $x(String.format("(%sbutton)[3]", getSwitchXpath(dutName)));
        return button;
    }

    public SelenideElement deleteClick(String dutName) {
        SelenideElement button = $x(String.format("(%sbutton)[5]", getSwitchXpath(dutName)));
        return button;
    }

    public SelenideElement portMember(String text) {
        SelenideElement srcport = $("#port_" + text);
        return srcport;
    }

    // not used
    public SelenideElement publicportChoice(String dutName, String text) {
        SelenideElement srcport = $x("//h5[text()='" + dutName + "']/../../..//div[contains(@id, \"divEtherBlckEditVlan\")]//span[text()='" + text
                + "'][contains(@id, 'spnEtherBlckEditVlan')]");
        return srcport;
    }

    public SelenideElement employeeportChoice(String dutName, String text) {
        SelenideElement srcport = $x("//h5[text()='" + dutName + "']/../../..//div[contains(@id, \"divEtherEmEditVlan\")]//span[text()='" + text
                + "'][contains(@id, 'spnEtherBlckEditVlan')]");
        return srcport;
    }

    public SelenideElement guestportChoice(String dutName, String text) {
        SelenideElement srcport = $x("//h5[text()='" + dutName + "']/../../..//div[contains(@id, \"divportEditVlan\")]//span[text()='" + text
                + "'][contains(@id, 'spnEtherBlckEditVlan')]");
        return srcport;
    }

    public SelenideElement publicUntagClick(String dutName) {
        SelenideElement button = $x("(//h5[text()='" + dutName + "'][contains(@id, 'hBoxShdwEditVlan')]/../../..//button)[2]");
        return button;
    }

    public SelenideElement publicTagClick(String dutName) {
        SelenideElement button = $x("(//h5[text()='" + dutName + "'][contains(@id, 'hBoxShdwEditVlan')]/../../..//button)[3]");
        return button;
    }

    public SelenideElement publicDeleteClick(String dutName) {
        SelenideElement button = $x("(//h5[text()='" + dutName + "'][contains(@id, 'hBoxShdwEditVlan')]/../../..//button)[5]");
        return button;
    }

    public SelenideElement employeeUntagClick(String dutName) {
        SelenideElement button = $x("(//h5[text()='" + dutName + "'][contains(@id, 'hBoxShwEmEditVlan')]/../../..//button)[2]");
        return button;
    }

    public SelenideElement employeeTagClick(String dutName) {
        SelenideElement button = $x("(//h5[text()='" + dutName + "'][contains(@id, 'hBoxShwEmEditVlan')]/../../..//button)[3]");
        return button;
    }

    public SelenideElement employeeDeleteClick(String dutName) {
        SelenideElement button = $x("(//h5[text()='" + dutName + "'][contains(@id, 'hBoxShwEmEditVlan')]/../../..//button)[5]");
        return button;
    }

    public SelenideElement guestUntagClick(String dutName) {
        SelenideElement button = $x("(//h5[text()='" + dutName + "'][contains(@id, 'hSnBShwEditVlan')]/../../..//button)[2]");
        return button;
    }

    public SelenideElement guestTagClick(String dutName) {
        SelenideElement button = $x("(//h5[text()='" + dutName + "'][contains(@id, 'hSnBShwEditVlan')]/../../..//button)[3]");
        return button;
    }

    public SelenideElement guestDeleteClick(String dutName) {
        SelenideElement button = $x("(//h5[text()='" + dutName + "'][contains(@id, 'hSnBShwEditVlan')]/../../..//button)[5]");
        return button;
    }
    // not used end

    public static SelenideElement reloadIcon  = $("[data-tooltip=\"" + WebportalParam.getLocText("Reload") + "\"]");
    public static SelenideElement yesReload   = $(Selectors.byText(WebportalParam.getLocText("Yes, reload")));
    public SelenideElement        applyButton = $("#btnSaveBtnPrtMirr");

    public String alertString = alert.getSearchCriteria();
    public SelenideElement vlanrow = $x("//*[@id=\"theadwiredVlan\"]/..");
    
    //AddedByPratik
    public SelenideElement  addVlanPlusSym      = $x("//span[@class='icon-add']");
    public SelenideElement  networkName         = $x("//input[@name='networkName']");
    public SelenideElement  vlanDesc            = $x("//input[@name='vlanDesc']");
    public SelenideElement  vlanName1           = $x("//input[@name='vlanName']");
    public SelenideElement  vlanId1             = $x("//input[@name='vlanId']");
    public SelenideElement  confirmBtn          = $x("//button[text()='Confirm']");
    public SelenideElement  successfulMessage   = $x("//p[contains(text(),'successfully completed')]");
    public SelenideElement  delVLANConfirmbtn   = $x("//button[text()='Yes, Continue']");
    public SelenideElement  wiredSettingText    = $x("//h3[text()='Settings']");
    public SelenideElement  portMembersText     = $x("//span[text()='Port Members']");
    public SelenideElement  portModeWarningMsg  = $x("//div[contains(text(),'The port modes are not selected. Please select the port modes')]");
    public SelenideElement  accessPortBtn       = $x("//button[text()='Access Port']");
  //AddedByPratik
    public SelenideElement editVLAN(String text) {
        SelenideElement vlan = $x("//td[text()='" + text + "']/..//p");
        return vlan;
    }
  //AddedByPratik 
    public SelenideElement deleteCustomVlan(String text) {
        SelenideElement vlan = $x("//td[text()='" + text + "']/../td[5]/p/i[2]");
        return vlan;
    }
    //AddedByPratik 
    public SelenideElement dataCustomVlan(String text) {
        SelenideElement vlan = $x("//td[text()='" + text + "']/../td[4]");
        return vlan;
    }
    //AddedByPratik 
    public SelenideElement editCustomVlan(String text) {
        SelenideElement vlan = $x("//td[text()='" + text + "']/../td[5]/p/i[1]");
        return vlan;
    }
    //AddedByPratik 
    public SelenideElement selectPortIfSw(String text, String num) {
        SelenideElement selectPort = $x("//span[text()='Port Members']/../..//h5[text()='"+ text +"']/../../../../../..//*[text()='"+ num +"']");
        return selectPort;
    }
}
