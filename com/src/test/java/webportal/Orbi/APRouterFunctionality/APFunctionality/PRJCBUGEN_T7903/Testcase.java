package webportal.Orbi.APRouterFunctionality.APFunctionality.PRJCBUGEN_T7903;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.BRUtils;
import util.MyCommonAPIs;
import webportal.weboperation.DevicesOrbiDeviceModePage;
import webportal.weboperation.DevicesOrbiSatellitesPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String toName = "Ceiling Orbi Pro-sss~!@#$%^&*()__+";

    @Feature("APRouterFunctionality.APFunctionality") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7903") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the behavior if the user enters a name with special characters.") // It's a testcase title
                                                                                                   // from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7903") // It's a testcase id/link from Jira Test Case.
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
        new DevicesOrbiDeviceModePage(false).initDeviceMode(true);
    }

    @Step("Test Step 3: Rename connected satellite, enters a name with special characters")
    public void step3() {
        DevicesOrbiSatellitesPage page = new DevicesOrbiSatellitesPage();
        page.changeIndoorName(toName);
        assertTrue(handle.getPageErrorMsg().length() > 10, "Error for not support special char");
        handle.refresh();
    }

    @Step("Test Step 4: Rename connected satellite, enters a name with special characters")
    public void step4() {
        toName = "Ceiling Orbi Pro-sss-__123";
        DevicesOrbiSatellitesPage page = new DevicesOrbiSatellitesPage();
        page.changeIndoorName(toName);
        handle.refresh();
        MyCommonAPIs.sleepsync();
        assertTrue(new BRUtils(BRUtils.api_satellites_details, 4).Dump().contains(toName), "satellites should be changed with special char");
    }
}
