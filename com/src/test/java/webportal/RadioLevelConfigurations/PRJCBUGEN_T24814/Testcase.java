package webportal.RadioLevelConfigurations.PRJCBUGEN_T24814;

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
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    String cmd = "iwconfig wifi1vap0 | grep Access";

    @Feature("RadioLevelConfigurations") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T24814") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("5 SSID is broadcast or not when 5 ghz radios are disable in Ap for wac and wax") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T24814") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        String Enable1 = "1";
        System.out.println("start to do tearDown");
        new WirelessQuickViewPage().GoToWirelessSettings();
        new WirelessQuickViewPage(false).DropDown5GhzHighWireless.click();
        new WirelessQuickViewPage(false).Enable5high(Enable1);
        MyCommonAPIs.sleepi(10); 
        new WirelessQuickViewPage(false).DropDown5GhzLowWireless.click();
        MyCommonAPIs.sleepi(10); 
        new WirelessQuickViewPage(false).Enable5low(Enable1);
        MyCommonAPIs.sleepi(10); 
        new WirelessQuickViewPage().deleteALLSSID();
        
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }

    
    @Step("Test Step 2: Create SSid")
    public void step2() {
              
        new WirelessQuickViewPage().deleteALLSSID();
        Map<String, String> locationInfo7 = new HashMap<String, String>();
        locationInfo7.put("SSID", "ssid24");
        locationInfo7.put("Band", "Both");
        locationInfo7.put("Security", "WPA2 Personal");
        locationInfo7.put("Password", "123456798");
        new WirelessQuickViewPage().addSsid1(locationInfo7);
        
      
    }
    
    @Step("Test Step 3: Disable 5ghz")
    public void step3() {
        String Enable = "0";
        new WirelessQuickViewPage().GoToWirelessSettings();
        new WirelessQuickViewPage(false).DropDown5GhzHighWireless.click();
        new WirelessQuickViewPage(false).Enable5high(Enable);
        new WirelessQuickViewPage(false).DropDown5GhzLowWireless.click();
        MyCommonAPIs.sleepi(10); 
        new WirelessQuickViewPage(false).Enable5low(Enable);
        MyCommonAPIs.sleepi(10);
        MyCommonAPIs.sleepi(120);
        
       
    }
    
    @Step("Test Step 4: Disable 5ghz")
    public void step4() {
    
    String WACAP = new APUtils(WebportalParam.ap2IPaddress).get80211status(cmd);
    String WAXAP = new APUtils(WebportalParam.ap3IPaddress).get80211status(cmd);
    
    assertTrue(WACAP.contains("Access Point: Not-Associated"),"");
    assertTrue(WAXAP.contains("Access Point: Not-Associated"),"");
    
    }

}
