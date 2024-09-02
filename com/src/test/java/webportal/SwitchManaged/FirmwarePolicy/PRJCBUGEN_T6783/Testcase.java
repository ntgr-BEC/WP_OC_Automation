package webportal.SwitchManaged.FirmwarePolicy.PRJCBUGEN_T6783;

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
import util.SwitchCLIUtilsMNG;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String tmpStr = "";

    @Feature("Switch.FirmwarePolicy") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T6783") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("201 - Create one time Firmware Policy and update by schedule") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T6783") // It's a testcase id/link from Jira Test Case.
    @Issue("PRJCBUGEN-17413")

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        fmpp.gotoPage();
        fmpp.disableAuto();
    }

    @Step("Test Step 0: Downgrade firmware")
    public void step0() {
        SwitchCLIUtilsMNG.updateSWFirmwareOld(true);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Go to \"Edit Network\" and enable \"Auto Upgrade\"")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        ddpmg.gotoPage();
        ddpmg.waitDevicesReConnected(WebportalParam.sw1serialNo);
        fmp.gotoFirmwarePage();
        assertTrue(fmp.waitForUpdateAvailable(), "switch is not in old firmware");
    }

    @Step("Test Step 2: Select a specific \"Start Date\" and \"End Date\" (PST), none recurrence, click \"Save\"")
    public void step2() {
        fmpp.gotoPage();
        fmpp.addSchedule(true, false, false);
    }

    @Step("Test Step 3: Go to \"Image management\" and set switch to upgrade by schedule, check the device upgrade happen when hit the time window")
    public void step3() {
        fmp.gotoFirmwarePage();
        assertTrue(fmp.waitForAutoUpdateDone(), "check auto-update is hitted and finished");
    }

}
