package webportal.CFD.CFD_Pro.PRJCBUGEN_T28362;
import static com.codeborne.selenide.Selenide.$x;
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
    String organizationName = "Organization";
    String locationName     = "office";
    int TotalOrgCount = 70;
    String warning   = "Cannot add devices from CSV file. Use CSV template column headings, and save file in .CVS format.";
    boolean micResult = false;
    @Feature("Create Multiple Organizations for Pro") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T28362") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify all organization are shown in credits allocation page when customer has more than 70 organizations") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T28362") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    
    public void test() throws Exception {
        runTest(this);
    }

 
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
       
        for(int i =1; i<= TotalOrgCount; i++) {
        new OrganizationPage().deleteOrganizationNew(organizationName+i);
        }
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
        int count = 0;
        List<String> Orglist=new ArrayList<String>();  
        for(int i =1; i<= TotalOrgCount; i++) {
            Map<String, String> organizationInfo = new HashMap<String, String>();
            organizationInfo.put("Name", organizationName+i);
            OrganizationPage OrganizationPage = new OrganizationPage();
            OrganizationPage.addOrganization(organizationInfo);
            Orglist.add(organizationName+i);
            count = count+1;
            System.out.println("Organization ------> "+count+" Created");
            Selenide.refresh();   
            MyCommonAPIs.sleepi(10);} 
        Selenide.sleep(3000);
        Selenide.refresh();
        Selenide.sleep(3000);
        Selenide.refresh();
        Selenide.sleep(3000);
        assertTrue(new AccountPage(false).VerifyCreatedOrganizatons(Orglist, TotalOrgCount), "Missing Organization Noted");
    }
    
    @Step("Test Step 3: Check Org anme in credit allocation page")
    public void step3() {
        
        new HamburgerMenuPage().gotoCreditsAllocationPage();
        for(int i =1; i<= TotalOrgCount; i++) {
        assertTrue( new HamburgerMenuPage(false).orgexits(organizationName+i).exists()  , "org name does not exits in credit allocation page");
        
    }
        }
  

}
    
    


