package webportal.PingAndTraceroute.Pro.PRJCBUGEN_T23045;

import static com.codeborne.selenide.Selenide.$$x;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.NetworkTroubleshootPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    String                                domain       = "www.bing.com";
    String                                tmpStr;
    String                                CompleteResultAP;
    public static NetworkTroubleshootPage troubleshoot = new NetworkTroubleshootPage(false);
    Map<String, String> pingTraceroute = new HashMap<String, String>();

    @Feature("PingAndTraceroute") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T23045") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify, traceroute test has advanced setting option") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T23045") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        if (troubleshoot.dnslookupclose.isDisplayed()) {
            troubleshoot.dnslookupclose.click();
        }
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Insight discover and manage one ap")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);

        handle.gotoLoction();
        new NetworkTroubleshootPage();
    }

    @Step("Test Step 2: Check ping test option;")
    public void step2() {
        troubleshoot.selectAllDevice.click();
        troubleshoot.btnRunTest.click();
        MyCommonAPIs.sleepi(5);
        troubleshoot.Traceroute.click();
        troubleshoot.testNow.click();
        MyCommonAPIs.sleepi(5);
        assertTrue(troubleshoot.AdvanceSettings.isDisplayed(), "Ping option is not avilable");
        
    }

}
