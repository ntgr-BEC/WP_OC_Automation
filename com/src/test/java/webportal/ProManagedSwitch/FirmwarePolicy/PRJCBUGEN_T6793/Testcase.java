package webportal.ProManagedSwitch.FirmwarePolicy.PRJCBUGEN_T6793;

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
import util.SwitchCLIUtils;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String  tmpStr     = "Firmware upgraded to version";
    boolean toContinue = true;

    @Issue("PRJCBUGEN-10845")
    @Feature("Switch.FirmwarePolicy") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T6793") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("211 - Image schedule upgrade successfully log and push notification") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T6793") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Go to \"Edit Network\" and enable \"Auto Upgrade\"")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        evtp.gotoPage();
        if (handle.pageSource().contains(tmpStr)) {
            toContinue = false;
        }
    }

    @Step("Test Step 2: Create firmware upgrade policy and when hit the time window")
    public void step2() {
        if (!toContinue)
            return;

        SwitchCLIUtils.updateSWFirmwareOld(true);

        fmpp.gotoPage();
        fmpp.addSchedule(true, false, false);
    }

    @Step("Test Step 3: Check the log")
    public void step3() {
        if (!toContinue)
            return;
        fmp.gotoFirmwarePage();
        assertTrue(fmp.waitForAutoUpdateDone(), "check auto-update is hitted and finished");

        evtp.gotoPage();
        evtp.getEventSummary();
        assertTrue(handle.pageSource().contains(tmpStr),
                "Firmware upgrade to version vx, initiated and device will reboot itself to apply changes.");
    }

}
