package webportal.Orbi.ORBIPRO_SSID_Management.WIFI6SSIDVLAN.PRJCBUGEN_T23394;

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
    String                      vlanName    = "test23394vlan";
    String                      vlanId      = "3394";
    boolean                     needClean   = false;
    String                      dhcpIp      = null;

    @Feature("ORBIPRO_SSID_Management.WIFI6SSIDVLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T23394") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify creating VLAN on an ethernet LAN port.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T23394") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    
    public void test() throws Exception {
        wifiNetwork = new DevicesOrbiWifiNetworkPage(true);
        orbiNetwork = new DevicesOrbiNetworkSetupPage();
        orbiDhcp = new DevicesOrbiDHCPServersPage();
        //dhcpIp = handle.getRandomIp();
        dhcpIp = "192.168.50.1";
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (needClean) {
            orbiDhcp.gotoPage();
            orbiDhcp.deleteOne(vlanId);
            orbiNetwork.gotoPage();
            orbiNetwork.deleteNetwork(vlanName);
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
    
    @Step("Test Step 2: Open Network setting page in Insight")
    public void step2() {
        ddp.openOBDevice();
        orbiNetwork.gotoPage();
    }
    
    @Step("Test Step 3: Create new VLAN profile 50, Assign new IP range 192.168.50.1")
    public void step3() {
        orbiNetwork.createNetwork(vlanName, vlanId, dhcpIp);
        needClean = true;
    }
    
    @Step("Test Step 4: Setting can be saved to local.")
    public void step4() {
        handle.waitRestReady(BRUtils.api_vlan_network_infos, vlanName, false, 3);
        assertTrue(new BRUtils().Dump().contains(vlanName), "check network name is changed");
        handle.waitRestReady(BRUtils.api_lan_subnet, dhcpIp, false, 3);
        assertTrue(new BRUtils().Dump().contains(dhcpIp), "check dhcp ip is changed");
    }
}
