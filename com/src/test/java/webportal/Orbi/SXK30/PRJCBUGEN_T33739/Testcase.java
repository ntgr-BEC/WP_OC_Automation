package webportal.Orbi.SXK30.PRJCBUGEN_T33739;


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
import orbi.weboperation.OrbiAdvancedmDNSPage;
import orbi.weboperation.OrbiBasicWirelessPage;
import orbi.weboperation.OrbiLoginPage;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
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

    @Feature("Orbi.SXK30") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T33739") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Pre-config mDNS gateway related settings then do the day-1 on-boarding in Router mode") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T33739") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success, delete devcie if exist.")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        if (!new DevicesDashPage().getDeviceName(WebportalParam.ob2serialNo).equals("")) {;
            new DevicesDashPage().enterDevice(WebportalParam.ob2serialNo);
            currentDeviceMode = new DevicesOrbiSummaryPage(false).getDeviceMode();
            new DevicesDashPage().deleteDeviceYes(WebportalParam.ob2serialNo);
            MyCommonAPIs.sleepi(100);
        }
        
        UserManage userManage = new UserManage();
        userManage.logout();
        MyCommonAPIs.sleepi(3);
    }
    
    @Step("Test Step 2: On local GUI set mDNS")
    public void step2() {        
        
        OrbiLoginPage orbiLoginPage = new OrbiLoginPage();
        orbiLoginPage.defaultLogin();
        
        OrbiAdvancedmDNSPage orbimDNSpage = new OrbiAdvancedmDNSPage();
        orbimDNSpage.OpenAdvancedmDNSPage();
        
        orbimDNSpage.EnablemDNS();
        orbimDNSpage.AddPolicy("policy1", "Airplay", "1", "20");
        orbimDNSpage.AddPolicy("policy2", "Chromecast", "20", "30");
        orbimDNSpage.AddPolicy("policy3", "Scanners", "40", "1");
        orbimDNSpage.ClickApply();
        
        OrbiLoginPage OrbiLoginPage = new OrbiLoginPage();
        OrbiLoginPage.OrbiLogout();
    }

    @Step("Test Step 3: Add device in this location")
    public void step3() {        
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
        ddp.gotoPage();
        
        
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ob2serialNo);
        devInfo.put("Device Name", WebportalParam.ob2Name);
        devInfo.put("MAC Address", WebportalParam.ob2MAC_Address.replace("-", ":"));

        new DevicesDashPage(false).addNewDevice(devInfo);
        MyCommonAPIs.sleepi(100);
    }

    @Step("Test Step 4: Verify device status")
    public void step4() {
        assertTrue(new DevicesDashPage().waitDevicesReConnected(WebportalParam.ob2serialNo), "Device cannot online.");
        assertTrue(new DevicesDashPage().waitDevicesIPvalid(WebportalParam.ob2serialNo), "Device IP not valid.");
    }

}
