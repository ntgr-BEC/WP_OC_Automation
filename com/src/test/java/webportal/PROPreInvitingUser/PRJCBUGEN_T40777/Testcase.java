package webportal.PROPreInvitingUser.PRJCBUGEN_T40777;

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
    String mailname = "owner" + String.valueOf(num);
    String ownMail  = mailname + "@yopmail.com";
    String organizationName = "PRJCBUGEN40777";

    @Feature("PROPreInvitingUser") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T40777") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the user is able to see the Owner account as active in the PRO admin Owner list.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T40777") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        new OrganizationPage().deleteOrganizationNew(organizationName);
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
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName);
        organizationInfo.put("Owner Name", "test14345");
        organizationInfo.put("Email Address", ownMail);
        organizationInfo.put("Phone Number", "12345678910");
        organizationInfo.put("Business Phone Number", "10987654321");

        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrganization(organizationInfo);
    } 
    
    @Step("Test Step 3: Invite manager and check its success;")
    public void step3() {
        UserManage userManage = new UserManage();
        userManage.logout();
        assertTrue(new HamburgerMenuPage(false).checkEmailMessageForInvitemangaerOwner(ownMail), "Not received Invitation email.");
        assertTrue(new HamburgerMenuPage(false).inviteEmailFillDateandAccept(), "Not received Invitation email.");
        Map<String, String> proAccountInfo = new HashMap<String, String>();
        proAccountInfo.put("Confirm Email", ownMail);
        proAccountInfo.put("Password", "Netgear1@");
        proAccountInfo.put("Confirm Password", "Netgear1@");
        proAccountInfo.put("Country", "United States of America");
        proAccountInfo.put("Phone Number", "1234567890");
        new HamburgerMenuPage(false).FillInvitemanagerOwnerInfoAndVerifylogin(proAccountInfo);
        userManage.logout();  
        
    }
    
    @Step("Test Step 4: Verify that oner is active or not;")
    public void step4() {
        
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        new OrganizationPage(false).openOrg(organizationName);
        assertTrue(new OrganizationPage(false).verifyActiveOwner(ownMail), "Owner is not showing active");
        
    }
}
