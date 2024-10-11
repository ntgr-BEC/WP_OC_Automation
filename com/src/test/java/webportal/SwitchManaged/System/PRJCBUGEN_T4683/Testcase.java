package webportal.SwitchManaged.System.PRJCBUGEN_T4683;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.SwitchCLIUtils;
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
    @Story("PRJCBUGEN_T4683") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("085-Discover and attach device after disable Cloud Management Mode") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4683") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p4") // "p3"
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

    @Step("Test Step 2: delete device")
    public void step2() {
        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        devicesDashPage.deleteDeviceYes(WebportalParam.sw1serialNo);
    }

    @Step("Test Step 3: modify devices to Cloud Management Mode disable")
    public void step3() {
        SwitchCLIUtils.CloudModeSet(false);
    }

    public void step4() {
        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        devicesDashPage.addNewDevice(DEVICEINFO);
        Selenide.refresh();
        String text = devicesDashPage.getDeviceStatus(WebportalParam.sw1serialNo);
        assertEquals(text, "Waiting for first connect");
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        try {
            SwitchCLIUtils.CloudModeSet(true);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        if (!handle.pageSource().contains(WebportalParam.sw1serialNo)) {
            devicesDashPage.addNewDevice(DEVICEINFO);
        }
        devicesDashPage.waitAllSwitchDevicesConnected();
    }
}
