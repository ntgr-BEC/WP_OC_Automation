package webportal.ICP.admin.PRJCBUGEN_T15008;

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
    String mailname = "";

    @Feature("InstantCaptivePortal.IcpUnderAccount.ProUser") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T15008") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify Pro account Owner is not able to see the Instant Captive Portal License details under Account Management") // It's a test case title from Jira TestCase.
    @TmsLink("PRJCBUGEN-T15008") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        String url = MyCommonAPIs.getCurrentUrl();
        if (!url.endsWith("#/accountManagement/creditAllocation") && !url.contains("managers")) {
            WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
            webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        } else if (url.endsWith("#/accountManagement/creditAllocation") && url.contains("insight.netgear")) {
            UserManage userManage = new UserManage();
            userManage.logout();

            WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
            webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        }

        new ManagerPage().deleteManager(mailname);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

    }

    @Step("Test Step 2: Invite manager and check url in invite email can sign up;")
    public void step2() {
        mailname = new HamburgerMenuPage(false).getRandomWord() + String.valueOf(num) + "@sharklasers.com";
        Map<String, String> managerInfo = new HashMap<String, String>();
        managerInfo.put("Name", "test15008");
        managerInfo.put("Email Address", mailname);
        managerInfo.put("Organization Name", WebportalParam.Organizations);
        managerInfo.put("Access Policy", "Read");

        new ManagerPage().addManager(managerInfo);

        System.out.println("adding manger is done");
        if ( new ManagerPage(false).checkManagerIsExist(managerInfo.get("Email Address"))) {
            UserManage userManage = new UserManage();
            userManage.logout();

            if (new HamburgerMenuPage(false).checkEmailMessage(managerInfo.get("Email Address"))) {
                Map<String, String> managerAccountInfo = new HashMap<String, String>();
                managerAccountInfo.put("Confirm Email", managerInfo.get("Email Address"));
                managerAccountInfo.put("Password", WebportalParam.adminPassword);
                managerAccountInfo.put("Confirm Password", WebportalParam.adminPassword);
                managerAccountInfo.put("Country", "United States of America");
                managerAccountInfo.put("Phone Number", "1234567890");

                new HamburgerMenuPage(false).createManagerAccount(managerAccountInfo);
                assertTrue(new HamburgerMenuPage(false).checkLoginSuccessful(), "Create manager account failed.");
            } else {
                assertTrue(false, "Not received invite manager email.");
            }
        } else {
            assertTrue(false, "Add manager failed.");
        }

    }

    @Step("Test Step 3: Check ICP credits;")
    public void step3() {
        new HamburgerMenuPage().accountmanager.click();
        MyCommonAPIs.waitReady();
        assertTrue(!new HamburgerMenuPage(false).captiveportalservices.exists(), "Captive portal services display error.");
    }

}
