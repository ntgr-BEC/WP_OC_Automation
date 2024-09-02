package webportal.CFServiceSubscription.PRJCBUGEN_T27176;

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
import util.APUtils;
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.ContentFilteringElement;
import webportal.weboperation.AccountPage;
import webportal.weboperation.ContentFilteringPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.GlobalNotificationPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    Random r        = new Random();
    int    num      = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num);
    Map<String, String> paymentInfo = new HashMap<String, String>();

    @Feature("CFServiceSubscription") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T27176") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Buying  CF Service Subscription for Switzerland country") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T27176") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
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

        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T10218");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "Switzerland");

        new HamburgerMenuPage(false).createAccount(accountInfo);
    }

    @Step("Test Step 2: add Network;")
    public void step2() {
        new HamburgerMenuPage(false).closeLockedDialog();
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "OnBoardingTest");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "8700");
        locationInfo.put("Country", "Switzerland");
        new AccountPage().addNetwork(locationInfo);
        
        new AccountPage(false).enterLocation(WebportalParam.location1);
    }
    
    @Step("Test Step 3: Buy CF Service Subscription;")
    public void step3() {
        
        Map<String, String> paymentInfo = new HashMap<String, String>();
        paymentInfo.put("Subscription Type", "Content Filtering Service Subscription");
        paymentInfo.put("Quantity", "2");
        paymentInfo.put("YearNum", "1");
        paymentInfo.put("First Name", mailname);
        paymentInfo.put("Last Name", "T17564");
        paymentInfo.put("Email", mailname + "@mailinator.com");
        paymentInfo.put("Street Address", "Zuerich");
        paymentInfo.put("City", "Zuerich");
        paymentInfo.put("Zip", "8700");
        paymentInfo.put("Country", "Switzerland");
        paymentInfo.put("State", "Kuesnacht");
        paymentInfo.put("Card Number", "5193911111111112");
        paymentInfo.put("CVV Number", "123");
        paymentInfo.put("Expiration Month", "May");
        paymentInfo.put("Expiration Year", "2030");
        new InsightServicesPage(false).buyCFProducts(paymentInfo);
        assertTrue(
                new ContentFilteringPage().checkBuyCFResult(paymentInfo.get("YearNum"), paymentInfo.get("Quantity")),
                "Vpn services page display incorrect.");
        assertTrue(new ContentFilteringPage().checkCFServicesSubscription(paymentInfo.get("Quantity"), paymentInfo.get("Quantity")),"CF page is not right");
        
    
    }
    
    
}
