package webportal.FeatureWPA3.Mandate.PRJCBUGEN_T22008;

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
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;
/**
 * @author Tejeshiwni K V
 */

public class Testcase extends TestCaseBase {

    @Feature("WPA3 security") 
    @Story("PRJCBUGEN_T22008") 
    @Description("verify  check end to end flow   when default  authentication method is WPA2-psk or not  and 802.11w(PMF) is Mandatory") 
    @TmsLink("PRJCBUGEN-T22008") 

    @Test(alwaysRun = true, groups = "p2") 
    public void test() throws Exception {
        runTest(this);
    }
   
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        AccountPage AccountPage =new AccountPage();
        MyCommonAPIs.waitReady();
        new WirelessQuickViewPage().deleteSsidYes("apwp22008");
    //    new AccountPage().deleteSsidVlan(WebportalParam.location1, "VLAN16598");
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
        
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("SSID", "apwp22008");
        locationInfo.put("Security", "WPA2 Personal Mixed");
        locationInfo.put("Password", "123456798");
        
        String Option = "Mandate";
        new WirelessQuickViewPage().WPA3(locationInfo,  Option); 
        new WirelessQuickViewPage().EditSSID(locationInfo); 
        assertTrue(new WirelessQuickViewPage(false).isMandateSelected()); 
        
        MyCommonAPIs.sleepi(60);
        int sum = 0;
        while (true) {
            MyCommonAPIs.sleepi(10);
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID apwp22008")
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
                .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp22008 123456798 WPA2PSK aes")
                .equals("true")) {
            result1 = false;
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp22008 123456798 WPA2PSK aes")
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


