package webportal.Orbi.OrbiTestCases.PRJCBUGEN_T25489;

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
    @Story("PRJCBUGEN_T25489") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify remove satellite functionality") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25489") // It's a testcase id/link from Jira Test Case.

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
    
    @Step("Test Step 2: Get satellite ip and reboot satellite")
    public void step2() {
        DevicesOrbiSatellitesPage satellitepage = new DevicesOrbiSatellitesPage();
        assertTrue(satellitepage.getFirstSatelliteStatus().matches("Excellent|Good|Fair"), "Check satellite should be connected");
        String ip = satellitepage.getFirstSatelliteIP();
        UserManage userManage = new UserManage();
        userManage.logout();
        OrbiDebugSettingsPage page = new OrbiDebugSettingsPage();
        page.OrbibaseEnableTelenet(ip, WebportalParam.loginDevicePassword);
        new OrbiTelnet(ip , WebportalParam.loginDevicePassword).orbiTelnetSendCmd("reboot");
        MyCommonAPIs.sleepi(30);
    }
    
    @Step("Test Step 3: Remove satellite")
    public void step3() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().enterDevice(WebportalParam.ob2serialNo);
        DevicesOrbiSatellitesPage satellitepage = new DevicesOrbiSatellitesPage();
        assertTrue(satellitepage.getFirstSatelliteStatus().matches("Disconnected"), "Check satellite should be disconnected");
        // Remove satellite
        satellitepage.deleteFirstSatellite();
        MyCommonAPIs.sleepi(3);
        assertTrue(!satellitepage.firstsatellite.exists(),"The satellite does not exist");
    }
}
