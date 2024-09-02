package webportal.ClientIsolationWithDLAN.PRJCBUGEN_T21421;

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
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
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
    
    Map<String, String> locationInfo = new HashMap<String, String>();
    

    @Feature("ClientIsolationWithDLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T21421") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify if the Allowed access to AP UI, is cleared from the Insight & from the device when Client Isolation is disabled.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T21421") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new WirelessQuickViewPage().deleteSsidYes("apwp21421");

    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccount();
    }

    @Step("Test Step 2: Create SSID with Client Isolation enabled")
    public void step2() {
        
        locationInfo.put("SSID", "apwp21421");
        locationInfo.put("Security", "WPA2 Enterprise");
        new WirelessQuickViewPage().addSsid1(locationInfo);
        
        new WirelessQuickViewPage().enableclientIsolation(locationInfo.get("SSID"));
    }
    
 
    @Step("Test Step 3: Verify client enabled")
    public void step3() {
        
        assertTrue(new WirelessQuickViewPage().disableClientIsolation(locationInfo.get("SSID")),"");  

        
    }

}
