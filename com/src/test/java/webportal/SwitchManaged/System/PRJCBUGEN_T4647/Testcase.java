package webportal.SwitchManaged.System.PRJCBUGEN_T4647;

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
import util.SwitchCLIUtilsMNG;
import util.SwitchTelnetMNG;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPageMNG;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author zheli
 *
 */
public class Testcase extends TestCaseBase implements Config {
    public String oldName = WebportalParam.sw1deveiceName;
    public String newName = "";

    @Feature("Switch.System") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4647") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("012-Set Device name when switch offline") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4647") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p4") // "p3"
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: make switch offline")
    public void step1() {
        SwitchCLIUtilsMNG.CloudModeSet(false);
    }

    @Step("Test Step 2: edit device name")
    public void step2() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        devicesDashPage.editDeviceName(WebportalParam.sw1serialNo, "abcd");
        MyCommonAPIs.sleep(10 * 1000);
        Selenide.refresh();

        SwitchTelnetMNG switchTelnet = new SwitchTelnetMNG(webportalParam.sw1IPaddress);
        newName = switchTelnet.getDeviceName();
        SwitchTelnetMNG.disconnect();
        assertTrue(newName.contains(oldName), "check device name");
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        try {
            SwitchCLIUtilsMNG.CloudModeSet(true);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        try {
            DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
            devicesDashPage.editDeviceName(WebportalParam.sw1serialNo, WebportalParam.sw1deveiceName);
            devicesDashPage.waitAllSwitchDevicesConnected();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
