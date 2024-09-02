package webportal.MarketPlace.IMPro.PRJCBUGEN_T10308;

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
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("MarketPlace.IMPro") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T10308") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that there should be a place where the Pro user(Admin) can enter License for VPN Services, and for Additional Pro License") // It's
                                                                                                                                                     // a
                                                                                                                                                     // testcase
    // title from Jira
    // Test Case.
    @TmsLink("PRJCBUGEN-T10308") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
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
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

    }

    @Step("Test Step 2: Click 'Add vpn service key' button, then pop up dialog;")
    public void step2() {
        assertTrue(new HamburgerMenuPage().checkAddVpnKey(), "Pop up 'Add VPN Service Key' dialog failed.");
    }

}
