package webportal.EEM.Mesh.PRJCBUGEN_T23071;

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
import util.APUtils;
import util.Javasocket;
import util.MyCommonAPIs;
import util.RunCommand;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author  Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    private static final String OnBoardingTest = null;

    @Feature("EEM") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T23071") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, When Antenna PS is enabled  for mesh configured AP, all but one antenna are turned off") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T23071") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage(false).disableEEM();
        System.out.println("start to do tearDown");
        
        
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInNormalAccount("admin");
    }

    @Step("Test Step 2: Enable IGMP;")
    public void step2() {
        
        new WirelessQuickViewPage().GoToEEM();
        new WirelessQuickViewPage(false).EnableEEM();
        MyCommonAPIs.sleepi(30);       
        assertTrue(new APUtils(WebportalParam.ap1IPaddress).getEEMEnableStatus(WebportalParam.ap1Model), "EEM is disabled");
                          
    }


}
