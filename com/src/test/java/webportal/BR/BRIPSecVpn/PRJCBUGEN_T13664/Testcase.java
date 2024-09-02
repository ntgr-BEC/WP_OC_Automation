package webportal.BR.BRIPSecVpn.PRJCBUGEN_T13664;

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
    String sName = null;

    @Feature("BR.BRIPSecVpn") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T13664") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("060-local subnet and remote sunnet config to single ip") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T13664") // It's a testcase id/link from Jira Test Case.

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

    @Step("Test Step 2: local subnet and remote sunnet config to single ip")
    public void step2() {
        bripsvp.testData.policyName = bripsvp.testData.policyName + testcaseId;
        bripsvp.gotoPage();
        bripsvp.testData.localLannet = "192.168.11.2";
        bripsvp.testData.localLanmask = "255.255.255.255";
        bripsvp.testData.remoteLannet = "192.168.22.2";
        bripsvp.testData.remoteLanmask = "255.255.255.255";
        bripsvp.addPolicy();
    }

    @Step("Test Step 3: All data is correctly on webportal and BR500")
    public void step3() {
        handle.waitRestReady(BRUtils.api_ipsec_policy, bripsvp.testData.remoteLannet, false, 0);

        BRUtils tocheck = new BRUtils();
        assertTrue(tocheck.Dump().contains(bripsvp.testData.remoteLannet), "verify remoteLannet: " + bripsvp.testData.remoteLannet);
        assertTrue(tocheck.Dump().contains(bripsvp.testData.localLannet), "verify localLannet: " + bripsvp.testData.localLannet);
        assertTrue(tocheck.Dump().contains(bripsvp.testData.remoteLanmask), "verify remoteLanmask: " + bripsvp.testData.remoteLanmask);
    }
}
