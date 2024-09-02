package webportal.Orbi.OnBoarding.AP.PRJCBUGEN_T14883;

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
import orbi.weboperation.OrbiAdvancedInternetSetupPage;
import orbi.weboperation.OrbiAdvancedRouterAPModePage;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.WebportalParam;
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
    
    public String currentDeviceMode = "AP";

    @Feature("Orbi.OnBoarding") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14883") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify day 1 AP mode Orbi router can be onboarded using the Serial #") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14883") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
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
        MyCommonAPIs.sleepsyncorbi();
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
    
    // Each step is a single test step from Jira Test Case

    @Step("Test Step 1: Login IM WP success, delete devcie if exist.")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        if (!new DevicesDashPage().getDeviceName(WebportalParam.ob2serialNo).equals("")) {;
            new DevicesDashPage().enterDevice(WebportalParam.ob2serialNo);
            // change to ap mode
            DevicesOrbiDeviceModePage page = new DevicesOrbiDeviceModePage();
            if(page.isRouterMode()) {
                page.setDeviceMode(true);
                MyCommonAPIs.sleepsyncorbi();
            }
            // get current mode
            currentDeviceMode = new DevicesOrbiSummaryPage().getDeviceMode();
            assertTrue(currentDeviceMode.equals("AP"), "check current mode is ap");  
            
            new DevicesDashPage().deleteDeviceYes(WebportalParam.ob2serialNo);
            MyCommonAPIs.sleepi(100);
        }

//        Selenide.close();
    }

    /*
    @Step("Test Step 2: Make sure device mode is AP;")
    public void step2() {
        if (!currentDeviceMode.equals(DevicesOrbiSetupWizardPage.device_setup_ap_Info.get("Device_Mode"))) {
            OrbiAdvancedRouterAPModePage orbiadvrouterapmodepage = new OrbiAdvancedRouterAPModePage();
            orbiadvrouterapmodepage.setDeviceModeVariable(this.currentDeviceMode);
            orbiadvrouterapmodepage.setDeviceMode(DevicesOrbiSetupWizardPage.device_setup_ap_Info.get("Device_Mode"));
            Selenide.close();
            this.currentDeviceMode = DevicesOrbiSetupWizardPage.device_setup_ap_Info.get("Device_Mode");
            WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
            webportalLoginPage.defaultLogin();
            handle.gotoLoction();
        }
        
    }
    */
    
    @Step("Test Step 3: Create new location and add device in this location;")
    public void step3() {
        //new AccountPage().addNetwork(new CommonDataType().LOCATION_INFO);
        //new AccountPage().enterLocation(WebportalParam.location1);
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
  
    @Step("Test Step 5: Check orbi device summary via webportal;")
    public void step5() {
        new DevicesDashPage(false).gotoPage();
        new DevicesDashPage().enterDevice(WebportalParam.ob2serialNo);
        OrbiAdvancedInternetSetupPage orbiAdvInternetSetupPage = new OrbiAdvancedInternetSetupPage();
        assertTrue(new DevicesOrbiSummaryPage().checkDeviceDetails(Device_Details_Info),"Device summry check failed");       
    }
 
}
