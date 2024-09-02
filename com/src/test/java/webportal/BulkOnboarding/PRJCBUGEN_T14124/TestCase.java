package webportal.BulkOnboarding.PRJCBUGEN_T14124;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
public class TestCase extends TestCaseBase  {
    String organizationName = "PRJCBUGEN_T14124";
    String locationName     = "BulkOnboarding";
    String warning   = "Verify user able to browse and select a CSV file in Add Multiple Devices Page.";
    boolean micResult = false;
    @Feature("Bulk Onboarding") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14124") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify user able to drag and drop CSV file in Add Multiple Devices Page.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T14124") // It's a testcase id/link from Jira Test Case.
    
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
        HamburgerMenuPage hamburgearMenuPage =  new HamburgerMenuPage();
        hamburgearMenuPage.configCreditAllocation(organizationName, 3, 0, 0);
        boolean Result = false;
        
        DeviceBulkOnboardingPage DeviceBulkOnboardingPage = new DeviceBulkOnboardingPage();         
        
        String filePath = DeviceBulkOnboardingPage.GetcurrentPath();
        filePath = filePath + "PRJCBUGEN_T14124\\Bulkonbord.csv";
        System.out.println(filePath);
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.openOrg(organizationName);
        new AccountPage(false).enterLocation(locationName); 
        System.out.println("check where the screen is");
        new DeviceBulkOnboardingPage().ImportCvsFile(filePath);

      
        assertTrue(new DevicesDashPage(false).checkNumberOfDevicesOrganization().equals("Threedevice"), "More device exits");
    }
//    }
//    @Step("Test Step 2: Create a organization and location")
//    public void step2() {
//        Map<String, String> organizationInfo = new HashMap<String, String>();
//        organizationInfo.put("Name", organizationName);
//
//        OrganizationPage OrganizationPage = new OrganizationPage();
//        OrganizationPage.addOrganization(organizationInfo);
//        OrganizationPage.addAllocateCredits(organizationName, "1"); 
//        OrganizationPage.openOrg(organizationName);
//        
//        HashMap<String, String> locationInfo = new HashMap<String, String>();
//        locationInfo.put("Location Name", locationName);
//        //locationInfo.put("Location Name", WebportalParam.location1);
//        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
//        locationInfo.put("Zip Code", "12345");
//        locationInfo.put("Country", "China");
//        new AccountPage(false).addNetwork(locationInfo);
//        MyCommonAPIs.sleepi(30);       
//       
//    }
//    
//    @Step("Test Step 3: Go to the Summary page and import CVS with wrong format")
//    public void step3 () throws IOException  {
//        OrganizationPage OrganizationPage = new OrganizationPage();
//        OrganizationPage.openOrg(organizationName);
//        new AccountPage(false).enterLocation(locationName);
//        //handle.gotoLoction();
//        System.out.println("!@@@@@@@@@@@@@@@@@@@@@@@!!!!!!!!!!!1");
//        System.out.println("!@@@@@@@@@@@@@@@@@@@@@@@!!!!!!!!!!!20");
//        boolean Result = false;
//        System.out.println("!@@@@@@@@@@@@@@@@@@@@@@@!!!!!!!!!!!2");
//        DeviceBulkOnboardingPage DeviceBulkOnboardingPage = new DeviceBulkOnboardingPage();
//        DeviceBulkOnboardingPage.GoToSummaryPage(locationName);
//        String filePath = DeviceBulkOnboardingPage.GetcurrentPath();
//        System.out.println(filePath);
//        filePath = filePath + "PRJCBUGEN_T14124";
//        System.out.println(filePath);
//        
//        Map<String, String> devInfo=new HashMap<String, String>();
//        devInfo.put("DEVICE SERIAL", WebportalParam.ap1serialNo);
//        devInfo.put("DEVICE NAME", WebportalParam.ap1deveiceName);
//        devInfo.put("LOCATION NAME",locationName);
//        List <Map<String, String>> listInfo = new ArrayList<Map<String, String>>();
//        listInfo.add(devInfo);
//        String fileName = DeviceBulkOnboardingPage.SetExcelFile(listInfo, filePath);
//        
//        MyCommonAPIs.sleepi(30);
//        System.out.println(fileName);
//        DeviceBulkOnboardingPage.ImportCvsFile(fileName);
//        DevicesDashPage DevicesDashPage= new DevicesDashPage();
//        new AccountPage(false).enterLocation(locationName);
//        boolean Result1 = false;
//        Result1 = DevicesDashPage.waitDevicesReConnected(WebportalParam.ap1serialNo);
//        //System.out.println(filePath);
//        System.out.println("***********************************");
//        DeviceBulkOnboardingPage.RemoveInfoOfCsv(fileName);
//        System.out.println("***********************************");
//        if (Result1 == true) {
//            micResult =  true;
//            assertTrue(micResult,"Pass:Connect Device successfully !");  
//        }else {
//            micResult = false;
//            assertTrue(micResult,"Failed:Connect Device unsuccessfully!"); 
//        }
        
  
}

