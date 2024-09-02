package webportal.ThirdRadioSpecific.PRJCBUGEN_T9636;

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
import util.RunCommand;
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
    @Story("PRJCBUGEN_T9636") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify user is able to set 2.4 ghz and 5ghz configuration for WAC 510 in radio and channel page in WEB") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T9636") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new DevicesDashPage().enterDevice(WebportalParam.ap2serialNo);
        channelInfo.put("2.4GHz channel", "Auto");
        channelInfo.put("2.4GHz output power", "Auto");
        channelInfo.put("2.4GHz channel width", "Dynamic 20/40 MHz");
        channelInfo.put("5GHz channel", "Auto");
        channelInfo.put("5GHz channel width", "Dynamic 20/40/80 MHz");
        channelInfo.put("5GHz output power", "Auto");
        new DevicesApRadioAndChannelsPage().configRadioAndChannel(channelInfo);
        new WirelessQuickViewPage().deleteSsidYes("apwp9636");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().checkDutInAdminAccount(WebportalParam.ap2serialNo, WebportalParam.ap2deveiceName, WebportalParam.ap2macaddress);
    }

    @Step("Test Step 2: Check radio and channel page;")
    public void step2() {
        String model = new DevicesDashPage().getDeviceModel(WebportalParam.ap2serialNo);
        if (model.contains("510") && model.contains("610") && model.contains("630E") && model.contains("615") && model.contains("750")) {
            assertTrue(false, "Ap need support 5GHz high.");
        }
        

        Map<String, String> ssidInfo = new HashMap<String, String>();
        ssidInfo.put("SSID", "apwp9636");
        ssidInfo.put("Security", "WPA2 Personal Mixed");
        ssidInfo.put("Password", "123456798");
        new WirelessQuickViewPage().addSsid(ssidInfo);
        
        System.out.println("SSID created");
        new DevicesDashPage().enterDevice(WebportalParam.ap2serialNo);
        channelInfo.put("2.4GHz channel", "8/2.447GHz");
        channelInfo.put("2.4GHz channel width", "40MHz");
        channelInfo.put("2.4GHz output power", "Half");
        channelInfo.put("5GHz channel", "36/5.18GHz");
        channelInfo.put("5GHz channel width", "80MHz");
        channelInfo.put("5GHz output power", "Quarter");
        new DevicesApRadioAndChannelsPage().configRadioAndChannel(channelInfo);
        
        System.out.println("Radio and channel configured");
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap2serialNo);
        new DevicesDashPage().enterDevice(WebportalParam.ap2serialNo);
        new DevicesApRadioAndChannelsPage().checkFiveGHzTireDisplay();
        new RunCommand().enableSSH4APALL(WebportalParam.ap2IPaddress);
        System.out.println("sdfjkefakewfaiewjfaewkjfwae");
        
        assertTrue(new DevicesApRadioAndChannelsPage(false).twoPointFourGHzChannelWidth.getSelectedText()
                .equals(channelInfo.get("2.4GHz channel width"))
                && new DevicesApRadioAndChannelsPage(false).twoPointFourGHzChannel.getSelectedText().equals(channelInfo.get("2.4GHz channel"))
                && new DevicesApRadioAndChannelsPage(false).twoPointFourGHzOutputPower.getSelectedText()
                        .equals(channelInfo.get("2.4GHz output power"))
                && new DevicesApRadioAndChannelsPage(false).fiveGHzChannelWidth.getSelectedText().equals(channelInfo.get("5GHz channel width"))
                && new DevicesApRadioAndChannelsPage(false).fiveGHzChannel.getSelectedText().equals(channelInfo.get("5GHz channel"))
                && new DevicesApRadioAndChannelsPage(false).fiveGHzOutputPower.getSelectedText().equals(channelInfo.get("5GHz output power")),
                "<1>Radio and channel configuration error.");

        Map<String, String> sshChannelInfo = new APUtils(WebportalParam.ap2IPaddress).getChannelStatus("2.4GHz 5GHz ", WebportalParam.ap2Model);
        
        System.out.println(sshChannelInfo);
        
        assertTrue(sshChannelInfo.get("2.4GHz channel").contains("channel 8") && sshChannelInfo.get("2.4GHz output power").contains("txPower 1")
                && sshChannelInfo.get("2.4GHz channel width").contains("channelWidth 1")
                && sshChannelInfo.get("5GHz channel").contains("channel 36") && sshChannelInfo.get("5GHz output power").contains("txPower 2")
                && sshChannelInfo.get("5GHz channel width").contains("channelWidth 2"), "<2>Radio and channel configuration error.");
    }

}
