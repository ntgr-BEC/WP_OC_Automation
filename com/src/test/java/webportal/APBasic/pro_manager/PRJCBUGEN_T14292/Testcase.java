package webportal.APBasic.pro_manager.PRJCBUGEN_T14292;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("APBasic.pro_manager") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14292") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify whether user is able to set AP name for pro manager") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14292") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        MyCommonAPIs.sleepsync();
        new WirelessQuickViewPage().editApName(WebportalParam.ap1serialNo, WebportalParam.ap1deveiceName);
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.managerName, WebportalParam.managerPassword);

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInNormalAccount("manager"); // must input admin or manager
    }

    @Step("Test Step 2: Set new name and click on ok and new name should be set on AP;")
    public void step2() {
        new WirelessQuickViewPage().editApName(WebportalParam.ap1serialNo, "apwp14292");
        MyCommonAPIs.sleepsync();
        assertTrue(new WirelessQuickViewPage().checkApNameIsExist(WebportalParam.ap1serialNo, "apwp14292"), "Device name updated unsuccessfully.");
    }

    @Step("Test Step 3: Verify device name updated successfully;")
    public void step3() {
        assertTrue(new APUtils(WebportalParam.ap1IPaddress).getDeviceName().contains("apwp14292"), "Device name updated unsuccessfully.");
    }

}
