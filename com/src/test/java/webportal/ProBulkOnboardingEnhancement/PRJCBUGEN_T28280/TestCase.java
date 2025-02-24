package webportal.ProBulkOnboardingEnhancement.PRJCBUGEN_T28280;
import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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


public class TestCase extends TestCaseBase {
    
    String organizationName = "PRJCBUGEN_T28280";
    String locationName     = "BulkOnboarding";
    int NoofRows = 0;
    int count = 0;
    
    @Feature("ProBulkOnboardingEnhancement") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T28280") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, multiple device on-boarding can be done from org level") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T28280") // It's a testcase id/link from Jira Test Case.
    
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
    
    @Step("Test Step 2: Create a organization, location and do the bulk onboarding")
    public void step2() throws IOException {
        
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

        new AccountPage(false).addMultipleDevices();
        
        DeviceBulkOnboardingPage DeviceBulkOnboardingPage = new DeviceBulkOnboardingPage(); 
        String filePath = DeviceBulkOnboardingPage.GetcurrentPath1();
        filePath = filePath + "\\PRJCBUGEN_T28279\\Bulkonbord.csv";
        System.out.println(filePath);
        System.out.println("check where the screen is");
        DeviceBulkOnboardingPage.ImportmultipledevicesformatFile1(filePath);
        assertTrue(new DevicesDashPage(false).checkNumberOfDevicesOrganization1().equals("Twelvedevice"), "More device exits");
        FileReader fileReader = null;
        BufferedReader reader = null;
        try
        {
            fileReader = new FileReader(filePath);
            reader = new BufferedReader(fileReader);
            String line;
            while((line = reader.readLine()) != null)
            {
                count++;
            }
        }
        finally
        {
            try{reader.close();}catch(Exception e){}
            try{fileReader.close();}catch(Exception ee){}
        }
        
        System.out.println("Count : "+count);
        NoofRows = count - 1;
        int temp = new DevicesDashPage(false).checkNumberOfDevicesOrganization11();
        assertTrue((NoofRows==temp), "Devices count is not matched with CSV file.");
        
    }
    
    
    
}
