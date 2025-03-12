package webportal.POEOverride.prouser.PRJCBUGEN_T42338;

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
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("POEOverride") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T42338") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("changepower setting  from  Auto  --> BT--> AF") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T42338") // It's a testcase id/link from Jira Test Case.

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
        // new DevicesDashPage().checkDeviceInAdminAccount();
    }

    @Step("Test Step 2: Go to AP settings and verify power settings tab; verify power settings drop down options. ")
    public void step2() {
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);
        MyCommonAPIs.sleepi(5);
        String powerMode = "802.3bt";
        assertTrue(new WirelessQuickViewPage(false).changePowerModeFromAutomaticToAnymode(powerMode),"Power mode not correctly selected.");   
        String powerMode1 = "802.3af";
        assertTrue(new WirelessQuickViewPage(false).changePowerModeFromAutomaticToAnymode(powerMode1),"Power mode not correctly selected.");  
        assertTrue(new DevicesDashPage(false).verifypowerModeonDevicedashPageAPstatus(powerMode1),"AP Status is not changed to Connected (PoE 802.3af only)");
        String defaultpowerMode = "Automatic";
        assertTrue(new WirelessQuickViewPage(false).changePowerModeToAutomatic(defaultpowerMode),"Power mode not correctly selected.");          
        
    }

}
