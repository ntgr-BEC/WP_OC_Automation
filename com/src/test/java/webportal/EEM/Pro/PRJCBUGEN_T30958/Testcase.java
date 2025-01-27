package webportal.EEM.Pro.PRJCBUGEN_T30958;

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
import util.MyCommonAPIs;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;
import webportal.param.WebportalParam;
import webportal.webelements.WirelessQuickViewElement;

/**
 *
 * @author tejeshwini   K V
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("EEM") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T30958") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, EEM option is at location level> wireless setting > Advanced> Network settings> below IGMP and B2UC we have option EEM") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T30958") // It's a testcase id/link from Jira Test Case.

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

        handle.gotoLoction();
    }
    
    @Step("Test Step 2: Check EEM option avilable")
    public void step2() {
        new WirelessQuickViewPage().GoToEEM();
        MyCommonAPIs.sleepi(5); 
        assertTrue(new WirelessQuickViewElement().EEM.isDisplayed() , "EEM is not displayed");
        
        
    }

}
