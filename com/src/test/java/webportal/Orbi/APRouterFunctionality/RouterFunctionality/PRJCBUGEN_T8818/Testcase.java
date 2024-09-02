package webportal.Orbi.APRouterFunctionality.RouterFunctionality.PRJCBUGEN_T8818;

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
import webportal.weboperation.DevicesOrbiWifiNetworkPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    boolean needrestore  = false;
    boolean originalmode = false;

    @Feature("APRouterFunctionality.RouterFunctionality") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T8818") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify the validation for Password Authentication filed under Guest Portal SSID") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T8818") // It's a testcase id/link from Jira Test Case.
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
    }

    @Step("Test Step 2: Choose online device, click \"Edit\" button")
    public void step2() {
        ddp.openOBDevice();
        new DevicesOrbiDeviceModePage(false).initDeviceMode(false);
    }

    @Step("Test Step 3: Click \"WiFi Network\" Tab, click  \"Guest Portal \"\"")
    public void step3() {
        DevicesOrbiWifiNetworkPage page = new DevicesOrbiWifiNetworkPage();
        String sGet = page.getWifiGuestCP();
        page.editGuestCPSSID(false, "obri12", true, "1", 1, "1234567890123456789012345678901234567890123456789012345678901234");
        
        assertTrue(handle.getPageErrorMsg().length() > 1, "check error msg on too long passwd");
    }
}
