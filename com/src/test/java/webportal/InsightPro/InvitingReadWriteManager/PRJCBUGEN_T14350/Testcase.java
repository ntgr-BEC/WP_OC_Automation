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
    String mailname = "";

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
        String url = MyCommonAPIs.getCurrentUrl();
        if (!url.endsWith("#/organization/dashboard") && !url.contains("managers")) {
            WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
            webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        }

        new ManagerPage().deleteManager(mailname);
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

    }

    @Step("Test Step 2: Invite manager and check sign up url in invite email;")
    public void step2() {
        mailname = new HamburgerMenuPage(false).getRandomWord() + String.valueOf(num) + "@sharklasers.com";
        Map<String, String> managerInfo = new HashMap<String, String>();
        managerInfo.put("Name", "test14350");
        managerInfo.put("Email Address", mailname);
        managerInfo.put("Organization Name", WebportalParam.Organizations);

        new ManagerPage().addManager(managerInfo);

        if (new ManagerPage(false).checkSuccessDialog() && new ManagerPage(false).checkManagerIsExist(managerInfo.get("Email Address"))) {
            UserManage userManage = new UserManage();
            userManage.logout();

            assertTrue(new HamburgerMenuPage(false).checkEmailMessage(managerInfo.get("Email Address"))
                    && new HamburgerMenuPage(false).checkCreateProAccountPage("checkManager"), "Not received invite manager email.");
        } else {
            assertTrue(false, "Add manager failed.");
        }

    }

}
