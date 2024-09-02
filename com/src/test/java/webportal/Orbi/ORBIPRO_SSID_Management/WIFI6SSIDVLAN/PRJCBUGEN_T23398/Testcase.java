package webportal.Orbi.ORBIPRO_SSID_Management.WIFI6SSIDVLAN.PRJCBUGEN_T23398;

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
    String                      vlanId       = "3398";
    String                      vlanName     = "testvlan" + vlanId;
    String                      dhcpIp       = null;
    DevicesOrbiWifiNetworkPage  wifiNetwork  = null;
    DevicesOrbiNetworkSetupPage orbiNetwork  = null;
    DevicesOrbiDHCPServersPage  orbiDhcp     = null;
    boolean                     toremovevlan = false;
    
    @Feature("ORBIPRO_SSID_Management.WIFI6SSIDVLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T23398") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify the Management and profile VLAN remain same even after reboot") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T23398") // It's a testcase id/link from Jira Test Case.
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
            orbiNetwork.gotoPage();
            orbiNetwork.deleteNetwork(vlanName);
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

    @Step("Test Step 2: Create a new VLAN profile")
    public void step2() {
        ddp.openOBDevice();
        orbiNetwork.gotoPage();
        assertTrue(orbiNetwork.getNetworks().size() >= 4, "check network has all 4 default profiles");

        orbiNetwork.createNetwork(vlanName, vlanId, dhcpIp);
        MyCommonAPIs.sleepsyncorbi();
        toremovevlan = true;
    }

    @Step("Test Step 3: New VLAN profile should be visible in  Network Setup")
    public void step3() {
        orbiNetwork.gotoPage();
        assertTrue(orbiNetwork.getNetworks().contains(vlanName), "check network is created on web");
    }
}
