package webportal.Orbi.ORBIPRO_SSID_Management.WIFI6SSIDVLAN.PRJCBUGEN_T23373;

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
import webportal.weboperation.DevicesOrbiWifiNetworkPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    @Feature("ORBIPRO_SSID_Management.WIFI6SSIDVLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T23373") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Connect clients to non-default VLAN SSID. Verify the clients are getting IP address from respective VLAN.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T23373") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
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
    
    @Step("Test Step 3: Enable Wireless 2 and set VLAN profile to employee(VLAN 20)")
    public void step3() {
        DevicesOrbiWifiNetworkPage page = new DevicesOrbiWifiNetworkPage();
        page.ssidData.ssid = "T23373-" + RandomStringUtils.randomNumeric(5);
        page.ssidData.ssidSecurity = 0;
        page.ssidData.enaSsidVlan = true;
        page.editSsidSetting(1);
        handle.waitRestReady(BRUtils.api_device_guest_wlan_info, page.ssidData.ssid, false, 3);
        assertTrue(new BRUtils().Dump().contains(page.ssidData.ssid), "ssid is changed");
    }
}
