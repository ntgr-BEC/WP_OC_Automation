package webportal.ScheduledWiFi.Manager.PRJCBUGEN_T15931;

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
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Pragya
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("ScheduledWiFi.Manager") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T15931") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the \"pick start time\" functionality while selecting days for wifi schedule on the wireless dashboard on web portal as Manager") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T15931") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().deleteSsidYes("apwp15931");
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


    @Step("Test Step 2: Add WIFI ssid and now click on statpick time.")
    public void step2() {
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("SSID", "apwp15931");
        locationInfo.put("Security", "WPA/WPA2-PSK");
        locationInfo.put("Password", "123456798");
        locationInfo.put("Schedule Name", "scheduled15931");
        new WirelessQuickViewPage().pickStartTime(locationInfo);
        
    }

    

}
