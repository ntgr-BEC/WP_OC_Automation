package webportal.Switch.VlanOverhauling.PRJCBUGEN_T11341;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.SwitchTelnet;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String vlanName    = "testvlan";
    String vlanId      = "1341";
    String networkName = "testnet" + vlanId;

    @Feature("Switch.VlanOverhauling") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T11341") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("075-Create a network and get dynamic IP address") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T11341") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1")
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

    @Step("Test Step 2: Assign port1 and port2 to vlan 600 with tag/Setting DHCP IP")
    public void step2() {
        netsp.gotoPage();
        netsp.openNetwork("Management VLAN");
        netsp.gotoStep(4);
        netsp.setNetwork4(true, "192.168.111.2", "192.168.111.3");
        netsp.finishAllStep();
        
        netsp.clickAdd();
        netsp.setNetwork1(networkName, null, 0, vlanName, vlanId);
        netsp.gotoStep(4);
        netsp.setNetwork4(true, "192.168.111.2", "192.168.111.3");
        netsp.finishAllStep();
    }

    @Step("Test Step 3: Check vlan 600 configuration by APP and GUI")
    public void step3() {
        handle.waitCmdReady(vlanId, false);
        SwitchTelnet switchTelnet = new SwitchTelnet(WebportalParam.sw1IPaddress, false);
        assertTrue(switchTelnet.checkVlanDHCP(vlanId), "By default dhcp mode is on for vlan");
    }
}
