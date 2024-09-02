package webportal.Switch.VlanOverhauling.PRJCBUGEN_T11330;

import static org.testng.Assert.assertFalse;
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
import webportal.param.WebportalParam;
/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String vlanName    = "testvlan";
    String vlanId      = "1330";
    String networkName = "testnet" + vlanId;

    @Feature("Switch.VlanOverhauling") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T11330") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("004-Create a network via the template of \"Data Network\" ") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T11330") // It's a testcase id/link from Jira Test Case.

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

    @Step("Test Step 2: Go to Location->Network page, and use template of Data Network\r\n"
            + "Configuration to create vlan 600，name data traffic vlan")
    public void step2() {
        netsp.gotoPage();
        netsp.createNetwork(networkName, 0, vlanName, vlanId);
    }

    @Step("Test Step 3: Create VLAN 600 success")
    public void step3() {
        netsp.gotoPage();
        assertTrue(netsp.getNetworks().contains(vlanId), "check vlan id is there " + vlanId);

        handle.waitCmdReady(vlanId, false);
        String tmpStr = "";
        if (WebportalParam.isRltkSW1) {
            tmpStr = handle.getCmdOutput("show running-config ", false);
        } else {
            tmpStr = handle.getCmdOutput("show vlan", false);
        }
        
        assertTrue(tmpStr.contains(vlanId), "check vlan is there " + vlanId);
    }

    @Step("Test Step 4: Assign port1 and port2 to vlan 600 with tag")
    public void step4() {
        netsp.gotoPage();
        netsp.openNetwork(vlanName);
        netsp.gotoStep(2);
        netsp.setNetwork2("1,2", 1, "", 0);
        netsp.finishAllStep();
    }

    @Step("Test Step 5: Check vlan 600 configuration by APP and GUI")
    public void step5() {
        assertTrue(netsp.getNetworks().contains(vlanName), "network is created on wp");
        assertTrue(SwitchCLIUtils.isTagPort("g1", vlanId), "port g1 is Tagged");
        assertTrue(SwitchCLIUtils.isTagPort("g2", vlanId), "port g2 is Tagged");
    }

    @Step("Test Step 6: Delete VLAN 600 by APP")
    public void step6() {
        netsp.gotoPage();
        netsp.deleteNetwork(vlanName);
    }

    @Step("Test Step 7: Delete VLAN success, check by APP and GUI")
    public void step7() {
        handle.waitCmdReady(vlanId, true);
        assertFalse(netsp.getNetworks().contains(vlanName), "network is deleted on wp");
        assertFalse(SwitchCLIUtils.isTagPort("g1", vlanId), "port g1 is not Tagged");
    }
}
