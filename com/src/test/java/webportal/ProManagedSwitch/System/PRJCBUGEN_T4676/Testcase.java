package webportal.ProManagedSwitch.System.PRJCBUGEN_T4676;

import static org.testng.Assert.assertFalse;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

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
 * @author sumanta
 *
 */
public class Testcase extends TestCaseBase implements Config {
    public String expectValue = "";

    @Feature("Switch.System") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4676") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("061- Delete the device from Cloud account DB") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4676") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p4") // "p1"
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: enter setting page")
    public void step1() {
        // link up dut 1 port1
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        handle.gotoLoction();
    }

    @Step("Test Step 2: Go to Device list and click delete device, Popup warning message, and click No")
    public void step2() {
        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        devicesDashPage.deleteDeviceNo(WebportalParam.sw1serialNo);
    }

    @Step("Test Step 3: Go to Device list and click delete device, Popup warning message, and click Yes")
    public void step3() {
        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        devicesDashPage.deleteDeviceYes(WebportalParam.sw1serialNo);
        MyCommonAPIs.sleep(20 * 1000);
        Selenide.refresh();
        boolean result = devicesDashPage.enterDeviceSummary(WebportalParam.sw1serialNo).isDisplayed();
        assertFalse(result);
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        try {
            DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
            devicesDashPage.addNewDevice(DEVICEINFO);
            devicesDashPage.waitAllSwitchDevicesConnected();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
