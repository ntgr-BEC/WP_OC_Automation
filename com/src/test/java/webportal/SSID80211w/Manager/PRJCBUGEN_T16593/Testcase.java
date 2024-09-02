package webportal.SSID80211w.Manager.PRJCBUGEN_T16593;

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
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Pragya
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("SSID80211w.Manager") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T16593") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the 802.11w SSID option should not be visible in case of an open SSID created by the user.Manager") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T16593") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().deleteSsidYes("apwp16593");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.managerName, WebportalParam.managerPassword);
        if (new HamburgerMenuPage(false).closedevicecredits.exists()) {
            new HamburgerMenuPage(false).closedevicecredits.click();
        }
        
        
        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInNormalAccount("manager"); // must input admin or manager
    }



    @Step("Test Step 2: Check default scheduled wifisettings.")
    public void step2() {
        Map<String, String> ssidInfo = new HashMap<String, String>();
        ssidInfo.put("SSID", "apwp16593");
        ssidInfo.put("Security", "Open");
        new WirelessQuickViewPage().addSsid(ssidInfo);
        new WirelessQuickViewPage().openSSID80211wsetting(ssidInfo);

    }


}
