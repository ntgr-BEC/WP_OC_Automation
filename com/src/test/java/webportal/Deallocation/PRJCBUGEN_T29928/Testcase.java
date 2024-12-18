package webportal.Deallocation.PRJCBUGEN_T29928;

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
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    
    String organizationName1 = "PRJCBUGEN_T28567";
    OrganizationPage page = new OrganizationPage();

    @Feature("Deallocation") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T29928") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that user should able to Add new organisation.") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T29928") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priori
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        page.deleteOrganizationNew(organizationName1);
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
    }

    @Step("Test Step 2: create Organization;")
    public void step2() {  
        page.gotoPage();
        page.addOrganization(organizationName1, "2");
       
    }
    @Step("Test Step 3:check ORg added or not")
    public void step3() { 

    assertTrue(page.checkOrganizationIsExist(organizationName1), "Og created ");
    }
    

}
