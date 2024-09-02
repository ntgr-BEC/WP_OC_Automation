package webportal.Orbi.OrbiTestCases.PRJCBUGEN_T25525;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import orbi.param.OrbiGlobalConfig;
import orbi.weboperation.OrbiAdvancedRouterAPModePage;
import testbase.TestCaseBase;
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesOrbiConnectedClientsPage;
import webportal.weboperation.DevicesOrbiDeviceModePage;
import webportal.weboperation.DevicesOrbiSetupWizardPage;
import webportal.weboperation.DevicesOrbiSummaryPage;
import webportal.weboperation.WebportalLoginPage;
/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    
    @Feature("Orbi.OrbiTestCases") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25525") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the wired disconnected clients on the connected clients page") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25525") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        // enable the interface
        String cmd = "netsh interface set interface %s enable";
        new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, String.format(cmd, WebportalParam.ob2CPCNIC));
        MyCommonAPIs.sleepi(120);
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }
    
    @Step("Test Step 2: Disable DUT LAN interface")
    public void step2() {
        String cmd = "netsh interface set interface %s disable";
        // disable nic to router
        new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, String.format(cmd, WebportalParam.ob2CPCNIC));
        MyCommonAPIs.sleepi(500);
    }
    
    @Step("Test Step 3: Check wired disconnected device")
    public void step3() {
        new DevicesDashPage().enterDevice(WebportalParam.ob2serialNo);
        // Open connected client page
        DevicesOrbiConnectedClientsPage page = new DevicesOrbiConnectedClientsPage();
        // The wired client should be there
        assertTrue(page.clientExists(WebportalParam.client1name, false), "Check the wired client is disconnected");
    }

}
