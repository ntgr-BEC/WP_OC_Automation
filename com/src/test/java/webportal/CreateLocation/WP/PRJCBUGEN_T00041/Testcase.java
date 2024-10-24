package webportal.CreateLocation.WP.PRJCBUGEN_T00041;

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
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    @Feature("ChangeLocationPassword") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T00041") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that the user is able to Change Location Password.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T00041") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new OrganizationPage().deleteOrganizationNew(WebportalParam.Organizations);
        System.out.println("start to do tearDown");
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to Insight Webportal")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrg(WebportalParam.Organizations); 
        
        HashMap<String, String> locationInfo = new HashMap<String, String>();      
        locationInfo.put("Location Name", WebportalParam.location1);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "China");
        new AccountPage().addNetwork(locationInfo);
        MyCommonAPIs.waitReady();
    }
    
    @Step("Test Step 2: Edit Location and Change Password")
    public void step2() {
        
        new AccountPage().editAndChangePasswordProaccLocation();
        assertTrue(new AccountPage().verifyProLocPassword(),"password is not updated successfully");
    }
}
