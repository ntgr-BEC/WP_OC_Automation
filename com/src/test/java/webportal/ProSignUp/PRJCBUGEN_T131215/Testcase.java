package webportal.ProSignUp.PRJCBUGEN_T131215;

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
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author Sumanta
 *
 */
public class Testcase extends TestCaseBase {


    Random r = new Random();
    int num = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num);
    String organizationName = "PRJCBUGEN_T131215";
  

    @Feature("ProSignUp") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T131215") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Create Pro Account using a flow Adding Pro Subscription Key - Netherlands country") // It's                                                                                                                                                     // Jira
    // Test
    // Case.
    @TmsLink("PRJCBUGEN-T131215") // It's a testcase id/link from Jira Test Case.

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
            if (new HamburgerMenuPage(false).GotoProAccount(mailname)) {
                              
                Map<String, String> proAccountInfo = new HashMap<String, String>();
                proAccountInfo.put("Confirm Email", mailname);
                proAccountInfo.put("Password", "Netgear1@");
                proAccountInfo.put("Confirm Password", "Netgear1@");
                proAccountInfo.put("Country", "Netherlands");
                proAccountInfo.put("Phone Number", "1234567890");
                new HamburgerMenuPage(false).CreateProAccount(proAccountInfo);

                Map<String, String> businessInfo = new HashMap<String, String>();
                businessInfo.put("Business Name", "Netgear");
                businessInfo.put("Primary Address of Business", "test 1st");
                businessInfo.put("City", "Amsterdam");
                businessInfo.put("State", "Amsterdam");
                businessInfo.put("Zip Code", "1054");
                businessInfo.put("Country", "Netherlands");
                businessInfo.put("Business Phone Number", "1234567890");
                businessInfo.put("Confirm Email", mailname);
                businessInfo.put("Password", "Netgear1@");
                
                assertTrue(new HamburgerMenuPage(false).addingOneYearInsightDevice(businessInfo)," Pro Account creation failed through flow Adding Pro Subscription Key ");
                assertTrue(new HamburgerMenuPage(false).checkLoginSuccessful(), "Create pro account unsuccess. ");      
                
        }
    }

}
