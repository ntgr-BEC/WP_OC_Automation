package webportal.BulkOnboarding.PRJCBUGEN_T14989;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
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
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
public class TestCase extends TestCaseBase  {
    String organizationName = "PRJCBUGEN_T14989";
    String locationName     = "BulkOnboarding";
    String warning   = "Location does not match.";
    boolean micResult = false;
    @Feature("Bulk Onboarding") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14989") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify what happens when user uploads a CSV file without adding locations to few of the device row") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T14989") // It's a testcase id/link from Jira Test Case.
    
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
        OrganizationPage.addAllocateCredits(organizationName, "10"); 
        OrganizationPage.openOrg(organizationName);
        
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", locationName);
        //locationInfo.put("Location Name", WebportalParam.location1);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "China");
        new AccountPage(false).addNetwork(locationInfo);
        MyCommonAPIs.sleepi(30);       
       
    }
    
    @Step("Test Step 3: Prepare CSV file with 5 Devices with respective serial number ,but with following configurations  In first two  devices  row  with proper location name \r\n" + 
            "Third device row with wrong location name \r\n" + 
            "fourth and fifth device row with no location nam.")
    public void step3 () throws IOException  {
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.openOrg(organizationName);
        new AccountPage(false).enterLocation(locationName);
        //handle.gotoLoction();
        //new AccountPage().enterLocation(WebportalParam.location1);
        System.out.println("!@@@@@@@@@@@@@@@@@@@@@@@!!!!!!!!!!!1");
        System.out.println("!@@@@@@@@@@@@@@@@@@@@@@@!!!!!!!!!!!20");
        boolean Result = false;
        System.out.println("!@@@@@@@@@@@@@@@@@@@@@@@!!!!!!!!!!!2");
        DeviceBulkOnboardingPage DeviceBulkOnboardingPage = new DeviceBulkOnboardingPage();
        DeviceBulkOnboardingPage.GoToSummaryPage(locationName);
        String filePath = DeviceBulkOnboardingPage.GetcurrentPath();
        filePath = filePath + "PRJCBUGEN_T14989\\Device.csv";
        System.out.println(filePath);
        DeviceBulkOnboardingPage.ImportCvsFileOfWrongLocationName(filePath);
        MyCommonAPIs.sleepi(10);
        
        
    }
    @Step("Test Step 4: User should be able to successfully add / edit locations  .")
    public void step4 ()  {
        boolean Result = false;
        DevicesDashPage DevicesDashPage= new DevicesDashPage();
       // new MyCommonAPIs().open(URLParam.hrefaccount, true);
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.openOrg(organizationName);
        new AccountPage(false).enterLocation(locationName);
        System.out.print( DevicesDashPage.getDevicesInfo().size());
        int test = DevicesDashPage.getDevicesInfo().size();
        System.out.print(test);
        System.out.println("000000000000000000000000000");
        
       if( test == 5) {          
               Result = true;   
           
       }
       System.out.print(Result);
       if (Result == true) {
           micResult =  true;
           assertTrue(micResult,"Pass:In Add Multiple Devices  pop-up --> Device credit should get updated and  shown as 5  !");  
       }else {
           micResult =  false;
           assertTrue(micResult,"Failed:In Add Multiple Devices  pop-up --> Device credit doesn't get updated and  shown as 5 !"); 
       }
        
        
    }
}