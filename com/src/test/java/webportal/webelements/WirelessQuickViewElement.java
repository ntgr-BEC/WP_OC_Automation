package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;

import java.util.Map;

import static com.codeborne.selenide.Selenide.$$x;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;


public class WirelessQuickViewElement extends MyCommonAPIs {
    //
    public SelenideElement        wirelesstab               = $x("//a[contains(text(),'Wireless')]");
    public SelenideElement        settingsorquickview       = $x("//*[@id=\"divLocBarwirquickView\"]/div[2]/a");
//    public SelenideElement        settingsorquickview       = $x("(//a[contains(@href,'wirelessSettings')])[3]"); //for maint-beta 3 otherwise 4
    public SelenideElement        proAccWirelessTab         = $x("(//span[text()=\"Wireless\"])[3]");  //for maint-beta server
    public SelenideElement        proAccWirelesstab1        = $x("//a[text()=\"Wireless\"]");
    public SelenideElement        wifischedules             = $x("//a[contains(@class,'anchor')][contains(text(),'WiFi Schedules')]");
    public static SelenideElement fastroaming               = $x(
            "//div[@class=\"divactiveLeftMenu\"]/a[text()='" + WebportalParam.getLocText("Fast Roaming") + "']");
    public static SelenideElement instantwifi               = $x(
            "//div[@class='leftMenuItems']//a[text()='" + WebportalParam.getLocText("Instant WiFi") + "']");
    public static String          autoChannel               = "//h5[text()='" + WebportalParam.getLocText("Automatic Channel Allocation")
            + "']/../label";
    public static SelenideElement optimizenowbutton         = $x("//button[text()='" + WebportalParam.getLocText("Optimize Now") + "']");
    public static SelenideElement selectschedule            = $x("//h5[text()='" + WebportalParam.getLocText("Schedule") + "']/..//select");
    public SelenideElement        saveInsantWifiBtn         = $("#saveEditInstantWifi");
    public SelenideElement        instantwifisuccessmeg     = $x("//div[@id='successMessage']/div[@class='alert alert-success alert-dismissable']");
    public SelenideElement        staFastRoaming            = $("#formFastRoam input");
    public SelenideElement        enablefastroaming         = $x("//*[@id=\"formFastRoam\"]/div/div/div/label/span");
    public SelenideElement        conformation              = $x("//button[text() ='Yes']");
    public SelenideElement        mobilityid                = $x("//h5[@id=\"hAutoSwitchautoRRM\"]/../p");
    public SelenideElement        enableblacklist           = $x("//input[@id=\"enableBlackList\"]/../span");
    public SelenideElement        addblacklisturl           = $("#spanAddIconurlFiltering");
    public SelenideElement        inputdomain               = $("#domain");
    public SelenideElement        savedomain                = $("#btnAddUrlModlurlFiltering");
    public SelenideElement        blacklisturl              = $("#tdtbdyFooterurlFiltering0");
    public SelenideElement        blacklisttable            = $("#tblNoFooterurlFiltering");
    public SelenideElement        deleteurlyes              = $("#btnPullRghturlFiltering");
    public SelenideElement        entermacacl               = $x("//div/a[text()=\"MAC ACL\"]");
    public SelenideElement        enablemacacl              = $x("//h5[text()=\"Enable MAC Access Control\"]/..//span");
    public SelenideElement        macacltype                = $("#radius");
    public SelenideElement        macaclpolicy              = $("#policy");
    public SelenideElement        macaclnodeviceconfirm     = $("#btnModOhkMacAuth");
    public SelenideElement        macacladddevice           = $x("//button[text()=\"Add Device\"]");
    public SelenideElement        macacladddeviceallow      = $x("(//button[text()='Allow'])[1]");
    public SelenideElement        entercaptiveportal        = $x("//div/a[text()=\"Captive Portal\"]");
    public SelenideElement        enablecaptiveportal       = $x("//h5[text()=\"Enable Captive Portal\"]/..//span");
    public SelenideElement        selectfacebookwifi        = $x("//input[@id='instantCpRadio']/..");
    public SelenideElement        checkFbWifiSelected       = $x("//input[@id='fwRadio']");
    public SelenideElement        fbwifimenu                = $x("//div/a[contains(@href,'facebookwifi')]");
    public static SelenideElement configurefbwifi           = $x("//button[text()='" + WebportalParam.getLocText("Configure Now") + "']");
    public static SelenideElement modifyfbwifi              = $x(
            "//div[contains(@class,'FaceBookWiFi in')]//button[text()='" + WebportalParam.getLocText("OK") + "']");
    public static SelenideElement enterfbwifi               = $x("//div/a[text()='" + WebportalParam.getLocText("Facebook WiFi") + "']");
    public static SelenideElement enterfbwifinew            = $x("//div/a[text()='" + WebportalParam.getLocText("Facebook Wi-Fi") + "']");
    public static SelenideElement enablefbwifi              = $x("//h5[text()='" + WebportalParam.getLocText("Facebook WiFi") + "']/..//span");
    public SelenideElement        registerfbwifi            = $x("//button[@id='registerBtnfb']");
    public static SelenideElement verifypage                = $x("//button[text()='" + WebportalParam.getLocText("Verify Page") + "']");
    public SelenideElement        savefbwifi                = $("#saveEditVlan");
    public SelenideElement        selectbasiccaptive        = $x("//input[@id='cpRadio']/..");
    public SelenideElement        checkBasicCaptiveSelected = $x("//input[@id='cpRadio']");
    public SelenideElement        checkenablecaptiveportal  = $("#divDisplayMessage");
    public SelenideElement        enableredirecturl         = $x("//h5[@id='hRedUrlCapPort']/..//span");
    public SelenideElement        inputredirecturl          = $x("//div[@id=\"divREdUrCapPort\"]//input");
    public SelenideElement        inputtitle                = $x("//div[@id=\"divOnOffTitCapPort\"]//input");
    public SelenideElement        inputmessage              = $x("//div[@id=\"divOnOffMsgSetCapPort\"]//input");
    public SelenideElement        savecaptive               = $("#btnSavCapPort");
    public SelenideElement        captiveok                 = $("#SuccessCapPort");
    public SelenideElement        enterratelimit            = $x("//div/a[text()=\"Rate Limiting\"]");
    public SelenideElement        enableratelimit           = $x("//h5[text()=\"Enable Settings\"]/..//span");
    public SelenideElement        uploadrate                = $x("//h5[text()=\"Upload Rate Unit\"]/../select");
    public SelenideElement        uploadslider              = $x(
            "//h5[text()=\"Upload Rate Limit\"]/..//span[@class=\"ui-slider-handle ui-corner-all ui-state-default\"]");
    public SelenideElement        uploadrateleft            = $("#spnLeftPullrateLimit");
    public SelenideElement        uploadrateright           = $("#spnPullRghtrateLimit");
    public SelenideElement        downloadrateleft          = $("#spnLftrateLimit");
    public SelenideElement        downloadrateright         = $("#spnRghtrateLimit");
    public SelenideElement        downloadrate              = $x("//h5[text()=\"Download Rate Unit\"]/../select");
    public SelenideElement        saveratelimit             = $("#btnSaverateLimit");
    public SelenideElement        ratelimitok               = $x("(//*[@id=\"successModalrateLimit\"])[2]");
    public SelenideElement        ssidlistone               = $("#NetHmWirSett0");
    public String                 ssidListTable             = "#tbdyWirSett";
    public SelenideElement        ssidListTable1            = $x("#tbdyWirSett");
    protected ElementsCollection  ssidlist                  = $$(ssidListTable);
    public SelenideElement        addssid                   = $("#divssidWirSett");
    public SelenideElement        addvlanssid               = $x("//button[text() = 'Add New WiFi (SSID)']");

    // load balancing elements
    public SelenideElement        enableLoadBalacing              = $x("(//label[@class='cmnSwitch'])[3]/span");
    public SelenideElement        maxNumClients                   = $x("(//label[@class='checkbox'])[1][i]");
    public static String          radioBarStatus                  = "(//p[contains(text(),'"
            + WebportalParam.getLocText("WAC and WAX series access points") + "')]/../..//div[contains(@class,'CustomizeReactInputRange')])[%s]";
    public static SelenideElement radio24Ghz                      = $x("(//p[contains(text(),'"
            + WebportalParam.getLocText("WAC and WAX series access points") + "')]/../..//div[@class='input-range__slider'])[1]");
    public static String          radioMinNum                     = "(//p[contains(text(),'"
            + WebportalParam.getLocText("WAC and WAX series access points") + "')]/../..//span[text()='5'])[%s]";
    public static String          radioMaxNum                     = "(//p[contains(text(),'"
            + WebportalParam.getLocText("WAC and WAX series access points") + "')]/../..//span[text()='200'])[%s]";
    public static SelenideElement radio5Ghz                       = $x("(//p[contains(text(),'"
            + WebportalParam.getLocText("WAC and WAX series access points") + "')]/../..//div[@class='input-range__slider'])[2]");
    public static SelenideElement radio5GhzHigh                   = $x("(//p[contains(text(),'"
            + WebportalParam.getLocText("WAC and WAX series access points") + "')]/../..//div[@class='input-range__slider'])[3]");
    public SelenideElement        balanceOnClientRxRSSI           = $x("(//label[@class='checkbox'])[2][i]");
    public static String          clientBarStatus                 = "(//h6[contains(text(),'" + WebportalParam.getLocText("Minimum signal strength")
            + "')]/../..//div[contains(@class,'CustomizeReactInputRange')])[%s]";
    public static SelenideElement client24Ghz                     = $x(
            "(//h6[contains(text(),'" + WebportalParam.getLocText("Minimum signal strength") + "')]/../..//div[@class='input-range__slider'])[1]");
    public static String          clientMinNum                    = "(//h6[contains(text(),'" + WebportalParam.getLocText("Minimum signal strength")
            + "')]/../..//span[text()='1'])[%s]";
    public static String          clientMaxNum                    = "(//h6[contains(text(),'" + WebportalParam.getLocText("Minimum signal strength")
            + "')]/../..//span[text()='50'])[%s]";
    public static SelenideElement client5Ghz                      = $x(
            "(//h6[contains(text(),'" + WebportalParam.getLocText("Minimum signal strength") + "')]/../..//div[@class='input-range__slider'])[2]");
    public static SelenideElement client5GhzHigh                  = $x(
            "(//h6[contains(text(),'" + WebportalParam.getLocText("Minimum signal strength") + "')]/../..//div[@class='input-range__slider'])[3]");
    public SelenideElement        balanceOnChannelUtilization     = $x("(//label[@class='checkbox'])[3][i]");
    public static String          channelBarStatus                = "(//h6[contains(text(),'"
            + WebportalParam.getLocText("Maximum channel load allowed") + "')]/../..//div[contains(@class,'CustomizeReactInputRange')])[%s]";
    public static SelenideElement channel24Ghz                    = $x("(//h6[contains(text(),'"
            + WebportalParam.getLocText("Maximum channel load allowed") + "')]/../..//div[@class='input-range__slider'])[1]");
    public static String          channelMinNum                   = "(//h6[contains(text(),'"
            + WebportalParam.getLocText("Maximum channel load allowed") + "')]/../..//span[text()='50'])[%s]";
    public static String          channelMaxNum                   = "(//h6[contains(text(),'"
            + WebportalParam.getLocText("Maximum channel load allowed") + "')]/../..//span[text()='90'])[%s]";
    public static SelenideElement channel5Ghz                     = $x("(//h6[contains(text(),'"
            + WebportalParam.getLocText("Maximum channel load allowed") + "')]/../..//div[@class='input-range__slider'])[2]");
    public static SelenideElement channel5GhzHigh                 = $x("(//h6[contains(text(),'"
            + WebportalParam.getLocText("Maximum channel load allowed") + "')]/../..//div[@class='input-range__slider'])[3]");
    public SelenideElement        enableDisassociateStickyClients = $x("(//label[@class='cmnSwitch'])[4]/span");
    public SelenideElement        checkDisassociateSickyClients   = $x("//*[contains(text(),'Disassociate sticky clients')]");

    public boolean getFastRomaingStatus() {
        return staFastRoaming.is(Condition.checked);
    }

    public boolean getIGMPStatus() {
        return staIGMP.is(Condition.checked);
    }

    public boolean getB2UCStatus() {
        return staB2UC.is(Condition.checked);
    }
    

    public void setFastRomaing(boolean enable) {
        setSelected(enablefastroaming, enable);
        if (!enable) {
            clickYesNo(true);
        }
        waitReady();
    }

    public SelenideElement addscheduledssid      = $x("//span[@class='icon-add']");
    public SelenideElement mainpage              = $x("//*[@id='logo-group']");
    public SelenideElement wifitimezone          = $x("//*[contains(text(),'Location Timezone')]/parent::*");
    public SelenideElement scheduledwifitimezone = $x("//p[@class='m-b-30 fontSize12']");
    public SelenideElement SSID80211wbtn         = $x("//*[contains(text(),'802.11w (PMF)')]");
    public SelenideElement enablessidschedule    = $x("//h5[@id='hEnableSsidSchedule']/..//span");
    public SelenideElement disablessidschedule   = $x("(//span[@id='spnSsidEditWireless']/..//span)[2]");
    public SelenideElement addnewschedule        = $x("//div[@class='rateLimitLabel bold']//img[@class='plusIcon']");
    public SelenideElement schedulename          = $x("//input[@id='newScheduleName']");
    public SelenideElement scheduleName          = $x("//input[@data-type='ssidScheduleName']");
    public SelenideElement strtpickbtn           = $x("(//*[text()=\"Pick a Time\"])[1]");
    public SelenideElement okbtn                 = $x("//*[contains(@class,'okblk')]/button");
    public SelenideElement plusSlot              = $x("//img[contains(@src,'icon-plus-circle')]");
    public SelenideElement okbtn1                 = $x("//button[@class=\"btn btn-ok\"]");
    public SelenideElement strtpickbtn1           = $x("//*[@id=\"schedule\"]/div/div[3]/div/div/div[1]/p");
    public SelenideElement strtpickbtn2           = $x("//*[@id=\"schedule\"]/div/div[4]/div/div/div[1]/p");
    public SelenideElement endpickbtn1           = $x("//*[@id=\"schedule\"]/div/div[3]/div/div/div[2]/p");
    public SelenideElement endpickbtn2           = $x("//*[@id=\"schedule\"]/div/div[4]/div/div/div[2]/p");
    public SelenideElement cnclbtn               = $x(
            "//div[@class='modal-dialog modal-large']//div[@class='modal-header backDarkPurple colorWhite text-center']//img");
    public SelenideElement endpicktbtn           = $x("(//*[text()=\"Pick a Time\"])[1]");
    public SelenideElement delTimeSlot           = $x("//img[contains(@src,'del')]");
    public SelenideElement upbtn                 = $x("//i[@class='fa fa-angle-up']");
    public SelenideElement selectday             = $x("//*[@id=\"schedule\"]/div/div/ul/li/label/span");
    public SelenideElement selectdayTue          = $x("(//*[@id=\"schedule\"]/div/div/ul/li/label/span)[2]");
    public SelenideElement ssid                  = $("#ssid");
    public SelenideElement broadcastssid         = $x("//*[@name=\"broadcast\"]");
    public SelenideElement band                  = $("#band");
    public SelenideElement bandsteering          = $x("//*[@name=\"bandSteering\"]");
    public SelenideElement Maximumlimit          = $x("//div[text() = 'SSID maximum number exceeded.']");
    public SelenideElement MaximumlimitVlan      = $x("//div[text() = 'Maximum SSID Limit Exceed.']");
    public SelenideElement close                 = $x("//*[@id=\"divMainWirSett\"]/div[3]/div/div/div/div[1]/button/img");
    public SelenideElement closeCG               = $x("//*[@id=\"divMainWirSett\"]/div[2]/div[1]/div/div/div[1]/button/img");
    public SelenideElement closevlan             = $x("//button[@class = 'btn cancelBtn']");
    public SelenideElement ssidvlan              = $x("//input[@name = 'ssidName']");
    public SelenideElement bandvlan              = $x("//select[@name = 'band']");
    public SelenideElement passwordvlan          = $x("//input[@name = 'password']");
    public SelenideElement Addvlanssid           = $x("//button[@class = 'btn saveBtn']");
    public SelenideElement CancelSSID            = $x("//*[@id=\"divMainWirSett\"]/div[3]/div/div/div/div[5]/button[1]");
    public SelenideElement warnning              = $x("//*[@id=\"successModalMpskWarning\"]");
    public SelenideElement security                = $("#security");
    public SelenideElement Band                    = $("#band");
    public SelenideElement password                = $("#password");
    public SelenideElement clientisolation         = $x("//*[@name=\"clientIsolation\"]");
    public SelenideElement vlaninput               = $("#vlanInput");
    public SelenideElement addnewvlan              = $x("//*[@id=\"divMainWirSett\"]/div[3]/div/div/div[2]/div[2]");
    public SelenideElement vlanname                = $("#vlanName");
    public SelenideElement vlanid                  = $("#vlanID");
    public SelenideElement saveschedule            = $x("/html/body/div[2]/div/div[2]/div/div[3]/div[1]/div/div/div[6]/button[2]");  //("/html/body/div[2]/div/div[2]/div[3]/div[3]/div[1]/div/div/div[6]/button[2]");
    public SelenideElement oksave                  = $x("(//*[@id=\"myModal\"]/div/div/div[3]/button)[3]");
    public SelenideElement editsave                = $("#btnSaveSsideditWirNet");
    public SelenideElement Disable                 = $x("//h5[text() = '802.11w (PMF)']//following::input[@value = '0'] ");
    public SelenideElement Optional                = $x("//h5[text() = '802.11w (PMF)']//following::input[@value = '1']");
    public SelenideElement Mandatory               = $x("//h5[text() = '802.11w (PMF)']//following::input[@value = '2']");
    public SelenideElement devices                 = $x("//h3[text() = 'Devices']");
    public SelenideElement Mandatoryclick          = $x("//p[text()='Mandatory']/.. ");
    public SelenideElement Optionalclick           = $x("//p[text()='Optional']/.. ");
    public SelenideElement Disableclick            = $x("//p[text()='Disable']/.. ");
    public SelenideElement cancel                  = $x("//*[@id=\"content\"]/div[3]/div/div/div/div[2]/div[3]/div/button[1]");
    public SelenideElement editschedulesave        = $x("//button[@class='btn saveBtn']");
    public SelenideElement cancelSchedul           = $("#cancelBtnAddSch");
    public SelenideElement confirmok               = $("#SuccsEditWirNet");
    public SelenideElement Save                    = $x("//*[@id=\"divOvrHiddnWirSett\"]/div[3]/button[2]");
    public SelenideElement saveandconfigure        = $x("//*[@id=\"ssidForm\"]/../..//button[text()=\"Save and Configure\"]");
    public SelenideElement deletessidyes           = $x("//button[text()=\"Delete\"]");
    public SelenideElement deletescheduledwifiyes  = $x("//button[contains(text(),'Delete')]");
    public SelenideElement editscheduledwifiyesbtn = $x(
          "//*[@id=\"divMainLocBarurlFiltering\"]/div[5]/div/div/div[3]/button[2]");
    public SelenideElement deleteapyes             = $x("//button[@class = 'btn btn-danger'][text() = 'Delete']");
    public SelenideElement macaddress              = $("#macAdrClientList0");
    public SelenideElement strtime                 = $x("//input[@id='startTime']");
    public SelenideElement strtdwnbtn              = $x("//i[@class='fa fa-angle-down']");
    public SelenideElement endwnbtn                = $x("//i[@class='fa fa-angle-down']");
    public SelenideElement endtime                 = $x("//input[@id='endTime']");
    public SelenideElement SSIDEx                  = $("#ssidModalError > div");
    public SelenideElement CancelEXSSID            = $x("//*[@id=\"divMainWirSett\"]/div[3]/div[1]/div/div/div[5]/button[1]");
  
    
    

    public SelenideElement        selectinsightcaptiveportal    = $x("//input[@id='instantCpRadio']/..");
     public SelenideElement        selectinsightECP              = $x("//input[@id='externalCP']/..");
    public SelenideElement        checkIcpSelected              = $x("//input[@id='instantCpRadio']");
    public SelenideElement        icpinsufficientcredit         = $x("//div[contains(@style,'display: block;')]//button[@id='insufficientCredit']");
    public SelenideElement        icpinsufficientcreditclose    = $x("//div[contains(@style,'display: block;')]//button[@class='close']");
    public SelenideElement        icpValueErrorAlert            = $x("//div[contains(@class,'alert-danger')]");
    public static SelenideElement enableschedulereports         = $x(
            "//h5[text()='" + WebportalParam.getLocText("Schedule Reports") + "']/..//span");
    public static SelenideElement enabledailylogins             = $x("//p[text()='" + WebportalParam.getLocText("Daily Logins") + "']");
    public static SelenideElement enabledailyanalytics          = $x("//p[text()='" + WebportalParam.getLocText("Daily Analytics") + "']");
    public static SelenideElement enableweeklyanalytics         = $x("//p[text()='" + WebportalParam.getLocText("Weekly Analytics") + "']");
    public static SelenideElement inputportalname               = $x(
            "//span[text()='" + WebportalParam.getLocText("Portal Name*") + "']/..//input");
    public static SelenideElement inputwelcomeheadline          = $x(
            "//span[text()='" + WebportalParam.getLocText("Welcome Headline*") + "']/..//input");
    public static SelenideElement inputWelcomeMsg               = $x(
            "//span[text()='" + WebportalParam.getLocText("Welcome Message") + "']/..//input");
    public String                 deleteImageBasic              = "(//span[text()='Delete'])";
    public SelenideElement        deleteImgOkBtn                = $x("//button[@ng-click='deleteImage()']");
    public SelenideElement        addcaptivelogo                = $("#mySelect1");
    public SelenideElement        chooseCaptiveLogoBtn          = $x("//button[contains(@ng-click,'logo')]");
    public static SelenideElement selectCaptiveLogoName         = $x("//div[text()='" + WebportalParam.getLocText("Image Name*") + "']/..//input");
    public SelenideElement        selectCaptiveLogoFile         = $("#file-logo");
    public SelenideElement        uploadImgOkBtn                = $x("//button[@ng-click='uploadImage();']");
    public SelenideElement        addbackgroundimage            = $("#mySelectBg");
    public static SelenideElement chooseBackgroundImg           = $x(
            "//h5[text()='" + WebportalParam.getLocText("Add Desktop Background Image*") + "']/..//button");
    public SelenideElement        selectBackgroundImgFile       = $("#file-background");
    public SelenideElement        addmobliebackgroundimage      = $("#mySelectMobileBg");
    public static SelenideElement chooseMobileBackgroundImg     = $x(
            "//h5[text()='" + WebportalParam.getLocText("Add Mobile Background Image") + "']/..//button");
    public SelenideElement        selectMobileBackgroundImgFile = $("#file-mobile-background");
    public static SelenideElement inputlandingurl               = $x(
            "//span[text()='" + WebportalParam.getLocText("Landing Page URL*") + "']/..//input");
    public static SelenideElement selectsessionduration         = $x(
            "//span[text()='" + WebportalParam.getLocText("Session Duration*") + "']/..//select");
    public static SelenideElement clickcaptiveportalstep        = $x(
            "//h3[text()='" + WebportalParam.getLocText("Captive Portal Steps") + "']/..//span");
    public static SelenideElement selectsteptype                = $x("//h5[text()='" + WebportalParam.getLocText("Step Type *") + "']/..//select");
    public SelenideElement        clickfacebook                 = $x("//input[@ng-true-value='Facebook']");
    public SelenideElement        clicktwitter                  = $x("//input[@ng-true-value='Twitter']");
    public SelenideElement        clicklinkedIn                 = $x("//input[@ng-true-value='LinkedIn']");
    public SelenideElement        clickregister                 = $x("//input[@ng-true-value='Register']");
    public SelenideElement        clickregisterCustomize        = $x("//input[@ng-true-value='Register']/../label/a");
    
    public static SelenideElement paypalClientId                = $x(
            "//div[contains(text(),'" + WebportalParam.getLocText("Paypal client id *") + "')]/..//input[contains(@ng-model,'path[0]')]");
    public SelenideElement        currency                      = $x("//select[contains(@ng-model,'path[2]')]");
    public static SelenideElement amountToChargeEndUser         = $x(
            "//span[text()='" + WebportalParam.getLocText("Amount to Charge End-User *") + "']/..//input");
    public String                 choosevideotoplay             = "//select[@id='mySelect6']";
    public static SelenideElement    okvideo                       = $x("//*[@id=\"videoModal\"]/div/div/div[3]/button[1]");
    
    public SelenideElement        uploadvideo                   = $x("//input[@ng-click='showVideoModal();']/..");
    public static SelenideElement inputvideoname                = $x("//div[text()='" + WebportalParam.getLocText(" Video Name*") + "']/../input");
    public SelenideElement        selectmp4file                 = $("#file-video1");
    public static SelenideElement uploadvideookbutton           = $x(
            "//h4[text()='" + WebportalParam.getLocText("Upload Video") + "']/../..//button[text()='" + WebportalParam.getLocText("Ok") + "']");
    public String                 selectdisplayad               = "//select[@id='displayAdSelected']";
    public SelenideElement        uploaddisplayad               = $x("//input[@ng-click='showUploadDisplayAdModal();']/..");
    public static SelenideElement inputadname                   = $x("//span[text()='" + WebportalParam.getLocText("Ad Name*") + "']/..//input");
    public SelenideElement        selectadimagefile             = $("#file-display-ad");
    public SelenideElement        uploaddisplayadok             = $x("//a[@ng-click='uploadDisplayAdImage();']");
    public static SelenideElement savecaptiveportalstep         = $x("//h4[text()='" + WebportalParam.getLocText("Captive Portal Step")
            + "']/../..//button[text()='" + WebportalParam.getLocText("Save") + "']");
    public SelenideElement        previewFrame                  = $("#AAAApreviewFrame");
    public static SelenideElement desktopPreview                = $x(
            "//button[contains(text(),'" + WebportalParam.getLocText("Desktop Preview") + "')]");
    public SelenideElement        previewAcceptBtn              = $("#tableRegisterButton");
    public static SelenideElement mobilePreview                 = $x(
            "//button[contains(text(),'" + WebportalParam.getLocText("Mobile Preview") + "')]");
    public SelenideElement        previewCloseBtn               = $x("//div[@id='previewPortalAAADiv']//button");

    public SelenideElement        generatevoucher     = $x("//span[@class='ManagerClTxt pointerCursor']");
    public SelenideElement        inputvouchernum     = $x("//input[@name='numberOfVoucher']");
    public SelenideElement        generatebutton      = $x("//button[@class='btn saveBtn']");
    public SelenideElement        accesscode          = $x("(//div[contains(@class,'voucherCode ')])[2]");
    public static SelenideElement closegeneratewindow = $x("//h4[text()='" + WebportalParam.getLocText("Generated Vouchers") + "']/..//img");

    public SelenideElement AdvanceWirelessSetting = $x("//a[text()='Advanced Network Settings']");
    public SelenideElement Advance                = $x("//*[text() = 'Advanced']");
    public SelenideElement Advance1                = $x("(//*[text() = 'Advanced'])[2]");
    public SelenideElement NetworkSettings        = $x("(//*[text() = 'Network Settings'])[1]");
    public SelenideElement WirelessSetting        = $x("(//*[text() = 'Wireless Settings'])[2]");
    public SelenideElement MeshSetting            = $x("//*[text() = 'Mesh Settings']");
    public SelenideElement staIGMP                = $x("//*[@id=\"divConSecCOlMdWirSett\"]/div[1]/div/div/div/div[2]/label/span");
    public SelenideElement staIGMP1               = $x("//input[@id='enableBlackList'])[2]");
    public SelenideElement staB2UC                = $x("//*[@id=\"divConSecCOlMdWirSett\"]/div[1]/div/div/div/div[1]/label/span");
    public SelenideElement SaveIGMP               = $x("//*[@id=\"networkSaveBtn\"]");
    public SelenideElement ConformSaveIGMP        = $x("//*[@id=\"ErrorModal\"]");
    public SelenideElement HelpTextclickIGMP      = $x("//*[@id=\"igmp\"]");
    public SelenideElement HelpTextclickB2UC      = $x("//*[@id=\"multicast\"]");
    public SelenideElement HelpTextcheck          = $x("//*[@id=\"divMainWirSett\"]/div[3]/div/div/div[1]/h4");
    public SelenideElement closeHelpTextcheck     = $x("//*[@id=\"divMainWirSett\"]/div[3]/div/div/div[1]/button");
    public SelenideElement Settings               = $x("//*[@id=\"header\"]/div[2]/div/div[2]/div/span");
    public SelenideElement Settings1              = $x("//*[@id=\"header\"]/div[2]/div/div[1]/div/img");

    public SelenideElement CopyConfig             = $x("//a[text()= 'Copy Configuration']");
    public SelenideElement SelectOrganization     = $x("//*[@id=\"content\"]/div[2]/div/div/div[1]/div[1]/div[3]/div/div[1]/select");
    public SelenideElement SelectLocation         = $x("//*[@id=\"content\"]/div[2]/div/div/div[1]/div[1]/div[3]/div/div[2]/select");
//    public SelenideElement Selectdestination      = $x("//p[text() = 'OnBoardingTest']//../i");
    
    public SelenideElement Apply                  = $x("//button[text() = 'Apply']");
    public SelenideElement CopyConfiguration      = $x("//button[text() = 'Copy Configuration']");

    public SelenideElement           enterAdvanceRateSelection    = $x("//div/a[text()=\"Advanced Rate Selection\"]");
    public static SelenideElement    enableSetMinimumRateControls = $x(
            "//p[text()='" + WebportalParam.getLocText("Set Minimum Rate Controls") + "']");
    public static SelenideElement    CheckSetMinimumRateControls  = $x("//p[text() = 'Set Minimum Rate Controls' ]/..//input");
    public static SelenideElement    SaveAs                       = $x("//*[@id=\"saveArs\"]");
    public static SelenideElement    OK                           = $x("//p[text() ='SSID settings have been saved successfully!']//..//..//.//div[3]//button");
    public static SelenideElement    OKWireless                   = $x("//p[text() ='Advance Wireless Settings have been saved successfully.']//..//..//.//div[3]//button");
    public static SelenideElement    twoGhz                       = $x("//li[text() = '2.4 GHz']");
    public static SelenideElement    FiveGhz                      = $x("//li[text() = '5 GHz']");
    public static SelenideElement    SaveAndConfigure             = $x("//button[text() = 'Save and Configure']");
    public static SelenideElement    editnetwork                  = $x("//*[@id=\"headerLocImg\"]");
    public static SelenideElement    gotoNetworksetup             = $x("//*[@id=\"divSideBarSecEditVlan\"]/div/div[3]/a");
    public SelenideElement GoToManagementVLAN                     = $("#tdtbdyFooterurl0");
    public static SelenideElement    enterVLAN                    = $x("//*[@id=\"tdtbdyFooterurl0\"]");
    public static SelenideElement    VLANOK                       = $x("//*[@id=\"myModal\"]/div/div/div[3]/button[2]");
    public static SelenideElement    NEXT                         = $x("//*[text()='VLAN ID']/../../../../../../../../..//button[text()='Next']");
    public static SelenideElement    NEXT1                         = $x("(//button[text() = 'Next'])[10]"); //Changed for maint-qa("(//button[text() = 'Next'])[10]");
    public static SelenideElement    ADDbutton                    = $x("//*[@id=\"content\"]/div[1]/div/div/span");
    public static SelenideElement    NetworkName                  = $x("//*[@id=\"networkConfigForm\"]/div[2]/input");
    public static SelenideElement    Discription                  = $x("//*[@id=\"networkConfigForm\"]/div[3]/input");
    public static SelenideElement    VLANname                     = $x("//*[@id=\"networkConfigForm\"]/div[5]/input");
    public static SelenideElement    VLANID                       = $x("//*[@id=\"networkConfigForm\"]/div[6]/input");
    public static SelenideElement    Next                         = $x("//button[@class=\"btn saveBtn\"]");   //button[text() = 'Next']
    public SelenideElement Next1     = $x("(//button[text()='Next'])[7]");
    public static SelenideElement    PlusButton                   = $x("//*[@id=\"ssidForm\"]/div[4]/div/div/h3/span[2]/i[2]");
    public static SelenideElement    AdvanceRateSelection         = $x("//span[text() = 'Advanced Rate Selection']");
    public static SelenideElement    clickAdvanceRateSelection    = $x("//span[text() = 'Advanced Rate Selection']/../img");
    public static SelenideElement    FixedMulticastRatedrop       = $x("//div[@class = 'Dropdown-placeholder is-selected']");
    public static SelenideElement    FixedMulticastRate24         = $x("//div[@aria-selected='true']");
    public static SelenideElement    Selectelement                = $x("//div[@class='Dropdown-menu']");
  //  public static SelenideElement    Density0                     = $x("//div[@aria-valuenow = '0']");
    public static SelenideElement    Density0Text                 = $x("//div[@class  = 'fontSize14']");
  //  public static SelenideElement    Density1                     = $x("//div[@aria-valuenow = '1']");
    public static SelenideElement    LowerDensityline             = $(".inputRangeLabelBlock div");
    public static SelenideElement    saveas                       = $x("//*[@id=\"saveArs\"]");
    public static ElementsCollection DensityLevel                 = $$(".inputRangeLabelBlock");
    public static ElementsCollection DensityLevel1                = $$("//div[@class=\"inputRangeLabelBlock d-flex fontSize14\"]");
  //  public static SelenideElement    Density2                     = $x("//*[@id=\"arsInputRange\"]/div[2]");
  //  public static SelenideElement    Density3                     = $x("//div[@aria-valuenow = '3']");
  //  public static SelenideElement    Density4                     = $x("//div[@aria-valuenow = '4']");
    public static SelenideElement    Density0                        = $x("//*[contains(@class, 'inputRangeLabelBlock')]/div[1]/span[1]");
    public static SelenideElement    Density1                        = $x("//*[contains(@class, 'inputRangeLabelBlock')]/div[2]/span[1]");
    public static SelenideElement    Density2                        = $x("//*[contains(@class, 'inputRangeLabelBlock')]/div[3]/span[1]");
    public static SelenideElement    Density3                        = $x("//*[contains(@class, 'inputRangeLabelBlock')]/div[4]/span[1]");
    public static SelenideElement    Density4                        = $x("//*[contains(@class, 'inputRangeLabelBlock')]/div[4]/span[2]");
    public static SelenideElement    slider                       = $x("//*[@id=\"wlan0densityLevelArs\"]/div/div/span/div");
    public static SelenideElement    slider5                       = $x("//*[@id=\"wlan1densityLevelArs\"]/div/div/span/div");
    
    public static SelenideElement    Density0check                        = $x("//*[contains(@aria-valuenow, '0')]");
    public static SelenideElement    Density1check                        = $x("//*[contains(@aria-valuenow, '1')]");
    public static SelenideElement    Density2check                        = $x("//*[contains(@aria-valuenow, '2')]");
    public static SelenideElement    Density3check                        = $x("//*[contains(@aria-valuenow, '3')]");
    public static SelenideElement    Density4check                        = $x("//*[contains(@aria-valuenow, '4')]");
    
    public static SelenideElement    RadioAndChannels                     = $x("//*[@id=\"divSideBarSecEditVlan\"]/div/div[5]/a");
    public static SelenideElement    Enable24Ghz                          = $x("(//*[@id=\"divColMdRadiChnl\"]/div[1]/label/input)[1]");
    public static SelenideElement    Enable5GhzHigh                       = $x("(//*[@id=\"divColMdRadiChnl\"]/div[1]/label/input)[3]");
    public static SelenideElement    Enable5GhzLow                        = $x("(//*[@id=\"divColMdRadiChnl\"]/div[1]/label/input)[2]");
    public static SelenideElement    EnableOK                  = $x("(//*[@id=\"myModal\"]/div/div/div[3]/button)[3]");
   
    
    
    public static SelenideElement    DropDown24Ghz                        = $x("(//*[@id=\"hServRadiChnl\"])[1]");
    public static SelenideElement    DropDown5GhzHigh                     = $x("(//*[@id=\"hServRadiChnl\"])[3]");
    public static SelenideElement    DropDown5GhzLow                      = $x("(//*[@id=\"hServRadiChnl\"])[2]");
    public static SelenideElement    DropDown24GhzWireless                        = $x("(//*[@id=\"icon2PlusNsaAccordHeadSettng\"])[1]");
    public static SelenideElement    DropDown5GhzHighWireless                     = $x("(//*[@id=\"icon2PlusNsaAccordHeadSettng\"])[3]");
    public static SelenideElement    DropDown6GhzHighWireless                     = $x("(//*[@id=\"iconMinNsaAccordHeadSettng\"])[4]");
    public static SelenideElement    fiveghzHigh                                  = $x("/html/body/div[2]/div/div[2]/div[3]/div[2]/div[4]/div/div[2]/div/div[2]/div/div/div/form/div[3]/div/div/div/h3");
    public static SelenideElement    channel                                      = $x("/html/body/div[2]/div/div[2]/div[3]/div[2]/div[4]/div/div[2]/div/div[2]/div/div/div/form/div[3]/div/div/div/div/div/div/div[3]/select");
    public static SelenideElement    channels                                      = $x("/html/body/div[2]/div/div[2]/div[3]/div[2]/div[4]/div/div[2]/div/div[2]/div/div/div/form/div[3]/div/div/div/div/div/div/div[3]/select/option");
    public static SelenideElement    DropDown5GhzLowWireless                      = $x("(//*[@id=\"icon2PlusNsaAccordHeadSettng\"])[2]");
    public static SelenideElement    SaveWireless                                 = $x("//*[@id=\"btnSaveUpdteRadiChnl\"]");
    public static SelenideElement    WirelessAdvanceseting                        = $x("//*[@id=\"divSideBarSecEditVlan\"]/div/div[8]/a");
    public static SelenideElement    enableradio24                                = $x("(//*[@id=\"divColMdRadiChnl\"]/div/div)[1]");
    public static SelenideElement    Radiomode24                                  = $x("(//select[@class='form-control formValidate inputTextField'])[2]");
    public static SelenideElement    Channel24                                    = $x("(//select[@class= \"form-control inputTextField\"])[2]"); //changed xpath for maint-qa ("(//*[@id=\"dropdown-width\"])[1]");
    public static SelenideElement    ChannelWidth24                               = $x("(//select[@class= \"form-control inputTextField\"])[3]"); //Changed xpath for maint-qa ("(//*[@id=\"dropdown-width\"])[1]");
    public static SelenideElement    OutputPower24                                = $x("(//*[@id=\"divSettRadiChnl\"]/select)[1]");
    public static SelenideElement    enableradio5low                              = $x("(//*[@id=\"divColMdRadiChnl\"]/div/div)[2]");
    public static SelenideElement    Radiomode5low                                = $x("(//select[@class='form-control formValidate inputTextField'])[3]");
    public static SelenideElement    Channel5low                                  = $x("(//select[@class=\"form-control inputTextField\"])[5]");    //changed for maint-qa//("(//*[@id=\"dropdown-width\"])[2]");
    public static SelenideElement    ChannelWidth5low                             = $x("(//select[@class=\"form-control inputTextField\"])[6]"); //changed for maint-qa(//*[@id=\"dropdown-width\"])[2]");
    public static SelenideElement    OutputPower5low                              = $x("(//*[@id=\"divSettRadiChnl\"]/select)[2]");
    public static SelenideElement    enableradio5high                             = $x("(//*[@id=\"divColMdRadiChnl\"]/div/div)[2]");
    public static SelenideElement    Radiomode5high                               = $x("(//select[@class='form-control formValidate inputTextField'])[4]");
    public static SelenideElement    Channel5high                                 = $x("(//select[@class=\"form-control inputTextField\"])[8]"); //changed for maint-qa//("(//*[@id=\"divChnRadiChnl\"]/select)[3]");
    public static SelenideElement    ChannelWidth5high                            = $x("(//select[@class=\"form-control inputTextField\"])[9]"); //changed for maint-qa
    public static SelenideElement    OutputPower5high                             = $x("(//*[@id=\"divSettRadiChnl\"]/select)[3]");
    public static ElementsCollection    Radiomode24Select                         = $$(Selectors.byXpath("(//*[@id=\"divColMdRadiChnl\"]/div[2])[1]"));
    public static ElementsCollection    Radiomode5lowSelect                         = $$("#divColMdRadiChnl > div:nth-child(2) > select");
    public static ElementsCollection    Radiomode5highSelect                         = $$("#divColMdRadiChnl > div:nth-child(2) > select");
    public static SelenideElement    SaveDevicelevel                              = $x("//*[@id=\"btnSaveUpdteRadiChnl\"]");
    public static SelenideElement    okbutton                                     = $x("(//*[@id=\"myModal\"]/div/div/div[3]/button)[3]");
    public static SelenideElement    ManualMACButton                              = $x("//button[@data-target=\".addDevicesmanual\"]");
    public static SelenideElement    DeviceName                                   = $x("//input[@id='deviceName1']");
    public static SelenideElement    DeviceMAC                                    = $x("//input[@id='macaddress1']");
    public static SelenideElement    MACConfirmButton                             = $x("//div[@class=\"modal-footer mb-10\"]/button[@class=\"btn btn-primary\"][1]");
    
    
    public String basicList = "//td[text()='%s']/..";
    public String uptime    = basicList + "/td[13]";
    

    public SelenideElement inputnameyes(String text) {
        SelenideElement yes = $x("(//td[text()='" + text + "']/../td[1]//button)[1]/i");
        return yes;
    }

    public SelenideElement inputName(String text) {
        SelenideElement inputname;
        if($x("(//td[text()='" + text + "']/../td[1]//input)[2]").isDisplayed()) {
         inputname = $x("(//td[text()='" + text + "']/../td[1]//input)[2]");
        }else {
          inputname = $x("(//td[text()='" + text + "']/../td[1]//input)");
        }
          
        
        return inputname;
    }

    public void addNewVlan(String Name, String id) {
        addnewvlan.click();
        vlanname.sendKeys(Name);
        vlanid.sendKeys(id);
    }

    public SelenideElement editName(String text) {
        SelenideElement nameelement = $x("//td[text()='" + text + "']/../td[1]");
        return nameelement;
    }

//    public SelenideElement editModule(String text) {
//        SelenideElement Ssid = $x("//td[text()='" + text + "']/../td[15]/p");
//        return Ssid;
//    }
    
    public SelenideElement editModule(String text) {
        //SelenideElement Ssid = $x("//td[text()='" + text + "']/../td[15]/p");
        SelenideElement Ssid = $x("//td[text()='" + text + "']/../td[17]/p");
        return Ssid;
    }
    
    public SelenideElement editModuleforswitch(String text) {
        SelenideElement Ssid = $x("//td[text()='" + text + "']/../td[3]");
        return Ssid;
    }
    
    public SelenideElement editModuleforBR(String text) {
        SelenideElement Ssid = $x("//td[text()='" + text + "']/../td[9]/p");
        return Ssid;
    }

//  public SelenideElement enterDevice(String text) {
//      SelenideElement device = $x("//td[text()='" + text + "']/../td[15]/p//i[1]");
//      return device;
//  }
  
    public SelenideElement enterDevice(String text) {
        //SelenideElement device = $x("//td[text()='" + text + "']/../td[15]/p//i[1]");
        SelenideElement device = $x("//td[text()='" + text + "']/../td[17]/p//i[2]");
        return device;
  }
    
    public SelenideElement enterDevice1(String text) {
        //SelenideElement device = $x("//td[text()='" + text + "']/../td[15]/p//i[1]");
        SelenideElement device = $x("//td[text()='" + text + "']/../td[16]/p//i[3]");
        return device;
  }
 
//    public SelenideElement deleteDevice(String text) {
//        SelenideElement Ssid = $x("//td[text()='" + text + "']/../td[15]/p//i[2]");
//        return Ssid;
//    }
    
    public SelenideElement deleteDevice(String text) {
        SelenideElement Ssid = $x("//td[text()='" + text + "']/../td[17]/p//i[3]");
        return Ssid;
    }
    
    public SelenideElement deleteDeviceBR(String text) {
        SelenideElement Ssid = $x("//td[text()='" + text + "']/../td[9]/p//i[3]");
        return Ssid;
    }

    public SelenideElement findUrl(String text) {
        SelenideElement url = $x("//td[text()='" + text + "']/../td[2]//img/ancestor::p");
        return url;
    }

    public SelenideElement deleteUrl(String text) {
        SelenideElement url = $x("//td[text()='" + text + "']/../td[2]//img");
        return url;
    }

    public SelenideElement editWifi(String text) {       
        SelenideElement Ssid = $x("//p[@title='" + text +"']/../../../td[11]//i[2]/img/ancestor::p");
        System.out.println(Ssid);
        if (!Ssid.exists()) {
            Ssid = $x("//p[@title='" + text + "']/../../../td[10]//i[2]/img/ancestor::p");
            System.out.println(Ssid);
        }
        return Ssid;   
    }

    public SelenideElement editManagementVLAN(String text) {
        SelenideElement ManagementVLAN = $x("//p[@title='" + text + "']/../../td[10]//i[2]/img/ancestor::p");
        return ManagementVLAN;
    }

    public SelenideElement delVlan(String vlan) {
        SelenideElement Ssid = $x("//p[@title='" + vlan + "']/../../td[10]//i[2]/img/ancestor::p");
        return Ssid;
    }

    public SelenideElement editWifiSchedule(String text) {
        SelenideElement Schedulewifi = $x("//*[@id='divConRowurlFiltering']//*[contains(text(),'" + text + "')]//parent::*//parent::*//i[1]//img");
        return Schedulewifi;
    }

    public SelenideElement editSsid(String text) {
        
        SelenideElement Ssid = $x("//p[@title='" + text + "']/../../../td[11]//i[1]");
        
        if (!Ssid.exists()) {
            Ssid = $x("//p[@title='" + text + "']/../../../td[10]//i[1]");
        }
        return Ssid;
        
    }
    
    public SelenideElement editOrbiSsid(String text) {
        SelenideElement Ssid = $x("//p[@title='" + text + "']/../../td[9]//i[1]");
        return Ssid;
    }

    public SelenideElement deleteSsid(String text) {
        SelenideElement Ssid = $x("//p[@title='" + text + "']/../../../td[11]//i[2]");
        System.out.println(Ssid);
        if (!Ssid.isDisplayed()) {
            if($x("//p[@title='" + text + "']/../../../td[10]//i[3]").isDisplayed()) {
            Ssid = $x("//p[@title='" + text + "']/../../../td[10]//i[3]");
            }else {
                Ssid = $x("//p[@title='" + text + "']/../../../td[10]//i[2]");
            }
            System.out.println(Ssid);
        }
        return Ssid;
    
    }

    public SelenideElement deleteScheduledwifi(String wifi) {
        SelenideElement Schedulewifi = $x("//*[@id='divConRowurlFiltering']//*[contains(text(),'" + wifi + "')]//parent::*//parent::*//i[2]//img");
        return Schedulewifi;
    }

    public SelenideElement selectDays(String day) {
        SelenideElement selectDay = $x("//div[contains(text(),'" + day + "')]/..//span");
        return selectDay;
    }
    
    public SelenideElement Selectdestination(String text) {
        SelenideElement Selectdestination = $x("//p[text() = '" + text + "']//../i");
        return Selectdestination;
    }
    
    public SelenideElement        ClientPage                     = $x("//*[@id=\"divMainwirQickView\"]/div[1]/ul/li[8]/a");
    public SelenideElement        DeviceClientPage               = $x("//*[@id=\"divSideBarSecEditVlan\"]/div/div[3]/a");
    public SelenideElement        DisconnectClientPage           = $x("//*[@id=\"cleintComponentContainer\"]/div[1]/div[1]/div/div[1]/ul/li[2]");
    public SelenideElement        ClientOverview                 = $x("//*[@id=\"overViewDataDetail\"]");
    public SelenideElement        Securitydropdown               = $x("//*[@id=\"securityCollapse\"]/i[2]");
    public SelenideElement        Statisticdropdown              = $x("//*[@id=\"statisticsCollapse\"]/i[2]");
    public SelenideElement        Clientsecurity                 = $x("//*[@id=\"securityDataDetail\"]");
    public SelenideElement        Clientstatistics               = $x("//*[@id=\"statisticsDataDetail\"]");
    public SelenideElement        Search                         = $x("//*[@id=\"_divToolTipSrchCli\"]/a/span");
    public SelenideElement        Searchinput                    = $x("//*[@id=\"connectClientSearchInput\"]");
    public SelenideElement        Searchclick                    = $x("(//*[@id=\"search\"])[1]");
    public SelenideElement        ReceivedData                    = $x("//*[@id=\"statisticsDataDetail\"]/div/ul[1]/li[2]");
    public SelenideElement        TransmittedData                 = $x("//*[@id=\"statisticsDataDetail\"]/div/ul[2]/li[2]");
    public SelenideElement        clientRoaming                   = $x("//*[@id=\"overViewDataDetail\"]/div/div/span");
    public SelenideElement        clientRoamingcheck              = $x("//*[@id=\"main\"]/div/div[3]/div/div/div[1]/h4");
    public SelenideElement        Authentication                  = $x("//*[@id=\"main\"]/div/div[3]/div/div/div[1]/h4");
    
    
    public SelenideElement        kvr80211                  = $x("//*[@id=\"fastRoamingSt\"]");
    public SelenideElement        save80211                  = $x("//*[@id=\"btnSaveSsideditWirNet\"]");
    public SelenideElement        ok80211                  = $x("//*[@id=\"SuccsEditWirNet\"]");
    public SelenideElement        check80211                  = $x("//*[@id=\"divClearFixeditWirNet\"]/div[4]/div/div/div[2]/label/span");
    public SelenideElement        Warrning                  = $x("(//h4[text()='Warning']/../../../..//div[@class=\"modal-body\"]/p)[1]");
    public SelenideElement        Warrning1                  = $x("(//*[@id=\"myModal\"]/div/div/div[2]/p)[3]");
    public SelenideElement        okFast                  = $x("(//*[@id=\"myModal\"]/div/div/div[3]/button)[4]");
    
    public SelenideElement        SDM                  = $x("//*[@id=\"divSideBarSecEditVlan\"]/div/div[10]/a");
    public SelenideElement        enableSDM            = $x("//*[@id=\"inpCheckBoxDiagMod\"]");
    public SelenideElement        SaveSDM            = $x("//*[@id=\"btnSaveDiagMod\"]");
    public SelenideElement        SDMPort           = $x("//*[@id=\"pPrtDiagMod\"]");
    public SelenideElement        SDMWarring          = $x("//*[@id=\"SDM_AP_div\"]/div[3]/div[2]/div/div[2]/p[1]");
    public SelenideElement        SDMok           = $x("//*[@id=\"SDM_AP_div\"]/div[3]/div[2]/div/div[3]/button[2]");
    
    
    public SelenideElement checkdevicestatus(String text) {
        SelenideElement Selectdestination = $x("//*[contains(text(), '" + text + "')]//../*[name()=\"image\" and @style=\"cursor: pointer;\"]");
        return Selectdestination;
    }
    
    public SelenideElement checkdevicestatusDevicedash(String text) {
        SelenideElement Selectdestination = $x("//*[contains(text(), '" + text + "')]//../*[name()=\"image\"]");
        return Selectdestination;
    }
    
    
    public SelenideElement        UploadCPfile           = $x("//*[@id=\"fileCaptivePortal\"]");
    public SelenideElement        ViewDashboard          = $x("//*[@id=\"_divPnlHeadrLoc\"]/p/a");
    public SelenideElement        Topology               = $x("//*[@id=\"divMaindevicesDash\"]/div[1]/ul/li[2]/a");
    public SelenideElement        TopologyDevicedash     = $x("//*[@id=\"divSideBarSecEditVlan\"]/div[3]/div[2]/a");
//    public SelenideElement        checkdevicestatus      = $("#svgContainer > svg > g >g:nth-of-type(2) >circle");
    public SelenideElement        devicestatus           = $x("//body/div[@id='smartadmin-root']/div[1]/div[2]/div[1]/div[5]/div[1]/div[2]/div[2]/p[2]");
    public SelenideElement        deviceIP               = $x("//*[@id=\"topologyContainer\"]/div[5]/div/div[4]/div/table/tbody/tr[6]/td[1]");
    public SelenideElement        More                   = $x("//div[@class=\"smallButtonsBlock text-center margin-top-10\"]/button[1]");
    public SelenideElement        Devicetype             = $x("//*[@id=\"topologyContainer\"]/div[5]/div/div[4]/div/table/tbody/tr[2]/td[2]/span");
    public SelenideElement        Sidemenu               = $x(" //*[@id=\"topologyContainer\"]/div[3]/div[2]/div[1]/img");
    public SelenideElement        SearchTopology         = $x("//*[@id=\"topologyContainer\"]/div[3]/div[2]/div[2]/ul/li[1]/div[1]/img");
    public SelenideElement        Searchsend             = $x("//*[@id=\"topologyContainer\"]/div[3]/div/div[2]/ul/li[1]/div[2]/div[2]/div/div/input");
    public SelenideElement        Filter                 = $x("//*[@id=\"topologyContainer\"]/div[3]/div[2]/div[2]/ul/li[2]/div[1]/img");
    public SelenideElement        Topologyview           = $x(" //*[@id=\"topologyContainer\"]/div[3]/div[2]/div[2]/ul/li[3]/div[1]/img");
    public SelenideElement        Legends                = $x("//*[@id=\"topologyContainer\"]/div[3]/div[2]/div[2]/ul/li[4]/div[1]/img");
    public SelenideElement        Help                   = $x("//*[@id=\"topologyContainer\"]/div[3]/div[2]/div[2]/ul/li[5]/div/img");
    public SelenideElement        wirelesscheck          = $x("//*[@id=\"topologyContainer\"]/div[3]/div/div[2]/ul/li[2]/div[2]/div[2]/div[2]/div/div[1]/label/input");
    public SelenideElement        Wiredcheck             = $x("//*[@id=\"topologyContainer\"]/div[3]/div/div[2]/ul/li[2]/div[2]/div[2]/div[2]/div/div[2]/label/input");
    public SelenideElement        bothcheck              = $x("//*[@id=\"topologyContainer\"]/div[3]/div/div[2]/ul/li[2]/div[2]/div[2]/div[2]/div/div[3]/label/input");
    public SelenideElement        Empty                  = $x("");

    public SelenideElement        ALLband                = $x("//input[@type=\"checkbox\"][@id=\"all_band\"]//../i");
    public SelenideElement        band24                 = $x("//input[@type=\"checkbox\"][@id=\"band_2\"]//../i");
    public SelenideElement        band5                  = $x("//input[@type=\"checkbox\"][@id=\"band_5\"]//../i");
    public SelenideElement        band6                  = $x("//input[@type=\"checkbox\"][@id=\"band_6\"]//../i");
    public SelenideElement        checkband24                 = $x("//*[@id=\"band_2\"]");
    public SelenideElement        checkband5                  = $x("//*[@id=\"band_5\"]");
    public SelenideElement        checkband6                  = $x("//*[@id=\"band_6\"]");
    
//    locate device
    public SelenideElement        Locatedevice                       = $x("//*[@id=\"spnStDetSumm\"]/a[2]/small");
    public SelenideElement        LocatedeviceClick                  = $x("//*[@id=\"spnStDetSumm\"]/a[2]/small/span[1]");
    public SelenideElement        Stop                               = $x("//*[@id=\"spnStDetSumm\"]/a[2]/small/span[2]");
    public SelenideElement        HelpText                           = $x("//*[@id=\"spnStDetSumm\"]/a[2]/span");
    public SelenideElement        HelpTextcontent                    = $x("//*[@id=\"divMainSumm\"]/div[3]/div/div/div[2]/p[3]/span[2]");
    
    public SelenideElement StatusTool(String text) {
        SelenideElement status = $x("(//p[contains(text(), '"+ text +"')]/../table/tbody/tr/td)[1]");
        return status;
    }
    public SelenideElement SerialNoTool(String text) {
        SelenideElement SerialNo = $x("(//p[contains(text(), '"+ text +"')]/../table/tbody/tr/td)[3]");
        return SerialNo;
    }
    public SelenideElement SerialNocheck(String text) {
        SelenideElement SerialNo = $x("(//p[contains(text(), '"+ text +"')]/../table/tbody/tr/td)[4]");
        return SerialNo;
    }
    public SelenideElement MACTool(String text) {
        SelenideElement MAC = $x("(//p[contains(text(), '"+ text +"')]/../table/tbody/tr/td)[5]");
        return MAC;
    }
    public SelenideElement IPAddressTool(String text) {
        SelenideElement IPAddress = $x("(//p[contains(text(), '"+ text +"')]/../table/tbody/tr/td)[7]");
        return IPAddress;
    }
    public SelenideElement FirmwareVersionTool(String text) {
        SelenideElement FirmwareVersion = $x("(//p[contains(text(), '"+ text +"')]/../table/tbody/tr/td)[9]");
        return FirmwareVersion;
    }
    public SelenideElement ModelTool(String text) {
        SelenideElement Model = $x("(//p[contains(text(), '"+ text +"')]/../table/tbody/tr/td)[11]");
        return Model;
    }
    public SelenideElement Modelcheck(String text) {
        SelenideElement Model = $x("(//p[contains(text(), '"+ text +"')]/../table/tbody/tr/td)[12]");
        return Model;
    }
    public SelenideElement CriticalNotificationTool(String text) {
        SelenideElement CriticalNotification = $x("(//p[contains(text(), '"+ text +"')]/../table/tbody/tr/td)[13]");
        return CriticalNotification;
    }
    public SelenideElement FirmwareUpdateAvailableTool(String text) {
        SelenideElement FirmwareUpdateAvailable = $x("(//p[contains(text(), '"+ text +"')]/../table/tbody/tr/td)[15]");
        return FirmwareUpdateAvailable;
    }
    public SelenideElement NumberofClientsTool(String text) {
        SelenideElement NumberofClients = $x("(//p[contains(text(), '"+ text +"')]/../table/tbody/tr/td)[15]");
        return NumberofClients;
    }
    
    public SelenideElement        DeviceNameTopology                      = $x("//*[@id=\"topologyContainer\"]/div[5]/div/div[2]/div[2]/p[1]");
    public SelenideElement        DeviceStatusTopology                    = $x("//*[@id=\"topologyContainer\"]/div[5]/div/div[2]/div[2]/p[2]");
    public SelenideElement        DeviceLastRereshTopology                = $x("//*[@id=\"topologyContainer\"]/div[5]/div/div[2]/div[2]/p[3]/span[1]");
    public SelenideElement        DeviceNetworkconfigurationTopology      = $x("//*[@id=\"topologyContainer\"]/div[5]/div/div[2]/div[2]/p[4]");
    public SelenideElement        SharediognasticTopology                 = $x("//*[@id=\"topologyContainer\"]/div[5]/div/div[3]/div/div[1]/span");
    public SelenideElement        DeviceResetTopology                     = $x("//*[@id=\"topologyContainer\"]/div[5]/div/div[3]/div/div[2]/span");
    public SelenideElement        DeviceRebootTopology                    = $x("//*[@id=\"topologyContainer\"]/div[5]/div/div[3]/div/div[3]/span");
    public SelenideElement        DeviceSeialNoTopology                   = $x("//*[@id=\"topologyContainer\"]/div[5]/div/div[4]/div/table/tbody/tr[1]/td[1]");
    public SelenideElement        DeviceModelTopology                     = $x("//*[@id=\"topologyContainer\"]/div[5]/div/div[4]/div/table/tbody/tr[2]/td[1]");
    public SelenideElement        DeviceMacTopology                       = $x("//*[@id=\"topologyContainer\"]/div[5]/div/div[4]/div/table/tbody/tr[3]/td[1]");
    public SelenideElement        DeviceIPTopology                        = $x("//*[@id=\"topologyContainer\"]/div[5]/div/div[4]/div/table/tbody/tr[4]/td[1]");
    public SelenideElement        DeviceGatewayIPTopology                 = $x("//*[@id=\"topologyContainer\"]/div[5]/div/div[4]/div/table/tbody/tr[5]/td[1]");
    public SelenideElement        DeviceDNSTopology                       = $x("//*[@id=\"topologyContainer\"]/div[5]/div/div[4]/div/table/tbody/tr[6]/td[1]"); 
    public SelenideElement        DeviceStaticTopology                    = $x("//*[@id=\"topologyContainer\"]/div[5]/div/div[4]/div/table/tbody/tr[7]/td[1]");
    public SelenideElement        DeviceConnectedNeigborsTopology         = $x("//*[@id=\"topologyContainer\"]/div[5]/div/div[4]/div/table/tbody/tr[8]/td[1]");
    public SelenideElement        DeviceFWVersionTopology                 = $x("//*[@id=\"topologyContainer\"]/div[5]/div/div[4]/div/table/tbody/tr[9]/td[1]");
    public SelenideElement        DeviceLine                              = $x("//*[@id=\"path93\"]");
    public SelenideElement        DeviceUsage                             = $x("//*[@id=\"linkTooltip\"]/div[2]/table/tbody/tr[2]/td[1]");
    public SelenideElement        DeviceSpeed                             = $x("//*[@id=\"linkTooltipUsage\"]");
    
    public SelenideElement        EmailAddress                            = $x("//*[@id=\"smart-form-share\"]/section/label[2]/input");
    public SelenideElement        sendEmail                               = $x("//*[@id=\"topologyContainer\"]/div[8]/div/div/div[3]/button[2]");
    public SelenideElement        sucessBanner                            = $x("//*[@id=\"showErrorMsg\"]/div");
    public SelenideElement        rebootconfirm                           = $x("//button[text()='Continue']");
    public SelenideElement        resetconfirm                            = $x("//button[text()='Yes, factory reset.']");
    public SelenideElement        AbstractView                            = $x("//*[text() = 'Abstract View']/..");
    public SelenideElement        TreeViewclick                           = $x("//*[text() = 'Tree View']/..");
    
   
    public SelenideElement        enableClientisolation                   = $x("//*[@id=\"spnClientIsolationeditWirNet\"]");
    public SelenideElement        enableClientisolationNetwork            = $x("//*[@name=\"clientIsoSt\"]//../span");
    public SelenideElement        dropAdvance                             = $x("//*[@id=\"ssidForm\"]/div[4]/div/div/h3/span[2]/i[2]");
    public SelenideElement        isenableClientisolation                 = $x("//*[@id=\"clientIsoSt\"]");
    public SelenideElement        AllowUIAcess                            = $x("//*[@id=\"divClearFixeditWirNet\"]/div[3]/div[2]/div/div[2]/div/label/p");
    public SelenideElement        VLANInput                               = $x("//*[@id=\"selectedVlanId\"]");
    public static SelenideElement        AddVLAN                                 = $x("//*[@id=\"divClearFixeditWirNet\"]/div[3]/div[1]/div/div[3]/a/span");
                                                                             
    public SelenideElement        sendVLANID                              = $x("//*[@id=\"newVlanId\"]");
    public SelenideElement        SaveVLANID                              = $x("//*[@id=\"btnPrmyeditWirNet\"]");
    public SelenideElement        enableDVLAN                             = $x("//*[@id=\"spnDynamicVlanditWirNet\"]");
    public SelenideElement        DVLANYes                                = $x("//*[@id=\"divMaineditWirNet\"]/div[5]/div/div/div[3]/button[1]");
    public SelenideElement        Exceptiontext                           = $x("//*[@id=\"divMaineditWirNet\"]/div[5]/div/div/div[2]/p[1]");

    public SelenideElement       Channel5highlist                         = $x("(//*[@id=\"divChnRadiChnl\"]/select)[3]");
    public SelenideElement       ChannelWidth5ghz                         = $x("(//*[@id=\"dropdown-width\"])[2]");
    public SelenideElement       ChannelWidth2ghz                         = $x("(//*[@id=\"dropdown-width\"])[1]");
    public SelenideElement       InstantWifi                              = $x("//a[@class='anchorundefined' and @href='#/wireless/instantWifi']");
    public SelenideElement       OptimizeNow                              = $x("//button[text()='Optimize Now']");
    public SelenideElement       CollapseIcon                             = $x("//i[@class='icon icon-icon-collapse']");
    public SelenideElement       twoghz1uncheck                           = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[2]/span[1]/label/p");
    public SelenideElement       twoghz6uncheck                           = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[2]/span[6]/label/p");
    public SelenideElement       twoghz11uncheck                          = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[2]/span[11]/label/p");
    public SelenideElement       twoghz2uncheck                           = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[2]/span[2]/label/p");
    public SelenideElement       twoghz3uncheck                           = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[2]/span[3]/label/p");
    public SelenideElement       twoghz4uncheck                           = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[2]/span[4]/label/p");
    public SelenideElement       twoghz5uncheck                           = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[2]/span[5]/label/p");
    public SelenideElement       twoghz7uncheck                           = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[2]/span[7]/label/p");
    public SelenideElement       twoghz8uncheck                           = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[2]/span[8]/label/p");
    public SelenideElement       twoghz9uncheck                           = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[2]/span[9]/label/p");
    public SelenideElement       twoghz10uncheck                          = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[2]/span[10]/label/p");
    public SelenideElement       fiveghzselectall                         = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[2]/div/div[2]/a[2]");
    public SelenideElement       fiveghz144check                          = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[2]/span[2]/span[12]/label/p");
    public SelenideElement       WifiSetting                              = $x("//*[@id=\"divLocBarwirquickView\"]/div[2]/a");
    public SelenideElement       fiveghz140check                          = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[2]/span[2]/span[10]/label/p");
    public SelenideElement       fiveghz136check                          = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[2]/span[2]/span[10]/label/p");
    public SelenideElement       TwoghzSelectAll                          = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/div[2]/a[2]");
    public SelenideElement       fiveghz48check                           = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[2]/span[1]/span[4]/label/p");
    public SelenideElement       fiveghz52check                           = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[2]/span[1]/span[5]/label/p");
    public SelenideElement       fiveghz56check                           = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[2]/span[1]/span[6]/label/p");
    public SelenideElement       fiveghz149check                          = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[2]/span[2]/span[13]/label/p");
    public SelenideElement       selectTimeNagivation                     = $x("//*[@id=\"schedule\"]/div/div[2]/div/div/div[1]/div/div[1]/ul/li/div[2]/i");
    public SelenideElement       SelectEndTime                            = $x("//*[@id=\"schedule\"]/div/div[2]/div/div/div[2]/p");          
    public SelenideElement       SelectEndTimeNavigation                  = $x("//*[@id=\"schedule\"]/div/div[2]/div/div/div[2]/div/div[1]/ul/li/div[2]/i");
    public SelenideElement       OkayEndtime                              = $x("//*[@id=\"schedule\"]/div/div[2]/div/div/div[2]/div/div[2]/button");
    public SelenideElement       save                                     = $x("(//button[text()='Save'])[3]");    
    public SelenideElement       saveedit                                 = $x("(//button[text()='Save'])[2]");  
    public SelenideElement       saveedfinal                              = $x("//*[@id=\"SuccsEditWirNet\"]");  
 
   //Elements added by Patik for MPSK
    
    public SelenideElement        office1LocationBtn                          = $x("//*[@id=\"_divlocDiv0\"]");
    public SelenideElement        wirelessTab                                 = $x("//*[@id=\"openCommonMenuBar\"]/ul/li[4]/a[1]");
    public SelenideElement        settingBtn                                  = $x("//*[@id=\"divLocBarwirquickView\"]/div[2]/a");
    public SelenideElement        advanceSettingsBtn                          = $x("(//*[text() = 'Advanced'])[2]");
    public SelenideElement        mpskSettingOpt                              = $x("(//*[text() = 'Multi PSK Settings'])[2]");
    public SelenideElement        verifyMPSKSetting                           = $x("//p[text()='Multi PSK Settings']");
    public SelenideElement        clkAddMPSK                                  = $x("//*[@id=\"divConSecCOlMdWirSett\"]/div/div[1]/div/div/div/span");
    public SelenideElement        selectVLAN                                  = $x("//*[@id=\"addMPSKModal\"]/div/div/form/div[3]/div[1]/div/div");
    public SelenideElement        selectManagement                            = $x("//*[@id=\"1\"]");
    public SelenideElement        passwordMPSK                                = $x("//*[@id=\"password\"]");
    public SelenideElement        mpskKeyName                                 = $x("//*[@id=\"keyName\"]");
    public SelenideElement        clkonAddKeyIdentifierBtn                    = $x("//*[@id=\"addMPSKModal\"]/div/div/form/div[4]/button[2]");
    public SelenideElement        cnfmMessage                                 = $x("//*[@id=\"showNotification\"]/div");
    public SelenideElement        mpskKeySwitch                               = $x("//*[@id=\"ssidForm\"]/div[3]/div[1]/div[2]/div/div/label/span");
    public SelenideElement        mpskKeySwitchDisplayed                      = $x("//h5[text()='Multi Pre-Shared Key (MPSK)']");
    public SelenideElement        selectMPSKKeyVisible                        = $x("//h5[text()='Select Multi Pre-Shared Key(s)']");
    public SelenideElement        selectMPSKKey                               = $x("//*[@id=\"multi_Pre_Shared_Key\"]/div/div/div");
    public SelenideElement        clkonCheckboxofMPSKKey                      = $x("//*[@id=\"multi_Pre_Shared_Key\"]/div/ul/li/div[2]/div[1]/div/ul/li/div[1]/label/p");
    public SelenideElement        clkonMPSKKey                                = $x("//*[@id=\"divConSecCOlMdWirSett\"]/div/div[2]/div/div[1]/table/tbody/tr/td[3]");
    public SelenideElement        clkonDelete                                 = $x("//*[@id=\"divConSecCOlMdWirSett\"]/div/div[2]/div/div[1]/table/tbody/tr/td[4]/p/i[2]/img");
    public SelenideElement        clkonDeleteBtn                              = $x("//*[@id=\"deleteMPSKModal\"]/div/div/div[3]/button[2]");
    public SelenideElement        maximumSSIDlinkedtoMPSK                     = $x("//*[@id=\"ssidModalError\"]/div/text()");
    public SelenideElement        maximumSSIDlinkedtoMPSK1                    = $x("//*[@id=\"ssidModalError\"]/div");
    public SelenideElement        closeSSIDWindow                             = $x("//*[text() ='Add New SSID']/../button");
    public SelenideElement        addNewMPSKKey                               = $x("//*[@id=\"divConSecCOlMdWirSett\"]/div/div[1]/div/div/div/span");
    public SelenideElement        addMPSKKey1toSSID                           = $x("//p[text()='MPSKSSIDTest01']");
    public SelenideElement        addMPSKKey2toSSID                           = $x("//p[text()='MPSKSSIDTest02']");
    public SelenideElement        addMPSKKey3toSSID                           = $x("//p[text()='MPSKSSIDTest03']");
    public SelenideElement        addMPSKKey4toSSID                           = $x("//p[text()='MPSKSSIDTest04']");
    public SelenideElement        addMPSKKey5toSSID                           = $x("//p[text()='MPSKSSIDTest05']");
    public SelenideElement        addMPSKKey6toSSID                           = $x("//p[text()='MPSKSSIDTest06']");
    public SelenideElement        addMPSKKey7toSSID                           = $x("//p[text()='MPSKSSIDTest07']");
    public SelenideElement        addMPSKKey8toSSID                           = $x("//p[text()='MPSKSSIDTest08']");
    public SelenideElement        addMPSKKey9toSSID                           = $x("//p[text()='MPSKSSIDTest09']");
    public SelenideElement        CheckKeySelected9                            = $x("//p[text()='MPSKSSIDTest09']/../input");
    public SelenideElement        CheckKeySelected8                            = $x("//p[text()='MPSKSSIDTest08']/../input");

    public SelenideElement        clkonSSID                                   = $x("//*[@id=\"td4WirStee0\"]");
    public SelenideElement        editSSID                                    = $x("//*[@id=\"imgeditvlanColumnWirSett0\"]");
    public SelenideElement        clkonMPSKKeydropdown                        = $x("//*[@id=\"multi_Pre_Shared_Key\"]/div/div/div");
    public SelenideElement        keyIdentifierNote                           = $x("//span[text()='(Select Upto 8 Keys)']");
    public SelenideElement        okSaveBtn                                   = $x("//*[@id=\"SuccsEditWirNet\"]");
    public SelenideElement        mpsk2                                       = $x("//*[@id=\"divConSecCOlMdWirSett\"]/div/div[2]/div/div[1]/table/tbody/tr[1]/td[3]");
    public SelenideElement        mpsk3                                       = $x("//*[@id=\"divConSecCOlMdWirSett\"]/div/div[2]/div/div[1]/table/tbody/tr[1]/td[3]");
    public SelenideElement        mpsk4                                       = $x("//*[@id=\"divConSecCOlMdWirSett\"]/div/div[2]/div/div[1]/table/tbody/tr[1]/td[3]");
    public SelenideElement        mpsk5                                       = $x("//*[@id=\"divConSecCOlMdWirSett\"]/div/div[2]/div/div[1]/table/tbody/tr[1]/td[3]");
    public SelenideElement        mpsk6                                       = $x("//*[@id=\"divConSecCOlMdWirSett\"]/div/div[2]/div/div[1]/table/tbody/tr[1]/td[3]");
    public SelenideElement        mpsk7                                       = $x("//*[@id=\"divConSecCOlMdWirSett\"]/div/div[2]/div/div[1]/table/tbody/tr[1]/td[3]");
    public SelenideElement        mpsk8                                       = $x("//*[@id=\"divConSecCOlMdWirSett\"]/div/div[2]/div/div[1]/table/tbody/tr[1]/td[3]");
    public SelenideElement        mpsk9                                       = $x("//*[@id=\"divConSecCOlMdWirSett\"]/div/div[2]/div/div[1]/table/tbody/tr/td[3]");
    public SelenideElement        delete1                                     = $x("(//*[@id=\"divConSecCOlMdWirSett\"]/div/div[2]/div/div[1]/table/tbody/tr/td[4]/p/i[2]/img)[1]");
    public SelenideElement        addNewMPSKKey1                              = $x("//*[@id=\"divConSecCOlMdWirSett\"]/div/div[1]/div/div/div/span");
    public SelenideElement        scrollBar                                   = $x("//*[@id=\"multi_Pre_Shared_Key\"]/div/ul/li/div[2]/div[3]/div");
    public SelenideElement        errorMsgSSIdMPSKKey                         = $x("//div[text()='Up to 4 SSIDs can be configured with Multi Pre-Shared Key (MPSK). Please disable MPSK on other SSID before enabling on this SSID.']");
    public SelenideElement        gotoHomePage                                = $x("//*[@id=\"logoInsightPro\"]/a/img");
    public SelenideElement        verifyMPSKKeyisAdded                        = $x("//td[text()='MPSKSSIDTest01']");
    public SelenideElement        verifyMPSKKeyisAdded1                       = $x("//td[text()='MPSKSSIDTest02']");
    public SelenideElement        verifyMPSKKeyisAdded2                       = $x("//td[text()='MPSKSSIDTest03']");
    public SelenideElement        verifyMPSKKeyisAdded3                       = $x("//td[text()='MPSKSSIDTest04']");
    public SelenideElement        verifyMPSKKeyisAdded4                       = $x("//td[text()='MPSKSSIDTest05']");
    public SelenideElement        verifyMPSKKeyisAdded5                       = $x("//td[text()='MPSKSSIDTest06']");
    public SelenideElement        verifyMPSKKeyisAdded6                       = $x("//td[text()='MPSKSSIDTest07']");
    public SelenideElement        verifyMPSKKeyisAdded7                       = $x("//td[text()='MPSKSSIDTest08']");
    public SelenideElement        verifyMPSKKeyisAdded8                       = $x("//td[text()='MPSKSSIDTest09']");

    public SelenideElement SecondaryAdmin                   = $x("//a[text()= 'Secondary Admins']");
    public SelenideElement InviteSecondaryAdmin             = $x("//*[@id=\"_divMoreIconOrg\"]/a/img");
    public SelenideElement SecondaryName                    = $x("//*[@id=\"secondaryAdminName\"]");
    public SelenideElement SecondaryEmail                   = $x("//*[@id=\"secondaryAdminEmail\"]");
    public SelenideElement SaveSecondaryOrg                 = $x("//*[@id=\"saveOrgBtn\"]");
    public SelenideElement SucessfullSecondaryAdmin         = $x("//*[@id=\"pWarnIconsubscriptions\"]");
    public SelenideElement ExceedLimit                      = $x("//*[@id=\"secNoOrgDiv\"]/div[3]/div/div/div[2]/p");
    public SelenideElement ExceedLimitok                    = $x("//*[@id=\"secNoOrgDiv\"]/div[3]/div/div/div[3]/button");
    public SelenideElement EmailExits                        = $x("//*[@id=\"errorDiv\"]/div");
    public SelenideElement PendingStatus                     = $x("//*[@id=\"tdDevMacAddrdevicesDash\"]/span");
    public SelenideElement ResendYes                         = $x("//*[@id=\"main\"]/div/div[3]/div/div/div[4]/button[2]");
    public SelenideElement Successinvite                    = $x("//*[@id=\"pWarnIconsubscriptions\"]");
    public SelenideElement oksucess                          = $x("//*[@id=\"successModalMpskWarning\"]");
    public SelenideElement checkNumberofUser                 = $x("//*[@id=\"content\"]/div[2]/div[1]/div/p[2]");
    public SelenideElement wifiBand                          = $x("//p[text()='All']");
    public SelenideElement wifi6GHzBand                      = $x("//p[text()='6 GHz']");
    public SelenideElement defaultSecurity                   = $x("//*[text()='WPA3 Personal Mixed (WPA2 + WPA3)']");
    public SelenideElement securityOption                    = $x("//*[@id=\"security\"]");
    public SelenideElement sixGHz                            = $x("//label[text()='6GHz']");
    public SelenideElement wpa3MixedSecurity                 = $x("//td[text()='WPA3 Personal Mixed (WPA2 + WPA3)']");
    public SelenideElement band2GHz                          = $x("//label[text()='2.4GHz']");
    public SelenideElement band5GHz                          = $x("//label[text()='5GHz']");
    public SelenideElement Seondexits                        = $x("//*[@id=\"managerTable\"]");
    public SelenideElement Seondname                         = $x("//*[@id=\"managerTable\"]/tbody/tr/td[2]");

    
    // added by Tejeshwini K V
    
    
    public SelenideElement DeleteMPSK                 = $x("//tr[1]/td[3]");
    
    public SelenideElement editMPSK(String text) {
        SelenideElement Ssid = $x("//td[text()='" + text + "']/../td/p");
        return Ssid;
    }
    
    
    public SelenideElement deleteMPSK(String text) {
        SelenideElement Ssid = $x("//td[text()='" + text + "']/../td/p/i[2]");
        return Ssid;
    }
	
    public SelenideElement MaxMPSK             = $x("//*[@id=\"mpskModalError\"]/div");
    public SelenideElement MaxMPSKclose        = $x("//*[@id=\"addMPSKModal\"]/div/div/form/div[1]/button");
    public SelenideElement enableMPSK        = $x("//*[@id=\"divColMdClearFixeditWirNet\"]/div[1]/div[2]/div/div/label/span");
    
    
    public SelenideElement SelectMPSK(String text) {       
        SelenideElement        SelectMPSK                        = $x("//p[text()='"+ text +"']");
        return SelectMPSK;
    }
    
    
    public SelenideElement EEM                          = $x("//input[@id='enableEEM']//../../div/h5");
    public SelenideElement HelpTextclickEEM             = $x("//*[@id=\"eemMode\"]");
    public SelenideElement HelpEEMTextcheck             = $x("//*[@id=\"energyEfficientModal\"]/div/div/div[2]/p[1]");
    public SelenideElement HelpEEMTextclose             = $x("//*[@id=\"energyEfficientModal\"]/div/div/div[1]/button/img");
    public SelenideElement noSSIDsAreAvailable          = $x("//td[text()='No Data Available']");
    
//    client isolation Enha
    
     public SelenideElement        checkAddIP                   = $x("//*[@id=\"addIpAdress\"]");
     public SelenideElement        clickAddIP                   = $x("//*[@id=\"divClearFixeditWirNet\"]/div[3]/div[2]/div/div[2]/div[1]/div/div[1]/label/i");
     public SelenideElement        clickAddIPNetwork            = $x("//*[@id=\"ssidForm\"]/div[4]/div/div/div/div/div[4]/div[2]/div/div/div[2]/div[1]/div/div[1]/label/i");
 
     public SelenideElement        SelectAll                   = $x("(//*[text()='Select All'])[1]");
     public SelenideElement        SelectAll5                  = $x("(//*[text()='Select All'])[2]");
     public SelenideElement        Selectchanall3              = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[2]/span[3]/label/i");
     public SelenideElement        Selectchanall8              = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[2]/span[8]/label/i");
     public SelenideElement        Selectchanall10             = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[2]/span[10]/label/i");
     public SelenideElement        DeSelectchanall36           = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[2]/span[1]/span[1]/label/i");
     public SelenideElement        DeSelectchanall100          = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[2]/span[2]/span[1]/label/i");
     public SelenideElement        DeSelectchanall136          = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[2]/span[2]/span[10]/label/i");
     public SelenideElement        DomainName                  = $x("//input[@placeholder = 'Enter here']");
     public SelenideElement        AddDomainName               = $x("//button[text()= 'Add']");
     public SelenideElement        AddDomainNamedisabled       = $x("//button[@class='btn btn-default disableAccordian']");
//     public SelenideElement        SelectAll5                   = $x("(//*[text()='Select All'])[2]");
//     public SelenideElement        Selectchanall3                   = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[2]/span[3]/label/i");
//     public SelenideElement        Selectchanall8                   = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[2]/span[8]/label/i");
//     public SelenideElement        Selectchanall10                  = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[2]/span[10]/label/i");
//     public SelenideElement        DeSelectchanall36                = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[2]/span[1]/span[1]/label/i");
//     public SelenideElement        DeSelectchanall100               = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[2]/span[2]/span[1]/label/i");
//     public SelenideElement        DeSelectchanall136               = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[2]/span[2]/span[10]/label/i");
//     public SelenideElement        DomainName                       = $x("//input[@placeholder = 'Enter here']");
//     public SelenideElement        AddDomainName                    = $x("//button[text()= 'Add']");
//     public SelenideElement        AddDomainNamedisabled            = $x("//button[@class='btn btn-default disableAccordian']");
//     public SelenideElement        SelectAll5                   = $x("(//*[text()='Select All'])[2]");
//     public SelenideElement        Selectchanall3                   = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[2]/span[3]/label/i");
//     public SelenideElement        Selectchanall8                   = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[2]/span[8]/label/i");
//     public SelenideElement        Selectchanall10                  = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[2]/span[10]/label/i");
//     public SelenideElement        DeSelectchanall36                = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[2]/span[1]/span[1]/label/i");
//     public SelenideElement        DeSelectchanall100               = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[2]/span[2]/span[1]/label/i");
//     public SelenideElement        DeSelectchanall136               = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[2]/div[2]/div[2]/span[2]/span[10]/label/i");
     public SelenideElement deviceListfirstDevice    = $("#pspnddevicesDash0");
     public SelenideElement deviceListfirstdevice1   = $("#tdinpEditdevicesDash0");
     public SelenideElement connectedState           = $x("//span[@class='colorGreen deviceStatus']");
     public SelenideElement statasticsTab            = $x("(//a[text()='Statistics'])[1]");
     public SelenideElement verifySSID               = $x("//td[text()='apwp14270']");
     public SelenideElement clearStatsBtn            = $x("//button[text()='Clear Stats']");
     public SelenideElement broadcastAndMulticast    = $x("(//p[text()='0'])[1]");
     public SelenideElement unicast                  = $x("(//p[text()='0'])[2]");
//     public SelenideElement        DomainName                       = $x("//input[@placeholder = 'Enter here']");
//     public SelenideElement        AddDomainName                    = $x("//button[text()= 'Add']");
//     public SelenideElement        AddDomainNamedisabled            = $x("//button[@class='btn btn-default disableAccordian']");
     public SelenideElement        lastKnownInfo                    = $x("//p[text()='Last known information']");
     public SelenideElement        lastKnownInfostarsym             = $x("//span[text()='*' and @class='colorRed']");
     public SelenideElement        upTimedeviceDashVerify           = $x("//td[@id='tdUpTimedevicesDash0']");
     public SelenideElement        upTimedeviceDashVerify1          = $x("//p[contains(text(),'Days')]");
//     Added by Vivek
     public SelenideElement        sixghz                           = $x("//span[@class='fontSemiBold' and text()='6 GHz']");
     public SelenideElement orgSSIDCheck               = $x("//p[@title='apwp14270']");
     public SelenideElement orgWideSSIDedit3            = $x("//span[text()='apwp14271']");
     public SelenideElement deleteOrgSSID              = $("#imgdellanColumnWirSett0");
     public SelenideElement deleteOrgSSID1              = $("#imgdellanColumnWirSett1");
     public SelenideElement orgwidessidCaptivePortal      = $x("(//a[text()='Captive Portal'])[1]");
     public SelenideElement orgWideSSIDedit1            = $x("//td[text()='Enable']");
     public SelenideElement orgwideSSIDIPCErrrorMsgPopup            = $x("//h4[text()='Insufficient Credits.']");
     public SelenideElement btnInsufcred            = $("#insufficientCredit");
     public SelenideElement cancelorgSSIDBtn            = $("#btnCanCapPOrt");
     public SelenideElement orgwidessidedit2            = $x("(//td[text()='Enable'])[2]");
     
     
     public SelenideElement orgWideSSIDedit(String text) {       
         SelenideElement        orgWideSSIDeditname                        = $x("//span[text()='"+text+"']");
         return orgWideSSIDeditname;
     }
     
     public SelenideElement statisticcsTab             = $x("(//a[@href='#/devices/accessPoint/statistics' and text()='Statistics'])[1]");
     public SelenideElement statisicsBody              = $x("//tbody//tr");
     public SelenideElement clearStatsButton           = $x("//button[text()='Clear Stats']");
     public SelenideElement clientOne                  = $x("//td[text()='1']");
     public SelenideElement clientZero                 = $x("//td[text()='0']");
     public SelenideElement brdcastToMulticastSta      = $x("(//p[text()='0' and text()=' GB'])[1]");
     public SelenideElement unicastStatus              = $x("(//p[text()='0' and text()=' GB'])[2]");
     public SelenideElement wireessDataConsumText      = $x("//h2[text()='Wireless Data Consumption']");
     public SelenideElement wireessDataConsumLink      = $x("(//img[@class='externalIcon'])[4]");
     public SelenideElement selecChart                 = $("#selchngeHrClientChrt");
     public SelenideElement dataConsumpChart           = $x("//div[@class='lineChart']");
     public SelenideElement connectedClient            = $x("//h2[text()='Connected Client']");
     public SelenideElement clientsSSID                = $x("//h4[text()='Clients SSID']");
     public SelenideElement clientsOS                  = $x("//h4[text()='Clients OS']");
     public SelenideElement connectedClientGraph1      = $x("(//h5[text()='1'])[1]");
     public SelenideElement connectedClientGraph2      = $x("(//h5[text()='1'])[2]");
     public SelenideElement windowsOS                  = $x("//li[text()='Windows OS']");
     public SelenideElement connectedClientPerSSID     = $x("//h2[text()='Connected Clients per SSID']");
     public SelenideElement connectedClientPerSSIDLink = $x("(//img[@class='externalIcon'])[3]");
     public SelenideElement connectedClientPerSSIDPageHeader      = $x("//h3[text()='Connected Clients per SSID']");
     public SelenideElement connectedClientPerSSIDTable = $x("//div[@class='TableHeadBlock']");
     public SelenideElement maxconnecetedClient        = $x("//p[text()='Maximum Connected Clients (1)']");
     public SelenideElement ap9      = $x("");
     
     public SelenideElement apDetails(String text) {       
         SelenideElement        apDetails                        = $x("//span[text()='"+text+"']");
         return apDetails;
     }
     
     public SelenideElement ssidDetails(String text) {       
         SelenideElement        ssidDetails                        = $x("//li[text()='"+text+"']");
         return ssidDetails;
     }
     
     public SelenideElement ssidDetails1(String text) {       
         SelenideElement        ssidDetails1                        = $x("//span[text()='"+text+"']");
         return ssidDetails1;
     }
     
     public SelenideElement ssidDetails2(String text) {       
         SelenideElement        ssidDetails                        = $x("(//li[text()='"+text+"'])[1]");
         return ssidDetails;
     }
     
     
     public SelenideElement checklinetooltip(String text) {
         SelenideElement Selectdestination = $x("//*[contains(text(), '" + text + "')]//../*[name()=\"image\" and @style=\"cursor: pointer;\"]/../../*[@id = 'path63']");
         return Selectdestination;
     }
     
     
     public SelenideElement ssidcheck(String text) {       
         SelenideElement        SSIDexits                        = $x("//*[text() = '"+text+"']");
         return SSIDexits;
     }
     
     public SelenideElement  Speed           = $x("//*[@id=\"linkTooltipSpeed\"]");
     public SelenideElement  Usage           = $x("//*[@id=\"linkTooltipUsage\"]");
     public SelenideElement  TreeView        = $x("//*[text() = 'Tree View']");
     public SelenideElement  AbstractViewc   = $x("//*[text() = 'Abstract View']");
     public SelenideElement  SSIDView        = $x("//*[text() = 'SSID View']"); 
     public SelenideElement  SSIDViewclick   = $x("//*[text() = 'SSID View']//../input");    
     public SelenideElement  SSIDexits        = $x("//*[text() = 'SSID View']/..");
     
  // added by ravi
     public SelenideElement  basiccaptiveportal    =$x("//*[@id=\"dvRowCOlCapPort\"]/div[2]/div/div/div/div[2]/label/p");
     public SelenideElement  wiredcheckbox        =$x("//*[@id=\"divClearFixeditWirNet\"]/div[3]/div[2]/div/div[2]/div[1]/div/div[1]/label/p");
     public SelenideElement  wiredaddress         =$x("//*[@id=\"divClearFixeditWirNet\"]/div[3]/div[2]/div/div[2]/div[2]/div[1]/div/form/input");
     public SelenideElement addbutton             =$x("//*[@id=\"divClearFixeditWirNet\"]/div[3]/div[2]/div/div[2]/div[2]/div[2]/button"); 
     public SelenideElement owe                   =$x("//*[@id=\"ssidForm\"]/div[3]/div[1]/div[3]/div/label/i");
     public SelenideElement owetmode                 =$x("//*[@id=\"ssidForm\"]/div[3]/div[1]/div[3]/div[2]/label/i");
     public SelenideElement mpskwarning              =$x("(//*[@id=\"myModal\"]/div/div/div[1]/div/h4)[4]");
     public SelenideElement addcustomvlan             =$x("//*[@id=\"divClearFixeditWirNet\"]/div[3]/div[1]/div/div[3]/a/img");
     public SelenideElement customid                 =$x("//*[@id=\"newVlanId\"]");
     public SelenideElement vlansave                  =$x("//*[@id=\"btnPrmyeditWirNet\"]");
     public SelenideElement adressing                = $x("//*[@id=\"ssidForm\"]/div[4]/div[1]/div[1]/select");
     public SelenideElement networkip                = $x("//*[@id=\"ssidForm\"]/div[4]/div[1]/div[2]/div[1]/input");
    public SelenideElement        toggleBtn                               = $x("//span[@id=\"spnEnableMacAcl\"]");
    public SelenideElement         macBox                                 =$x("//div[@id=\"showNotification\"]");
    public SelenideElement         fastroaming1                            =$x("//*[@id=\"divMainWirSett\"]/div[3]/div[1]/div/div/div[3]/div[2]/label/span");
                                                                              //*[@id="divClearFixeditWirNet"]/div[4]/div/div/div[2]/label/span

//     ECP by tejeshwini K V
   
     public SelenideElement        ECPRadio                 = $x("(//*[@id=\"myModal\"]/div/div/div[2]/p)[6]");
     public SelenideElement        editECPRadio             = $x("(//*[@id=\"myModal\"]/div/div/div[2]/p)[5]");
     public SelenideElement        ECPProceed               = $x("//*[@id=\"walledGarden\"]");
     public SelenideElement        okECP                    = $x("//*[@id=\"enterpriseSecurityECPWarning\"]");
     public SelenideElement        okECPedit                = $x("//*[@id=\"enterpriseWPASecurityECPWarning\"]");
     public SelenideElement        saveokECP                = $x("//*[@id=\"btnSavCapPort\"]");
     public SelenideElement        SuccsECP                 = $x("//*[@id=\"SuccsEditWirNet\"]");
     public SelenideElement        WalledGarden             = $x("//*[@id=\"validateWalledGardenUrl\"]/div/textarea");
     public SelenideElement        AddWalledGarden          = $x("(//*[text()=\"Add\"])[2]");
     public SelenideElement        SaveeditECP              = $x("//*[@id=\"btnSavCapPort\"]");
     public SelenideElement        RemioveWalledGarden      = $x("//*[text()=\"Remove\"]");
     public SelenideElement        SplashPageURL            = $x("//*[@id=\"SplashPageURL\"]/div/div/div/input");
     public SelenideElement        radius                   = $x("(//*[@name=\"radius\"])[2]/..");
     public SelenideElement        IP                       = $x("(//*[text() = 'IPv4 Address'])[1]/../div/input");
     public SelenideElement        IPPassword               = $x("(//*[text() = 'Password'])[1]/../div/input");
     public SelenideElement        WebSecret                = $x("//*[text() = 'Secret']/../../div[2]/input");
     public SelenideElement        WebKey                   = $x("//*[text() = 'Key']/../../div[2]/input");
     public SelenideElement        AllowHTTPSEnable         = $x("(//*[@name ='allowHTTPS']/..)[1]");
     public SelenideElement        AllowFailsafe            = $x("(//*[@name ='failsafe']/..)[1]");
     
     
     public SelenideElement        SplashPageURLHelp            = $x("//*[@id=\"SplashPageURL\"]/div/div/div/div/div/span");
     public SelenideElement        WebHttpHelp                  = $x("//*[text() ='Web/HTTP']/../../div/span");
     public SelenideElement        RadiusLHelp                  = $x("//*[text() ='Radius']/../../div/span");
     public SelenideElement        WebAuthenticationURLHelp     = $x("//*[text() ='Web Authentication URL']/../div/span");
     public SelenideElement        FailsafeHelp                 = $x("//*[text() ='Failsafe']/../div/span");
     public SelenideElement        AllowHTTPSHelp               = $x("//*[text() ='Allow HTTPS']/../div/span");
     public SelenideElement        WalledGardenSHelp            = $x("//*[text() ='Walled Garden']/../div/span");
     
     public SelenideElement        SplashPageURLHelpText            = $x("//*[@id=\"SplashPageURL\"]/div/div/div/div/div/div");
     public SelenideElement        WebHttpHelpText                  = $x("//*[text() ='Web/HTTP']/../../div/div/p");
     public SelenideElement        RadiusLHelpText                  = $x("//*[text() ='Radius']/../../div/div/p");
     public SelenideElement        WebAuthenticationURLHelpText     = $x("//*[text() ='Web Authentication URL']/../div/div/p");
     public SelenideElement        FailsafeHelpText                 = $x("//*[text() ='Failsafe']/../div/div/p");
     public SelenideElement        AllowHTTPSHelpText               = $x("//*[text() ='Allow HTTPS']/../div/div/p");
     public SelenideElement        WalledGardenSHelpText            = $x("//*[text() ='Walled Garden']/../div/div/p");
     
     
     public SelenideElement SelectWalledGarden(String text) {       
         SelenideElement        SelectWalledGarden                        = $x("//*[text() = '"+text+"']/../i");
         System.out.println(SelectWalledGarden);
         return SelectWalledGarden;
     }
     
     public SelenideElement editorgWifi(String text) {
         SelenideElement Ssid = $x("//p[@title='" + text + "']/../../../td[9]//i[2]/img/ancestor::p");
         return Ssid;
     }
     
     
     public SelenideElement deleteorgSsid(String text) {
         SelenideElement Ssid = $x("//p[@title='" + text + "']/../../../td[9]//i[3]");
         if (!Ssid.exists()) {
             Ssid = $x("//p[@title='" + text + "']/../../../td[9]//i[2]");
         }
         return Ssid;
     }
  
     
//     Localization
     
     public SelenideElement       saveLOC                          = $x("//*[@id=\"divMainWirSett\"]/div[3]/div[1]/div/div/div[5]/button[2]");
     public SelenideElement       entercaptiveportalLoc            = $x("//*[@id=\"divSideBarSecEditVlan\"]/div[3]/div[3]/a");
     public SelenideElement       enablecaptiveportalLoc           = $x("//*[@id=\"spnEnableMsgCaptivePortal\"]");
     public static SelenideElement enableschedulereportsLoc        = $x("(//*[@id=\"spnEnableMsgCaptivePortal\"])[4]");
     public static SelenideElement enabledailyloginsLoc            = $x("//*[@id=\"captivePortalForm\"]/div[2]/div[2]/div[1]/label/p");
     public static SelenideElement enabledailyanalyticsLoc         = $x("//*[@id=\"captivePortalForm\"]/div[2]/div[2]/div[2]/label/p");
     public static SelenideElement enableweeklyanalyticsLoc        = $x("//*[@id=\"captivePortalForm\"]/div[2]/div[2]/div[3]/label/p");
     public static SelenideElement inputportalnameLoc              = $x("/html/body/div[4]/div[1]/div/div[1]/div[2]/div[1]/div[1]/input");
     public static SelenideElement inputwelcomeheadlineLoc         = $x("/html/body/div[4]/div[1]/div/div[1]/div[2]/div[1]/div[2]/input");
     public static SelenideElement inputWelcomeMsgLoc              = $x("/html/body/div[4]/div[1]/div/div[1]/div[2]/div[1]/div[3]/input");
     public static SelenideElement inputlandingurlLoc              = $x("/html/body/div[4]/div[1]/div/div[4]/div[1]/div[1]/div[1]/input");
     public static SelenideElement selectsessiondurationLoc        = $x("/html/body/div[4]/div[1]/div/div[4]/div[1]/div[1]/div[2]/select");
     public static SelenideElement clickcaptiveportalstepLoc       = $x("/html/body/div[4]/div[1]/div/div[4]/div[2]/div[1]/div/div/span");
     public static SelenideElement selectsteptypeLoc               = $x("//*[@id=\"addStepModal\"]/div/div/div[2]/div[2]/div[1]/select");
     public static SelenideElement savecaptiveportalstepLoc        = $x("//*[@id=\"addStepModal\"]/div/div/div[3]/button");
     public SelenideElement        deletessidyesLoc                = $x("//*[@id=\"divMainWirSett\"]/div[5]/div[2]/div/div[3]/button[2]");
     public SelenideElement        Customize                       = $x("//*[text()= 'Customize']");
     public SelenideElement        Label                           = $x("//*[@id=\"customizeRegisterOptionsModal\"]/div/div/div[2]/div[1]/div/table/tbody/tr/td[2]/input");
     public SelenideElement        Required                        = $x("//*[@id=\"customizeRegisterOptionsModal\"]/div/div/div[2]/div[1]/div/table/tbody/tr/td[5]/select");
     public SelenideElement        Add                             = $x("//*[@id=\"customizeRegisterOptionsModal\"]/div/div/div[2]/div[1]/div/button");
     public SelenideElement        Done                            = $x("//*[@id=\"customizeRegisterOptionsModal\"]/div/div/div[3]/button");

     
     // Written By Vivek for advance instant wifi

     public SelenideElement Allocation  = $x("//h5[@class='LevelTitle' and text()='Allocation']");
     public SelenideElement Description = $x("//p[@class='fontSize14 m-b-20 ' and text()='Description']");
     public SelenideElement LearnMore   = $x("//p[@class='fontSize14 m-b-20 ']/span[text()='Learn More']");
     public SelenideElement headerText  = $x("//div[@id='divModCOneditWirNet']/div/h4[text()='Instant WiFi']");
     public SelenideElement cross_close = $x("//div[@id='helpInstantWifi']//button[@id='btnCloseeditWirNet']");

     public SelenideElement    AutChlAloctn_cstm_sldr   = $x("//h5[text()='Automatic Channel Allocation']/../label/span");
     public ElementsCollection RestoreDefault           = $$(Selectors.byXpath("//a[text()='Restore Default']"));
     public ElementsCollection SelectAll_button         = $$(Selectors.byXpath("//a[text()='Select All']"));
     public ElementsCollection De_SelectAll             = $$(Selectors.byXpath("//a[text()='Deselect All']"));
     public SelenideElement    channelError             = $x("//div[@class='modal-content']//div[@class='m-b-30']/p");
     public SelenideElement    OkGotIt                  = $x("//div[@class='modal-content']//button[text()='OK. Got it']");
     public SelenideElement    Device_Configuration     = $x("//a/small[text()='Device Configuration']");
     public SelenideElement    DeviceSerial             = $x("//div[@id='divSeNoSumm']");
     public SelenideElement    plusIcon                 = $x("(//i[@class='icon icon-icon-collapse'])[1]");
     public SelenideElement    DeviceNameSerialnum      = $x("//span[@class='colorBlack']");
     public ElementsCollection Channel2_4data           = $$(Selectors.byXpath("//td[text()='2.4GHz']/../td[2]/select/option"));
     public ElementsCollection power2_4data             = $$(Selectors.byXpath("//td[text()='2.4GHz']/../td[3]/select/option"));
     public ElementsCollection ChannelWidth2_4data      = $$(Selectors.byXpath("//td[text()='2.4GHz']/../td[4]/select/option"));
     public ElementsCollection Channel5ghzLowdata       = $$(Selectors.byXpath("//td[text()='5GHz Low']/../td[2]/select/option"));
     public ElementsCollection power5ghzLowdata         = $$(Selectors.byXpath("//td[text()='5GHz Low']/../td[3]/select/option"));
     public ElementsCollection ChannelWidth5ghzLowdata  = $$(Selectors.byXpath("//td[text()='5GHz Low']/../td[4]/select/option"));
     public ElementsCollection Channel5ghzHighdata      = $$(Selectors.byXpath("//td[text()='5GHz High']/../td[2]/select/option"));
     public ElementsCollection power5ghzHighdata        = $$(Selectors.byXpath("//td[text()='5GHz High']/../td[3]/select/option"));
     public ElementsCollection ChannelWidth5ghzhighdata = $$(Selectors.byXpath("//td[text()='5GHz High']/../td[4]/select/option"));
     public SelenideElement    instantwifisuccessmegnew = $x("//div[@id='showErrorDiv']/div[@class='alert alert-success alert-dismissable']");
     public SelenideElement    leftAdvanceMenu          = $x("//div[@class='divundefined leftMenuHaveSec']//a[text() = 'Advanced']");
     public ElementsCollection AdvancedSubMenus         = $$x("//a[text()='Advanced']/../ul[@class='leftMenuSecondLevel']/li");
     
     //pratik clients traverse
     public SelenideElement     clientsTab              = $x("(//a[@href='#/devices/accessPoint/clients'])[1]"); 
//     public SelenideElement  dynamicvlanwarning         =        $x("(//*[@id=\"myModal\"]/div/div/div[1])[9]");
//     public SelenideElement  Nattobridge                =               $x("//*[@id=\"divClearFixeditWirNet\"]/div[3]/div[1]/div/div[1]/div[1]/select");
//     public SelenideElement  Natrange                   =             $x("//*[@id=\"divClearFixeditWirNet\"]/div[3]/div[1]/div/div[1]/div[2]/div[1]/p");
     //movedevicePratik
     public SelenideElement  noclientDetails                = $x("//td[text()='No connected clients available']");
     public SelenideElement  noReportDetails                = $x("//td[text()='No Reports Available']");
     public SelenideElement  wirelessDataConsumption        = $("#divWidgetLoc");
     public SelenideElement  wireLessDataConsumptionLink    = $x("(//img[@src='assets/img/commonIcons/external-link-symbol.png'])[4]");
     public SelenideElement  wirelessDashboardGraph         = $("#divtblHeadBlkDevList");
     public SelenideElement  dataProgressConsumptionBlock   = $x("//div[contains(@style,'height: 100%; width: 100%; background-color: rgb')]");
     public SelenideElement  selecttimerforWIrelessDataConsumption                 = $x("//select[@id='selchngeHrClientChrt']");
     public SelenideElement  verifyGraphCoordinates         = $x("//*[contains(@class,'apexcharts-xcrosshairs')]"); 
     public SelenideElement  verifyGraphCoordinates1        = $x("//*[contains(@class,'apexcharts-marker')]");
     public SelenideElement  connectedClientPerSSIDGraph         = $x("(//*[contains(@class,'apexcharts-svg')])[3]");
     public SelenideElement  connectedClientPerSSIDNo       = $x("(//span[text()='1'])[1]");
     //public SelenideElement  connectedClientPerSSID123      = $x("(//li[@title='apwp31957'])[1]");
     public SelenideElement  connectedClientLink            = $x("(//img[@src='assets/img/commonIcons/external-link-symbol.png'])[3]");
     public SelenideElement  connectedClientWirelessSSIDPage = $x("//*[contains(@class,'apexcharts-svg')]");
     
     public SelenideElement connectedClientPerSSID123(String ssid) {       
         SelenideElement        ssidname                        = $x("(//li[@title='"+ssid+"'])[1]");
         return ssidname;
     }
//     public SelenideElement  dynamicvlanwarning =        $x("(//*[@id=\"myModal\"]/div/div/div[1])[9]");
//     public SelenideElement  Nattobridge =               $x("//*[@id=\"divClearFixeditWirNet\"]/div[3]/div[1]/div/div[1]/div[1]/select");
//     public SelenideElement  Natrange      =             $x("//*[@id=\"divClearFixeditWirNet\"]/div[3]/div[1]/div/div[1]/div[2]/div[1]/p");
     public SelenideElement  dynamicvlanwarning =        $x("(//*[@id=\"myModal\"]/div/div/div[1])[9]");
     public SelenideElement  Nattobridge =               $x("//*[@id=\"divClearFixeditWirNet\"]/div[3]/div[1]/div/div[1]/div[1]/select");
     public SelenideElement  Natrange      =             $x("//*[@id=\"divClearFixeditWirNet\"]/div[3]/div[1]/div/div[1]/div[2]/div[1]/p");
     public SelenideElement  warningnat =                $x("//*[@id=\"ssidModalError\"]/div/text()");
     public SelenideElement  warningele =                $x("//*[@id=\"ssidModalError\"]");
     public SelenideElement  Natsubnetrange      =       $x("//*[@id=\"subNet\"]");
     public SelenideElement  Managementvlan =            $x("//*[@id=\"selectedVlanId\"]");
     public SelenideElement  editnetworkip                = $x("//*[@id=\"divClearFixeditWirNet\"]/div[3]/div[1]/div/div[1]/div[2]/div[1]/input");
     public SelenideElement IPAddressToolcheck(String text) {
         SelenideElement IPAddress = $x("(//p[contains(text(), '"+ text +"')]/../table/tbody/tr/td)[8]");
         return IPAddress;
         
         
         
     }
     
     
     public SelenideElement editMac(String text) {
         
         SelenideElement Ssid = $x("//*[text()='"+ text +"']/../../../td[1]/label");
         return Ssid;
     }
     
     public SelenideElement  removeMac =            $x("//*[@id=\"macAclSaveBtn\"]");
     public SelenideElement  deleteMac =            $x("(//button[text()=\"Delete\"])[2]");  
     public SelenideElement  connectedClients =     $x("//*[@id=\"_ulDataProp\"]/li[5]"); 
     public SelenideElement  connectedClientsCount =     $x("//*[@id=\"_ulDataProp\"]/li[5]/h3");
     public SelenideElement  connected =     $x("//*[@id=\"cleintComponentContainer\"]/div[1]/div[1]/div/div[1]/ul/li[1]"); 
     public SelenideElement  disconnected =     $x("//*[@id=\"cleintComponentContainer\"]/div[1]/div[1]/div/div[1]/ul/li[2]"); 
     public SelenideElement  wired =     $x("//*[@id=\"cleintComponentContainer\"]/div[1]/div[2]/div[1]/div[1]/div[1]/div/div[2]/label/p"); 
     public SelenideElement  wireless =     $x("//*[@id=\"cleintComponentContainer\"]/div[1]/div[2]/div[1]/div[1]/div[1]/div/div[3]/label/p"); 
     
     public SelenideElement deleteSsidORG(String text) {
         SelenideElement Ssid = $x("//p[@title='" + text + "']/../../../td[10]//i[3]");
         if (!Ssid.exists()) {
             Ssid = $x("//p[@title='" + text + "']/../../../td[9]//i[2]");
         }
         return Ssid;
     }

     
     //addedByPratik
     public SelenideElement  homeAccountPage  = $x("//img[@alt='Cloud Web Portal']");
     public SelenideElement  clickonLocation  = $x("//span[@class='location-pic']");
     public SelenideElement  clickwirelessTab      = $x("//a[text()='Wireless']");
     public SelenideElement  macAclCheckBox        = $x("//input[@id='selectAllMannualconnected']/..");
     public SelenideElement  about =     $x("//*[@id=\"smartadmin-root\"]/div/div[3]/div/div/div[1]/ul/li[6]/a");
     
     public SelenideElement sliderdtm(String num) {
//         SelenideElement Ssid = $x("//td[text()='" + text + "']/../td[9]/p//i[2]");(//*[@id="divColMdRadiChnl"]/div[8]/div[1]/div/div/span/div)[1])
                 SelenideElement marker = $x("(//*[@id=\"divColMdRadiChnl\"]/div[8]/div[1]/div/div/span/div)["+num+"]");
                 return marker;
     }
     
     public SelenideElement Interval(String hz,String num) {   
               int number =Integer.parseInt(num);
               if(number<=5)
               {
                 SelenideElement inter = $x("(//*[@id=\"divColMdRadiChnl\"]/div[8]/div[2]/div["+num+"]/span)["+hz+"]");
                return inter; 
               }
               else if(number==6)
               {
                   SelenideElement inter = $x("(//*[@id=\"divColMdRadiChnl\"]/div[8]/div[2]/div["+num+"]/span[1])["+hz+"]");
                   return inter; 
               }
               else
               {
                   SelenideElement inter = $x("(//*[@id=\"divColMdRadiChnl\"]/div[6]/div[2]/div[6]/span[2])["+hz+"]");
                   return inter; 
               }
   }
//     public SelenideElement Interval5h(String num) {
////       SelenideElement Ssid = $x("//td[text()='" + text + "']/../td[9]/p//i[2]");(//*[@id="divColMdRadiChnl"]/div[8]/div[1]/div/div/span/div)[1])
//               SelenideElement inter = $x("(//*[@id=\"divColMdRadiChnl\"]/div[8]/div[2]/div["+num+"]/span)[2]");
//               return inter;     
//   }
//     public SelenideElement Interval5l(String num) {
////       SelenideElement Ssid = $x("//td[text()='" + text + "']/../td[9]/p//i[2]");(//*[@id="divColMdRadiChnl"]/div[8]/div[1]/div/div/span/div)[1])
//               SelenideElement inter = $x("(//*[@id=\"divColMdRadiChnl\"]/div[8]/div[2]/div["+num+"]/span)[3]");
//               return inter;     
//   }
     public static SelenideElement    savead                       = $x("//*[@id=\"btnSaveUpdteRadiChnl\"]");
     public static SelenideElement    okw                     = $x("(//*[@id=\"myModal\"]/div/div/div[3]/button)[3]");
//     public static SelenideElement    beacon                   = $x("(//*[@id=\"divColMdRadiChnl\"]/div[4]/input)[1]");
     public static SelenideElement    warningbeacon                   = $x("//*[@id=\"showNotification\"]/div");
     
     
     public SelenideElement sliderbroadcast(String num) {
//       SelenideElement Ssid = $x("//td[text()='" + text + "']/../td[9]/p//i[2]");(//*[@id="divColMdRadiChnl"]/div[8]/div[1]/div/div/span/div)[1])
               SelenideElement marker = $x("(//*[@id=\"divColMdRadiChnl\"]/div[6]/div[1]/div/div/span/div)["+num+"]");
               return marker;
                                           
   }
     public SelenideElement Intervalbroadcast(String num,String hz) {
//       SelenideElement Ssid = $x("//td[text()='" + text + "']/../td[9]/p//i[2]");(//*[@id="divColMdRadiChnl"]/div[8]/div[1]/div/div/span/div)[1])
              int number =Integer.parseInt(num);
              if(number<=3)
              {
                   SelenideElement inter = $x("(//*[@id=\"divColMdRadiChnl\"]/div[6]/div[2]/div["+num+"]/span)["+hz+"]");
               return inter; 
              }
              else if(number==4)
              {
                  SelenideElement inter = $x("(//*[@id=\"divColMdRadiChnl\"]/div[6]/div[2]/div["+num+"]/span[1])["+hz+"]");
                  return inter; 
              }
              else
              {
                  SelenideElement inter = $x("(//*[@id=\"divColMdRadiChnl\"]/div[6]/div[2]/div[4]/span[2])["+hz+"]");
                  return inter; 
              }
    
     }
     public SelenideElement beacon(String num) {
//       SelenideElement Ssid = $x("//td[text()='" + text + "']/../td[9]/p//i[2]");(//*[@id="divColMdRadiChnl"]/div[8]/div[1]/div/div/span/div)[1])
               SelenideElement inter = $x("(//*[@id=\"divColMdRadiChnl\"]/div[4]/input)["+num+"]");
               return inter; 
    
     }
     
     public static SelenideElement    probeWarning                   = $x("//*[@id=\"showNotificationErrorSyslog\"]");
     public static SelenideElement    syslogLink                     = $x("//*[@id=\"showNotificationErrorSyslog\"]/div/a[2]");
     public static SelenideElement    syslogServer                   = $x("//*[@id=\"ipPortConfig\"]/div[1]/h5");
     public static SelenideElement    port                           = $x("//*[@id=\"ipPortConfig\"]/div[2]/h5");
     public static SelenideElement    sysLogStatus                   = $x("//*[@id=\"divConSecCOlMdWirSett\"]/div/div[2]/div/div/div[6]/label");
     public static SelenideElement    questionLogProbing             = $x("//*[@id=\"syslog\"]");
     public static SelenideElement    questionLogProbinginfo         = $x("//*[@id=\"divMainWirSett\"]/div[3]/div/div/div[2]/p");
     public static SelenideElement    probeClient                   = $x("//*[@id=\"divConSecCOlMdWirSett\"]/div/div[2]/div/div/div[6]/label");
     public static SelenideElement    networkPage                   = $x("//*[@id=\"smartadmin-root\"]");
     
     public SelenideElement ExpandAP(String text) {
         
         SelenideElement Ssid = $x("//*[text()='"+ text +"']/../span/i");
         return Ssid;
     }

     public static SelenideElement    channelinstantWiFI                 = $x("//*[@id=\"group-of-rows-0\"]/td/table/tr[1]/td[2]/select");
     public static SelenideElement    OutputpowerinstantWiFI             = $x("//*[@id=\"group-of-rows-0\"]/td/table/tr[1]/td[3]/select");
     public static SelenideElement    ChannelwidthinstantWiFI            = $x("//*[@id=\"group-of-rows-0\"]/td/table/tr[1]/td[4]/select");
     public static SelenideElement    channelinstantWiFI5                 = $x("//*[@id=\"group-of-rows-0\"]/td/table/tr[2]/td[2]/select");
     public static SelenideElement    OutputpowerinstantWiFI5             = $x("//*[@id=\"group-of-rows-0\"]/td/table/tr[2]/td[3]/select");
     public static SelenideElement    ChannelwidthinstantWiFI5            = $x("//*[@id=\"group-of-rows-0\"]/td/table/tr[2]/td[4]/select");
     public static SelenideElement    SaveInstantWiFI                      = $x("//*[@id=\"saveEditInstantWifi\"]");
     
     public static SelenideElement    ChannelTrigerINstantWiFI            = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[3]/div/div/div[2]/label/span");
     public static SelenideElement    DefaultTransmitPowerinstantWiFI5            = $x("//*[@id=\"divConSecCOlMdautoRRM\"]/div[3]/div[1]/div[1]/div[3]/div/div[2]/select");
     
     
//Added by Anusha H
     
     public SelenideElement  selectdropdownInECP  = $x("//div[@class=\"selectweek padding-top-5 inputTextField p-b-5 cursor-pointer\"]");
     public SelenideElement  selectJazeNetwork     = $x("//span[text()=\"Jaze Networks\"]");

     
//Added By Pratik
     public SelenideElement ssidName(String ssidName) {
         SelenideElement Ssid = $x("//span[text()='" + ssidName + "']");
         return Ssid;
     }    
     public SelenideElement clientMac() {
         SelenideElement mac = $x("//span[text()='" + WebportalParam.client1mac + "']");
         return mac;
     }
     public SelenideElement apSerialNoInClientsDetail() {
         SelenideElement apSerialNo = $x("//td[@class='ClientRoamingEllipses']//span[text()='" + WebportalParam.ap1serialNo + "']");
         return apSerialNo;
     }
     public SelenideElement     nosOfClientcountText            = $x("(//div[@class='ChartIconCol']//h5)[1]");
     public SelenideElement     nosOfSSIDcountText              = $x("(//div[@class='ChartIconCol']//h5)[2]");

     public SelenideElement  oktrunk     = $x("//*[@id=\"myModal\"]/div/div/div[3]/button[2]");
     
     
     public SelenideElement ManageProfiles                = $x("//*[@id=\"divSideBarSecEditVlan\"]/div[3]/div[10]/a");
     public SelenideElement RFProfiles                    = $x("//*[@id=\"macIPfilter\"]/div/div[2]/div[1]/div/ul/li[1]");
     public SelenideElement CustomerProfile               = $x("//*[@id=\"macIPfilter\"]/div/div[2]/div[1]/div/ul/li[2]");
     public SelenideElement OpenOffice                    = $x("//*[@id=\"DataTables_Table_5\"]/tbody/tr[1]/td[1]");
     public SelenideElement Outdoor                       = $x("//*[@id=\"DataTables_Table_5\"]/tbody/tr[2]/td[1]");
     public SelenideElement Savant                        = $x("//*[@id=\"DataTables_Table_6\"]/tbody/tr[1]/td[1]");
     public SelenideElement BandO                         = $x("//*[@id=\"DataTables_Table_6\"]/tbody/tr[2]/td[1]");
     public SelenideElement NICE                          = $x("//*[@id=\"DataTables_Table_6\"]/tbody/tr[3]/td[1]");
     public SelenideElement BluOS                         = $x("//*[@id=\"DataTables_Table_6\"]/tbody/tr[4]/td[1]");
     public SelenideElement addRFProfile                  = $x("//*[@id=\"DataTables_Table_6\"]/tbody/tr[4]/td[1]");
     public SelenideElement RFProfileName                 = $x("//*[text()='Create RF Profile']/../../div[3]/div[2]/div/div/input");
     public SelenideElement RFProfileDescription          = $x("//*[text()='Create RF Profile']/../../div[3]/div[2]/div/div[3]/input");
     public SelenideElement CreateRFProfile               = $x("//*[@id=\"divMainApNotify\"]/div[3]/div/div/div[4]/button[2]");
     public SelenideElement CloseRFProfile                = $x("//*[@id=\"divMainApNotify\"]/div[3]/div/div/div[1]/button");
     
     
     public SelenideElement editRFProfileName             = $x("//*[@id=\"macIPfilter\"]/div[2]/div/div/div/div[1]/input");
     public SelenideElement editRFProfileDescription      = $x("//*[@id=\"macIPfilter\"]/div[2]/div/div/div/div[2]/input");
     public SelenideElement SaveEditRFProfile             = $x("//*[@id=\"general_save\"]");
     public SelenideElement custom                        = $x("//*[@id=\"ssidForm\"]/div[2]/div/label/span");
     public SelenideElement customEdit                    = $x("//*[@id=\"divColMdClearFixeditWirNet\"]/div[6]/div/label/span");
     public SelenideElement SelectProfile                 = $x("//*[@id=\"ssidForm\"]/div[2]/div[2]/select");
     public SelenideElement enablecopyProfile             = $x("//*[@id=\"divMainApNotify\"]/div[3]/div/div/div[3]/div[2]/div/div[2]/div[1]/div/label/i");
     public SelenideElement SelectcopyProfile             = $x("//*[@id=\"divMainApNotify\"]/div[3]/div/div/div[3]/div[2]/div/div[2]/div[2]/select");
     public SelenideElement DescriptionValidation         = $x("//*[@id=\"showErrorMsg\"]/div");
     public ElementsCollection preferredcolumnsnew        = $$x("//*[@id=\"DataTables_Table_2\"]/thead/tr");
     
     public SelenideElement GeneralRF                     = $x("//*[@id=\"divSideBarSecEditVlan\"]/div[3]/div[1]/a");
     public SelenideElement DeviceListRF                  = $x("//*[@id=\"divSideBarSecEditVlan\"]/div[3]/div[2]/a");
     public SelenideElement RadioSettingsRF               = $x("//*[@id=\"divSideBarSecEditVlan\"]/div[3]/div[3]/a");
     public SelenideElement InstantWiFiPreferredChannelRF = $x("//*[@id=\"divSideBarSecEditVlan\"]/div[3]/div[4]/a");
     
     public SelenideElement GeneralRFName                 = $x("//*[@id=\"macIPfilter\"]/div[2]/div/div/div/div[1]/input");
     public SelenideElement GeneralRFDis                 = $x("//*[@id=\"macIPfilter\"]/div[2]/div/div/div/div[2]/input");
     public SelenideElement Saveedit                     = $x("//*[@id=\"general_save\"]");
     
     public SelenideElement DevicelistName                 = $x("//*[@id=\"macIPfilter\"]/div[2]/table/thead/tr/th[1]");
     public SelenideElement DevicelistSerialNumber         = $x("//*[@id=\"macIPfilter\"]/div[2]/table/thead/tr/th[2]");
     public SelenideElement DevicelistModel                = $x("//*[@id=\"macIPfilter\"]/div[2]/table/thead/tr/th[3]");
     
     
     public SelenideElement outputpower24                   = $x("//*[@id=\"divSettRadiChnl\"]/select");
     public SelenideElement channelWidth24                  = $x("//*[@id=\"dropdown-width\"]");
     public SelenideElement RadioMode24                     = $x("//*[@id=\"divColMdRadiChnl\"]/div/div/div/div[2]/select");
     
     public SelenideElement outputpower24Device             = $x("(//*[@id=\"divColMdRadiChnl\"]/div[2]/select)[1]");
     public SelenideElement channelWidth24Device            = $x("(//*[@id=\"divOnOdSetRadiChnl\"]/select)[1]");
     public SelenideElement RadioMode24Device               = $x("(//*[@id=\"divSettRadiChnl\"]/select)[1]");
     public SelenideElement channel4                        = $x("//*[@id=\"instant_WiFi\"]/div[4]/div/div[2]/div[2]/div[1]/div[1]/div[2]/span[4]/label/i");
     public SelenideElement channel56                       = $x("//*[@id=\"instant_WiFi\"]/div[4]/div/div[2]/div[2]/div[1]/div[1]/div[2]/span[4]/label/i");
     public SelenideElement Devicelist(String text, int num) {
         SelenideElement RF = $x("//*[text()='" + text + "']/../td['"+ num+"']");
         
         return RF;
     }
     
     
     public SelenideElement editRF(String text) {
         SelenideElement RF = $x("//td[text()='" + text + "']/../td[9]/p");
         
         return RF;
     }
     
     public SelenideElement deleteRFprofile(String text) {
         SelenideElement RF = $x("//td[text()='" + text + "']/../td[9]/p/i[2]");        
         return RF;
     }
     
     
     public SelenideElement editRFprofile(String text) {
         SelenideElement RF = $x("//td[text()='" + text + "']/../td[9]/p/i[1]");        
         return RF;
     }
     public SelenideElement DisRFprofile(String text) {
         SelenideElement RF = $x("//td[text()='" + text + "']/../td[8]");        
         return RF;
     }
     
     public boolean getCustomStatus() {
         if(customEdit.isDisplayed()) {
             return customEdit.is(Condition.checked);
         }else {
             return custom.is(Condition.checked);
         }
         
     }
     
     public SelenideElement FWverOnInsight          = $x("(//*[@id=\"tdFirmWreDevIddevicesDash0\"])[1]");
   //AddedByPratik
     public SelenideElement disassociateStickyClientSw  = $x("//*[contains(text(),'Disassociate sticky clients')]/..//span[@class='cstmSlider cstmRound']");
     public SelenideElement loadBalancingPageSaveButton = $x("//button[@id='loadBalancingSave']");
     public SelenideElement loadBalancingpageOKButton   = $x("//p[text()='Settings Applied Successfully']/../..//button[text()='OK']");
     public SelenideElement clientIsolationQuestionMark = $x("//p[contains(text(),'Add wired IP Address or')]/../../..//div[contains(text(),'?')]");
     public SelenideElement clientIsolationHelpText     = $x("//p[contains(text(),'Client Isolation restriction is enabled')]");
     public SelenideElement checkBoxAddIpAddress        = $x("//input[@id='addIpAdress']/../i");
     public SelenideElement addIpWarningText            = $x("//p[text()='Up to 16 entries are supported. On devices running old firmware, only 5 entries will be configured.']");
     public SelenideElement hoverToSSID                 = $x("//td[text()='N/A']");

     public SelenideElement goZoneIP(int text)
     {
         SelenideElement ecpip = $x("(//*[text() = 'IPv4 Address'])["+text+"]/../div/input");  
         return ecpip;
     }
     
     public SelenideElement goZonePassword(int text)
     {
         SelenideElement password = $x("(//*[text() = 'Password'])["+text+"]/../div/input");  
         return password;
     }
    
     public SelenideElement        proceed              = $x("//*[@id=\"walledGarden\"]");
    
     public static SelenideElement    broadcastTogglebutton                          = $x("//*[@id=\"broadCastSSID\"]");
     
     public SelenideElement        selectDropdown             = $x("//*[@class=\"selectweek padding-top-5 inputTextField p-b-5 cursor-pointer\"]");
     public SelenideElement        selectJaze              = $x("//*[text()=\"Jaze Networks\"]");
}


