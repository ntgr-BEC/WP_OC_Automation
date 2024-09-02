package webportal.B2UC.Premium.PRJCBUGEN_T23084;

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
import testbase.TestCaseBase;
import util.APUtils;
import util.Javasocket;
import util.MyCommonAPIs;
import util.RunCommand;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author  Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    HashMap<String, String> BackupInfo = new HashMap<String, String>();

    @Feature("B2UC_Premium") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T23084") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify if the Brodcast to unicast gets pushed as a part of first config") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T23084") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new WirelessQuickViewPage().B2UCDisable();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        BackupInfo.put("Name", "test23102");
        BackupInfo.put("Description", "test");
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccount();
    }

    @Step("Test Step 2: Delete device and enable IGMP")
    public void step2() {
        new WirelessQuickViewPage().deleteDeviceYes(WebportalParam.ap1serialNo);
        new WirelessQuickViewPage().B2UCEnable();
    }
    
    @Step("Test Step 3: Add device and check the status;")
    public void step3() {
   WebCheck.checkHrefIcon(URLParam.hrefDevices);
        
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap1serialNo);
        devInfo.put("Device Name", WebportalParam.ap1deveiceName);
        devInfo.put("MAC Address1", WebportalParam.ap1macaddress);

        new DevicesDashPage(false).addNewDevice(devInfo);

        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        MyCommonAPIs.sleepi(120); 
        new RunCommand().enableSSH4AP(WebportalParam.loginPassword, WebportalParam.ap1IPaddress);
        assertTrue(new APUtils(WebportalParam.ap1IPaddress).getB2UCEnableStatus(WebportalParam.ap1Model), "IGMP is disabled after restoring");

    }
       

}
