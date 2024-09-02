package webportal.BulkOnboarding.PRJCBUGEN_T28276;
import static com.codeborne.selenide.Selenide.$$;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DeviceBulkOnboardingPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;


public class TestCase extends TestCaseBase {
    
    String organizationName = "PRJCBUGEN_T28276";
    String locationName     = "BulkOnboarding";
    String locationName1     = "ValidLocation";
    String locationName2     = "emptylication";
    String  sOrganizationLocationElement = "#gridView .location-name"; 
    boolean micResult = false;
    
    
    @Feature("Bulk Onboarding") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T28276") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, bulk on-boarding from location level ignores 'location entry' from csv file and all devices are on-boarded to current location") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T28276") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    
    public void test() throws Exception {
        runTest(this);
    }

 
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new OrganizationPage().deleteOrganizationNew(organizationName);
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
    }
    @Step("Test Step 2: Create a organization and location")
    public void step2() {
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName);

        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrganization(organizationInfo);
        OrganizationPage.openOrg(organizationName);

        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", locationName);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "United States of America");
        new AccountPage(false).addNetwork(locationInfo);
        MyCommonAPIs.sleepi(30);       
        
    }
    
    @Step("Test Step 3: Go to the Summary page and import CVS with wrong format")
    public void step3() {
        HamburgerMenuPage hamburgearMenuPage =  new HamburgerMenuPage();
        hamburgearMenuPage.configCreditAllocation(organizationName, 3, 0, 0);
        boolean Result = false;
        OrganizationPage OrganizationPage = new OrganizationPage();
        DeviceBulkOnboardingPage DeviceBulkOnboardingPage = new DeviceBulkOnboardingPage();         
        OrganizationPage.openOrg(organizationName);
        new AccountPage(false).enterLocation(locationName);
        DeviceBulkOnboardingPage.GoToSummaryPage(locationName);
        
        String filePath = DeviceBulkOnboardingPage.GetcurrentPath();
        filePath = filePath + "PRJCBUGEN_T28276\\Bulkonbord.csv";
        System.out.println(filePath);
        System.out.println("check where the screen is");

       new  DeviceBulkOnboardingPage().wronglocationName(filePath);
       
       assertTrue(new DevicesDashPage(false).checkNumberOfDevicesOrganization().equals("Threedevice"), "More device exits");
       
       AccountPage AccountPage =new AccountPage();
       MyCommonAPIs.waitElement(sOrganizationLocationElement);
       ElementsCollection esc = $$(sOrganizationLocationElement);
       for (SelenideElement locelem : esc) { 
           if(locelem.getText().equals("BulkOnboarding")) { 
               System.out.println(locelem.getText()+"is deleteds");
               AccountPage.deleteLocation(locelem.getText());
           }
           }       
       
    }
    
    @Step("Test Step 4: invalid location update")
    public void step4() {
       
       HashMap<String, String> locationInfo = new HashMap<String, String>();
       locationInfo.put("Location Name", locationName1);
       locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
       locationInfo.put("Zip Code", "12345");
       locationInfo.put("Country", "United States of America");
       new AccountPage(false).addNetwork(locationInfo);
       MyCommonAPIs.sleepi(30);       
       
       boolean Result = false;
       OrganizationPage OrganizationPage = new OrganizationPage();
       DeviceBulkOnboardingPage DeviceBulkOnboardingPage = new DeviceBulkOnboardingPage();         
       OrganizationPage.openOrg(organizationName);
       new AccountPage(false).enterLocation(locationName1);
       DeviceBulkOnboardingPage.GoToSummaryPage(locationName1);
       
       String filePath = DeviceBulkOnboardingPage.GetcurrentPath();
       filePath = filePath + "PRJCBUGEN_T28276\\Validlocation.csv";
       System.out.println(filePath);
       System.out.println("check where the screen is");

      new  DeviceBulkOnboardingPage().wronglocationName(filePath);
      
      assertTrue(new DevicesDashPage(false).checkNumberOfDevicesOrganization().equals("Threedevice"), "More device exits");
      
 
      AccountPage AccountPage =new AccountPage();
      MyCommonAPIs.waitElement(sOrganizationLocationElement);
      ElementsCollection esc = $$(sOrganizationLocationElement);
      for (SelenideElement locelem : esc) { 
          if(locelem.getText().equals(locationName1)) { 
              System.out.println(locelem.getText()+"is deleteds");
              AccountPage.deleteLocation(locelem.getText());
          }
          }       
       
    }
    
    
    @Step("Test Step 5: location empty")
    public void step5() {
       
       HashMap<String, String> locationInfo = new HashMap<String, String>();
       locationInfo.put("Location Name", locationName1);
       locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
       locationInfo.put("Zip Code", "12345");
       locationInfo.put("Country", "United States of America");
       new AccountPage(false).addNetwork(locationInfo);
       MyCommonAPIs.sleepi(30);       
       
       boolean Result = false;
       OrganizationPage OrganizationPage = new OrganizationPage();
       DeviceBulkOnboardingPage DeviceBulkOnboardingPage = new DeviceBulkOnboardingPage();         
       OrganizationPage.openOrg(organizationName);
       new AccountPage(false).enterLocation(locationName2);
       DeviceBulkOnboardingPage.GoToSummaryPage(locationName2);
       
       String filePath = DeviceBulkOnboardingPage.GetcurrentPath();
       filePath = filePath + "PRJCBUGEN_T28276\\emptylocation.csv";
       System.out.println(filePath);
       System.out.println("check where the screen is");

      new  DeviceBulkOnboardingPage().wronglocationName(filePath);
      
      assertTrue(new DevicesDashPage(false).checkNumberOfDevicesOrganization().equals("Threedevice"), "More device exits");
      
      
      AccountPage AccountPage =new AccountPage();
      MyCommonAPIs.waitElement(sOrganizationLocationElement);
      ElementsCollection esc = $$(sOrganizationLocationElement);
      for (SelenideElement locelem : esc) { 
          if(locelem.getText().equals(locationName2)) { 
              System.out.println(locelem.getText()+"is deleteds");
              AccountPage.deleteLocation(locelem.getText());
          }
          }       
       
    }
  
    
    

}
