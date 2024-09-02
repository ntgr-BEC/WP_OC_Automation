package webportal.Switch.VlanOverhauling.PRJCBUGEN_T11324;

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
    String vlanName    = "testvlan";
    String vlanId      = "1320";
    String networkName = "testnet" + vlanId;

    @Feature("Switch.VlanOverhauling") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T11324") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("013-Delete default vlan network") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T11324") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p3")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open page and goto Vlan Page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }

    @Step("Test Step 2: Delete the default VLAN on Insight")
    public void step2() {
        netsp.gotoPage();
        int before = netsp.getNetworks().size();
        netsp.deleteNetwork("Management");
        netsp.deleteNetwork("vlan2");
        int after = netsp.getNetworks().size();
        assertTrue(before == after, "either mana/vlan2 is removed");
    }
}
