package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import java.util.logging.Logger;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
public class DeviceBulkOnboardingElement extends MyCommonAPIs{
    Logger logger = Logger.getLogger("DeviceBulkOnboardingElement");
    public static SelenideElement    addDeviceButton    = $x("//span[@class=\"icon-add dropdown-toggle\"]");
    public static SelenideElement    changeaddDeviceButton    = $x("//*[@id=\"_divLocOptLocDash\"]/div[1]/span");
    public SelenideElement           addSingleDevice    = $x("//*[@id=\"divLocBardevicesDash\"]/div[4]/ul/li[1]/a/b");
    public SelenideElement           addMultiDevice     = $x("//b[text() = 'Add Multiple Devices']");
    public SelenideElement           addMultiDevicechange     = $x("//b[text()='Add Multiple Devices']");
    public SelenideElement           addSinglelocation     = $x("//a[@class=\"colorGray\"]/b[contains( text(),'Add Single Device')]");
    public SelenideElement           browseButton       =$("#uploadFile");
    public SelenideElement           sendserial       =$x("//*[@id=\"serialNo\"]");
    public SelenideElement        goDeviceBtn       = $x("//button[@class=\"btn btn-primary addDeviceBtn\"][text()=\"Go\"]");   //$(".in .addDeviceBtn");
    public SelenideElement        macAddress        = $("#macAddress");
    public SelenideElement        addDeviceBtn      = $(".in .addDeviceBtn"); 
    public static SelenideElement errorOK           = $(".in .modal-footer button");
    public SelenideElement        viewDevices1      = $("#btnSavCapPort");
    public SelenideElement ShowErrorMsg           = $x("//*[@id=\"showErrorMsg\"]/div");
    public SelenideElement close = $x("//*[@id=\"addDeviceModal\"]/div/div/div[1]/button/img");
    public SelenideElement           warningOfWrongformat = $x("//*[@id=\"showErrorDivBulkAdd\"]/div");
    public SelenideElement           closewarning = $x("//*[@id=\"_divMainLocDash\"]/div[2]/div/div/div/div[1]/button");
    public SelenideElement           clocewarning = $x("//*[@id=\"content\"]/div[1]/div/div/div/div[1]/button/img");
    public SelenideElement           continueButton = $x("//*[@id=\"_divMainLocDash\"]/div[2]/div[2]/div/div/div[3]/button[1]");
    public SelenideElement           errorMassege = $x("//*[@id=\"content\"]/div[4]/div/div[1]/div/div/div[1]/table/tbody/tr[1]/td[4]/div/span");
    public SelenideElement           clickhereButton       =$x("//*[@id=\"_divMainLocDash\"]/div[2]/div[1]/div/div/div[4]/div[1]/div[2]/div[2]/h5/a");
    public SelenideElement           updatelocationButton = $x("//button[text()='Update Devices List']");
    public SelenideElement           selectAll  = $x("//*[@id='multipleAll']/..//i");
    public SelenideElement           selectAll1  = $x("//*[@id=\"multipleAll\"]/../i");
    public SelenideElement           checkAll  = $("//*[@id=\"content\"]/div[6]/div/div[1]/div/div/div[1]/div/table/thead/tr/th[1]/div/span/label/i");
    public SelenideElement           updatedevicelistButton = $x("//*[@id=\"content\"]/div[4]/div/div[2]/button[2]");
    public SelenideElement           errorMassageofworngname = $x("//*[@id=\"content\"]/div[6]/div/div[1]/div/div/div[1]/div/table/tbody/tr[1]/td[4]/div/span");
    public SelenideElement           errorMassage = $x("//*[@id=\"content\"]/div[6]/div/div[1]/div/div/div[1]/div/table/tbody/tr[1]/td[5]/div/span");
    public SelenideElement           warningoferrormessage = $x("//*[@id=\"content\"]/div[3]/div/div[1]/p");
    public SelenideElement           Viewdevice = $x("//button[text()='View Devices']");
    public SelenideElement           Uploaddevice = $x("//*[@id=\"content\"]/div[6]/div/div[2]/button[2]");
    public SelenideElement           SelectALL = $x("//*[@id=\"content\"]/div[5]/div/div[1]/div/div/div[1]/div/table/thead/tr/th[1]/div/span/label/i");
    public SelenideElement           upload = $x("//*[@id=\"content\"]/div[5]/div/div[2]/button[2]");
    
    
    public SelenideElement           OrgSettings        = $x("//*[@id=\"settingsView\"]");
    public SelenideElement           Orgdevice          = $x("(//a[contains( text(),'Devices')])[2]");
    public SelenideElement           OrgAddButton       = $x("//*[@id=\"_divAddOrgIcon\"]/span");
    public SelenideElement           OrgUpload         = $x("//*[@id=\"uploadFile\"]");
    public SelenideElement           updateDevicelist   = $x("//*[@id=\"content\"]/div[6]/div/div[2]/button[2]");
    
    
    public SelenideElement           SummaryAdd                = $x("//div[@data-tooltip='Add Device']");
    public SelenideElement           SummaryMultiple           = $x("//b[text()='Add Multiple Devices']");
     
    public SelenideElement           RouterClick              = $x("//*[@id=\"divMaindevicesDash\"]/div[1]/ul/li[3]/a");
    public SelenideElement           RouterAdd                 = $x("//*[@id=\"_righticon\"]/div[1]/span");
    public SelenideElement           RouterMultiple            = $x("//*[@id=\"_righticon\"]/div[1]/ul/li[2]/a/b");
    
    
    public SelenideElement           MobileAdd                 = $x("//*[@id=\"content\"]/div[2]/div/div[1]/span");
    public SelenideElement           MobileMultiple            = $x("//*[@id=\"content\"]/div[2]/div/div[1]/ul/li[2]/a/b");
    
    public SelenideElement           WirelessAdd                 = $x("//*[@id=\"divLocBarDevList\"]/div[1]/span");
    public SelenideElement           WirelessMultiple            = $x("//*[@id=\"divLocBarDevList\"]/div[1]/ul/li[2]/a/b");
    
    
    public SelenideElement           WiredAdd                 = $x("//*[@id=\"divTblheadwiredQuickView\"]/div[1]/span");
    public SelenideElement           WiredMultiple            = $x("//*[@id=\"divTblheadwiredQuickView\"]/div[1]/ul/li[2]/a/b");
    
    public SelenideElement           StorageAdd                 = $x("//*[@id=\"_righticon\"]/div[1]/span");
    public SelenideElement           StorageMultiple            = $x("//*[@id=\"_righticon\"]/div[1]/ul/li[2]/a/b");
    
    
    public SelenideElement           DeviceName                = $x("//*[@id=\"deviceName\"]");
    public SelenideElement           DeviceName2                = $x("(//*[@id=\"deviceName\"])[2]");
    public SelenideElement           Locationdevice                = $x("//*[@class=\"icon-bulkonboard-add dropdown-toggle\"]");
    public SelenideElement           selctlocation                = $x("//*[@id=\"locationdropdown\"]");
    public SelenideElement           Go                         = $x("//*[@id=\"addDeviceModal\"]/div/div/div[3]/button[2]");
    public SelenideElement           Next                         = $x("//*[@id=\"addDeviceModal\"]/div/div/div[3]/button[2]");
    public SelenideElement        AddMultipleDevicebanner      = $x("//*[@id=\"newLocationAlert\"]/div/div[2]/div[1]/button[2]");
    public SelenideElement        BannerSingle                  = $x("//*[@id=\"newLocationAlert\"]/div/div[2]/div[1]/button[1]");
    public SelenideElement        browseButtonOrg               = $x("//div[contains(@class,'addMultipleDevice in')]//input[@id='uploadFile']");
    public SelenideElement        routerPageTab                 = $x("//a[@href='#/routers/quickview']//span[text()='Router/VPN']");
    public SelenideElement        routersAddDevicesIcon         = $x("//span[@class='icon-add-2 dropdown-toggle']");
    public SelenideElement        addMultipleDevices            = $x("//b[text()='Add Multiple Devices']");
    public SelenideElement        wirelessPageTab               = $x("//a[@href='#/wireless/wirelessQuickView' and text()='Wireless']");
    public SelenideElement        wirelessAdddevicesIcon        = $x("//span[@class='icon-add dropdown-toggle']");
    public SelenideElement        wiredPageTab                  = $x("//a[@href='/#/wired/quickView' and text()='Wired']"); 
    public SelenideElement        wiredAdddevicesIcon           = $x("//span[contains(@class,'icon-add dropdown-toggle')]");
    public SelenideElement        devicesPageTab                = $x("//a[@href='/#/devices/dash']//span[text()='Devices' and @class='hideWhenVertical']"); 
    public SelenideElement        devicesAdddevicesIcon         = $x("//span[contains(@class,'icon-add dropdown-toggle')]");
    public SelenideElement        orgLvlAddDevicesIcon          = $x("//span[@class='icon-bulkonboard-add dropdown-toggle']");
    public SelenideElement        locatioNotmatcherror          = $x("(//span[text()='Location does not match.'])[1]");
    public SelenideElement        locatioNotmatcherror1         = $x("(//span[text()='Location does not match.'])[2]");
    public SelenideElement        locatioEmptyError             = $x("(//span[text()='Location does not match.'])[2]");
    public SelenideElement        browseButtonOrg1              = $x("(//input[@id='uploadFile'])[2]");
    
    //addedbyPratikfor bulk onboarding priqa
    
    public SelenideElement        wirelessAddButton                         = $x("//*[@id=\"divLocBarDevList\"]/div[2]/span");
    public SelenideElement        wirelessPageaddMultipleDevices            = $x("//*[@id=\"divLocBarDevList\"]/div[2]/ul/li[2]/a/b");
    public SelenideElement        closeWarningBulkOnboarding                = $x("(//*[@id=\"content\"]/div/div/div/div/div/button)[1]");
    public SelenideElement        orgSettingsAddDevicesButton               = $x("//*[@id=\"content\"]/div[4]/div/div[2]/span");
    public SelenideElement        devicelist1                                = $x("//*[@id=\"deviceTable_paginate\"]/ul/li[2]/a[text()='1']");
    public SelenideElement        devicelist2                                = $x("//*[@id=\"deviceTable_paginate\"]/ul/li[3]/a[text()='2']");
   
    public SelenideElement           OrgUpload1                = $x("(//*[@id=\"uploadFile\"])[2]");
    public SelenideElement           OrgAdd                    = $x("//span[@class=\"icon-bulkonboard-add dropdown-toggle\"]");
    public SelenideElement           AddMultiple               = $x("//b[text()=\"Add Multiple Devices\"]");
    //Added by Anusha for pro bulk onboarding
    public SelenideElement           Remove               = $x("//*[@id=\"content\"]/div[6]/div/div[2]/button[1]");
    public SelenideElement           errorMessageBulkOnboard   = $x("(//*[@id='content']//span[@class='WarnIcon']/..)");
}
