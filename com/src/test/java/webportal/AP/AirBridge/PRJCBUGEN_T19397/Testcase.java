package webportal.AP.AirBridge.PRJCBUGEN_T19397;

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
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    @Feature("AP.AirBridge") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T19397") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to rename the device while onboarding from the device dashboard tab") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T19397") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        ddp.gotoPage();
        ddp.editDeviceName(WebportalParam.ap1serialNo, WebportalParam.ap1deveiceName);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login into IM WP page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }

    @Step("Test Step 2: The user should be successfully able to rename the device")
    public void step2() {
        ddp.gotoPage();
        if (ddp.getDeviceName(WebportalParam.ap1serialNo).equals("")) {
            String[] sAPNos = { WebportalParam.ap1serialNo };
            String[] sAPNames = { WebportalParam.ap1deveiceName };
            ddp.addDeviceAirBridge(sAPNos, sAPNames);
            ddp.waitDevicesReConnected(WebportalParam.ap1serialNo);
            assertTrue(ddp.getDeviceName(WebportalParam.ap1serialNo).equals(WebportalParam.ap1deveiceName),
                    "The user should be successfully able to rename the device");
        } else {
            String newName = "testT19397";
            ddp.editDeviceName(WebportalParam.ap1serialNo, newName);
            assertTrue(ddp.getDeviceName(WebportalParam.ap1serialNo).equals(newName), "The user should be successfully able to rename the device");
        }
    }
}
