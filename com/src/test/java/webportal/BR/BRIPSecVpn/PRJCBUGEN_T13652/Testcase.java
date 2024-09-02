package webportal.BR.BRIPSecVpn.PRJCBUGEN_T13652;

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
    @Story("PRJCBUGEN_T13652") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("036-Edit VPN Policy, change local/remote subnet") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T13652") // It's a testcase id/link from Jira Test Case.

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

    @Step("Test Step 2: Edit VPN policy as follows: Change both local and remote subnet after created")
    public void step2() {
        bripsvp.testData.policyName = bripsvp.testData.policyName + testcaseId;
        bripsvp.gotoPage();
        bripsvp.addPolicy();

        bripsvp.openPolicy(bripsvp.testData.policyName);
        bripsvp.testData.localLanmask = "255.255.252.0";
        bripsvp.testData.remoteLanmask = "255.255.254.0";
        bripsvp.addPolicy();
    }

    @Step("Test Step 3: On webportal and BR500,generated a new vpn policy,all the data same as config")
    public void step3() {
        assertTrue(bripsvp.getPolicyNameList().contains(bripsvp.testData.policyName), "verify policy is created on page");
    }

    @Step("Test Step 4: On webportal and DUT,the local and remote subnet display correctly")
    public void step4() {
        handle.waitRestReady(BRUtils.api_ipsec_policy, bripsvp.testData.policyName, false, 0);

        BRUtils tocheck = new BRUtils();
        assertTrue(tocheck.Dump().contains(bripsvp.testData.localLanmask), "verify localLanmask: " + bripsvp.testData.localLanmask);
        assertTrue(tocheck.Dump().contains(bripsvp.testData.remoteLanmask), "verify remoteLanmask: " + bripsvp.testData.remoteLanmask);
    }
}
