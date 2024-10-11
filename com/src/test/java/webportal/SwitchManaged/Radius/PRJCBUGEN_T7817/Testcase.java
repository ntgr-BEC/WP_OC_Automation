package webportal.SwitchManaged.Radius.PRJCBUGEN_T7817;

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
    String vlanName = "testvlan";
    String lagName  = "testlag7817";
    String vlanId   = "816";
    String ip1      = "11.22.33.44";
    String ip2      = "11.22.33.45";

    @Feature("Switch.Radius") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7817") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("011 - Deploy \"Authorized\" mode to per switch on vlan level") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7817") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        wlp.gotoLagPage();
        wlp.deleteLag();
        wlp.deleteLagCli();
        
        rcp.gotoPage();
        rcp.disableRadius(false);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open page, add vlan and lag")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        wvp.gotoPage();
        wvp.newVlan(vlanName, vlanId, 0);

        wlp.gotoLagPage();
        wlp.addLag(lagName, false, false);
    }

    @Step("Test Step 2: Go to Location->Edit Location-> RADIUS")
    public void step2() {
        rdp.gotoPage();
        rdp.enableAuth(ip1, ip2);
    }

    @Step("Test Step 3: Go Enable radius server option on VLAN200 level")
    public void step3() {
        wvp.gotoPage();
        wvp.editPortMode(vlanId, true, true, false);
        MyCommonAPIs.sleepsync();
    }

    @Step("Test Step 4: Go to local switch GUI, Radius server info is deployed and enable on VLAN200")
    public void step4() {
        handle.waitCmdReady(lagName, false);
        String tmpStr = SwitchCLIUtils.getRadiusInfo("g" + WebportalParam.sw1LagPort1);
        assertTrue(SwitchCLIUtils.RadiusClass.isEnabled, "check radius option is enabled");
        assertTrue(SwitchCLIUtils.RadiusClass.portStatus == 1, "check radius option on port");
    }
}
