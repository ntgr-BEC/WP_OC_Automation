package webportal.DeviceGroup.CustomGroupNPI.PRJCBUGEN_T32420;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
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
    Map<String, String> cfgInfo = new HashMap<String, String>();
    String              msg     = "";
    String cmd = "iwconfig wifi0vap0 | grep Access";

    @Feature("DeviceGroup CustomGroupNPI") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T32420") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, first config of device on-boarded to CG will have CG's config will have SSID/CP/wifi schedule/Fast roaming/LB/adv config") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T32420") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        OrganizationPage.openOrg(WebportalParam.Organizations);
        new DeviceGroupPage().GoToDeviceGroup(WebportalParam.location1);
        MyCommonAPIs.sleepi(5);
        new DeviceGroupPage().deleteCGGroupALL();
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        
        OrganizationPage.openOrg(WebportalParam.Organizations);
    }

    @Step("Test Step 2: Add device to DG;")
    public void step2() {
        
        new DeviceGroupPage().GoToDeviceGroup(WebportalParam.location1);
        MyCommonAPIs.sleepi(5);
        new DeviceGroupPage().CreateDGGroup("Automation1", "Check Grop creation");
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
     
    }
    
   
    
    @Step("Test Step 5: oboard device and wait for config Push;")
    public void step5() {
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap1serialNo);
        devInfo.put("MAC Address1", WebportalParam.ap1macaddress);

        new DeviceGroupPage().addNewDevice(devInfo, "Automation1");
        MyCommonAPIs.sleepi(60); 
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        MyCommonAPIs.sleepi(60); 

    }
        
    @Step("Test Step 6: Disable 5ghz")
    public void step6() {
      
    String WACAP1 = new APUtils(WebportalParam.ap1IPaddress).get80211status(cmd);  
    assertTrue(WACAP1.contains("Access Point: Not-Associated"),"");   
    }  
}