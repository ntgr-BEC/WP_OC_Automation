package webportal.Orbi.APRouterFunctionality.RouterFunctionality.PRJCBUGEN_T7836;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.RunCommand;
import webportal.param.WebportalParam;
import webportal.webelements.DevicesOrbiLANIPPageElement;
import webportal.weboperation.DevicesOrbiDeviceModePage;
import webportal.weboperation.DevicesOrbiLanIPPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    @Feature("APRouterFunctionality.RouterFunctionality") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7836") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the IP and MAC is well displayed on the device dashboard screen after assigning the static IP in router mode") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7836") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to WP/Select one Location/Click \"Device\" button")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        
        handle.gotoLoction();
        
        ddp.gotoPage();
        assertTrue(ddp.isDeviceConnected(WebportalParam.ob1serialNo), "device should be online with current mode");
    }
    
    @Step("Test Step 2: Choose online device, click \"Edit\" button")
    public void step2() {
        ddp.openOBDevice();
        new DevicesOrbiDeviceModePage(false).initDeviceMode(false);
    }
    
    @Step("Test Step 3: Change lan ip from dhcp to static")
    public void step3() {
        DevicesOrbiLanIPPage page = new DevicesOrbiLanIPPage();
        String sGet = DevicesOrbiLANIPPageElement.txtIp.getValue();
        assertTrue(RunCommand.isHostAlive(sGet, 443), "device should be reachable");
    }
}
