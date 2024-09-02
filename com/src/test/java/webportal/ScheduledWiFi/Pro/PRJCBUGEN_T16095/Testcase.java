package webportal.ScheduledWiFi.Pro.PRJCBUGEN_T16095;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author pragya
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("ScheduledWiFi.Pro") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T16095") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether Config Backup works for WiFi Schedule.Pro") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T16095") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new AccountPage().mainpage.click();
        handle.gotoLoction();
        new WirelessQuickViewPage().deleteSsidYes("apwp16095");
        System.out.println("start to do tearDown");
    }

 // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

        handle.gotoLoction();
 //       new DevicesDashPage().checkDeviceInNormalAccount("admin"); // must input admin or manager
    }


    @Step("Test Step 2: Add WIFI And Config Backup for WiFi Schedule;")
    public void step2() {
        AccountPage AccountPage = new AccountPage();
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("SSID", "apwp16095");
        locationInfo.put("Security", "WPA/WPA2-PSK");
        locationInfo.put("Password", "123456798");
        locationInfo.put("Name", "apwp16095");
        locationInfo.put("Description", "apwp16095");
        new WirelessQuickViewPage().addSsid(locationInfo);
        new AccountPage().mainpage.click();
        AccountPage.orgpage.click();
        AccountPage.ssidconfigbackupLocation(WebportalParam.location1,locationInfo);
        
    }
}