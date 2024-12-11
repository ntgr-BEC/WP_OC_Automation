package webportal.InsightPro.InvitingReadWriteManager.PRJCBUGEN_T14350;

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
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    Random r        = new Random();
    int    num      = r.nextInt(10000);
    String mailname = "apwptest" + String.valueOf(num);
    String writeMan = mailname + "@yopmail.com";

    @Feature("InsightPro.InvitingReadWriteManager") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14350") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify when we click on the invitation for read-write manager its redirect to me manager signup page.") // It's a testcase
                                                                                                                                  // title from Jira
                                                                                                                                  // Test Case.
    @TmsLink("PRJCBUGEN-T14350") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        new ManagerPage().deleteManager(writeMan);
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
        Map<String, String> managerInfo = new HashMap<String, String>();
        managerInfo.put("Name", "test14347");
        managerInfo.put("Email Address", writeMan);
        managerInfo.put("Organization Name", WebportalParam.Organizations);
        managerInfo.put("Access Policy", "Read/Write");

        new ManagerPage().addManager(managerInfo);

        assertTrue(
                new ManagerPage(false).checkSuccessDialog()
                        && new ManagerPage(false).checkEditResult(managerInfo.get("Email Address"), managerInfo.get("Access Policy"), "1"),
                "Invite manager failed.");

        UserManage userManage = new UserManage();
        userManage.logout();

    }
    
    @Step("Test Step 3: Invite manager and check its success;")
    public void step3() {

        assertTrue(new HamburgerMenuPage(false).checkEmailMessageForInvitemangaerOwner(writeMan), "Not received Invitation email.");
        assertTrue(new HamburgerMenuPage(false).inviteEmailFillDateandAccept(), "Not received Invitation email.");
        Map<String, String> proAccountInfo = new HashMap<String, String>();
        proAccountInfo.put("Confirm Email", writeMan);
        proAccountInfo.put("Password", "Netgear1@");
        proAccountInfo.put("Confirm Password", "Netgear1@");
        proAccountInfo.put("Country", "United States of America");
        proAccountInfo.put("Phone Number", "1234567890");
        new HamburgerMenuPage(false).FillInvitemanagerOwnerInfoAndVerifylogin(proAccountInfo);
        UserManage userManage = new UserManage();
        userManage.logout();
        
    }

}
