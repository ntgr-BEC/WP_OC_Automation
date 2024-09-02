package webportal.Switch.System.PRJCBUGEN_T4661;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import util.SwitchTelnet;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.NetworkEditNetworkPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author zheli
 *
 */
public class Testcase extends TestCaseBase implements Config {
    String oldPassword = WebportalParam.loginDevicePassword;

    @Issue("have a bug")
    @Feature("Switch.System") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4661") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("036-Allow special characters") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4661") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p4") // "p3"
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: login in")
    public void step1() {
        // link up dut 1 port1
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
    }

    @Step("Test Step 2: modify device password")
    public void step2() {
        AccountPage accountPage = new AccountPage();
        NetworkEditNetworkPage networkEditNetworkPage = accountPage.enterEditNetworkPage();
        // oldPassword = networkEditNetworkPage.getDeviceAdminPassword();
        networkEditNetworkPage.modifyDeviceAdminPassword(NEW_PASSWORD);
        MyCommonAPIs.sleep(5000);
    }

    @Step("Test Step 3: check in switch telnet")
    public void step3() {
        boolean result1 = false;
        for (int i = 1; i < 20; i++) {
            SwitchTelnet switchTelnet = new SwitchTelnet(webportalParam.sw1IPaddress, NEW_PASSWORD);
            result1 = switchTelnet.isLoginSuccess();
            if (result1) {
                break;
            }
        }

        assertTrue(result1, "use new password login");
        SwitchTelnet.disconnect();
        SwitchTelnet switchTelnet2 = new SwitchTelnet(webportalParam.sw1IPaddress, oldPassword);
        boolean result2 = switchTelnet2.isLoginSuccess();
        assertFalse(result2, "use old password login");
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        AccountPage accountPage = new AccountPage();
        NetworkEditNetworkPage networkEditNetworkPage = accountPage.enterEditNetworkPage();
        networkEditNetworkPage.modifyDeviceAdminPassword(oldPassword);
        MyCommonAPIs.sleepi(5 * 60);
        new DevicesDashPage();
    }
}
