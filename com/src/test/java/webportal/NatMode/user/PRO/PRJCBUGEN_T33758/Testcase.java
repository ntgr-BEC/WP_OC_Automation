package webportal.NatMode.user.PRO.PRJCBUGEN_T33758;

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
 * @author Ravishankar S
 *
 */
public class Testcase extends TestCaseBase {
    
    OrganizationPage OrganizationPage = new OrganizationPage();
    Map<String, String> devInfo = new HashMap<String, String>();
    Map<String, String> ssidInfo = new HashMap<String, String>();

    @Feature("NatMode") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T33758") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Nat mode should support in custom groups") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T33758") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().deleteALLSSID();
        new DeviceGroupPage().deleteDG("Automation1");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {

        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
       
        
        OrganizationPage.openOrg(WebportalParam.Organizations);
    }
    
    @Step("Test step 2: DELETE THE AP")
    public void step2() 
    {   
        new DevicesDashPage().deleteDeviceNo(WebportalParam.ap1serialNo);
    }

    @Step("Test Step 3: Check CG name and description shown;")
    public void step3() {
        
        new DeviceGroupPage().GoToDeviceGroup(WebportalParam.location1);
        MyCommonAPIs.sleepi(5);
        new DeviceGroupPage().CreateDGGroup("Automation1", "Check Grop creation");
               
        
        devInfo.put("Serial Number", WebportalParam.ap1serialNo);
        devInfo.put("MAC Address1", WebportalParam.ap1macaddress);

        assertTrue(new DeviceGroupPage().addNewDevicedropdown(devInfo, "Automation1"),"dropdown to select CG step is shown");      
        
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ob1serialNo);
     
    }
    
    
    @Step("Test Step 4: Check CG for AP ssid;")
    public void step4() {
        ssidInfo.put("SSID", "apwp14008");
        ssidInfo.put("Security", "WPA2 Personal");
        ssidInfo.put("Password", "12345678");
        

        OrganizationPage.openOrg(WebportalParam.Organizations);
        new DeviceGroupPage().GoToDeviceGroup(WebportalParam.location1);
        MyCommonAPIs.sleepi(5);
        new DeviceGroupPage().goinsideDG("Automation1");
        MyCommonAPIs.sleepi(5);
        MyCommonAPIs.sleepi(5);
        new DeviceGroupPage().DGWiFi.click();
        new WirelessQuickViewPage(false).addSsidNat(ssidInfo);
        


        MyCommonAPIs.sleepi(60);
        assertTrue(new APUtils(WebportalParam.ap1IPaddress).getNatStatus(WebportalParam.ap1Model), "CONFIG NOT PUSHED");
        
        
        
        
        
      
  
        
    }
        

}
