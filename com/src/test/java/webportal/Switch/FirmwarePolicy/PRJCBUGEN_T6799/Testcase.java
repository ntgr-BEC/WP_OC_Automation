package webportal.Switch.FirmwarePolicy.PRJCBUGEN_T6799;

import static org.testng.Assert.assertFalse;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String tmpStr = "";

    @Issue("PRJCBUGEN-11124")
    @Feature("Switch.FirmwarePolicy") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T6799") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("217 - The image is already latest when hit the time window") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T6799") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p3") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    @Step("Test Step 0: Device is managed and loading with latest one")
    public void step0() {
        // throw new RuntimeException("Check Issue");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Go to \"Edit Network\" and enable \"Auto Upgrade\"")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        fmp.gotoFirmwarePage();
        fmp.doUpdate(false);

        evtp.gotoPage();
        evtp.deleteAllEvent();
    }

    @Step("Test Step 2: Create firmware upgrade policy and when hit the time window")
    public void step2() {
        fmpp.gotoPage();
        fmpp.addSchedule(true, false, false);
    }

    @Step("Test Step 3: Check the log")
    public void step3() {
        fmpp.Sleep4FirmwareSchedule();
        evtp.gotoPage();
        assertFalse(handle.pageSource().contains("Upgrade"), "It showing trigger auto upgrade, but upgrade nothing");
    }

}
