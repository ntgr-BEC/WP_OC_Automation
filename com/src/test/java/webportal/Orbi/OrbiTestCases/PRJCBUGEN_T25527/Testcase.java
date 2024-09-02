package webportal.Orbi.OrbiTestCases.PRJCBUGEN_T25527;

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
import webportal.weboperation.DevicesOrbiWifiNetworkPage;
import webportal.weboperation.WebportalLoginPage;
/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    
    @Feature("Orbi.OrbiTestCases") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25527") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the wireless disconnected clients on the connected clients page") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25527") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        // disconnect the wireless client
        String cmd = "WAFdisconnect";
        new Javasocket().sendCommandToWinClient(WebportalParam.client2ip, WebportalParam.client2port, cmd);
        MyCommonAPIs.sleepi(60);
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }
    
    @Step("Test Step 2: Connect the wireless client to orbi")
    public void step2() {
        ddp.gotoPage();
        ddp.openOB2();
        DevicesOrbiWifiNetworkPage page = new DevicesOrbiWifiNetworkPage();
        page.editSsid(0);
        page.setSsidName("PRJCBUGEN-T25527");
        page.setSsidSecurity(1, "12345678");
        page.btnSave.click();
        MyCommonAPIs.sleepsyncorbi();
        
        boolean checkpoint = true;
        String cmd = String.format("WAFconnect %s %s WPA2PSK aes", "PRJCBUGEN-T25527", "12345678");
        if (!new Javasocket()
                .sendCommandToWinClient(WebportalParam.client2ip, WebportalParam.client2port, cmd)
                .equals("true")) {
            checkpoint = false;
        }
        System.out.println(checkpoint);
        assertTrue(checkpoint, "checkpoint : client can connect to orbi's wifi1");
        MyCommonAPIs.sleepi(500);
    }
    
    @Step("Test Step 3: Disconnect the wireless client")
    public void step3() {
        // disconnect the wireless client
        String cmd = "WAFdisconnect";
        new Javasocket().sendCommandToWinClient(WebportalParam.client2ip, WebportalParam.client2port, cmd);
        MyCommonAPIs.sleepi(500);
    }
    
    @Step("Test Step 4: Check wireless disconnected device")
    public void step4() {
        new DevicesDashPage().enterDevice(WebportalParam.ob2serialNo);
        DevicesOrbiConnectedClientsPage page = new DevicesOrbiConnectedClientsPage();
        // The wireless client should be there
        assertTrue(page.clientExists(WebportalParam.client2name, false), "Check the disconnected wireless client");
    }

}
