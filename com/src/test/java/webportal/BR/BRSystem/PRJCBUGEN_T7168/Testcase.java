package webportal.BR.BRSystem.PRJCBUGEN_T7168;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.publicstep.PublicButton;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    Map<String, String> devInfo = new HashMap<String, String>();

    @Feature("BR.BRSystem") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7168") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("013-Remove the BR by IM APP") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7168") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p4") // "p2" Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        devInfo.put("Serial Number", WebportalParam.br1serialNo);
        devInfo.put("Device Name", WebportalParam.br1deveiceName);
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        handle.refresh();
        new DevicesDashPage().addNewDevice(devInfo);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM APP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }

    @Step("Test Step 2: Go to BR device page, click Remove button;")
    public void step2() {
        new DevicesDashPage().enterDevicesSwitchSummary(WebportalParam.br1serialNo, 1);
        new PublicButton().removeDevice();
    }

    @Step("Test Step 3: The BR was removed success;")
    public void step3() {
        String devName = new DevicesDashPage().getDeviceName(WebportalParam.br1serialNo);
        assertTrue(devName == "", "device was not removed");
    }
}
