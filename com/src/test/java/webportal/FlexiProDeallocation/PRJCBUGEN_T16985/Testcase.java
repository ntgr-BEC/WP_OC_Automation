package webportal.FlexiProDeallocation.PRJCBUGEN_T16985;

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
    String organizationName = "TEST16985";

    @Feature("FlexiProDeallocation") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T16985") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify owner have option to \"Deallocate\" in account") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T16985") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        String url = MyCommonAPIs.getCurrentUrl();
        if (!url.contains("accountManagement/creditAllocation") && !url.contains("insight.netgear")) {
            WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
            webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        } else if (url.contains("accountManagement/creditAllocation") && url.contains("insight.netgear")) {
            UserManage userManage = new UserManage();
            userManage.logout();

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
        
        new OrganizationPage().deleteOrganizationNew(organizationName);
    }

    @Step("Test Step 2: Create a organization and add one owner, then create owner account and check credits allocate page;")
    public void step2() {
        mailname = new HamburgerMenuPage(false).getRandomWord() + String.valueOf(num) + "@yopmail.com";
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName);
        organizationInfo.put("Owner Name", "test16985");
        organizationInfo.put("Email Address", mailname);

        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrganization(organizationInfo);

        if (OrganizationPage.checkOrganizationIsExist(organizationName) && OrganizationPage.checkOrganizationOwner(organizationInfo)) {
            System.out.println("----------------------");
            UserManage userManage = new UserManage();
            userManage.logout();

            if (new HamburgerMenuPage(false).checkEmailMessageForMultiAdmin(mailname)) {
                Map<String, String> ownerAccountInfo = new HashMap<String, String>();
                ownerAccountInfo.put("Confirm Email", organizationInfo.get("Email Address"));
                ownerAccountInfo.put("Password", WebportalParam.adminPassword);
                ownerAccountInfo.put("Confirm Password", WebportalParam.adminPassword);
                ownerAccountInfo.put("Country", "United States of America");
                ownerAccountInfo.put("Phone Number", "1234567890");

                new HamburgerMenuPage(false).createManagerAccount(ownerAccountInfo);
                assertTrue(OrganizationPage.checkOrganizationIsExist(organizationName), "Create owner account failed.");
            } else {
                assertTrue(false, "Not received invite owner email.");
            }
        } else {
            assertTrue(false, "Organization not created.");
        }

        new HamburgerMenuPage().gotoCreditsAllocationPage();
        assertTrue(!new HamburgerMenuPage(false).deallocate.exists(), "Credits allocate page display error.");
    }

}
