package webportal.GlobalNavigation.non_pro_admin.PRJCBUGEN_T17445;

import static org.testng.Assert.assertTrue;

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
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.GlobalNotificationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("Global_navigation.non_pro_admin") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T17462") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify whether user is redirected to AP setting page after click on network configuration hyperlink in AP device dash board") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T17462") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
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

    @Step("Test Step 2: Go to device dash board;")
    public void step2() {
        
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);
        
        new GlobalNotificationPage().clickNetworkconfiguration();
        
        MyCommonAPIs.sleep(3000);
        
        boolean result = true;
        
        assertTrue(new GlobalNotificationPage().checkNetworkconfigurationpage(),"Did not enter AP wireless setting page");
        
    }

}
