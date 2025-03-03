package webportal.ProUseCase.PRJCBUGEN_T33498;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
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
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HardBundlePage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;

/**
 *
 * @author Vivek
 *
 */
public class Testcase extends TestCaseBase {
    String AP1 =new DevicesDashPage(false).GenaraterandomSerial ("4XT");
    String AP2 =new DevicesDashPage(false).GenaraterandomSerial ("4W8");
    String MAC = "aa:bb:cc:dd:ee:ff";
    Random r                = new Random();
    int    num              = r.nextInt(10000);
    String mailname         = "case3" + String.valueOf(num);
    String organizationName = "Netgear";
    String mailid           = mailname + "@yopmail.com";
    String password         = "Netgear1@";

    @Feature("ProUseCase") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T33498") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify premium account to pro account migration with HBB device") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T33498") // It's a testcase id/link from Jira Test Case.

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
    @Step("Test Step 1: Create IM WP account success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        // webportalLoginPage.defaultLogin();
        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T33498");
        accountInfo.put("Email Address", mailid);
        accountInfo.put("Confirm Email", mailid);
        accountInfo.put("Password", "Netgear1@");
        accountInfo.put("Confirm Password", "Netgear1@");
        accountInfo.put("Country", "United States");
        new HamburgerMenuPage(false).createAccount(accountInfo);
        logger.info(accountInfo.get(mailid));

    }

     @Step("Test Step 2: Create a new location;")
     public void step2() {
     HashMap<String, String> locationInfo = new HashMap<String, String>();
     locationInfo.put("Location Name", WebportalParam.location1);
     locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
     locationInfo.put("Zip Code", "32003");
     locationInfo.put("Country", "United States of America");
     new AccountPage().addNetwork(locationInfo);
     }

    @Step("Test Step 3: Onboarding a HBB dummy device and logout")
    public void step3() {
         Map<String, String> firststdevInfo = new HashMap<String, String>();
         firststdevInfo.put("Serial Number1", AP1);
         firststdevInfo.put("MAC Address1", MAC);
         new AccountPage(false).enterLocation(WebportalParam.location1);
         new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
//         assertTrue(new DevicesDashPage(false).verifyDeviceIsOnboarded(WebportalParam.ap1serialNo), "Device is not Onboarded");
            
    }


    @Step("Test Step 4: clicking premium to pro Subscription and fill the business form")
    public void step4() {

        new HamburgerMenuPage().openAccountMgmt();
        new AccountPage(false).clickingUpgradeToProSubscription();
        Map<String, String> businessInfo = new HashMap<String, String>();
        businessInfo.put("Business Name", "Netgear");
        businessInfo.put("Primary Address of Business", "Spring Road");
        businessInfo.put("City", "RedBank");
        businessInfo.put("State", "Florida");
        businessInfo.put("Zip Code", "32003");
        businessInfo.put("Country", "United States of America");
        businessInfo.put("Business Phone Number", "0000000000");
        new AccountPage(false).netgearUpgradetoProPage(businessInfo);
        

    }
    
    @Step("Test Step 5:Selecting the exesting location and create a new org")
    public void step5() {
        new HamburgerMenuPage(false).selectLocationAndSetNewOrg();
        new HamburgerMenuPage(false).setNewOrg(organizationName);
    
    }
    
    
     @Step("Test Step 6: Adding PRO LMS Key in migrated account")
     public void step6() {
     new HamburgerMenuPage().openAccountMgmt();
     new HamburgerMenuPage(false).EnterlmsKey();
    
     }
    
    @Step("Test Step 7: Allocate Credits ;")
     public void step7() {
     new HamburgerMenuPage().openAccountMgmt();
     new HamburgerMenuPage(false).OpenCreditAllocationPage();
     new HamburgerMenuPage(false).clickCreditsPlus(1, 0, 0);
     new HamburgerMenuPage(false).clickOnAllocateButtonforsave();
     
    }
    
  @Step("Test Step 8: Onboarding a dummy device;")
  public void step8() {
      Map<String, String> firststdevInfo = new HashMap<String, String>();
      firststdevInfo.put("Serial Number1", AP2);
      firststdevInfo.put("MAC Address1", MAC);  
      new DevicesDashPage(false).addNewdummyDeviceProAccount(firststdevInfo);
  }

}
