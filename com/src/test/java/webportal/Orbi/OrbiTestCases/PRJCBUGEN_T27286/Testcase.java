package webportal.Orbi.OrbiTestCases.PRJCBUGEN_T27286;

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
import webportal.weboperation.DevicesOrbiDiagnosticModePage;
import webportal.weboperation.DevicesOrbiSatellitesPage;
import webportal.weboperation.DevicesOrbiSetupWizardPage;
import webportal.weboperation.DevicesOrbiSummaryPage;
import webportal.weboperation.DevicesOrbiTroubleshootPage;
import webportal.weboperation.DevicesOrbiWifiNetworkPage;
import webportal.weboperation.WebportalLoginPage;
/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    
    @Feature("Orbi.OrbiTestCases") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T27286") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, password validation on all 4 SSID") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T27286") // It's a testcase id/link from Jira Test Case.

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
    
    @Step("Test Step 2: Goto wifi1 / Enter password less than 8 characters")
    public void step2() {
        DevicesOrbiWifiNetworkPage page = new DevicesOrbiWifiNetworkPage();
        page.editSsid(0);
        page.setSsidSecurity(1, "1234567");
        page.btnSave.click();
        assertTrue(handle.getPageErrorMsg().length() > 10, "check allert message exists");
    }
    
    @Step("Test Step 3: Goto wifi2 / Enter password less than 8 characters")
    public void step3() {
        DevicesOrbiWifiNetworkPage page = new DevicesOrbiWifiNetworkPage();
        page.editSsid(1);
        page.setSsidSecurity(1, "1234567");
        page.btnSave.click();
        assertTrue(handle.getPageErrorMsg().length() > 10, "check allert message exists");
    }
    
    @Step("Test Step 4: Goto wifi3 / Enter password less than 8 characters")
    public void step4() {
        DevicesOrbiWifiNetworkPage page = new DevicesOrbiWifiNetworkPage();
        page.editSsid(2);
        page.setSsidSecurity(1, "1234567");
        page.btnSave.click();
        assertTrue(handle.getPageErrorMsg().length() > 10, "check allert message exists");
    }
    
    @Step("Test Step 5: Goto guest / Enter password less than 8 characters")
    public void step5() {
        DevicesOrbiWifiNetworkPage page = new DevicesOrbiWifiNetworkPage();
        page.editSsid(3);
        page.authenticationSelect.selectOption(1);
        page.SSIDPwd.setValue("1234567");
        page.btnSave.click();
        assertTrue(handle.getPageErrorMsg().length() > 10, "check allert message exists");
    }
    
}
