package webportal.AdvancedPurchaseHistory.DontRunICPVPN.PRJCBUGEN_T36491;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
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
import webportal.weboperation.PostManPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Vivek Singh
 *
 */
public class Testcase extends TestCaseBase {

    Map<String, String> ssidInfo     = new HashMap<String, String>();
    Random              r            = new Random();
    int                 num          = r.nextInt(10000000);
    String              mailname     = "purhtry" + String.valueOf(num);
    String              locationName = "PurchaseHistoryLoc";

    @Feature("IM-7.2 AdvancedPurchaseHistory") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T36491") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify Purchase History All Details after purchase the ICP Three AP for Three Year") // It's a test case title from Jira
                                                                                                               // Test Case.
    @TmsLink("PRJCBUGEN-T36491") // It's a testcase id/link from Jira Test Case.

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
        new AccountPage().deleteOneLocation(locationName);
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);

        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T36491");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear1@");
        accountInfo.put("Confirm Password", "Netgear1@");
        accountInfo.put("Country", "United States");

        new HamburgerMenuPage(false).createAccount(accountInfo);
    }

    @Step("Test Step 2: Create 1 location")
    public void step2() {

        new HamburgerMenuPage(false).closeLockedDialog();
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", locationName);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "32003");
        locationInfo.put("Country", "United States of America");
        new AccountPage().addNetwork(locationInfo);

    }
    
    @Step("Test Step 3: Add device To the Network;")
    public void step3() {
        
        new AccountPage(false).enterLocation(WebportalParam.location1);
        
         new AccountPage().enterLocation("OnBoardingTest");
        
        Map<String, String> firststdevInfo = new HashMap<String, String>();
       
        
        firststdevInfo.put("Serial Number1", WebportalParam.ap1serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ap1macaddress);
        
        System.out.println(firststdevInfo);
 
                
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
        
    }


    @Step("Test Step 4: Buy 3 AP Device for 1 Years;")
    public void step4() {
        Map<String, String> CaptivePortalPaymentInfo = new HashMap<String, String>();
        CaptivePortalPaymentInfo = new CommonDataType().CARD_INFO;
        CaptivePortalPaymentInfo.put("First Name", mailname);
        CaptivePortalPaymentInfo.put("Last Name", "T23919");
        CaptivePortalPaymentInfo.put("Email", mailname + "@mailinator.com");
        CaptivePortalPaymentInfo.put("Quantity", "3");
        CaptivePortalPaymentInfo.put("Duration", "3");
        CaptivePortalPaymentInfo.put("Street Address", "Springs Rd");
        CaptivePortalPaymentInfo.put("City", "Red Bank");
        CaptivePortalPaymentInfo.put("Zip", "32003");
        CaptivePortalPaymentInfo.put("Country", "US");
        CaptivePortalPaymentInfo.put("State", "Florida");
        new InsightServicesPage(false).buyCaptivePortalProducts(CaptivePortalPaymentInfo);
        boolean result = false;
        result = new HamburgerMenuPage().checkCaptivePortalServicesCredits();
        assertTrue(result, "<2> Captive portal services credits is incorrect.");
    }

    @Step("Test Step 6: Open Purchase History Page and expand insigt HBB div Credits Section")
    public void step6() {
        new HamburgerMenuPage().openAccountMgmt();
        new HamburgerMenuPage(false).expandICPCreditsSection();

    }

    @Step("Test Step 7: Verify ICP 3 AP, 3 Year subscription Text are present there")
    public void step7() {
        assertTrue(new HamburgerMenuPage(false).VerifyTheICPforThreeAPThreeeYearSubsText(), "ICP 3 year 3 AP subs Texts are not present");

    }

    @Step("Test Step8: Verify The Credit Count should be one")
    public void step8() {
        assertTrue(new HamburgerMenuPage(false).VerifyICPforThreeYearCreditQuantity(), "Credit Count is not correct");

    }

    @Step("Test Step9: Verify the Activation date and Expiry date should be according to testcase")
    public void step9() {
        assertTrue(new HamburgerMenuPage(false).VerifyICPActivationAndExpiryDats(), "Activation Date is not equal to Today's Date");

    }
}
