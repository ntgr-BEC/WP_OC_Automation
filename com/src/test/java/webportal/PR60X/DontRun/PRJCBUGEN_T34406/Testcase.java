package webportal.PR60X.DontRun.PRJCBUGEN_T34406;

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
import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.DevicesApSummaryPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.PRDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("PR60X.Sanity") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T34406") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to perform the device's factory reset.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T34406") // It's a testcase id/link from Jira Test Case.

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
        new DevicesDashPage().checkDeviceInAdminAccountGen(WebportalParam.pr1serialNo, WebportalParam.pr1macaddress, WebportalParam.pr1deveiceName);
    }

    @Step("Test Step 2: Delete PRX;")
    public void step2() {
        
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.pr1serialNo);
        new DevicesApSummaryPage().clickReset();
        
        

        assertTrue(new DevicesApSummaryPage().checkAlertIsExist(), "Ap cannot reset");

        int count = 0;
        while (count < 5) {
            MyCommonAPIs.sleepsync();
            count += 1;
        }
        
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.pr1serialNo);
        devInfo.put("Device Name", WebportalParam.pr1deveiceName);
        new DevicesDashPage().addNewDevice(devInfo);

        new DevicesDashPage().waitDevicesReConnected(WebportalParam.pr1serialNo);

        assertTrue(new WirelessQuickViewPage().checkApIsOnline(WebportalParam.pr1serialNo));
        
    }

}
