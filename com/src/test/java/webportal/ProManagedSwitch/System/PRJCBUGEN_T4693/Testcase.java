package webportal.ProManagedSwitch.System.PRJCBUGEN_T4693;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
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
    @Story("PRJCBUGEN_T4693") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("100 - Discover and manage new device by invalid SN") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4693") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p3")
    @Issue("PRJCBUGEN-21486")
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
        try {
            devicesDashPage.addNewDevice(DEVICEINFO);
        } catch (Throwable e) {
        }
        String result = devicesDashPage.showErrorMessage();
        assertEquals(result.toLowerCase(), "Please enter a valid serial number".toLowerCase());
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
    }
}
