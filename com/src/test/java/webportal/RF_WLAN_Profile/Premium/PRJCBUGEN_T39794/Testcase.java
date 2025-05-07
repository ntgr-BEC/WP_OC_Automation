package webportal.RF_WLAN_Profile.Premium.PRJCBUGEN_T39794;

import static com.codeborne.selenide.Selenide.$x;
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
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author  Pratik
 *
 */
public class Testcase extends TestCaseBase {
    
    Map<String, String> ssidInfo = new HashMap<String, String>();

    @Feature("RF_WLAN_Profile.Premium") // It's a folder/component name to make test suite more readable from Jira Test Case
    @Story("PRJCBUGEN_T39794") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("In Location Level SSID, we should add \"Customer profile\" column, here we will show the Customer profile name mapped to this SSID.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T39794") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().deleteSsidYes("PRJCBUGEN_T39794");
        System.out.println("start to do tearDown");

    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
       
    }

    @Step("Test Step 2: Delete device and enable IGMP")
    public void step2() {
           
        ssidInfo.put("SSID", "PRJCBUGEN_T39794");
        ssidInfo.put("Security", "WPA2 Personal Mixed");
        ssidInfo.put("Password", "123456798");
        new WirelessQuickViewPage().addSsid1(ssidInfo);
       
    }
    
    
    @Step("Test Step 3: Enable customer profile")
    public void step3() {
        ssidInfo.put("custom", "enable");
        new WirelessQuickViewPage().checkRateLimitARSTabsVisible(ssidInfo);
    }

    @Step("Test Step 4: verify Customer Profile and Captive Portal is visible after enabling customer profile")
    public void step4() {

        MyCommonAPIs.sleepi(10);
        boolean result = false;
        if ($x("//th[text()='Customer Profile']").isDisplayed() && $x("//td[text()='Savant']").isDisplayed() && $x("//th[text()='Captive Portal']").isDisplayed()) {
            result = true;
        }

        assertTrue(result, "Customer Profile and Captive Portal is not visible after enabling customer profile");

    }

}
