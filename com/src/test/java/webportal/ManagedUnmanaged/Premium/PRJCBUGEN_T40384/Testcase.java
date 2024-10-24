package webportal.ManagedUnmanaged.Premium.PRJCBUGEN_T40384;

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
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    int temp;
    int temp1;
    int temp2;

    @Feature("ManagedUnmanaged") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T40384") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("[premium]Test to verify the add one HB device and verify the toggle button is disabled.")
    @TmsLink("PRJCBUGEN_T40384") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new DevicesDashPage(false).openLocationFromotherOrg();
        MyCommonAPIs.sleepi(10);
        new DevicesDashPage().deleteDevice1(WebportalParam.ap10serialNo);
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
    }

    @Step("Test Step 2:Verify Managed Unmanaged switch on device dash page is working fine;")
    public void step2() {
        new MyCommonAPIs().gotoLoction(WebportalParam.location1);
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap10serialNo);
        devInfo.put("Device Name", WebportalParam.ap10deveiceName);
        devInfo.put("MAC Address", WebportalParam.ap10macaddress);
        new DevicesDashPage().addNewDevice(devInfo);
        MyCommonAPIs.sleepi(5);
        assertTrue(new DevicesDashPage(false).verifyManageUnmanageswiutchIsthereafternewdeviceonboard(WebportalParam.ap10serialNo),"Test case not passed successfully");
    }
}
