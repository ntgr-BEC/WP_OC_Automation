package webportal.BR.BRIPSecVpn.PRJCBUGEN_T13662;

import static org.testng.Assert.assertTrue;

import org.apache.commons.lang.RandomStringUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String sName   = null;
    String tocheck = null;

    @Feature("BR.BRIPSecVpn") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T13662") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("058-Policy name force error check >maxmum") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T13662") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p3") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM APP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        ddp.gotoPage();
        ddp.openBR1();
    }

    @Step("Test Step 2: Policy Name: Input !@#$%^")
    public void step2() {
        bripsvp.gotoPage();
        sName = RandomStringUtils.randomAlphanumeric(33);
        tocheck = bripsvp.addPolicy(sName);
    }

    @Step("Test Step 3: On webportal, check error")
    public void step3() {
        assertTrue(tocheck.length() > 10, "The name of the policy can have up to 32 characters.");
    }
}
