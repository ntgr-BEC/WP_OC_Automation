package webportal.Orbi.APRouterFunctionality.RouterFunctionality.PRJCBUGEN_T7849;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.weboperation.DevicesOrbiDeviceModePage;
import webportal.weboperation.DevicesOrbiWanIPPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    /**
     * 0 - dhcp, 1 - static
     */
    int getmode = 9;
    
    @Feature("APRouterFunctionality.RouterFunctionality") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7849") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the user is able to assign static WAN IP and it gets updated on the WAN IP tile on the device dashboard.")
    @TmsLink("PRJCBUGEN-T7849") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (getmode != 9) {
            DevicesOrbiWanIPPage page = new DevicesOrbiWanIPPage();
            if (page.isDHCP() && (1 == getmode)) {
                page.setDHCP(false);
            } else if (!page.isDHCP() && (0 == getmode)) {
                page.setDHCP(true);
            }
        }
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to WP/Select one Location/Click \"Device\" button")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        
        handle.gotoLoction();
        
        ddp.gotoPage();
    }
    
    @Step("Test Step 2: Choose online device, click \"Edit\" button")
    public void step2() {
        ddp.openOBDevice();
        new DevicesOrbiDeviceModePage(false).initDeviceMode(false);
    }
    
    @Step("Test Step 3: Set WAN ip to static")
    public void step3() {
        DevicesOrbiWanIPPage page = new DevicesOrbiWanIPPage();
        getmode = page.isDHCP() ? 0 : 1;
        if (0 == getmode) {
            page.setDHCP(false);
            handle.refresh();
        }
        assertTrue(!page.isDHCP(), "DHCP can be disabled");
    }
}
