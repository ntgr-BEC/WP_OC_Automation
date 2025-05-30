package webportal.OrganizationWideSSIDEnhancement.EnterpriseOrgWideSSIDAPNotNeeded.PRJCBUGEN_T34388;

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
    @Story("PRJCBUGEN_T34388") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify WPA3 Enterprise SSID with all bands is pushed to newly created location") // It's                                                                                                                                                     // Jira
    // Test
    // Case.
    @TmsLink("PRJCBUGEN_T34388") // It's a testcase id/link from Jira Test Case.

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
          orgssidInfo.put("SSID", "PRJCBUGEN_T34388");
          orgssidInfo.put("Security", "WPA3 Enterprise");
          new OrganizationPage(false).createOrgSSIdWPA3EnterpriseAllBand(orgssidInfo);
          List<String> Loclist=new ArrayList<String>();  
          int totalLocationsrequired = 2;
          int created = 0;
          new OrganizationPage(false).openOrg("Netgear");
          HashMap<String, String> locationInfo = new HashMap<String, String>();
          locationInfo.put("Location Name", "office1");
          locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
          locationInfo.put("Zip Code", "70184");
          locationInfo.put("Country", "Germany");
          new AccountPage(false).addNetwork(locationInfo);
          new OrganizationPage(false).openOrg("Netgear");
          HashMap<String, String> locationInfo1 = new HashMap<String, String>();
          locationInfo1.put("Location Name", "office2");
          locationInfo1.put("Device Admin Password", WebportalParam.loginDevicePassword);
          locationInfo1.put("Zip Code", "70184");
          locationInfo1.put("Country", "Germany");
          new AccountPage(false).addNetwork(locationInfo1);
          assertTrue(new AccountPage(false).VerifyCreatedLocations(Loclist, totalLocationsrequired), "Missing Networks/Locations Noted");
      }
      
      @Step("Test Step 3:verify that organizationWide SSID with WPA2 Enterprise Security is pushed to both locaytions ")
      public void step3() {
//          new MyCommonAPIs().sleepi(300);
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new MyCommonAPIs().gotoLoction(WebportalParam.location1);
          new OrganizationPage(false).goWirelessSetting(); 
          orgssidInfo.put("SSID", "PRJCBUGEN_T34388");
          assertTrue(new OrganizationPage(false).verifylocation1WPA3EnterpriseOrgSSIDAllBands(orgssidInfo)," org wide ssid with wpa3 enterprise security not pushed successfull.");  
          
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new MyCommonAPIs().gotoLoction(WebportalParam.location2);
          new OrganizationPage(false).goWirelessSetting();
          orgssidInfo.put("SSID", "PRJCBUGEN_T34388");
          assertTrue(new OrganizationPage(false).verifylocation1WPA3EnterpriseOrgSSIDAllBands(orgssidInfo)," org wide ssid with wpa3 enterprise security not pushed successfull."); 
      }
}