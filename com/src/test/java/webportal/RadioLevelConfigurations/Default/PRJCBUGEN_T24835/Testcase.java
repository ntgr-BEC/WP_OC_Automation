package webportal.RadioLevelConfigurations.Default.PRJCBUGEN_T24835;

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
    @Story("PRJCBUGEN_T24835") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify  default radio mode is ac in wac in 5high and 5 low  radio  for WAC") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T24835") // It's a testcase id/link from Jira Test Case.

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

    
    @Step("Test Step 2: Check 5Ghz High radio status  is enable By WAC3;")
    public void step2() {
        
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);
        new WirelessQuickViewPage(false).RadioAndChannels.click();
        MyCommonAPIs.sleepi(10); 
        new WirelessQuickViewPage(false).DropDown5GhzHigh.click();        
        
        String modeWAx = new WirelessQuickViewPage(false).Radiomode5high.getText();
        System.out.println(modeWAx);
        assertTrue(modeWAx.equals("11ac"), "default value is not right");
        
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap2serialNo);
        new WirelessQuickViewPage(false).RadioAndChannels.click();
        MyCommonAPIs.sleepi(10); 
        new WirelessQuickViewPage(false).DropDown5GhzLow.click(); 
        
        String mode = new WirelessQuickViewPage(false).Radiomode5low.getText();
        System.out.println(mode);
        assertTrue(mode.equals("11ac"), "default value is not right");
    }
}