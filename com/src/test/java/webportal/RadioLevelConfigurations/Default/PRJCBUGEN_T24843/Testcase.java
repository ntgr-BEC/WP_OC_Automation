package webportal.RadioLevelConfigurations.Default.PRJCBUGEN_T24843;

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
    @Story("PRJCBUGEN_T24843") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify, radio mode for 2.4 GHz has  11b, 11bg, 11ng, 11ax for WAX AP") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T24843") // It's a testcase id/link from Jira Test Case.

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

    
    @Step("Test Step 2: configured to 11a for 2 GHz;")
    public void step2() {
        
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap3serialNo);
        new WirelessQuickViewPage(false).RadioAndChannels.click();
        MyCommonAPIs.sleepi(10);
        
        
        String mode = "11b";
        String mode1 = "11bg";
        String mode2 = "11ng";
        String mode3 = "11ax";
        new WirelessQuickViewPage(false).Radiomode24.selectOption(mode);
        assertTrue(new WirelessQuickViewPage(false).RadioMode2ghz(mode), "default value is not right");
        
        
        new WirelessQuickViewPage(false).Radiomode24.selectOption(mode1);
        assertTrue(new WirelessQuickViewPage(false).RadioMode2ghz(mode1), "default value is not right");
        
        new WirelessQuickViewPage(false).Radiomode24.selectOption(mode2);
        assertTrue(new WirelessQuickViewPage(false).RadioMode2ghz(mode2), "default value is not right");
        
        
        new WirelessQuickViewPage(false).Radiomode24.selectOption(mode3);
        assertTrue(new WirelessQuickViewPage(false).RadioMode2ghz(mode3), "default value is not right");
        

    }
    
   

}
