package webportal.Orbi.OnBoarding.Router.PRJCBUGEN_T14099;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import orbi.param.OrbiGlobalConfig;
import orbi.telnetoperation.OrbiTelnet;
import orbi.weboperation.OrbiAdvancedBackupSettingsPage;
import orbi.weboperation.OrbiDebugSettingsPage;
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
import webportal.weboperation.WebportalLoginPage;
/**
 *
 * @author bingke.xue
 *
 */
public class Testcase extends TestCaseBase {
    
    public String currentDeviceMode = "Router";
    public String newAccountEmail = WebportalParam.adminName;
    public String newAccountPwd = WebportalParam.adminPassword;
    public Boolean testresult = false ;

    @Feature("Orbi.OnBoarding") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14099") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify admin user can onboard the factory orbi router using Serial #;") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14099") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }
    
    //Device details info:
    public final static Map<String, String> Device_Details_Info = new HashMap<String, String>() {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        {
            put("Connnected_status", "Connected");
            put("Device_Mode", DevicesOrbiSetupWizardPage.device_setup_router_Info.get("Device_Mode"));
            put("Name", WebportalParam.ob2Name);
            put("Serial_Number", WebportalParam.ob2serialNo);
            put("Model", WebportalParam.ob2Model);
            put("Base_MAC_Address", WebportalParam.ob2MAC_Address);
            put("Uptime", "NNA");
            put("Ip_Adress", OrbiGlobalConfig.orbiLANIp);
            put("Firmware_Version", WebportalParam.ob2FirmwareVersion);
        }
    };
    
    // Each step is a single test step from Jira Test Case

    @Step("Test Step 1: Login IM WP success, delete devcie if exist.")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        
        new DevicesDashPage().enterDevice(WebportalParam.ob2serialNo);
        new DevicesOrbiDeviceModePage(false).initDeviceMode(false);
        new DevicesDashPage().deleteDeviceYes(WebportalParam.ob2serialNo);
        MyCommonAPIs.sleep(10 * 1000);
        
        UserManage userManage = new UserManage();
        userManage.logout();
        MyCommonAPIs.sleepi(3);
        
//        if (!new DevicesDashPage().getDeviceName(WebportalParam.ob2serialNo).equals("")) {
//            new DevicesDashPage().enterDevice(WebportalParam.ob2serialNo);
//            currentDeviceMode = new DevicesOrbiSummaryPage(false).getDeviceMode();
//            new DevicesDashPage().deleteDeviceYes(WebportalParam.ob2serialNo);
//            MyCommonAPIs.sleep(10 * 1000);
//        }
    }
    
    @Step("Test Step 2: Factory default orbi")
    public void step2() {
//        OrbiDebugSettingsPage page = new OrbiDebugSettingsPage();
//        page.OrbibaseEnableTelenet(WebportalParam.ob2IPaddress, WebportalParam.loginDevicePassword);
//        String cmd = "factory_default";
//        new OrbiTelnet(WebportalParam.ob2IPaddress , WebportalParam.loginDevicePassword).orbiTelnetSendCmd(cmd);
//        MyCommonAPIs.sleepi(10);
//        cmd = "reboot";
//        MyCommonAPIs.sleepi(200);
        
        OrbiAdvancedBackupSettingsPage orbiadvbackupsettingspage = new OrbiAdvancedBackupSettingsPage();
//        orbiadvbackupsettingspage.setDeviceModeVariable(this.currentDeviceMode);
        orbiadvbackupsettingspage.goToOrbiAdvancedBackupSettingsPage();
        orbiadvbackupsettingspage.factoryDefaultDevice();  
    }

    @Step("Test Step 3: Login with a new account, Create new location and add device in this location;")
    public void step3() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(newAccountEmail, newAccountPwd);       
        handle.gotoLoction();
        
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ob2serialNo);
        devInfo.put("Device Name", WebportalParam.ob2Name);
        devInfo.put("MAC Address", WebportalParam.ob2MAC_Address.replace("-", ":"));
        new DevicesDashPage().addNewDevice(devInfo);
       
    }

    @Step("Test Step 4: Verify device status;")
    public void step4() {
        assertTrue(new DevicesDashPage().waitDevicesReConnected(WebportalParam.ob2serialNo), "Device cannot online.");
        assertTrue(new DevicesDashPage().waitDevicesIPvalid(WebportalParam.ob2serialNo), "Device IP not valid.");
    }
    
    @Step("Test Step 5: Setup device to Router mode on webportal; Verify device summary info;")
    public void step5() {
        new DevicesDashPage(false).gotoPage();
        new DevicesDashPage().enterDevice(WebportalParam.ob2serialNo);
        DevicesOrbiSetupWizardPage setupWizardPage = new DevicesOrbiSetupWizardPage();
        setupWizardPage.SetupOrbiDayODevice(DevicesOrbiSetupWizardPage.device_setup_router_Info);

        MyCommonAPIs.sleepi(120);
        new DevicesDashPage(false).gotoPage();
        new DevicesDashPage().enterDevice(WebportalParam.ob2serialNo);
        testresult = new DevicesOrbiSummaryPage().checkDeviceDetails(Device_Details_Info);
    }
    
    @Step("Test Step6: Remove device;")
    public void step6() {
        new DevicesDashPage(false).gotoPage();
        new DevicesDashPage().deleteDeviceYes(WebportalParam.ob2serialNo);
        MyCommonAPIs.sleep(10 * 1000);
        new UserManage().logout();
    }
    @Step("Test Step7: Add device to test account;")
    public void step7() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();     
        handle.gotoLoction();
        new AccountPage().addNetwork(new CommonDataType().LOCATION_INFO);
        new AccountPage().enterLocation(WebportalParam.location1);
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
    @Step("Test Step9: return test result;")
    public void step9() {
        assertTrue(testresult,"Device summry check failed");
    }
}
