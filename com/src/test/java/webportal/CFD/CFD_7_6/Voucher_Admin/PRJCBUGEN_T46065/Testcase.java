package webportal.CFD.CFD_7_6.Voucher_Admin.PRJCBUGEN_T46065;

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
 * @author RaviShankar
 *
 */
public class Testcase extends TestCaseBase {

    Random r        = new Random();
    int    num      = r.nextInt(10000);
    String mailname = "apwptest" + String.valueOf(num);
    String vochMan = mailname + "@yopmail.com";

    @Feature("Vocher_Admin") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14465") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify whether are we able to voucher admin or not for a pro account") // It's a testcase                                                                                                                           // Test Case.
    @TmsLink("PRJCBUGEN_T14465") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        new ManagerPage().deleteManager(vochMan);
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
        managerInfo.put("Name", "test14460");
        managerInfo.put("Email Address", vochMan);
        managerInfo.put("Organization Name", WebportalParam.Organizations);
        managerInfo.put("Manager Type", "Voucher Admin");

        new ManagerPage().addManager(managerInfo);

        assertTrue(new ManagerPage(false).checkEditResult(managerInfo.get("Email Address"), "-", "1"),
                "Invite manager failed.");

        UserManage userManage = new UserManage();
        userManage.logout();

    }
    
    @Step("Test Step 3: Invite manager and check its success;")
    public void step3() {

        assertTrue(new HamburgerMenuPage(false).checkEmailMessageForInvitemangaerOwner(vochMan), "Not received Invitation email.");
        assertTrue(new HamburgerMenuPage(false).inviteEmailFillDateandAccept(), "Not received Invitation email.");
        Map<String, String> proAccountInfo = new HashMap<String, String>();
        proAccountInfo.put("Confirm Email", vochMan);
        proAccountInfo.put("Password", "Netgear1@");
        proAccountInfo.put("Confirm Password", "Netgear1@");
        proAccountInfo.put("Country", "United States of America");
        proAccountInfo.put("Phone Number", "1234567890");
        new HamburgerMenuPage(false).FillInvitemanagerOwnerInfoAndVerifylogin(proAccountInfo);
        
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(vochMan, proAccountInfo.get("Password"));
        
        assertTrue(new HamburgerMenuPage(false).checkLoginSuccessful());
        
        UserManage userManage = new UserManage();
        userManage.logout();
        
    }

}
