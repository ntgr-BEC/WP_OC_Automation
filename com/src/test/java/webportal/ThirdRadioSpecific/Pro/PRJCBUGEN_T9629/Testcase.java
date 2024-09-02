package webportal.ThirdRadioSpecific.Pro.PRJCBUGEN_T9629;

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
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    Map<String, String> channelInfo = new HashMap<String, String>();

    @Feature("ThirdRadioSpecific") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T9629") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify user is able to set 5ghzlow configuration for WAC 540 in radio and channel page in WEB for pro") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T9629") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new DevicesDashPage().enterDevice(WebportalParam.ap1serialNo);
        channelInfo.put("5GHz channel", "Auto");
        channelInfo.put("5GHz output power", "Auto");
        channelInfo.put("5GHz channel width", "Dynamic 20/40/80 MHz");
        new DevicesApRadioAndChannelsPage().configRadioAndChannel(channelInfo);
        new WirelessQuickViewPage().deleteSsidYes("apwp9629");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInNormalAccount("admin");
    }

    @Step("Test Step 2: Check radio and channel page;")
    public void step2() {
        Map<String, String> ssidInfo = new HashMap<String, String>();
        ssidInfo.put("SSID", "apwp9629");
        ssidInfo.put("Security", "WPA2 Personal Mixed");
        ssidInfo.put("Password", "123456798");
        new WirelessQuickViewPage().addSsid(ssidInfo);

        new DevicesDashPage().enterDevice(WebportalParam.ap1serialNo);
        channelInfo.put("5GHz channel width", "80MHz");
        channelInfo.put("5GHz channel", "60/5.30GHz(DFS)");
        channelInfo.put("5GHz output power", "Eighth");
        new DevicesApRadioAndChannelsPage().configRadioAndChannel(channelInfo);

        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        new DevicesDashPage().enterDevice(WebportalParam.ap1serialNo);
        new DevicesApRadioAndChannelsPage().checkFiveGHzTireDisplay();
        assertTrue(new DevicesApRadioAndChannelsPage(false).fiveGHzChannelWidth.getSelectedText().equals(channelInfo.get("5GHz channel width"))
                && new DevicesApRadioAndChannelsPage(false).fiveGHzChannel.getSelectedText().equals(channelInfo.get("5GHz channel"))
                && new DevicesApRadioAndChannelsPage(false).fiveGHzOutputPower.getSelectedText().equals(channelInfo.get("5GHz output power")),
                "<1>Radio and channel configuration error.");

        Map<String, String> sshChannelInfo = new APUtils(WebportalParam.ap1IPaddress).getChannelStatus("5GHz " , WebportalParam.ap1Model);

        assertTrue(sshChannelInfo.get("5GHz channel").contains("channel 60") && sshChannelInfo.get("5GHz output power").contains("txPower 3")
                && sshChannelInfo.get("5GHz channel width").contains("channelWidth 2"), "<2>Radio and channel configuration error.");
    }

}
