package webportal.Orbi.ORBIPRO_SSID_Management.WIFI6SSIDVLAN.PRJCBUGEN_T23384;

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
    String                      vlanName           = "Iot-new";
    boolean                     ClientIsolation    = true;
    String                      DhcpIp             = handle.getRandomIp();
    
    @Feature("ORBIPRO_SSID_Management.WIFI6SSIDVLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T23384") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that VLAN can be changed even though Client isolation is enabled") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T23384") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    
    public void test() throws Exception {
        vlanName = "Iot" + RandomStringUtils.randomNumeric(5);
        orbiNetwork = new DevicesOrbiNetworkSetupPage();
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        orbiNetwork.gotoPage();
        orbiNetwork.editNetwork(vlanName, "Iot", orbiNetwork.networkData.DhcpIp);
        MyCommonAPIs.sleepsyncorbi();
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
    }
    
    @Step("Test Step 3: Open Network Setup>VLAN 30")
    public void step3() {
        orbiNetwork.gotoPage();
        orbiNetwork.editNetwork("Iot", vlanName, DhcpIp); // fix uncompleted function call
    }
    
    @Step("Test Step 4: the new VLAN profile setting should be pushed to local correctly.")
    public void step4() {
        handle.waitRestReady(BRUtils.api_vlan_network_infos, vlanName, false, 3);
        assertTrue(new BRUtils().getFieldsOrbi("vlanName").contains(vlanName), "check vlan name can be changed");
        assertTrue(new BRUtils(BRUtils.api_lan_subnet, 3).getFieldsOrbi("lanIP").contains(DhcpIp),
                "check lan ip can be changed");
    }
}
