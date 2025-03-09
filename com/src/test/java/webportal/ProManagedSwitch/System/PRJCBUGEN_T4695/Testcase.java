package webportal.SwitchManaged.System.PRJCBUGEN_T4695;

import static org.testng.Assert.assertEquals;

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
import webportal.weboperation.DevicesDashPageMNG;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author zheli
 *
 */
public class Testcase extends TestCaseBase implements Config {
    public String expectValue = "";

    @Feature("Switch.System") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4695") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("104 - Discover and manage new device by SN adding") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4695") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p4") // "p2"
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: enter setting page")
    public void step1() {
        // link up dut 1 port1
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
    }

    @Step("Test Step 2: Discover and add new device by SN adding")
    public void step2() {
        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        devicesDashPage.deleteDeviceYes(WebportalParam.sw1serialNo);

        MyCommonAPIs.sleep(30 * 1000);

        devicesDashPage.addNewDevice(DEVICEINFO);
        devicesDashPage.waitAllSwitchDevicesConnected();
        String result = devicesDashPage.getDeviceName(WebportalParam.sw1serialNo);
        assertEquals(result, WebportalParam.sw1deveiceName);
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        try {
            DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
            if (!handle.pageSource().contains(WebportalParam.sw1serialNo)) {
                devicesDashPage.addNewDevice(DEVICEINFO);
            }
            devicesDashPage.waitAllSwitchDevicesConnected();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
