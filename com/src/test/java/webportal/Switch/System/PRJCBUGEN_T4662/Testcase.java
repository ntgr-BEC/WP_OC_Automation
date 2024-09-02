package webportal.Switch.System.PRJCBUGEN_T4662;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.weboperation.AccountPage;
import webportal.weboperation.NetworkEditNetworkPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author zheli
 *
 */
public class Testcase extends TestCaseBase implements Config {

    @Feature("Switch.System") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4662") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("037-Check min/max length of password") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4662") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p3")
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: login")
    public void step1() {
        // link up dut 1 port1
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
    }

    @Step("Test Step 2: On Web Portal, enter nothing or less 6 chars,or 21 chars, then deploy")
    public void step2() {
        AccountPage accountPage = new AccountPage();
        NetworkEditNetworkPage networkEditNetworkPage = accountPage.enterEditNetworkPage();
        networkEditNetworkPage.modifyDeviceAdminPassword(PASSWORD1);
        String result = networkEditNetworkPage.getErrorMessage();
        assertTrue(result.length() > 10, "error msg:" + result);
        MyCommonAPIs.sleep(10 * 1000);
        networkEditNetworkPage.modifyDeviceAdminPassword(PASSWORD2);
        result = networkEditNetworkPage.getErrorMessage();
        assertTrue(result.length() > 10, "error msg:" + result);
        MyCommonAPIs.sleep(10 * 1000);
        networkEditNetworkPage.modifyDeviceAdminPassword(PASSWORD3);
        result = networkEditNetworkPage.getErrorMessage();
        assertTrue(result.length() > 10, "error msg:" + result);
        MyCommonAPIs.sleep(10 * 1000);
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
    }
}
