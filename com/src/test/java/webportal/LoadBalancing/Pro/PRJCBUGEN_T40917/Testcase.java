package webportal.LoadBalancing.Pro.PRJCBUGEN_T40917;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.CopyConfigurationPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @Pratik
 *
 */
public class Testcase extends TestCaseBase {

    Map<String, String> cfgInfo = new HashMap<String, String>();

    @Feature("LoadBalancing") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T40917") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify disassociate sticky client is pushed to AP or not") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T40917") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
        
        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccount();
    }

    @Step("Test Step 2: Verify Disassociated sticky client enable switch is pushed to AP;")
    public void step2() {
        new WirelessQuickViewPage().gotoLoadBalancingPage();
        new WirelessQuickViewPage(false).enablecheckDisassociateSickyClients();
        assertTrue(new APUtils(WebportalParam.ap1IPaddress).getLoadBalancingDisassociateStickeyClients(WebportalParam.ap1Model),"Verified Disassociated sticky client enable switch is not pushed to AP");
        new WirelessQuickViewPage(false).enablecheckDisassociateSickyClients();
    }

}
