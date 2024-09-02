package webportal.BR.BRSystem.PRJCBUGEN_T7177;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.BRUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String sExpect = "";

    @Feature("BR.BRSystem") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7177") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("022-Upgrade image for BR device") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7177") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p4") // "p1"
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM APP success;")
    public void step1() {
        new BRUtils().updateSystemFirmware(0);

        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }

    @Step("Test Step 2: Go to Networks-->Firmware Management page, check firmware status;")
    public void step2() {
        fmp.gotoFirmwarePage();
        assertTrue(fmp.waitForUpdateAvailable(), "BR is not in old firmware");
    }

    @Step("Test Step 3: Upgrade firmware success;\r\n" + "Check BR image by GUI and IM APP after device reboot success;")
    public void step3() {
        fmp.doUpdate(false);
        DevicesDashPage devicesDashPage = new DevicesDashPage();
        devicesDashPage.devicesStatus(WebportalParam.br1serialNo).shouldHave(Condition.text(WebportalParam.getLocText("Connected")));
    }
}
