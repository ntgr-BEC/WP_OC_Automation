package webportal.POEOverride.PRJCBUGEN_T42330;

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
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesApSummaryPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("POEOverride") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T42330") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("change Power  setting   to  AT from  Auto then  reboot AP") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T42330") // It's a testcase id/link from Jira Test Case.

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
        new DevicesDashPage().checkDeviceInAdminAccount();
    }

    @Step("Test Step 2: Go to AP settings and verify power settings tab; verify power settings drop down options. ")
    public void step2() {
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);
        MyCommonAPIs.sleepi(5);
        String powerMode = "802.3at";
        assertTrue(new WirelessQuickViewPage(false).changePowerModeFromAutomaticToAnymode(powerMode),"Power mode not correctly selected."); 
    }
    
    @Step("Test Step 3: Reboot Ap and verify AF power setting is applied or not. ")
    public void step3() {
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);
        new DevicesApSummaryPage().clickReboot();
        MyCommonAPIs.sleepi(180);
        String powerMode = "802.3at";
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);
        assertTrue(new WirelessQuickViewPage(false).verifyAndselectedpowerOptionIsvisbleOrNot(powerMode),"Power Setting options are not visible on power settings page");
        MyCommonAPIs.sleepi(5);
        String defaultpowerMode = "Automatic";
        assertTrue(new WirelessQuickViewPage(false).changePowerModeToAutomatic(defaultpowerMode),"Power mode not correctly selected.");       
        
    }

}
