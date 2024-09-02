package webportal.AP.AirBridge.PRJCBUGEN_T17185;

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
    @Story("PRJCBUGEN_T17185") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify if the Management SSID can be enabled and disabled, the same takes effect on the device.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T17185") // It's a testcase id/link from Jira Test Case.
    
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
    
    @Step("Test Step 2: 6. Change managessid to idle off")
    public void step2() {
        ddp.gotoPage();
        ddp.enterDevice(WebportalParam.ap1serialNo);
        WirelessAirBridgeGroupsPage wabg = new WirelessAirBridgeGroupsPage();
        wabg.selectSubDevicePage(1);
        
        boolean toset = !wabg.getManagementSSIDStatus();
        wabg.editDeviceConfiguration(toset);
        
        handle.refresh();
        assertTrue(toset == wabg.getManagementSSIDStatus(), "verify managessid status is: " + (toset ? "on" : "off"));

        toset = !wabg.getManagementSSIDStatus();
        wabg.editDeviceConfiguration(toset);
        
        handle.refresh();
        assertTrue(toset == wabg.getManagementSSIDStatus(), "verify managessid status is: " + (toset ? "on" : "off"));
    }
}
