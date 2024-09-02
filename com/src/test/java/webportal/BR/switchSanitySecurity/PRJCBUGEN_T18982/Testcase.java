package webportal.BR.switchSanitySecurity.PRJCBUGEN_T18982;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
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

    @Feature("Switch Security Sanity") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T18982") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Rename switch") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T18982") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage(false).editswitchName( WebportalParam.sw1serialNo, WebportalParam.sw1deveiceName);
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccountswitch();
    }
    
    @Step("Test Step 2: Set new name and click on ok and new name should be set on Switch;")
    public void step2() {
        
        
        new WiredQuickViewPage().checkswitchIsExist(WebportalParam.sw1deveiceName);
        
        new WirelessQuickViewPage(false).editswitchName(WebportalParam.sw1serialNo, "apwp14280");
        
        MyCommonAPIs.sleepsync();
       
        assertTrue(new WirelessQuickViewPage(false).checkswitchNameIsExist(WebportalParam.sw1serialNo, "apwp14280"), "Device name updated unsuccessfully.");
        
        
    }
    
}


