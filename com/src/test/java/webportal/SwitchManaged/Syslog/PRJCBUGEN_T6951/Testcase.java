package webportal.SwitchManaged.Syslog.PRJCBUGEN_T6951;

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
import webportal.weboperation.AccountPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String logIP   = "10.1.1.171";
    int    logPort = 514;

    @Issue("PRJCBUGEN-12133")
    @Story("PRJCBUGEN_T6951") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Feature("Switch.Syslog") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Description("003-Verify parameter validity of syslog server by Insight") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T6951") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        //throw new RuntimeException("Check Issue");
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

    @Step("Test Step 2: Try to configre Syslog host IP address with invalid IP address:")
    public void step2() {
      //*[@id="showNotification"]
        sysp.setSyslog("127.0.0.1", logPort);
        assertTrue(handle.getPageErrorMsg().contains("enter a valid ip address"));
        sysp.setSyslog("224.0.0.1", logPort);
        assertTrue(handle.getPageErrorMsg().contains("enter a valid ip address"));
        sysp.setSyslog("255.255.255.255", logPort);
        assertTrue(handle.getPageErrorMsg().contains("enter a valid ip address"));
        sysp.setSyslog("0.0.0.0", logPort);
        assertTrue(handle.getPageErrorMsg().contains("enter a valid ip address"));
    }

    @Step("Test Step 3: Try to configre Syslog host with invalid UDP ports:")
    public void step3() {
        sysp.setSyslog(logIP, -514);
        assertTrue(handle.getPageErrorMsg().contains("port number should be between 1 and 65535"));
        sysp.setSyslog(logIP, 0);
        assertTrue(handle.getPageErrorMsg().contains("port number should be between 1 and 65535"));
        sysp.setSyslog(logIP, 65536);
        assertTrue(handle.getPageErrorMsg().contains("port number should be between 1 and 65535"));
        sysp.setSyslog(logIP, 99999);
        assertTrue(handle.getPageErrorMsg().contains("port number should be between 1 and 65535"));
    }

}
