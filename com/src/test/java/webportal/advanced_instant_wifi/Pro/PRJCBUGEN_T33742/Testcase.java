package webportal.advanced_instant_wifi.Pro.PRJCBUGEN_T33742;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import util.MyCommonAPIs;
import util.RunCommand;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Vivek Singh
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("Advance Instant WIFI 6.10") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T33742") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify 'Allocation' and 'Description' text are present on #/wireless/instantWifi page") // It's a testcase title from Jira
                                                                                                                  // Test Case.
    @TmsLink("PRJCBUGEN-T33742") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p3") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
    }

    @Step("Test Step 2: Verify Account have one Location and one AP in connected state ")
    public void step2() {
        handle.gotoLoction();
        assertTrue(new DevicesDashPage().isDeviceConnected(WebportalParam.ap1serialNo));

    }

    @Step("Test Step 3: Go to Instant Wifi Page")
    public void step3() {
        new WirelessQuickViewPage();
        new WirelessQuickViewPage(false).open_instant_wifi_page();
    }

    @Step("Test Step 4: verify Allocation and Description text should be there")
    public void step4() {
        assertTrue(new WirelessQuickViewPage(false).verify_allocation_text_is_present_on_instant_wifi_page(), "Allocation text is not Present");
        assertTrue(new WirelessQuickViewPage(false).verify_Description_text_is_present_on_instant_wifi_page(), "Description text is not Present");
    }

}
