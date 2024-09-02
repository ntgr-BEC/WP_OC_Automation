package webportal.Deallocation.PRJCBUGEN_T29931;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

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
    
    
    String organizationName1 = "PRJCBUGEN_T29931";
    OrganizationPage page = new OrganizationPage();

    @Feature("Deallocation") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T29931") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that credit deallocated is reflected on screen.") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T29931") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new OrganizationPage().deleteOrganizationNew(organizationName1);
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
    
    
    @Step("Test Step 3: allocate credit")
    public void step3() {  
        
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addAllocateCredits(organizationName1, "2", "0", "0");
        
        
        HashMap<String, String> creditsInfo = new HamburgerMenuPage().getCreditAllocationStatus(organizationName1);
        assertTrue(
                creditsInfo.get("Used Devices Credits").equals("0") && creditsInfo.get("Unused Devices Credits").equals("2")
                        && creditsInfo.get("Used ICP Credits").equals("0") && creditsInfo.get("Unused ICP Credits").equals("0"),
                "Allocate credits error.");
        
       
    }

    @Step("Test Step 4: deallocate credit")
    public void step4() {  
        
        
        new HamburgerMenuPage().deallocateCredit(organizationName1, "2", "0");
        
        HashMap<String, String> creditsInfo = new HamburgerMenuPage().getCreditAllocationStatus(organizationName1);
        
        assertTrue(
                creditsInfo.get("Used Devices Credits").equals("0") && creditsInfo.get("Unused Devices Credits").equals("0")
                        && creditsInfo.get("Used ICP Credits").equals("0") && creditsInfo.get("Unused ICP Credits").equals("0"),
                "Allocate credits error.");
    }
    

}
