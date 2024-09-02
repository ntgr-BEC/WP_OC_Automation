package webportal.BR.BRIPSecVpn.PRJCBUGEN_T13690;

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

    String tomatch = "dh14";

    @Feature("BR.BRIPSecVpn") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T13690") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("088-PFS change from no to dh14") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T13690") // It's a testcase id/link from Jira Test Case.

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

    @Step("Test Step 2: webportal create a vpn policy,PFS is no")
    public void step2() {
        bripsvp.gotoPage();
        bripsvp.addPolicy();

        bripsvp.openPolicy(bripsvp.testData.policyName);
        bripsvp.openAdv();
        bripsvp.txtDpdInterval.setValue("60");
        bripsvp.btnSave.click();
    }

    @Step("Test Step 3: Verify on device side")
    public void step3() {
        handle.waitRestReady(BRUtils.api_ipsec_policy, bripsvp.testData.policyName, false, 0);

        BRUtils tocheck = new BRUtils();
        assertTrue(tocheck.Dump().contains("no"), "verify new pfs is no");
    }

    @Step("Test Step 4: webportal edit vpn policy,PFS change to dh14")
    public void step4() {
        bripsvp.openPolicy(bripsvp.testData.policyName);
        bripsvp.openAdv();
        bripsvp.lbPFS.selectOption(tomatch);
        bripsvp.btnSave.click();
    }

    @Step("Test Step 5: Verify on device side")
    public void step5() {
        handle.waitRestReady(BRUtils.api_ipsec_policy, tomatch, false, 0);

        BRUtils tocheck = new BRUtils();
        assertTrue(tocheck.Dump().contains(tomatch), "verify new pfs is to: " + tomatch);
    }
}
