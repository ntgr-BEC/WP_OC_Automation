package webportal.Onboard_recursive.PRJCBUGEN_T14174;

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
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HardBundlePage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;

/**
 *
 * @author Vivek
 *
 */
public class Testcase extends TestCaseBase {

    String locationName     = "office1";
    int iteration_count = 250;

    @Feature("DeviceOnboarding") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T12345") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify user is able to add one device and delete and again add to the account") // It's
                                                                                                          // Test
                                                                                                          // Case.
    @TmsLink("PRJCBUGEN_T12345") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");

        new WirelessQuickViewPage().deleteDeviceYes(WebportalParam.ap1serialNo);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
    }

    @Step("Test Step 2: Onboarding the Device and Delete the device ")
    public void step2() {
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        firststdevInfo.put("Serial Number1", WebportalParam.ap1serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ap1macaddress);
        for (int i = 0; i <iteration_count; i++) {
            handle.gotoLoction(locationName);
            new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
            System.out.println("Waiting for Device Connected state");
            new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
            System.out.println("Going to Delete The AP");
            MyCommonAPIs.sleepi(2);
            new WirelessQuickViewPage().deleteDeviceYes(WebportalParam.ap1serialNo);
            System.out.println("Verifying for AP Deletion");
            assertTrue(!new WirelessQuickViewPage().checkApIsExist(WebportalParam.ap1serialNo), "Device delete unsuccessful");
            System.out.println("iteration count Done = "+ i);

        }

    }

}
