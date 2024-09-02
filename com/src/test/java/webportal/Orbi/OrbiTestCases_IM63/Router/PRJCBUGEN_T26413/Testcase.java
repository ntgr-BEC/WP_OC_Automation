package webportal.Orbi.OrbiTestCases_IM63.Router.PRJCBUGEN_T26413;

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
    @Story("PRJCBUGEN_T26413") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the pause/resume functionality of the connected clients") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T26413") // It's a testcase id/link from Jira Test Case.

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

    @Step("Test Step 2: Connect the wireless client to orbi")
    public void step2() {
        DevicesOrbiWifiNetworkPage page = new DevicesOrbiWifiNetworkPage();
        page.editSsid(0);
        page.setSsidName("PRJCBUGEN-T26413");
        page.setSsidSecurity(1, "12345678");
        page.btnSave.click();
        MyCommonAPIs.sleepsyncorbi();
        boolean checkpoint = true;
        String cmd = String.format("WAFconnect %s %s WPA2PSK aes", "PRJCBUGEN-T26413", "12345678");
        if (!new Javasocket().sendCommandToWinClient(WebportalParam.client2ip, WebportalParam.client2port, cmd).equals("true")) {
            checkpoint = false;
        }
        System.out.println(checkpoint);
        assertTrue(checkpoint, "checkpoint : client can connect to orbi's wifi1");
        MyCommonAPIs.sleepi(500);
    }

    @Step("Test Step 3: Pause the client and check the traffic cannot pass through")
    public void step3() {
        DevicesOrbiConnectedClientsPage page = new DevicesOrbiConnectedClientsPage();
        page.pauseClient(WebportalParam.client2name);
        // ping test
        String remote_client_ip = new Javasocket().sendCommandToWinClient(WebportalParam.client2ip, WebportalParam.client2port, "WAFgetip Wi-Fi");
        String[] splited = remote_client_ip.split("\\s+");
        remote_client_ip = splited[1];
        String cmd = String.format("WAFping %s %s", "8.8.8.8", remote_client_ip);
        String ping_result2 = new Javasocket().sendCommandToWinClient(WebportalParam.client2ip, WebportalParam.client2port, cmd);
        System.out.println(ping_result2);
        String loss_percentage2 = ping_result2.substring(ping_result2.indexOf("(") + 1, ping_result2.indexOf("%"));
        System.out.println(loss_percentage2);

        String s = "Reply from %s:";
        assertTrue(!ping_result2.contains(String.format(s, "8.8.8.8")), "Traffic can pass through");
    }

    @Step("Test Step 4: Resume the client and check the traffic can pass through")
    public void step4() {
        DevicesOrbiConnectedClientsPage page = new DevicesOrbiConnectedClientsPage();
        page.resumeClient(WebportalParam.client2name);
        // ping test
        String remote_client_ip = new Javasocket().sendCommandToWinClient(WebportalParam.client2ip, WebportalParam.client2port, "WAFgetip Wi-Fi");
        String[] splited = remote_client_ip.split("\\s+");
        remote_client_ip = splited[1];
        String cmd = String.format("WAFping %s %s", "8.8.8.8", remote_client_ip);
        String ping_result2 = new Javasocket().sendCommandToWinClient(WebportalParam.client2ip, WebportalParam.client2port, cmd);
        System.out.println(ping_result2);
        String loss_percentage2 = ping_result2.substring(ping_result2.indexOf("(") + 1, ping_result2.indexOf("%"));
        System.out.println(loss_percentage2);

        String s = "Reply from %s:";
        assertTrue(ping_result2.contains(String.format(s, "8.8.8.8")), "Traffic cannot pass through");
    }

}
