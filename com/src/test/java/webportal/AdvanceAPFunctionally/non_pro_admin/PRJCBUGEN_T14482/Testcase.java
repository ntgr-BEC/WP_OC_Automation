package webportal.AdvanceAPFunctionally.non_pro_admin.PRJCBUGEN_T14482;

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

    @Feature("AdvanceAPFunctionally.non_pro_admin") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14482") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify whether error message is shown or not when user set 11 url in url filter as non pro admin") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14482") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        String urls[] = {
                "www.rediff.com", "www.yahoo.com", "www.eeandu.net", "www.cricinfo.com", "www.yopmail.com", "www.outlook.com", "www.facebook.com",
                "www.linkin.com", "www.job.com", "www.cricket.com", "www.mail.com"
        };
        for (String url : urls) {
            new WirelessQuickViewPage().deleteBlacklistUrl(url);
        }
        new WirelessQuickViewPage().disableUrlFiltering();
        new WirelessQuickViewPage().deleteSsidYes("apwp14482");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.loginName, WebportalParam.loginPassword);

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccount();
    }

    @Step("Test Step 2: Add WIFI ssid and add 11 url to blacklist,check error message;")
    public void step2() {
        Map<String, String> ssidInfo = new HashMap<String, String>();
        ssidInfo.put("SSID", "apwp14482");
        ssidInfo.put("Security", "WPA2 Personal");
        ssidInfo.put("Password", "12345678");
        new WirelessQuickViewPage().addSsid(ssidInfo);

        String urls[] = {
                "www.rediff.com", "www.yahoo.com", "www.eeandu.net", "www.cricinfo.com", "www.yopmail.com", "www.outlook.com", "www.facebook.com",
                "www.linkin.com", "www.job.com", "www.cricket.com"
        };

        for (String url : urls) {
            new WirelessQuickViewPage().enableUrlFilteringAndAddUrl(url);
        }

        assertTrue(new WirelessQuickViewPage().checkBlacklistUrlLimitReached("www.mail.com"));
    }

}
