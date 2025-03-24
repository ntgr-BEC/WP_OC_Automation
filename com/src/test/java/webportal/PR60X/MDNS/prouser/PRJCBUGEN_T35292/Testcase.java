package webportal.PR60X.MDNS.prouser.PRJCBUGEN_T35292;

import static com.codeborne.selenide.Selenide.$x;
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
import webportal.weboperation.PRDashPage;
import webportal.webelements.MDNSElement;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;
import webportal.webelements.WirelessQuickViewElement;

/**
 *
 * @author  Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    HashMap<String, String> BackupInfo = new HashMap<String, String>();

    @Feature("PR60X.MDNS") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T35292") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify, user must select Gateway before mDNS toggle enabled") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T35292") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        MyCommonAPIs.sleepi(10);
        new MDNSPage().deletePolicy("MDNS");
        MyCommonAPIs.sleepi(5);
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
        new MDNSPage();
        MyCommonAPIs.sleepi(5);
        new MDNSPage().MDNSGateway.click();
        MyCommonAPIs.sleepi(5);
        boolean status = new MDNSPage().checkMDNS.isEnabled();
        assertTrue(status==false,"MDNS is not diabled before we select gateway");
        new PRDashPage().selectGateway.selectOption(WebportalParam.pr1deveiceName);
        boolean status1 = new MDNSPage().checkMDNS.isEnabled();
        assertTrue(status1==true,"MDNS is diabled before we select gateway");
    }
    
  
       

}
