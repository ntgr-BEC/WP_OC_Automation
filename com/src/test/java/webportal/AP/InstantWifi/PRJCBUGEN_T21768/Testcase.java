package webportal.AP.InstantWifi.PRJCBUGEN_T21768;

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
import webportal.weboperation.DevicesApRadioAndChannelsPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    String              region       = "";
    Map<String, String> locationInfo = new HashMap<String, String>();
    Map<String, String> channelInfo  = new HashMap<String, String>();

    @Feature("AP.InstantWifi") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T21768") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify popup as InstantWiFi is in progress is shown or not when the click on optimize now button after InstantWiFi is running because of change in channel width due to any reason") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T21768") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new DevicesDashPage().enterDevice(WebportalParam.ap1serialNo);
        channelInfo.put("5GHz channel width", "Dynamic 20/40/80 MHz");
        new DevicesApRadioAndChannelsPage().configRadioAndChannel(channelInfo);
        new WirelessQuickViewPage().deleteSsidYes("apwp21768");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccount();
    }

    @Step("Test Step 2: Check popup;")
    public void step2() {
        Map<String, String> ssidInfo = new HashMap<String, String>();
        ssidInfo.put("SSID", "apwp21768");
        ssidInfo.put("Security", "WPA/WPA2-PSK");
        ssidInfo.put("Password", "123456798");
        new WirelessQuickViewPage().addSsid(ssidInfo);

        new DevicesDashPage().enterDevice(WebportalParam.ap1serialNo);
        channelInfo.put("5GHz channel width", "80MHz");
        new DevicesApRadioAndChannelsPage().configRadioAndChannel(channelInfo);

        boolean result = false;
        if (!new WirelessQuickViewPage().optimizeInstantWifi(false)) {
            int i = 0;
            while (i < 2) {
                MyCommonAPIs.sleepi(10 * 60);
                if (new WirelessQuickViewPage().optimizeInstantWifi(false)) {
                    result = true;
                    break;
                }
            }
        }

        new WirelessQuickViewPage(false).optimizenowbutton.click();
        MyCommonAPIs.waitReady();
        MyCommonAPIs.waitElement(new WirelessQuickViewPage(false).instantwifisuccessmeg);
        assertTrue(result && MyCommonAPIs.getText(new WirelessQuickViewPage(false).instantwifisuccessmeg)
                .contains("Instant Wifi Configuration is in progress, please try after sometime"), "Popup error.");
    }
}
