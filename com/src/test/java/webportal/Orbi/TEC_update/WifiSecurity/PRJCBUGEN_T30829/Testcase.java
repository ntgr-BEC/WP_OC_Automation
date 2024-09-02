package webportal.Orbi.TEC_update.WifiSecurity.PRJCBUGEN_T30829;

import static com.codeborne.selenide.Selenide.$;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.Orbi.TEC_update.WifiSecurity.PRJCBUGEN_T30829.Config;
import util.MyCommonAPIs;
import webportal.weboperation.WebportalLoginPage;
import webportal.param.WebportalParam;
import webportal.weboperation.SummaryPage;
import webportal.weboperation.DevicesOrbiSummaryPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesOrbiTroubleshootPage;
import webportal.weboperation.DevicesOrbiWifiNetworkPage;
import webportal.publicstep.UserManage;
import orbi.weboperation.OrbiAdvancedHomePage;
import orbi.weboperation.OrbiBasicWirelessPage;
import orbi.weboperation.OrbiLoginPage;
import orbi.webelements.DNIOrbiCommanElement;
import orbi.webelements.OrbiAllMenueElements;
import orbi.webelements.OrbiBasicWirelessElements;


/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase implements Config {
    @Feature("Orbi.TEC_update.WifiSecurity") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T30829") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("WPA2/WPA3 mixed mode check after on-boarding") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T30829") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP, delete DUT if it's on-boarded")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
        ddp.gotoPage();
        
        new DevicesDashPage().deleteDeviceYes(WebportalParam.ob2serialNo);
        MyCommonAPIs.sleep(10 * 1000);
        
        UserManage userManage = new UserManage();
        userManage.logout();
    }

    @Step("Test Step 2: DUT standalone mode, enable wireless123 and set security to WPA2-WPA3 mixed mode")
    public void step2() {
        OrbiLoginPage orbiLoginPage = new OrbiLoginPage();
        orbiLoginPage.defaultLogin();
        
        OrbiBasicWirelessPage orbiwirelesspage = new OrbiBasicWirelessPage();
        orbiwirelesspage.ConfigWirelessInfor(wireless1info);
        MyCommonAPIs.sleepi(120); // satellite sync
        orbiwirelesspage.ConfigWirelessInfor(wireless2info);
        MyCommonAPIs.sleepi(120); // satellite sync
        orbiwirelesspage.ConfigWirelessInfor(wireless3info);
        MyCommonAPIs.sleepi(120); // satellite sync
        
        OrbiLoginPage OrbiLoginPage = new OrbiLoginPage();
        OrbiLoginPage.OrbiLogout();
    }
    
    @Step("Test Step 3: On-board to Insight, check security mode on Insight")
    public void step3() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
        ddp.gotoPage();
        
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ob2serialNo);
        devInfo.put("Device Name", WebportalParam.ob2Name);
        devInfo.put("MAC Address", WebportalParam.ob2MAC_Address.replace("-", ":"));
        new DevicesDashPage().addNewDevice(devInfo);
        
        assertTrue(new DevicesDashPage().waitDevicesReConnected(WebportalParam.ob2serialNo), "Device cannot online.");
        
        new DevicesDashPage().enterDevice(WebportalParam.ob2serialNo);
        DevicesOrbiWifiNetworkPage page = new DevicesOrbiWifiNetworkPage();
        assertTrue(page.getWiFiInfo(1, 4).equals("WPA2-PSK/WPA3-SAE"), "wifi1 security is not correct on Insight");
        assertTrue(page.getWiFiInfo(2, 4).equals("WPA2-PSK/WPA3-SAE"), "wifi2 security is not correct on Insight");
        assertTrue(page.getWiFiInfo(3, 4).equals("WPA2-PSK/WPA3-SAE"), "wifi3 security is not correct on Insight");
        
        UserManage userManage = new UserManage();
        userManage.logout();
    }
    
    @Step("Test Step 4: Check on local GUI, security mode should keep")
    public void step4() {
        OrbiLoginPage orbiLoginPage = new OrbiLoginPage();
        orbiLoginPage.defaultLogin();
        
        OrbiBasicWirelessPage orbiwirelesspage = new OrbiBasicWirelessPage();
        orbiwirelesspage.wireless1.click();
        MyCommonAPIs.sleepi(5);
        assertTrue(orbiwirelesspage.wireless1wpa2wpa3.getAttribute("checked").equals("checked"), "wifi1 security is not correct on local");
        orbiwirelesspage.wireless2.click();
        MyCommonAPIs.sleepi(5);
        assertTrue(orbiwirelesspage.wireless1wpa2wpa3.getAttribute("checked").equals("checked"), "wifi1 security is not correct on local");
        orbiwirelesspage.wireless3.click();
        MyCommonAPIs.sleepi(5);
        assertTrue(orbiwirelesspage.wireless1wpa2wpa3.getAttribute("checked").equals("checked"), "wifi1 security is not correct on local");
        
    }
    
    @Step("Test Step 5: Reboot via local GUI, security mode should keep on local and on Insight")
    public void step5() {
        // local
        OrbiAdvancedHomePage orbiadvancedhomepage = new OrbiAdvancedHomePage();
        orbiadvancedhomepage.OpenAdvancedHomePage();
        orbiadvancedhomepage.rebootOrbiPro();
        MyCommonAPIs.sleepi(200);
        
        OrbiLoginPage orbiLoginPage = new OrbiLoginPage();
        orbiLoginPage.defaultLogin();
        OrbiBasicWirelessPage orbiwirelesspage = new OrbiBasicWirelessPage();
        orbiwirelesspage.wireless1.click();
        MyCommonAPIs.sleepi(5);
        assertTrue(orbiwirelesspage.wireless1wpa2wpa3.getAttribute("checked").equals("checked"), "wifi1 security is not correct on local");
        orbiwirelesspage.wireless2.click();
        MyCommonAPIs.sleepi(5);
        assertTrue(orbiwirelesspage.wireless1wpa2wpa3.getAttribute("checked").equals("checked"), "wifi1 security is not correct on local");
        orbiwirelesspage.wireless3.click();
        MyCommonAPIs.sleepi(5);
        assertTrue(orbiwirelesspage.wireless1wpa2wpa3.getAttribute("checked").equals("checked"), "wifi1 security is not correct on local");
        
        OrbiLoginPage OrbiLoginPage = new OrbiLoginPage();
        OrbiLoginPage.OrbiLogout();
        
        // Insight
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
        ddp.gotoPage();
        
        new DevicesDashPage().enterDevice(WebportalParam.ob2serialNo);
        DevicesOrbiWifiNetworkPage page = new DevicesOrbiWifiNetworkPage();
        assertTrue(page.getWiFiInfo(1, 4).equals("WPA2-PSK/WPA3-SAE"), "wifi1 security is not correct on Insight");
        assertTrue(page.getWiFiInfo(2, 4).equals("WPA2-PSK/WPA3-SAE"), "wifi2 security is not correct on Insight");
        assertTrue(page.getWiFiInfo(3, 4).equals("WPA2-PSK/WPA3-SAE"), "wifi3 security is not correct on Insight");
        
    }
    
    @Step("Test Step 6: Reboot via Insight GUI, security mode should keep on Insight and on local")
    public void step6() {
        // Insight
        ddp.gotoPage();
        // reboot
        ddp.rebootDevice(WebportalParam.ob2serialNo);
        assertTrue(new DevicesDashPage().waitDevicesReConnected(WebportalParam.ob2serialNo), "Device cannot online after reboot.");
        
        new DevicesDashPage().enterDevice(WebportalParam.ob2serialNo);
        DevicesOrbiWifiNetworkPage page = new DevicesOrbiWifiNetworkPage();
        assertTrue(page.getWiFiInfo(1, 4).equals("WPA2-PSK/WPA3-SAE"), "wifi1 security is not correct on Insight");
        assertTrue(page.getWiFiInfo(2, 4).equals("WPA2-PSK/WPA3-SAE"), "wifi2 security is not correct on Insight");
        assertTrue(page.getWiFiInfo(3, 4).equals("WPA2-PSK/WPA3-SAE"), "wifi3 security is not correct on Insight");
        
        UserManage userManage = new UserManage();
        userManage.logout();
        // local
        OrbiLoginPage orbiLoginPage = new OrbiLoginPage();
        orbiLoginPage.defaultLogin();
        
        OrbiBasicWirelessPage orbiwirelesspage = new OrbiBasicWirelessPage();
        orbiwirelesspage.wireless1.click();
        MyCommonAPIs.sleepi(5);
        assertTrue(orbiwirelesspage.wireless1wpa2wpa3.getAttribute("checked").equals("checked"), "wifi1 security is not correct on local");
        orbiwirelesspage.wireless2.click();
        MyCommonAPIs.sleepi(5);
        assertTrue(orbiwirelesspage.wireless1wpa2wpa3.getAttribute("checked").equals("checked"), "wifi1 security is not correct on local");
        orbiwirelesspage.wireless3.click();
        MyCommonAPIs.sleepi(5);
        assertTrue(orbiwirelesspage.wireless1wpa2wpa3.getAttribute("checked").equals("checked"), "wifi1 security is not correct on local");
    }
}