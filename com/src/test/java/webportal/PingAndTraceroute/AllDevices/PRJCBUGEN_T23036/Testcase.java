package webportal.PingAndTraceroute.AllDevices.PRJCBUGEN_T23036;

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
    String                                CompleteResult;  
    public static NetworkTroubleshootPage troubleshoot = new NetworkTroubleshootPage(false);
    Map<String, String> pingTraceroute = new HashMap<String, String>();

    @Feature("PingAndTraceroute") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T23036") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify, ping can be performed across all WAC, WAX ,Switches and Orbi on device level config") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T23036") // It's a testcase id/link from Jira Test Case.

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
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new NetworkTroubleshootPage();
    }

    @Step("Test Step 2: Check ping test option for AP;")
    public void step2() {
        SelenideElement Device = troubleshoot.selectOneDevice(WebportalParam.ap1serialNo);
        Device.click();    
        tmpStr = troubleshoot.runPingTestOnDevice(pingTraceroute,domain, WebportalParam.ap1serialNo);
        CompleteResult = troubleshoot.CheckPingResult(WebportalParam.ap1serialNo);
        System.out.println(tmpStr);
        assertTrue(tmpStr.contains(WebportalParam.getLocText("Success").toLowerCase()), "ping  result is not Success.");
        assertTrue(CompleteResult.contains(WebportalParam.getLocText("3 packets transmitted").toLowerCase()), "ping  result is not complete.");
    }
    
    @Step("Test Step 3: Check ping test option for Orbi;")
    public void step3() {
        SelenideElement Device = troubleshoot.selectOneDevice(WebportalParam.ob1serialNo);
        Device.click();    
        tmpStr = troubleshoot.runPingTestOnDevice(pingTraceroute,domain, WebportalParam.ob1serialNo);
        CompleteResult = troubleshoot.CheckPingResult(WebportalParam.ob1serialNo);
        System.out.println(tmpStr);
        assertTrue(tmpStr.contains(WebportalParam.getLocText("Success").toLowerCase()), "ping  result is not Success.");
        assertTrue(CompleteResult.contains(WebportalParam.getLocText("3 packets transmitted").toLowerCase()), "ping  result is not complete.");
    }
    
    @Step("Test Step 4: Check ping test option for switch;")
    public void step4() {
        SelenideElement Device = troubleshoot.selectOneDevice(WebportalParam.sw1serialNo);
        Device.click();    
        tmpStr = troubleshoot.runPingTestOnDevice(pingTraceroute,domain, WebportalParam.sw1serialNo);
        CompleteResult = troubleshoot.CheckPingResult(WebportalParam.sw1serialNo);
        System.out.println(tmpStr);
        assertTrue(tmpStr.contains(WebportalParam.getLocText("Success").toLowerCase()), "ping  result is not Success.");
        assertTrue(CompleteResult.contains(WebportalParam.getLocText("3 packets transmitted").toLowerCase()), "ping  result is not complete.");
    }

}
