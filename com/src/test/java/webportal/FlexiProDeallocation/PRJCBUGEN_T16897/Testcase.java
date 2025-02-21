package webportal.FlexiProDeallocation.PRJCBUGEN_T16897;

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

    String              organizationName    = "test16897";
    Map<String, String> organizationInfo    = new HashMap<String, String>();
    Map<String, String> organizationInfoNew = new HashMap<String, String>();
    int                 devNum              = 4;
    int                 devNumNew           = 3;
    int                 icpNum              = 2;
    int                 icpNumNew           = 1;

    @Feature("FlexiProDeallocation") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T16897") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify in deallocation works properly") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T16897") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
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

    @Step("Test Step 3: Deallocate credits, then check devices credits page;")
    public void step3() {
        new HamburgerMenuPage().deallocateCredit(organizationName, "3", "1");
        new HamburgerMenuPage().deallocateCredit(organizationInfoNew.get("Name"), "1", "1");

        HashMap<String, String> creditsInfo = new HamburgerMenuPage().getCreditAllocationStatus(organizationName);
        HashMap<String, String> creditsInfoNew = new HamburgerMenuPage().getCreditAllocationStatus(organizationInfoNew.get("Name"));
        assertTrue(creditsInfo.get("Used Devices Credits").equals("0") && creditsInfo.get("Unused Devices Credits").equals("1")
                && creditsInfo.get("Used ICP Credits").equals("0") && creditsInfo.get("Unused ICP Credits").equals("1")
                && creditsInfoNew.get("Used Devices Credits").equals("0") && creditsInfoNew.get("Unused Devices Credits").equals("2")
                && creditsInfoNew.get("Used ICP Credits").equals("0") && creditsInfoNew.get("Unused ICP Credits").equals("0"),
                "Allocate credits error.");
    }
}
