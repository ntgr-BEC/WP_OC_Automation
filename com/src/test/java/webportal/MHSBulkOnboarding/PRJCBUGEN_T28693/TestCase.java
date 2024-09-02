package webportal.MHSBulkOnboarding.PRJCBUGEN_T28693;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
public class TestCase extends TestCaseBase {
    
    /**
    *
    * @author Anusha H
    *
    */

    
    
    String organizationName = "Netgear";
    String locationName     = "office1";
    boolean micResult = false;
    @Feature("MHSBulkOnboarding") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T28693") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Onboard 1000 MHS devices at Organisation level with a Valid location and Profile name") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T28693") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    
    public void test() throws Exception {
        runTest(this);
    }

 
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        AccountPage AccountPage =new AccountPage();
        AccountPage.deleteLocation(locationName);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
    
    }
    
    @Step("Test Step 2: Create a location;")
    public void step2() {
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "office1");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "32003");
        locationInfo.put("Country", "United States of America");
        new HamburgerMenuPage();
        new AccountPage().addNetwork(locationInfo);
    }
    
    @Step("Test Step 3:  Browse across the webportal in  all the Device adding screen click on the (+) option and In List down option we should see \" Add Multiple Device \" ")
    public void step3() {
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.openOrg(organizationName);
     
        DeviceBulkOnboardingPage DeviceBulkOnboardingPage = new DeviceBulkOnboardingPage();
        
        String filePath = DeviceBulkOnboardingPage.GetcurrentPath12();
        filePath = filePath + "PRJCBUGEN_T28693\\MHSBulkOnboarding.csv";
        System.out.println(filePath);
        
        new DeviceBulkOnboardingPage().ImportCvsFileviaOrgScreen(filePath);
        assertTrue(new DevicesDashPage(false).checkNumberOfDevicesOrganization().equals("OneThousand"), "More device exits");
        
    }
   
    
}
