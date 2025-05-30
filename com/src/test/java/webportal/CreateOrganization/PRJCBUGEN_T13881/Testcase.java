package webportal.CreateOrganization.PRJCBUGEN_T13881;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    String organizationName1 = "PRJCBUGEN_T138811";
    String organizationName2 = "PRJCBUGEN_T138812";

    @Feature("CreateOrganization") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T13881") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that the user is able to Create single / Multiple locations under the organization") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T13881") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        OrganizationPage page = new OrganizationPage();
        page.deleteOrganizationNew(organizationName1);
        page.deleteOrganizationNew(organizationName2);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to Insight Webportal")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
    }

    @Step("Test Step 2:  to Create single locations under the organization")
    public void step2() {
        OrganizationPage page = new OrganizationPage();
        page.gotoPage();
        MyCommonAPIs.sleepi(5);
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName1);
        page.addOrganization(organizationInfo);
        MyCommonAPIs.sleepi(5);
        List<String> Loclist=new ArrayList<String>();  
        int totalLocationsrequired = 1;
        int created = 0;
        new OrganizationPage(false).openOrg(organizationName1);
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "office1");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "70184");
        locationInfo.put("Country", "Germany");
        new AccountPage(false).addNetwork(locationInfo);
    }

    @Step("Test Step 3:  to Create multiple locations under the organization")
    public void step3() {
        OrganizationPage page = new OrganizationPage();
        page.gotoPage();
        MyCommonAPIs.sleepi(5);
        Map<String, String> organizationInfo1 = new HashMap<String, String>();
        organizationInfo1.put("Name", organizationName2);
        page.addOrganization(organizationInfo1);
        MyCommonAPIs.sleepi(5);
        List<String> Loclist=new ArrayList<String>();  
        int totalLocationsrequired = 1;
        int created = 0;
        new OrganizationPage(false).openOrg(organizationName1);
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "office1");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "70184");
        locationInfo.put("Country", "Germany");
        new AccountPage(false).addNetwork(locationInfo);
    }
    
}
