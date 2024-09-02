package webportal.SwitchManaged.FirmwarePolicy.PRJCBUGEN_T6782;

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
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String tmpStr = "";

    @Feature("Switch.FirmwarePolicy") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T6782") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("200 - By default Auto Upgrade will be OFF") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T6782") // It's a testcase id/link from Jira Test Case.

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
    @Step("Test Step 1: Firmware Scheduler is introduced only on network level, go to \"Edit Network\" page, check \"Auto Upgrade\" status")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        ddpmg.gotoPage();
        ddpmg.waitDevicesReConnected(webportalParam.sw1serialNo);
        fmp.gotoFirmwarePage();
        assertTrue(fmp.waitForUpdateAvailable(), "switch is not in old firmware");

        fmpp.gotoPage();
        fmpp.addSchedule(true, false, false);
        handle.refresh();

        fmpp.gotoPage();
        handle.setSelected(fmpp.cbAuto, false, true);
    }

    @Step("Test Step 2: Go to \"Firmware Management\", check manual update")
    public void step2() {
        fmp.gotoFirmwarePage();
        assertTrue(fmp.btnUpdate.exists(), "check update button");
        fmp.btnUpdate.click();
        assertTrue(handle.getPageErrorMsg().contains("not interrupt the network"), "check update is doable");
    }

    @Step("Test Step 3: Go to \"Edit Network\" page, enable \"Auto Upgrade\" and create one schedule")
    public void step3() {
        fmpp.gotoPage();
        fmpp.addSchedule(false, false, false);

        fmp.gotoFirmwarePage();
        fmp.btnUpdate.click();
        assertTrue(handle.getPageErrorMsg().contains("update is enabled"), "check update is disabled");

        fmpp.gotoPage();
        assertTrue(fmpp.cbAuto.isSelected(), "check auto-update is enabled");
    }
}
