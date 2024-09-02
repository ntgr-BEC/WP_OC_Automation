package webportal.KVR80211.Pro.PRJCBUGEN_T22629;

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
    String cmd = "cat /var/config | grep -i wlan1:vap0:11r";
    String cmd1 = "cat /sysconfig/config | grep -i wlan1:vap0:11r";
    

    @Feature("KVR802.11") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T22629") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verifiy  when AP is added in that location then  following elements is sent to all the APs 11r status , Mobility ID , AES Key,MAC Address.AP Number") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T22629") // It's a testcase id/link from Jira Test Case.

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
        MyCommonAPIs.sleepi(60);
        
        
     }
    
    @Step("Test Step 4: disable 802.11kvr;")
    public void step4() {
        String WACAP = new APUtils(WebportalParam.ap1IPaddress).get80211status(cmd);
        
        assertTrue(WACAP.contains("1rMobilityID"),"");
        assertTrue(WACAP.contains("11rStatus 1"),"");
        assertTrue(WACAP.contains("11rAES"),"");
        assertTrue(WACAP.contains("11rAPnum"),"");
        assertTrue(WACAP.contains("11rAPMac0"),"");
        
        String WAXAP = new APUtils(WebportalParam.ap2IPaddress).get80211status(cmd1);
        assertTrue(WAXAP.contains("1rMobilityID"),"");
        assertTrue(WAXAP.contains("11rStatus 1"),"");
        assertTrue(WAXAP.contains("11rAES"),"");
        assertTrue(WAXAP.contains("11rAPnum"),"");
        assertTrue(WAXAP.contains("11rAPMac0"),"");
     }
    }
    