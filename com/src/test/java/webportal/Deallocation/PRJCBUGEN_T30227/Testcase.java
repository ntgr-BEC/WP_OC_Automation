package webportal.Deallocation.PRJCBUGEN_T30227;

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
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    OrganizationPage OrganizationPage = new OrganizationPage();
    String organizationName1 = "PRJCBUGEN_T30227";
    HashMap<String, String> locationInfo     = new HashMap<String, String>();
    String                  locationName     = "PRJCBUGEN_T30227";
    Map<String, String>     devInfo          = new HashMap<String, String>();

    @Feature("Deallocation") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T30227") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that user should able to Add purchase subscription key on organisation.") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T30227") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on prior
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

    @Step("Test Step 2: Add license key at account level and verify data are correct ;")
    public void step2() {  
        
        String Key = new HamburgerMenuPage(false).readLicenceKeyByTxt("Write");
        System.out.println(Key);  
        String typeofOrg = "Account";
        int value=0;
        new HamburgerMenuPage().AddKeyAndVerify1(Key, typeofOrg, value);
        assertTrue(new HamburgerMenuPage(true).verify(Key), "Not received verify email.");
       
    }
    
    
    @Step("Test Step 3: Add license key again")
    public void step3() {  
        
        String Key1 = new HamburgerMenuPage(false).readLicenceKeyByTxt("Write");
        System.out.println(Key1);        
        String typeofOrg = "";
        int value=0;
        new HamburgerMenuPage().AddKeyAndVerify1(Key1, typeofOrg, value);
        assertTrue(new HamburgerMenuPage(true).verifyOrg(Key1, WebportalParam.Organizations), "Not received verify email.");
       
    }
    
    
    
 
}
