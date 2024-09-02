package webportal.Switch.ACL.PRJCBUGEN_T5001;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
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
    String tclname = getClass().getName();

    String                vlanName = "testvlan";
    String                vlanId   = "501";
    String                tmpStr;

    String ipaclName = "test";
    String ipaclIp   = "11.1.1.1";
    String ipaclMac  = "11:22:33:11:22:33";

    @Issue("PRJCBUGEN-9730")
    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T5001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("079-Deploy deny and allow policy for one vlan") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T5001") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p3") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        wvp.gotoPage();
        wvp.deleteAllVlan();
        wvp.removeAllAclCli();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create vlan50 on switch")
    public void step1() {
        final WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wvp.gotoPage();
        wvp.openVlan(vlanName, vlanId, 0);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: Deploy mac acl to deny source mac address")
    public void step2() {
        wvp.editVlanIpFilteringAllow(vlanName, ipaclName, ipaclIp);
    }

    @Step("Test Step 3: Deploy mac acl to allow source mac address")
    public void step3() {
        wvp.editVlanIpFilteringAllow(vlanName, ipaclName, ipaclIp);
    }

    @Step("Test Step 4: Deploy ip acl to deny source ip address")
    public void step4() {
        wvp.editVlanIpFilteringAllow(vlanName, ipaclName, ipaclIp);
    }

    @Step("Test Step 5: Deploy ip acl to allow source ip address")
    public void step5() {
        wvp.editVlanIpFilteringAllow(vlanName, ipaclName, ipaclIp);
    }

}
