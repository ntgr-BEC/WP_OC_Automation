package webportal.Onboard_recursive.PRJCBUGEN_T14175;

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

    String locationName1     = "office1";
    String locationName2     = "office2";

    @Feature("DeviceOnboarding") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T12346") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify a user can add one device in one location delete it and again add it to diff location. ") // It's
                                                                                                          // Test
                                                                                                          // Case.
    @TmsLink("PRJCBUGEN_T12346") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");

        new DevicesDashPage().deleteDeviceYes(WebportalParam.ap1serialNo);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success and Create Two new location;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
      
        
    }

    @Step("Test Step 2: Onboarding the Device and Delete the device ")
    public void step2() {
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        firststdevInfo.put("Serial Number1", WebportalParam.ap1serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ap1macaddress);
        
        for (int i = 0; i < 250; i++) {
            handle.gotoLoction(locationName1);
            new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
            new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
            new DevicesDashPage().deleteDeviceYes(WebportalParam.ap1serialNo);
            assertTrue(!new WirelessQuickViewPage().checkApIsExist(WebportalParam.ap1serialNo), "Device delete unsuccessful");
            handle.gotoLoction(locationName2);
            new DevicesDashPage().addNewdummyDevice(firststdevInfo);
            new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
            new DevicesDashPage().deleteDeviceYes(WebportalParam.ap1serialNo);
            assertTrue(!new WirelessQuickViewPage().checkApIsExist(WebportalParam.ap1serialNo), "Device delete unsuccessful");
            System.out.println("iteration count Done = "+ i);
            

        }

    }

}
