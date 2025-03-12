package webportal.MDNS.prouser.PRJCBUGEN_T31105;

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
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.MDNSPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author  Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    HashMap<String, String> BackupInfo = new HashMap<String, String>();

    @Feature("MDNS") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T31105") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify mDNS shows has the \"Discovered Services\" with a refresh button") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T31105") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new MDNSPage(false).disableMDNS();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
        new HamburgerMenuPage(false).closeLockedDialog();
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);

        handle.gotoLoction();
        // new DevicesDashPage().checkDeviceInAdminAccount();
    }

    @Step("Test Step 2: check MDNS")
    public void step2() {
        new MDNSPage(false).checkaddSsid();
        assertTrue(new MDNSPage().DiscoveredServicesCheck(),"is ot disabled by default");
    }
    
  
       

}
