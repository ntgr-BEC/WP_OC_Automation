package webportal.BR.BRIPSecVpn.PRJCBUGEN_T13656;

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
import webportal.param.WebportalParam;
import webportal.publicstep.PublicButton;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String policyName1 = "pname11";
    String policyName2 = "pname22";
    String policyName3 = "pname33";
    String policyName4 = "pname44";

    @Feature("BR.BRIPSecVpn") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T13656") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("051-The configure can not change after reboot") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T13656") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        ddp.gotoPage();
        ddp.openBR1();
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

    @Step("Test Step 2: Create 4 vpn policy on BR500 via webportal")
    public void step2() {
        bripsvp.testData.policyName = bripsvp.testData.policyName + testcaseId;
        bripsvp.gotoPage();
        bripsvp.addPolicy(policyName1);
        bripsvp.addPolicy(policyName2);
        bripsvp.addPolicy(policyName3);
        bripsvp.addPolicy(policyName4);
    }

    @Step("Test Step 3: Check config on web portal")
    public void step3() {
        assertTrue(bripsvp.getPolicyNameList().size() > 3, "4 policy in web portal");
    }

    @Step("Test Step 4: Check config on after reboot")
    public void step4() {
        new PublicButton().rebootDevice();
        ddp.waitDevicesReConnected(WebportalParam.br1serialNo);

        handle.waitRestReady(BRUtils.api_ipsec_policy, bripsvp.testData.policyName, false, 0);

        BRUtils tocheck = new BRUtils();
        assertTrue(tocheck.Dump().contains(policyName1), "verify policy name: " + policyName1);
        assertTrue(tocheck.Dump().contains(policyName2), "verify policy name: " + policyName2);
        assertTrue(tocheck.Dump().contains(policyName3), "verify policy name: " + policyName3);
        assertTrue(tocheck.Dump().contains(policyName4), "verify policy name: " + policyName4);
    }
}
