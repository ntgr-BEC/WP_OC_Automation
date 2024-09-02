package webportal.SwitchManaged.System.PRJCBUGEN_T4664;

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
import util.SwitchCLIUtilsMNG;
import webportal.weboperation.DevicesDashPageMNG;
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
    @Story("PRJCBUGEN_T4664") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("041- Run \"Reload\" on Switch with device level configuration") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4664") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p4") // p2
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

    @Step("Test Step 2: create VLAN 888")
    public void step2() {
        handle.getCmdOutputLines("vlan database; vlan 888", false);
        String tmpStr = handle.getCmdOutput("show vlan", false);
        assertTrue(tmpStr.contains("888"));
    }

    @Step("Test Step 3: Do factory default from Web GUI or preset \"default\" button on front panel")
    public void step3() {
        handle.doSwitchCommand(2);
    }

    @Step("Test Step 4: After reload, and check U200 status and previous configuration")
    public void step4() {
        new DevicesDashPageMNG().waitAllSwitchDevicesConnected();
        String tmpStr = handle.getCmdOutput("show vlan", false);
        assertFalse(tmpStr.contains("888"));
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        SwitchCLIUtilsMNG.CloudModeSet(true);
        try {
            DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
            devicesDashPage.waitAllSwitchDevicesConnected();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
