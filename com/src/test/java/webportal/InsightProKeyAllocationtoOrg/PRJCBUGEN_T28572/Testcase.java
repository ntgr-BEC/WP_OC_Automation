package webportal.InsightProKeyAllocationtoOrg.PRJCBUGEN_T28572;

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
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    
    String organizationName1 = "PRJCBUGEN_T28572";
    OrganizationPage page = new OrganizationPage();

    @Feature("InsightProKeyAllocationtoOrg") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T28572") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify if any single device credit is used from any LMS key and some of the credits are free then In \"Allocate Additional credit from keys\" list doesn't show the LMS key.s") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T285672") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
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
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);


    }
    
    @Step("Test Step 2: create Organization;")
    public void step2() {  
        page.gotoPage();
        page.addOrganization(organizationName1, "2");
       
    }

    @Step("Test Step 3: add license key ;")
    public void step3() {  
        
        String Key = new HamburgerMenuPage(false).readLicenceKeyByTxt("Write");
        System.out.println(Key);  
        String typeofOrg = "Account";
        int value=0;
        new HamburgerMenuPage().AddKeyAndVerify1(Key, typeofOrg, value);
//        assertTrue(new HamburgerMenuPage(true).verify(Key), "Not received verify email.");
    }

    @Step("Test Step 4:Allocate Credit ;")
    public void step4() {  
        OrganizationPage OrganizationPage = new OrganizationPage();
        
  
        
        assertTrue(OrganizationPage.addAllocateKeycheck(organizationName1, "1"), "it is disabled");
        
        
       
    }
    
    
   

}
