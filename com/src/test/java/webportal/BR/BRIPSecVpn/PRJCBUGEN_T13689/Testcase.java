package webportal.BR.BRIPSecVpn.PRJCBUGEN_T13689;

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
    @Story("PRJCBUGEN_T13689") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("087-DPD Interval(seconds 1-300)") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T13689") // It's a testcase id/link from Jira Test Case.

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

    @Step("Test Step 2: DPD Interval input 60")
    public void step2() {
        bripsvp.gotoPage();
        bripsvp.addPolicy();

        bripsvp.openPolicy(bripsvp.testData.policyName);
        bripsvp.openAdv();
        bripsvp.txtDpdInterval.setValue("60");
        bripsvp.btnSave.click();
    }

    @Step("Test Step 3: DPD Interval input 300")
    public void step3() {
        bripsvp.openPolicy(bripsvp.testData.policyName);
        bripsvp.openAdv();
        bripsvp.txtDpdInterval.setValue("300");
        bripsvp.btnSave.click();
        assertTrue(bripsvp.getPolicyNameList().contains(bripsvp.testData.policyName), "verify policy is updated on page");
    }

    @Step("Test Step 4: DPD Interval input 301")
    public void step4() {
        bripsvp.openPolicy(bripsvp.testData.policyName);
        bripsvp.openAdv();
        bripsvp.txtDpdInterval.setValue("301");
        bripsvp.btnSave.click();
        assertTrue(handle.getPageErrorMsg().length() > 10, "check msg on 99999 value");
    }
}
