package webportal.FlexiProDeallocation.PRJCBUGEN_T16896;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

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
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {
    OrganizationPage OrganizationPage = new OrganizationPage();
    String              organizationName    = "test16896";
    Map<String, String> organizationInfo    = new HashMap<String, String>();
    Map<String, String> organizationInfoNew = new HashMap<String, String>();
    int                 devNum              = 3;
    int                 devNumNew           = 2;
    int                 icpNum              = 2;
    int                 icpNumNew           = 1;

    @Feature("FlexiProDeallocation") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T16896") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify in deallocate page user able to select all the Unused credit options for deallocation") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T16896") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new OrganizationPage().deleteOrganizationNew(organizationName);
        new OrganizationPage().deleteOrganizationNew(organizationInfoNew.get("Name"));
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
    }

    @Step("Test Step 2: Add new organization, then check devices credits page;")
    public void step2() {
        organizationInfo.put("Name", organizationName);
        organizationInfoNew.put("Name", organizationName + "new");

        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrganization(organizationInfo);
        OrganizationPage.addOrganization(organizationInfoNew);

        new HamburgerMenuPage().configCreditAllocation(organizationInfoNew.get("Name"), devNumNew, 0, icpNumNew);
        new HamburgerMenuPage().configCreditAllocation(organizationName, devNum, 0, icpNum);

        HashMap<String, String> creditsInfo = new HamburgerMenuPage().getDeallocatePageInfo();
        assertTrue(creditsInfo.get("Deallocate DevNum").equals(String.valueOf(devNumNew + devNum))
                && creditsInfo.get("Deallocate IcpNum").equals(String.valueOf(icpNum + icpNumNew)), "Allocate credits error.");
    }

    @Step("Test Step 3: Check deallocate credits page;")
    public void step3() {
        assertTrue(new HamburgerMenuPage().checkDeallocateOrgExist(organizationName)
               , "Deallocate credits error.");
        OrganizationPage.gotoPage();
        OrganizationPage.openOrg(organizationName);
        
        assertTrue(new HamburgerMenuPage().checkDeallocateOrgExist(organizationInfoNew.get("Name")), "Deallocate credits error.");
    }

}
