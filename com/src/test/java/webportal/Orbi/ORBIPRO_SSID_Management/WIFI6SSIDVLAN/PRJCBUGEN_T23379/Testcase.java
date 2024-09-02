package webportal.Orbi.ORBIPRO_SSID_Management.WIFI6SSIDVLAN.PRJCBUGEN_T23379;

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
    String                      oldNetwork  = "Iot";
    String                      newNetwork  = "Iot" + RandomStringUtils.randomNumeric(5);
    String                      newLanIp    = "192.168.30." + RandomStringUtils.randomNumeric(2);
    
    @Feature("ORBIPRO_SSID_Management.WIFI6SSIDVLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T23379") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify able to edit the VLAN profile.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T23379") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")

    public void test() throws Exception {
        runTest(this);
        
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
        orbiNetwork.gotoPage();
        orbiNetwork.editNetwork(newNetwork, oldNetwork, newLanIp);
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

    @Step("Test Step 3: Change name and change LAN IP")
    public void step3() {
        orbiNetwork = new DevicesOrbiNetworkSetupPage();
        orbiNetwork.gotoPage();
        orbiNetwork.editNetwork(oldNetwork, newNetwork, newLanIp);
    }

    @Step("Test Step 4: no error,new setting can be pushed to local")
    public void step4() {
        handle.waitRestReady(BRUtils.api_vlan_network_infos, newNetwork, false, 3);
        assertTrue(new BRUtils().Dump().contains(newNetwork), "check network name is changed");
        handle.waitRestReady(BRUtils.api_lan_subnet, newLanIp, false, 3);
        assertTrue(new BRUtils(BRUtils.api_lan_subnet, 3).Dump().contains(newLanIp), "check dhcp is changed");
    }
}
