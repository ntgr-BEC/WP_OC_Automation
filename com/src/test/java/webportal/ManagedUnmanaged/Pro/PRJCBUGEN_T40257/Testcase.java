package webportal.ManagedUnmanaged.Pro.PRJCBUGEN_T40257;

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
import webportal.weboperation.AccountPage;
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
    
    int temp;
    int temp1;
    int temp2;
    
    @Feature("ManagedUnmanaged") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T40257") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("[PRO]  Test to verify the user can enable the managed toggle button then device credit moves to the used state.") // It's a testcase title from Jira Test
                                                                                                          // Case.
    @TmsLink("PRJCBUGEN_T40257") // It's a testcase id/link from Jira Test Case.

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
    }

    @Step("Test Step 2:Verify Managed Unmanaged switch on device dash page is working fine;")
    public void step2() {
        HashMap<String, String> creditsInfo = new HamburgerMenuPage().getCreditAllocationStatus(WebportalParam.Organizations);
        assertTrue(
                creditsInfo.get("Used Devices Credits").equals("4") && creditsInfo.get("Unused Devices Credits").equals("0"),
                "Allocate credits error.");
        
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        new MyCommonAPIs().gotoLoction(WebportalParam.location1);
        assertTrue(new DevicesDashPage().disablemanagedSwitch(WebportalParam.ap5serialNo));
        MyCommonAPIs.sleepi(5);
        
        HashMap<String, String> creditsInfo1 = new HamburgerMenuPage().getCreditAllocationStatus(WebportalParam.Organizations);
        assertTrue(
                creditsInfo1.get("Used Devices Credits").equals("3") && creditsInfo1.get("Unused Devices Credits").equals("1"),
                "Allocate credits error.");
        
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        new MyCommonAPIs().gotoLoction(WebportalParam.location1);
        new DevicesDashPage().enablemanagedUnmanagedSwitch(WebportalParam.ap5serialNo);
        
        HashMap<String, String> creditsInfo2 = new HamburgerMenuPage().getCreditAllocationStatus(WebportalParam.Organizations);
        assertTrue(
                creditsInfo2.get("Used Devices Credits").equals("4") && creditsInfo2.get("Unused Devices Credits").equals("0"),
                "Allocate credits error.");
    }

}
