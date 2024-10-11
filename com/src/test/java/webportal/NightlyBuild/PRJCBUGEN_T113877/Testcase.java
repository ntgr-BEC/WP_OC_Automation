package webportal.NightlyBuild.PRJCBUGEN_T113877;

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
import webportal.param.WebportalParam;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 * @author Tejeshwini K V
 */
public class Testcase extends TestCaseBase {
    String organizationName = "PRJCBUGEN_T113877";
    Map<String, String> locationInfo = new HashMap<String, String>();
    Random r = new Random();
    int num = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num);
    String locationName     = "office";
    
    @Feature("NightlyBuild") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T113877") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that the user is able to create an ORG wide SSID WPA3 for existing Organization") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T113877") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new OrganizationPage(false).goToOrgSsid(WebportalParam.Organizations);
        new WirelessQuickViewPage(false).deleteOrgSsidYes(locationInfo.get("SSID"));
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to Insight Webportal")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
    }
    
    @Step("Test Step 2: Verify that the user is able to navigates to ORG page and creates ORG wide SSID")
    public void step2() {
        new OrganizationPage(false).openOrg(organizationName);
        new OrganizationPage(false).goToOrgSsid(organizationName);
        
        locationInfo.put("SSID", "PRJCBUGEN_T24664");
        locationInfo.put("Security", "WPA3 Personal");
        locationInfo.put("Password", "123456798");
        new OrganizationPage(false).CreateOrgSSId(locationInfo);    
    }
    
}
