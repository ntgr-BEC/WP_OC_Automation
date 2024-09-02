package webportal.CreateMultipleLocation.PRJCBUGEN_T50006;
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
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import java.util.Random;

public class TestCase extends TestCaseBase {
    String locationName     = "BECnewLoc";
    //String organizationName = "MultipleLocationSumanta";
    boolean micResult = false;
    Random              r           = new Random();
    int                 num         = r.nextInt(10000000);
    String              mailname    = "apwptest" + String.valueOf(num);
    @Feature("Create Multiple Locations") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T50006") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Create Multiple Locations") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T50006") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    
    public void test() throws Exception {
        runTest(this);
    }

 
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        UserManage userManage = new UserManage();
        userManage.logout();
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.loginName,WebportalParam.loginPassword);
        
//        Map<String, String> accountInfo = new HashMap<String, String>();
//        accountInfo.put("First Name", mailname);
//        accountInfo.put("Last Name", "T24065");
//        accountInfo.put("Email Address", mailname + "@mailinator.com");
//        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
//        accountInfo.put("Password", "Netgear#123");
//        accountInfo.put("Confirm Password", "Netgear#123");
//        accountInfo.put("Country", "Australia");

       // new HamburgerMenuPage(false).createAccount(accountInfo);
        
    }
    @Step("Test Step 2: Create location and Verify each location after Creating")
    public void step2() {        
        //new HamburgerMenuPage(false).closeLockedDialog();
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "L6A");
        locationInfo.put("Country", "Canada");
        List<String> Loclist=new ArrayList<String>();  
        int totalLocationsrequired = 75;
        int created = 0;
        for(int i = 1; i <=totalLocationsrequired; i++){
            Random random = new Random();
            int num = random.nextInt(100000);
            String formatted = String.format("%05d", num);
            Loclist.add(locationName+i);
            locationInfo.put("Location Name", locationName+i);
            new AccountPage(false).addNetwork(locationInfo);
            created = created+1;
            System.out.println("Location ---------->"+created+" Created");
            Selenide.refresh();
            MyCommonAPIs.sleepi(30);
            }        
        Selenide.sleep(5000);
        Selenide.refresh();
        Selenide.sleep(5000);
        Selenide.refresh();
        Selenide.sleep(5000);
        assertTrue(new AccountPage(false).VerifyCreatedLocations(Loclist, totalLocationsrequired), "Missing Networks/Locations Noted");
        MyCommonAPIs.sleepi(30);
    }
    
  
    
    

}
