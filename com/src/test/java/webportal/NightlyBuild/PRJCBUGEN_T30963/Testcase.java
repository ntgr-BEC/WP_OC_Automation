package webportal.NightlyBuild.PRJCBUGEN_T30963;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
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
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;
import webportal.param.WebportalParam;
import webportal.webelements.WirelessQuickViewElement;

/**
 *
 * @author Tejeshwini   K V
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("NightlyBuild") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T30963") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, EEM can be enabled and is effective only for supported FW on WAC500 and WAX600 series") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T30963") // It's a testcase id/link from Jira Test Case.

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
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }
    
    @Step("Test Step 2: Enable EEM and check whether it is push to AP")
    public void step2() {
        new WirelessQuickViewPage().GoToEEM();
        new WirelessQuickViewPage(false).EnableEEM();        
        MyCommonAPIs.sleepi(10);
        new WirelessQuickViewPage(false).disableEEM();
    }

}
