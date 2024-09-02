package webportal.Orbi.ORBIPRO_SSID_Management.WIFI6SSIDVLAN.PRJCBUGEN_T23375;

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
    String                      vlanId       = "3375";
    String                      vlanName     = "testvlan" + vlanId;
    String                      vlanId1      = "2375";
    String                      vlanName1    = "testvlan" + vlanId;
    String                      dhcpIp       = null;
    DevicesOrbiNetworkSetupPage page         = null;
    boolean                     toremovevlan = false;
    
    @Feature("ORBIPRO_SSID_Management.WIFI6SSIDVLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T23375") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Delete the non-default VLAN SSID. Check the configuration is applied properly (cloned)") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T23375") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")

    public void test() throws Exception {
        dhcpIp = String.format("10.%s.%s.%s", RandomStringUtils.randomNumeric(2), RandomStringUtils.randomNumeric(2),
                RandomStringUtils.randomNumeric(2));
        page = new DevicesOrbiNetworkSetupPage();
        
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (toremovevlan) {
            DevicesOrbiDHCPServersPage dhcp = new DevicesOrbiDHCPServersPage();
            dhcp.gotoPage();
            dhcp.deleteOne(vlanId);
            
            page.gotoPage();
            page.deleteNetwork(vlanName);
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

    @Step("Test Step 2: Go to Wifi Network page, Create a new VLAN profile 50")
    public void step2() {
        ddp.openOBDevice();
        page.gotoPage();
        page.createNetwork(vlanName, vlanId, dhcpIp);
        MyCommonAPIs.sleepsync();
    }

    @Step("Test Step 3: Check LAN 5 in DHCP server,VLAN profile is being used")
    public void step3() {
        handle.waitRestReady(BRUtils.api_vlan_network_infos, vlanName, false, 3);
        assertTrue(new BRUtils().Dump().contains(vlanName), "check vlan is created on device");
        toremovevlan = true;
    }
    
    @Step("Test Step 4: Delete VLAN profile 50 in Network Setup")
    public void step4() {
        page.gotoPage();
        page.deleteNetwork(vlanName);
        assertTrue(handle.getPageErrorMsg().length() > 10, "check error if dhcp was existed, removing network is not allowed");
    }

    @Step("Test Step 5: Create a new VLAN profile 60 in Insight then delete it")
    public void step5() {
        page.gotoPage();
        page.createNetwork(vlanName1, vlanId1, dhcpIp);
        handle.waitRestReady(BRUtils.api_vlan_network_infos, vlanName1, false, 3);
        assertTrue(new BRUtils().Dump().contains(vlanName1), "check vlan1 is created on device");
        
        page.deleteNetwork(vlanName1);
        handle.waitRestReady(BRUtils.api_vlan_network_infos, vlanName1, true, 3);
        assertTrue(!new BRUtils().Dump().contains(vlanName1), "VLAN profile 60 can be deleted directly.");
    }
}
