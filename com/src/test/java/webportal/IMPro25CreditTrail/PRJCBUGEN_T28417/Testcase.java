package webportal.IMPro25CreditTrail.PRJCBUGEN_T28417;

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
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.HardBundlePage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    Random r = new Random();
    int num = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num);
    String Email = mailname + "@mailinator.com";

    @Feature("IMPro25CreditTrail") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T28417") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether account which are converted from premium Trail to Pro account can get option to activate Insight Pro Free Trail") 
    @TmsLink("PRJCBUGEN-T28417") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        String url = MyCommonAPIs.getCurrentUrl();
        if (!url.endsWith("/dashboard")) {
            WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
            webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        }
        System.out.println("start to do tearDown");
    }

    
    @Step("Test Step 1: Create IM WP account success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
//        webportalLoginPage.loginByUserPassword("apwptest5993851@mailinator.com","Netgear#123");
        
        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T27057");
        accountInfo.put("Email Address", Email );
        accountInfo.put("Confirm Email", Email);
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "Australia");
        new HamburgerMenuPage(false).createAccount(accountInfo);
    }
    
    @Step("Test Step 2: logout and migrates to a PRO account.")
    public void step2() {
        new HamburgerMenuPage(false).closeLockedDialog();
           String sCheck = "[alt=\"User Image\"]";
            System.out.println("try to do logout");
            $(sCheck).click();
            $(Selectors.byCssSelector(".open ul li:last-child a")).click();
            System.out.println("user is logout");
            MyCommonAPIs.waitReady();
            
            new HardBundlePage().checkCreateProAccountPage("checkNext:" + Email);
       

            
            Map<String, String> businessInfo = new HashMap<String, String>();
//            businessInfo.put("Licence Key", new HamburgerMenuPage(false).readLicenceKeyByTxt("Write"));
            businessInfo.put("Business Name", "Netgear");
            businessInfo.put("Primary Address of Business", "test 1st");
            businessInfo.put("City", "Montville");
            businessInfo.put("State", "Queensland");
            businessInfo.put("Zip Code", "4560");
            businessInfo.put("Country", "Australia");
            businessInfo.put("Business Phone Number", "1234567890");
            new HamburgerMenuPage(false).PremiumTrailAndFinishSignin(businessInfo);

//            assertTrue(new HardBundlePage().checkLoginSuccessful(), "Create pro account unsuccess.");
        
    }

    @Step("Test Step 3: Check trail ")
    public void step3() {
        new HamburgerMenuPage(true).StartFreeTrail();
        assertTrue(new HamburgerMenuPage(true).CheckFreeTrailPro(), "Not received verify email.");
    }

}
