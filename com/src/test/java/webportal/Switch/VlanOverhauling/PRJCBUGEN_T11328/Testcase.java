package webportal.Switch.VlanOverhauling.PRJCBUGEN_T11328;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.SwitchCLIUtils;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String vlanName    = "testvlan";
    String vlanId      = "4088";
    String networkName = "testnet" + vlanId;

    @Feature("Switch.VlanOverhauling") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T11328") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("002-Verify OUI-based VoIP function via default template of Voice") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T11328") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        netsp.gotoPage();
        netsp.deleteAllNetwork();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open page and goto Vlan Page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }

    @Step("Test Step 2: Insight APP go to Network Level Configuration, Create VLAN via default template of Voice VLAN")
    public void step2() {
        netsp.gotoPage();
        netsp.createNetwork(networkName, 1, vlanName, vlanId);
        handle.waitCmdReady(vlanId, false);
    }

    @Step("Test Step 3: Operation success, default Voice VLAN ID is 4088. And the Voice VLAN template change to grey and cannot be clicked again")
    public void step3() {
        netsp.gotoPage();
        netsp.openNetwork(vlanId);
        netsp.gotoStep(2);
        assertTrue(netsp.lbTrafficPriority.getSelectedText().contains("7"), "By default Traffic Priority should be 7");
    }

    @Step("Test Step 4: Check all ports: Auto VoIP Mode should be Enable, Operational Status should be Up, Prioritization Type should be Remark and Class Value should be 7")
    public void step4() {
        assertTrue(SwitchCLIUtils.getVoiceVlan().contains(vlanId), "Voice vlan id should be 4088");
        assertTrue(SwitchCLIUtils.checkVoiceVlan(), "Auto VoIP Mode should be Enable");
        assertTrue(SwitchCLIUtils.getVoiceInfo(1, 0).contains("7"), "Class Value should be 7");
    }
}
