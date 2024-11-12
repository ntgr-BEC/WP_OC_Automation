package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class OrganizationElement extends MyCommonAPIs {
    Logger                 logger         = Logger.getLogger("OrganizationElement");
    public SelenideElement homeLogin      = $(Selectors.byId("ancNinHm"));
    public String          sloginEmail    = "loginEmail";
    public SelenideElement ProloginEmail  = $(Selectors.byName(sloginEmail));
    public SelenideElement ProloginPwd    = $(Selectors.byId("passwordField"));
    public SelenideElement ProloginButton = $(".loginBtnSection");

    public SelenideElement        ProloginEmailNew  = $x("//*[@id=\"input_0\"]");
    public SelenideElement        ProloginPwdNew    = $x("//*[@id=\"input_1\"]");
    public SelenideElement        ProloginButtonNew = $("form[name*=loginForm] button");
    public SelenideElement        SettingsForOrg    = $x("//*[@id=\"settingsView\"");
    public String                 OrgPagebody       = "#content";
    public SelenideElement        AddOrg            = $x("//*[@id=\"_divAddOrgIcon\"]/span");
    public SelenideElement        SaveOrg           = $x("//*[@id=\"saveOrgBtn\"]");
    public SelenideElement        devAdminPwd       = $("#editAdminPass");
    public SelenideElement        autoZipCode       = $x("//input[@name='postCode']");
    public static SelenideElement nextButton        = $x("(//button[text()='" + WebportalParam.getLocText("Next") + "'])[10]");
    public SelenideElement        maxValueAlert     = $x("//div[contains(@class,'alert-danger')]");

    public static ElementsCollection dialogbox1                = $$("#modal-content");
    public SelenideElement           NameOrg                   = $x("//*[@id=\"orgName\"]");
    public SelenideElement           ownerName                 = $("#ownerName");
    public SelenideElement           ownerEmail                = $("#ownerEmail");
    public SelenideElement           ownerPhone                = $("#personalPhn");
    public SelenideElement           businessPhone             = $("#businessPhn");
    public SelenideElement           existingUser              = $("#existingUser");
    public static String             emailnotificationsadmin   = "//h5[text()='" + WebportalParam.getLocText("Email Notifications")
            + "']/..//input[@id='emailMSP']";
    public static String             emailnotificationsowner   = "//h5[text()='" + WebportalParam.getLocText("Email Notifications")
            + "']/..//input[@id='emailOwner']";
    public static String             emailnotificationsmanager = "//h5[text()='" + WebportalParam.getLocText("Email Notifications")
            + "']/..//input[@id='emailManager']";
    public static String             mobilenotificationadmin   = "//h5[text()='" + WebportalParam.getLocText("Mobile App Push Notifications")
            + "']/..//input[@id='pushMSP']";
    public static String             mobilenotificationowner   = "//h5[text()='" + WebportalParam.getLocText("Mobile App Push Notifications")
            + "']/..//input[@id='pushOwner']";
    public static String             mobilenotificationmanager = "//h5[text()='" + WebportalParam.getLocText("Mobile App Push Notifications")
            + "']/..//input[@id='pushManager']";
    public static String             emailreportadmin          = "//h5[text()='" + WebportalParam.getLocText("Email Reports")
            + "']/..//input[@id='emailReportMSP']";
    public static String             emailreportowner          = "//h5[text()='" + WebportalParam.getLocText("Email Reports")
            + "']/..//input[@id='emailReportOwner']";
    public static String             emailreportmanager        = "//h5[text()='" + WebportalParam.getLocText("Email Reports")
            + "']/..//input[@id='emailReportManager']";
    public static SelenideElement    deviceownershipadmin      = $x(
            "//h5[text()='" + WebportalParam.getLocText("Device Ownership") + "']/..//input[@id='enaMSPOwner']");
    public static SelenideElement    deviceownershipowner      = $x(
            "//h5[text()='" + WebportalParam.getLocText("Device Ownership") + "']/..//input[@id='enabBusiOwner']");
    public SelenideElement           scheduledreport           = $("#spnScheduledReportChckbox");
    public static SelenideElement    scheduleweekly            = $x(
            "//h5[text()='" + WebportalParam.getLocText("Scheduled Reports") + "']/..//input[@id='schRadioWeekly']");
    public static SelenideElement    schedulemonthly           = $x(
            "//h5[text()='" + WebportalParam.getLocText("Scheduled Reports") + "']/..//input[@id='schRadioMonthly']");

    public SelenideElement deletedialogbutton = $x("//button[text()='Delete']");

    public SelenideElement organizationOwnerInfo = $x("(//div[@class='EditBlock']//h3)[1]");
    public SelenideElement allocateDeviceCredits = $x("(//input[contains(@class,'cmnSwitch ')])[1]");
    public SelenideElement vpnCredits            = $x("(//input[contains(@class,'cmnSwitch ')])[2]");
    public SelenideElement icpCredits            = $x("(//input[contains(@class,'cmnSwitch ')])[3]");
    public SelenideElement allocate              = $x("//*[@id=\"locationDivsContainer\"]/div[5]/div/div/div[3]/button[2]");
    public SelenideElement allocate1              = $x("//button[contains(text(), 'Allocate')]");

    public SelenideElement dropdownOrganizationElement(String name) {
        SelenideElement dropdownelement = $x("//p[text()='" + name + "']/../..//a[contains(@class,'locationDivMoreOption')]");
        return dropdownelement;
    }

    public SelenideElement editOrganizationElement(String name) {
        SelenideElement deleteelement = $x("//p[text()='" + name + "']/../..//b[text()='" + WebportalParam.getLocText("Edit organization") + "']");
        return deleteelement;
    }

    public SelenideElement deleteOrganizationElement(String name) {
        SelenideElement deleteelement = $x(
                "//p[text()='" + name + "']/../..//b[text()='" + WebportalParam.getLocText("Delete organization") + "']");
        return deleteelement;
    }

    public SelenideElement addCreditsOrganizationElement(String name) {
        SelenideElement deleteelement = $x("//p[text()='" + name + "']/../..//b[text()='" + WebportalParam.getLocText("Allocate Credits") + "']");
        return deleteelement;
    }

    public SelenideElement organizationElement(String name) {
        
    
//        SelenideElement organization = $x("//div[@id='successMessage']/..//div[@class='EditBlock']//h3[text()='"+name+" "+"']");
      //div[@id='successMessage']/..//div[@class='EditBlock']//h3[text()='test16985 ']
//                $x("//div[@id='gridView']/div[@class='locationDiv']//p[text()='" + name + "']");
//                                         $x ("//div[@id='successMessage']/..//div[@class='EditBlock']//h3[text()='"+name+ "']");
        SelenideElement organization = $x("(//*[text()='"+ name +"'])[1]");
        return organization;
    }

    public Map<String, String> organizationOwnerInfo() {
        ElementsCollection organizationownerinfo = $$x("//div[@class='EditBlock']//h3");
        Map<String, String> organizationInfo = new HashMap<String, String>();
        String[] key = {
                "Name", "Address", "Phone", "Business Phone"
        };
        int i = 0;
        for (SelenideElement se : organizationownerinfo) {
            organizationInfo.put(key[i], se.getText());
            i += 1;
        }
        return organizationInfo;
    }
    
    public SelenideElement OrgSettings               = $x("//*[@id=\"settingsView\"]");
    public SelenideElement DeviceReboot              = $x("//*[@id=\"divSideBarSecEditVlan\"]//a[text()='Device Reboot']");
    public SelenideElement ViewAllScheduleRebot      = $x("//*[@id=\"content\"]/div[3]/div/div[2]/div/div[1]/div[1]/div[1]/p");
    public SelenideElement RebootNow                 = $x("//*[@id=\"content\"]/div[3]/div/div[2]/div/div[1]/div[1]/div[2]/div[1]/label/input");
    public SelenideElement RebootSchedule            = $x("//*[@id=\"content\"]/div[3]/div/div[2]/div/div[1]/div[1]/div[2]/div[2]/label/input");
    public SelenideElement ExpandDevices             = $x("//*[@id=\"content\"]/div[3]/div/div[2]/div/div[1]/div[2]/div[2]/div/div/h3/span[2]/i[2]");
//    public SelenideElement ExpandDevicesSchedule     = $x("//span[text()='office1']/../span//i[contains(@class,'collapse')]");
    
    public SelenideElement ExpandDevicesSchedule(String location) {
        SelenideElement ExpandDevicesSchedule = $x("//span[text()='" + location +"']/../span//i[contains(@class,'collapse')]");
        return ExpandDevicesSchedule;
    }
    
    public SelenideElement RebootNowconform          = $x("//button[text() ='Reboot Now']");
    public SelenideElement increaseHr                = $x("//div[text()='Hour']/..//*[@class='fa fa-angle-up']");
    public SelenideElement Continue                  = $x("//p[text() = 'You are about to reboot device(s). It will not be available until the process is complete.']");
    public SelenideElement closePopUp                = $x("//*[@id=\"rebootModal\"]/div/div/div[1]/button/img");
    public SelenideElement Bluebanner                = $x("//*[@id=\"pWarnIconsubscriptions\"]");
    public SelenideElement Bluebannerlink            = $x("//*[@id=\"pWarnIconsubscriptions\"]/span");
    public SelenideElement DevicesTab                = $x("//th[contains(text(), 'Reboot Status')]");
    public SelenideElement ContinueButton            = $x("//*[@id=\"rebootModal\"]/div/div/div[3]/button[2]");
    public SelenideElement ChecktheStatus            = $x("//*[@id=\"DataTables_Table_1\"]/tbody/tr/td[6]/div/p/text()");
    public SelenideElement goDeviceTab               = $x("//*[@id=\"divSideBarSecEditVlan\"]/div/div[2]/a");
    public SelenideElement rebootSchedule            = $x("//*[@id=\"content\"]/div[3]/div/div[2]/div/div[1]/div[1]/div[2]/div[2]/label");
    public SelenideElement rebootSchedulecheck       = $x("//*[@id=\"content\"]/div[3]/div/div[2]/div/div[1]/div[1]/div[2]/div[2]/label/input");
    public SelenideElement rebootScheduleText        = $x("//*[@id=\"content\"]/div[3]/div/div[2]/div/div[1]/div[2]/div/div[1]/p");
    public SelenideElement StatDate                  = $x("//*[@id=\"content\"]/div[3]/div/div[2]/div/div[1]/div[2]/div/div[3]/div/div[1]/div/div[2]/div[1]/div/input");
    public SelenideElement StartTime                 = $x("(//*[@id=\"startTime\"]/../p)[1]");
    public SelenideElement EndDate                   = $x("//*[@id=\"content\"]/div[3]/div/div[2]/div/div[1]/div[2]/div/div[3]/div/div[3]/div/div[2]/div[1]/div/input");
    public SelenideElement EndTime                   = $x("(//*[@id=\"startTime\"]/../p)[1]");
    public SelenideElement SelectDevices             = $x("//*[@id=\"content\"]/div[3]/div/div[2]/div/div[1]/div[2]/div/div[4]/div/div/select");
    
    public SelenideElement SelectDate                = $x("//*[text() = '30']");
    
    public SelenideElement SelectTime                = $x("(//button[text() =  'OK'])[3]");
    public SelenideElement deletessidyes             = $x("//button[text()=\"Delete\"]");
    public SelenideElement Repeters                  = $x("(//select[@class='form-control inputTextField'])[2]");
    public SelenideElement ScheduleName              = $x("//*[@id=\"content\"]/div[3]/div/div[2]/div/div[1]/div[2]/div/div[2]/div/div/input");
    public SelenideElement RebootSchedulebutton      = $x("//button[text()= 'Reboot Schedule']");
    public SelenideElement Greenbanner                = $x("//*[@id=\"pWarnIconsubscriptions\"]");
    public SelenideElement ViewAllScheduler           = $x("//p[text()=\"View All Schedules\"]");
    public SelenideElement gotoDashboard             = $x("//*[@id=\"gotodashboard\"]");
    public SelenideElement assignLocation            = $x("//*[@id=\"gridView\"]/div/ul/li[1]/div/label/i");
    public SelenideElement assigntoOrganization      = $x("//*[@id=\"content\"]/div[8]/div/button");
    public SelenideElement addnewOrganization        = $x("//*[@id=\"addOrgForm\"]/div[4]/input");
    public SelenideElement submitBtn                 = $x("//*[@id=\"addLocToOrg\"]/div[2]/div/div/div[4]/button");
    public SelenideElement clickOnOrganization       = $x("//*[@id=\"_divorgDiv0\"]/div/span/div");
    public SelenideElement locationisVisible         = $x("//p[@title='office1']");
    public SelenideElement humbergerMenuPageforPro   = $x("//*[@id=\"header\"]/div[2]/div/ul/li/div/div/div[2]/p[2]");
    public SelenideElement accountManagementpro      = $x("//*[@id=\"header\"]/div[2]/div/ul/li/ul/li[1]/a");
    public SelenideElement clickonSubscriptionMaximizebtn1   = $x("//*[@id=\"content\"]/div[4]/div/div[2]/div/div[2]/div/div[1]/h3/span[2]/i[2]");
    public SelenideElement clickonSubscriptionMaximizebtn2   = $x("//*[@id=\"content\"]/div[4]/div/div[2]/div/div[2]/div/div[2]/h3/span[2]/i[2]");
    public SelenideElement insightPemiumSubscription         = $x("//span[text()='Insight Premium Subscription']");
    public SelenideElement cancelledSubscription             = $x("//p[text()='Cancelled']");
    public SelenideElement icpSubscriptionCheck              = $x("//span[text()='Captive Portal, Pack of 1, 1-yr-AUD']");
    public SelenideElement icpSubActivateDate                = $x("(//p[text()='Aug 23, 2022'])[2]");
    
    public SelenideElement selcetDate(String Date) {
        SelenideElement selectdate = $x("//*[text()='" + Date + "'][contains(@class, 'react-datepicker__day--today')]");
        return selectdate;
    }
    
    
    public SelenideElement OrgName(String name) {
        SelenideElement OrgName = $x("//label[text()='" + name + "']");
        return OrgName;
    }
    public SelenideElement LocName(String name) {
        SelenideElement LocName = $x("//*[text()='" + name + "']");
        return LocName;
    }
    public SelenideElement DeviceName(String name) {
        SelenideElement DeviceName = $x("//div[text()='" + name + "']");
        return DeviceName;
    }
    
    public SelenideElement SelctDevice(String name) {
        SelenideElement SelctDevice = $x("//div[text() = '" + name + "']//..//../span/label/i");
        return SelctDevice;
    }

    public SelenideElement SelctDevice1(String name) {
        SelenideElement SelctDevice1 = $x("//div[text() = '" + name + "']//..//../span/label/input");
        return SelctDevice1;
    }
    
    
//    public SelenideElement Status(String name) {
//        SelenideElement Status = $x("//td[text() = '" + name + "']//../td[7]/div/p");
//        return Status;
//    }
    
    public SelenideElement Status(String name) {
        SelenideElement Status = $x("//td[text() = '" + name + "']//../td[8]/div/p");
        return Status;
    }
    
    public SelenideElement editScheduler(String name) {
        SelenideElement Ssid = $x("//td[text() ='"+ name +"']/../td[10]/p");
        return Ssid;
    }
    
    public static SelenideElement      ownerSelect        = $x("//*[@id=\"existingUser\"]");
    public static SelenideElement      selectAdminOwner   = $x("(//*[@id=\"existingUser\"]/option)[2]");
    public static SelenideElement      okaybtn            = $x("//*[@id=\"myModal\"]/div/div/div[3]/button[2]");
    
    public SelenideElement deleteScheduler(String name) {
        SelenideElement Ssid = $x("//td[text() ='"+ name +"']/../td[10]/p/span/i[2]");
        return Ssid;
    }
    
    public SelenideElement editSchIcon(String name) {
        SelenideElement Ssid = $x("//td[text() ='"+ name +"']/../td[10]/p/span/i[1]");
        return Ssid;
    }
    
   
    
    public SelenideElement allocateDeviceCreditsKeys  = $x("//*[@id=\"deviceCredits\"]/div/div");
    public SelenideElement checktheKey                = $x("//*[@id=\"deviceCredits\"]/ul/li/div[2]/div[1]/ul[1]/li/div[1]/label/p");
    public SelenideElement SelectKey                  = $x("//*[@id=\"deviceCredits\"]/ul/li/div[2]/div[1]/ul[1]/li/div[1]/label/i");
    public SelenideElement closebox                   = $x("//*[@id=\"locationDivsContainer\"]/div[5]/div/div/div[1]/button/img");
    public SelenideElement allocateCredits            = $x("//b[text()='Allocate Credits']");
    public SelenideElement deviceCredits              = $x("(//span[text()='Device Credits'])[2]");
    public SelenideElement deviceCreditsTextbox       = $x("(//input[@type=\"text\"])[9]");
    public SelenideElement allocateBtn                = $x("//button[text()='Allocate']");
    public SelenideElement Homebtn                    = $x("(//img[@alt='Cloud Web Portal'])[1]");
    public SelenideElement Setting                    = $x("//a[text()='Settings']");
    public SelenideElement organizationWideSSID       = $x("(//a[text()='Organization-wide SSID'])[1]");
    public SelenideElement organizationWideSSIDradiobtn = $x("(//span[@class=\"cstmSlider cstmRound\"])[3]");
    public SelenideElement addssidsign                = $x("//a[@id=\"ancssidModWirSett\"]");
    public SelenideElement orgwidessidName            = $x("//input[@id='ssid']");
    public SelenideElement band6GHz                   = $x("//p[text()='6 GHz']");
    public SelenideElement security                   = $x("//*[@id=\"security\"]");
    public SelenideElement securityWPA2Per            = $x("//option[text()='WPA2 Personal']");
    public SelenideElement orgwideSSIDPass            = $x("//input[@id=\"password\"]");
    public SelenideElement orgwideSSIDSaveBtn         = $x("(//button[text()='Save'])[3]");
    public SelenideElement orgwideSSIDOKBtn           = $x("(//button[text()='OK'])[3]");
    public SelenideElement ssidCreated                = $x("//td[text()='Enable']");
    public SelenideElement viwDevices                 = $x("//button[text()='View Devices']");
    public SelenideElement clkonOrganization          = $x("(//span[text()='Netgear'])[1]");
    public SelenideElement clkonOrganization2         = $x("(//span[text()='All Organizations'])[1]");
    public SelenideElement clkonOrganization1         = $x("(//span[text()='Netgear'])[2]");
    public SelenideElement office1                    = $x("//span[text()='office1']");
    public SelenideElement office2                    = $x("//span[text()='office2']");
    public SelenideElement wirelesstab                = $x("(//span[text()='Wireless'])[3]");
    public SelenideElement proAccWirelesstab1         = $x("//a[text()=\"Wireless\"]");
    public SelenideElement settingtab                 = $x("(//a[text()='Settings'])[3]");
    public SelenideElement locationwiseSSID1          = $x("//p[text()='apwp14270']");
    public SelenideElement editLocOrgWideSSID         = $x("(//img[contains(@src,'edit.png')])[4]");
    public SelenideElement deleteLocOrgWideSSID       = $x("//img[contains(@src,'del.png')]");
    public SelenideElement editorgwideSSIDSaveBtn     = $x("(//button[text()='Save'])[2]");
    public SelenideElement opensecurity               = $x("//td[text()='Open']");
    public SelenideElement orgWideSSIDedit            = $x("//td[text()='Enable']");
    public SelenideElement orgWideSSIDDelete          = $x("(//img[contains(@src,'del.png')])[2]");
    public SelenideElement messageOrgWideSSID         = $x("//p[@class='IconColVpn']");
    public SelenideElement messagelocOrgWideSSID      = $x("//td[text()='No Data Available']");
    public SelenideElement confirmdeletebtn           = $x("//button[text()='Delete']");
    public SelenideElement orgenterlimit              = $x("(//a[text()='Rate Limiting'])[1]");
    public SelenideElement orgenableratelimit         = $x("//span[@id=\"spnEnableSttRateLimit\"]");
    public SelenideElement orguploadrate              = $x("//select[@id=\"uploadUnit\"]");
    public SelenideElement  orgmoveSlider             = $x("//div[@id=\"divToolTipInnerSliderRateLimit\"]");
    public SelenideElement  uploadrateright           = $("#spnPullRghtrateLimit");
    public SelenideElement  downloadrateleft          = $("#spnLftrateLimit");
    public SelenideElement  downloadrateright         = $("#spnRghtrateLimit");
    public SelenideElement  orgdownloadrate           =$x("//select[@id=\"downloadUnit\"]");
    public SelenideElement  orgupmoveSlider           =$x("//div[@id=\"divToolTipInnerDownloadSliderRateLimit\"]");
    public SelenideElement  orgsaveratelimit          =$x("//button[@id=\"btnSaverateLimit\"]");
    public SelenideElement  orgratelimitok            =$x("(//*[@id=\"successModalrateLimit\"])[2]");
    public SelenideElement enterAdvanceRateSelection  =$x("(//a[text()='Advanced Rate Selection'])[1]");
    public SelenideElement  twopointfourghztab         =$x("//li[text()='2.4 GHz']");
    public SelenideElement orgenableSetMinimumRateControls =$x("//p[text()='Set Minimum Rate Controls']");
    public SelenideElement orgslider                     = $x("//div[@class=\"input-range__slider\"]");
    public SelenideElement orgdensityone                 = $x("//span[@class=\"topDeviderLine\"]");
    public SelenideElement orgsave                       = $x("//button[@id=\"saveArs\"]");
    public SelenideElement orgok                         = $x("(//button[text()='OK'])[3]");
    public SelenideElement clkonOrganizationSSID         = $x("//td[@id=\"td6WirStee0\"]");
    public SelenideElement clkon5GHz                     = $x("//li[text()='5 GHz']");
    public SelenideElement select4GHz                    = $x("//span[@class='topDeviderLine lastChildDevider']");
    public SelenideElement apusername                    = $("#userName");
    public SelenideElement appassword                    = $("#userPwd");
    public SelenideElement aploginbtn                    = $x("//button[text()='Login']");
    public SelenideElement apmanagementtab               = $x("//a[text()='Management']");
    public SelenideElement apokbtn                       = $x("//button[text()='OK']");
    public SelenideElement apconfigbtn                   = $x("//span[text()='Configuration']");
    public SelenideElement apwirlesstab                  = $("#listgroup_wireless");
    public SelenideElement apbasictab                    = $x("(//a[text()='Basic'])[2]");
    public SelenideElement apssid1                       = $x("//span[text()='SSID1']");
    public SelenideElement aporgwidessid                 = $x("//div[text()='apwp14270']");
    public SelenideElement apadvancerateclk              = $x("//span[text()='Advanced Rate Selection']");
    public SelenideElement ap2Ghz1GHzisselected          = $x("(//span[text()=\"1\"])[1]");
    public SelenideElement ap2Ghz4GHzisselected          = $x("(//span[text()=\"4\"])[1]");
    public SelenideElement ap5Ghz4GHzisselected          = $x("(//span[text()=\"4\"])[4]");
    public SelenideElement cpativeportalTab              = $x("//a[text()='Captive Portal' and @class='anchoractiveLeftMenu']");
    public SelenideElement captivePortalText             = $x("//h5[text()='Enable Captive Portal']");
    public SelenideElement enableCaptivePortal           = $x("#spnEnableMsgCaptivePortal");
    public SelenideElement basicCaptivePortalOption      = $x("//div//label//p[text()='Basic Captive Portal']");
    public SelenideElement editsaveOrgSSIDBtn            = $x("//button[text()='Save' and @class='btn saveBtn']");
    public SelenideElement editokayOrgSSIDBtn            = $x("(//button[text()='OK'])[5]");
    public SelenideElement ssidCaptiveportalRtext        = $x("//th[text()='Captive Portal']");
    public SelenideElement onText                        = $x("//td[text()='On']");
    public SelenideElement macACL                        = $x("//a[text()='MAC ACL' and @class='anchorundefined']");
    public SelenideElement manualclientaddBtn            = $x("//button[text()='Manual']");
    public SelenideElement clientName                    = $x("//input[@data-type='deviceName' and @id = 'deviceName1']");
    public SelenideElement clientMAC                     = $x("//input[@data-type='mac' and @id = 'macaddress1']");
    public SelenideElement addclientBtn                  = $x("//button[text()='Add']");
    public SelenideElement enablemacSw                   = $x("//span[@class='cstmSlider cstmRound' and @id='spnEnableMacAcl']");
    public SelenideElement radiusType                    = $x("//select[@id='radius']");
    public SelenideElement policy                        = $x("//select[@id='policy']");
    public SelenideElement orgwidessidCaptivePortal      = $x("(//a[text()='Captive Portal'])[1]");
    public SelenideElement enablecaptiveportalText       = $x("//h5[text()='Enable Captive Portal']");
    public SelenideElement onoffcaptiveSwitch            = $x("//span[@id='spnEnableMsgCaptivePortal']");
    public SelenideElement basiccaptiveButton            = $x("//input[@id='cpRadio']");
    public SelenideElement basiccaptiveText              = $x("(//p[text()='Basic Captive Portal'])[1]");
    public SelenideElement saveButton                    = $x("//button[@id='btnSavCapPort']");
    public SelenideElement okaysuccessbtn                = $x("//button[@id='SuccessCapPort']");
    public SelenideElement errorMsg1                     = $x("//li[text()='SSID maximum number exceeded.']"); //("(//li/text())[3]"); //("((//div[@class='modal-body'])[13]/div/div/ul/li/text())[1]");
    public SelenideElement errorMsg2                     = $x("((//div[@class='modal-body'])[13]/div/div/ul/li/text())[2]");
    public SelenideElement cancelBtn                     = $x("(//button[text()='Cancel'])[6]");
    public SelenideElement cancelBtnOrgWideSSID          = $x("(//button[text()='Cancel'])[5]");
    public SelenideElement orgSSIDSwitch                 = $x("(//span[@class='cstmSlider cstmRound'])[4]");
    public SelenideElement enabledOrgSSIDText            = $x("//p[@class='IconColVpn']");
    public SelenideElement loc1SSIDErrorMsg              = $x("//span[text()='office1']");
    public SelenideElement orgSSIDSaveandConfigBtn       = $x("//button[text()='Save and Configure']");
    public SelenideElement captiveportalOpt              = $x("(//a[text()='Captive Portal'])[1]");
    public SelenideElement orgSSIDCaptiveSaveandConfigSuccessMsg = $x("//p[starts-with(text(),'You successfully created a captive')]");
    public SelenideElement homePage                      = $x("(//img[@alt='Cloud Web Portal'])[1]");
    public SelenideElement icpCreditsInput               = $x("(//input[@type='text'])[11]");
    public SelenideElement a2              = $x("");
    public SelenideElement a3              = $x("");

    public SelenideElement orgpageAddDevices             = $x("//span[@class='icon-bulkonboard-add dropdown-toggle']");
    public SelenideElement addSingleDevice               = $x("//b[text()='Add Single Device']");

    
    public SelenideElement editOrgWideSSID(String text) {
        SelenideElement Ssid = $x("//p[@title='" + text + "']/../../../td[9]//i//img[contains(@src,'edit.png')]");
        return Ssid;
    }
    
    public SelenideElement        enableClientisolation                   = $x("//*[@id=\"spnClientIsolationeditWirNet\"]");
    public SelenideElement        clickAddIP                              = $x("//*[@id=\"divClearFixeditWirNet\"]/div[3]/div[2]/div/div[2]/div[1]/div/div[1]/label/i");
    public SelenideElement        DomainName                              = $x("//input[@placeholder = 'Enter here']");
    public SelenideElement        AddDomainName                           = $x("//button[text()= 'Add']");

    public SelenideElement        settingtab1                             = $x("(//a[text()='Settings'])[7]");
    public SelenideElement        checkband6                              = $x("//*[@id=\"band_6\"]");
    public SelenideElement        band6                                   = $x("//input[@type=\"checkbox\"][@id=\"band_6\"]//../i");
    
    
    
    // added by tejeshwini
    
    public SelenideElement        enableOrgSsid                             = $x("//*[text() ='Enable']/../label");
    public SelenideElement        addssid                                   = $("#divssidWirSett");
    public SelenideElement        ssid                                      = $("#ssid");
    public SelenideElement        checkband24                               = $x("//*[@id=\"band_2\"]");
    public SelenideElement        checkband5                                = $x("//*[@id=\"band_5\"]");
    public SelenideElement        checkband61                                = $x("//*[@id=\"band_6\"]");
    public SelenideElement        band24                 = $x("//input[@type=\"checkbox\"][@id=\"band_2\"]//../i");
    public SelenideElement        band5                  = $x("//input[@type=\"checkbox\"][@id=\"band_5\"]//../i");
    public SelenideElement        band61                  = $x("//input[@type=\"checkbox\"][@id=\"band_6\"]//../i");
    public SelenideElement        ALLband                = $x("//input[@type=\"checkbox\"][@id=\"all_band\"]//../i");
    public SelenideElement        password                = $("#password");
    public SelenideElement        save                                     = $x("(//button[text()='Save'])[3]");
    public SelenideElement        Maximumlimit          = $x("//div[text() = 'SSID maximum number exceeded.']");
    public SelenideElement        close                 = $x("//*[@id=\"divMainWirSett\"]/div[3]/div/div/div/div[1]/button/img");
    public SelenideElement        closeCG               = $x("//*[@id=\"divMainWirSett\"]/div[2]/div[1]/div/div/div[1]/button/img");
    public SelenideElement        settingsorquickview       = $x("//*[@id=\"divLocBarwirquickView\"]/div[2]/a");
    public SelenideElement        rateLimittabSSID      = $x("//th[text()='Rate Limiting']");
    public SelenideElement        rateLimitOnStatus     = $x("//td[text()='On']");
    public SelenideElement        icpCreditsOrg     = $x("//td//span[text()='Instant Captive Portal Credits']");
    public SelenideElement        icpCreditAllocation     = $x("(//input[@type=\"text\"])[11]");
    public SelenideElement        orgSSIDSaveAndConfigBtn  = $x("//button[text()='Save and Configure']");
    public SelenideElement        addVLANLink                      = $x("//span[@class='addVLANLink']");
    public SelenideElement        customDVLAN                      = $x("//input[@value='Custom SSID VLAN']");
    public SelenideElement        vlanID                           = $x("//input[@data-type='vlanID']");
    public SelenideElement        vlanSaveBtn                      = $x("(//button[text()='Save'])[3]");
    public SelenideElement        orgWideSSIDSaveBtn1              = $x("(//button[text()='Save'])[2]");
    public SelenideElement        okBtnOrgSSID                     = $x("(//button[text()='OK'])[3]");
    public SelenideElement        wirelessLocationVLAN             = $x("//th[text()='VLAN ID']");
    public SelenideElement        locCustomVLAN                    = $x("//td[text()='Custom SSID VLAN']");
    public SelenideElement        selectVLAN                       = $x("//select[@id='selectedVlanId']");
    public SelenideElement        manageVLAN                       = $x("//td[text()='Management VLAN']");
    
    //probulkonboarding
    
    public SelenideElement        orgAddDevice                     = $x("//div[@data-tooltip='Add Device']");
    public SelenideElement        orgAddSingleDevice               = $x("//b[text()='Add Single Device']");
    public SelenideElement        orgAddMultipleDevice             = $x("//b[text()='Add Multiple Devices']");
    public SelenideElement        band24GHz                        = $x("//label[text()='2.4GHz']");
    public SelenideElement        securityWPA2Personal             = $x("//td[text()='WPA2 Personal']");
    public SelenideElement        securityWPA2PersonalMixed        = $x("//td[text()='WPA2 Personal Mixed']");
    public SelenideElement        securityWPA3PersonalMixed        = $x("//td[text()='WPA3 Personal Mixed (WPA2 + WPA3)']");
    public SelenideElement        securityWPA3                     = $x("//td[text()='WPA3 Personal']");
    public SelenideElement        band5GHz                         = $x("//label[text()='5GHz']");
    public SelenideElement        secband6GHz                      = $x("//label[text()='6GHz']");
    public SelenideElement        orgssidSettingTab                = $x("(//a[text()='Settings'])[1]");
    public SelenideElement        editOrgwideSSIDSaveBtn           = $x("(//button[text()='Save'])[2]");
    public SelenideElement        editorgssidOkaySuccessBtn        = $x("//button[text()='OK' and @id='SuccsEditWirNet']");
    public SelenideElement        cancelButton                     = $x("//button[@class='btn cancelBtn']");
    public SelenideElement        locationWPA2EnterpriseOrgSsidSecurity                   = $x("//td[text()='WPA2 Enterprise']");
    public SelenideElement        locationWPA3EnterpriseOrgSsidSecurity                   = $x("//td[text()='WPA3 Enterprise']");
    public SelenideElement        nodataAvailable                                         = $x("//td[text()='No Data Available']");
    public SelenideElement        orgwideSSIDToggleSwitchOnOff                            = $x("//h6[text()='Organization-wide SSID']/..//span[@class='cstmSlider cstmRound']");
    public SelenideElement        orgClick                         = $x("//*[@id=\"_divorgDiv1\"]/div");
    public SelenideElement        netgear                           = $x("//*[@id=\"logoInsightPro\"]/a/img");

    
    
    

    
    
    
    
    public SelenideElement modifyScheduler(String name) {
        SelenideElement modifyScheduler = $x("//td[text() ='"+ name +"']/../td[10]/p/span/i[1]");
        return modifyScheduler;
    }
    
    public SelenideElement NotificationText(String name) {
        SelenideElement NotificationText = $x("(//span[text() ='"+ name +", AP']/../../p[2])[1]");
        return NotificationText;
    }
    
    public SelenideElement SelctLoc(String name) {
        SelenideElement SelctLoc = $x("//*[text() = '" + name + "']/span/label/i");
        return SelctLoc;
    }
    
    
    public SelenideElement       SchedulerName                = $x("//*[@id=\"content\"]/div[5]/div/div/div[2]/div[2]/div/div/input");
    public SelenideElement       CreatedSchedule              = $x("//*[@id=\"wirelessTable\"]/thead/tr");
    public SelenideElement       Notificationicon              = $x("//*[@id=\"notificationDrop\"]/div");
    public SelenideElement       SeeAllNotification            = $x("//*[@id=\"notificationPadd\"]/div/div[3]/button");
    public SelenideElement       SearchLoc                     = $x("//*[@id=\"ancSearchIconOrgDash\"]");
    public SelenideElement       sendsearchText                = $x("//*[@id=\"orgSearchField\"]");
    public SelenideElement       Repeats                       = $x("//*[@id=\"content\"]/div[3]/div/div[2]/div/div[1]/div[2]/div/div[4]/div/div/select");
    
 //Added by Vivek ScreenNavigationPro
    
    public SelenideElement        TotalOrgCount               = $x("//h3[@id='hDeviceswiredQuickView']/span[text()='Organizations']/span");
    public SelenideElement        ProFilterOption             = $x("//a[@id='_ancDropToggOptOrg']/span[@class='icon-overflow']");
    public SelenideElement        FilterActiveOrg             = $x("//a[@class='padding-10 padding-top-0 padding-bottom-0 displayOptions colorGray active' and text()='Organization']");
    public SelenideElement        ProFilterApplyButton        = $x("//button[@id='_btnCancelOrg' and text()='Apply']");
    public SelenideElement        BoxOrgCardOrgTitle          = $x("//div[@class='LacationDivClick']/p[@class='location-name margin-top-5 fontSize20 DarkColor']");
    public SelenideElement        FilterActiveOwner           = $x("//a[@class='padding-10 padding-top-0 padding-bottom-0 displayOptions colorGray active' and text()='Owner']");
    public SelenideElement        BoxOrgCardOwnerTitle        = $x("//p[@id='gridOwnerDiv'] ");
    public SelenideElement        FilterActiveDevices         = $x("//a[@class='padding-10 padding-top-0 padding-bottom-0 displayOptions colorGray active' and text()='Devices']");
    public SelenideElement        BoxOrgCardDeviceTitle       = $x("//ul[@class=\"list-inline list-unstyled Organization-grid-details\"]");
    public SelenideElement        FilterLoc                   = $x("//a[@class='padding-10 padding-top-0 padding-bottom-0 displayOptions colorGray' and text()='Locations']");
    public SelenideElement        BoxOrgCardLocTitle          = $x("//ul[@class='list-inline list-unstyled Organization-grid-details Org-Location-Col']");
    public SelenideElement        FilterMgr                   = $x("//a[@class='padding-10 padding-top-0 padding-bottom-0 displayOptions colorGray' and text()='Managers']");
    public SelenideElement        BoxOrgCardMgrTitle          = $x("//p[@id='gridMgrDiv']");
    public SelenideElement        LocNameoffice1              = $x("//span[@class='location-name' and text()='office1']");
    public SelenideElement        ProFilterForlocBox          = $x("//div[@class='TableHeader headerindex1']//span[@class='icon-overflow-2']");
    public SelenideElement        LocFilterActiveDevices      = $x("//a[@class='padding-10 padding-top-0 padding-bottom-0 displayOptions colorGray active colorPurple' and text()='Devices']");
    public SelenideElement        BoxLocCardDeviceTitle       = $x("//p[@id='_pliLocDiv0']");
    public SelenideElement        LocFilterActiveWirelessClnt = $x("//a[@class='padding-10 padding-top-0 padding-bottom-0 displayOptions colorGray active colorPurple' and text()='Wireless Client']");
    public SelenideElement        BoxLocCardWirelessClntTitle = $x("//span[@class=\"location-details\" and text()='Wireless Client']");
    public SelenideElement        OrgSettingLeftDeviceSection = $x("//div[@class='leftMenuItems']//a[text()='Devices']");
    public SelenideElement        OrgSetting1stDeviceName     = $x("//th[@id='th1NameDeviceDash']/../../../tbody//span[@class='displayBlock']");
    public SelenideElement        OrgSettingLeftClientSection = $x("//div/a[text()='Clients']");
    public SelenideElement        clientheadertxt             = $x("//div[@class='clientTabcol inputTextField backInfoGray']");

    public SelenideElement        policyPro                   = $x("//a[@href=\"#/organization/settings/policy\"]");
    public SelenideElement        EmailNoti                   = $x("//*[text()=\"Email Notifications\"]");
    public SelenideElement        PushNoti                    = $x("//*[text()=\"Push Notifications\"]");
    public SelenideElement        Deviceown                   = $x("//*[text()=\"Device Ownership\"]");
    public SelenideElement        ScheduledReports            = $x("//*[text()=\"Scheduled Reports\"]");
    public SelenideElement        EmailReports                = $x("//*[text()=\"Email Reports\"]");
    public SelenideElement        ChooseAFile                 = $x("(//*[text()=\"Choose a file...\"])[2]");
    public SelenideElement        DeviceReb                   = $x("//a[@href=\"#/organization/settings/deviceReboot\"]");
    public SelenideElement        Rebootnow                   = $x("//p[text()=\"Reboot Now\"]");
    public SelenideElement        RebootSch                   = $x("//p[text()=\"Reboot Schedule\"]");
    public SelenideElement        LocPlus                     = $x("//i[@class=\"icon icon-icon-collapse\"]");
    public SelenideElement        DevSerialInDR               = $x("(//div[@class=\"DarkColor\"])[2]");
    public SelenideElement        ViewAllSchedules            = $x("//p[@class=\"mb-0 colorPurple ManagerClTxt pointerCursor linkUnderline\"]");
    
    public SelenideElement        ManagerPage                 = $x("(//a[@href=\"#/organization/settings/managers\"])[2]");
    public SelenideElement        QuickView                   = $x("//*[@id=\"quickView\"]");
    public SelenideElement        ManagerLabel                = $x("//h3[text()=\"Managers\"]");
    public SelenideElement        Plus                        = $x("//*[@id=\"content\"]/div[1]/div/div[1]/a/span");
    public SelenideElement        RoleLabel                   = $x("//*[@id=\"managerTable\"]/thead/tr/th[5]");
    public SelenideElement        StatusLabel                 = $x("//*[@id=\"managerTable\"]/thead/tr/th[6]");
    
    public SelenideElement        OrgWidepage                 = $x("//a[@href=\"#/organization/settings/orgWideSSID\" and @class=\"anchor\"]");
    public SelenideElement        PlusOrgSSID                 = $x("//*[@id=\"ancssidModWirSett\"]");
    public SelenideElement        SearchOrgSSID               = $x("//*[@id=\"divSearchPurWirSett\"]");
    public SelenideElement        OrgWideEnable               = $x("(//span[@class=\"cstmSlider cstmRound\"])[4]");
    public SelenideElement        OrgWideSSIDLabel            = $x("//h6[@class=\"LevelTitle font-size-16 m-b-20 padding-bottom-10\"]");
    
    public SelenideElement       client                       = $x("//*[@id=\"divSideBarSecEditVlan\"]/div[2]/div[3]");
    
//  Added By vivek || Web Portal Usability Improvements
  public SelenideElement        orgChooseBtn                                         = $x("//div[@class='clearfix']//input[@id='file']");
  public SelenideElement        OrgCountMgrPage                                      = $x("(//td[@class='managerOrgListToolTip'])[1]");
  public SelenideElement        OrgAndDeviceInfo                                     = $x("//h3[@id='hDeviceswiredQuickView']");
  public SelenideElement        UpdateOrgOK                                          = $x("//h4[text()='Organization Updated Successfully']/../..//button[text()='OK']");
  public SelenideElement getOrgImgLogoUrl(String name) {
      SelenideElement url = $x("//p[@title='" + name + "']/..//a/img");
      return url;
  }
     
  public SelenideElement OrgCardDeviceData(String Org_name) {
      SelenideElement url = $x("//p[text()='" + Org_name + "']/..//ul[@class='list-inline list-unstyled Organization-grid-details']");
      return url;
  }
    
  // Added by Anusha H
  public SelenideElement        PolicyInorg                     = $x("(//a[@href=\"#/organization/settings/policy\"])[1]");
  public SelenideElement        ApplytoAllOrgsCheckbox          = $x("//*[@id=\"radioEmailMSP1\"]/label/i");
  public SelenideElement        ApplytoAllOrgsCheckbox1          = $x("//*[@id=\"applyToAllOrg\"]");
  public SelenideElement        OrgInProAcct                    = $x("//*[@id=\"radioEmailMSP1\"]/label/i");
  public SelenideElement        BusinessOwnerEmailCheckbox      = $x("//*[text()=\"Email Notifications\"]/../div/label[2]/i");
  public SelenideElement        AdminEmailCheckbox              = $x("//*[text()=\"Email Notifications\"]/../div/label[1]/i");
  public SelenideElement        ManagerEmailCheckbox            = $x("//*[text()=\"Email Notifications\"]/../div/label[3]/i");
  public SelenideElement        Submit                          = $x("//button[text()=\"Submit\"]"); 
  public SelenideElement        BusinessOwnerPushCheckbox       = $x("//*[text()=\"Push Notifications\"]/../div/label[2]/i");
  public SelenideElement        AdminPushCheckbox               = $x("//*[text()=\"Push Notifications\"]/../div/label[1]/i");
  public SelenideElement        ManagerPushCheckbox             = $x("//*[text()=\"Push Notifications\"]/../div/label[3]/i");
  public SelenideElement        ScheduleReportsIcon             = $x("//*[@id=\"scheduleReportSwitch\"]");
  public SelenideElement        ScheduleReportsIcon1            = $x("//*[@id=\"scheduleReportSwitch\"]/../span");
  public SelenideElement        ScheduleReportsWeeklyCheckBox   = $x("//*[@id=\"isSchRadioWeekly\"]");
  public SelenideElement        ScheduleReportsWeekly           = $x("//*[@id=\"orgPolicyForm\"]/div[5]/div/label[1]/p");
  public SelenideElement        ScheduleReportsMonthlyCheckBox  = $x("//*[@id=\"isSchRadioMonthly\"]");
  public SelenideElement        ScheduleReportsMonthly          = $x("//*[@id=\"orgPolicyForm\"]/div[5]/div/label[2]/p");
  public SelenideElement        BusinessEmailAlertCheckbox      = $x("//*[text()=\"Email Reports\"]/../div/label[2]/i");
  public SelenideElement        AdminEmailAlertCheckbox         = $x("//*[text()=\"Email Reports\"]/../div/label[1]/i");
  public SelenideElement        ManagerEmailAlertCheckbox       = $x("//*[text()=\"Email Reports\"]/../div/label[3]/i");
  public SelenideElement        ChooseALogo                     = $x("//span[@class=\"button PinkChooseFile chooseBtn\"]");
  public SelenideElement        ManagerInOrg                    = $x("//*[@id=\"divSideBarSecEditVlan\"]/div[2]/div[4]/a");
  public SelenideElement        Orgname                         = $x("//*[@class=\"ManagerClTxt\"]");
  public SelenideElement        OrginSecAdmin                   = $x("//*[@id=\"_divorgDiv0\"]/div/p");
  public SelenideElement        LocInSecAdmin                   = $x("//*[text()=\"office1\"]");
  public SelenideElement        ownerName1                      = $x("//*[@id=\"existingUser\"]");
  public SelenideElement        ExistingOwnerMail               = $x("//*[@id=\"existingUser\"]/option[2]");
  public SelenideElement        ErrorMessage                    = $x("(//*[@id=\"myModal\"]/div/div/div[2]/p)[4]");
  public SelenideElement        OKtoErrorMessage                = $x("(//button[@class=\"btn btn-danger\"])[2]");
  public SelenideElement        dots                            = $x("//*[@id=\"_ancliOrgDiv0\"]");
  public SelenideElement        EditorgIn                       = $x("//*[@id=\"_liulliOrgDiv0\"]");
  
  //AddedByPratik
//  public SelenideElement        selectAllDeviceFromLocationCheckbox     = $x("//span[text()='office1']/..//span/span/label/i");
//  public SelenideElement        selectAllDeviceFromLocation2Checkbox    = $x("//span[text()='office2']/..//span/span/label/i");
//  public SelenideElement        ExpandDevicesScheduleloc2               = $x("//span[text()='office2']/../span//i[contains(@class,'collapse')]");
  public SelenideElement selectAllDeviceFromLocationCheckbox(String location) {
      SelenideElement selectAllDeviceFromLocationCheckbox = $x("//span[text()='" + location +"']/..//span/span/label/i");
      return selectAllDeviceFromLocationCheckbox;
  }
  public SelenideElement selectAllDeviceFromLocation2Checkbox(String location2) {
      SelenideElement selectAllDeviceFromLocation2Checkbox = $x("//span[text()='" + location2 +"']/..//span/span/label/i");
      return selectAllDeviceFromLocation2Checkbox;
  }
  public SelenideElement ExpandDevicesScheduleloc2(String location2) {
      SelenideElement ExpandDevicesScheduleloc2 = $x("//span[text()='" + location2 +"']/../span//i[contains(@class,'collapse')]");
      return ExpandDevicesScheduleloc2;
  }
  
  public SelenideElement        selectTimePeriodAmorpm                  = $x("//select[@id='timechange']");
  public SelenideElement        selectTimePeriodAm                      = $x("//select[@id='timechange']/option[text()='a.m.']");
  public SelenideElement        selectTimePeriodPm                      = $x("//select[@id='timechange']/option[text()='p.m.']");
  public SelenideElement        clientsTab                              = $x("//div[@class='leftMenuItems']/..//a[@href='#/organization/settings/clients']");
  public SelenideElement        ssidNameonClientList(String Ssid) {
      SelenideElement SSID = $x("//span[text()='"+ Ssid +"']");
      return SSID;
  }
  
  //AddedByPratik
  public SelenideElement        dropdown = $("#existingUser");
  public SelenideElement verifyOwnerEmailonOrgPage(String ownerEmail) {
      SelenideElement ownerEmailverify = $x("//h3[text()='"+ ownerEmail +"']");
      return ownerEmailverify;
  }
  public SelenideElement orgsettingsDevicesTab       = $x("//div[@id='divSideBarSecEditVlan']//a[@href='#/organization/settings/devices']");
  
}