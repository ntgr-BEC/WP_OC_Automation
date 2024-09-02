package webportal.BR.BRIPSecVpn.PRJCBUGEN_T13696;

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
    @Story("PRJCBUGEN_T13696") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("097-Establish vpn with IKE2,phase1 proposal use sha512-aes192-dh24,phase2 proposal use esp-sha512-aes256") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T13696") // It's a testcase id/link from Jira Test Case.

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

    @Step("Test Step 2: Establish vpn with IKE2,phase1 proposal use sha512-aes192-dh24,phase2 proposal use esp-sha512-aes256")
    public void step2() {
        bripsvp.testData.ikeVer = 1;
        bripsvp.testData.phase1Proposal1 = "sha512-aes192-dh24";
        bripsvp.testData.phase2Proposal1 = "esp-sha512-aes256";
        bripsvp.gotoPage();
        bripsvp.addPolicy();
    }

    @Step("Test Step 3: Verify on device side")
    public void step3() {
        handle.waitRestReady(BRUtils.api_ipsec_policy, bripsvp.testData.policyName, false, 0);

        BRUtils tocheck = new BRUtils();
        assertTrue(tocheck.Dump().contains("ikev2"), "verify ike version: ikev2");
        assertTrue(tocheck.Dump().contains(bripsvp.testData.phase1Proposal1),
                "verify bripsvp.testData.phase1Proposal1: " + bripsvp.testData.phase1Proposal1);
        assertTrue(tocheck.Dump().contains(bripsvp.testData.phase2Proposal1.replace("esp-", "")),
                "verify bripsvp.testData.phase2Proposal1: " + bripsvp.testData.phase2Proposal1);
    }
}
