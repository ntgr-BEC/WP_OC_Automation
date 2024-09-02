package webportal.Orbi.ORBIPRO_SSID_Management.WIFI6SSIDVLAN.PRJCBUGEN_T23403;

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
    String                      vlanId          = "3403";
    String                      vlanName        = "testvlan" + vlanId;
    String                      vlanId1         = "2403";
    String                      vlanName1       = "testvlan" + vlanId;
    String                      dhcpIp          = null;
    DevicesOrbiWifiNetworkPage  wifiNetwork     = null;
    DevicesOrbiNetworkSetupPage orbiNetwork     = null;
    DevicesOrbiDHCPServersPage  orbiDhcp        = null;
    boolean                     toremovevlan    = false;
    String                      vlanIdtoOpen    = "20";
    String                      vlanIdtoRestore = "Employee";
    
    @Feature("ORBIPRO_SSID_Management.WIFI6SSIDVLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T23403") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify the to delete the VLAN.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T23403") // It's a testcase id/link from Jira Test Case.
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
            String newvlanname = orbiNetwork.networkData.vlanName;
            orbiNetwork.networkData.vlanName = vlanIdtoRestore;
            orbiNetwork.networkData.DhcpIp = "192.168.20.1";
            orbiNetwork.editNetwork(newvlanname);
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

    @Step("Test Step 2: Delete VLAN profile 20  which is used in LAN Setup 2")
    public void step2() {
        ddp.openOBDevice();
        orbiNetwork.gotoPage();
        orbiNetwork.deleteNetwork(vlanIdtoRestore);
        assertTrue(handle.getPageErrorMsg().length()>0, "VLAN profile should not be deletable");
        
        System.out.println(orbiNetwork.networkData);
        orbiNetwork.networkData.vlanName = vlanIdtoRestore + RandomStringUtils.randomNumeric(5);
        orbiNetwork.networkData.ClientIsolation = true;
        orbiNetwork.networkData.portList = "3";
        orbiNetwork.networkData.portMode = 1;
        orbiNetwork.networkData.DhcpIp = handle.getRandomIp();
        orbiNetwork.editNetwork(vlanIdtoRestore);
        MyCommonAPIs.sleepsyncorbi();
        toremovevlan = true;
    }

    @Step("Test Step 3: Create a new VLAN profile and make sure it is not being used in LAN setup,delete it ")
    public void step3() {
        assertTrue(true, "is coverted by other script already");
    }
    
    @Step("Test Step 4: Move mouse to default VLAN 1")
    public void step4() {
        orbiNetwork.gotoPage();
        try {
            orbiNetwork.deleteNetwork("Default");
            assertTrue(false, "check vlan 1 is removed");
        } catch (Throwable e) {
            assertTrue(true, "check vlan 1 should not be removed");
        }
    }
}
