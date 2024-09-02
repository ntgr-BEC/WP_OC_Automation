package webportal.BR.BRVPNRemoteUser2Site.PRJCBUGEN_T7222;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.VPNClient;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String sUser = webportalParam.winVPNClientUser;

    @Feature("BR.BRVPNRemoteUser2Site") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7222") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("009-Click 'Forgot Account' to reset passwrod by VPN client") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7222") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p3") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: IM APP discover and manage BR500")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.openOneBRDevice(true);
    }

    @Step("Test Step 2: Open VPN client software, click ‘Forgot Account’ button;")
    public void step2() {
        assertTrue(new VPNClient().checkForgotAccount(), "no forgot account link by client app");
    }
}
