package webportal.APDeviceScheduledUpdateTest.PRJCBUGEN_T15164;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
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

    @Feature("APDeviceScheduledUpdateTest") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T15164") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify if the user shouldnt be able to schedule a job which is less than 60 mins")
    @TmsLink("PRJCBUGEN-T15164") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }

    @Step("Test Step 1: Downgrade firmware")
    public void step1() {
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: Now navigate to the Locations tab >> Firmware Tab tile >> Enable the Schedule Update option")
    public void step2() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        ddp.gotoPage();
    }

    @Step("Test Step 3: Observe that the  Application throws a warning popup if the time gap between start and end time is less than 60 mins")
    public void step3() {
        fmp.gotoFirmwarePage();
        fmpp.enableAuto();
        fmpp.SetDailyDateTime(false, true, false);
        handle.clickButton(0);

        assertTrue(handle.getPageErrorMsg().contains("1 hour"), "check warning msg on less 1 hour");
    }
}
