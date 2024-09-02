package webportal.Orbi.ORBIPRO_SSID_Management.WIFI6SSIDVLAN.PRJCBUGEN_T23386;

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

    @Feature("ORBIPRO_SSID_Management.WIFI6SSIDVLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T23386") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that VLAN configured on the CP enabled SSID and MVLAN can be same") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T23386") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    
    public void test() throws Exception {
        wifiNetwork = new DevicesOrbiWifiNetworkPage(true);
        orbiNetwork = new DevicesOrbiNetworkSetupPage();
        orbiDhcp = new DevicesOrbiDHCPServersPage();
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        wifiNetwork.gotoPage();
        wifiNetwork.ssidData.ssidVlan = "Employee";
        wifiNetwork.editSsidSetting(1);
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
        wifiNetwork.gotoPage();
    }
    
    @Step("Test Step 3: Go to Wifi Network page, Set VLAN 20 to Guest wifi")
    public void step3() {
        wifiNetwork.ssidData.ssid = "T23386-" + RandomStringUtils.randomNumeric(5);
        wifiNetwork.ssidData.enaSsidVlan = true;
        wifiNetwork.ssidData.ssidVlan = "Guest";
        wifiNetwork.editSsidSetting(1);

        handle.waitRestReady(BRUtils.api_device_guest_wlan_info, wifiNetwork.ssidData.ssid, false, 3);
        assertTrue(new BRUtils().getFieldsOrbi("vlanProfile").contains("40"), "check vlan name can be changed to guest");
    }
}
