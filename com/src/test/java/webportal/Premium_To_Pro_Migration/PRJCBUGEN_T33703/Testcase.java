package webportal.Premium_To_Pro_Migration.PRJCBUGEN_T33703;

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
import webportal.param.CommonDataType;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import com.codeborne.selenide.Selenide;

/**
 *
 * @Pratik
 *
 */
public class Testcase extends TestCaseBase {
    
    Random r = new Random();
    int num = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num);
    String organizationName = "PRJCBUGEN_T33703";

    @Feature("Premium_To_Pro_Migration") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T33703") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, add instant VPN service and migrate premium to pro in same month then current date and end date should same.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T33703") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (new OrganizationPage().checkOrganizationIsExist(organizationName)){
            new OrganizationPage().deleteOrganizationNew(organizationName);
            System.out.println("start to do tearDown");
        } else {
            new AccountPage().deleteOneLocation("OnBoardingTest");
            System.out.println("start to do tearDown");
        }
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create IM WP account success;")
    public void step1() {
        
        Selenide.clearBrowserCookies();
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        
        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T18721");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "United States");


        new HamburgerMenuPage(false).createAccount(accountInfo);
    }
    
    @Step("Test Step 2: check buy vpn services;")
    public void step2() {
        Map<String, String> paymentInfo = new HashMap<String, String>();
        paymentInfo = new CommonDataType().CARD_INFO;
        
        paymentInfo.put("First Name", mailname);
        paymentInfo.put("Last Name", "T10218");
        paymentInfo.put("Email", WebportalParam.loginName);
        paymentInfo.put("YearNum", "1");
        paymentInfo.put("BuyNum", "2");
        paymentInfo.put("First Name", mailname);
        paymentInfo.put("Last Name", "T17564");
        paymentInfo.put("Email", mailname + "@mailinator.com");
        paymentInfo.put("Street Address", "Springs Rd");
        paymentInfo.put("City", "Red Bank");
        paymentInfo.put("Zip", "32003");
        paymentInfo.put("Country", "US");
        paymentInfo.put("State", "Florida");
        new InsightServicesPage().buyVpnProducts(paymentInfo);
        assertTrue(
                new HamburgerMenuPage().checkBuyVpnResult(paymentInfo.get("YearNum"), paymentInfo.get("BuyNum"))
                        && new HamburgerMenuPage().checkVpnServicesSubscription(paymentInfo.get("BuyNum"), paymentInfo.get("BuyNum")),
                "Vpn services page display incorrect.");
    }
    
    @Step("Test Step 3: Check start date and end date of vpn subscription and upgrade to pro;")
    public void step3() {
        
        new MyCommonAPIs().open(URLParam.hrefVPNServices, true);
        new HamburgerMenuPage(false).premiumVPNServicesStartDateEndDate();
        
    }

    @Step("Test Step 4: Navigate to Account Management, check upgrade to pro option and click on it;")
    public void step4() {
        System.out.println("starting with setup 2");
        assertTrue(new HamburgerMenuPage().insightPritoinsightPro(),"Failed navigate to Account Management");
        
    }
    
    @Step("Test Step 5: Check upgraded to pro")
    public void step5() {
        
        Map<String, String> businessInfo = new HashMap<String, String>();
        businessInfo.put("Licence Key", new HamburgerMenuPage(false).readLicenceKeyByTxt("Write"));
        businessInfo.put("Business Name", "Netgear");
        businessInfo.put("Primary Address of Business", "test 1st");
        businessInfo.put("City", "NewYork");
        businessInfo.put("State", "test");
        businessInfo.put("Zip Code", "12345");
        businessInfo.put("Country", "United States of America");
        businessInfo.put("Business Phone Number", "1234567890");
        new HamburgerMenuPage(false).inputLicenceAndFinishSignin(businessInfo);
        assertTrue(new HamburgerMenuPage(false).checkLoginSuccessful(), "Create pro account unsuccess.");
        
    }
    
    @Step("Test Step 6: create new organization and verify vpn start date and end date in pro account with premium")
    public void step6() {
        
        new OrganizationPage(false).gotoDashboard();
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName);

        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrganization(organizationInfo);
        
        new MyCommonAPIs().open(URLParam.hrefVPNServices, true);
        assertTrue(new HamburgerMenuPage(false).proVPNServicesStartDateEndDate(), "Both Dates are not matching");
        
    }
    
}
