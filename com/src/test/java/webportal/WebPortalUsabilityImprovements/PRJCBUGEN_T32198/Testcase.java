package webportal.WebPortalUsabilityImprovements.PRJCBUGEN_T32198;

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
import webportal.weboperation.SummaryPage;

/**
 *
 * @author Vivek Singh
 *
 */
public class Testcase extends TestCaseBase {
    String organizationName1 = "organization1";
    String locationName = "Office1";
 

    @Feature("IM-6.10-Web Portal Usability Improvements") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T32198") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that user should able to click on verticle button and all menu should be displayed on vertically..") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T32198") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
  

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        OrganizationPage page = new OrganizationPage();
        page.deleteOrganizationNew(organizationName1);
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
        OrganizationPage.openOrg(organizationName1);

        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", locationName);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "United States of America");
        new AccountPage(false).addNetwork(locationInfo);
        MyCommonAPIs.sleepi(5);
        
    }
    
    
    @Step("Test Step 3: Going to summary page and openCustomizeSettings and Enable VerticalMenu")
    public void step3() {
        new SummaryPage().gotoPage(locationName);
        new SummaryPage().openCustomizeSettings();
        new SummaryPage().enableVerticalMenu();
        MyCommonAPIs.sleepi(2); 
        
    }
    

    @Step("Test Step 5: Verify All Menu Items Are Present")
    public void step5() {
        assertTrue(new SummaryPage().verifyAllMenuItemsArePresent()," All Menu Items are Not Present");
        new SummaryPage().closeTheMenuAndDisableTheVerticalMenuSwitch();
        
    }
    
   
    }
