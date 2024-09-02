package webportal.BR.BRIPSecVpn.PRJCBUGEN_T13670;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.BRUtils;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("BR.BRIPSecVpn") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T13670") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("066-Phase-1 Settings:config 4 proposa,all proposal is different") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T13670") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
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

    @Step("Test Step 2: On DUT1 config phase-1 setting on du1, same on dut2")
    public void step2() {
        bripsvp.testData.policyName = bripsvp.testData.policyName + testcaseId;
        bripsvp.gotoPage();
        bripsvp.testData.phase1Proposal1 = "md5-3des-dh1";
        bripsvp.testData.phase1Proposal2 = "md5-3des-dh2";
        bripsvp.testData.phase1Proposal3 = "md5-3des-dh5";
        bripsvp.testData.phase1Proposal4 = "md5-aes128-dh1";
        bripsvp.addPolicy();
    }

    @Step("Test Step 3: Check on web portal")
    public void step3() {
        assertTrue(bripsvp.getPolicyNameList().contains(bripsvp.testData.policyName), "verify policy is created on page");
    }

    @Step("Test Step 4: Check on device side")
    public void step4() {
        handle.waitRestReady(BRUtils.api_ipsec_policy, bripsvp.testData.policyName, false, 0);

        BRUtils tocheck = new BRUtils();
        assertTrue(tocheck.Dump().contains(bripsvp.testData.phase1Proposal1), "verify phase1Proposal1: " + bripsvp.testData.phase1Proposal1);
        assertTrue(tocheck.Dump().contains(bripsvp.testData.phase1Proposal2), "verify phase1Proposal2: " + bripsvp.testData.phase1Proposal2);
        assertTrue(tocheck.Dump().contains(bripsvp.testData.phase1Proposal3), "verify phase1Proposal3: " + bripsvp.testData.phase1Proposal3);
        assertTrue(tocheck.Dump().contains(bripsvp.testData.phase1Proposal4), "verify phase1Proposal4: " + bripsvp.testData.phase1Proposal4);
    }
}
