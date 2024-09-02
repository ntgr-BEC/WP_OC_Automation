package webportal.MultipackDiffrentFlow.Multipack5Device1Year.PRJCBUGEN_T25547;

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
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    Random              r           = new Random();
    int                 num         = r.nextInt(10000000);
    String              mailname    = "apwptest" + String.valueOf(num);
    Map<String, String> paymentInfo = new HashMap<String, String>();
   

    @Feature("MultipackDiffrentFlow Multipack 5Device 1Year") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25547") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Muktipack 5Device 1Year Australia") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25547") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create IM WP account success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
//        webportalLoginPage.loginByUserPassword("apwptest6104367@mailinator.com","Netgear#123");
        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T10218");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "Australia");

        new HamburgerMenuPage(false).createAccount(accountInfo);        
         
    }

    @Step("Test Step 2: check and buy Multipack;")
    public void step2() {
        new HamburgerMenuPage(false).closeLockedDialog();
        
        paymentInfo.put("First Name", "New");
        paymentInfo.put("Last Name", "New");
        paymentInfo.put("Email", mailname + "@mailinator.com");
        paymentInfo.put("Device Credits Pack", "5");
        paymentInfo.put("Buy Year", "1");
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
        new InsightServicesPage().buyInsightPremiumSubscriptions(paymentInfo);
        new HamburgerMenuPage().gotoInsightPremiumSubscriptions();
        
        assertTrue(new HamburgerMenuPage(false).checkSubscriptionsPage("Insight Premium", paymentInfo.get("Device Credits Pack")),
                "Subscriptions page display error.");
    }
    
    @Step("Test Step 3: Check cancel devices credits;")
    public void step3() {
        new HamburgerMenuPage().cancelDeviceCredits();

        assertTrue(!new HamburgerMenuPage(false).checkDeviceCredits(paymentInfo.get("Device Credits Pack")));
    }


}
