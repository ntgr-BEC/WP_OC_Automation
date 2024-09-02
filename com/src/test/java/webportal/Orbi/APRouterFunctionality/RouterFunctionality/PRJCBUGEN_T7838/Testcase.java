package webportal.Orbi.APRouterFunctionality.RouterFunctionality.PRJCBUGEN_T7838;

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
import webportal.weboperation.DevicesOrbiDHCPServersPage;
import webportal.weboperation.DevicesOrbiDeviceModePage;
import webportal.weboperation.DevicesOrbiLanIPPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    boolean needrestore  = false;
    boolean originalmode = false;

    @Feature("APRouterFunctionality.RouterFunctionality") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7838") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the device must not come online if the user has assigned an invalid range static IP address") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7838") // It's a testcase id/link from Jira Test Case.
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

    @Step("Test Step 3: Change ip pool to invalid one")
    public void step3() {
        if(WebportalParam.ob2Model.contains("60")) {
            DevicesOrbiLanIPPage page = new DevicesOrbiLanIPPage();
            page.changeDHCPPool(256, 256);
        }else {
            DevicesOrbiLanIPPage page = new DevicesOrbiLanIPPage(false);
            DevicesOrbiDHCPServersPage page2 = new DevicesOrbiDHCPServersPage();
            page2.gotoPage();
            page2.openOne("1");
            page.changeDHCPPool(256, 256);
        }
        assertTrue(handle.getPageErrorMsg().length() > 10, "check msg on invalid ip value");
    }
}
