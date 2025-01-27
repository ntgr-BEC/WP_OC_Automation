package webportal.FeatureWPA3.Premium.PRJCBUGEN_T22009;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredVLANPage;
import webportal.weboperation.WiredVLANPageForVLANPage;
import webportal.weboperation.WirelessQuickViewPage;
/**
 * @author Tejeshiwni K V
 */

public class Testcase extends TestCaseBase {
    final static Logger logger = Logger.getLogger("PRJCBUGEN_T16598");

    @Feature("WPA3 security") 
    @Story("PRJCBUGEN_T22009") 
    @Description("verify end to end flow when authentication method is WPA2-psk  Mixed   and 802.11w(PMF) is Mandatory from vlan setting") 
    @TmsLink("PRJCBUGEN_T22009") 

    @Test(alwaysRun = true, groups = "p2") 
    public void test() throws Exception {
        runTest(this);
    }
   
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WiredVLANPage(true).deleteAllVlan();
        System.out.println("start to do tearDown");
    }

    @Step("Test Step 1: Log in to a premium account;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccount();
               
    }

    
    @Step("Test Step 2:Enable functionality for 802.11w feature")
    public void step2(){
        
        new WiredVLANPageForVLANPage().CreateVLANToAddSSID();
        
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("SSID", "apwp22009");
        locationInfo.put("Band", "Both");
        locationInfo.put("Security", "WPA2 Personal Mixed");
        locationInfo.put("Password", "123456798");
        
        String Option = "Mandate";
        new WirelessQuickViewPage(false).WPA3VLAN(locationInfo,  Option);
        new WiredVLANPageForVLANPage(false).AddSSIDVLAN();
        new WirelessQuickViewPage().EditSSID(locationInfo); 
        assertTrue(new WirelessQuickViewPage(false).isMandateSelected());
        
        MyCommonAPIs.sleepi(60);
        int sum = 0;
        while (true) {
            MyCommonAPIs.sleepi(10);
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID apwp22009")
                    .indexOf("true") != -1) {
                break;
            } else if (sum > 30) {
                assertTrue(false, "Client cannot connected.");
                break;
            }
            sum += 1;
        }

        boolean result1 = true;
        if (!new Javasocket()
                .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp22009 123456798 WPA2PSK aes")
                .equals("true")) {
            result1 = false;
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp22009 123456798 WPA2PSK aes")
                    .equals("true")) {
                result1 = true;
            }
        }

        assertTrue(result1, "Client cannot connected.");
    }

    @Step("Test Step 3: Check whether connected connect is shown in client list;")
    public void step3() {
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        assertTrue(new WirelessQuickViewPage().checkClientConnect(WebportalParam.clientwlanmac), "Client cannot connected.");
        new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
    }

        
}

