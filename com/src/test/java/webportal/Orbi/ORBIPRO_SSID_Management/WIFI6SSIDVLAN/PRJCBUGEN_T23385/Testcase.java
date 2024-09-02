package webportal.Orbi.ORBIPRO_SSID_Management.WIFI6SSIDVLAN.PRJCBUGEN_T23385;

import static org.testng.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.BRUtils;
import util.MyCommonAPIs;
import webportal.weboperation.DevicesOrbiDHCPServersPage;
import webportal.weboperation.DevicesOrbiNetworkSetupPage;
import webportal.weboperation.DevicesOrbiWifiNetworkPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    DevicesOrbiWifiNetworkPage  wifiNetwork = null;
    DevicesOrbiNetworkSetupPage orbiNetwork = null;
    DevicesOrbiDHCPServersPage  orbiDhcp    = null;
    
    @Feature("ORBIPRO_SSID_Management.WIFI6SSIDVLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T23385") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that VLAN can be configured on the SSID having WPA2 Enterprise sesurity with CP configured and functionality works fine") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T23385") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")

    public void test() throws Exception {
        wifiNetwork = new DevicesOrbiWifiNetworkPage(true);
        orbiNetwork = new DevicesOrbiNetworkSetupPage();
        orbiDhcp = new DevicesOrbiDHCPServersPage();
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        orbiNetwork.gotoPage();
        orbiNetwork.deleteNetwork(orbiNetwork.networkData.vlanName);
        
        wifiNetwork.gotoPage();
        wifiNetwork.ssidData.ssidVlan = "Guest";
        wifiNetwork.editSsidSetting(3);
        handle.waitRestReady(BRUtils.api_device_guest_portal_wlan_info, orbiNetwork.networkData.vlanName, false, 3);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to WP and go to location")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();

        handle.gotoLoction();

        ddp.gotoPage();
    }

    @Step("Test Step 2: Go to Wifi Network page")
    public void step2() {
        ddp.openOBDevice();
        wifiNetwork.gotoPage();
    }

    @Step("Test Step 3: Open guest wifi and set VLAN profile to 40, Open Network setting and rename VLAN 40")
    public void step3() {
        wifiNetwork.ssidData.ssid = "T23385-" + RandomStringUtils.randomNumeric(5);
        wifiNetwork.ssidData.ssidSecurity = 1;
        wifiNetwork.ssidData.enaSsidVlan = true;
        wifiNetwork.ssidData.ssidVlan = "Guest";
        wifiNetwork.editSsidSetting(3);
        
        orbiNetwork.gotoPage();
        orbiNetwork.networkData.vlanName = "Guest-" + RandomStringUtils.randomNumeric(5);
        orbiNetwork.editNetwork("Guest");
    }

    @Step("Test Step 4: the new VLAN profile setting should be pushed to local correctly.")
    public void step4() {
        handle.waitRestReady(BRUtils.api_vlan_network_infos, orbiNetwork.networkData.vlanName, false, 3);
        assertTrue(new BRUtils().getFieldsOrbi("vlanName").contains(orbiNetwork.networkData.vlanName), "check vlan name can be changed");
        assertTrue(new BRUtils(BRUtils.api_device_guest_portal_wlan_info, 3).getFieldsOrbi("SSID").contains(wifiNetwork.ssidData.ssid),
                "check lan ip can be changed");
    }
    
    @Step("Test Step 5: Create a new VLAN profile 50, Change guest VLAN 40 to 50")
    public void step5() {
        orbiNetwork.gotoPage();
        orbiNetwork.networkData.vlanName = "PRJCBUGEN_T23385";
        orbiNetwork.networkData.vlanId = "3385";
        orbiNetwork.createNetwork();

        handle.waitRestReady(BRUtils.api_vlan_network_infos, orbiNetwork.networkData.vlanName, false, 3);

        wifiNetwork.gotoPage();
        wifiNetwork.ssidData.ssidVlan = orbiNetwork.networkData.vlanName;
        wifiNetwork.editSsidSetting(3);
        handle.waitRestReady(BRUtils.api_device_guest_portal_wlan_info, orbiNetwork.networkData.vlanName, false, 3);
        assertTrue(new BRUtils().getFieldsOrbi("vlanProfile").contains(orbiNetwork.networkData.vlanId), "check vlan name can be changed");
    }
}
