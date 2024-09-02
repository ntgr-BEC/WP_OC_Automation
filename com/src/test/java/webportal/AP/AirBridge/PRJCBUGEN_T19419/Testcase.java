package webportal.AP.AirBridge.PRJCBUGEN_T19419;

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
    String abGroupName    = "grp19419";
    String abGroupNameNew = "grp19419new";
    
    @Feature("AP.AirBridge") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T19419") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify user can create more than one AB Group.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T19419") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WirelessAirBridgeGroupsPage wabg = new WirelessAirBridgeGroupsPage();
        wabg.gotoPage();
        wabg.deleteGroup(abGroupName);
        wabg.deleteGroup(abGroupNameNew);
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login into IM WP page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
    }
    
    @Step("Test Step 2: 5. Click on create a new air bridge group")
    public void step2() {
        new WirelessQuickViewPage();
        WirelessAirBridgeGroupsPage wabg = new WirelessAirBridgeGroupsPage();
        wabg.gotoPage();
        if (wabg.getGroups().size() == 0) {
            wabg.createGroup(abGroupName, true);
        }
        
        wabg.createGroup(abGroupNameNew, true);
        assertTrue(wabg.getGroups().contains(abGroupNameNew), "The user should be able to create air bridge-group successfully");
    }
}
