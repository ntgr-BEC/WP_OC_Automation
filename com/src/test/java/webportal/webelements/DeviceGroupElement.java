/**
 *
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import org.apache.log4j.Logger;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.WiredPoESchedulesPage;
import webportal.weboperation.WiredVLANPage;

/**
 * @author zheli
 */
public class DeviceGroupElement extends MyCommonAPIs {
    Logger logger = Logger.getLogger("DeviceGroupElement");

   
    public static SelenideElement DeviceGroup                         = $x("//*[@href=\"#/network/deviceGroups\"]");
    public static SelenideElement SysLog                              = $x("(//*[@href='#/network/SyslogConfiguration'])[1]");
    public static SelenideElement LedSettings                         = $x("//*[@href=\"#/network/LedSettings\"]");
    public static SelenideElement SNMP                                = $x("//*[@href=\"#/network/snmp\"]");
    public static SelenideElement CreateDeviceGroup                   = $x("//button[text()=\"Create Device Group\"]");
    public static SelenideElement AddDeviceGroup                      = $x("//*[@id=\"divConRowWirSett\"]/div[2]/div/div[1]/div/div/div/span");
    public static SelenideElement AddDeviceGroupName                  = $x("//*[@id=\"addCustomGroup\"]/div[2]/input");
    public static SelenideElement AddDeviceGroupDescription           = $x("//*[@id=\"addCustomGroup\"]/div[3]/textarea");
    public static SelenideElement closeDeviceGroup                    = $x("//*[@id=\"divMainWirSett\"]/div[3]/div/div/div[1]/button/img");
    public static SelenideElement CreateDeviceGroupbutton             = $x("//button[text()='Create']");
    public static SelenideElement CancelDeviceGroupbutton             = $x("//button[text()='Create']/..//button[text()='Cancel']");
    public static SelenideElement errorText                           = $x("//*[@id=\"errorMessage\"]/div");
    public static SelenideElement Sucessmessage                       = $x("//*[@id=\"customGroupTableNotif\"]/div");
    public static SelenideElement CGName                              = $x("//*[@id=\"customGroupsTable\"]/thead/tr/th[1]");
    public static SelenideElement CGDescription                       = $x("//*[@id=\"customGroupsTable\"]/thead/tr/th[2]");
    public static SelenideElement CGDevices                           = $x("//*[@id=\"customGroupsTable\"]/thead/tr/th[3]/p");
    public static SelenideElement CGOnline                            = $x("//*[@id=\"customGroupsTable\"]/thead/tr/th[3]/ul/li[2]");
    public static SelenideElement CGonboarded                         = $x("//*[@id=\"customGroupsTable\"]/thead/tr/th[3]/ul/li[1]");
    public SelenideElement        deletessidyes                       = $x("//button[text()=\"Delete\"]");
    public SelenideElement        CGexits                             = $x("//*[@id=\"customGroupsTable\"]/tbody/tr[1]");
    public SelenideElement        grabCGName                          = $x("//*[@id=\"customGroupsTable\"]/tbody/tr[1]/td[1]");
    public SelenideElement        addDevice2                          = $x("//div[@id='divLocBardevicesDash']/div[6]");
    public static SelenideElement addDevice1                          = $x("//*[@id=\"content\"]/div[1]/div/div[2]");
    public static SelenideElement addSingleDevice                     = $x("//b[text()='" + WebportalParam.getLocText("Add Single Device") + "']");
    public SelenideElement        goDeviceBtn                         = $x("//button[@class=\"btn btn-primary addDeviceBtn\"][text()=\"Go\"]");   //$(".in .addDeviceBtn");
    public SelenideElement        serialNo                            = $("#serialNo");
    public SelenideElement        DGGroup                             = $x("//*[@id=\"customGroupDropDown\"]");
    public SelenideElement        macAddress                          = $("#macAddress");
    public static SelenideElement viewDevices                         = $x("//*[@id=\"btnSavCapPort\"]");
    public static SelenideElement DGDevicelist                        = $x("//a[text()=\"Device List\"]");
    public static SelenideElement DGWiFi                              = $x("//a[text()=\"WiFi\"]");
    public static SelenideElement DGGeneral                           = $x("//a[text()=\"General\"]");
    public static SelenideElement DGProperties                        = $x("//a[text()=\"Properties\"]");
    public static SelenideElement DGDeviceList                        = $x("//a[text()=\"Device List\"]");
    public static SelenideElement DGWiFiSchedules                     = $x("//a[text()=\"WiFi Schedules\"]");
    public static SelenideElement DGFastRoaming                       = $x("//a[text()=\"Fast Roaming\"]");
    public static SelenideElement DGLoadBalancing                     = $x("//a[text()=\"Load Balancing\"]");
    public static SelenideElement DGVLANNetworkSetup                  = $x("//a[text()=\"VLAN/Network Setup\"]");
    public static SelenideElement DGAdvanced                          = $x("//a[text()=\"Advanced\"]");
    public static SelenideElement enbleSysLog                         = $x("//*[@id=\"toggleSyslogConfig\"]/../span");
    public static SelenideElement SysLogIP                            = $x("//*[@id=\"ipPortConfig\"]/div[1]/input");
    public static SelenideElement SysLogPort                          = $x("//*[@id=\"ipPortConfig\"]/div[2]/input");
    public static SelenideElement Apply                               = $x("//button[text() ='Apply']");
    public static SelenideElement Ok                                  = $x("//*[text()='Yes, Continue']");
    public static SelenideElement SetLed                              = $x("//*[@id=\"_divColMdThree\"]/div[6]/div/div/div/div/div/div/div[1]");
    public static SelenideElement SetDefault                          = $x("//div[text() = 'Default']");
    public static SelenideElement SetOff                              = $x("//div[text() = 'Off']");
    public static SelenideElement SetExcept                           = $x("//div[text() = 'Off Except Power LED']");
    public static SelenideElement enbleSNMP                           = $x("//*[@id=\"snmpForm\"]/div/div[2]/label/span");
    public static SelenideElement SNMPIP                              = $x("//*[@id=\"snmpForm\"]/div/div[3]/input");
    public static SelenideElement SNMPCString                         = $x("//*[@id=\"snmpForm\"]/div/div[4]/input");
    public static SelenideElement OKSNMP                              = $x("//p[text() = 'Settings Applied Successfully']/../../div[3]/button");
    public static SelenideElement ClearSNMP                           = $x("(//button[text() = 'Clear'])");
    public static SelenideElement ClearSNMPagain                      = $x("(//button[text() = 'Clear'])[2]");
    public static SelenideElement SaveSNMP                            = $x("(//button[text() ='Save'])[2]");
    public static SelenideElement EditDGGeneral                       = $x("//*[@id=\"updateCustomGroup1\"]/div[3]/textarea");
    public static SelenideElement EditDGGeneralName                   = $x("//*[@id=\"updateCustomGroup1\"]/div[2]/input");
    public static SelenideElement TotalAP                             = $x("//p[text() = 'Total AP']/../h3");
    public static SelenideElement Properties                          = $x("//h2[text() = 'Properties']");
    public static SelenideElement SystemStatus                        = $x("//h2[text() = 'System Status']");
    public static SelenideElement UserName                            = $x("//*[@id=\"userName\"]");
    public static SelenideElement Password                            = $x("//*[@id=\"userPwd\"]");
    public static SelenideElement Login                               = $x("//*[@id=\"enter\"]");
    public static SelenideElement Management                          = $x("//*[@id=\"maintenance_section\"]/a");
    public static SelenideElement Managementok                        = $x("/html/body/div[56]/div[7]/div/button");
    public static SelenideElement Monitaring                          = $x("//*[@id=\"listgroup_monitoring\"]/span");
    public static SelenideElement System                              = $x("//*[@id=\"monitorsystem_s\"]");
    public static SelenideElement Antenna                             = $x("//*[@id=\"antenna2g\"]");
    public static SelenideElement NotificationBell                    = $x("//*[@id=\"notificationDrop\"]/div/img");
    public static SelenideElement NotificationMes                     = $x("//*[@id=\"notificationPadd\"]/div/ul/li[1]/p");
    public static SelenideElement SelectAll                           = $x("//*[@id=\"notificationPadd\"]/div/div[3]/button");
    public static SelenideElement SelectEnable                        = $x("//*[@id=\"editNotificationIcon\"]/div/a/span");
    public static SelenideElement SelectOe                            = $x("//*[@id=\"itemsNotificat0\"]/span/label/i");
    public static SelenideElement Share                               = $x("//*[@id=\"content\"]/div[3]/div[2]/button[3]");
    public static SelenideElement Email                               = $x("//*[@id=\"shareAll_email_id1\"]");
    public static SelenideElement Send                                = $x("//*[@id=\"main\"]/div/div[4]/div/div/div[3]/button[2]");
    public static SelenideElement SucessEmail                         = $x("//*[@id=\"notificationMsg\"]/div");
    public static SelenideElement Deletebutton                        = $x("//*[@id=\"btnDelete1\"]");
    public static SelenideElement SucessNoti                          = $x("//*[@id=\"notificationMsg\"]/div");   
    public SelenideElement        LocName                             = $x("//*[@id=\"locationdropdown\"]");    
    public SelenideElement        close                               = $x("//*[@id=\"addDeviceModal\"]/div/div/div[1]/button/img");
    public static SelenideElement DeviceName                          = $x("//*[@id=\"customGroupsDevicesTable\"]/thead/tr/th[1]");
    public static SelenideElement DeviceSerialNumber                  = $x("//*[@id=\"customGroupsDevicesTable\"]/thead/tr/th[2]");
    public static SelenideElement WirelessFilter                      = $x("//*[@id=\"filterDropdown\"]");
    public static SelenideElement SidedateClient                      = $x("//*[text()=\"No. Of Clients\"]");
    public static SelenideElement Sidedatetraffic                     = $x("//*[text()='Traffic (KBPS)']");
    public SelenideElement        ssidlistone                         = $("#NetHmWirSett0");
    public SelenideElement        next                         =$x("//*[@id=\"addDeviceModal\"]/div/div/div[3]/button[2]");
   
    public SelenideElement Locedit(String LocName) {
        return $x("//p[@title='" + LocName + "']/../../ul");
    }
    public SelenideElement        addDevice3                         = $x("//div[@data-tooltip='Add Device']");
    

    public SelenideElement Fileter(String DGName) {
        return $x("//p[text() = '" + DGName + "']");
    }

    public SelenideElement ClientWidges(String SerialNo) {
        return $x("(//li[text() = '" + SerialNo + "'])[1]");
    }

    public SelenideElement TrafficWidges(String SerialNo) {
        return $x("(//li[text() = '" + SerialNo + "'])[2]");
    }
    public SelenideElement devicesList(String serialNumber) {
        return $x("//span[text() = '" + serialNumber + "']");
    }
    
    public SelenideElement devicesName(String serialNumber) {
        return $x("//img[@data-deviceserial='" + serialNumber + "']/ancestor::tr//p[1]/span");
    }
    
    public SelenideElement editCG(String text) {
        SelenideElement CGname = $x("//td[text()='" + text + "']/..//../td[4]/p");
        return CGname;
    }
    
    public SelenideElement editDeviceTabCG(String text) {
        SelenideElement CGname = $x("//td[text()='" + text + "']/../td[3]/p");
        return CGname;
    }
    
    public SelenideElement deleteCG(String text) {
        SelenideElement  CGname = $x("//td[text()='" + text + "']/..//../td[4]/p/i[2]");
        return CGname;
    }
    
    public SelenideElement enterCG(String text) {
        SelenideElement  CGname = $x("//td[text()='" + text + "']/..//../td[4]/p/i[1]");
        return CGname;
    }
    
    public SelenideElement DeleteDeviceCG(String text) {
        SelenideElement  CGname = $x("//td[text()='" + text + "']/../td[3]/p/i");
        return CGname;
    }
    
    public SelenideElement CGcheck(String text) {
        SelenideElement  CGname = $x("//td[text()='" + text + "']");
        return CGname;
    }
    
    public SelenideElement locationName(String text) {
        waitReady();
        if (!$("#_divColNoNetWork").exists() & !$x("//p[text()='Networks not available']").exists()) {
            waitElement("//div[@class='locationDiv']");
        }
        SelenideElement location = $x("//div[@class='locationDiv']//span[text()='" + text + "']");
        return location;
    }
    
    public void editNetwork(String Name) {
        $x("//p[@title='" + Name + "']/../../ul/li/a").click();
        if ($x("//p[@title='" + Name + "']/../../ul//b[text()='Edit']/..").exists()) {
            $x("//p[@title='" + Name + "']/../../ul//b[text()='Edit']/..").click();
        } else if ($x("//p[@title='" + Name + "']/../../ul//b[text()='Edit location']/..").exists()) {
            $x("//p[@title='" + Name + "']/../../ul//b[text()='Edit location']/..").click();
        }
        
    }
    
    public static SelenideElement        enableSysLogText                         =$x("//*[text()=\"Enable Syslog\"]");
    public static SelenideElement okSys   = $x("//*[@id=\"SuccsEditWirNet\"]");
    
    
    // Tejeshwini NASID
    
    
    public static SelenideElement Radious                             = $x("//*[@href= \"#/network/Radius\" and @class ='anchor']");
    public static SelenideElement enableRadiouscheck                  = $x("//*[@id=\"onOffAuthStatusRadius\"]");
    public static SelenideElement enableRadious                       = $x("//*[@id=\"_divTogOnOfSetRaLiBlColMdRowStRad\"]/span");
    public static SelenideElement NASID                               = $x("//*[@id=\"nasIdentifierValue\"]");
    public static SelenideElement SaveRadious                         = $x("//*[@id=\"_buSaveBtnTwo\"]");
    public static SelenideElement confirmRadious                      = $x("//*[@id=\"SuccsEditWirNet\"]");
    public static SelenideElement confirmdisableRadious               = $x("//*[contains(text(), \"SSIDs using WPA Enterprise security will stop working\")]/../../div/button[contains(@class, 'btn-danger')]");
    public static SelenideElement ExcMessage                          = $x("//*[@id=\"radiusMsg\"]");
    
    
}

