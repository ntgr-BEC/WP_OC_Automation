package webportal.Orbi.OrbiTestCases_IM63.Router.PRJCBUGEN_T26452;

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
import webportal.weboperation.DevicesOrbiDeviceModePage;
import webportal.weboperation.DevicesOrbiWanIPPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author ann.fang
 */
public class Testcase extends TestCaseBase {
    boolean needrestore  = true;
    boolean originalmode = false;

    @Feature("Orbi.OrbiTestCases_IM63") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T26452") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify changing from static WAN IP to a DHCP IP") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T26452") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1")
    
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (needrestore) {
            DevicesOrbiWanIPPage page = new DevicesOrbiWanIPPage();
            page.setDHCP(originalmode);
            ddp.gotoPage();
            ddp.waitDevicesReConnected(WebportalParam.ob2serialNo);
            util.MyCommonAPIs.sleepi(100);
        }
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to WP/Select one Location/Click \"Device\" button")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
        ddp.gotoPage();
        assertTrue(ddp.isDeviceConnected(WebportalParam.ob2serialNo), "device should be online with current mode");
    }
    
    @Step("Test Step 2: Choose online device, click \"Edit\" button")
    public void step2() {
        ddp.openOBDevice();
        new DevicesOrbiDeviceModePage(false).initDeviceMode(false);
    }
    
    @Step("Test Step 3: If original WAN IP is dhcp, change it to static first")
    public void step3() {
        DevicesOrbiWanIPPage page = new DevicesOrbiWanIPPage();
        originalmode = page.isDHCP();
        if (originalmode) {
            needrestore = false;
            page.setDHCP(false);
            ddp.gotoPage();
            ddp.waitDevicesReConnected(WebportalParam.ob2serialNo);
            util.MyCommonAPIs.sleepi(100);
        }
    }
    
    @Step("Test Step 4: Change WAN IP from static to DHCP")
    public void step4() {
        ddp.gotoPage();
        ddp.openOB2();
        DevicesOrbiWanIPPage page = new DevicesOrbiWanIPPage();
        page.setDHCP(true);
    }
    
    @Step("Test Step 5: Check")
    public void step5() {
        ddp.gotoPage();
        ddp.openOB2();
        DevicesOrbiWanIPPage page = new DevicesOrbiWanIPPage();
        boolean currentmode = page.isDHCP();
        assertTrue(currentmode, "current wan ip is dhcp");
    }
    
}
