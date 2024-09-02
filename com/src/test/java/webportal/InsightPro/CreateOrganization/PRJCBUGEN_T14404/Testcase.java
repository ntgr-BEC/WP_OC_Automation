package webportal.InsightPro.CreateOrganization.PRJCBUGEN_T14404;

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
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    Random r                = new Random();
    int    num              = r.nextInt(10000);
    String mailname         = "";
    String organizationName = "TEST14404";

    @Feature("InsightPro.CreateOrganization") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14404") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Owner invitation email.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14404") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        String url = MyCommonAPIs.getCurrentUrl();
        if (!url.endsWith("#/organization/dashboard") && url.indexOf("insight.netgear") == -1) {
            WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
            webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        }

        new OrganizationPage().deleteOrganizationNew(organizationName);
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
    }

    @Step("Test Step 2: Create a organization and add one owner, then check invite owner email;")
    public void step2() {
        mailname = new HamburgerMenuPage(false).getRandomWord() + String.valueOf(num) + "@sharklasers.com";
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName);
        organizationInfo.put("Owner Name", "test14404");
        organizationInfo.put("Email Address", mailname);

        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrganization(organizationInfo);

        if (OrganizationPage.checkOrganizationIsExist(organizationName) && OrganizationPage.checkOrganizationOwner(organizationInfo)) {
            UserManage userManage = new UserManage();
            userManage.logout();

            assertTrue(new HamburgerMenuPage(false).checkEmailMessage(mailname), "Not received invite owner email.");
        } else {
            assertTrue(false, "Organization not created.");
        }
    }

}
