package webportal.BroadcastMulticastRateLimit.PRJCBUGEN_T40563;

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
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesApSummaryPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author  Pratik
 *
 */
public class Testcase extends TestCaseBase {
    

    @Feature("BroadcastMulticastRateLimit") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T40563") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify configuring the values and then reboot the device") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T40563") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().GoToWirelessSettings();
        assertTrue(new WirelessQuickViewPage(false).moveSliderUntilValue("64", "Save"),"rate limit not set to 60");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {

        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();

    }

    @Step("Test Step 2: Set rate limit to 60 and save it then reboot AP and after getting connected go to AP Local GUI and varify")
    public void step2() {

        new WirelessQuickViewPage().GoToWirelessSettings();
        assertTrue(new WirelessQuickViewPage(false).moveSliderUntilValue("60", "Save"),"rate limit not set to 60");
        MyCommonAPIs.sleepi(60);
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);
        new DevicesApSummaryPage().clickReboot();
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        assertTrue(new WirelessQuickViewPage(false).verifyBroadcastLimit(WebportalParam.ap1IPaddress,"60", "admin", "Netgear1@"),"rate limit not set to 60");
        
    }

}
