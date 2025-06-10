package webportal.CFD.CFD_7_6.Switch.PRJCBUGEN_T33330;


import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.*;

/**
 *
 * @author RaviShankar
 * precondition premium account with switch onboarded
 * SW6
 *
 */
public class Testcase extends TestCaseBase {


    @Feature("Verify Notification") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T33330") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify getting the notification for device GS728TPPv2.") // It's a testcase                                                                                                                           // Test Case.
    @TmsLink("PRJCBUGEN_T33330") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }




    @Step("Step 1: Login into Insight Premium account")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
    }

    @Step("Step2: Reboot the Switch")
    public void step2() {
        new DevicesDashPage().rebootDevice(WebportalParam.sw1deveiceName);
        new DevicesDashPage().isDeviceRebooting(WebportalParam.sw1deveiceName);
        evtp.gotoPage();
    }


    @Step("Step3: Verify reboot notification")
    public void step3() {
        MyCommonAPIs.sleepi(5);
        boolean notificationResult1 = evtp.notificationScheduleForSwitch(WebportalParam.sw1deveiceName,"Device is rebooting");
        assertTrue(notificationResult1, "Reboot notification not found for the switch");
        new DevicesDashPage().waitDevicesOnline(WebportalParam.sw1deveiceName);
        evtp.gotoPage();
        boolean notificationResult2 =evtp.notificationScheduleForSwitch(WebportalParam.sw1deveiceName,"Device resumed communication with the Insight cloud");
        boolean notificationResult3 =evtp.notificationScheduleForSwitch(WebportalParam.sw1deveiceName,"Port");
        assertTrue(notificationResult2||notificationResult3, "Reboot notification not found for the switch");
    }

}
