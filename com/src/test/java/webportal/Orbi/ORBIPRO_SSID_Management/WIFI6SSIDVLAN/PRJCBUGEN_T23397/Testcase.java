package webportal.Orbi.ORBIPRO_SSID_Management.WIFI6SSIDVLAN.PRJCBUGEN_T23397;

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
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesOrbiDHCPServersPage;
import webportal.weboperation.DevicesOrbiNetworkSetupPage;
import webportal.weboperation.DevicesOrbiWifiNetworkPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String                      vlanId       = "3397";
    String                      vlanName     = "testvlan" + vlanId;
    String                      vlanId1      = "2397";
    String                      vlanName1    = "testvlan" + vlanId;
    String                      dhcpIp       = null;
    DevicesOrbiWifiNetworkPage  wifiNetwork  = null;
    DevicesOrbiNetworkSetupPage orbiNetwork  = null;
    DevicesOrbiDHCPServersPage  orbiDhcp     = null;
    boolean                     toremovevlan = false;

    @Feature("ORBIPRO_SSID_Management.WIFI6SSIDVLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T23397") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify the Management and profile VLAN remain same even after reboot") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T23397") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    
    public void test() throws Exception {
        dhcpIp = handle.getRandomIp();
        wifiNetwork = new DevicesOrbiWifiNetworkPage(true);
        orbiNetwork = new DevicesOrbiNetworkSetupPage();
        orbiDhcp = new DevicesOrbiDHCPServersPage();

        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (toremovevlan) {
            orbiDhcp.gotoPage();
            orbiDhcp.deleteOne(vlanId);

            orbiNetwork.gotoPage();
            orbiNetwork.deleteNetwork(vlanName);
            orbiNetwork.deleteNetwork(vlanName1);
            MyCommonAPIs.sleepsync();
            handle.waitRestReady(BRUtils.api_vlan_network_infos, vlanName, true, 3);
        }
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to WP and go to location")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();

        handle.gotoLoction();

        ddp.gotoPage();
    }
    
    @Step("Test Step 2: Add 2-3 new VLAN profiles")
    public void step2() {
        ddp.openOBDevice();
        orbiNetwork.gotoPage();
        orbiNetwork.createNetwork(vlanName, vlanId, dhcpIp);
        orbiNetwork.createNetwork(vlanName1, vlanId1, dhcpIp);
        MyCommonAPIs.sleepsyncorbi();
        toremovevlan = true;
    }
    
    @Step("Test Step 3: VLAN profiles and setting remain same after reboot.")
    public void step3() {
        ddp.gotoPage();
        ddp.rebootDevice(WebportalParam.ob1serialNo);
        ddp.waitDevicesReConnected(WebportalParam.ob1serialNo);

        handle.waitRestReady(BRUtils.api_vlan_network_infos, vlanName1, false, 3);
        assertTrue(new BRUtils().Dump().contains(vlanName), "check vlan is created on device");
        assertTrue(new BRUtils().Dump().contains(vlanName1), "check vlan1 is created on device");
    }
}
