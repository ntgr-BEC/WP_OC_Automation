package webportal.HamburgerMenu.PRJCBUGEN_T15515;

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
import webportal.publicstep.UserManage;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
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

    Map<String, String> ssidInfo                 = new HashMap<String, String>();
    Random              r           = new Random();
    int                 num         = r.nextInt(10000000);
    String              mailname    = "apwptest" + String.valueOf(num);
    String              mailname1   = mailname + String.valueOf(num) + "@sharklasers.com";
    String              temp        = "";
    String              password    = "Netgear#123";
    String             newpassword1 = "Netgear1@";
    Map<String, String> accountInfo = new HashMap<String, String>();

    @Feature("AdvanceICP10Device1Year") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T15515") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify forget password functionality for Insight Premium Account") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T15515") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create new premium account")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);

       
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T24065");
        accountInfo.put("Email Address", mailname1);
        accountInfo.put("Confirm Email", mailname1);
        accountInfo.put("Password", password);
        accountInfo.put("Confirm Password", password);
        accountInfo.put("Country", "Australia");

        new HamburgerMenuPage(false).createAccount(accountInfo);
    }
    
   

    @Step("Test Step 2: While login into premium account click on forget password")
    public void step2() {
        
        new HamburgerMenuPage().changePassowrd(accountInfo.get("Password"), "Netgear1@");
     
        UserManage userManage = new UserManage();
        userManage.logout();
        
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(mailname1,"Netgear1@");
    }
    
    

}
