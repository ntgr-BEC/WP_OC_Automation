package webportal.WebPortalUsabilityImprovements.PRJCBUGEN_T32202;

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
import util.APUtils;
import util.MyCommonAPIs;
import util.RunCommand;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.ManagerPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;
import webportal.weboperation.SummaryPage;

/**
 *
 * @author Vivek Singh
 *
 */
public class Testcase extends TestCaseBase {
    String organizationName1 = "organization1";
    String locationName      = "Office1";
    Random r                 = new Random();
    int    num               = r.nextInt(10000);
    String mailname          = "";
    String mgrName           = "PRJCBUGEN_T32202";

    @Feature("IM-6.10-Web Portal Usability Improvements") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T32202") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that managers should be assigned 1 or more organisations to manage.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T32202") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        OrganizationPage page = new OrganizationPage();
        page.deleteOrganizationNew(organizationName1);
    }

//     Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

    }

     @Step("Test Step 2: Create an organization1")
     public void step2() {
     Map<String, String> organizationInfo = new HashMap<String, String>();
     organizationInfo.put("Name", organizationName1);
     OrganizationPage OrganizationPage = new OrganizationPage();
     OrganizationPage.addOrganization(organizationInfo);
     MyCommonAPIs.sleepi(5);
    
     }

    @Step("Test Step 3: Invite manager from All Manager Page")
    public void step3() {
        mailname = new HamburgerMenuPage(false).getRandomWord() + String.valueOf(num) + "@sharklasers.com";
        logger.info("manager email -----> " + mailname);
        Map<String, String> managerInfo = new HashMap<String, String>();
        managerInfo.put("Name", mgrName);
        managerInfo.put("Email Address", mailname);
        managerInfo.put("Organization Name", organizationName1);
        new ManagerPage().addManager(managerInfo);
        MyCommonAPIs.sleepi(5);
    }

    @Step("Test Step 4: verify that manager is able to accept the invitation and Manage the Org")
    public void step4() {
        UserManage userManage = new UserManage();
        userManage.logout();
        MyCommonAPIs.sleepi(5);
        Map<String, String> managerInfo = new HashMap<String, String>();
        managerInfo.put("Email Address", mailname);
        managerInfo.put("Name", "mgrName");
        if (new HamburgerMenuPage(false).checkEmailMessage(managerInfo.get("Email Address"))) {
            Map<String, String> managerAccountInfo = new HashMap<String, String>();
            managerAccountInfo.put("Confirm Email", managerInfo.get("Email Address"));
            managerAccountInfo.put("Password", WebportalParam.adminPassword);
            managerAccountInfo.put("Confirm Password", WebportalParam.adminPassword);
            managerAccountInfo.put("Country", "United States of America");
            managerAccountInfo.put("Phone Number", "1234567890");
            System.out.println("Going to Create Manager Accounts");
            new HamburgerMenuPage(false).createManagerAccount(managerAccountInfo);
            System.out.println("Going to loging with manager email");
            assertTrue(new HamburgerMenuPage(false).checkLoginSuccessful(), "Create manager account failed.");
        } else {
            assertTrue(false, "Not received invite manager email.");
        }
    }

}
