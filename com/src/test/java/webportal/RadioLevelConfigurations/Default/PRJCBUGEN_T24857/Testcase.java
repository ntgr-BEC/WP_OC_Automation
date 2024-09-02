package webportal.RadioLevelConfigurations.Default.PRJCBUGEN_T24857;

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
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshiwni K V
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("RadioLevelConfigurations") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T24857") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("wireless mode as ng for 2.4ghz  at location") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T24857") // It's a testcase id/link from Jira Test Case.

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

    
    @Step("Test Step 2: configured to 11a for 5 GHz;")
    public void step2() {
        
       String Num = "1";
        
        new WirelessQuickViewPage().GoToWirelessSettings();
        MyCommonAPIs.sleepi(10);
          
//        new WirelessQuickViewPage(false).Enable24(Num);
            
        
        
        String mode = "11ng";
        new WirelessQuickViewPage(false).Radiomode24.selectOption(mode);
        assertTrue(new WirelessQuickViewPage(false).RadioMode2ghz(mode), "default value is not right");
        
    }
}
