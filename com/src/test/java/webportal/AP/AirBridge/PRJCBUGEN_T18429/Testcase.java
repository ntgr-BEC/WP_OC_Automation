package webportal.AP.AirBridge.PRJCBUGEN_T18429;

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
    String abGroupName = "grp18429";
    
    @Feature("AP.AirBridge") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T18429") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify if the user can create AB Group from the wireless page") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T18429") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
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
    
    @Step("Test Step 2: 4. Click on the cross icon to add the group")
    public void step2() {
        new WirelessQuickViewPage();
        WirelessAirBridgeGroupsPage wabg = new WirelessAirBridgeGroupsPage();
        wabg.gotoPage();
        wabg.createGroup(abGroupName, false);
        assertTrue(wabg.getGroups().contains(abGroupName), "The user should be successfully able to save the group");
    }
}
