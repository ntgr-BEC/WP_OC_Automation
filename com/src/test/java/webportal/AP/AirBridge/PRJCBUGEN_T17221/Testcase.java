package webportal.AP.AirBridge.PRJCBUGEN_T17221;

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
import webportal.weboperation.WirelessAirBridgeGroupsPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String abGroupName = "grp17221";
    String apNo        = null;

    @Feature("AP.AirBridge") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T17221") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify the user is able to remove and re add the satellite in the same location picking the same AB Group") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T17221") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        apNo = handle.getFakeDeviceNo("WAC502");
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WirelessAirBridgeGroupsPage wabg = new WirelessAirBridgeGroupsPage();
        wabg.gotoPage();
        wabg.deleteGroup(abGroupName);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login into IM WP page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }

    @Step("Test Step 2: Add 2 APs into AB Group")
    public void step2() {
        new WirelessQuickViewPage();
        WirelessAirBridgeGroupsPage wabg = new WirelessAirBridgeGroupsPage();
        wabg.gotoPage();
        wabg.createGroup(abGroupName, true);
        wabg.gotoPage();
        wabg.openGroup(abGroupName);
        wabg.AddAPToGroup(abGroupName, apNo);
    }

    @Step("Test Step 3: Remove this AP and add again")
    public void step3() {
        ddp.gotoPage();
        ddp.deleteDeviceYes(apNo);
        
        new WirelessQuickViewPage();
        WirelessAirBridgeGroupsPage wabg = new WirelessAirBridgeGroupsPage();
        wabg.gotoPage();
        wabg.openGroup(abGroupName);
        wabg.selectSubGroupPage(2);
        assertTrue(wabg.getAPCount() == 1, "check ap is removed");

        wabg.gotoPage();
        wabg.openGroup(abGroupName);
        wabg.AddAPToGroup(abGroupName, apNo);

        wabg.gotoPage();
        wabg.openGroup(abGroupName);
        wabg.selectSubGroupPage(2);
        assertTrue(wabg.getAPCount() == 2, "check ap is added again");
    }

}
