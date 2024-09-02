package webportal.ICP.user.PRJCBUGEN_T14981;

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
import webportal.param.CommonDataType;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    Random              r                        = new Random();
    int                 num                      = r.nextInt(10000000);
//    String              mailname                 = "";
 //   Random              r           = new Random();
 //   int                 num         = r.nextInt(10000000);
    String              mailname    = "apwptest" + String.valueOf(num);
    String              account     = mailname + "@mailinator.com";
    String              password    = "Netgear#123";
    Map<String, String> CaptivePortalPaymentInfo = new CommonDataType().PAYMENT_INFO;

    @Feature("InstantCaptivePortal.IcpUnderAccount.BasicOrPremiumUser") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14981") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that user can buy multiple subscriptions of different durations") // It's a test case title from Jira TestCase.
    @TmsLink("PRJCBUGEN-T14981") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new AccountPage().deleteOneLocation("OnBoardingTest");
        System.out.println("start to do tearDown");

    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);

        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T14976");
        accountInfo.put("Email Address", account);
        accountInfo.put("Confirm Email", account);
        accountInfo.put("Password", password);
        accountInfo.put("Confirm Password", password);
        accountInfo.put("Country", "United States");

        new HamburgerMenuPage(false).createAccount(accountInfo);
        
        
    }

    
    @Step("Test Step 2: Create the Location;")
    public void step2() {
        
        new HamburgerMenuPage(false).closeLockedDialog();
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "OnBoardingTest");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "4560");
        locationInfo.put("Country", "United States of America");
        new AccountPage().addNetwork(locationInfo);
        new AccountPage(false).enterLocation(WebportalParam.location1);
    }

    
    @Step("Test Step 3: Add device To the Network;")
    public void step3() {
        
        new AccountPage().enterLocation("OnBoardingTest");
        
        Map<String, String> firststdevInfo = new HashMap<String, String>();

        
        firststdevInfo.put("Serial Number1", WebportalParam.ap5serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ap5macaddress);
        
        System.out.println(firststdevInfo);

                
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);

      
    }
    @Step("Test Step 4: Check ICP credits;")
    public void step4() {
        mailname = new HamburgerMenuPage(false).getRandomWord() + String.valueOf(num);
        CaptivePortalPaymentInfo.put("First Name", mailname);
        CaptivePortalPaymentInfo.put("Last Name", "T14981");
        CaptivePortalPaymentInfo.put("Email", mailname + "@mailinator.com");
        CaptivePortalPaymentInfo.put("Quantity", "3"); // can input 1 , 3 , 10 , 40
        CaptivePortalPaymentInfo.put("Duration", "1"); // can input 1 , 3
        new InsightServicesPage().buyCaptivePortalProducts(CaptivePortalPaymentInfo);
        CaptivePortalPaymentInfo.put("Quantity", "10"); // can input 1 , 3 , 10 , 40
        CaptivePortalPaymentInfo.put("Duration", "3"); // can input 1 , 3
        new InsightServicesPage().buyCaptivePortalProducts(CaptivePortalPaymentInfo);

        new HamburgerMenuPage().gotoIcpServicesPage();
        assertTrue(new HamburgerMenuPage().getIcpCreditsNum().equals("13"), "Captive portal services credits is incorrect.");
    }

}
