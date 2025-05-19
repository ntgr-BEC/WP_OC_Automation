package webportal.RF_WLAN_Profile.Premium.PRJCBUGEN_T40190;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import webportal.weboperation.DevicesApSummaryPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    Map<String, String> RFdata                  = new HashMap<String, String>();
    public List<String> ap24BandChannels        = new ArrayList<>();
    public List<String> ap5BandChannels         = new ArrayList<>();
    public List<String> ap6BandChannels         = new ArrayList<>();
    public List<String> rfProfile24BandChannels = new ArrayList<>();
    public List<String> rfProfile5BandChannels  = new ArrayList<>();
    public List<String> rfProfile6BandChannels  = new ArrayList<>();

    @Feature("RF_WLAN_Profile.Premium") // It's a folder/component name to make test suite more readable from Jira Test Case
    @Story("PRJCBUGEN_T40190") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether AP's which are assigned with RF Profiles  get the new channels allocated when we run instant WiFi on location level  --> Instant WiFi page")                                                                                                                                                                    // Case.
    @TmsLink("PRJCBUGEN_T40190") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
//        new DevicesDashPage().GoToDevicesDashPage();
//        new DevicesDashPage().UNAssignRF(WebportalParam.ap1serialNo);
//        new DevicesDashPage().GoToDevicesDashPage();
//        new DevicesDashPage().UNAssignRF(WebportalParam.ap2serialNo);
//        new WirelessQuickViewPage().GotoRF();
//        new WirelessQuickViewPage(false).deleteRF(RFdata.get("RFName"));
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();

    }

    @Step("Test Step 2: Create RF Profiles and Verify")
    public void step2() {

        RFdata.put("RFName", "Netgear");
        RFdata.put("RFDescription", "BEC Automation Team");
        RFdata.put("Copy Configurations", "Open Office");
        new WirelessQuickViewPage().GotoRF();
        new WirelessQuickViewPage(false).CreateRFProfile(RFdata);

    }

    @Step("Test Step 3: Now verify default radio modes shown on all RF Profiles")
    public void step3() {

        new WirelessQuickViewPage().GotoRF();
        rfProfile24BandChannels = new WirelessQuickViewPage(false).verifyRFProfileInstantWifiBandChannels("Netgear", "2.4 GHz");
        new WirelessQuickViewPage().GotoRF();
        rfProfile5BandChannels = new WirelessQuickViewPage(false).verifyRFProfileInstantWifiBandChannels("Netgear", "5 GHz");
        new WirelessQuickViewPage().GotoRF();
        rfProfile6BandChannels = new WirelessQuickViewPage(false).verifyRFProfileInstantWifiBandChannels("Netgear", "6 GHz");
    }

    @Step("Test Step 4: Assign RF Profiles")
    public void step4() {

        new DevicesDashPage().GoToDevicesDashPage();
        String RFName = RFdata.get("RFName");
        new DevicesDashPage().AssignRF(WebportalParam.ap1serialNo, RFName);
        new DevicesDashPage().GoToDevicesDashPage();
        new DevicesDashPage().AssignRF(WebportalParam.ap2serialNo, RFName);
        MyCommonAPIs.sleepi(60);

    }

    @Step("Test Step 5: RF Profile Optimize now and verify radio channels its pushe to both ap's ")
    public void step5() {
        
        new WirelessQuickViewPage().GotoRF();
        new WirelessQuickViewPage(false).verifyInstantWifiOptimizeNowonRFProfile("Netgear");
        
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);
        new WirelessQuickViewPage(false).RadioAndChannels.click();
        MyCommonAPIs.sleepi(10);
        String channel24GHz = new WirelessQuickViewPage(false).getChannelIndexValue("2.4GHz");
        assertTrue(
                rfProfile24BandChannels.contains(channel24GHz),
                "Channel " + channel24GHz + " not found in rfProfile Channel list"
            );
        
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);
        new WirelessQuickViewPage(false).RadioAndChannels.click();
        MyCommonAPIs.sleepi(10);
        new WirelessQuickViewPage(false).DropDown5GhzLow.click();
        MyCommonAPIs.sleepi(5);
        String channel5GHz = new WirelessQuickViewPage(false).getChannelIndexValue("5GHz");
        assertTrue(
                rfProfile5BandChannels.contains(channel5GHz),
                "Channel " + channel5GHz + " not found in rfProfile Channel list"
            );
        
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap2serialNo);
        new WirelessQuickViewPage(false).RadioAndChannels.click();
        MyCommonAPIs.sleepi(10);
        channel24GHz = new WirelessQuickViewPage(false).getChannelIndexValue("2.4GHz");
        assertTrue(
                rfProfile24BandChannels.contains(channel24GHz),
                "Channel " + channel24GHz + " not found in rfProfile Channel list"
            );
        
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap2serialNo);
        new WirelessQuickViewPage(false).RadioAndChannels.click();
        MyCommonAPIs.sleepi(10);
        new WirelessQuickViewPage(false).DropDown5GhzLow.click();
        MyCommonAPIs.sleepi(5);
        channel5GHz = new WirelessQuickViewPage(false).getChannelIndexValue("5GHz");
        assertTrue(
                rfProfile5BandChannels.contains(channel5GHz),
                "Channel " + channel5GHz + " not found in rfProfile Channel list"
            );

    }

}
