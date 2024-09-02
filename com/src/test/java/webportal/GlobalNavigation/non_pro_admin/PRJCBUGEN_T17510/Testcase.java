package webportal.GlobalNavigation.non_pro_admin.PRJCBUGEN_T17510;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.GlobalNotificationPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author Tejeshwini Needs to check with Dallas
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("Global_navigation.non_pro_admin") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T17510") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify whether user is redirected to switch dashboard when user click on device configuration beside device name in PoESchedules") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T17510") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        wpsp.deleteAll();
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        
        new DevicesDashPage().checkDeviceInAdminAccountswitch();
    }

    @Step("Test Step 2: Go to device dash board;")
    public void step2() {
        
        handle.gotoLoction();
        
        
        new GlobalNotificationPage().EnterWirelessSettingpage();
        
        wpsp.deleteAll();
        
        new GlobalNotificationPage().checkPOEScheduler(WebportalParam.sw1deveiceName);
        
        assertTrue(new GlobalNotificationPage().checkDeviceEnterDashBoardPage(WebportalParam.sw1deveiceName), "Did not enter Orbi dash board");
        
    }

}
