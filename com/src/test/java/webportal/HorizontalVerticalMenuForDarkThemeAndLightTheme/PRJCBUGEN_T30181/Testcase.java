package webportal.HorizontalVerticalMenuForDarkThemeAndLightTheme.PRJCBUGEN_T30181;

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
import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DeviceScreenNavigationPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.SummaryPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author Anusha H
 *
 */
public class Testcase extends TestCaseBase {

    Random r        = new Random();
    int    num      = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num);

    @Feature("HorizontalVerticalMenuForDarkThemeAndLightTheme") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T30181") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that sub menu should be displayed into the drop down option of main menu.") // It's a testcase title
                                                                                                                 // from Jira Test Case.
    @TmsLink("PRJCBUGEN_T30181") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new SummaryPage().closeTheMenuAndDisableTheVerticalMenuSwitch();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create IM WP account in Australia success;")
    public void step1() {
 
            WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
            webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
            
            handle.gotoLoction();
            
    }

    @Step("Test Step 2: Switch to Vertical Menu through flash icon present on the right side of the screen ;")
    public void step2() {
        
        new SummaryPage().openCustomizeSettings();
        new SummaryPage().enableVerticalMenu();
        
        assertTrue(new  DeviceScreenNavigationPage().verifyVerticalDashboard(),"The menu is switched to vertical but tabs are not displayed properly ");
        MyCommonAPIs.sleepi(5);
        assertTrue(new  DeviceScreenNavigationPage().verifySubVerticalDashboard(),"Inner sub menu is not displayed properly");
        
    }
    
}
