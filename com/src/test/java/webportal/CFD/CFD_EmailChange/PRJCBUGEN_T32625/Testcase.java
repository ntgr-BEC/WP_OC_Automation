package webportal.CFD.CFD_EmailChange.PRJCBUGEN_T32625;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @Pratik
 *
 */
public class Testcase extends TestCaseBase {

    String organizationName = "PRJCBUGEN_T32625";
    Random r                = new Random();
    int    num              = r.nextInt(10000000);
    String mailname         = "apwptest" + String.valueOf(num);
    Map<String, String> organizationInfo = new HashMap<String, String>();
    Map<String, String> proAccountInfo = new HashMap<String, String>();
    String ownerMail = mailname + "22@mailcatch.com";

    @Feature("ProSubscriptionKey") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T32625") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the pro admin user changed the email address using the owner's email address.") // It's // Jira
    // Test
    // Case.
    @TmsLink("PRJCBUGEN_T32625") // It's a testcase id/link from Jira Test Case.

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
        
        Selenide.clearBrowserCookies();
        Selenide.refresh();
        
        if (new HamburgerMenuPage(false).checkCreateProAccountPage("checkNext:" + mailname+ "@mailinator.com")) {
            
            proAccountInfo.put("Confirm Email", mailname+ "@mailinator.com");
            proAccountInfo.put("Password", "Netgear1@");
            proAccountInfo.put("Confirm Password", "Netgear1@");
            proAccountInfo.put("Country", "United States of America");
            proAccountInfo.put("Phone Number", "1234567890");
            new HamburgerMenuPage(false).CreateProAccount(proAccountInfo);

            Map<String, String> businessInfo = new HashMap<String, String>();
            businessInfo.put("Business Name", "Netgear");
            businessInfo.put("Primary Address of Business", "test 1st");
            businessInfo.put("City", "Florida");
            businessInfo.put("State", "Florida");
            businessInfo.put("Zip Code", "32003");
            businessInfo.put("Country", "United States of America");
            businessInfo.put("Business Phone Number", "1234567890");

            assertTrue(new HamburgerMenuPage(false).freeTrail(businessInfo), " Free Trail pro Account creation failed. ");
            assertTrue(new HamburgerMenuPage(false).checkLoginSuccessful(), "Create pro account unsuccess.");
        }
    }

    @Step("Test Step 2: to change the organization owner")
    public void step2() {
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName);
        organizationInfo.put("Owner Name", "test14345");
        organizationInfo.put("Email Address", mailname + "@mailcatch.com");
        organizationInfo.put("Phone Number", "12345678910");
        organizationInfo.put("Business Phone Number", "10987654321");
        organizationInfo.put("Email Address", ownerMail);
        organizationInfo.put("Phone Number", "10987654321");
        organizationInfo.put("Business Phone Number", "12345678910");

        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrganization(organizationInfo);
//        MyCommonAPIs.sleepi(10);
//        if (OrganizationPage.checkOrganizationIsExist(organizationName) && OrganizationPage.checkOrganizationOwner(ownerMail)) {
//
//            new OrganizationPage().editOrganization(organizationInfo);
//
//            assertTrue(OrganizationPage.checkOrganizationOwner(organizationInfo), "Organization owner information not changed.");
//        } else {
//            assertTrue(false, "Organization not created.");
//        }
        
    }
    
    @Step("Test Step 3: Change admin email with owner;")
    public void step3() {
        Map<String, String> changeMailInfo = new HashMap<String, String>();
        changeMailInfo.put("New Email Name", ownerMail);
        changeMailInfo.put("Password", "Netgear1@");
        assertTrue(new HamburgerMenuPage().verifyAndDochangeEmail(changeMailInfo), "Owner mail is not changed with admin mail");
    }
}
