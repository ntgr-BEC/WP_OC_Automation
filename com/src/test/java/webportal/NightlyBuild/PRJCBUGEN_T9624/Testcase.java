package webportal.NightlyBuild.PRJCBUGEN_T9624;

import static org.hamcrest.CoreMatchers.containsString;
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
import util.APUtils;
import util.MyCommonAPIs;
import util.Javasocket;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.WirelessQuickViewPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesApRadioAndChannelsPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    Map<String, String> channelInfo = new HashMap<String, String>();

    @Feature("NightlyBuild") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T9624") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Radio and channel page in WEB") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T9624") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");    
        new WirelessQuickViewPage().deleteSsidYes("apwp9624");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccount();
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
    }

    @Step("Test Step 2: Check radio and channel page;")
    public void step2() {
        Map<String, String> ssidInfo = new HashMap<String, String>();
        ssidInfo.put("SSID", "apwp9624");
        ssidInfo.put("Security", "WPA2 Personal Mixed");
        ssidInfo.put("Password", "123456798");
        new WirelessQuickViewPage().addSsid(ssidInfo);

        new DevicesDashPage().enterDevice(WebportalParam.ap1serialNo);
        channelInfo.put("2.4GHz channel", "11/2.462GHz");
        channelInfo.put("2.4GHz channel width", "40MHz");
        channelInfo.put("2.4GHz output power", "Half");
        new DevicesApRadioAndChannelsPage().configRadioAndChannel(channelInfo);

        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        new DevicesDashPage().enterDevice(WebportalParam.ap1serialNo);
        new DevicesApRadioAndChannelsPage().checkFiveGHzTireDisplay();
        assertTrue(new DevicesApRadioAndChannelsPage(false).twoPointFourGHzChannelWidth.getSelectedText()
                .equals(channelInfo.get("2.4GHz channel width"))
                && new DevicesApRadioAndChannelsPage(false).twoPointFourGHzChannel.getSelectedText().equals(channelInfo.get("2.4GHz channel"))
                && new DevicesApRadioAndChannelsPage(false).twoPointFourGHzOutputPower.getSelectedText()
                        .equals(channelInfo.get("2.4GHz output power")),
                "<1>Radio and channel configuration error.");      
    }
}
