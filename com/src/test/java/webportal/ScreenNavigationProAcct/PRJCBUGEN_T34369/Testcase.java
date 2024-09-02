package webportal.ScreenNavigationProAcct.PRJCBUGEN_T34369;

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
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Vivek Singh
 *
 */
public class Testcase extends TestCaseBase {
    String organizationName1 = "organization1";
    String locationName1     = "office1";
    String organizationName2 = "organization2";
    String locationName2     = "office2";

    @Feature("Screen Navigation ProAcct IM-7.0") // It's a folder/component name to make test suite more readable from Jira
                                                                      // Test Case.
    @Story("PRJCBUGEN_T34369") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to Verify Organisation Count is showing correct") // It's
                                                                                                                             // a
                                                                                                                             // testcase
                                                                                                                             // title
                                                                                                                             // from
                                                                                                                             // Jira
    // Test Case.
    @TmsLink("PRJCBUGEN-T34369") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p3") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        OrganizationPage page = new OrganizationPage();
        new OrganizationPage().deleteOrganizationNew(organizationName1);
//        MyCommonAPIs.sleepi(2);
//        new OrganizationPage().deleteOrganizationNew(organizationName2);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login With Pro Account IM WP success;")
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
    
//    @Step("Test Step 3: Create an organization2")
//    public void step3() {
//        Map<String, String> organizationInfo = new HashMap<String, String>();
//        organizationInfo.put("Name", organizationName2);
//        OrganizationPage OrganizationPage = new OrganizationPage();
//        OrganizationPage.addOrganization(organizationInfo);
//        MyCommonAPIs.sleepi(5);
//    }
    

    @Step("Test Step 5: verify Organization Count is Correct")
    public void step5() {
        
        assertTrue(new OrganizationPage().VerifyOrgCountisShowingCorrect(), "Organization Count is Not Correct");
    }

}
