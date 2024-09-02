package webportal.OlaTestcases.PRJCBUGEN_T13886;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
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
import util.MyCommonAPIs;
import util.RunCommand;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.FileHandling;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;
import webportal.weboperation.RadiusPage;

/**
 * @author Tejeshwini K V
 */
public class Testcase extends TestCaseBase {


    Random r = new Random();
    String organizationName = "Netgear";
    String locationName     = "office";
    String organizationName1 = "Netgear1";
    String locationName1     = "office1";
    String organizationName2 = "Netgear2";
    String locationName2     = "office2";

    Map<String, String> SSID = new HashMap<String, String>();
    Map<String, String> SSID1 = new HashMap<String, String>();

    @Feature("OlaTestcases") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T13886") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Make sure new location gets org level SSIDs ( both enterprise and non-enterprise)") // It's                                                                                                                                                     // Jira
    // Test
    // Case.
    @TmsLink("PRJCBUGEN_T13886") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new OrganizationPage(false).goToOrgSsid(WebportalParam.Organizations);
        new WirelessQuickViewPage(false).deleteOrgSsidYes("PRJCBUGEN_T34389");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to pro account success.")
    public void step1() {

        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword); 
        
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        new MyCommonAPIs().gotoLoction("office1");
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        new MyCommonAPIs().gotoLoction("office2");
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap2serialNo);
           
    }
        
      @Step("Test Step 2: Add organizationWide SSID with WPA2 Enterprise Security")

      public void step2() {
          
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);  
          new OrganizationPage(false).goToOrgSsid(WebportalParam.Organizations);
         
          SSID.put("SSID", "PRJCBUGEN_T13884");
          SSID.put("Security", "WPA2 Enterprise");
          new OrganizationPage(false).createOrgSSIdWPA2Enterprise2Band(SSID);
          
          SSID1.put("SSID", "PRJCBUGEN_T13882");
          SSID1.put("Security", "WPA2 Personal Mixed");
          SSID1.put("Password", "123456798");
          new OrganizationPage(false).CreateOrgSSId(SSID1);

      }
      
      
      
      @Step("Test Step 3:verify that organizationWide SSID with WPA2 Enterprise Security is pushed to both locaytions as well as AP's ")
      public void step3() {
          
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new MyCommonAPIs().gotoLoction(WebportalParam.location1);
          new OrganizationPage(false).goWirelessSetting(); 
          assertTrue(new OrganizationPage(false).verifylocation1WPA2EnterpriseOrgSSID2Bands(SSID)," org wide ssid with wpa2 enterprise security not pushed successfull.");  
          assertTrue(new OrganizationPage(false).verifylocation1WPA2EnterpriseOrgSSID2Bands(SSID1)," org wide ssid with wpa2 personal mixed security not pushed successfull.");  
          
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new MyCommonAPIs().gotoLoction(WebportalParam.location2);
          new OrganizationPage(false).goWirelessSetting();
          assertTrue(new OrganizationPage(false).verifylocation1WPA2EnterpriseOrgSSID2Bands(SSID)," org wide ssid with wpa2 enterprise security not pushed successfull."); 
          assertTrue(new OrganizationPage(false).verifylocation1WPA2EnterpriseOrgSSID2Bands(SSID1)," org wide ssid with wpa2 personal mixed security not pushed successfull."); 
            
              
      }
      
      
      @Step("Test Step 4: Verify that the user is able to create an organization without an owner")
      public void step4() {
          OrganizationPage page = new OrganizationPage();
          page.gotoPage();
          
          Map<String, String> organizationInfo = new HashMap<String, String>();
          organizationInfo.put("Name", organizationName1);         
          page.addOrganization(organizationInfo);
          assertTrue(page.checkOrganizationIsExist(organizationName1), "check user is able to create an organization");
          page.openOrg(organizationName1);
          
          HashMap<String, String> locationInfo = new HashMap<String, String>();      
          locationInfo.put("Location Name", locationName1);
          locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
          locationInfo.put("Zip Code", "12345");
          locationInfo.put("Country", "United States of America");
          new AccountPage().addNetwork(locationInfo);
          
          
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new MyCommonAPIs().gotoLoction(WebportalParam.location2);
          new OrganizationPage(false).goWirelessSetting();
          assertTrue(new OrganizationPage(false).verifylocation1WPA2EnterpriseOrgSSID2Bands(SSID)," org wide ssid with wpa2 enterprise security not pushed successfull."); 
          assertTrue(new OrganizationPage(false).verifylocation1WPA2EnterpriseOrgSSID2Bands(SSID1)," org wide ssid with wpa2 personal mixed security not pushed successfull."); 
      }
      
     
             
      
}
