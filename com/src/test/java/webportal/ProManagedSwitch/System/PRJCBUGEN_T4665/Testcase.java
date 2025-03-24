package webportal.ProManagedSwitch.System.PRJCBUGEN_T4665;

import static org.testng.Assert.assertFalse;
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
import webportal.publicstep.PublicButton;
import webportal.weboperation.DevicesDashPageMNG;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author sumanta
 *
 */
public class Testcase extends TestCaseBase implements Config {
    public String expectValue = "";

    String devName  = "test123";
    String vlanName = "testvlan";

    @Feature("Switch.System") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4665") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("042- Run \"Reload\" switch with additional network level configuration") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4665") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p4") // p3
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: enter setting page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

        handle.gotoLoction();
        handle.gotoLocationWireSettings();
    }

    @Step("Test Step 2: create VLAN 555")
    public void step2() {
        handle.getCmdOutputLines("vlan database; vlan 555", false);
        String tmpStr = handle.getCmdOutput("show vlan", false);
        assertTrue(tmpStr.contains("555"));
    }

    @Step("Test Step 3: Deploy \"Factory Default\" command from Web Portal to Switch")
    public void step8() {
        new DevicesDashPageMNG().enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
        new PublicButton().reloadDevice();
        handle.waitDeviceOnlineReload();
    }

    @Step("Test Step 4: After switch reload, check switch info on Web Portal and device Web GUI")
    public void step9() {
        String tmpStr = handle.getCmdOutput("show vlan", false);
        assertFalse(tmpStr.contains("555"));
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        try {
            DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
            devicesDashPage.waitAllSwitchDevicesConnected();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
