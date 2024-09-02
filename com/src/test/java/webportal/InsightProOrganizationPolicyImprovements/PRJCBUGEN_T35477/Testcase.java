package webportal.InsightProOrganizationPolicyImprovements.PRJCBUGEN_T35477;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DeviceScreenNavigationPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.SummaryPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Anusha H
 *
 */
public class Testcase extends TestCaseBase {
    String organizationName = "PRJCBUGEN_T35477";

    @Feature("InsightProOrganizationPolicyImprovements") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T35477") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that user able to edit organisation details in both organisation and location level.") // It's a testcase title
                                                                                                                 // from Jira Test Case.
    @TmsLink("PRJCBUGEN-T35477") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        OrganizationPage page = new OrganizationPage();
        page.deleteOrganizationNew("Org2");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to the pro account ;")
    public void step1() {
 
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);

    }

    @Step("Test Step 2 : Edit the org and location details ;")
    public void step3() {

        assertTrue(new OrganizationPage(false).EditOrg(),"The Apply to all organization checkbox is present inside ;");          
        
    }
    
}
