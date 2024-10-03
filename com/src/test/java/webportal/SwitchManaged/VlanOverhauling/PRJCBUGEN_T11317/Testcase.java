package webportal.SwitchManaged.VlanOverhauling.PRJCBUGEN_T11317;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import util.SwitchCLIUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String vlanName    = "testvlan";
    String vlanId      = "1317";
    String networkName = "testnet" + vlanId;
    String lagName     = "testlag1317";

    @Feature("Switch.VlanOverhauling") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T11317") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("020-ADD lag port to network") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T11317") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        netsp.gotoPage();
        netsp.deleteNetwork(vlanName);
        wlp.gotoLagPage();
        wlp.deleteLag();
        wlp.deleteLagCli();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open page and goto Vlan Page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }

    @Step("Test Step 2: Create vlan100，Create LGA1 with member ports 1,2")
    public void step2() {
        wlp.gotoLagPage();
        wlp.addLag(lagName, true, false);
        handle.waitCmdReady(lagName, false);

        netsp.gotoPage();
        netsp.createNetwork(networkName, 0, vlanName, vlanId);
        handle.waitCmdReady("vlan " + vlanId, false);
    }

    @Step("Test Step 3: Go to vlan 100, on the Port Members select port1 or port2, Click Access port")
    public void step3() {
        netsp.gotoPage();
        netsp.openNetwork(vlanName);
        netsp.gotoStep(2);
        netsp.clickPort(true, WebportalParam.sw1deveiceName, 4);
        MyCommonAPIs.sleep(5, "wait lag being selected");
        netsp.clickPortMode(0);
        netsp.finishAllStep();
        MyCommonAPIs.sleepsync();
    }

    @Step("Test Step 4: Lag1 is untaged in vlan 100")
    public void step4() {
        assertTrue(SwitchCLIUtils.isLagPort(WebportalParam.getSwitchLag(false, false)), "g4 is added to lag");
        assertTrue(SwitchCLIUtils.isLagPort(WebportalParam.getSwitchLag(false, true)), "g5 is added to lag");
        assertTrue(SwitchCLIUtils.isPortInVlan(WebportalParam.getSwitchLag(false, false), vlanId), "g4 is added to vlan");
        assertTrue(SwitchCLIUtils.isPortInVlan(WebportalParam.getSwitchLag(false, true), vlanId), "g5 is added to vlan");
        assertTrue(!SwitchCLIUtils.isTagPort(WebportalParam.getSwitchLag(false, false), vlanId), "g4 is untag");
        assertTrue(!SwitchCLIUtils.isTagPort(WebportalParam.getSwitchLag(false, true), vlanId), "g5 is untag");
    }

    @Step("Test Step 5: Go to vlan 100,and change lag1 type to Trunk port")
    public void step5() {
        netsp.gotoPage();
        netsp.openNetwork(vlanName);
        netsp.gotoStep(2);
        netsp.clickPort(true, WebportalParam.sw1deveiceName, 4);
        MyCommonAPIs.sleep(5, "wait lag being selected");
        netsp.clickPortMode(1);
        netsp.finishAllStep();
        MyCommonAPIs.sleepsync();
    }

    @Step("Test Step 6: Lag1 is taged in vlan 100")
    public void step6() {
        assertTrue(SwitchCLIUtils.isTagPort(WebportalParam.getSwitchLag(false, false), vlanId), "g4 is tag");
        assertTrue(SwitchCLIUtils.isTagPort(WebportalParam.getSwitchLag(false, true), vlanId), "g5 is tag");
    }
}
