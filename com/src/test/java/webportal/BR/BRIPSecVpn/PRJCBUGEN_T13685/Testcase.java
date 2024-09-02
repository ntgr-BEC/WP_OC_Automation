package webportal.BR.BRIPSecVpn.PRJCBUGEN_T13685;

import static org.testng.Assert.assertTrue;

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
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("BR.BRIPSecVpn") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T13685") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("083-Phase-1 settings:SA-Lifetime input check (seconds 600-604800)") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T13685") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p3") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        bripsvp.gotoPage();
        bripsvp.deletePolicyNames();
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

    @Step("Test Step 2: SA-Lifetime (sec): 0/999999")
    public void step2() {
        bripsvp.gotoPage();
        bripsvp.addPolicy();

        bripsvp.openPolicy(bripsvp.testData.policyName);
        bripsvp.openAdv();
        bripsvp.txtph1SaLifetime.setValue("0");
        bripsvp.btnSave.click();
        assertTrue(handle.getPageErrorMsg().length() > 10, "check msg on 0 value");

        bripsvp.txtph1SaLifetime.setValue("999999");
        bripsvp.btnSave.click();
        assertTrue(handle.getPageErrorMsg().length() > 10, "check msg on 99999 value");
    }

    @Step("Test Step 3: SA-Lifetime (sec): 600")
    public void step3() {
        bripsvp.txtph1SaLifetime.setValue("600");
        bripsvp.btnSave.click();
    }

    @Step("Test Step 4: SA-Lifetime (sec): 604800")
    public void step4() {
        bripsvp.openPolicy(bripsvp.testData.policyName);
        bripsvp.openAdv();
        bripsvp.txtph1SaLifetime.setValue("604800");
        bripsvp.btnSave.click();
    }

    @Step("Test Step 5: Check on policy can be saved")
    public void step5() {
        assertTrue(bripsvp.getPolicyNameList().contains(bripsvp.testData.policyName), "verify policy is updated on page");
    }
}
