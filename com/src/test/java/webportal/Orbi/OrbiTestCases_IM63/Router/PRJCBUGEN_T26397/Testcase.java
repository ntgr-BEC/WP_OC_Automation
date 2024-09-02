package webportal.Orbi.OrbiTestCases_IM63.Router.PRJCBUGEN_T26397;

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
import orbi.param.OrbiGlobalConfig;
import orbi.weboperation.OrbiAdvancedBackupSettingsPage;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
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

    @Feature("Orbi.OrbiTestCases_IM63") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T26397") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify changing the SSID name while completing the day 0 onboarding") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T26397") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        // if the device is deleted and not added back, try to add it back
        try {
            if (new DevicesDashPage().getDeviceName(WebportalParam.ob2serialNo).equals("")) {
                Map<String, String> devInfo = new HashMap<String, String>();
                devInfo.put("Serial Number", WebportalParam.ob2serialNo);
                devInfo.put("Device Name", WebportalParam.ob2Name);
                devInfo.put("MAC Address", WebportalParam.ob2MAC_Address.replace("-", ":"));
                new DevicesDashPage().addNewDevice(devInfo);
            }
            
        } catch(Throwable e) {
            System.out.println("failed to add device back");
        }
        
    }
    
    public final static Map<String, String> device_setup_router_Info_ssid = new HashMap<String, String>() {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        {
            put("Device_Mode", "Router");
            put("WifiNetworkName", WebportalParam.ob2Model + "-newssid");
            put("WifiNetworkPwd", WebportalParam.ob2WifiNetworkPwd1);
            put("Serial_Number", WebportalParam.ob2serialNo);
        }
    };
    
    
    // Each step is a single test step from Jira Test Case
    
    @Step("Test Step 1: Login IM WP success, delete devcie if exist.")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        if (!new DevicesDashPage().getDeviceName(WebportalParam.ob2serialNo).equals("")) {
            new DevicesDashPage().enterDevice(WebportalParam.ob2serialNo);
            currentDeviceMode = new DevicesOrbiSummaryPage(false).getDeviceMode();
            new DevicesDashPage().deleteDeviceYes(WebportalParam.ob2serialNo);
            MyCommonAPIs.sleepi(100);
        }
        
    }

    @Step("Test Step 2: Factory default orbi base;")
    public void step2( ) {
        OrbiAdvancedBackupSettingsPage orbiadvbackupsettingspage = new OrbiAdvancedBackupSettingsPage();
        orbiadvbackupsettingspage.setDeviceModeVariable(this.currentDeviceMode);
        orbiadvbackupsettingspage.goToOrbiAdvancedBackupSettingsPage();
        orbiadvbackupsettingspage.factoryDefaultDevice();                
    }
    
   
    @Step("Test Step 3: Login IM WP success;")
    public void step3() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
    }
    
    @Step("Test Step 4: Create new location and add device in this location;")
    public void step4() {
        //new AccountPage().addNetwork(new CommonDataType().LOCATION_INFO);

        //new AccountPage().enterLocation(WebportalParam.location1);
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ob2serialNo);
        devInfo.put("Device Name", WebportalParam.ob2Name);
        devInfo.put("MAC Address", WebportalParam.ob2MAC_Address.replace("-", ":"));
        new DevicesDashPage(false).addNewDevice(devInfo);
        
    }

    @Step("Test Step 5: Verify device status;")
    public void step5() {
        assertTrue(new DevicesDashPage().waitDevicesReConnected(WebportalParam.ob2serialNo), "Device cannot online.");
        assertTrue(new DevicesDashPage().waitDevicesIPvalid(WebportalParam.ob2serialNo), "Device IP not valid.");
    }
    
    @Step("Test Step 6: Setup orbi wizard router mode via webportal;")
    public void step6() {
        
        new DevicesDashPage(false).gotoPage();
        new DevicesDashPage().enterDevice(WebportalParam.ob2serialNo);
        
        DevicesOrbiSetupWizardPage setupWizardPage = new DevicesOrbiSetupWizardPage();
        setupWizardPage.SetupOrbiDayODevice(device_setup_router_Info_ssid);

        MyCommonAPIs.sleepsyncorbi();
        new DevicesDashPage(false).gotoPage();
        new DevicesDashPage().enterDevice(WebportalParam.ob2serialNo);
        
        // check device ssidname change
        
        DevicesOrbiWifiNetworkPage wifipage = new DevicesOrbiWifiNetworkPage();
        String ret[] = wifipage.getSsidSetting(0);
        assertTrue(ret[0].equals(WebportalParam.ob2Model + "-newssid"), "check ssid name changed");
        assertTrue(ret[1].equals(WebportalParam.ob2WifiNetworkPwd1), "check password not changed");
    }

}
