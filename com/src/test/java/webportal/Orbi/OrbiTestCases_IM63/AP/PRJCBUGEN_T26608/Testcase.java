package webportal.Orbi.OrbiTestCases_IM63.AP.PRJCBUGEN_T26608;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import orbi.weboperation.OrbiAdvancedBackupSettingsPage;
import orbi.weboperation.OrbiAdvancedInternetSetupPage;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesOrbiDeviceModePage;
import webportal.weboperation.DevicesOrbiSetupWizardPage;
import webportal.weboperation.DevicesOrbiSummaryPage;
import webportal.weboperation.DevicesOrbiWifiNetworkPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    
    public String currentDeviceMode = "Router";

    @Feature("Orbi.OrbiTestCases_IM63") // 必须要加，对应目录名
    @Story("PRJCBUGEN_T26608") // 对应testrunkey
    @Description("Test to verify changing the mode while completing day 0 onboarding") // 对应用例名字
    @TmsLink("PRJCBUGEN-T26608") // 对应用例详情页的URL最后几位数字

    @Test(alwaysRun = true, groups = "p1") // 标记测试用例
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        //restore to initial state, change to router mode
        
        ddp.gotoPage();
        new DevicesDashPage().enterDevicesSwitchSummary(WebportalParam.ob2serialNo, 3);
        DevicesOrbiDeviceModePage page = new DevicesOrbiDeviceModePage();
        page.setDeviceMode(false);
        MyCommonAPIs.sleepsync(); 
          
    }
    
    //Device details info:
    public final static Map<String, String> Device_Details_Info = new HashMap<String, String>() {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        {
            put("Connnected_status", "Connected");
            put("Device_Mode", DevicesOrbiSetupWizardPage.device_setup_ap_Info.get("Device_Mode"));
            put("Name", WebportalParam.ob2Name);
            put("Serial_Number", WebportalParam.ob2serialNo);
            put("Model", WebportalParam.ob2Model);
            put("Base_MAC_Address", WebportalParam.ob2MAC_Address);
            put("Uptime", "NNA");
//            put("Ip_Adress", new OrbiAdvancedInternetSetupPage().getWANIpFromRouter());
            put("Firmware_Version", WebportalParam.ob2FirmwareVersion);
        }
    };
    public final static Map<String, String> SSIDInfo = new HashMap<String, String>() {/**
         * 
         */
        private static final long serialVersionUID = 1L;

        {
        
            put("SSIDName", WebportalParam.ob2WifiNetworkName1);
            put("SSIDStatus", "Enabled");
            put("SSIDSecurity", "WPA2-PSK");
        }
    };
    
    // step对应jira测试用例里面的步骤，括号里的是描述

    @Step("Test Step 1: Login IM WP success, delete devcie if exist.")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        if (!new DevicesDashPage().getDeviceName(WebportalParam.ob2serialNo).equals("")) {
            new DevicesDashPage().enterDevice(WebportalParam.ob2serialNo);
            // change to router mode
            DevicesOrbiDeviceModePage page = new DevicesOrbiDeviceModePage();
            if(!page.isRouterMode()) {
                page.setDeviceMode(false);
                MyCommonAPIs.sleepsyncorbi();
            }
            
            currentDeviceMode = new DevicesOrbiSummaryPage().getDeviceMode();
            assertTrue(currentDeviceMode.equals("Router"), "check current mode is router");
            
            new DevicesDashPage().deleteDeviceYes(WebportalParam.ob2serialNo);
            MyCommonAPIs.sleepi(100); // wait cloud and dut clear data
        }
        
        // add logout
        UserManage userManage = new UserManage();
        userManage.logout();
//        Selenide.close();
    }

    @Step("Test Step 2: Factory default orbi base;")
    public void step2() {
        OrbiAdvancedBackupSettingsPage orbiadvbackupsettingspage = new OrbiAdvancedBackupSettingsPage();
        orbiadvbackupsettingspage.setDeviceModeVariable(this.currentDeviceMode);
        orbiadvbackupsettingspage.goToOrbiAdvancedBackupSettingsPage();
        orbiadvbackupsettingspage.factoryDefaultDevice();  
        MyCommonAPIs.sleepi(300); // add to wait factory default complete
    }

   
    @Step("Test Step 3: Login IM WP success;")
    public void step3() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }

    @Step("Test Step 4: Create new location and add device in this location;")
    public void step4() {
        new AccountPage().addNetwork(new CommonDataType().LOCATION_INFO);
        new AccountPage().enterLocation(WebportalParam.location1);
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ob2serialNo);
        devInfo.put("Device Name", WebportalParam.ob2Name);
        devInfo.put("MAC Address", WebportalParam.ob2MAC_Address.replace("-", ":"));
        new DevicesDashPage().addNewDevice(devInfo);
        
    }

    @Step("Test Step 5: Verify device status;")
    public void step5() {
        assertTrue(new DevicesDashPage().waitDevicesReConnected(WebportalParam.ob2serialNo), "Device cannot online.");
        assertTrue(new DevicesDashPage().waitDevicesIPvalid(WebportalParam.ob2serialNo), "Device IP not valid.");
    }
  
    @Step("Test Step 6: Setup orbi wizard ap mode via webportal;")
    public void step6() {       
        new DevicesDashPage(false).gotoPage();
        new DevicesDashPage().enterDevice(WebportalParam.ob2serialNo);
        DevicesOrbiSetupWizardPage setupWizardPage = new DevicesOrbiSetupWizardPage();
        setupWizardPage.SetupOrbiDayODevice(DevicesOrbiSetupWizardPage.device_setup_ap_Info);
        
        MyCommonAPIs.sleepsync();
        MyCommonAPIs.sleepsync(); // wait cloud update dut finish setup
        
        new DevicesDashPage(false).gotoPage();
        new DevicesDashPage().enterDevice(WebportalParam.ob2serialNo);
        //comment below
        //OrbiAdvancedInternetSetupPage orbiAdvInternetSetupPage = new OrbiAdvancedInternetSetupPage();
        //Device_Details_Info.put("Ip_Adress", orbiAdvInternetSetupPage.getWANIpFromRouter());
        assertTrue(new DevicesOrbiSummaryPage().checkDeviceDetails(Device_Details_Info),"Device summry check failed"); 
        assertTrue(new DevicesOrbiWifiNetworkPage().CheckSSIDSummary(SSIDInfo),"Device SSID check failed");
//        new OrbiLoginPage(DevicesOrbiSetupWizardPage.device_setup_ap_Info.get("Device_Mode"));
    }
    @Step("Test Step 7: Remove device and add device again")
    public void step7() {
        
        new DevicesDashPage(false).gotoPage();
        if (!new DevicesDashPage().getDeviceName(WebportalParam.ob2serialNo).equals("")) {
            new DevicesDashPage().enterDevice(WebportalParam.ob2serialNo);
            currentDeviceMode = new DevicesOrbiSummaryPage(false).getDeviceMode();
            new DevicesDashPage().deleteDeviceYes(WebportalParam.ob2serialNo);
            MyCommonAPIs.sleep(10 * 1000);
        }
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ob2serialNo);
        devInfo.put("Device Name", WebportalParam.ob2Name);
        devInfo.put("MAC Address", WebportalParam.ob2MAC_Address.replace("-", ":"));
        new DevicesDashPage(false).addNewDevice(devInfo);
    }
    
    @Step("Test Step 8: Verify device status;")
    public void step8() {
        assertTrue(new DevicesDashPage().waitDevicesReConnected(WebportalParam.ob2serialNo), "Device cannot online.");
        assertTrue(new DevicesDashPage().waitDevicesIPvalid(WebportalParam.ob2serialNo), "Device IP not valid.");
    }
    
    @Step("Test Step 9: Check day1 device SSID info;")
    public void step9() {
        new DevicesDashPage().enterDevice(WebportalParam.ob2serialNo);
        assertTrue(new DevicesOrbiWifiNetworkPage().CheckSSIDSummary(SSIDInfo),"Device SSID check failed");
    }

}
