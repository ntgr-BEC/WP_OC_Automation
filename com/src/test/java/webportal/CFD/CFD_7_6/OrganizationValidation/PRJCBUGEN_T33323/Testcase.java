package webportal.CFD.CFD_7_6.OrganizationValidation.PRJCBUGEN_T33323;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.$$x;
import static org.testng.Assert.assertFalse;
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
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.webelements.AccountPageElement;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.GenericMethods;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author RaviShankar S
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("Delete_Organization") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T33323") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify Whether we are able to delete Location or not") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T33323") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod
    public void afterMethod()
    {
        new OrganizationPage().deleteOrganization("Netgearnew");
    }
              
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
    }
    
    
    @Step("Test Step 2: Create Organization and Location ")
    public void step2() {
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", "Netgearnew");

        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrganization(organizationInfo);
        OrganizationPage.openOrg("Netgearnew");
                     
        HashMap<String, String> locationInfo = new HashMap<String, String>();      
        locationInfo.put("Location Name", "officenew");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "32003");
        locationInfo.put("Country", "United States of America");
        new AccountPage(false).addNetwork(locationInfo);              
        MyCommonAPIs.sleepi(15);
                            
    }
    
    @Step("Test Step3: Assert the Deletion")
    public void step3() {
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.openOrg("Netgearnew");
        new AccountPageElement().deleteLocation("officenew");
        assertTrue(!GenericMethods.checkVisibleElements($$x("//*[text()='officenew']")),"Location Not Deleted");
                

    }
  
}
