package webportal.ProManagedSwitch.FirmwarePolicy.PRJCBUGEN_T6794;

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
    String tmpStr = "";

    @Issue("PRJCBUGEN-11325")
    @Feature("Switch.FirmwarePolicy") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T6794") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("212 - Firmware schedule upgrade failed because of device offline") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T6794") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p3") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    @Step("Test Step 0: Downgrade firmware")
    public void step0() {
        throw new RuntimeException("Check Issue");
        // SwitchCLIUtils.updateSWFirmwareOld();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Go to \"Edit Network\" and enable \"Auto Upgrade\"")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        ddpmg.gotoPage();
        ddpmg.waitDevicesReConnected(webportalParam.sw1serialNo);
        fmp.gotoFirmwarePage();
        assertTrue(fmp.waitForUpdateAvailable(), "switch is not in old firmware");
    }

    @Step("Test Step 2: Select a specific \"Start Date\" and \"End Date\" (PST), none recurrence, click \"Save\"")
    public void step2() {
        fmpp.gotoPage();
        fmpp.addSchedule(true, false, false);
    }

    @Step("Test Step 3: Before hit the policy, offline the device")
    public void step3() {
        SwitchCLIUtils.CloudModeSet(false);
        fmpp.Sleep4FirmwareSchedule();
    }

    @Step("Test Step 4: After end-date, bring the device online")
    public void step4() {
        SwitchCLIUtils.CloudModeSet(true);
        evtp.gotoPage();
        assertTrue(handle.pageSource().contains("failed"), "Show the firmware upgrade failed because of the device offline");
    }

}
