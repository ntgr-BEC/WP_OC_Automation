package webportal.ICP.user.PRJCBUGEN_T14008;

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
import webportal.param.WebportalParam;
import webportal.weboperation.GenericMethods;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    Map<String, String> ssidInfo = new HashMap<String, String>();

    @Feature("InstantCaptivePortal.Configuration") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14008") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify all 3 type of captive portal are present under captive portal page") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14008") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new WirelessQuickViewPage().deleteSsidYes(ssidInfo.get("SSID"));
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.loginName, WebportalParam.loginPassword);

        handle.gotoLoction();
    }

    @Step("Test Step 2: Check captive portal page;")
    public void step2() {
        ssidInfo.put("SSID", "apwp14008");
        ssidInfo.put("Security", "WPA2 Personal");
        ssidInfo.put("Password", "12345678");
        new WirelessQuickViewPage().addSsid(ssidInfo);

        MyCommonAPIs.waitElement(new WirelessQuickViewPage(false).settingsorquickview);
        new WirelessQuickViewPage(false).settingsorquickview.click();
        MyCommonAPIs.waitReady();
        new WirelessQuickViewPage(false).clickEditSsid(ssidInfo.get("SSID"));
        new WirelessQuickViewPage(false).entercaptiveportal.click();
        MyCommonAPIs.sleepi(3);
        new WirelessQuickViewPage(false).enablecaptiveportal.click();
        MyCommonAPIs.sleepi(3);
        assertTrue(new WirelessQuickViewPage(false).selectfacebookwifi.exists() && new WirelessQuickViewPage(false).selectbasiccaptive.exists()
                && GenericMethods.checkVisibleElements(new WirelessQuickViewPage(false).selectinsightcaptiveportal), "Captive portal page display error.");
    }

}
