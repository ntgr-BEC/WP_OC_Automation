package webportal.DeviceGroup.CustomGroupNPI.WithAP.PRJCBUGEN_T32407;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DeviceGroupPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;
import webportal.webelements.DeviceGroupElement;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    OrganizationPage OrganizationPage = new OrganizationPage();
    Map<String, String> locationInfo = new HashMap<String, String>();
    String cmd = "iwconfig wifi0vap0 | grep Access";

    @Feature("DeviceGroup CustomGroupNPI") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T32407") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, advanced wireless radio config is applied on all devices on CG") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T32407") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        String Enable = "1";
        MyCommonAPIs.sleepi(5);
        new WirelessQuickViewPage(false).GoToWirelessSettings();
        MyCommonAPIs.sleepi(10);
        new WirelessQuickViewPage(false).Enable24(Enable);
        MyCommonAPIs.sleepi(5);
        new DeviceGroupPage().DGWiFi.click();
        new WirelessQuickViewPage(false).deleteSsidYes("apwp19980");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        
        OrganizationPage.openOrg(WebportalParam.Organizations);
    }

    @Step("Test Step 2: Check CG name and description shown;")
    public void step2() {
        
        new DeviceGroupPage().GoToDeviceGroup(WebportalParam.location1);
        MyCommonAPIs.sleepi(5);
        new DeviceGroupPage().goinsideDG("Automation1");
        MyCommonAPIs.sleepi(5);
        new DeviceGroupPage().DGWiFi.click();
       
    }
    
    @Step("Test Step 3: Add WIFI ssid and edit siid with Name, Security and Password;")
    public void step3() {
             
        locationInfo.put("SSID", "apwp19980");
        locationInfo.put("Band", "Both");
        locationInfo.put("Security", "WPA2 Personal");
        locationInfo.put("Password", "123456798");
        new WirelessQuickViewPage(false).addSsid1(locationInfo);
        
    }
    
    @Step("Test Step 4: Disable 5ghz")
    public void step4() {
        String Enable = "0";
        new WirelessQuickViewPage(false).GoToWirelessSettings();
        MyCommonAPIs.sleepi(10);
        new WirelessQuickViewPage(false).Enable24(Enable);
        MyCommonAPIs.sleepi(120);        
    }
    
    @Step("Test Step 5: Disable 5ghz")
    public void step5() {
    
  
    String WACAP1 = new APUtils(WebportalParam.ap1IPaddress).get80211status(cmd);  
    assertTrue(WACAP1.contains("Access Point: Not-Associated"),"");   
    }
}