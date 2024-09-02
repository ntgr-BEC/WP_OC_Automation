package webportal.Orbi.OrbiTestCases_IM63.Router.PRJCBUGEN_T26414;

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
    
    @Feature("Orbi.OrbiTestCases_IM63") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T26414") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the connected clients details such as MAC address, IP address, SSID etc") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T26414") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success, add devcie if not exist. / Change to router mode")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().enterDevice(WebportalParam.ob2serialNo);
    }
    
    @Step("Test Step 2: Check wired connected device")
    public void step2() {
        // Open connected client page
        DevicesOrbiConnectedClientsPage page = new DevicesOrbiConnectedClientsPage();
        // The wired client should be there
        assertTrue(page.clientExists(WebportalParam.client1name, true), "Check the wired client exists");
        assertTrue(page.isWiredClient(WebportalParam.client1name, true), "Check the wired client img");
        assertTrue(page.getSSID(WebportalParam.client1name, true).equals("N/A"), "Check wired client's SSID");
    }
    
    @Step("Test Step 3: Connect the wireless client to orbi")
    public void step3() {
        DevicesOrbiWifiNetworkPage page = new DevicesOrbiWifiNetworkPage();
        page.editSsid(0);
        page.setSsidName("PRJCBUGEN-T26414");
        page.setSsidSecurity(1, "12345678");
        page.btnSave.click();
        MyCommonAPIs.sleepsyncorbi();
        boolean checkpoint = true;
        String cmd = String.format("WAFconnect %s %s WPA2PSK aes", "PRJCBUGEN-T26414", "12345678");
        if (!new Javasocket()
                .sendCommandToWinClient(WebportalParam.client2ip, WebportalParam.client2port, cmd)
                .equals("true")) {
            checkpoint = false;
        }
        System.out.println(checkpoint);
        assertTrue(checkpoint, "checkpoint : client can connect to orbi's wifi1");
        MyCommonAPIs.sleepi(500);
    }
    
    @Step("Test Step 4: Check wireless connected device")
    public void step4() {
        new DevicesDashPage().enterDevice(WebportalParam.ob2serialNo);
        // Open connected client page
        DevicesOrbiConnectedClientsPage page = new DevicesOrbiConnectedClientsPage();
        // The wired client should be there
        assertTrue(page.clientExists(WebportalParam.client2name, true), "Check the wireless client exists");
        assertTrue(page.isWirelessClient(WebportalParam.client2name, true), "Check the wireless client img");
        assertTrue(page.getSSID(WebportalParam.client2name, true).equals("PRJCBUGEN-T26414"), "Check wireless client's SSID");
        assertTrue(page.getMAC(WebportalParam.client2name, true).equals(WebportalParam.client2wifimac), "Check wireless client's mac");
    }

}
