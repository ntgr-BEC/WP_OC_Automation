package webportal.SwitchManaged.Syslog.PRJCBUGEN_T6949;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import util.MyCommonAPIs;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.SwitchCLIUtilsMNG;
import util.SwitchTelnetMNG;
import webportal.weboperation.AccountPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String logIP    = "192.168.34.12";
    int    logPort  = 514;
    String logIP1   = "11.1.1.11";
    int    logPort1 = 30000;

    @Feature("Switch.Syslog") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T6949") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("001-Verify Syslog parameter configuration on Insight") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T6949") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
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
        assertTrue(sysp.cbEnable.exists(), "check syslog option");
    }

    @Step("Test Step 2: Set Syslog parameter as below via Insight APP")
    public void step2() {
        sysp.setSyslog(logIP, logPort);
    }

    @Step("Test Step 3: Check Syslog configuration on Insight and CLI:")
    public void step3() {
        handle.waitCmdReady(logIP, false);
        MyCommonAPIs.sleep(8000);
        String tmpStr = SwitchCLIUtilsMNG.getLogging();
        System.out.println(tmpStr);
        assertTrue(tmpStr.contains(logIP));
        assertTrue(tmpStr.contains(" " + logPort));
    }

    @Step("Test Step 4: Change some parameter via Insight APP:")
    public void step4() {
        sysp.setSyslog(logIP1, logPort1);
    }

    @Step("Test Step 5: Check Syslog configuration on Insight and CLI:")
    public void step5() {
        handle.waitCmdReady(logIP1, false);
        String tmpStr = SwitchCLIUtilsMNG.getLogging();
        assertTrue(tmpStr.contains(logIP1), "check logIP1");
        assertTrue(tmpStr.contains(" " + logPort1), "check port");
    }

    @Step("Test Step 6: Disable Syslog via Insight APP")
    public void step6() {
        sysp.disableSyslog();
        handle.sleepsync();
    }

    @Step("Test Step 7: Check Syslog configuration on Insight and CLI:")
    public void step7() {
        SwitchTelnetMNG switchTelnet = new SwitchTelnetMNG(webportalParam.sw1IPaddress);
        assertFalse(switchTelnet.checkLoggingStatus(), "logging should be off");
    }

}
