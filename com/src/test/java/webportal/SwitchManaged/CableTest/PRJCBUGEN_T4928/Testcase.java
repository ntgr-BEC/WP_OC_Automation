package webportal.SwitchManaged.CableTest.PRJCBUGEN_T4928;

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
import webportal.weboperation.DevicesDashPageMNG;
import webportal.weboperation.DevicesSwitchCableTestPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredGroupPortConfigPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredVLANPage;

/**
 * @author xuchen
 */
public class Testcase extends TestCaseBase implements Config {
    public boolean Result  = true;
    String         Normal  = "";
    String         NoCable = "";

    @Feature("Switch.CableTest") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4928") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("007-Get Cable Test Status on 100M or 10M ports") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4928") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p3")
    @Issue("PRJCBUGEN-26283")
    public void test() throws Exception {
        Normal = WebportalParam.ntgrLanguageTranslate("cableTest", "normal", WebportalParam.BrowserLanguage);
        NoCable = WebportalParam.ntgrLanguageTranslate("cableTest", "noCable", WebportalParam.BrowserLanguage);
        System.out.println(Normal + NoCable);
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

    @Step("Test Step 2: Set port as 10M Half-duplex mode")
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
        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);

        DevicesSwitchCableTestPage cableTestPage = new DevicesSwitchCableTestPage();
        cableTestPage.logger.info("step3......");
        cableTestPage.testCableTest(sw1port);
        String testResult = cableTestPage.getCableTestResult(sw1port[0]);
        System.out.println(testResult);
        if (testResult.contains(Normal) || testResult.contains(NoCable)) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "10M Half-duplex cable test result is " + testResult);
        }
    }

    @Step("Test Step 4: Set port as 10M Full-duplex mode")
    public void step4() {
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPage wiredVLANPage = new WiredVLANPage(false);
        WiredGroupPortConfigPage wiredGroupPortConfigPage = new WiredGroupPortConfigPage();
        wiredGroupPortConfigPage.multiSetting(SWITCH1_PORTARRAY, BATTCHSETTING2);
        MyCommonAPIs.sleep(6000);
    }

    @Step("Test Step 5: Check cable test on webportal")
    public void step5() {
        // check sw1 on webportal
        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);

        DevicesSwitchCableTestPage cableTestPage = new DevicesSwitchCableTestPage();
        cableTestPage.logger.info("step5......");
        cableTestPage.testCableTest(sw1port);
        String testResult = cableTestPage.getCableTestResult(sw1port[0]);
        System.out.println(testResult);
        if (testResult.contains(Normal) || testResult.contains(NoCable)) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "10M Full-duplex cable test result is " + testResult);
        }
    }

    @Step("Test Step 6: Set port as 100M half-duplex mode")
    public void step6() {
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPage wiredVLANPage = new WiredVLANPage(false);
        WiredGroupPortConfigPage wiredGroupPortConfigPage = new WiredGroupPortConfigPage();
        wiredGroupPortConfigPage.multiSetting(SWITCH1_PORTARRAY, BATTCHSETTING3);
        MyCommonAPIs.sleep(6000);
    }

    @Step("Test Step 7: Check cable test on webportal")
    public void step7() {
        // check sw1 on webportal
        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);

        DevicesSwitchCableTestPage cableTestPage = new DevicesSwitchCableTestPage();
        cableTestPage.logger.info("step7......");
        cableTestPage.testCableTest(sw1port);
        String testResult = cableTestPage.getCableTestResult(sw1port[0]);
        System.out.println(testResult);
        if (testResult.contains(Normal) || testResult.contains(NoCable)) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "100M Half-duplex cable test result is " + testResult);
        }
    }

    @Step("Test Step 8: Set port as 100M full-duplex mode")
    public void step8() {
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPage wiredVLANPage = new WiredVLANPage(false);
        WiredGroupPortConfigPage wiredGroupPortConfigPage = new WiredGroupPortConfigPage();
        wiredGroupPortConfigPage.multiSetting(SWITCH1_PORTARRAY, BATTCHSETTING4);
        MyCommonAPIs.sleep(6000);
    }

    @Step("Test Step 9: Check cable test on webportal")
    public void step9() {
        // check sw1 on webportal
        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);

        DevicesSwitchCableTestPage cableTestPage = new DevicesSwitchCableTestPage();
        cableTestPage.logger.info("step9......");
        cableTestPage.testCableTest(sw1port);
        String testResult = cableTestPage.getCableTestResult(sw1port[0]);
        System.out.println(testResult);
        if (testResult.contains(Normal) || testResult.contains(NoCable)) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "100M Full-duplex cable test result is " + testResult);
        }
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
    }

}
