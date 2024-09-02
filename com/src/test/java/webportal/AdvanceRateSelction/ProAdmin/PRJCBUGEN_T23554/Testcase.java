package webportal.AdvanceRateSelction.ProAdmin.PRJCBUGEN_T23554;

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
    
   

    @Feature("AdvanceRateSelction_Proadmin") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T23554") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Density Level as 3 in 5 ghz") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T23554") // It's a testcase id/link from Jira Test Case.

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

        handle.gotoLoction();
//        new DevicesDashPage().checkDeviceInAdminAccount();
    }

    @Step("Test Step 2: Add WIFI ssid and now connect client to this ssid;")
    public void step2() {
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("SSID", "apwp21929");
        locationInfo.put("Security", "WPA2 Personal Mixed");
        locationInfo.put("Password", "123456798");
        new WirelessQuickViewPage().addSsid(locationInfo);
        
        int densitylevel = 3;
        new WirelessQuickViewPage().GoToAdvanceRateCntrol(locationInfo.get("SSID"));
        new WirelessQuickViewPage(false).clickon5Ghz();
        new WirelessQuickViewPage(false).enableSetMinimumRateControl();        
        new WirelessQuickViewPage(false).DragDensityTo5(densitylevel);
        new WirelessQuickViewPage().GoToAdvanceRateCntrol(locationInfo.get("SSID"));
        new WirelessQuickViewPage(false).clickon5Ghz();
        assertTrue(new WirelessQuickViewPage(false).Density0Text2Ghz(densitylevel), "Advanceratelimit was enabled by default");
        
    }

}
