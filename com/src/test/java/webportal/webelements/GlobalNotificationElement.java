/**
 *
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import org.apache.log4j.Logger;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.weboperation.WiredPoESchedulesPage;
import webportal.weboperation.WiredVLANPage;

/**
 * @author zheli
 */
public class GlobalNotificationElement extends MyCommonAPIs {
    Logger logger = Logger.getLogger("GlobalNotificationElement");

    // locationBarIcons
    public static SelenideElement Networkconfigurationbutton = $x("//small[contains(text(),'Network Configuration')]");
    public static SelenideElement Quickviewexits             = $x("//*[@id=\"quickView\"]");
    public static SelenideElement BRQuickviewexits           = $x("//*[@id=\"brSettingsView\"]");
    public static SelenideElement Troubleshot                = $x("//a[contains(text(),'Troubleshoot')]");
    public static SelenideElement sharediagnosticsap         = $x("//*[@id=\"aOpenShareDiagApBtn\"]/span");
    public static SelenideElement deviceMode                 = $x("//*[@id=\"divCOnSecSumm\"]/div[2]/div/div[1]/div/div/div[1]/h5");

    public static SelenideElement sharediagnosticsapFourSwitch   = $x("//*[@id=\"aModalFourSwitchBtn\"]/span");
    public static SelenideElement editnetwork                    = $x("//*[@id=\"headerLocImg\"]");
    public static SelenideElement gotoNetworksetup               = $x("//*[@id=\"divSideBarSecEditVlan\"]/div/div[3]/a");
    public static SelenideElement enterVLAN                      = $x("//*[@id=\"tdtbdyFooterurl0\"]");
    public static SelenideElement enterVLANinsettings            = $x(
            "//*[text() ='Management VLAN' ] //following::img[contains(@src,'assets/img/commonIcons/edit.png' )]");
    public static SelenideElement VLANOK                         = $x("//*[@id=\"myModal\"]/div/div/div[3]/button[2]");
    public static SelenideElement NEXT                           = $x("(//button[text() = 'Next'])[8]");
    public static SelenideElement Wireless                       = $x("//a[contains(text(), 'Wireless')]");
    public static SelenideElement Wiredpage                      = $("li:nth-child(5) > a.hideWhenVertical");
    public static SelenideElement Wiredpage1                     = $("li:nth-child(6) > a.hideWhenVertical");
    public static SelenideElement enterdevicespage               = $x("//a[contains(@href, '/#/devices/dash' ) and //span[text() = 'Devices']]");
    public static SelenideElement Settings                       = $x("(//a[contains(text(),'Settings')])[13]");
    public static SelenideElement WiredSettings                  = $x("//*[@id=\"settingsView\"]");
    public static SelenideElement InstantWiFi                    = $x("//*[@id=\"divSideBarSecEditVlan\"]/div/div[5]/a");
    public static SelenideElement DeviceconfigurationinstantWiFi = $x(
            "//*[contains(@class,'IconCallRightMarg colorPurple fontSemiBold fontSize14') and text()='Device Configuration']");
    public static SelenideElement PortMember                     = $x("//*[@id=\"iconPlusEditVlan\"]");
    public static SelenideElement GroupPortSettings              = $x("//a[contains(text(), 'Group Port Configuration')]");
    public static SelenideElement SpanningTree                   = $x("//a[@class  = 'anchor'][@href='#/wired/spanningTree']");
    public static SelenideElement EnableSpanningTree             = $x("//span[@class ='cstmSlider cstmRound']");
    public static SelenideElement POESchedules                   = $x("//*[@id=\"divSideBarSecEditVlan\"]/div/div[6]/a");
    public static SelenideElement AddScheduler                   = $x("//*[@id=\"ancAddPoppupwiredPoeSch\"]/span");
    public static SelenideElement SchedulerName                  = $x("//input[@id = 'scheduleName']");
    public static SelenideElement EnableAllDay                   = $x("//*[@id=\"spnOnSliderWiredPoeSch\"]");
    public static SelenideElement clickStartdate                 = $x("//input[@id ='startDate']");    
    public static SelenideElement selectstartdate                = $x("//a[contains(text(), '27')]");
    public static SelenideElement clickenddate                   = $x("//input[@id ='endDate']");
    public static SelenideElement selectenddate                  = $x("//a[contains(text(), '28')]");
    public static SelenideElement SaveselectPort                 = $x("//button[contains(text(), 'Save and')]");
    public static SelenideElement Radiusconf                     = $x("//a[@class = 'anchor'][@href = '#/wired/radiusConfiguration']");
    public static SelenideElement TestVPNServer                  = $x("//span[@class= 'testVpnServerIcom' ]");
    public static SelenideElement RecurrenceType                 = $x("//select[@id='recurrenceType']");
    public static SelenideElement RecurrenceValue                = $x("//option[@value='1']");
    public static SelenideElement DeleteSwitch                    = $x("//*[@id=\"aModalOneSwitchBtn\"]/span");
    public static SelenideElement YesDeleteButton                 = $x("//button[@class = 'btn btn-danger'][text() = 'Yes, Continue']");
    public static SelenideElement Rebootswitch                    = $x("//*[@id=\"aModalTwoSwitchBtn\"]/span");
    public static SelenideElement YesRebootButton                 = $x("//button[@class = 'btn btn-danger'][text() = 'Yes, reboot']");
    public static SelenideElement ShareDiognastic                    = $x("//*[@id=\"aModalFourSwitchBtn\"]/span");
    public SelenideElement shareemail      = $("#share_email_id");
    public SelenideElement send            = $x("//button[text()='Send']");
    public static SelenideElement Rebootorbi                    = $x("//*[@id=\"aModalTwoApBtn\"]/span");
    public static SelenideElement RebootBR                    = $x("//*[@id=\"aModalTwoSwitchBtn\"]/span");
    public static SelenideElement ContinueRebootButton                 = $x("//button[@class = 'btn btn-danger'][text() = 'Continue']");

    public static SelenideElement ShareDiognasticOrbi                    = $x("//*[@id=\"aOpenShareDiagApBtn\"]/span");
    public static SelenideElement ShareDiognasticBR         =$x("//a[@id=\"aModalFourSwitchBtn\"]/span[1]");
    public SelenideElement alerttext       = $x("//div[@class='alert alert-success alert-dismissable']");
  
    public SelenideElement downtimerange = null;
    
    public SelenideElement devicenameoninstantWifiPage(String name) {
        return $("null");
    }

    public static WiredVLANPage         wvp  = new WiredVLANPage();
    public static WiredPoESchedulesPage wpsp = new WiredPoESchedulesPage(true);

    public SelenideElement deviceconfigurationinvlan(String text) {
        SelenideElement deviceconfiguration = $x("//*[text()='" + text + "']//following::small[contains(text(),'Device Configuration')]");
        return deviceconfiguration;
    }
   

    public SelenideElement deviceinfonetworkloc(String text) {
        SelenideElement deviceinfonetwork = $x("//h5[contains(text(),'" + text + "')]");
        return deviceinfonetwork;
    }

    public SelenideElement clickdevice(String text) {
        SelenideElement clickondevice = $x("//td[contains(text(), '" + text + "')]");
        return clickondevice;
    }

    public SelenideElement TroubleshotDevice(String text) {
        
        SelenideElement TroubleshotDeviceConfigurationAP = $x("//p[contains(text(),'" + text + "')]/a");
        return TroubleshotDeviceConfigurationAP;
    }

    public SelenideElement devicedashboard(String text) {

        SelenideElement deviceinfo = $x("//h3[contains(text(),'" + text + "')]");
        return deviceinfo;
    }
    
    public SelenideElement editName(String text) {
        SelenideElement nameelement = $x("//td[text()='" + text + "']/../td[1]//span");
        return nameelement;
    }
    public SelenideElement inputName(String text) {
        SelenideElement inputname = $x("(//td[text()='" + text + "']/../td[1]//input)[2]");
        return inputname;
    }
    public SelenideElement inputnameyes(String text) {
        SelenideElement yes = $x("(//td[text()='" + text + "']/../td[1]//button)[1]/i");
        return yes;
    }
    
    public SelenideElement deleteDevice(String text) {
        SelenideElement Device = $x("(//img[@data-deviceserial='" + text + "']/..)[1]");
        return Device;
    }
    public SelenideElement editModule(String text) {
        SelenideElement Device = $x("//img[@data-deviceserial='" + text + "']/ancestor::p");
        return Device;
    }
    
   

    
    public SelenideElement Dropdown           = $x("//*[@id=\"divColMdAptn\"]/div[1]/span");
    public SelenideElement Dropdownforswitch           = $x("//*[@id=\"divColMdSwitchBtn\"]/div[1]/span");
    public SelenideElement reset             = $("#divColMdAptn > div.moreIcon.HeaderAdminBlock.open > ul > li:nth-child(1)");
    public SelenideElement rebootconfirm      = $x("//button[text()='Continue']");
    public SelenideElement Delete             = $("#divColMdAptn > div.moreIcon.HeaderAdminBlock.open > ul > li:nth-child(3)");
    public SelenideElement reboot             = $("#divColMdAptn > div.moreIcon.HeaderAdminBlock.open > ul > li:nth-child(2)");
    public SelenideElement rebootswitch             = $("#divColMdSwitchBtn > div.moreIcon.HeaderAdminBlock.open > ul > li:nth-child(2) > a");
    public SelenideElement deleteswitch             = $("#divColMdSwitchBtn > div.moreIcon.HeaderAdminBlock.open > ul > li:nth-child(3) > a");
    public static SelenideElement addVLANWied                      = $x("//*[@id=\"ancOpenwiredVlan\"]");
    public static SelenideElement continueWired                     = $x("//*[@id=\"divMainVLan\"]/div[4]/div/div/div[2]/div/button");
    public SelenideElement Next1     = $("button.saveBtn");
    
    
    public SelenideElement EnterNetworkName     = $x("//*[@id=\"networkConfigForm\"]/div[2]/input");
    public SelenideElement EnterVlanName        = $x("//*[@id=\"networkConfigForm\"]/div[5]/input");
    public SelenideElement EnterVlanID          = $x("//*[@id=\"networkConfigForm\"]/div[6]/input");
 
    
    public SelenideElement selectdate1(String Date) {
        SelenideElement selectdate = $x("//*[text()='" + Date + "'][contains(@aria-current, 'false')]");
        return selectdate;
    }
    
    
}

