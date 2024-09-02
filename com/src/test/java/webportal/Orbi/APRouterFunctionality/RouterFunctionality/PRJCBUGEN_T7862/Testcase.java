package webportal.Orbi.APRouterFunctionality.RouterFunctionality.PRJCBUGEN_T7862;

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
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    boolean needrestore  = false;
    boolean originalmode = false;
    
    @Feature("APRouterFunctionality.RouterFunctionality") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7862") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("[WAN IP] Test to verify the toggle functionality of \"Assign IP address automatically\" button") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7862") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")

    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (needrestore) {
            DevicesOrbiWanIPPage page = new DevicesOrbiWanIPPage();
            page.setDHCP(originalmode);
            ddp.gotoPage();
            ddp.waitDevicesReConnected(WebportalParam.ob1serialNo);
        }
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

    @Step("Test Step 3: Change wan ip from static to dhcp")
    public void step3() {
        DevicesOrbiWanIPPage page = new DevicesOrbiWanIPPage();
        originalmode = page.isDHCP();
        if (!originalmode) {
            needrestore = true;
            page.setDHCP(true);
            ddp.gotoPage();
            ddp.waitDevicesReConnected(WebportalParam.ob1serialNo);
            ddp.openOBDevice();
            new DevicesOrbiWanIPPage();
            assertTrue(page.isDHCP(), "change to dhcp successfully");
        }
    }
}
