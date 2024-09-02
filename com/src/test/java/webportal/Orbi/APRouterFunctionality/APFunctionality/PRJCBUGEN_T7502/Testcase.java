package webportal.Orbi.APRouterFunctionality.APFunctionality.PRJCBUGEN_T7502;

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
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("APRouterFunctionality.APFunctionality") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7502") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the orbi take IP address automatically if dynamic option is enabled.") // It's a testcase title from Jira Test
                                                                                                        // Case.
    @TmsLink("PRJCBUGEN-T7502") // It's a testcase id/link from Jira Test Case.
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

    @Step("Test Step 3: Should be able to take IP address")
    public void step3() {
        DevicesOrbiWanIPPage page = new DevicesOrbiWanIPPage();

        assertTrue(page.isIPformat(page.txtIp), "check wan ip is ip format");
        assertTrue(page.isIPformat(page.txtMask), "check wan mask is ip format");
        assertTrue(page.isIPformat(page.txtGateway), "check wan gateway is ip format");
        assertTrue(page.isIPformat(page.txtDNS1), "check wan dns1 is ip format");
    }
}
