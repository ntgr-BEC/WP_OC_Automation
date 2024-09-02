package webportal.Switch.Radius.PRJCBUGEN_T11591;

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
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String vlanName    = "testvlan";
    String vlanId      = "1591";
    String networkName = "testnet" + vlanId;
    String ip1         = "11.22.33.44";
    String ip2         = "11.22.33.45";

    @Feature("Switch.Radius") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T11591") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("017 - Edit dot1x port auth mode under network setup") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T11591") // It's a testcase id/link from Jira Test Case.

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
    @Step("Test Step 1: Open page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }

    @Step("Test Step 2: Go to Location->Edit Location-> RADIUS")
    public void step2() {
        rdp.gotoPage();
        rdp.enableAuth(ip1, ip2);
    }

    @Step("Test Step 3: Go to Network Setup, create VLAN200 (select 5 ports of switch A, 10 ports of switch B)")
    public void step3() {
        netsp.gotoPage();
        netsp.createNetwork(networkName, vlanId, false);
    }

    @Step("Test Step 4: Go to local switch GUI, Radius server info is deployed and ports under VLAN200 stay \"Authorized\"")
    public void step4() {
        String tmpStr = SwitchCLIUtils.getRadiusInfo("g1");
        assertTrue(SwitchCLIUtils.RadiusClass.isServerConfiged, "check radius server option");
//        assertTrue(SwitchCLIUtils.RadiusClass.isEnabled, "check dot1x option");
        assertTrue(SwitchCLIUtils.RadiusClass.portStatus == 1, "check port mode in auth");
    }

    @Step("Test Step 5: Go to Location->Network Setup,edit vlan200,set port auth mode to \"Unauth\"")
    public void step5() {
        netsp.gotoPage();
        netsp.openNetwork(networkName);
        netsp.gotoStep(2);
        netsp.setPortRadius(WebportalParam.sw1deveiceName, "1", 2);
        netsp.setPortRadius(WebportalParam.sw2deveiceName, "1", 2);
        netsp.finishAllStep();
    }

    @Step("Test Step 6: Go to local switch GUI, the port auth change from \"Authorized\" to \"Unauthorized\"")
    public void step6() {
        String tmpStr = SwitchCLIUtils.getRadiusInfo("g1");
        assertTrue(SwitchCLIUtils.RadiusClass.isServerConfiged, "check radius server option");
//        assertTrue(SwitchCLIUtils.RadiusClass.isEnabled, "check dot1x option");
        assertTrue(SwitchCLIUtils.RadiusClass.portStatus == 2, "check port mode in unauth");
    }

    @Step("Test Step 7: Go to Location->Network Setup,edit vlan200,set port auth mode to \"Auto\"")
    public void step7() {
        netsp.gotoPage();
        netsp.openNetwork(networkName);
        netsp.gotoStep(2);
        netsp.setPortRadius(WebportalParam.sw1deveiceName, "1", 0);
        netsp.setPortRadius(WebportalParam.sw2deveiceName, "1", 0);
        netsp.finishAllStep();
    }

    @Step("Test Step 8: Go to local switch GUI, the port auth change from \"Unauthorized\" to \"Auto\"")
    public void step8() {
        String tmpStr = SwitchCLIUtils.getRadiusInfo("g1");
        assertTrue(SwitchCLIUtils.RadiusClass.isServerConfiged, "check radius server option");
//        assertTrue(SwitchCLIUtils.RadiusClass.isEnabled, "check dot1x option");
        assertTrue(SwitchCLIUtils.RadiusClass.portStatus == 0, "check port mode in auto");
    }
}
