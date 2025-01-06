package webportal.advanced_instant_wifi.Premium.PRJCBUGEN_T33839;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
import util.RunCommand;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Vivek Singh
 *
 */
public class Testcase extends TestCaseBase {

    String              region       = "";
    Map<String, String> locationInfo = new HashMap<String, String>();
    Random              r            = new Random();
    int                 num          = r.nextInt(10000000);
    String              mailname     = "apwptest" + String.valueOf(num);

    @Feature("Advance Instant WIFI 6.10") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T33839") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify instant wifi restore default option for all channels through ssh is working") // It's a testcase title from Jira
                                                                                                               // Test Case.
    @TmsLink("PRJCBUGEN-T33839") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        // new AccountPage().deleteOneLocation("OnBoardingTest");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
    }

    @Step("Test Step 2: Verify Account have one Location and one AP in connected state ")
    public void step2() {
        handle.gotoLoction();
        assertTrue(new DevicesDashPage().isDeviceConnected(WebportalParam.ap1serialNo));

    }

    @Step("Test Step 3: Enable SSH and Create one SSID")
    public void step3() {
        new RunCommand().enableSSH4APALL(WebportalParam.ap1IPaddress);
        new WirelessQuickViewPage().deleteALLSSID();
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("SSID", "ap@9990");
        locationInfo.put("Band", "Both");
        locationInfo.put("Security", "WPA2 Personal");
        locationInfo.put("Password", "Netgear1@");
        new WirelessQuickViewPage().addSsid1(locationInfo);

    }

    @Step("Test Step 4: Login Tera Term and Go to instant wifi page")
    public void step4() {
        new APUtils(WebportalParam.ap1IPaddress);
        new WirelessQuickViewPage();
        new WirelessQuickViewPage(false).open_instant_wifi_page();
    }

    @Step("Test Step 4:click on all Restore Default options and click optimize Now Button and Verify sucesse message")
    public void step5() {
        new WirelessQuickViewPage(false).clickOnAllselectAllLink();
        new WirelessQuickViewPage(false).clickOnAllDeselectAllLink();
        new WirelessQuickViewPage(false).clickOnAllRestoreDefaultLink();

    }

    @Step("Test Step 5: Click on optimize Now Button and Verify sucesse message")
    public void step6() {
        new WirelessQuickViewPage(false).clickOnOptimizeButton();
    }

    @Step("Test Step 6: Run command 'grep 'Rf' /tmp/log/messages' and verify RF setting is present in responce")
    public void step7() {
        assertTrue(new APUtils(WebportalParam.ap1IPaddress).checkInstantWifiProfileRF_Setting());

    }

}
