package webportal.Orbi.OrbiTestCases.PRJCBUGEN_T25532;

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
    @Story("PRJCBUGEN_T25532") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify editing each SSID name, passphrase, security type from the wifi networks page") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25532") // It's a testcase id/link from Jira Test Case.

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
    
    @Step("Test Step 2: Edit wireless2 and check if it's saved")
    public void step2() {
        // change ssid name
        DevicesOrbiWifiNetworkPage page = new DevicesOrbiWifiNetworkPage();
        page.editSsid(1);
        page.inputSSIDName.setValue(WebportalParam.ob2WifiNetworkName1 + "-new");
        page.btnSave.click();
        MyCommonAPIs.sleepsyncorbi();
        page.gotoPage();
        page.editSsid(1);
        assertTrue(page.inputSSIDName.getValue().equals(WebportalParam.ob2WifiNetworkName1 + "-new"), "Check SSID name changed");
    }
    
    @Step("Test Step 3: Edit wireless3 and check if it's saved")
    public void step3() {
        // change security type and password
        DevicesOrbiWifiNetworkPage page = new DevicesOrbiWifiNetworkPage();
        page.editSsid(2);
        page.setSsidSecurity(2, "Netgear1@");
        page.btnSave.click();
        MyCommonAPIs.sleepsyncorbi();
        page.gotoPage();
        page.editSsid(2);
        assertTrue(page.securitySelect.getSelectedValue().equals("WPA-PSK/WPA2-PSK"), "Check security changed");
        assertTrue(page.SSIDPwd.getValue().equals("Netgear1@"), "Check password changed");
    }

}
