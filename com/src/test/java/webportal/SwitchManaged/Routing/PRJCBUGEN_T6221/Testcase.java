package webportal.SwitchManaged.Routing.PRJCBUGEN_T6221;

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
import webportal.weboperation.AccountPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String vlanName = "testvlan";
    String vlanId   = "101";
    String ipMask   = "255.255.255.0";
    String ip1      = "120.1.1.1";
    String ip2      = "120.1.1.2";
    String gateway  = "121.1.1.1";

    @Feature("Switch.Routing") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T6221") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("006-Add gateway to a unknown ip address") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T6221") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        SwitchCLIUtils.cleanIpRouter();
        wvp.gotoPage();
        wvp.deleteAllVlan();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create a new vlan 100")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wvp.gotoPage();
        wvp.newVlan(vlanName, vlanId, 0);

        new AccountPage().enterEditNetworkPage();
        rtp.gotoPage();
    }

    @Step("Test Step 2: Add ip address and mask to vlan 100")
    public void step2() {
        rtp.deleteVlanRoute(vlanId);
        rtp.addIpToVlan(vlanName, ipMask, ip1, ip2);
    }

    @Step("Test Step 3: Add gateway to a unknown ip address.")
    public void step3() {
        rtp.setGateway(gateway);
    }

    @Step("Test Step 4: Check configuraiton from app and local web gui")
    public void step4() {
        handle.waitCmdReady(gateway, false);
        String tmpStr = handle.getCmdOutputShowRunningConfig(false);
        assertTrue(tmpStr.contains(ip1) || tmpStr.contains(ip2), "check ip1&ip2");
        assertTrue(tmpStr.contains(ipMask) || tmpStr.contains("/24"), "check mask");
    }
}
