package webportal.BR.BRSystem.PRJCBUGEN_T7172;

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
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String sExpect = "";

    @Feature("BR.BRSystem") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7172") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("017-Set max length Device Name") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7172") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p3") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        handle.refresh();
        new DevicesDashPage().editDeviceName(WebportalParam.br1serialNo, WebportalParam.br1deveiceName);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM APP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }

    @Step("Test Step 2: Set max length strings Device Name, then deploy to BR500;")
    public void step2() {
        sExpect = "PRJCBUGEN_T7172";
        new DevicesDashPage().editDeviceName(WebportalParam.br1serialNo, sExpect);
        handle.waitRestReady(BRUtils.api_device_name, sExpect, false, 0);
        assertTrue(new BRUtils().getField("name").contains(sExpect), "device name is not changed");
    }
}
