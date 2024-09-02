package webportal.Orbi.APRouterFunctionality.RouterFunctionality.PRJCBUGEN_T7853;

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
import webportal.publicstep.PublicButton;
import webportal.weboperation.DevicesOrbiDeviceModePage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    PublicButton publicButton = new PublicButton();
    String       predate      = "";
    String       curdate      = "";

    @Feature("APRouterFunctionality.RouterFunctionality") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7853") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the orbi goes to reboot when the user clicks on \"reload configuration\"")
    @TmsLink("PRJCBUGEN-T7853") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")

    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to WP/Select one Location/Click \"Device\" button")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();

        handle.gotoLoction();

        ddp.gotoPage();
        predate = ddp.getDeviceUptime(WebportalParam.ob1serialNo, true);
    }

    @Step("Test Step 2: Verify the orbi goes to reboot when the user clicks on \"reload configuration\"")
    public void step2() {
        ddp.openOBDevice();
        new DevicesOrbiDeviceModePage(false).initDeviceMode(false);
        ddp.gotoPage();
        ddp.rebootDevice(WebportalParam.ob1serialNo);

        ddp.gotoPage();
        ddp.waitDevicesReConnected(WebportalParam.ob1serialNo);
        MyCommonAPIs.sleepsync();

        ddp.gotoPage();
        curdate = ddp.getDeviceUptime(WebportalParam.ob1serialNo, true);
        assertTrue(Integer.parseInt(predate) > Integer.parseInt(curdate), "check reboot is done");
    }
}
