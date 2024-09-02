package webportal.BulkOnboarding.PRJCBUGEN_T15001;


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
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
public class TestCase extends TestCaseBase  {
    String organizationName = "PRJCBUGEN_T15001";
    String locationName     = "BulkOnboarding";
    String warning   = "Upload .CSV format files only.";
    boolean micResult = false;
    @Feature("Bulk Onboarding") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T15001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that webportal has limit on csv file size") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T15001") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    
    public void test() throws Exception {
        runTest(this);
    }

 
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new OrganizationPage().deleteOrganizationNew(organizationName);
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
        locationInfo.put("Location Name", locationName);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "United States of America");
        new AccountPage(false).addNetwork(locationInfo);
        MyCommonAPIs.sleepi(30);       
        
    }
    
    @Step("Test Step 3: Go to the Summary page and import CVS with wrong format")
    public void step3() {
        boolean Result = false;
        OrganizationPage OrganizationPage = new OrganizationPage();
        DeviceBulkOnboardingPage DeviceBulkOnboardingPage = new DeviceBulkOnboardingPage();         
        OrganizationPage.openOrg(organizationName);
        new AccountPage(false).enterLocation(locationName);
        DeviceBulkOnboardingPage.GoToSummaryPage(locationName);
        
        String filePath = DeviceBulkOnboardingPage.GetcurrentPath();
        filePath = filePath + "PRJCBUGEN_T15001\\Bulkonbord.zip";
        System.out.println(filePath);
        System.out.println("check where the screen is");

        String warningForWrongFormat = DeviceBulkOnboardingPage.ImportWrongformatFile85(filePath);
        if(warningForWrongFormat.equals(warning)) {
            Result = true;
        }
        System.out.print(Result);
        if (Result == true) {
            micResult =  true;
            assertTrue(micResult,"Pass:System give correct message for wrong format CVS !");  
        }else {
            micResult =  false;
            assertTrue(micResult,"Failed:System give incorrect message for wrong format CVS !"); 
        } 
       
    }
    
}




