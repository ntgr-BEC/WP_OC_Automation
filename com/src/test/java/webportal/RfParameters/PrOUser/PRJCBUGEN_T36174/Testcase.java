package webportal.RfParameters.PrOUser.PRJCBUGEN_T36174;

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
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Ravishankar S
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("RF Paramater") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T36174") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify,Beacon iNTERVAL VALUE RANGES BETWEEN 100 TO 300") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T36174") // It's a testcase id/link from Jira Test Case.

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


    
 
    @Step("Test Step 2: OBSERVE DTIM INTERVAL VALUE;")
    public void step2() {
        
      new WirelessQuickViewPage().GoToWirelessSettings();
        MyCommonAPIs.sleepi(10);
        assertTrue(new WirelessQuickViewPage(false).setBeaconvalue("3000","1"),"error message is not available");
        MyCommonAPIs.sleepi(10);
        assertTrue(new WirelessQuickViewPage(false).setBeaconvalue("250","1"),"error message is not available");
                        
          }
    
}
   


