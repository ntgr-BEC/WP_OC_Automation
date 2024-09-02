package webportal.Orbi.OrbiTestCases.PRJCBUGEN_T25482;

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
import orbi.telnetoperation.OrbiTelnet;
import orbi.weboperation.OrbiAdvancedRouterAPModePage;
import orbi.weboperation.OrbiDebugSettingsPage;
import testbase.TestCaseBase;
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesOrbiConnectedClientsPage;
import webportal.weboperation.DevicesOrbiDeviceModePage;
import webportal.weboperation.DevicesOrbiSatellitesPage;
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
    @Story("PRJCBUGEN_T25482") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the search bar functionality on the connected client tile") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25482") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().enterDevice(WebportalParam.ob2serialNo);
    }
    
    @Step("Test Step 2: Go to connected clients page / Check search device name")
    public void step2() {
        DevicesOrbiConnectedClientsPage page = new DevicesOrbiConnectedClientsPage();
        String devicename = WebportalParam.client1name;
        assertTrue(page.search(devicename, devicename,true), "The client should be listed");
        assertTrue(!page.search(devicename, devicename + "-wrong", true), "The client should not be listed");
    }
    
    @Step("Test Step 3: Go to connected clients page / Check search mac address")
    public void step3() {
        DevicesOrbiSummaryPage sumpage = new DevicesOrbiSummaryPage();
        DevicesOrbiConnectedClientsPage page = new DevicesOrbiConnectedClientsPage();
        String devicename = WebportalParam.client1name;
        String mac = page.getMAC(devicename, true);
        System.out.println(mac);
        String pattern = mac.substring(0, mac.length() - 1); 
        assertTrue(page.search(devicename, pattern, true), "The client should be listed");
        assertTrue(!page.search(devicename,  "11:22:33:44:55:66", true), "The client should not be listed");
    }
    
    @Step("Test Step 4: Go to connected clients page / Check search ip address")
    public void step4() {
        DevicesOrbiSummaryPage sumpage = new DevicesOrbiSummaryPage();
        DevicesOrbiConnectedClientsPage page = new DevicesOrbiConnectedClientsPage();
        String devicename = WebportalParam.client1name;
        String ip = page.getIP(devicename, true);
        assertTrue(page.search(devicename, ip,true), "The client should be listed");
        assertTrue(!page.search(devicename,  ip + "1", true), "The client should not be listed");
    }
    
}
