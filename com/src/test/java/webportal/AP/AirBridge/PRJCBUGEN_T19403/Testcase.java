package webportal.AP.AirBridge.PRJCBUGEN_T19403;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessAirBridgeGroupsPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    @Feature("AP.AirBridge") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T19403") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify if the P2P bridge link connection remains established upon changing the air bridge group channel width") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T19403") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login into IM WP page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
    }
    
    @Step("Test Step 2: 10. Change the air bridge channel width")
    public void step2() {
        WirelessAirBridgeGroupsPage wabg = new WirelessAirBridgeGroupsPage();
        wabg.gotoPage();
        wabg.abGroupData.name = null;
        wabg.abGroupData.channelWidth = null;
        wabg.editGroupConfig(wabg.sDefaultAirBridgeGroup);
        ddp.gotoPage();
        ddp.waitDevicesReConnected(WebportalParam.ap2serialNo);

        wabg.gotoPage();
        wabg.openGroup(wabg.sDefaultAirBridgeGroup);
        wabg.selectSubGroupPage(2);
        wabg.selectSubViewInABGDevice(2);
        assertTrue(wabg.isDeviceConnected(WebportalParam.ap2deveiceName),
                "The bridge link should break and re-establish after changing the channel width");
    }
}
