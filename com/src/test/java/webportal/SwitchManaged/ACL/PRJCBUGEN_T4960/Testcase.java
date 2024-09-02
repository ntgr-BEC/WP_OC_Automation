package webportal.SwitchManaged.ACL.PRJCBUGEN_T4960;

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
    String tclname = getClass().getName();

    String        vlanName = "testvlan";
    String        tmpStr;

    String ipaclName = "test";
    String ipaclIp   = "1.1.1.1";
    String ipaclMac  = "11:22:33:11:22:33";

    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4960") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("022-Multiple ACL binding to one vlan") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4960") // It's a testcase id/link from Jira Test Case.
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
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wvp.gotoPage();
        wvp.openVlan(vlanName, "50", 0);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: ​​​​​Create 1 IP ACL,binding to one vlan")
    public void step2() {
        wvp.editVlanIpFilteringAllow(vlanName, ipaclName, ipaclIp);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: Create 1 MAC ACL,binding to the same vlan")
    public void step3() {
        wvp.editVlanMacAuthAllow(vlanName, ipaclName, ipaclMac);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 4: ​​​​​IP ACL is configed on switch,all data is right on switch")
    public void step4() {
        handle.waitCmdReady(ipaclMac, false);
        tmpStr = handle.getCmdOutputShowRunningConfig(false);
        assertTrue(tmpStr.contains(ipaclIp));
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 5: ​​​​​​​​​​MAC ACL is configed on switch,all data is right on switch")
    public void step5() {
        assertTrue(tmpStr.contains(ipaclMac));
    }

}
