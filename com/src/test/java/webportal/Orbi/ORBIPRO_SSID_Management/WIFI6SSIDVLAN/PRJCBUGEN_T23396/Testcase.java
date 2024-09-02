package webportal.Orbi.ORBIPRO_SSID_Management.WIFI6SSIDVLAN.PRJCBUGEN_T23396;

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
    DevicesOrbiWifiNetworkPage  wifiNetwork = null;
    DevicesOrbiNetworkSetupPage orbiNetwork = null;
    DevicesOrbiDHCPServersPage  orbiDhcp    = null;
    String                      vlanName    = "Default";
    String                      vlanId      = "";
    boolean                     needClean   = false;
    String                      dhcpIp      = null;

    @Feature("ORBIPRO_SSID_Management.WIFI6SSIDVLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T23396") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify the Management VLAN is configured with non-default VLAN") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T23396") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    
    public void test() throws Exception {
        wifiNetwork = new DevicesOrbiWifiNetworkPage(true);
        orbiNetwork = new DevicesOrbiNetworkSetupPage();
        orbiDhcp = new DevicesOrbiDHCPServersPage();
        dhcpIp = handle.getRandomIp();
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (needClean) {
            orbiNetwork.gotoPage();
            orbiNetwork.networkData.vlanName = vlanName;
            orbiNetwork.editNetwork(vlanName);
            MyCommonAPIs.sleepsyncorbi();
        }
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to WP and go to location")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        
        handle.gotoLoction();
        
        ddp.gotoPage();
    }
    
    @Step("Test Step 2: Create a new VLAN ID which is same with an existed VLAN profile")
    public void step2() {
        ddp.openOBDevice();
        orbiNetwork.gotoPage();
        orbiNetwork.networkData.vlanName = vlanName + RandomStringUtils.randomNumeric(5);
        orbiNetwork.networkData.portList = "4";
        orbiNetwork.networkData.portMode = 1;
        orbiNetwork.networkData.DhcpIpStart = MyCommonAPIs.nextIP(WebportalParam.ob1IPaddress, 10);
        orbiNetwork.networkData.DhcpIpEnd = MyCommonAPIs.nextIP(WebportalParam.ob1IPaddress, 100);
        orbiNetwork.editNetwork(vlanName);
        needClean = true;
    }
    
    @Step("Test Step 3: Setting can be saved to local.")
    public void step3() {
        handle.waitRestReady(BRUtils.api_vlan_network_infos, orbiNetwork.networkData.vlanName, false, 3);
        assertTrue(new BRUtils().Dump().contains(orbiNetwork.networkData.vlanName), "check network name is changed");
        String portCheck = new BRUtils().getFieldsOrbi("baseLanPort");
        assertTrue(portCheck.contains("3T") && !portCheck.contains("4T"), "check port mode is changed");
        assertTrue(new BRUtils(BRUtils.api_lan_ip_settings, 3).Dump().contains(orbiNetwork.networkData.DhcpIpStart), "check lan ip is changed");
    }
}
