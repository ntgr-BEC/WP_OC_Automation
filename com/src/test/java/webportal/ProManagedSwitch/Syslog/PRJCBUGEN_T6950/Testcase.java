package webportal.SwitchManaged.Syslog.PRJCBUGEN_T6950;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.RunCommand;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String logIP   = "198.168.172.49";
    int    logPort = 514;

    @Feature("Switch.Syslog") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T6950") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("002-Verify Syslog basic functional scenario that configured by Insight") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T6950") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Check Syslog default configuration on Insight and Web GUI")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new AccountPage().enterEditNetworkPage();
        sysp.gotoPage();
    }

    @Step("Test Step 2: Set Syslog parameter as below via Insight APP to our tftp")
    public void step2() {
        logIP = webportalParam.getLocalNetIp(false);
        System.out.println("please make sure that tftp has syslog enabled in placed under C:\\AUTOMATION\\BIN\\tftpd32");

        sysp.setSyslog(logIP, logPort);
    }

    @Step("Test Step 3: Check Syslog configuration on Insight and CLI:")
    public void step3() {
        handle.waitCmdReady(logIP, false);
    }

    @Step("Test Step 4: Reload DUT from Insight.")
    public void step4() {
        new RunCommand().restartTftp();
        ddpmg.gotoPage();
        ddpmg.waitDevicesReboot(WebportalParam.sw1serialNo);
    }

    @Step("Test Step 5: Check syslog severity level on CLI after DUT bootup.")
    public void step5() {
        assertTrue(new RunCommand().readSyslog().contains(WebportalParam.sw1IPaddress), "there must be at least one line for logging");
    }

}
