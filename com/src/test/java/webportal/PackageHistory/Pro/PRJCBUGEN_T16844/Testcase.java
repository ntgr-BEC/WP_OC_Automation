package webportal.PackageHistory.Pro.PRJCBUGEN_T16844;
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
 * @author Pragya
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("PackageHistory.Pro") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T16844") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that Purchase History tab is clickable as pro") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T16844") // It's a testcase id/link from Jira Test Case.

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
            webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        }

        @Step("Test Step 2: In the hamburger menu, click on account management, check purchaseorderhistory tab is clicked;")
        public void step2() {
            assertTrue(new HamburgerMenuPage().PurchaseOrderHistoryScreen());
        }

    }
