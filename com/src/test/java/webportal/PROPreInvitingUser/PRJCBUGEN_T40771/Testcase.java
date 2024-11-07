package webportal.PROPreInvitingUser.PRJCBUGEN_T40771;

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
import webportal.weboperation.ManagerPage;
import webportal.weboperation.OrganizationPage;
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
    @Story("PRJCBUGEN_T40771") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify user clicks on the invitation URL then its redirects login page instead of the signup page for the again invited Secondary Admin.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T40771") // It's a testcase id/link from Jira Test Case.

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

    @Step("Test Step 2: Add Manager;")
    public void step2() {
        Map<String, String> secAdminInfo = new HashMap<String, String>();
        secAdminInfo.put("Name", "testsec14408");
        secAdminInfo.put("Email Address", secAdminMail);
        assertTrue(new ManagerPage(false).addSecondaryAdmin(secAdminInfo), "Invite secondary admin failed.");
        UserManage userManage = new UserManage();
        userManage.logout();
        assertTrue(new HamburgerMenuPage(false).checkEmailMessageForInvitemangaerOwner(secAdminMail), "Not received Invitation email.");
        assertTrue(new HamburgerMenuPage(false).inviteEmailFillDateandAccept(), "Not received Invitation email.");
        Map<String, String> proAccountInfo = new HashMap<String, String>();
        proAccountInfo.put("Confirm Email", secAdminMail);
        proAccountInfo.put("Password", "Netgear1@");
        proAccountInfo.put("Confirm Password", "Netgear1@");
        proAccountInfo.put("Country", "United States of America");
        proAccountInfo.put("Phone Number", "1234567890");
        new HamburgerMenuPage(false).FillInvitemanagerOwnerInfoAndVerifylogin(proAccountInfo);
        userManage.logout();

    } 
    
    @Step("Test Step 3: Login again and delete added manager and again invite same manager;")
    public void step3() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        new ManagerPage(false).deleteSecondaryAdminEmail(secAdminMail);
        
        Map<String, String> secAdminInfo = new HashMap<String, String>();
        secAdminInfo.put("Name", "testsec14408");
        secAdminInfo.put("Email Address", secAdminMail);
        assertTrue(new ManagerPage(false).addSecondaryAdmin(secAdminInfo), "Invite secondary admin failed.");
        UserManage userManage = new UserManage();
        userManage.logout();
        assertTrue(new HamburgerMenuPage(false).checkEmailMessageForInvitemangaerOwner1(secAdminMail), "Not received Invitation email.");
        assertTrue(new HamburgerMenuPage(false).inviteEmailFillDateandAccept(), "Not received Invitation email.");
        MyCommonAPIs.sleepi(10);
        new WebportalLoginPage(false).inputLogin(secAdminMail, "Netgear1@");
        new OrganizationPage(false).clickonOkayGotit();
        userManage.logout();
        
    }
}
