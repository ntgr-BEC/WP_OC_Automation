package webportal.BR.BRIPSecVpn.PRJCBUGEN_T13665;

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
    String sName = null;

    @Feature("BR.BRIPSecVpn") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T13665") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("061-IPSec preshared key check after reboot DUT") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T13665") // It's a testcase id/link from Jira Test Case.

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

    @Step("Test Step 2: Configure IPSec IKE policy using preshared key")
    public void step2() {
        bripsvp.testData.policyName = bripsvp.testData.policyName + testcaseId;
        bripsvp.gotoPage();
        bripsvp.testData.preShardkey = bripsvp.testData.preShardkey + "13665";
        bripsvp.addPolicy();
    }

    @Step("Test Step 3: All data is correctly on webportal and BR500")
    public void step3() {
        handle.waitRestReady(BRUtils.api_ipsec_policy, bripsvp.testData.preShardkey, false, 0);

        BRUtils tocheck = new BRUtils();
        assertTrue(tocheck.Dump().contains(bripsvp.testData.preShardkey), "verify preShardkey: " + bripsvp.testData.preShardkey);
    }

    @Step("Test Step 4: Reboot DUT 5 times")
    public void step4() {
        for (int i = 0; i < 5; i++) {
            ddp.gotoPage();
            ddp.enterDevicesSwitchSummary(WebportalParam.br1serialNo, 1);
            new PublicButton().rebootDevice();

            ddp.gotoPage();
            ddp.waitDevicesReConnected(WebportalParam.br1serialNo);
        }
    }

    @Step("Test Step 5: All data is correctly on webportal and BR500 after Reboot DUT 5 times")
    public void step5() {
        BRUtils tocheck = new BRUtils(BRUtils.api_ipsec_policy, 0);
        assertTrue(tocheck.Dump().contains(bripsvp.testData.preShardkey), "verify preShardkey: " + bripsvp.testData.preShardkey);
    }
}
