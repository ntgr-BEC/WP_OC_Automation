package webportal.ProManagedSwitch.Port.PRJCBUGEN_T4908;

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
import webportal.weboperation.DevicesSwitchConnectedNeighboursPortConfiqSummaryPage;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredGroupPortConfigPage;
import webportal.weboperation.WiredLAGPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredVLANPage;

/**
 *
 * @author Sumanta
 *
 */
public class Testcase extends TestCaseBase implements Config {
    @Feature("Switch.Port") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4908") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("034-Change port speed mode that belong to a lag") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4908") // It's a testcase id/link from Jira Test Case.
    @Issue("PRJCBUGEN-14844")
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: enter setting page")
    public void step1() {
        // link up dut 1 port1
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
        handle.gotoLoction();
    }

    @Step("Test Step 2: Create lag 1 and assign port 1 port 2 to it")
    public void step2() {
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPage wiredVLANPage = new WiredVLANPage(false);
        // WiredGroupPortConfigPage wiredGroupPortConfigPage = new WiredGroupPortConfigPage();
        // wiredGroupPortConfigPage.multiSettingAllPorts(BATTCHSETTING1);
        WiredLAGPage wiredLAGPage = new WiredLAGPage();
        wiredLAGPage.addLag(LAG_NAME, SWITCH1_PORTARRAY);
        MyCommonAPIs.sleep(200 * 1000);
    }

    @Step("Test Step 3: Change port 1 speed mode to 100 Mbps Half Duplex")
    public void step3() {
        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
        DevicesSwitchSummaryPage devicesSwitchSummaryPage = new DevicesSwitchSummaryPage();
        DevicesSwitchConnectedNeighboursPortConfiqSummaryPage devicesSwitchConnectedNeighboursPortConfiqSummaryPage = devicesSwitchSummaryPage
                .enterPortConfigSummary(WebportalParam.sw1LagPort1);
        boolean result = devicesSwitchConnectedNeighboursPortConfiqSummaryPage.checkSettingIcon();
        if (result == false) {
            micResult = true;

        } else {
            micResult = false;
            assertTrue(micResult, "setting page should be hide due to lag");
        }
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPage wiredVLANPage = new WiredVLANPage(false);
        WiredGroupPortConfigPage wiredGroupPortConfigPage = new WiredGroupPortConfigPage();
        WiredLAGPage wiredLAGPage = new WiredLAGPage();
        wiredLAGPage.deleteLag();
        wiredLAGPage.deleteLagCli();
        MyCommonAPIs.sleep(60 * 1000);
    }
}
