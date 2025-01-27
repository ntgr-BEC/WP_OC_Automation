package webportal.ProAccountCreation.DirectSubscriptionCreateAndPurchaseLink.PRI_QA.PRJCBUGEN_T45684;

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
 * @author Pratik
 *
 */
public class Testcase extends TestCaseBase {

    Map<String, String> ssidInfo                 = new HashMap<String, String>();
    Random              r           = new Random();
    int                 num         = r.nextInt(10000000);
    String              mailname    = "apwptest" + String.valueOf(num);
    String              url         = "https://accounts2-stg.netgear.com/login?redirectUrl=https:%2F%2Fbilling-stg.netgear.com%2Finsight-pro&GotoDashboard=https://pri-qa.insight.netgear.com&category_id=18&clientId=les2m0e41u7nr1asu03uk2iot";


    @Feature("ProAccountCreation") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T45684") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that the Insight pro account can be created using the direct premium purchase link.") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T45684") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {

        new HamburgerMenuPage(false).launchdirectpurchaseQAServerLink(url);

        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T24091");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "United States");

        assertTrue(new HamburgerMenuPage(false).createAccountInsightPro(accountInfo),"Through marketing page not able create new premium account");
    }
    
    @Step("Test Step 2: Fill the business info")
    public void step2() {
        
        Map<String, String> businessInfo = new HashMap<String, String>();
        businessInfo.put("Licence Key", new HamburgerMenuPage(false).readLicenceKeyByTxt("Write"));
        businessInfo.put("Business Name", "Netgear");
        businessInfo.put("Primary Address of Business", "Main Street");
        businessInfo.put("City", "Montville");
        businessInfo.put("State", "Queensland");
        businessInfo.put("Zip Code", "4560");
        businessInfo.put("Country", "Australia");
        businessInfo.put("Business Phone Number", "1234567890");
        new HamburgerMenuPage(false).inputbusinessInfoforProAccthroghServicesPageLink(businessInfo);

        assertTrue(new HamburgerMenuPage(false).checkLoginSuccessful(), "Create pro account unsuccess.");
        
    }

}
