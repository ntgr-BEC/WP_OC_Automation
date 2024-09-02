package webportal.ClientRoaminghistory.PRJCBUGEN_T21534;

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
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesApSummaryPage;
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
    Map<String, String> locationInfo = new HashMap<String, String>();
    Map<String, String> locationInfo7 = new HashMap<String, String>();
    String Disconnect = "Yes";
    

    @Feature("ClientRoaminghistory") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T21534") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Search funcationally in disconnected  client table") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T21534") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().deleteSsidYes("apwp21534");
        new WirelessQuickViewPage().deleteSsidYes("apwp21535");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccount();
        
    }

  
    @Step("Test Step 2:  Add WIFI ssid and now connect client to this ssid;")
    public void step2() {
       

        locationInfo7.put("SSID", "apwp21534");
        locationInfo7.put("Band", "Both");
        locationInfo7.put("Security", "WPA2 Personal");
        locationInfo7.put("Password", "123456798");
        new WirelessQuickViewPage().addSsid1(locationInfo7);
           
       
        locationInfo.put("SSID", "apwp215345");
        locationInfo.put("Band", "Both");
        locationInfo.put("Security", "WPA2 Personal");
        locationInfo.put("Password", "123456798");
        new WirelessQuickViewPage(false).addSsid1(locationInfo);

        int sum = 0;
        while (true) {
            MyCommonAPIs.sleepi(10);
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID apwp21534")
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
                .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp21534 123456798 WPA2PSK aes")
                .equals("true")) {
            result1 = false;
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp21534 123456798 WPA2PSK aes")
                    .equals("true")) {
                result1 = true;
            }
        }

        assertTrue(result1, "Client cannot connected.");
        MyCommonAPIs.sleepi(30);
        new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
        
        int sum1 = 0;
        while (true) {
            MyCommonAPIs.sleepi(10);
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID apwp215345")
                    .indexOf("true") != -1) {
                break;
            } else if (sum > 30) {
                assertTrue(false, "Client cannot connected.");
                break;
            }
            sum += 1;
        }

        boolean result2 = true;
        if (!new Javasocket()
                .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp215345 123456798 WPA2PSK aes")
                .equals("true")) {
            result2 = false;
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp215345 123456798 WPA2PSK aes")
                    .equals("true")) {
                result2 = true;
            }
        }

        assertTrue(result2, "Client cannot connected.");
        MyCommonAPIs.sleepi(30);
        new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
    }
    
    @Step("Test Step 4: Check whether connected connect is shown in Wireless page annd client page;")
    public void step4() {
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        if(new WirelessQuickViewPage().checkClientConnect(WebportalParam.clientwlanmac))
            {
            assertTrue(true);
         
            }
        else
        {
            assertTrue(new WirelessQuickViewPage(false).checkClientConnectDisconnected(WebportalParam.clientwlanmac), "Connected Client not showing on wireless page.");
        }
        logger.info("Connected client details is showned on wireless page");
        WebCheck.checkHrefIcon(URLParam.hrefClients);
        if(new WirelessQuickViewPage().checkClientConnect(WebportalParam.clientwlanmac))
        {
        assertTrue(true);
     
        }
    else
    {
        assertTrue(new WirelessQuickViewPage(false).checkClientConnectDisconnected(WebportalParam.clientwlanmac), "Connected Client not showing on wireless page.");
    }
       
        logger.info("Connected client details is showned on clients page");
    }
    
        
    }
    