package webportal.Orbi.ORBIPRO_SSID_Management.WIFI6SSIDVLAN.PRJCBUGEN_T23402;

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
    String                      vlanId          = "3402";
    String                      vlanName        = "testvlan" + vlanId;
    String                      vlanId1         = "2402";
    String                      vlanName1       = "testvlan" + vlanId;
    String                      dhcpIp          = null;
    DevicesOrbiWifiNetworkPage  wifiNetwork     = null;
    DevicesOrbiNetworkSetupPage orbiNetwork     = null;
    DevicesOrbiDHCPServersPage  orbiDhcp        = null;
    boolean                     toremovevlan    = false;
    String                      vlanIdtoOpen    = "20";
    String                      vlanIdtoRestore = "Employee";
    
    @Feature("ORBIPRO_SSID_Management.WIFI6SSIDVLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T23402") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify the to Edit the VLAN.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T23402") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1")

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
            orbiNetwork.gotoPage();
            orbiNetwork.networkData.vlanName = vlanIdtoRestore;
            orbiNetwork.editNetwork(vlanIdtoRestore);
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

    @Step("Test Step 2: open VLAN profile 20 and open it")
    public void step2() {
        ddp.openOBDevice();
        orbiNetwork.networkData.vlanName = vlanIdtoRestore + RandomStringUtils.randomNumeric(5);
        orbiNetwork.networkData.ClientIsolation = true;
        orbiNetwork.networkData.portList = "4";
        orbiNetwork.networkData.portMode = 1;
        orbiNetwork.networkData.DhcpIp = handle.getRandomIp();
        orbiNetwork.editNetwork(vlanIdtoRestore);
        MyCommonAPIs.sleepsyncorbi();
        toremovevlan = true;
    }

    @Step("Test Step 3: New setting can be saved and sync to local")
    public void step3() {
        handle.waitRestReady(BRUtils.api_vlan_network_infos, orbiNetwork.networkData.vlanName, false, 3);
        assertTrue(new BRUtils().Dump().contains(orbiNetwork.networkData.vlanName), "check vlan is changed");
        String valCheck = new BRUtils().getFieldOrbi("vlanNetworkInfo", "vlanID", "20", "baseLanPort");
        assertTrue(valCheck.contains("4"), "check port mode is changed");
    }
    
    @Step("Test Step 4: Configure default  VLAN IP and set access or trunk port")
    public void step4() {
        orbiNetwork.gotoPage();
        orbiNetwork.openNetwork("Default");
        assertTrue(!orbiNetwork.txtVlanId.isEnabled(), "check vlan id should be disabled");
    }

    @Step("Test Step 5: Open default VLAN and rename VLAN and VLAN ID")
    public void step5() {
        orbiNetwork.gotoPage();
        orbiNetwork.networkData.portList = "4";
        orbiNetwork.networkData.portMode = 1;
        orbiNetwork.editNetwork("Default");
        String valCheck = new BRUtils().getFieldOrbi("vlanNetworkInfo", "vlanID", "1", "baseLanPort");
        assertTrue(valCheck.contains("4") && !valCheck.contains("4T"), "check port mode is changed");
    }
}
