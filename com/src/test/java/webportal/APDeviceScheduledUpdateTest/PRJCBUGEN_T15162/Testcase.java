package webportal.APDeviceScheduledUpdateTest.PRJCBUGEN_T15162;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String tmpStr = "";

    @Feature("APDeviceScheduledUpdateTest") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T15162") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that the manual update remains disabled until scheduled update option is disabled")
    @TmsLink("PRJCBUGEN-T15162") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        fmpp.gotoPage();
        fmpp.disableAuto();
    }

    @Step("Test Step 1: Downgrade firmware")
    public void step1() {
        new APUtils(WebportalParam.ap1IPaddress).updateFirmwareOld(null);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: Now navigate to the Locations tab >> Firmware Tab tile >> Enable the Schedule Update option")
    public void step2() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        ddp.gotoPage();

        ddp.waitDevicesReConnected(WebportalParam.ap1serialNo);

        fmp.gotoFirmwarePage();
        assertTrue(fmp.waitForUpdateAvailable(), "AP is not in old firmware");

        fmpp.gotoPage();
        fmpp.addSchedule(false, false, false);
    }

    @Step("Test Step 3: Also upon trying the same user should get a warning popup regarding the same.")
    public void step3() {
        fmp.gotoFirmwarePage();
        fmp.btnUpdate.click();

        assertTrue(fmp.btnWarnYes.exists(), "check warning msg");
        fmp.btnWarnYes.click();
    }
}
