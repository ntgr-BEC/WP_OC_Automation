package webportal.SubscriptionWith100percentPromoCode.PRJCBUGEN_T27166;

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
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.WebportalLoginPage;
public class TestCase extends TestCaseBase {
    String organizationName = "PRJCBUGEN_T21509";
    Random r        = new Random();
    int    num      = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num);
    boolean micResult = false;
    @Feature("PromoCode") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T27166") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify Validation of Zip code when on buying device") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T27166") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    
    public void test() throws Exception {
        runTest(this);
    }

 
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    
    @Step("Test Step 1:  Create an account;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T21509");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "United States");
        new HamburgerMenuPage(false).createAccount(accountInfo);
        
        WebportalLoginPage webportalLoginPage1 = new WebportalLoginPage(true);
        Map<String, String> paymentInfo = new HashMap<String, String>();
        paymentInfo.put("Subscription Time", "Yearly");
        paymentInfo.put("Number of Device Credits", "1");
        paymentInfo.put("First Name", mailname);
        paymentInfo.put("Last Name", "T21509");
        paymentInfo.put("Street Address", "Street 4568 James Avenue");
        paymentInfo.put("City", "INVERNESS");
        paymentInfo.put("Zip", "453367-45677777");
        paymentInfo.put("Country", "US");
        paymentInfo.put("State", "Florida");
        paymentInfo.put("Card Number", "4112344112344113");
        paymentInfo.put("CVV Number", "123");
        paymentInfo.put("Expiration Month", "May");
        paymentInfo.put("Expiration Year", "2030");
        paymentInfo.put("Promo Code", WebportalParam.promocode1);
        assertTrue(
                new HamburgerMenuPage(false).CheckforZipCodeValidation(paymentInfo),
                "Zipcode Validation is UnScuuessful Observed some issue");
    }
        
    @Step("Test Step 2: Restore DUT configuraion!")
    public void ste2() {
           
            UserManage userManage = new UserManage();
            userManage.logout();
     }
    
    

}
