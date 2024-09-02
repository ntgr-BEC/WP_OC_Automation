package webportal.KVR80211.Pro.PRJCBUGEN_T23622;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import util.Javasocket;
import util.MyCommonAPIs;
import util.RunCommand;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HardBundlePage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;


/**
 *
 * @author Tejeshwini
 *
 */
public class Testcase extends TestCaseBase {
    
    Map<String, String> ssidInfo = new HashMap<String, String>();
    String cmd = "cat /var/config | grep -i wlan1:vap0:11rStatus";
    String cmd1 = "cat /sysconfig/config | grep -i wlan1:vap0:11RStatus";
    

    @Feature("KVR802.11") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T23622") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify if 11R Config gets Pushed when a new AP is Onboarded to a location with 11R Config") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T23622") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().deleteSsidYes("apwp22554");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInNormalAccount("admin");
        new WirelessQuickViewPage().deleteDeviceYes(WebportalParam.ap1serialNo);
        new WirelessQuickViewPage().deleteDeviceYes(WebportalParam.ap2serialNo);
        
    }

  
    @Step("Test Step 2:  Add WIFI ssid and now connect client to this ssid;")
    public void step2() {
      
        ssidInfo.put("SSID", "apwp22554");
        ssidInfo.put("Security", "WPA2 Personal Mixed");
        ssidInfo.put("Password", "123456798");
        new WirelessQuickViewPage().addSsid(ssidInfo);

      
    }
    
    @Step("Test Step 3: enable 802.11kvr;")
    public void step3() {
        new WirelessQuickViewPage().enable80211(ssidInfo.get("SSID"));
        
        
     }
    
    @Step("Test Step 4: add AP;")
    public void step4() {

        Map<String, String> devInfo = new HashMap<String, String>();
        Map<String, String> devInfo1 = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap1serialNo);
        devInfo.put("MAC Address1", WebportalParam.ap1macaddress);
        devInfo1.put("Serial Number", WebportalParam.ap2serialNo);
        devInfo1.put("MAC Address1", WebportalParam.ap2macaddress);

        new DevicesDashPage().addNewDevice(devInfo);
        new DevicesDashPage().addNewDevice(devInfo1);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo); 
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap2serialNo); 
        MyCommonAPIs.sleepi(120);
        new RunCommand().enableSSH4AP(WebportalParam.loginPassword, WebportalParam.ap1IPaddress);
        new RunCommand().enableSSH4AP(WebportalParam.loginPassword, WebportalParam.ap2IPaddress);
       
     }
    
    @Step("Test Step 5: Check 11r status;")
    public void step5() {
        
        String WACAP = new APUtils(WebportalParam.ap1IPaddress).get80211status(cmd);
        String WAXAP = new APUtils(WebportalParam.ap2IPaddress).get80211status(cmd1);
        
        assertTrue(WACAP.contains("vapSettings:vapSettingTable:wlan1:vap0:11rStatus 1"),"");
        assertTrue(WAXAP.contains("vapSettings:vapSettingTable:wlan1:vap0:11rStatus 1"),"");
    }
    
    }
    