package webportal.WebPoratlPerformanceImprovement.PRJCBUGEN_T33222;
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
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DeviceBulkOnboardingPage;
import webportal.weboperation.DeviceScreenNavigationPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;

/**
*
* @ Tejeshwini K V
*
*/
public class TestCase extends TestCaseBase {

    String organizationName = "PRJCBUGEN_T33222";
    String locationName     = "BulkOnboarding";
    boolean micResult = false;
    @Feature("Web Portal Performance Improvements") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T33222") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify,the performance when 2500-4000 devices and device type will Switch, AP and Orbi on device list.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T33222") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    
    public void test() throws Exception {
        runTest(this);
    }

 
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new OrganizationPage().deleteOrganizationNew(organizationName);
       }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
    }
    
    
    @Step("Test Step 2: Create a organization and location")
    public void step2() {
//        Map<String, String> organizationInfo = new HashMap<String, String>();
//        organizationInfo.put("Name", organizationName);
//
//        OrganizationPage OrganizationPage = new OrganizationPage();
//        OrganizationPage.addOrganization(organizationInfo);
//        OrganizationPage.addAllocateCredits(organizationName, "3000", "0", "0");
//        OrganizationPage.openOrg(organizationName);
//
//        HashMap<String, String> locationInfo = new HashMap<String, String>();
//        locationInfo.put("Location Name", locationName);
//        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
//        locationInfo.put("Zip Code", "12345");
//        locationInfo.put("Country", "United States of America");
//        new AccountPage(false).addNetwork(locationInfo);
//        MyCommonAPIs.sleepi(30);       
        
    }
    @Step("Test Step 3:  Browse across the webportal in  all the Device adding screen click on the (+) option and In List down option we should see \" Add Multiple Device \" ")
    public void step3() {
        
//        OrganizationPage OrganizationPage = new OrganizationPage();
//        OrganizationPage.openOrg(organizationName);
//        new AccountPage(false).enterLocation(locationName);
//        boolean Result = false;
//     
//        DeviceBulkOnboardingPage DeviceBulkOnboardingPage = new DeviceBulkOnboardingPage();
//        DeviceBulkOnboardingPage.GoToSummaryPage(locationName);
//        
//        String filePath = DeviceBulkOnboardingPage.GetcurrentPathWebPoratlPerformanceImprovement();
//        filePath = filePath + "PRJCBUGEN_T33222\\BulkonbordAP.csv";
//       
//        System.out.println(filePath);
//        
//        new DeviceBulkOnboardingPage().ImportCvsFilesummaryscreen(filePath);
//        
//
//        DeviceBulkOnboardingPage.GoToSummaryPage(locationName);
//
//        MyCommonAPIs.sleepi(30);
//        
//        String filePath1 = DeviceBulkOnboardingPage.GetcurrentPathWebPoratlPerformanceImprovement();
//        filePath1 = filePath1 + "PRJCBUGEN_T33222\\Bulkonbordorbi.csv";
//        System.out.println(filePath1);
//        
//        new DeviceBulkOnboardingPage().ImportCvsFilesummaryscreen(filePath1);
//        
//        MyCommonAPIs.sleepi(30);
//        
//        String filePath2 = DeviceBulkOnboardingPage.GetcurrentPathWebPoratlPerformanceImprovement();
//        filePath2 = filePath2 + "PRJCBUGEN_T33222\\Bulkonbordswitch.csv";
//        System.out.println(filePath2);
//        
//        DeviceBulkOnboardingPage.GoToSummaryPage(locationName);
//        
//        new DeviceBulkOnboardingPage().ImportCvsFilesummaryscreen(filePath2);
//        
//        MyCommonAPIs.sleepi(20);
      
    }
    
    @Step("Test Step 4: able to see correct count of devices in device screen ")
    public void step4() {
        
//        assertTrue(new DevicesDashPage().DeviceCount("3000"), "not able to see correct count of devices in device screen");
        
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        new OrganizationPage(false).goToOrgSsid(WebportalParam.Organizations);
        
        assertTrue(new OrganizationPage(false).enableOrgSsid.isDisplayed(),"org wide ssid page not visible when we have 300 devices ");
        
    }
    
  
    
}
