package webportal.Orbi.ORBIPRO_SSID_Management.WIFI6SSIDVLAN.PRJCBUGEN_T23374;

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
    String[] allSSid = new String[5];

    @Feature("ORBIPRO_SSID_Management.WIFI6SSIDVLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T23374") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Create multiple VLAN with different security authentication. Verify clients are getting internet access and also check clients are getting IP from respective VLAN (cloned)") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T23374") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")

    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
        DevicesOrbiWifiNetworkPage page = new DevicesOrbiWifiNetworkPage();

        page.ssidData.ssid = "ORBI31-2";
        page.ssidData.ssidSecurity = 0;
        page.ssidData.enaSsidVlan = false;
        page.editSsidSetting(1);

        DevicesOrbiWifiNetworkPage page2 = new DevicesOrbiWifiNetworkPage();
        page2.ssidData.ssid = "ORBI31-3";
        page2.ssidData.ssidSecurity = 0;
        page2.ssidData.enaSsidVlan = false;
        page2.editSsidSetting(2); 
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

    @Step("Test Step 3: Enable wireless 2 and set VLAN 20,security set to WPA2")
    public void step3() {
        DevicesOrbiWifiNetworkPage page = new DevicesOrbiWifiNetworkPage();
        page.ssidData.ssid = "T23374-" + RandomStringUtils.randomNumeric(5);

        page.ssidData.enableSsid = true;
      
        page.ssidData.ssidSecurity = 1;
        page.ssidData.enaSsidVlan = true;
        page.ssidData.ssidVlan = "Employee";
        page.editSsidSetting(1);
    }
    
    @Step("Test Step 4: Enable wireless 3 and set VLAN 30,security set to WPA")
    public void step4() {
        DevicesOrbiWifiNetworkPage page = new DevicesOrbiWifiNetworkPage();
        page.ssidData.ssid = "T23374-" + RandomStringUtils.randomNumeric(5);
        page.ssidData.ssidSecurity = 1;
        page.ssidData.enaSsidVlan = true;
        page.ssidData.ssidVlan = "Iot";
        page.editSsidSetting(2);
    }

    @Step("Test Step 5: Verify configuration to device")
    public void step5() {
        MyCommonAPIs.sleepi(15);
        System.out.println(new BRUtils(BRUtils.api_device_guest_wlan_info, 3).getField("SSID5G"));
        assertTrue(new BRUtils(BRUtils.api_device_guest_wlan_info, 3).getField("SSID5G").contains("T23374-"), "check 2nd ssid");
        System.out.println(new BRUtils(BRUtils.api_device_iot_wlan_info, 3).getField("SSID5G"));
        assertTrue(new BRUtils(BRUtils.api_device_iot_wlan_info, 3).getField("SSID5G").contains("T23374-"), "check 3rd ssid");

    }

}
