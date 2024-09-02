package webportal.Orbi.OrbiTestCases.PRJCBUGEN_T25535;

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
 * @author ann.fang
 */
public class Testcase extends TestCaseBase {
    @Feature("OrbiTestCases") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25535") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the traffic usage information on the traffic page")
    @TmsLink("PRJCBUGEN-T25535") // It's a testcase id/link from Jira Test Case.
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

    @Step("Test Step 3: Verify able to get the weekly traffic or not.")
    public void step3() {
        DevicesOrbiTrafficPage page = new DevicesOrbiTrafficPage();
        assertTrue(page.getDownloads().size() == 4, "Check there should be 4 bar");
        assertTrue(Float.parseFloat(page.getDownloads().get(0)) >= 0, "Show today download value");
        assertTrue(Float.parseFloat(page.getUploads().get(0)) >= 0, "Show today upload value");
        assertTrue(Float.parseFloat(page.getDownloads().get(1)) >= 0, "Show yesterday download value");
        assertTrue(Float.parseFloat(page.getUploads().get(1)) >= 0, "Show yesterday upload value");
        assertTrue(Float.parseFloat(page.getDownloads().get(2)) >= 0, "Show week download value");
        assertTrue(Float.parseFloat(page.getUploads().get(2)) >= 0, "Show week upload value");
        assertTrue(Float.parseFloat(page.getDownloads().get(3)) >= 0, "Show month download value");
        assertTrue(Float.parseFloat(page.getUploads().get(3)) >= 0, "Show month upload value");
    }
}
