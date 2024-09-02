package webportal.SwitchManaged.VlanOverhauling.PRJCBUGEN_T11315;

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
import util.BRUtils;
import util.SwitchCLIUtilsMNG;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String vlanName    = "testvlan";
    String vlanId      = "1315";
    String networkName = "testnet" + vlanId;

    @Feature("Switch.VlanOverhauling") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T11315") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("028-config vlan network for 2*switch and 1*BR500 at the same time") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T11315") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p3")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        netsp.gotoPage();
        netsp.deleteAllNetwork();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open page and goto Vlan Page, Find two switch and one BR500 from app")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        ddpmg.gotoPage();
        ddpmg.getSWDevice();
        ddpmg.getBRDevice();
    }

    @Step("Test Step 2: Config vlan100 for two switch and one BR500 at same time")
    public void step2() {
        netsp.gotoPage();
        netsp.createNetwork(networkName, 0, vlanName, vlanId);
    }

    @Step("Test Step 3: check vlan 100 on two switch and one BR500")
    public void step3() {
        handle.waitCmdReady(vlanId, false);
        String tmpStr = handle.getCmdOutput("show vlan", false);
        assertTrue(tmpStr.contains(vlanId), "vlan should be created on switch");

        assertFalse(new BRUtils(BRUtils.api_lan_port_stats, 0).Dump().contains(vlanId), "vlan should not be created on br");
    }

    @Step("Test Step 4: on switch 1 assign port 1,2,3,4 to vlan 100 on switch 2 assign port 5,6,7,8 to vlan 100 on BR500, assign port4 to VLAN100")
    public void step4() {
        netsp.gotoPage();
        netsp.openNetwork(vlanName);
        netsp.gotoStep(2);
        netsp.setPortMembers(WebportalParam.sw1deveiceName, "1,2", 0);
        netsp.setPortMembers(WebportalParam.sw2deveiceName, "3,4", 1);
        netsp.setPortMembers(WebportalParam.br1deveiceName, "1,2", 1);
        netsp.finishAllStep();
        handle.sleepsync();
        handle.sleepsync();
    }

    @Step("Test Step 5: check vlan 100 on two switch and one BR500")
    public void step5() {
        assertTrue(SwitchCLIUtilsMNG.isPortInVlan("g1", vlanId), "g1 is added to vlan on switch");

        String tagPort = vlanId + "t";
        handle.waitRestReady(BRUtils.api_lan_port_stats, tagPort, false, 0);
        assertTrue(new BRUtils().Dump().contains(tagPort), "port1 should be tagged in vlan on br");
    }

    @Step("Test Step 6: Delete vlan 100 on two switch at same time")
    public void step6() {
        netsp.gotoPage();
        netsp.deleteAllNetwork();
        handle.sleepsync();
    }

    @Step("Test Step 7: Vlan 100 is deleted on two switchs and BR500")
    public void step7() {
        String tmpStr = handle.getCmdOutput("show vlan", true);
        assertFalse(tmpStr.contains(vlanId), "vlan should be deleted on switch");

        String tagPort = vlanId + "t";
        handle.waitRestReady(BRUtils.api_lan_port_stats, tagPort, true, 0);
        assertFalse(new BRUtils().Dump().contains(tagPort), "port1 should not be tagged in vlan on br");
    }

}
