package webportal.HardBundle.PRJCBUGEN_T21468;

import static com.codeborne.selenide.Selenide.$;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selectors;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.HardBundlePage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;


/**
 *
 * @author Tejeshwini
 *
 */
public class Testcase extends TestCaseBase {
    
    String                  organizationName = "OnBoardingTest";
    String                  locationName     = "OnBoardingTest";
    String                  ActivationDatePRO;
    String                  ExpiryDatePRO;
    String                  ActivationDatePremium;
    String                  ExpiryDatePremium;   
    

    @Feature("Hardbundle") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T21468") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("[Pro Account] Test to verify does the device continue to be a hard bundle device if removed from one account and added to another account.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T21468") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new WirelessQuickViewPage().deleteDeviceYes(WebportalParam.getDeviceSerialNoCSV("ap1"));
    }
    
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        
    }

    @Step("Test Step 2: Create Organization and Location ")
    public void step2() {
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName);

        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrganization(organizationInfo);
        OrganizationPage.openOrg(organizationName);
        HashMap<String, String> locationInfo     = new HashMap<String, String>();
        locationInfo.put("Location Name", locationName);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "China");
        new AccountPage(false).addNetwork(locationInfo);
        
        new HamburgerMenuPage().configCreditAllocation(organizationName, 2, 0, 0);
//        assertTrue(new HardBundlePage().checkCreditsAllocationSuccessMsg(), "Allocate credits error.");
        
    }
    
    @Step("Test Step 3:  Add hardbundle device and check whether it allows to add without credit allocation")
    public void step3() {
        
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.openOrg(organizationName);
        new AccountPage(false).enterLocation(locationName);
        
        
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        
        firststdevInfo.put("Serial Number1", WebportalParam.getDeviceSerialNoCSV("ap1"));
        firststdevInfo.put("MAC Address1", WebportalParam.getDeviceMacCSV("ap1"));
        
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
       
        assertTrue(new HardBundlePage().checkCreditsUsed(organizationName), "Device credit allocated for hardbundlle device");
        
        new  HardBundlePage().gotoOneYearInsightIncludedwithHardwarePRO();
        
        ActivationDatePRO = new HamburgerMenuPage().getActivationDate( WebportalParam.getDeviceSerialNoCSV("ap1"));
        
        System.out.println(ActivationDatePRO);
     
        ExpiryDatePRO = new HamburgerMenuPage().getExpiryDate( WebportalParam.getDeviceSerialNoCSV("ap1"));
        
        System.out.println(ExpiryDatePRO);
    }
    
    @Step("Test Step 4:  Delete Hardbundle device from Pro account and add it it to Premium account")
    public void step4() {
        
        new OrganizationPage().deleteOrganizationNew(organizationName);
        
        String sCheck = "[alt=\"User Image\"]";
        System.out.println("try to do logout");
        $(sCheck).click();
        $(Selectors.byCssSelector(".open ul li:last-child a")).click();
        System.out.println("user is logout");
        MyCommonAPIs.waitReady();
        
        
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        new AccountPage().enterLocation(WebportalParam.location1);
        
        Map<String, String> firststdevInfo = new HashMap<String, String>();
      
         firststdevInfo.put("Serial Number1", WebportalParam.getDeviceSerialNoCSV("ap1"));
         firststdevInfo.put("MAC Address1", WebportalParam.getDeviceMacCSV("ap1"));
      
         new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
     
    }
    
    @Step("Test Step 5: Compare Activation and Expiry date on both the account ")
    public void step5() {
        
         new  HardBundlePage().gotoOneYearInsightIncludedwithHardwarePRO();
        
        ActivationDatePremium = new HamburgerMenuPage().getActivationDate( WebportalParam.getDeviceSerialNoCSV("ap1"));
        
        System.out.println(ActivationDatePremium);
     
        ExpiryDatePremium = new HamburgerMenuPage().getExpiryDate( WebportalParam.getDeviceSerialNoCSV("ap1"));
        
        System.out.println(ExpiryDatePremium);
        
        assertTrue(new HardBundlePage().comparebothdates(ActivationDatePremium, ActivationDatePRO, ExpiryDatePremium, ExpiryDatePRO), "Activation and expiry date are not same");
       
           
    }
    
    
}
