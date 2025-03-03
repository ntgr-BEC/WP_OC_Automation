package webportal.WebPortalUsabilityImprovements.PRJCBUGEN_T32709;

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
        String organizationName2 = "organization2";
        String organizationName3 = "organization3";
        String organizationName4 = "organization4";
        String organizationName5 = "organization5";
        String organizationName6 = "organization6";
        String locationName      = "Office1";
        Random r                 = new Random();
        int    num               = r.nextInt(10000);
        String mailname          = "";
        String mgrName           = "Test_T32205";


    @Feature("IM-6.10-Web Portal Usability Improvements") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T32709") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify When support user click on org on manager list on trey 5 org should be see and click on view option then all org should be displayed.") // It's a
                                                                                                                               // testcase title
                                                                                                                               // from Jira Test
                                                                                                                               // Case.
    @TmsLink("PRJCBUGEN-T32709") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        OrganizationPage page = new OrganizationPage();
        page.deleteOrganizationNew(organizationName1);
        page.deleteOrganizationNew(organizationName2);
        page.deleteOrganizationNew(organizationName3);
        page.deleteOrganizationNew(organizationName4);
        page.deleteOrganizationNew(organizationName5);
        page.deleteOrganizationNew(organizationName6);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminSupportUser, WebportalParam.adminPassword);
        webportalLoginPage.clickOnGoToMyInsightAccount();
    }

    @Step("Test Step 2: Create an organization_1")
    public void step2() {
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName1);
        OrganizationPage OrganizationPage = new OrganizationPage();
        new MyCommonAPIs().open(URLParam.hreforganization, true);
        OrganizationPage.addOrganization(organizationInfo);
        MyCommonAPIs.sleepi(5);

    }

    @Step("Test Step 3: Create an organization_2")
    public void step3() {
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName2);
        OrganizationPage OrganizationPage = new OrganizationPage();
        new MyCommonAPIs().open(URLParam.hreforganization, true);
        OrganizationPage.addOrganization(organizationInfo);
        MyCommonAPIs.sleepi(5);

    }

    @Step("Test Step 4: Create an organization_3")
    public void step4() {
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName3);
        OrganizationPage OrganizationPage = new OrganizationPage();
        new MyCommonAPIs().open(URLParam.hreforganization, true);
        OrganizationPage.addOrganization(organizationInfo);
        MyCommonAPIs.sleepi(5);

    }

    @Step("Test Step 5: Create an organization_4")
    public void step5() {
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName4);
        OrganizationPage OrganizationPage = new OrganizationPage();
        new MyCommonAPIs().open(URLParam.hreforganization, true);
        OrganizationPage.addOrganization(organizationInfo);
        MyCommonAPIs.sleepi(5);

    }

    @Step("Test Step 6: Create an organization_5")
    public void step6() {
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName5);
        OrganizationPage OrganizationPage = new OrganizationPage();
        new MyCommonAPIs().open(URLParam.hreforganization, true);
        OrganizationPage.addOrganization(organizationInfo);
        MyCommonAPIs.sleepi(5);

    }

    @Step("Test Step 7: Create an organization_6")
    public void step7() {
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName6);
        OrganizationPage OrganizationPage = new OrganizationPage();
        new MyCommonAPIs().open(URLParam.hreforganization, true);
        OrganizationPage.addOrganization(organizationInfo);
        MyCommonAPIs.sleepi(5);

    }

    @Step("Test Step 8: Invite manager from All Manager Page")
    public void step8() {
        mailname = new HamburgerMenuPage(false).getRandomWord() + String.valueOf(num) + "@sharklasers.com";
        System.out.println("step8 email"+mailname);
        Map<String, String> managerInfo = new HashMap<String, String>();
        managerInfo.put("Name", mgrName);
        managerInfo.put("Email Address", mailname);
//        managerInfo.put("Organization Name", organizationName1);
        managerInfo.put("Grant Access All", "Grant Access All");
        managerInfo.put("Select All", "Select All");
        new ManagerPage().addManager(managerInfo);
    }

    @Step("Test Step 9: Verify that user should be able to see the organization under the pop-up screen ")
    public void step9() {
        System.out.println("step9 email"+mailname);
        new ManagerPage(false).hoverOverTheOrgCountOfAddedMgr(mailname);
        MyCommonAPIs.sleepi(2);
        assertTrue(new ManagerPage(false).VerifyFiveOrgisVisibleOnPopUp(mailname));

    }
    
    @Step("Test Step 10: Verify that user should be able to click View All Button and also verify org are present on that page ")
    public void step10() {
        System.out.println("step10 email"+mailname);
        new ManagerPage(false).clickOnViewAllButton(mailname);
        MyCommonAPIs.sleepi(2);
        assertTrue(new ManagerPage(false).VerifyOrgIsPresentOnViewAllSection(organizationName4));

    }

}
