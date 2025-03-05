package webportal.PremiumToProMigration.FreeTrail.PRJCBUGEN_T31299;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.WebDriver;
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
  

    @Feature("FreeTrail") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T31299") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Create Pro Account using a flow Free Trail - Portugal country") // It's                                                                                                                                                     // Jira
    // Test
    // Case.
    @TmsLink("PRJCBUGEN-T31299") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Check create pro account success.")
    public void step1() {

            mailname = mailname + "@mailinator.com";
            if (new HamburgerMenuPage(false).checkCreateProAccountPage("checkNext:" + mailname)) {
                Map<String, String> proAccountInfo = new HashMap<String, String>();
                proAccountInfo.put("Confirm Email", mailname);
                proAccountInfo.put("Password", "Netgear1@");
                proAccountInfo.put("Confirm Password", "Netgear1@");
                proAccountInfo.put("Country", "Portugal");
                proAccountInfo.put("Phone Number", "1234567890");
                new HamburgerMenuPage(false).CreateProAccount(proAccountInfo);

                Map<String, String> businessInfo = new HashMap<String, String>();
                businessInfo.put("Business Name", "Netgear");
                businessInfo.put("Primary Address of Business", "Praia da Falesia");
                businessInfo.put("City", "Albufeira");
                businessInfo.put("State", "Albufeira");
                businessInfo.put("Zip Code", "8200");
                businessInfo.put("Country", "Portugal");
                businessInfo.put("Business Phone Number", "1234567890");
                
                assertTrue(new HamburgerMenuPage(false).freeTrail(businessInfo)," Free Trail pro Account creation failed. ");
                assertTrue(new HamburgerMenuPage(false).checkLoginSuccessful(), "Create pro account unsuccess.");        
        }
    }

}
