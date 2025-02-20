package webportal.FlexibleProSubscription.PRJCBUGEN_T16017;

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

    String organizationName = "test16017";

    @Feature("FlexibleProSubscription") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T16017") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that an error message is displayed if the user allocates the credit count greater than the available credit count.") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T16017") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new OrganizationPage().deleteOrganizationNew(organizationName);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
    }

    @Step("Test Step 2: Add new organization, then check devices credits page;")
    public void step2() {
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName);

        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrganization(organizationInfo);
        OrganizationPage.openOrg(organizationName);

        new HamburgerMenuPage().gotoCreditsAllocationPage();
        String totalDevCredits = new HamburgerMenuPage(false).getTotalDevicesCredits();
        String totalIcpCredtis = new HamburgerMenuPage(false).getTotalIcpCredits();
        HashMap<String, String> creditsInfo = new HamburgerMenuPage().getCreditAllocationTableInfo(organizationName);
        
        assertTrue(new HamburgerMenuPage().checkCreditsAllocationErrorMsg(organizationName,
                Integer.valueOf(totalDevCredits) - Integer.valueOf(creditsInfo.get("Devices Credits")), 0,
                Integer.valueOf(totalIcpCredtis) - Integer.valueOf(creditsInfo.get("ICP Credits"))), "Allocate credits error.");
    }

}
