package webportal.OrganizationWideSSIDEnhancement.EnterpriseOrgWideSSIDAPNotNeeded.PRJCBUGEN_T34366;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

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
import webportal.publicstep.UserManage;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {


    Random r = new Random();
    int num = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num);
    String organizationName = "Netgear";
    String locationName     = "office";
    OrganizationPage OrganizationPage = new OrganizationPage();
    Map<String, String> orgssidInfo = new HashMap<String, String>();

    @Feature("OrganizationWideSSIDEnhancement") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T34366") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify WPA2 Enterprise SSID with 2 bands is pushed to newly created location.") // It's                                                                                                                                                     // Jira
    // Test
    // Case.
    @TmsLink("PRJCBUGEN_T34366") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        new OrganizationPage().deleteOrganizationNew(organizationName);
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to pro account success.")
    public void step1() {

        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);        
           
    }
        
      @Step("Test Step 2: Create an organization and add multiple locations")
      public void step2() {
          
          Map<String, String> organizationInfo = new HashMap<String, String>();
          organizationInfo.put("Name", organizationName);
          OrganizationPage.addOrganization(organizationInfo);
          
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);  
          new OrganizationPage(false).goToOrgSsid(WebportalParam.Organizations);
          orgssidInfo.put("SSID", "PRJCBUGEN_T34366");
          orgssidInfo.put("Security", "WPA2 Enterprise");
          new OrganizationPage(false).createOrgSSIdWPA2Enterprise2Band(orgssidInfo);
          new OrganizationPage(false).openOrg(WebportalParam.Organizations); 
          HashMap<String, String> locationInfo = new HashMap<String, String>();
          locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
          locationInfo.put("Zip Code", "12345");
          locationInfo.put("Country", "United States of America");
          List<String> Loclist=new ArrayList<String>();  
          int totalLocationsrequired = 2;
          int created = 0;
          for(int i = 1; i <=totalLocationsrequired; i++){
              Random random = new Random();
              int num = random.nextInt(100000);
              String formatted = String.format("%05d", num);
              Loclist.add(locationName+i);
              locationInfo.put("Location Name", locationName+i);
              new AccountPage(false).addNetworkforLocationCheck(locationInfo);
              created = created+1;
              System.out.println("Location ---------->"+created+" Created");
              MyCommonAPIs.sleepi(5);             
          }  
          Selenide.sleep(5000);
          Selenide.refresh();
          Selenide.sleep(5000);
          Selenide.refresh();
          Selenide.sleep(5000);
          assertTrue(new AccountPage(false).VerifyCreatedLocations(Loclist, totalLocationsrequired), "Missing Networks/Locations Noted");
      }
      
      @Step("Test Step 3:verify that organizationWide SSID with WPA2 Enterprise Security is pushed to both locaytions ")
      public void step3() {
//          new MyCommonAPIs().sleepi(300);
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new MyCommonAPIs().gotoLoction(WebportalParam.location1);
          new OrganizationPage(false).goWirelessSetting(); 
          orgssidInfo.put("SSID", "PRJCBUGEN_T34366");
          assertTrue(new OrganizationPage(false).verifylocation1WPA2EnterpriseOrgSSID2Bands(orgssidInfo)," org wide ssid with wpa2 enterprise security not pushed successfull.");  
          
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new MyCommonAPIs().gotoLoction(WebportalParam.location2);
          new OrganizationPage(false).goWirelessSetting();
          orgssidInfo.put("SSID", "PRJCBUGEN_T34366");
          assertTrue(new OrganizationPage(false).verifylocation1WPA2EnterpriseOrgSSID2Bands(orgssidInfo)," org wide ssid with wpa2 enterprise security not pushed successfull."); 
      }
}