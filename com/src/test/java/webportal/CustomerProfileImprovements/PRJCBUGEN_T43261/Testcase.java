package webportal.CustomerProfileImprovements.PRJCBUGEN_T43261;

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
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("AdvanceAPFunctionally.non_pro_admin") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T43261") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether selected customer profile name is pushed to the device - NICE") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T43261") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        assertTrue(new WirelessQuickViewPage().deleteSsidYesConfirm("apwp43261"),"Ssid not deleted successfully");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccount();
    }

    @Step("Test Step 2: Add WIFI ssid and enable Customer Profile;")
    public void step2() {
        Map<String, String> ssidInfo = new HashMap<String, String>();
        ssidInfo.put("SSID", "apwp43261");
        ssidInfo.put("Security", "WPA2 Personal");
        ssidInfo.put("Password", "12345678");
        new WirelessQuickViewPage().addSsid(ssidInfo);
        new WirelessQuickViewPage().editSSIDAndAddCustomerProfile(ssidInfo.get("SSID"), "NICE");
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);    
    }

    @Step("Test Step 3: Verify Customer Profile NICE is pushed to AP or not;")
    public void step3() {
        new RunCommand().enableSSH4APALL(WebportalParam.ap1IPaddress);
        assertTrue(new APUtils(WebportalParam.ap1IPaddress).getCustomerProfileStatus("NICE", WebportalParam.ap1Model),"Customer Profile NICE is not pushed to AP");
    }

}
