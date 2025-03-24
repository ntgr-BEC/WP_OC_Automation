package webportal.RadioLevelConfigurations.PrOuSeR.PRJCBUGEN_T24810;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    String cmd = "iwconfig wifi0vap0 | grep Access";

    @Feature("RadioLevelConfigurations") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T24810") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("2.4 SSID is broadcast   or not when 2.4 ghz radios are disable  in Ap") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T24810") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        String Enable = "1";
        new WirelessQuickViewPage(false).Enable24(Enable);
        new WirelessQuickViewPage().deleteALLSSID();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
        new HamburgerMenuPage(false).closeLockedDialog();
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);

        handle.gotoLoction();
    }

    
    @Step("Test Step 2: Create SSid with 2.4 ghz")
    public void step2() {
              
        new WirelessQuickViewPage().deleteALLSSID();
        Map<String, String> locationInfo7 = new HashMap<String, String>();
        locationInfo7.put("SSID", "ssid24");
        locationInfo7.put("Band", "2.4 GHz");
        locationInfo7.put("Security", "WPA2 Personal");
        locationInfo7.put("Password", "123456798");
        new WirelessQuickViewPage().addSsid1(locationInfo7);
        
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("SSID", "Both");
        locationInfo.put("Band", "Both");
        locationInfo.put("Security", "Open");
        new WirelessQuickViewPage(false).addSsid1(locationInfo);
    }
    
    @Step("Test Step 3: disable 2.4Ghz")
    public void step3() {
        String Enable = "0";
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);
        new WirelessQuickViewPage(false).RadioAndChannels.click();
        MyCommonAPIs.sleepi(10); 
        new WirelessQuickViewPage(false).Enable24(Enable);
        MyCommonAPIs.sleepi(120);    
       
    }
    
    
    @Step("Test Step 4:   ssh AP for the status of 2Ghz")
    public void step4() {
    
    String WACAP = new APUtils(WebportalParam.ap1IPaddress).get80211status(cmd);       
    assertTrue(WACAP.contains("Access Point: Not-Associated"),"");
 
    
    }

}
