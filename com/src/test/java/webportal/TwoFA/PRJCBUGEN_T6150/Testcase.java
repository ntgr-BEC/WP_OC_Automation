package webportal.TwoFA.PRJCBUGEN_T6150;

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
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {
    
    Random r = new Random();
    int num = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num);
    Map<String, String> accountInfo = new HashMap<String, String>();

    @Feature("TwoFA") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T6150") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether 2FA EMAIL works") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T6150") // It's a testcase id/link from Jira Test Case.

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
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T17523");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "Ireland");


        new HamburgerMenuPage(false).createAccount(accountInfo);
      
    }

    @Step("Test Step 2: Enable 2FA and check resend message;")
    public void step2() {
        new HamburgerMenuPage().enableTwoFAEmail();
        MyCommonAPIs.sleepi(10);
        
        UserManage userManage = new UserManage();
        userManage.logout();
        
        new WebportalLoginPage().twoFaEmailLogin(mailname,accountInfo.get("Password"));

        assertTrue(new HamburgerMenuPage().checkLoginSuccessful(), "Login successful");
    }

}
