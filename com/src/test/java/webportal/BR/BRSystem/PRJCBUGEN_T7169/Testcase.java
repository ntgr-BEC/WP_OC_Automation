package webportal.BR.BRSystem.PRJCBUGEN_T7169;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MailHandler;
import webportal.param.WebportalParam;
import webportal.publicstep.PublicButton;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String sExpect = "";

    @Feature("BR.BRSystem") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7169") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("014-Share Diagnostics for BR by IM APP") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7169") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p3") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM APP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }

    @Step("Test Step 2: check email Info")
    public void step2() {
        DevicesDashPage devicesDashPage = new DevicesDashPage();
        DevicesSwitchSummaryPage devicesSwitchSummaryPage = devicesDashPage.enterDevicesSwitchSummary(WebportalParam.br1serialNo, 1);
        PublicButton publicButton = new PublicButton();
        
        MailHandler mailH = new MailHandler();
        String mailTo = mailH.getRandomAddress();
        publicButton.ShareDiagnostics(mailTo);
        String mailBody = mailH.getHTMLBody();
        if (mailBody.contains("Device Name") && mailBody.contains("Network Name") && mailBody.contains("Network Name")
                && mailBody.contains("Serial No") && mailBody.contains("Model") && mailBody.contains("IP Address")) {
            micResult = true;

        }
        assertTrue(micResult);
    }

}
