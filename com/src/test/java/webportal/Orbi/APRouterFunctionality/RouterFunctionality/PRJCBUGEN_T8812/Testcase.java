package webportal.Orbi.APRouterFunctionality.RouterFunctionality.PRJCBUGEN_T8812;

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
import webportal.weboperation.DevicesOrbiTrafficPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    @Feature("APRouterFunctionality.RouterFunctionality") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T8812") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify able to get yesterday traffic used data.")
    @TmsLink("PRJCBUGEN-T8812") // It's a testcase id/link from Jira Test Case.
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
    }

    @Step("Test Step 2: Choose online device, click \"Edit\" button")
    public void step2() {
        ddp.openOBDevice();
        new DevicesOrbiDeviceModePage(false).initDeviceMode(false);
    }

    @Step("Test Step 3: Verify able to get the yesterday traffic or not.")
    public void step3() {
        DevicesOrbiTrafficPage page = new DevicesOrbiTrafficPage();
        assertTrue(page.getDownloads().size() == 4, "Check there should be 4 bar, the 2nd one is for yesterday");
        assertTrue(Float.parseFloat(page.getDownloads().get(1)) >= 0, "Show month download value");
        assertTrue(Float.parseFloat(page.getUploads().get(1)) >= 0, "Show month upload value");
    }
}
