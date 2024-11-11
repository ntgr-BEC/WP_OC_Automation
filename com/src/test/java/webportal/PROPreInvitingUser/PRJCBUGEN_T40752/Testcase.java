package webportal.PROPreInvitingUser.PRJCBUGEN_T40752;

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
    String mailname = "apwptest" + String.valueOf(num);
    String email = mailname + "@yopmail.com";

    @Feature("PROPreInvitingUser") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T40752") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify user is able to get the invitation link on email.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T40752") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        new ManagerPage().deleteManager(mailname + "@yopmail.com");
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
        Map<String, String> managerInfo = new HashMap<String, String>();
        managerInfo.put("Name", "test14408");
        managerInfo.put("Email Address", email);
        managerInfo.put("Organization Name", WebportalParam.Organizations);
        managerInfo.put("Access Policy", "Read/Write");

        new ManagerPage().addManager1(managerInfo);

        assertTrue(
                new ManagerPage(false).checkSuccessDialog()
                        && new ManagerPage(false).checkEditResult(managerInfo.get("Email Address"), managerInfo.get("Access Policy"), "1"),
                "Invite manager failed.");

    } 
    
    @Step("Test Step 3: Invite manager and check its success;")
    public void step3() {
        
        new ManagerPage().deleteManager(mailname + "@yopmail.com");
        Map<String, String> managerInfo = new HashMap<String, String>();
        managerInfo.put("Name", "test14408");
        managerInfo.put("Email Address", email);
        managerInfo.put("Organization Name", WebportalParam.Organizations);
        managerInfo.put("Access Policy", "Read/Write");

        new ManagerPage().addManager1(managerInfo);
        assertTrue(
                new ManagerPage(false).checkSuccessDialog()
                        && new ManagerPage(false).checkEditResult(managerInfo.get("Email Address"), managerInfo.get("Access Policy"), "1"),
                "Invite manager failed.");
        UserManage userManage = new UserManage();
        userManage.logout();
        assertTrue(new HamburgerMenuPage(false).checkEmailMessageForInvitemangaerOwner(managerInfo.get("Email Address")), "Not received Invitation email.");
        
    }
}
