package webportal.Switch.System.PRJCBUGEN_T4668;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.SwitchCLIUtils;
import webportal.param.WebportalParam;
import webportal.publicstep.PublicButton;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author zheli
 *
 */
public class Testcase extends TestCaseBase implements Config {
    public String expectValue = "";

    String devName  = "test123";
    String vlanName = "testvlan";

    @Feature("Switch.System") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4668") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("045-Cache reboot device") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4668") // It's a testcase id/link from Jira Test Case.
    @Test(enabled = false, alwaysRun = true, groups = "p3")
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: enter setting page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();
    }

    @Step("Test Step 2: create VLAN 777")
    public void step2() {
        handle.getCmdOutputLines("vlan database; vlan 777", false);
        String tmpStr = handle.getCmdOutput("show vlan", false);
        assertTrue(tmpStr.contains("777"));
    }

    @Step("Test Step 3: Offline switch before Deploy \"Reboot\" command from Web Portal")
    public void step3() {
        SwitchCLIUtils.CloudModeSet(false);
    }

    @Step("Test Step 4: Deploy \"Factory Default\" command from Web Portal to Switch")
    public void step4() {
        new DevicesDashPage().enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
        new PublicButton().rebootDevice();
    }

    @Step("Test Step 5: Online switch")
    public void step5() {
        SwitchCLIUtils.CloudModeSet(true);
        handle.sleep(2 * 60, "wait device to do restart");
    }

    @Step("Test Step 6: After switch reload, check switch info on Web Portal and device Web GUI")
    public void step9() {
        new DevicesDashPage().waitAllSwitchDevicesConnected();
        String tmpStr = handle.getCmdOutput("show vlan", false);
        assertTrue(tmpStr.contains("777"));
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        SwitchCLIUtils.CloudModeSet(true);
        try {
            DevicesDashPage devicesDashPage = new DevicesDashPage();
            devicesDashPage.waitAllSwitchDevicesConnected();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
