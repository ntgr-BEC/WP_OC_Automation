package webportal.CFD.CFD_7_6.Changemail_MonthlyPlan.PRJCBUGEN_T45773;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.PostManPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author RaviShankar
 *
 */
public class Testcase extends TestCaseBase {

    Map<String, String> ssidInfo                 = new HashMap<String, String>();
    Random              r           = new Random();
    int                 num         = r.nextInt(10000000);
    String              mailname    = "apwptest" + String.valueOf(num);
    String mailnew  =  mailname+"new";
    Map<String, String> paymentInfo = new HashMap<String, String>();

    @Feature("Change-email_Post_Purchase") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T457773") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Buy monthly subscription ,the change mail id_UK") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T45773") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void tearUp() {
       
       new PostManPage().Deregister(WebportalParam.ap1serialNo);
        
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new AccountPage().deleteOneLocation("office1");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create an account;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);

        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T23809");
        accountInfo.put("Email Address", mailname + "@yopmail.com");
        accountInfo.put("Confirm Email", mailname + "@yopmail.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "UK");

        new HamburgerMenuPage(false).createAccount(accountInfo);
    }

    @Step("Test Step 2: Check captive portal services credits is correct;")
    public void step2() {
        

        new HamburgerMenuPage(false).closeLockedDialog();
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "office1");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "4560");
        locationInfo.put("Country", "Australia");
        new AccountPage().addNetwork(locationInfo);       
    }
    
    
    @Step("Test Step 3: Add device To the Network;")
    public void step3() {
        
        new AccountPage(false).enterLocation(WebportalParam.location1);
        
        new AccountPage().enterLocation("office1");
        
        Map<String, String> firststdevInfo = new HashMap<String, String>();
       
        
        firststdevInfo.put("Serial Number1", WebportalParam.ap1serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ap1macaddress);
        
        System.out.println(firststdevInfo);
 
                
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
        
    }

    @Step("Test Step 4: Buy Monthly Subscription")
    public void step4() {
                
        new MyCommonAPIs().open(URLParam.hrefPaymentSubscription, true);
 
        paymentInfo = new CommonDataType().CARD_INFO;
        paymentInfo.put("Subscription Time", "Monthly");
        paymentInfo.put("Number of Device Credits", "2");
        paymentInfo.put("First Name", mailname);
        paymentInfo.put("Last Name", "T17512");
        paymentInfo.put("Email", mailname + "@yopmail.com");
        paymentInfo.put("Street Address", "Saltash");
        paymentInfo.put("City", "England");
        paymentInfo.put("Zip", "PL12");
        paymentInfo.put("Country", "UK");
        paymentInfo.put("State", "England");
        new HamburgerMenuPage(false).upgradeSubscription(paymentInfo);
        assertTrue(new HamburgerMenuPage(false).checkMonthlySubscriptionScreen(String.valueOf(Integer.valueOf(paymentInfo.get("Number of Device Credits")))), "Amount is incorrect.");

    }
     
    @Step("Test Step 5: Change login email account, check the user should be able to login successfully;")
    public void step5() {
        new HamburgerMenuPage().changeEmail(mailnew+"@yopmail.com", mailnew+"@yopmail.com", WebportalParam.loginPassword);
        Selenide.refresh();
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(mailnew+"@yopmail.com", WebportalParam.loginPassword);
        

        assertTrue(new HamburgerMenuPage(false).checkLoginSuccessful());
    }
    
    @Step("Test Step 6: Buy DeviceCredits again")
    public void step6() {
                
        new MyCommonAPIs().open(URLParam.hrefPaymentSubscription, true);

        paymentInfo.put("Subscription Time", "Yearly");
        paymentInfo.put("Number of Device Credits", "6");
        paymentInfo.put("First Name", mailnew);
        paymentInfo.put("Email", mailnew + "@yopmail.com");
       
        new HamburgerMenuPage().buyDeviceCredits(paymentInfo);
        assertTrue(new HamburgerMenuPage(false).checkMonthlySubscriptionScreen(
                String.valueOf(Integer.valueOf(paymentInfo.get("Number of Device Credits")) + 2)), "Buy basic subscription unsuccessful.");
        assertTrue(new HamburgerMenuPage(false).subscriptionEmail.getText().contains(mailname),"CFD ISSUE Wrong mail id");
        
    }
    
    
    
}
