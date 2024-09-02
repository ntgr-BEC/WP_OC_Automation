package webportal.CreateMultipleLocation.PRJCBUGEN_T50005;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

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
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import java.util.Random;

public class TestCase extends TestCaseBase {
    String organizationName = "Organization115";
    String locationName     = "office";
    String warning   = "Cannot add devices from CSV file. Use CSV template column headings, and save file in .CVS format.";
    boolean micResult = false;
    @Feature("Create Multiple Locations Pro") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T50005") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Create Multiple location Pro") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T50005") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    
    public void test() throws Exception {
        runTest(this);
    }

 
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        //new OrganizationPage().deleteOrganizationNew(organizationName);
        UserManage userManage = new UserManage();
        userManage.logout();
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
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "India");
        List<String> Loclist=new ArrayList<String>();  
        int totalLocationsrequired = 50;
        int created = 0;
        for(int i = 1; i <=totalLocationsrequired; i++){
//            try {
            Random random = new Random();
            int num = random.nextInt(100000);
            String formatted = String.format("%05d", num);
            Loclist.add(locationName+i);
            locationInfo.put("Location Name", locationName+i);
            new AccountPage(false).addNetworkforLocationCheck(locationInfo);
            created = created+1;
            System.out.println("Location ---------->"+created+" Created");
            MyCommonAPIs.sleepi(5);
//            Selenide.refresh();
//            Selenide.sleep(5000);
//            Selenide.refresh();
//            Selenide.sleep(5000);
//            assertTrue(new AccountPage(false).VerifyCreatedLocations(Loclist, totalLocationsrequired), "Missing Networks/Locations Noted");
//            MyCommonAPIs.sleepi(5);
//            }
//            catch(Exception e){
////                System.out.println("Exception => " + e.getMessage());
////                System.out.println("Some error occured while Creation new location");
////            }
//            
            }  
        Selenide.sleep(5000);
        Selenide.refresh();
        Selenide.sleep(5000);
        Selenide.refresh();
        Selenide.sleep(5000);
        assertTrue(new AccountPage(false).VerifyCreatedLocations(Loclist, totalLocationsrequired), "Missing Networks/Locations Noted");
    }
    
  
    
    

}
