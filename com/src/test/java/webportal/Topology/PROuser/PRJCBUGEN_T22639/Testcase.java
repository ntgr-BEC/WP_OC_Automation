package webportal.Topology.PROuser.PRJCBUGEN_T22639;

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
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DeviceBulkOnboardingPage;
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
    
    Random              r           = new Random();
    int                 num         = r.nextInt(10000000);
    String              mailname    = "apwptest" + String.valueOf(num);

    @Feature("Topology") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T22639") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("VIew  full dashboard  for Topolgy") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T22639") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
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
        new HamburgerMenuPage(false).closeLockedDialog();
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);

        handle.gotoLoction();
    }
        
    @Step("Test Step 2: Create Location;")
    public void step2() {            
        
        new DeviceBulkOnboardingPage().GoToSummaryPage(WebportalParam.location1);
        assertTrue(new WirelessQuickViewPage(false).ViewDashboard(),  "did not landed in topology page");
        }
        

  


}
