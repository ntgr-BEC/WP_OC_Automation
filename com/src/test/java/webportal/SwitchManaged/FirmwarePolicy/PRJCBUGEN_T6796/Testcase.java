package webportal.SwitchManaged.FirmwarePolicy.PRJCBUGEN_T6796;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.SwitchCLIUtils;
import webportal.weboperation.AccountPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String tmpStr = "";

    @Feature("Switch.FirmwarePolicy") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T6796") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("214 - Trigger firmware schedule upgrade when set network location time zone + 8:00") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T6796") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p3") // Use p1/p2/p3 to high/normal/low on priority
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
        SwitchCLIUtils.updateSWFirmwareOld(true);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to WP")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        ddpmg.gotoPage();
        ddpmg.waitDevicesReConnected(webportalParam.sw1serialNo);
        fmp.gotoFirmwarePage();
        assertTrue(fmp.waitForUpdateAvailable(), "switch is not in old firmware");
    }

    @Step("Test Step 2: Set user network time zone GMT+8:00")
    public void step2() {
        new AccountPage().enterEditNetworkPage().setTimeZone(true);
    }

    @Step("Test Step 3: Set a one-time firmware schedule policy")
    public void step3() {
        fmpp.gotoPage();
        fmpp.addSchedule(true, false, false);
    }

    @Step("Test Step 4: Check auto image upgrade time, scheduling will be based on network location time zone")
    public void step4() {
        fmp.gotoFirmwarePage();
        assertTrue(fmp.waitForAutoUpdateDone(), "check auto-update is hitted and finished");
    }

}
