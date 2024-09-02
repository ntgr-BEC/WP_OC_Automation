package webportal.Switch.CableTest.PRJCBUGEN_T4922;

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
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesSwitchCableTestPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredGroupPortConfigPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredVLANPage;

/**
 * @author xuchen
 */
public class Testcase extends TestCaseBase implements Config {
    public boolean Result = true;
    
    @Feature("Switch.CableTest") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4922") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("001-Get Cable Test Status with one port") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4922") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1")
    @Issue("PRJCBUGEN-26283")
    public void test() throws Exception {
        runTest(this);
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: enter setting page")
    public void step1() {
        // link up dut 1 port1
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
    }
    
    @Step("Test Step 2: Connect DUT and SW with a normal cable")
    public void step2() {
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPage wiredVLANPage = new WiredVLANPage(false);
        WiredGroupPortConfigPage wiredGroupPortConfigPage = new WiredGroupPortConfigPage();
        wiredGroupPortConfigPage.multiSetting(SWITCH1_PORTARRAY, BATTCHSETTING1);
        MyCommonAPIs.sleep(8000);
    }
    
    @Step("Test Step 3: Check cable test on webportal")
    public void step3() {
        // check sw1 on webportal
        DevicesDashPage devicesDashPage = new DevicesDashPage();
        devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
        
        DevicesSwitchCableTestPage cableTestPage = new DevicesSwitchCableTestPage();
        cableTestPage.logger.info("step3......");
        String testResult = "init";
        for (int i = 0; i < 3; i++) { // try 2 more times
            try {
                cableTestPage.testCableTest(sw1port);
                testResult = cableTestPage.getCableTestResult(sw1port[0]);
            } catch (Throwable e) {
                handle.refresh();
                continue;
            }
            System.out.println(testResult);
            String Normal = WebportalParam.ntgrLanguageTranslate("cableTest", "normal", WebportalParam.BrowserLanguage);
            String NoCable = WebportalParam.ntgrLanguageTranslate("cableTest", "noCable", WebportalParam.BrowserLanguage);
            System.out.println(Normal + NoCable);
            if (testResult.contains(Normal) || testResult.contains(NoCable)) {
                micResult = true;
                break;
            } else {
                micResult = false;
                handle.refresh();
            }
        }
        assertTrue(micResult, "cable test result is: " + testResult + ", expected to: Normal");
    }
    
    @AfterMethod(alwaysRun = true)
    public void restore() {
    }
    
}
