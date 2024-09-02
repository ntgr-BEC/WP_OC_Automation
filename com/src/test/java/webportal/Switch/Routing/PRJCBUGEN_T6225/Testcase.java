package webportal.Switch.Routing.PRJCBUGEN_T6225;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.weboperation.AccountPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String vlanName = "testvlan";
    String vlanId   = "105";
    String ipMask   = "255.255.255.0";
    String ip1      = "105.1.1.1";
    String ip2      = "105.1.1.2";

    @Feature("Switch.Routing") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T6225") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("010-Add ip address to vlan create via \"Create Data Network\"") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T6225") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p3") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
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
        rtp.addIpToVlan(vlanId, ipMask, ip1, ip2);
    }

    @Step("Test Step 3: Check configuraiton from app and local web gui")
    public void step3() {
        handle.waitCliReady("show running-config", "105.1.1", false);
        String tmpStr = handle.getCmdOutput("show running-config", false);
        assertTrue(tmpStr.contains(ip1) || tmpStr.contains(ip2), "verify ip is set");
    }

}
