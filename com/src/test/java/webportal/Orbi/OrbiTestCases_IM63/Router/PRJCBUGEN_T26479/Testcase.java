package webportal.Orbi.OrbiTestCases_IM63.Router.PRJCBUGEN_T26479;

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
import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesOrbiSetupWizardPage;
import webportal.weboperation.DevicesOrbiSummaryPage;
import webportal.weboperation.WebportalLoginPage;
/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    
    public String currentDeviceMode = "Router";

    @Feature("Orbi.OrbiTestCases_IM63") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T26479") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify remove orbi device functionality") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T26479") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        handle.gotoLoction();
        if (new DevicesDashPage().getDeviceName(WebportalParam.ob2serialNo).equals("")) {
            Map<String, String> devInfo = new HashMap<String, String>();
            devInfo.put("Serial Number", WebportalParam.ob2serialNo);
            devInfo.put("Device Name", WebportalParam.ob2Name);
            devInfo.put("MAC Address", WebportalParam.ob2MAC_Address.replace("-", ":"));

            new DevicesDashPage(false).addNewDevice(devInfo);
            MyCommonAPIs.sleepsyncorbi();
        }
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success, add devcie if it does not exist.")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        if (new DevicesDashPage().getDeviceName(WebportalParam.ob2serialNo).equals("")) {
            Map<String, String> devInfo = new HashMap<String, String>();
            devInfo.put("Serial Number", WebportalParam.ob2serialNo);
            devInfo.put("Device Name", WebportalParam.ob2Name);
            devInfo.put("MAC Address", WebportalParam.ob2MAC_Address.replace("-", ":"));

            new DevicesDashPage(false).addNewDevice(devInfo);
            MyCommonAPIs.sleepsyncorbi();
        }
    }

    @Step("Test Step 2: Delete device in this location / Check device is deleted")
    public void step2() {        
        new DevicesDashPage().deleteDeviceYes(WebportalParam.ob2serialNo);
        MyCommonAPIs.sleepi(120);
        assertTrue(new DevicesDashPage().getDeviceName(WebportalParam.ob2serialNo).equals(""), "Device does not exist.");
    }

}
