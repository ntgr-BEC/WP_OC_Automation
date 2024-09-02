package webportal.CFD.CFD_Troubleshoot_Ookala.PRJCBUGEN_T33338;

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
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.ManagerPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @Pratik
 *
 */
public class Testcase extends TestCaseBase {

    Random r        = new Random();
    int    num      = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num);
    String newEmailAddress = "secondaryadmin@mailcatch.com";
    Map<String, String> organizationInfo = new HashMap<String, String>();
    Map<String, String> proAccountInfo = new HashMap<String, String>();
    String Name     = "PRJCBUGEN_T33338";
    
    @Feature("InsightPro.InvitingReadManager") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T33338") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("CFD CESMB-4241 Troubleshooting > Speedtest screen error") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T33338") // It's a testcase id/link from Jira Test Case.

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
            proAccountInfo.put("Confirm Email", mailname);
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

    @Step("Test Step 2: Invite manager and check its success;")
    public void step2() {
        assertTrue(new WirelessQuickViewPage(false).Inviteadmin(Name, newEmailAddress ), "secondary admin is not sucessfull");
    }
    
    @Step("Test Step 3: Change admin email with Secondary Admin;")
    public void step3() {
        Map<String, String> changeMailInfo = new HashMap<String, String>();
        changeMailInfo.put("New Email Name", newEmailAddress);
        changeMailInfo.put("Password", "Netgear1@");
        new HamburgerMenuPage().verifyAndDochangeEmail(changeMailInfo);
    }

}
