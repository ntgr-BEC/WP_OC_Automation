package webportal.RF_WLAN_Profile.Premium.PRJCBUGEN_T39745;

import static org.testng.Assert.assertTrue;

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
import util.RunCommand;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author  Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    Map<String, String> ssidInfo = new HashMap<String, String>();

    @Feature("RF_WLAN_Profile.Premium") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T39745") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether user able to enable / disable Customer profile option in Add / Edit SSID") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T39745") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");

    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccount();
       
    }

    @Step("Test Step 2: While creating ssid add customer profile and verify")
    public void step2() {
       
        
        ssidInfo.put("SSID", "apwp14270");
        ssidInfo.put("Security", "WPA2 Personal Mixed");
        ssidInfo.put("Password", "123456798");
        ssidInfo.put("custom", "enable");
        new WirelessQuickViewPage().addSsidcustom(ssidInfo);
        
        assertTrue(!new WirelessQuickViewPage().checkCustomProfileeditSSID(ssidInfo),"RF is disabled");
       
    }
    
    @Step("Test Step 3: disable customer profile and verify")
    public void step3() {
              

        ssidInfo.put("custom", "disable");
        new WirelessQuickViewPage().addSsidcustom(ssidInfo);
        
        assertTrue(new WirelessQuickViewPage().checkCustomProfileeditSSID(ssidInfo),"RF is enabled");
       
    }
    
    
   
    
       

}
