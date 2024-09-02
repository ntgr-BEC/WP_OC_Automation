package webportal.VPNMigration.PRJCBUGEN_T27057;

import static com.codeborne.selenide.Selenide.$;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selectors;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.HardBundlePage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini
 *
 */
public class Testcase extends TestCaseBase {
    
    String                  locationName     = "OnBoardingTest";
    Map<String, String> paymentInfo = new HashMap<String, String>();
    
    Random r = new Random();
    int num = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num);
    String Email = mailname + "@mailinator.com";
    Map<String, String> CaptivePortalPaymentInfo = new HashMap<String, String>();
    Map<String, String> ssidInfo                 = new HashMap<String, String>();
    

    @Feature("VPN_Migration") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T27057") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("converting premiun to pro account funcationally") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T27057") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new OrganizationPage().deleteOrganizationNew(WebportalParam.Organizations);
    }
    
    @Step("Test Step 1: Create IM WP account success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
//        webportalLoginPage.loginByUserPassword("apwptest9363362@mailinator.com","Netgear#123");
        
        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T27057");
        accountInfo.put("Email Address", Email );
        accountInfo.put("Confirm Email", Email);
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "Australia");
        new HamburgerMenuPage(false).createAccount(accountInfo);
    }
    
    
    @Step("Test Step 2: change premium Trail to Premium Yearly;")
    public void step2() {
        
        Map<String, String> paymentInfo = new HashMap<String, String>();
        paymentInfo.put("Subscription Time", "Yearly");
        paymentInfo.put("Number of Device Credits", "1");
        paymentInfo.put("First Name", mailname);
        paymentInfo.put("Last Name", "T27057");
        paymentInfo.put("Email", mailname + "@mailinator.com");
        paymentInfo.put("Street Address", "Main Street");
        paymentInfo.put("City", "Montville");
        paymentInfo.put("Zip", "4560");
        paymentInfo.put("Country", "Australia");
        paymentInfo.put("State", "Queensland");
        paymentInfo.put("Card Number", "5193911111111112");
        paymentInfo.put("CVV Number", "123");
        paymentInfo.put("Expiration Month", "May");
        paymentInfo.put("Expiration Year", "2030");
        new HamburgerMenuPage(false).upgradeSubscription(paymentInfo);
        assertTrue(new HamburgerMenuPage().checkSubscriptionScreen(String.valueOf(Integer.valueOf(paymentInfo.get("Number of Device Credits")) + 0)), "Amount is incorrect.");
    }
            
         
    
    @Step("Test Step 3: Create Location ")
    public void step3() {
             
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", locationName);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "4560");
        locationInfo.put("Country", "Australia");
        new AccountPage().addNetwork(locationInfo);
        
    }
      
        
        @Step("Test Step 5: Purchase VPN;")
        public void step5() {
            new HamburgerMenuPage(false).closeLockedDialog();
            HashMap<String, String> locationInfo = new HashMap<String, String>();
            locationInfo.put("Location Name", "OnBoardingTest");
            locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
            locationInfo.put("Zip Code", "4560");
            locationInfo.put("Country", "Australia");
            new AccountPage().addNetwork(locationInfo);
            new AccountPage(false).enterLocation(WebportalParam.location1);
            
            paymentInfo.put("First Name", mailname);
            paymentInfo.put("Last Name", "T10218");
            paymentInfo.put("Email", WebportalParam.loginName);
            paymentInfo.put("YearNum", "1");
            paymentInfo.put("BuyNum", "1");
            paymentInfo.put("Subscription Time", "Monthly");
            paymentInfo.put("Number of Device Credits", "3");
            paymentInfo.put("First Name", mailname);
            paymentInfo.put("Last Name", "T17512");
            paymentInfo.put("Email", mailname + "@mailinator.com");
            paymentInfo.put("Street Address", "Main Street");
            paymentInfo.put("City", "Montville");
            paymentInfo.put("Zip", "4560");
            paymentInfo.put("Country", "Australia");
            paymentInfo.put("State", "Queensland");
            paymentInfo.put("Card Number", "5193911111111112");
            paymentInfo.put("CVV Number", "123");
            paymentInfo.put("Expiration Month", "May");
            paymentInfo.put("Expiration Year", "2030");
            new InsightServicesPage().buyVpnProducts(paymentInfo);
            assertTrue(
                    new HamburgerMenuPage().checkBuyVpnResult(paymentInfo.get("YearNum"), paymentInfo.get("BuyNum"))
                            && new HamburgerMenuPage().checkVpnServicesSubscription(paymentInfo.get("BuyNum"), paymentInfo.get("BuyNum")),
                    "Vpn services page display incorrect.");

    }
    
    @Step("Test Step 6: logout and migrates to a PRO account.")
    public void step6() {
        
           String sCheck = "[alt=\"User Image\"]";
            System.out.println("try to do logout");
            $(sCheck).click();
            $(Selectors.byCssSelector(".open ul li:last-child a")).click();
            System.out.println("user is logout");
            MyCommonAPIs.waitReady();
            
            new HardBundlePage().checkCreateProAccountPage("checkNext:" + Email);
       

            
            Map<String, String> businessInfo = new HashMap<String, String>();
            businessInfo.put("Licence Key", new HamburgerMenuPage(false).readLicenceKeyByTxt("Write"));
            businessInfo.put("Business Name", "Netgear");
            businessInfo.put("Primary Address of Business", "test 1st");
            businessInfo.put("City", "Montville");
            businessInfo.put("State", "Queensland");
            businessInfo.put("Zip Code", "4560");
            businessInfo.put("Country", "Australia");
            businessInfo.put("Business Phone Number", "1234567890");
            new HamburgerMenuPage(false).inputLicenceAndFinishSignin(businessInfo);

            assertTrue(new HardBundlePage().checkLoginSuccessful(), "Create pro account unsuccess.");
            
            assertTrue(
                    new HamburgerMenuPage().checkBuyVpnResult(paymentInfo.get("YearNum"), paymentInfo.get("BuyNum")),
                    "Vpn services page display incorrect.");
        
    }
    
  
    
}
