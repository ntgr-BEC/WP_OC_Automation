package webportal.WebPoratlPerformanceImprovement.BulkOnboarding.PRJCBUGEN_T33225;
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
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DeviceBulkOnboardingPage;
import webportal.weboperation.DeviceScreenNavigationPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
*
* @ Tejeshwini K V
*
*/
public class TestCase extends TestCaseBase {

    String organizationName = "PRJCBUGEN_T33225";
    String locationName     = "BulkOnboarding";
    boolean micResult = false;
    @Feature("Web Portal Performance Improvements") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T33225") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that removal of topology section from summary screen should be successful to increase performance.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T33225") // It's a testcase id/link from Jira Test Case.
    
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
    @Step("Test Step 3:  Browse across the webportal in  all the Device adding screen click on the (+) option and In List down option we should see \" Add Multiple Device \" ")
    public void step3() {
        
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.openOrg(organizationName);
        new AccountPage(false).enterLocation(locationName);
        boolean Result = false;
     
        DeviceBulkOnboardingPage DeviceBulkOnboardingPage = new DeviceBulkOnboardingPage();
        DeviceBulkOnboardingPage.GoToSummaryPage(locationName);
        
        String filePath = DeviceBulkOnboardingPage.GetcurrentPathWebPoratlPerformanceImprovement();
        filePath = filePath + "PRJCBUGEN_T33225\\Bulkonbord.csv";
        System.out.println(filePath);
        
        new DeviceBulkOnboardingPage().ImportCvsFilesummaryscreen(filePath);
        
        MyCommonAPIs.sleepi(20);
      
    }
    
    @Step("Test Step 4: able to see correct count of devices in device screen ")
    public void step4() {
        
        assertTrue(new DevicesDashPage().DeviceCount("500"), "not able to see correct count of devices in device screen");
        
      
    }
    
  
    @Step("Test Step 5: Verify Summary page")
    public void step5() {
        
        new WirelessQuickViewPage(false).GoToTopology();
    }
}
