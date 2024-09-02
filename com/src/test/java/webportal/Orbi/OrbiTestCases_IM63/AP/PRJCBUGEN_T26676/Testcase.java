package webportal.Orbi.OrbiTestCases_IM63.AP.PRJCBUGEN_T26676;

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
import webportal.weboperation.DevicesOrbiWifiNetworkPage;
import webportal.weboperation.WebportalLoginPage;
/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    
    @Feature("Orbi.OrbiTestCases_IM63") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T26676") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify enable the secure diagnostic mode functionality") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T26676") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        try {
            DevicesOrbiDiagnosticModePage page = new DevicesOrbiDiagnosticModePage();
            page.DisableDiagMode();
            MyCommonAPIs.sleepsyncorbi();
        } catch (Throwable e) {
            System.out.println("failed to resore");
        }
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().enterDevice(WebportalParam.ob2serialNo);
        new DevicesOrbiDeviceModePage(false).initDeviceMode(true);
    }
    
    @Step("Test Step 2: Goto diagnostic page / Enable diagnostics mode / Refresh page and check the port number exists")
    public void step2() {
        DevicesOrbiDiagnosticModePage page = new DevicesOrbiDiagnosticModePage();
        page.EnableDiagMode();
        MyCommonAPIs.sleepsyncorbi();
        page.gotoPage();
        assertTrue(page.portNumbebr.isDisplayed(), "The port number is displayed");
        assertTrue(page.inputEnableDiagMod.isSelected(), "The checkbox is selected");
    }
    
}
