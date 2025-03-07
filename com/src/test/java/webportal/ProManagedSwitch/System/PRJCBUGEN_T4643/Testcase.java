package webportal.SwitchManaged.System.PRJCBUGEN_T4643;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

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
import util.SwitchTelnet;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPageMNG;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author zheli
 *
 */
public class Testcase extends TestCaseBase implements Config {

    @Feature("Switch.System") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4643") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("006-Set blank Device Name") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4643") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p3")
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

    @Step("Test Step 2: modify device name")
    public void step2() {
        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        devicesDashPage.editDeviceName(WebportalParam.sw1serialNo, "");
        MyCommonAPIs.sleep(3000);
        Selenide.refresh();
        String newName = devicesDashPage.getDeviceName(WebportalParam.sw1serialNo);
        assertEquals(newName, WebportalParam.sw1deveiceName);
    }

    @Step("Test Step 3: check in switch telnet")
    public void step3() {
        SwitchTelnet switchTelnet = new SwitchTelnet(webportalParam.sw1IPaddress);
        String newName = switchTelnet.getDeviceName();
        SwitchTelnet.disconnect();
        assertTrue(newName.contains(WebportalParam.sw1deveiceName), "check device name");
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        devicesDashPage.editDeviceName(WebportalParam.sw1serialNo, WebportalParam.sw1deveiceName);
    }
}
