package webportal.SearchFilterChooseColumn.PRJCBUGEN_T14657;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {
    
    private static final String system = null;
    String name1 = "TEST1";
    String name2 = "TEST2";

    @Feature("SearchFilterChooseColumn") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14657") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify Device sort functionality") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14657") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
		System.out.println("start to do tearDown");
        new DevicesDashPage().editDeviceName(name1, WebportalParam.sw1deveiceName);
        new DevicesDashPage().editDeviceName(name2, WebportalParam.ap1deveiceName);

        
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.loginName, WebportalParam.loginPassword);

        handle.gotoLoction();
    }

    @Step("Test Step 2: Check devices table sort;")
    public void step2() {
        if (new DevicesDashPage().getDeviceName(WebportalParam.ap1serialNo).equals("")
                && new DevicesDashPage().getDeviceName(WebportalParam.sw1serialNo).equals("")) {
            assertTrue(false, "Need add one ap and one switch.");
        }
       
        new DevicesDashPage().editDeviceName(WebportalParam.sw1serialNo, name1);
        new DevicesDashPage().editDeviceName(WebportalParam.ap1serialNo, name2);

        new DevicesDashPage().waitDevicesReConnected(WebportalParam.sw1serialNo);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);

        boolean result = true;

        if (new DevicesDashPage().checkSortDevicesName(name1, name2)) {
            new DevicesDashPage().clickSortDevicesName();

            if (new DevicesDashPage().checkSortDevicesName(name1, name2)) {
            } else {
                result = false;
            }
        } else {
            result = false;
        }

        assertTrue(result, "Devices table sort is incorrect.");
    }

}
