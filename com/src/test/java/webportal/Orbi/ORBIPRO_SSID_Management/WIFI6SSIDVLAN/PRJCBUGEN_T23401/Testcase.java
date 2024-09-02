package webportal.Orbi.ORBIPRO_SSID_Management.WIFI6SSIDVLAN.PRJCBUGEN_T23401;

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
    String                      vlanId          = "3401";
    String                      vlanName        = "testvlan" + vlanId;
    String                      vlanId1         = "2401";
    String                      vlanName1       = "testvlan" + vlanId1;
    String                      dhcpIp          = null;
    String                      dhcpIp1          = null;
    DevicesOrbiWifiNetworkPage  wifiNetwork     = null;
    DevicesOrbiNetworkSetupPage orbiNetwork     = null;
    DevicesOrbiDHCPServersPage  orbiDhcp        = null;
    boolean                     toremovevlan    = false;
    String                      vlanIdtoOpen    = "30";
    String                      vlanIdtoRestore = "Iot";
    
    @Feature("ORBIPRO_SSID_Management.WIFI6SSIDVLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T23401") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify the VLAN associated to the DHCP server") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T23401") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1")

    public void test() throws Exception {
        dhcpIp = handle.getRandomIp();
        dhcpIp1 = handle.getRandomIp();
        wifiNetwork = new DevicesOrbiWifiNetworkPage(true);
        orbiNetwork = new DevicesOrbiNetworkSetupPage();
        orbiDhcp = new DevicesOrbiDHCPServersPage();
        
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (toremovevlan) {
            orbiDhcp.gotoPage();
            orbiDhcp.setDHCP(vlanId, false);
            orbiDhcp.gotoPage();
            orbiDhcp.setDHCP(vlanId1, false);
            MyCommonAPIs.sleepsyncorbi();

            orbiDhcp.gotoPage();
            orbiDhcp.dumpInfo();
            orbiDhcp.deleteOne(vlanId);
            orbiDhcp.gotoPage();
            orbiDhcp.dumpInfo();
            orbiDhcp.deleteOne(vlanId1);
            MyCommonAPIs.sleepsyncorbi();
            
            orbiNetwork.gotoPage();
            orbiNetwork.dumpInfo();
            orbiNetwork.deleteNetwork(vlanName);
            orbiNetwork.gotoPage();
            orbiNetwork.dumpInfo();
            orbiNetwork.deleteNetwork(vlanName1);
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

    @Step("Test Step 2: Create a new vlan profile in Network Setup, Create another new vlan profile in Network Setup")
    public void step2() {
        ddp.openOBDevice();
        orbiNetwork.gotoPage(); 
        orbiNetwork.createNetwork(vlanName, vlanId, dhcpIp);
        orbiNetwork.createNetwork(vlanName1, vlanId1, dhcpIp1);
        MyCommonAPIs.sleepsyncorbi();
        toremovevlan = true;
    }

    @Step("Test Step 3: VLAN profile should be created in local with no issue")
    public void step3() {
        handle.waitRestReady(BRUtils.api_vlan_network_infos, vlanId, false, 3);
        assertTrue(new BRUtils().Dump().contains(vlanId), "check  vlan id is added");
        assertTrue(new BRUtils().Dump().contains(vlanId), "check  vlan1 id is added");
    }
}
