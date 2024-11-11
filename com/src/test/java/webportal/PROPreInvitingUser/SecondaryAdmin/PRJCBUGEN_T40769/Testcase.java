package webportal.PROPreInvitingUser.SecondaryAdmin.PRJCBUGEN_T40769;

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

/**
 *
 * @Pratik
 *
 */
public class Testcase extends TestCaseBase {

    Random r        = new Random();
    int    num      = r.nextInt(10000000);
    String mailname = "secadmintest" + String.valueOf(num);
    String secAdminMail = mailname + "@yopmail.com";

    @Feature("PROPreInvitingUser") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T40769") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify a user is able to get the invitation link on email for the again invited Secondary Admin.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T40769") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        new ManagerPage(false).deleteSecondaryAdminEmail(secAdminMail);
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

    }

    @Step("Test Step 2: Invite manager and check its success;")
    public void step2() {
        Map<String, String> secAdminInfo = new HashMap<String, String>();
        secAdminInfo.put("Name", "testsec14408");
        secAdminInfo.put("Email Address", secAdminMail);
        assertTrue(new ManagerPage(false).addSecondaryAdmin(secAdminInfo), "Invite secondary admin failed.");
    } 
    
    @Step("Test Step 3: Invite manager and check its success;")
    public void step3() {
        
        new ManagerPage(false).deleteSecondaryAdminEmail(secAdminMail);
        Map<String, String> secAdminInfo = new HashMap<String, String>();
        secAdminInfo.put("Name", "testsec14408");
        secAdminInfo.put("Email Address", secAdminMail);
        assertTrue(new ManagerPage(false).addSecondaryAdmin(secAdminInfo), "Invite secondary admin failed.");
        assertTrue(new HamburgerMenuPage(false).checkEmailMessageForInvitemangaerOwner(secAdminInfo.get("Email Address")), "Not received Invitation email.");
        
    }
}
