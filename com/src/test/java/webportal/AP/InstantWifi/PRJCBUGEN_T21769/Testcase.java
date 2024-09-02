package webportal.AP.InstantWifi.PRJCBUGEN_T21769;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

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
    @Story("PRJCBUGEN_T21769") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify user is able to change channel width when auto channel allocation is enable") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T21769") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new DevicesDashPage().enterDevice(WebportalParam.ap1serialNo);
        channelInfo.put("2.4GHz channel width", "Dynamic 20/40 MHz");
        channelInfo.put("5GHz channel width", "Dynamic 20/40/80 MHz");
        new DevicesApRadioAndChannelsPage().configRadioAndChannel(channelInfo);
        new WirelessQuickViewPage().deleteSsidYes("apwp21769");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccount();
    }

    @Step("Test Step 2: Check automatic channel allocation;")
    public void step2() {
        Map<String, String> ssidInfo = new HashMap<String, String>();
        ssidInfo.put("SSID", "apwp21769");
        ssidInfo.put("Security", "WPA/WPA2-PSK");
        ssidInfo.put("Password", "123456798");
        new WirelessQuickViewPage().addSsid(ssidInfo);
        MyCommonAPIs.sleepi(10000);
        new WirelessQuickViewPage().enableInstantWifiAutoChannel(WebportalParam.ap1serialNo);
        
        new DevicesDashPage().enterDevice(WebportalParam.ap1serialNo);
        channelInfo.put("2.4GHz channel width", "20MHz");
        channelInfo.put("5GHz channel width", "40MHz");
        new DevicesApRadioAndChannelsPage().configRadioAndChannel(channelInfo);

        MyCommonAPIs.sleepsync();
        Selenide.refresh();
        new DevicesApRadioAndChannelsPage(false).checkFiveGHzTireDisplay();
        assertTrue(new DevicesApRadioAndChannelsPage(false).twoPointFourGHzChannelWidth.getSelectedText()
                .equals(channelInfo.get("2.4GHz channel width"))
                && new DevicesApRadioAndChannelsPage(false).fiveGHzChannelWidth.getSelectedText().equals(channelInfo.get("5GHz channel width")),
                "Configure channel failed.");
    }

}
