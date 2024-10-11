/**
 *
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;

import java.util.HashMap;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;

/**
 * @author zheli
 */
/**
 * @author Lenovo
 *
 */
public class DevicesDashPageElements extends MyCommonAPIs {
    public static SelenideElement addDevice1        = $("#_divAddDevLocDash");
    public SelenideElement rebootconfirm             = $x("//button[text()='Reboot']");
    public static SelenideElement addDevice         = $("[data-tooltip=\"" + WebportalParam.getLocText("Add Device") + "\"]");
    public static SelenideElement search            = $("[data-tooltip=\"" + WebportalParam.getLocText("Search") + "\"]");
    public SelenideElement        addDevice2        = $x("//div[@id='divLocBardevicesDash']/div[4]");
    public SelenideElement        addDevice3        = $x("//div[@id='divLocBardevicesDash']/div[6]");
    public static SelenideElement addSingleDevice   = $x("//b[text()='" + WebportalParam.getLocText("Add Single Device") + "']/..");
    public SelenideElement        addDeviceErrorMsg = $x("//div[@id='showErrorMsg']/div");
    public static SelenideElement viewDevices       = $x("//*[@id=\"btnSavCapPort\"]");
    public SelenideElement        viewDevices1      = $("#btnSavCapPort");
    public SelenideElement        MoreOptions       = $(".moreIcon backPurple");
    public SelenideElement        serialNo          = $("#serialNo");
    public SelenideElement        macAddress        = $("#macAddress");
    public SelenideElement        goDeviceBtn       = $x("//button[@class=\"btn btn-primary addDeviceBtn\"][text()=\"Go\"]");   //$(".in .addDeviceBtn");
    public SelenideElement        addDeviceBtn      = $(".in .addDeviceBtn"); //$x("//button[@class=\"renameDeviceBtn btn btn-primary\"][text()=\"Next\"]");    //$(".in .addDeviceBtn");
    public SelenideElement        viewDeviceBtn     = $x("//button[@class=\"btn saveBtn\"][text()=\"View Devices\"]");
    public SelenideElement        deviceName        = $("#deviceName");
    public SelenideElement        errorInfo         = $(".in .modal-body p");
    public static SelenideElement errorOK           = $(".in .modal-footer button");
    public static SelenideElement noDontDelete      = $(Selectors.byText(WebportalParam.getLocText("No, don't delete")));
    public static SelenideElement deleteConfirm     = $x("//button[text()=\"Delete\"]");
    public SelenideElement        showErrorMsg      = $("#showErrorMsg");
    public SelenideElement        addDeviceCancel   = $x("//*[@id=\"divMaindevicesDash\"]/div[3]/div/div/div[3]/button[1]");
    public SelenideElement        deviceStatus      = $("#spnCirDevIddevicesDash0");
    public SelenideElement        addagain          = $(".alert.alert-danger.alert-dismissable");
    public SelenideElement        nodevice          = $("td.dataTables_empty");
    public SelenideElement        btnConfirmEdit    = $(".input-group-btn .sendEditField");
    public SelenideElement        btnCancelEdit     = $(".input-group-btn .cancelEdit");
    public SelenideElement        gobtn             = $x("//*[@id=\"addDeviceModal\"]/div/div/div[3]/button[2]");
    public SelenideElement        nextbtn           = $x("//*[@id=\"addDeviceModal\"]/div/div/div[3]/button[2]");
    public SelenideElement        viewDevicesProacc = $x("//*[@id=\"btnSavCapPort\"]");
    // for airbridge
    public SelenideElement btnNextStep      = $(".in .cancelBtn");
    public SelenideElement btnAddMoreDevice = $(".in .btn-primary");
    
    public SelenideElement ShowErrorMsg           = $x("//*[@id=\"showErrorMsg\"]/div");
    public SelenideElement wiresslessDoesnotMatch = $x("//div[@class = 'alert alert-danger alert-dismissable']");
    
    public SelenideElement close = $x("//*[@id=\"addDeviceModal\"]/div/div/div[1]/button/img");
    
    public String          deviceTable            = "#deviceTable_wrapper tbody";
    public SelenideElement lnLearnMore            = $(".colorPurple.ManagerClTxt");
    public String          txtHowMeshAP           = "#main > div > div:nth-child(3) > div.modal.fade.connectViaMesh.in > div > div > div.modal-header > h4 > div";
    public SelenideElement btnHowMeshAPLeftArrow  = $(".connectViaMesh.in .icon-icon-arrow-previous");
    public SelenideElement btnHowMeshAPRightArrow = $(".connectViaMesh.in .icon-icon-arrow-next-3x");
    public SelenideElement btnCloseHowMeshAP      = $(".connectViaMesh.in button");
    
    String sDeleteDevice = "//td[text()='%s']/..//img[contains(@src, 'del')]";
    String sRebootDevice = "//td[text()='%s']/..//img[contains(@src, 'reboot')]";
    public String sDeviceMacViaSN = "//td[text()='%s']/../td[contains(@id, 'tdDevMacAddrdevicesDash')]";
    public String sDeviceIpViaSN = "//td[text()='%s']/../td[contains(@id, 'tdNASDevIddevicesDash')]";
    public SelenideElement        addDevice4        = $x("//div[@id='divLocBardevicesDash']/div[6]");
    
    public void xRebootDevice(String sSerialNo) {
        click($x(String.format(sRebootDevice, sSerialNo)), true);
    }
    
    public static HashMap<String, HashMap<String, String>> mapDeviceList = new HashMap<String, HashMap<String, String>>();
    
    /**
     * @param  sSerialNo
     * @param  sDevStatus
     *                    TODO
     * @param  sName
     * @param  sModel
     * @param  sIp
     * @return            true to okay updated
     */
    public boolean updateDeviceList(String sSerialNo, String sDevStatus, String sName, String sModel, String sIp) {
        HashMap<String, String> mapDeviceInfo = new HashMap<String, String>();
        mapDeviceInfo.put("Name", sName);
        mapDeviceInfo.put("Model", sModel);
        mapDeviceInfo.put("Ip", sIp);
        if (sIp.equals("NA") || !sDevStatus.equals("Connected")) {
            logger.info("skip to update this device due to lack info: " + sSerialNo);
            return false;
        }
        if (mapDeviceList.containsKey(sSerialNo)) {
            mapDeviceList.replace(sSerialNo, mapDeviceInfo);
        } else {
            mapDeviceList.put(sSerialNo, mapDeviceInfo);
        }
        logger.info(String.format("No.Dev: %s / Info: %s", mapDeviceList.size(), mapDeviceList.toString()));
        return true;
    }
    
    public int getDeviceCount() {
        return $$(String.format("%s tr", deviceTable)).size();
    }
    
    public void clickAddDevice() {
        MyCommonAPIs.sleepi(5);
        if (addDevice5.exists()) {
            addDevice5.click();
        }
        else if (addDevice4.exists()) {
            addDevice4.click();
        } else if (addDevice1.exists()) {
            addDevice1.click();
            MyCommonAPIs.sleepi(5);
        } else if(addDevice3.exists()){
            addDevice3.click();
            if (addSingleDevice.exists()) {
                addSingleDevice.click();
               
            }
            if (addSingleDevice.exists()) {
                addSingleDevice.click();
            }
        }
        if (addSingleDevice.exists()) {
            addSingleDevice.click();
        }
    }
    
//    Added by Vivek
    public void clickProAddDevice() {
        if (addDeviceProAccount.exists()) {
            addDeviceProAccount.click();
        }
        else {
            addDeviceProAccount.click();
            MyCommonAPIs.sleepi(5);
        }
            if (addSingleDevice.exists()) {
                addSingleDevice.click();
            }
    }
    
    
    
    public SelenideElement enterDeviceSummary(String serialNumber) {
        return $(Selectors.byText(serialNumber));
    }
    
    public SelenideElement editDevice(String text) {
        SelenideElement Device = $x("//i[1]/img[@data-deviceserial='" + text + "']/..");
        return Device;
    }
    
    public SelenideElement editModule(String text) {
        SelenideElement Device = $x("//img[@data-deviceserial='" + text + "']/ancestor::p");
        return Device;
    }
    
    public SelenideElement hoverDevice(String serialNumber) {
        SelenideElement Device = $x(String.format("//td[text()='%s']", serialNumber));
        return Device;
    }
    
    public SelenideElement deleteDevice(String text) {
        SelenideElement Device = $x("(//img[@data-deviceserial='" + text + "']/..)[1]");
        return Device;
    }
    
    public SelenideElement editDeviceNameModule(String serialNumber) {
        return $x("//img[@data-deviceserial='" + serialNumber + "']/ancestor::tr/td[1]");
    }
    
    public SelenideElement editDeiveNameIcon(String serialNumber) {
        return $x("//img[@data-deviceserial='" + serialNumber + "']/ancestor::tr/td[1]/p//i/img");
    }
    
    public SelenideElement editDeviceNameText(String serialNumber) {
        return $x("//img[@data-deviceserial='" + serialNumber + "']/ancestor::tr//input");
    }
    
    public SelenideElement editDeviceName = $x("//input[@class='deviceEditField']");
    
    public SelenideElement editDeviceName1(String serialNumber) {
        return $x("//*[@value='" + serialNumber + "']");
    }
    
    public SelenideElement editDeviceNameConfirm(String serialNumber) {
        return $x("//img[@data-deviceserial='" + serialNumber + "']/ancestor::tr//span[1]/button");
    }
    
    public SelenideElement btnCancelEditName = $("i[id*=icontddevicesDash]");
    
    public SelenideElement editDeviceNameCancel(String serialNumber) {
        return $x("//img[@data-deviceserial='" + serialNumber + "']/ancestor::tr//span[2]/button");
    }
    
    public SelenideElement devicesName(String serialNumber) {
        return $x("//img[@data-deviceserial='" + serialNumber + "']/ancestor::tr//p[1]/span");
    }
    
    public static int deviceserialNumberIndex = 99;
    public static int deviceIpIndex           = 9; //7;
    public static int deviceModelIndex        = 5;
    public static int deviceStatusIndex       = 2;
    public static int deviceUptimeIndex       = 8;
    public static int deviceMacIndex          = 11;
    public static int deviceUptimeIndex1       = 9; //8;
    public static int deviceUptimeIndex2       = 10;
    public static int deviceRFIndex           = 9;
    
    /**
     * need a way to know it's new page or not
     *
     * @param  columnIndex
     *                     0 - status, 1 - model, 2 - uptime, 3 - ip
     * @return
     */
    public int getFieldPos(int columnIndex) {
        logger.info("to: " + columnIndex);
        if (99 == deviceserialNumberIndex) {
            if ($$("#deviceTable tr:first-child td").size() == 1) {
                logger.info("No device is found, init next time");
                return 0;
            }
            deviceserialNumberIndex = 3;
            deviceStatusIndex = 2;
            if ($$("#deviceTable tr:first-child td").get(9).getAttribute("id").contains("tdUpTimedevicesDash")) {
                logger.info("it's old device table");
                deviceIpIndex = 8;
                deviceModelIndex = 5;
                deviceUptimeIndex = 10;
            } else {
                deviceIpIndex = 10;
                deviceModelIndex = 5;
                deviceUptimeIndex = 11;
            }
        }
        
        if (columnIndex == 0)
            return deviceStatusIndex;
        else if (columnIndex == 1)
            return deviceModelIndex;
        else if (columnIndex == 2)
            return deviceUptimeIndex;
        else if (columnIndex == 3)
            return deviceIpIndex;
        else
            throw new RuntimeException("error field");
        
    }
    
    public SelenideElement devicesModel(String serialNumber) {
        return $x("//img[@data-deviceserial='" + serialNumber + String.format("']/ancestor::tr/td[%s]", deviceModelIndex));
    }
    
    public SelenideElement devicesStatus(String serialNumber) {
        return $x("//img[@data-deviceserial='" + serialNumber + String.format("']/ancestor::tr/td[%s]", deviceStatusIndex));
    }
    
    public SelenideElement devicesStatusUnmanaged(String serialNumber) {
        System.out.println("3inn");
        return $x("//img[@data-deviceserial='" + serialNumber + String.format("']/ancestor::tr/td[%s]/label", deviceStatusIndex));
    }
    
    public SelenideElement devicesIP(String serialNumber) {
        System.out.println("<----------check device ip index--------------->");
        System.out.println(deviceIpIndex);
        return $x("//img[@data-deviceserial='" + serialNumber + String.format("']/ancestor::tr/td[%s]", deviceIpIndex));
    }
    
    public SelenideElement devicesUptime(String serialNumber) {
//        return $x("//img[@data-deviceserial='" + serialNumber + String.format("']/ancestor::tr/td[%s]", deviceUptimeIndex2));
        return $x("//img[@data-deviceserial='" + serialNumber + "']/ancestor::tr/td[contains(@id, 'tdUpTimedevicesDash')]");
    }
    
    public String sDeviceName     = "//span[contains(@id, 'pspnddevicesDash')]";
    public String sDeviceStatus   = "//td[contains(@id, 'tdDevStatusdevicesDash')]/span";
    public String sDeviceSerialNo = "//td[contains(@id, 'tdDevSerialIddevicesDash')]";
    public String sDeviceModel    = "//td[contains(@id, 'tdDevModlIddevicesDash')]";
    public String sDeviceFW       = "//td[contains(@id, 'tdFirmWreDevIddevicesDash')]";
    public String sDeviceIp       = "//td[contains(@id, 'tdNASDevIddevicesDash')]";
    public String sDeviceUptime   = "//td[contains(@id, 'tdUpTimedevicesDash')]";
    
    public SelenideElement filtericon        = $("#spanFilterIcondevicesDash");
    public SelenideElement filternasicon     = $("#lblNasdevicesDash");
    public SelenideElement filterapicon      = $("#lblAcessPointdevicesDash");
    public SelenideElement filterswitchicon  = $("#lblSwitchdevicesDash");
    public SelenideElement filterapplybutton = $("#btnApplyDevicesDash");
    public SelenideElement inputsearchtext   = $("#deviceSearchField");
    public SelenideElement sortTableName     = $("#th1NameDesvicesDash");
    // public SelenideElement onedevice = $("#tdDevModlIddevicesDash0");
    // public SelenideElement Twodevice = $("#tdDevModlIddevicesDash1");
    // public SelenideElement Threedevice = $("#tdDevModlIddevicesDash2");
    public SelenideElement DeviceCount = $("#_spnTotalDevliLocDiv0");
    public SelenideElement DeviceCountOrg = $x("//*[@id=\"_divorgDiv0\"]/div/div[1]/ul/li[3]");
    public SelenideElement deviceListfirstDevice    = $("#pspnddevicesDash0");
    public SelenideElement deviceListfirstdevice1   = $("#tdinpEditdevicesDash0");
    public SelenideElement connectedState           = $x("//span[@class='colorGreen deviceStatus']");
    public SelenideElement statasticsTab            = $x("(//a[text()='Statistics'])[1]");
    public SelenideElement verifySSID               = $x("(//tr/td/text())[1]");
    public SelenideElement clearStatsBtn            = $x("//button[text()='Clear Stats']");
    public SelenideElement broadcastAndMulticast    = $x("(//p[text()='0'])[1]");
    public SelenideElement unicast                  = $x("(//p[text()='0'])[2]");


    public SelenideElement selectLocation = $("#locationdropdown");
    
 // Code Added for Seach filter Choose Devices by Pratik
    public SelenideElement devicesFilter              = $x("//span[@id='spanFilterIcondevicesDash']");
    public SelenideElement filterDeviceVerification   = $x("//span[text()='Device']");
    public SelenideElement selectAccessPoint          = $x("//label[@id='lblAcessPointdevicesDash']");
    public SelenideElement selectNAS                  = $x("//label[@id='lblNasdevicesDash']");
    public SelenideElement selectOrbiPro              = $x("//label[@id='lblOrbidevicesDash']");
    public SelenideElement selectSwitch               = $x("//label[@id='lblSwitchdevicesDash']");
    public SelenideElement selectBusinessRouter       = $x("//label[@id='lblBrdevicesDash']");
    public SelenideElement selectMeshAP               = $x("//label[@id='lblMeshdevicesDash']");
    public SelenideElement selectAirBridge            = $x("//label[@id='lblairBridgedevicesDash']");
    public SelenideElement searchFilterApplyButton    = $x("//button[@id='btnApplyDevicesDash']");
    public SelenideElement coommanDeviceParameter     = $x("//tr[contains(@id,'trtbdydevicesDash')]");
    public SelenideElement businessRouter             = $x("//tr[contains(@id,'trtbdydevicesDash')]/td[text()='BR500']");
    public SelenideElement orbi                       = $x("//tr[contains(@id,'trtbdydevicesDash')]/td[text()='Orbi']");
    public SelenideElement switch1                    = $x("//tr[contains(@id,'trtbdydevicesDash')]/td[text()='Switch']");
    public SelenideElement ap                         = $x("//tr[contains(@id,'trtbdydevicesDash')]/td[text()='Access Point']");
    public SelenideElement noDeviceAvailable          = $x("//td[text()='No device available']");
    public SelenideElement addDeviceProAccount        = $x("//div[@id='divLocBardevicesDash']/div/span[@class='icon-add dropdown-toggle']");
    public SelenideElement lastKnownInfo              = $x("//p[text()='Last known information']");
    public SelenideElement lastKnownInfostarsym       = $x("//span[text()='*' and @class='colorRed']");
    public SelenideElement upTimedeviceDashVerify     = $x("//td[@id='tdUpTimedevicesDash0']");
    public SelenideElement upTimedeviceDashVerify1    = $x("//p[contains(text(),'Days')]");
    public SelenideElement firstLocation              = $x("//div[@class='locationDiv']");
    public SelenideElement addDevice5                 = $x("//div[@data-tooltip='Add Device']");
    public SelenideElement        orgAddDevice                     = $x("//div[@data-tooltip='Add Device']");
    public SelenideElement        orgAddSingleDevice               = $x("//b[text()='Add Single Device']");
    public SelenideElement        orgAddMultipleDevice             = $x("//b[text()='Add Multiple Devices']");
    public SelenideElement addDeviceNextButtton       = $x("//button[text()='Next' and @class='renameDeviceBtn btn btn-primary']");
    
    
//    added by tejeshwini 
    
    public SelenideElement divDeviceListCSV       = $x("//*[@id=\"divDeviceListCSV\"]/span");
    public SelenideElement previous               = $x("//*[@id=\"deviceTable_previous\"]/a");
    public SelenideElement firstpage              = $x("//*[@id=\"deviceTable_paginate\"]/ul/li[2]/a");
    public SelenideElement Secondpage             = $x("//*[@id=\"deviceTable_paginate\"]/ul/li[3]/a");
    public SelenideElement Next                   = $x("//*[@id=\"deviceTable_next\"]/a");
    public SelenideElement NoofDevices            = $x("//*[@id=\"artSucsMsgdevicesDash\"]/div[3]/div[1]/h3");
    public SelenideElement DevicesSearch          = $x("//*[@id=\"ancSearchIcondevicesDash\"]/span");
    public SelenideElement SendSearch             = $x("//*[@id=\"deviceSearchField\"]");
    public SelenideElement ClickSearch            = $x("//*[@id=\"btnApplySrchOrg\"]");
    public SelenideElement Selectfirstpage        = $x("//*[@id=\"deviceTable_paginate\"]/ul/li[2][@class =\"paginate_button active\"]");
    public SelenideElement SelectSecondpage       = $x("//*[@id=\"deviceTable_paginate\"]/ul/li[3][@class =\"paginate_button active\"]");
    public SelenideElement  next      = $x("//*[@id=\"addDeviceModal\"]/div/div/div[3]/button[2]");
    
    public SelenideElement devicesStatus1(String serialNumber) { 
        return $x("//p[@data-title='" + serialNumber + String.format("']/ancestor::tr/td[%s]", deviceStatusIndex));
    }
    
    
    public SelenideElement devicesUptime1(String serialNumber) {   
//      return $x("//img[@data-deviceserial='" + serialNumber + String.format("']/ancestor::tr/td[%s]", deviceUptimeIndex2));
      return $x("//p[@data-title='" + serialNumber + "']/ancestor::tr/td[contains(@id, 'tdUpTimedevicesDash')]");
  }
    
    
    public SelenideElement moveDevice                 = $x("//img[@src='assets/img/commonIcons/move-device-icon.png']");
    public SelenideElement yesButton                  = $x("//button[text()='Yes' and @data-testid='yesButton']");
    public SelenideElement selectOrg                  = $x("//div[text()='Netgear']");
    public SelenideElement selectSecondOrg            = $x("//ul[@class='list-unstyled fontSize16']/li[2]");
    public SelenideElement moveButton                 = $x("//button[text()='Move']");
    public SelenideElement successMsg                 = $x("//a[@class='customeSuccessClose']");
    public SelenideElement selectloc                  = $x("//div[text()='office1']");
    public SelenideElement deviceDashboardClientNo    = $x("//td[@id='tdDevConStusdevicesDash0' and text()='1']");
    public SelenideElement NoofDevicesWireless    = $x("//*[@id=\"hDevDevList\"]");
    public SelenideElement NoofDevicesWired       = $x("//*[@id=\"hDeviceswiredQuickView\"]");
    public SelenideElement selectSecondloc            = $x("//li[text()='office2']");
    public SelenideElement moveDevice1                = $x("(//img[@src='assets/img/commonIcons/move-device-icon.png'])[2]");
    public SelenideElement selectLocationfromAnotherOrg             = $x("//span[text()='office1']");
    public SelenideElement noDevicesAvailable         = $x("//td[text()='No device available']");
    public SelenideElement enterIntoAP                = $x("//span[text()='Connected']");
    public SelenideElement statasticsRow              = $x("//table[contains(@class,'table table-hover client-list-')]/tbody/tr/td[text()='1']");
    public SelenideElement statastcsClientsAfterClearStats           = $x("//td[text()='0']");
    public SelenideElement broadcastAndMulticastZeroData             = $x("(//p[text()='0' and text()=' GB'])[1]");
    public SelenideElement unicastZeroData            = $x("(//p[text()='0' and text()=' GB'])[2]");
    public SelenideElement deleteButtonConfm      = $x("//button[text()='Delete']");     
    public SelenideElement RefreshButton          = $x("//*[@id=\"divLocBardevicesDash\"]/div[5]/span");
    public SelenideElement DeviceStatusemt       = $x("//tr[@title='Double click to edit']/td[contains(@id,'tdDevStatusdevices')]");
    public SelenideElement locImg                = $x("//span[@class='location-pic']");
    public SelenideElement AdminPwdTxt           = $x("//p[@class='d-flex align-items-center']/span[text()='Admin Password']");
    public SelenideElement eyeHideShowIcon       = $x("//p[@class='d-flex align-items-center']//i[@id='iEyeIcon']");
    public SelenideElement passwordData          = $x("//p[@class='d-flex align-items-center']//input[@name='password']");
    public SelenideElement device                = $x("//li/a/span[text()='Devices']");
    public SelenideElement previousOrg            = $x("//*[@id=\"deviceTableLoc_previous\"]/a");
    public SelenideElement clickOnLocationDropdown = $x("(//div[@class='selectedValue'])[2]");
    public SelenideElement Selectlocation1         = $x("//li[text()='office1']");

    //addedByPratikForSettingsMyDevices
    public SelenideElement settingsIconOnHomePage    = $x("//img[@src='assets/img/Header/icon-settings.png']");
    public SelenideElement myDevicesoption           = $x("//a[text()='My Devices']");
    public SelenideElement alldevices4Number         = $x("//strong[text()='4']");
    public SelenideElement oneLocation               = $x("//strong[text()='1']");
    public SelenideElement settingPagetext           = $x("//*[text()=\"Devices are available on\"]");
    public SelenideElement locationsText             = $x("(//*[text()=\"Locations\"])[2]");
    public SelenideElement office1Text               = $x("(//span[text()='office1'])[3]");
    public SelenideElement plusIconMyDevices         = $x("//i[@id='iconPlusNwDevMyDevices0']");
    public SelenideElement plusIconMyAllNetgearDev   = $x("//i[@id='iconPlusHeadMyDevices']");
    public SelenideElement npDataAvailable           = $x("//tbody[@id='othersTableBody']/tr//td[text()='No Data Available']");
    public SelenideElement settingsPageFilter        = $x("//span[@class='icon-filter']");
    public SelenideElement settingsPageFilterAP      = $x("//a[text()='Access Point']");
    public SelenideElement settingsPageFilterSwitch  = $x("//a[text()='Switch']");
    public SelenideElement settingsPageFilterOrbi    = $x("//a[text()='Orbi']");
    public SelenideElement settingsPageFilterBR500   = $x("//a[text()='Business Router']");
    public SelenideElement settingsPageFilrAirbridge = $x("//a[text()='AirBridge']");
    public SelenideElement settingsfilterLocationver = $x("(//span[text()='Location'])[1]");
    public SelenideElement settingsfilteroffice1loc  = $x("//a[text()='office1']");
    public SelenideElement settingsFilterApplyBtn    = $x("//button[text()='Apply']");
    public SelenideElement settingsFilterOrbi        = $x("//h5[text()='SRR60']");
    public SelenideElement settingsFilterAPWAC       = $x("//h5[contains(text(),'WAC')]");
    public SelenideElement settingsFilterAPWAX       = $x("//h5[contains(text(),'WAX')]");
    public SelenideElement settingsFilterSwitch      = $x("//h5[contains(text(),'GC')]");
    public SelenideElement settingsFilterBR500       = $x("//h5[contains(text(),'BR')]");
    public SelenideElement settingsFilterNoDevices   = $x("//td[text()='No Device Available']");
    public SelenideElement premiumAccGoToMainPage    = $x("(//img[@alt='Cloud Web Portal' and @src='assets/img/netgear-insight-logo.png'])[1]");
    public SelenideElement settingsfilterSerialNoverify(String serialNumber) { 
        return $x("//h5[text()='" + serialNumber + "']");
  }
    
    public SelenideElement searchClick   = $("#btnApplySrchOrg");  
    public SelenideElement selectLocationDropdown = $x("(//div[@class='selectedValue'])[2]");
    
    public SelenideElement AssignRFProfile     = $x("//*[text()=\"Assign RF Profile\"]");  
    public SelenideElement UnassignRFProfile   = $x("//*[text()=\"Unassign RF Profile\"]");  
    public SelenideElement SelectRF           = $x("//*[text()=\"RF Profile\"]/../select");
    public SelenideElement SaveRF             = $x("//*[text()=\"RF Profile\"]/../../../../../div[4]/button[2]");
    


    //addedByPratik
    public SelenideElement deviceDashPagenumberofConnectedClients = $x("//td[@id='tdDevConStusdevicesDash0' and text()='1']");
    public SelenideElement managedToggleSwitchinOnstate           = $x("//input[@type='checkbox' and @checked]");
    public SelenideElement unmanagedStatusAP2                     = $x("//label[text()='Unmanaged']");
    

    public SelenideElement SelectDevice(String serialNumber) {
        if($x("//td[text()='" + serialNumber + "']/../td/small/label/i").isDisplayed()) {
            return $x("//td[text()='" + serialNumber + "']/../td/small/label/i");
        }else {
            return $x("//td[text()='" + serialNumber + "']/../td/p/small/label");
        }
    }
    

    
    
    public SelenideElement devicesRF(String serialNumber) {
        System.out.println("<----------check device RF --------------->");
        System.out.println(deviceRFIndex);
        return $x("//img[@data-deviceserial='" + serialNumber + String.format("']/ancestor::tr/td[%s]", deviceRFIndex));
    }
    //addedbyPratik
    public SelenideElement moveMultipleDevicesfromOneLocation(String serialNumber) {
        return $x("//td[text()='"+serialNumber+"']/..//img[@src='assets/img/commonIcons/move-device-icon.png']");
    }
    

    public SelenideElement delteDevicesfromOneLocation(String serialNumber) {
        return $x("//td[text()='"+serialNumber+"']/..//img[@class='deleteDeviceIcon']");
    }
    
    public SelenideElement dropdownclick                 = $x("/html/body/form/table/tbody/tr/td[2]/select");
    public SelenideElement submit                 = $x("//input[@type = 'submit']");
    public SelenideElement sucessmessage                 = $x("//center[2]");

}