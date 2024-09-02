package webportal.BR.BRVPNSite2Site.PRJCBUGEN_T7209;

import static org.testng.Assert.assertFalse;

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
    String groupName = "testvpn";

    @Feature("BR.BRVPNSite2Site") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7209") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("012-Delete the VPN Group") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7209") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: IM APP discover and manage BR500")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.openOneBRDevice(true);
    }

    @Step("Test Step 2: Create one VPN Group, add 3 BR to the Group")
    public void step2() {
        brdvgp.gotoPage();
        brdvgp.createVpnGroupWithDevices(groupName);
        handle.refresh();
    }

    @Step("Test Step 3: Delete the VPN Group ")
    public void step3() {
        brdvgp.gotoPage();
        brdvgp.deleteGroup(groupName);
        assertFalse(brdvgp.getGroups().contains(groupName), "group was not deleted right");
    }

}
