package webportal.NightlyBuild.PRJCBUGEN_T12362;

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
import util.APUtils;
import util.Javasocket;
import util.MyCommonAPIs;
import util.RunCommand;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    Map<String, String> locationInfo = new HashMap<String, String>();

    @Feature("NightlyBuild") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T12362") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify Edit of ORG WPA2 Enterprise Security as pro admin and connect to a clinet ") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T12362") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        new OrganizationPage(false).goToOrgSsid(WebportalParam.Organizations);
        new WirelessQuickViewPage(false).deleteSsidYes("apwp12362");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);

        handle.gotoLoction();
    }

    @Step("Test Step 2: Add organizationWide SSID")
    public void step2() {
        
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        new OrganizationPage(false).goToOrgSsid(WebportalParam.Organizations);
        locationInfo.put("SSID", "apwp12362");
        locationInfo.put("Security", "WPA2 Enterprise");
        new OrganizationPage(false).CreateOrgSSId(locationInfo);  
    
    }
    
  
}
