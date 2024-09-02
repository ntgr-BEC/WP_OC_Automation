package webportal.BR.BRVPNSite2Site.PRJCBUGEN_T7205;

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
    String groupName1 = "test1vpn";
    String groupName2 = "test2vpn";

    @Feature("BR.BRVPNSite2Site") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7205") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("006-Create two VPN Group ") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7205") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        brdvup.gotoPage();
        brdvup.deleteAllUser();
        brdvgp.gotoPage();
        brdvgp.deleteAllGroups();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: IM APP discover and manage BR500")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.openOneBRDevice(true);
    }

    @Step("Test Step 2: Create two VPN Group by IM APP;")
    public void step2() {
        brdvgp.gotoPage();
        brdvgp.deleteAllGroups();
        brdvgp.createVPNGroup(groupName1);
        brdvgp.createVPNGroup(groupName2);
        handle.refresh();
    }

    @Step("Test Step 3: Add BR1 and BR2 to VPN Group 1")
    public void step3() {
        brdvgp.addDeviceGroup(groupName1, 2);
    }

    @Step("Test Step 4: then add BR3 to VPN Group2")
    public void step4() {
        brdvgp.addDeviceGroup(groupName2, 1);
    }

    @Step("Test Step 5: Config success, check VPN Group detailed info via IM APP and BR GUI")
    public void step5() {
        assertTrue(brdvgp.getDevices(groupName1).size() == 2, "2 devices cannot be added");
        assertTrue(brdvgp.getDevices(groupName2).size() == 1, "1 device cannot be added");
    }

}
