/**
 *
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;

/**
 * @author zheli
 */
public class AccountPageElement extends MyCommonAPIs {

    // locationBarIcons
    public SelenideElement        addInsightIncludedDev = $x("//div[contains(@class,'insightIncluded ')]/button");
    public static SelenideElement addNetWorkButton1     = $(".icon-add.dropdown-toggle");
    public static SelenideElement Locationgrid          = $("//*[@id=\"addLocationIcon\"]/span");
    public SelenideElement        addMultiplelocations  = $(Selectors.byXpath("//*[@data-tooltip=\"Add Location\"]/ul/li[2]/a"));
    public SelenideElement        addMultiplelocations0 = $(Selectors.byXpath("//*[@data-tooltip=\"Add Location\"]/ul/li[1]/a"));
    public SelenideElement        MessageAlert          = $x("//*[@id=\"addNetwork\"]/div");
    public SelenideElement        ImageIcon             = $x("//*[@id=\"file\"]");
    ElementsCollection            Image_elems           = $$("//*[@class='location-pic']/a/img");

    // public static SelenideElement addNetWorkButton = $("//div[@class='locationBarIcons']/span[@class='addIcon']");

    public SelenideElement           TotalLocationCountPath= $x("//div[@class=\"TableHeader headerindex1\"]/h3[1]");
    
    public static SelenideElement    addNetWorkButton      = $("[data-tooltip='" + WebportalParam.getLocText("Add Network") + "']");
    public static SelenideElement    addNetWorkPro         = $x("//div[@data-tooltip='Add Location']/span");
    public SelenideElement           addsinglelocation     = $x("//b[text()='Add Single Location']/..");
    public SelenideElement           locationlist          = $("#locationDropdown");
    public ElementsCollection        alllocations          = $$(Selectors.byXpath("//ul[@class='search-location-list']/li"));
    public static SelenideElement    numberMultipleloc     = $("#businessPhn");
    public SelenideElement           addNetLocationName    = $("#addNetLocationName");
    public SelenideElement           addPassword1          = $("#editAdminPass");
    public SelenideElement           netCountryList1       = $(Selectors.byXpath(("//*[@id=\"_laInputTwo\"]/select")));
    public SelenideElement           timeZone1             = $(Selectors.byXpath("//*[@id=\"_laInputTwo\"]/select[@name=\"timeZone\"]"));
    public SelenideElement           addNetZipcode1        = $(Selectors.byXpath("//*[@id=\"_laInputTwo\"]/input[@name=\"postCode\"]"));
    public ElementsCollection        LocTable              = $$(Selectors.byXpath("//*[@id=\"tbdydevicesDash\"]/tr"));
    public SelenideElement           savebutton1           = $(Selectors.byXpath("//*[@id=\"divTblResdevicesDash\"]/div[2]/button[2]"));
    public static ElementsCollection dialogbox1            = $$("#modal-content");
    public SelenideElement           addNetPassword        = $("#locationPass");
    public SelenideElement           addNetStreet          = $("#addNetStreet");
    public SelenideElement           addNetCity            = $("#addNetCity");
    public SelenideElement           addNetState           = $("#addNetState");
    public SelenideElement           addNetZipcode         = $("#addNetZipcode");
    public SelenideElement           netCountryList        = $("#netCountryList");
    public SelenideElement           timeZone              = $("#timeZone");
    public SelenideElement           Next                  =  $x("//*[@id=\"divPrt25SwitchIpSett\"]/div/button[2]");
//            $x("(//button[text()=\"Next\"])[9]");
    
    public SelenideElement           defaultopt            = $x("//button[text()=\"No\"]");
    public SelenideElement           cancelbutton          = $x("//button[text()=\"Cancel\"]");
    public static SelenideElement    savebutton            = $x("//button[text()='" + WebportalParam.getLocText("Save") + "']");
    public SelenideElement           alert                 = $(".alert alert-danger alert-dismissable");
    public SelenideElement           firstlocation         = $x("//div[@class='locationDiv'][1]");
    public SelenideElement           firstLocationMoreIcon = $("#_ancliLocDiv0");
    public SelenideElement           firstLocationEditIcon = $x("//div[@id='_divlocDiv0']//a[@class='colorGray']");
    public SelenideElement           editname              = $("#editName");
    public SelenideElement           editpassword          = $("#editAdminPass");
    public SelenideElement           editzipcode           = $("#editZipCode");
    public SelenideElement           editcountry           = $("#editCountry");
    public SelenideElement           edittimezone          = $("#editTimeZone");
    public SelenideElement           firsttimezone         = $x("//*[@id=\"editTimeZone\"]/option[1]");
    public SelenideElement           secondtimezone        = $x("//*[@id=\"editTimeZone\"]/option[3]");
    public SelenideElement           editwirelessregion    = $("#editWirelessRegion");
    public SelenideElement           lockscreen    = $x("//*[@id=\"logoInsightPro\"]/a/img");
    public SelenideElement           changeregionconfirm   = $x("//button[text()='Yes']");
    public SelenideElement           editsave              = $("#buSaveBtn");
    public SelenideElement           yesbtn                = $x("//button[@class='btn btn-primary addDeviceBtn']");
    public SelenideElement           editcancel            = $("#aCancelBtn");
    public static SelenideElement    networksetup          = $x(
            "//a[@class='anchor'][contains(text(),'" + WebportalParam.getLocText("Network Setup") + "')]");
    
    public SelenideElement           addnetworksetupbtn    = $x("//span[@class='icon-add']");
    public static SelenideElement    VLANOK                = $x("//*[@id=\"myModal\"]/div/div/div[3]/button[2]");
    public static SelenideElement    cnfgbckuprstr         = $x(
            "//a[@class='anchor'][contains(text(),'" + WebportalParam.getLocText("Configuration Backup & Restore") + "')]");
    public SelenideElement           mainpage              = $x("//span[contains(@id,'logo')]/a/img");
    public SelenideElement           orgpage               = $x("//td[@id='_divColMdOrgDiv0']");
    public SelenideElement           headingtimezone       = $x("//*[@id='hTimeZoneText']");
    public SelenideElement           crtbckup              = $x("//span[@class='icon-add-2']");
    public SelenideElement           timezonevalue         = $x("//*[@id='editTimeZone']//option[@selected='']");
    public SelenideElement           networkname           = $x("//input[@name='networkName']");
    public SelenideElement           networkvlanname       = $x("//input[@name='vlanName']");
    public SelenideElement           bckupkname            = $x("//input[@name='name']");
    public SelenideElement           description           = $x("//input[@name='description']");
    public SelenideElement           crtbckupbtn           = $x("//button[contains(text(),'Create Backup Now')]");
    public SelenideElement           networkvlanid         = $x("//input[@name='vlanId']");
    public SelenideElement           nxtbtn                = $x("//button[@class='btn saveBtn']");
    public SelenideElement           skpbtn                = $x("//button[@class='btn cancelBtn' and text()='Skip']");
    public SelenideElement           ssid                  = $x("//input[@name='ssidName']");
    public SelenideElement           security              = $x("//select[@name='securityType']");
    public SelenideElement           password              = $x("//input[@name='password']");
    public SelenideElement           addnewssidwifi        = $x("//button[contains(text(),'Add New WiFi (SSID)')]");
    public SelenideElement           SSID80211wbtn         = $x("//*[@id='ssidForm']//h5[contains(text(),\"802.11w(PMF)\")]/parent::*//span");
    public SelenideElement           addbtn                = $x("//button[@class='btn saveBtn']");
    public SelenideElement           cnfrmbtn              = $x("//button[contains(text(),'Confirm')]");
    public static SelenideElement    crtbckupbutton        = $x("//button[text()='" + WebportalParam.getLocText("Create Backup") + "']");
    public SelenideElement           backupStatus          = $("#tdStatus0");
    public static SelenideElement    deleteBackupYes       = $x("//button[text()='" + WebportalParam.getLocText("Delete") + "']");
    public static SelenideElement    editBackupSave        = $x(
            "//h4[text()='" + WebportalParam.getLocText("Edit Backup") + "']/../..//button[text()='" + WebportalParam.getLocText("Save") + "']");
    public SelenideElement           editBackupName        = $("#backupName");
    public SelenideElement           editBackupDes         = $("#backupDescription");
    public SelenideElement           restoreBackupYes      = $x("//button[contains(@class,'addDeviceBtn')]");
    public SelenideElement        band24                   = $x("//input[@type=\"checkbox\"][@id=\"band_2\"]//../i");
    public SelenideElement        band5                    = $x("//input[@type=\"checkbox\"][@id=\"band_5\"]//../i");
    public SelenideElement        band6                    = $x("//input[@type=\"checkbox\"][@id=\"band_6\"]//../i");
//  public SelenideElement        checkband6                  = $x("//*[@id=\"band_6\"]");
    public SelenideElement        checkband6                  = $x("//*[@id=\"ssidForm\"]/div[2]/div/div/div[3]/div/div[2]/div[3]/div/label/p");
    public SelenideElement        GreenBanner              = $x("//*[@id=\"newLocationAlert\"]/div");
    public SelenideElement        locations                = $x("//*[@id=\"locationDivsContainer\"]/div[1]/div[1]/h3");
    public SelenideElement        editLocation             = $x("//*[@id=\"_ancliLocDiv0\"]");
    public SelenideElement        editLocationbtn          = $x("//b[text()='Edit location']");
    public SelenideElement        changePassword           = $x("//*[@id=\"editAdminPass\"]");
    public SelenideElement        viewPassword             = $x("(//*[@id=\"iEyeIcon\"])[2]");
    public SelenideElement        clkonSave                = $x("//*[@id=\"buSaveBtn\"]");
    public SelenideElement        successMsg               = $x("//*[@id=\"editNetworkMsg\"]/div/text()");
    public SelenideElement        clickOnHomePage          = $x("//*[@id=\"logoInsightPro\"]/a/img");

   
    
    public SelenideElement        netgearHomePage        = $x("//*[@id=\"logoInsightPro\"]");
    public SelenideElement        clickonOrgProAcc       = $x("//*[@id=\"_divorgDiv0\"]/div/span/div");
    public SelenideElement        clickonDelete          = $x("(//*[@id=\"62dff47ea7d921128bfcf031\"]/b)[2]");
    public SelenideElement        deletebtn              = $x("/html/body/div[2]/div/div[2]/div[3]/div[4]/div/div[1]/div[2]/div/div[1]/div/div[2]/div/ul/li/ul/li[2]/a/b");
   
    


    public SelenideElement lock = $x("//i[@class = 'fa fa-lock']");

    public SelenideElement maximumnumberdialog = $("div.in p");

    public SelenideElement locationName(String text) {
        waitReady();
//        if (!$("#_divColNoNetWork").exists() & !$x("//*[contains(text(), 'No Rows To Show')]").exists()) {
//            waitElement("//div[@col-id='locations' and contains(@class, 'ag-cell') and not(contains(@class, 'ag-header'))]");
//        }
//        SelenideElement location = $x("//div[@col-id='locations' and contains(@class, 'ag-cell') and not(contains(@class, 'ag-header'))]");
        MyCommonAPIs.sleepi(2);
        String rowindex=dropdownLocationElementNew(text).getAttribute("aria-rowindex");
        MyCommonAPIs.sleepi(2);
        SelenideElement location=Locationname(rowindex);
        System.out.println(location);
        return location;
    }

    public void editLocation() {
        waitReady();
//        $x("//span[text()='" + WebportalParam.location1 + "']/../../../ul/li/a").click();
//        $x("//span[text()='" + WebportalParam.location1 + "']/../../../ul/li/ul/li[1]").click();
        MyCommonAPIs.sleepi(2);
        String rowindex=dropdownLocationElementNew(WebportalParam.location1).getAttribute("aria-rowindex");
        MyCommonAPIs.sleepi(2);
        ariaSetIndex(rowindex).shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(2);
        ariaSetIndexEdit(rowindex).shouldBe(Condition.visible).click();
    }

    public void editNetwork(String Name) {
//        $x("//p[@title='" + Name + "']/../../ul/li/a").click();
//        if ($x("//p[@title='" + Name + "']/../../ul//b[text()='Edit location']/..").exists()) {
//            $x("//p[@title='" + Name + "']/../../ul//b[text()='Edit location']/..").click();
//        } else if ($x("//p[@title='" + Name + "']/../../ul//b[text()='Edit']/..").exists()) {
//            $x("//p[@title='" + Name + "']/../../ul//b[text()='Edit']/..").click();
//        }
        MyCommonAPIs.sleepi(2);
        String rowindex=dropdownLocationElementNew(Name).getAttribute("aria-rowindex");
        MyCommonAPIs.sleepi(2);
        ariaSetIndex(rowindex).shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(2);
        ariaSetIndexEdit(rowindex).shouldBe(Condition.visible).click();

    }

    public void setWirelessRegion(String country) {
        if (!editwirelessregion.getSelectedText().equals(country)) {
            editwirelessregion.selectOption(country);
            waitReady();
            if ($x("//div[@class=\"modal fade myModalChangeRegion in\"]").exists()) {
                changeregionconfirm.click();
            }
        }
    }

    public SelenideElement rstrconfg(String text) {
        // SelenideElement Ssid = $x("//p[@title='" + text + "']/../../td[10]//i[2]/img/ancestor::p");
        SelenideElement Ssid = $x("//span[contains(text(),'" + text + "')]/../../td[6]//i[1]");
        return Ssid;
    }
    
    public SelenideElement checkVLAN(String text) {
        SelenideElement VLAN = $x("//td[text()='" + text + "']");        
        return VLAN;
    }

    public SelenideElement Mandatory = $x("//p[text()='Mandatory']/.. ");

    public SelenideElement rstrconfgblock(String text) {
        SelenideElement Ssid = $x("//span[contains(text(),'" + text + "')]/../../td[6]//i[2]/img/ancestor::p");
        return Ssid;
    }

    public SelenideElement editConfg(String text) {
        SelenideElement ele = $x("//span[contains(text(),'" + text + "')]/../../td[6]//i[2]/img/ancestor::p");
        return ele;
    }

    public SelenideElement editconfg(String text) {
        SelenideElement Ssid = $x("//span[contains(text(),'" + text + "')]/../../td[6]//i[2]");
        return Ssid;
    }

    public SelenideElement delteconfg(String text) {
        SelenideElement Ssid = $x("//span[contains(text(),'" + text + "')]/../../td[6]//i[3]");
        return Ssid;
    }

    public void deleteLocation(String Name) {
        MyCommonAPIs.sleepi(15);
        SelenideElement deleteLocation3 = $x("//button[text()='Delete']");
        if( $x("//p[@title='" + Name + "']/../../ul/li/a").exists())
        {
        $x("//p[@title='" + Name + "']/../../ul/li/a").click();
        String deleteLocation1 = String.format("//p[@title='" + Name + "']/../../ul//b[text()='%s']/..",WebportalParam.getLocText("Delete location"));
        String deleteLocation2 = String.format("//p[@title='" + Name + "']/../../ul//b[text()='%s']/..", WebportalParam.getLocText("Delete"));
        
        SelenideElement deleteLocation4 = $x("//button[@class='btn btn-danger' and text()='Delete']");
        SelenideElement deleteLocation5 = $x("//b[text()='Delete']");
        SelenideElement deleteLocation6 = $x("//button[@class='btn btn-danger' and text()='Yes, delete location']");
        MyCommonAPIs.sleepi(35);
        if ($x(deleteLocation1).exists()) {
            $x(deleteLocation1).click();
        } else if ($x(deleteLocation2).exists()) {
            $x(deleteLocation2).click();
        } else if(deleteLocation3.exists()) {
            deleteLocation3.click();          
        } else if(deleteLocation4.exists()) {
            deleteLocation4.click();          
        } else if(deleteLocation5.exists()){
            deleteLocation5.click();
        } else {
            deleteLocation6.click();
        }
        MyCommonAPIs.sleepi(35);
        if(deleteLocation4.exists()) {
        waitElement(deleteLocation4);
        deleteLocation4.click();
        }
        if(deleteLocation6.exists()) {
            waitElement(deleteLocation6);
            deleteLocation6.click();
            }
        }
        else
        {
            String rowindex=dropdownLocationElementNew(Name).getAttribute("aria-rowindex");
            MyCommonAPIs.sleepi(2);
            ariaSetIndex(rowindex).shouldBe(Condition.visible).click();
            MyCommonAPIs.sleepi(2);
            ariaSetIndexDelete(rowindex).shouldBe(Condition.visible).click();
        }
        MyCommonAPIs.sleepi(10);
        if(deleteLocation3.shouldBe(Condition.visible).isDisplayed()) {
            deleteLocation3.shouldBe(Condition.visible).click();
        
    }
    }
    
    public SelenideElement        checkPassword               = $x("//input[@value='Netgear2@']");
    public SelenideElement        editLocationpro             = $x("//*[@id=\"_ancliLocDiv0\"]");
    public SelenideElement        editLocationbtnpro          = $x("//b[text()='Edit']");
    public SelenideElement        changePasswordpro           = $x("//*[@id=\"editAdminPass\"]");
    public SelenideElement        viewPasswordpro             = $x("(//i[@id='iEyeIcon'])[2]");
    public SelenideElement        clkonSavepro                = $x("//*[@id=\"buSaveBtn\"]");
    public SelenideElement        clickOnHomePagepro          = $x("//*[@id=\"logoInsightPro\"]/a/img");
    public SelenideElement        checkproPassword            = $x("//input[@value='Netgear2@']");   
    public SelenideElement        upgradetoProText            = $x("//p[text()='Upgrade to Pro']");
    public SelenideElement        accountType                 = $x("//label[text()='Account Type']");
    public SelenideElement        managedServiceProvider      = $x("//*[@id=\"divInlineRadioSubscription\"]/label[1]/p");
    public SelenideElement        businessName                = $x("//input[@id='businessName']");
    public SelenideElement        businessAddressName         = $x("//*[@id=\"_fldReg\"]/section[2]/label[2]/input");
    public SelenideElement        businessCity                = $x("//*[@id=\"_fldReg\"]/section[3]/label[2]/input");
    public SelenideElement        businessState               = $x("//*[@id=\"_fldReg\"]/div[2]/div[1]/section/label[2]/input");
    public SelenideElement        businesscityZipCode         = $x("//*[@id=\"_fldReg\"]/div[2]/div[2]/section/label[2]/input");
    public SelenideElement        businessCountry             = $x("//*[@id=\"_fldReg\"]/section[4]/label[2]/select");
    public SelenideElement        businessPhoneNumber         = $x("//*[@id=\"_fldReg\"]/section[5]/label[2]/input");
    public SelenideElement        businessUpgradeBtn          = $x("//button[@class='loginBtnSection fontSize16' and text()='Upgrade']");
    public SelenideElement        businessAccSuccessMsg       = $x("//p[@id='NoOrgHeadOneDiv']");
    public SelenideElement        verifyText1                 = $x("//div[contains(text(),'You have successfully added a location')]");
    public SelenideElement        verifyText2                 = $x("//div[contains(text(),'Continue to add device(s)')]");
    public SelenideElement        addDeviceBtn                = $x("//button[text()='Add Device']");
    public SelenideElement        addMultipleDevices          = $x("//button[text()='Add Multiple Device']");
    public SelenideElement        UpgradeToProLink            = $x("//a[text()=\"Upgrade to Pro Subscription\"]");
    public SelenideElement        UpgradeToProConfirmButton   = $x("//button[text()='Upgrade to Pro']");
    public SelenideElement        closeICOnLocation           = $x("//*[text()='Setup a New Network Location']/parent::div/button[@class='close']");
    public SelenideElement        locAddLogo                  = $x("//div[@id='divOnOffSettingNine']//input[@name='file']");
    public SelenideElement        LocHeaderIconUrl            = $x("//img[@id='headerLocImg']");
    public SelenideElement        wirelessRegion              = $x("//*[@id=\"editWirelessRegion\"]");
    public SelenideElement        radiusText                  = $x("//div[@class='leftMenuItems']/div/a[text()='RADIUS']");
    public SelenideElement        RadiusServerInput           = $x("//input[@id='primaryAdd']");
    public SelenideElement        radiusSwitchButton          = $x("//label[@id='_divTogOnOfSetRaLiBlColMdRowStRad']/span[@class='cstmSlider cstmRound']");
    public SelenideElement        RadiusSaveButton            = $x("//button[@id='_buSaveBtnTwo']");
    public SelenideElement        RadiusSuccessMsg            = $x("//div[@class='alert alert-success alert-dismissable']");
    public SelenideElement        locationPwd                 = $x("//input[@id='editAdminPass']");
    public SelenideElement        LocSaveButton               = $x("//button[@id='buSaveBtn']");
    public SelenideElement        locCount                    = $x("//div[@class='TableHeader headerindex1']/h3");
    
    

    public SelenideElement        radiusServerInput           = $x("//input[@id='primaryAdd']");
    public SelenideElement        radiusSaveButton            = $x("//button[text()='Save' and @id='_buSaveBtnTwo']");
    public SelenideElement        radiusSuccessMsg            = $x("//*[text()='Radius Settings configured successfully. It might take some time to reflect.']");    
    public SelenideElement        checkName                   = $x("//input[@value=\"OnBoardingTestNew\"]");
    public SelenideElement        radiusServerSecret          = $x("//input[@id='primarySecret']");
    public SelenideElement        editNetwork                 = $x("//img[@class='location-logo' and @id='headerLocImg']");
    public SelenideElement        addNetworkButton            = $x("//div[@id='_divAddIconAccnt']");
    
    public SelenideElement dropdownLocationElementNew(String name) {
        SelenideElement dropdownelementnew = $x("//p[text()='"+name+"']/../../..");
        return dropdownelementnew;
    }
    
    
    public SelenideElement Locationname(String name) {
        SelenideElement dropdownelementnew = $x("//div[@aria-rowindex='"+ name +"']/div[@col-id =\"locations\"]");
        return dropdownelementnew;
    }
    
    public SelenideElement ariaSetIndex(String index) {
        SelenideElement dropdownelementnew = $x("//div[@aria-rowindex='"+index+"']//div[@aria-colindex='7']/div/span");
        return dropdownelementnew;
    }
    
    public SelenideElement ariaSetIndexDelete(String index) {
        SelenideElement dropdownelementnew = $x("//div[@aria-rowindex='"+index+"']//li[text() = 'Delete']");
        return dropdownelementnew;
    }
    
    public SelenideElement ariaSetIndexEdit(String index) {
        SelenideElement dropdownelementnew = $x("//div[@aria-rowindex='"+index+"']//li[text() = 'Edit']");
        return dropdownelementnew;
    }
    
    public SelenideElement        nasIdentifier            = $x("//input[@id='nasIdentifierValue']");
    public SelenideElement        createNewLocationButton  = $x("//button[contains(text(),'new location')]");
}
