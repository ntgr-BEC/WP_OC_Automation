package webportal.PROPreInvitingUser.Owner.PRJCBUGEN_T40773;

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
    String mailname = "apwptest" + String.valueOf(num);
    String ownMail  = mailname + "@yopmail.com";
    String organizationName = "PRJCBUGEN_T40773";

    @Feature("PROPreInvitingUser") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T40773") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify a user is able to Invite the Owner.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T40773") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new OrganizationPage().deleteOrganizationNew(organizationName);
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

    }

    @Step("Test Step 2: Create a organization and change organization owner information, then check its changed;")
    public void step2() {
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName);
        organizationInfo.put("Owner Name", "test14345");
        organizationInfo.put("Email Address", mailname + "@yopmail.com");
        organizationInfo.put("Phone Number", "12345678910");
        organizationInfo.put("Business Phone Number", "10987654321");

        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrganization(organizationInfo);

        if (OrganizationPage.checkOrganizationIsExist(organizationName) && OrganizationPage.checkOrganizationOwner(organizationInfo)) {
            organizationInfo.put("Email Address", mailname + "22@yopmail.com");
            organizationInfo.put("Phone Number", "10987654321");
            organizationInfo.put("Business Phone Number", "12345678910");
            new OrganizationPage().editOrganization(organizationInfo);

            assertTrue(OrganizationPage.checkOrganizationOwner(organizationInfo), "Organization owner information not changed.");
        } else {
            assertTrue(false, "Organization not created.");
        }
     
    }

}
