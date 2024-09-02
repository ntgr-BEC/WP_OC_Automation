package webportal.AP.AirBridge.PRJCBUGEN_T19416;

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
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessAirBridgeGroupsPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    @Feature("AP.AirBridge") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T19416") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify the troubleshoot tile functionality") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T19416") // It's a testcase id/link from Jira Test Case.
    
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
    
    @Step("Test Step 2: Select the devices and then click on Test Now")
    public void step2() {
        ddp.gotoPage();
        ddp.enterDevice(WebportalParam.ap1serialNo);
        WirelessAirBridgeGroupsPage wabg = new WirelessAirBridgeGroupsPage();
        
        wabg.selectSubDevicePage(4);
        wabg.RunDNSLookupSpeedTest(0);
        assertTrue(MyCommonAPIs.getText(wabg.txtDNSResultHost()).length() > 1, "verify host name");
        assertTrue(MyCommonAPIs.getText(wabg.txtDNSResultTime()).length() > 1, "verify lookup time");
        assertTrue(wabg.getDNSResultIp().size() > 0, "verify lookup ip");

        wabg.selectSubDevicePage(5);
        wabg.RunDNSLookupSpeedTest(1);
        assertTrue(wabg.getSpeedResult(0).length() > 1, "verify upload speed");
        assertTrue(wabg.getSpeedResult(1).length() > 1, "verify download speed");
        assertTrue(wabg.getSpeedResult(2).length() > 1, "verify delay time of speed");
    }
}
