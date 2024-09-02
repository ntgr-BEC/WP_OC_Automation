package webportal.GlobalNavigation.non_pro_admin.PRJCBUGEN_T17459;

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

/**
 *
 * @author Tejeshwini
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("Global_navigation.non_pro_admin") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T17459") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify whether user is redirected to router setting page after click on network configuration hyperlink in BR500/BR100 device dash board") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T17459") // It's a testcase id/link from Jira Test Case.

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
        
        new DevicesDashPage().checkDeviceInAdminAccountBR();
    }

    @Step("Test Step 2: Go to device dash board;")
    public void step2() {
        
        handle.gotoLoction();
        
        new GlobalNotificationPage().enterDeviceYes(WebportalParam.br1deveiceName);
        
        new GlobalNotificationPage().clickNetworkconfiguration();
        
        MyCommonAPIs.sleep(3000);
        
        boolean result = true;
        
        assertTrue(new GlobalNotificationPage().checkNetworkconfigurationpageBR500(),"Did not enter Router wireless setting page");
        
    }

}
