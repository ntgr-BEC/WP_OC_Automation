package webportal.EightSSID.pro_admin.PRJCBUGEN_T20399;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
import webportal.weboperation.WiredVLANPageForVLANPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini
 *
 */
public class Testcase extends TestCaseBase {
    

    @Feature("Eight SSID") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T20399") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify  user  is able to  crearte 8 Ssid from vlan page") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T20399") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
    
    
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        wvp.deleteAllVlan();
        new WirelessQuickViewPage().deleteALLSSID();
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
       
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInNormalAccount("admin"); //must input admin or manager
    }

    @Step("Test Step 2: Add WIFI ssid and now connect client to this ssid;")
    public void step2() {
        
        ArrayList<String> list = new ArrayList<String>();
        Random r = new Random();
         
        int i;
        for(i=0;i<=6;i++) {
        int num = r.nextInt(10000);
        String mailname = "ssid" + String.valueOf(num);
        list.add(mailname);
        }
        System.out.println(list);
        
        wvp.deleteAllVlan();
        new WirelessQuickViewPage().deleteALLSSID();
        
        new WiredVLANPageForVLANPage().CreateVLANToAddSSID();
        
        Map<String, String> locationInfo7 = new HashMap<String, String>();
        locationInfo7.put("SSID", "apwp20399");
        locationInfo7.put("Band", "Both");
        locationInfo7.put("Security", "WPA2 Personal");
        locationInfo7.put("Password", "123456798");
        new WirelessQuickViewPage(false).addSsidFromVLAN(locationInfo7);
           
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("SSID", list.get(0));
        System.out.println(list.get(0));
        locationInfo.put("Band", "Both");
        locationInfo.put("Security", "Open");
        new WirelessQuickViewPage(false).addSsidFromVLAN(locationInfo);
        
        Map<String, String> locationInfo1 = new HashMap<String, String>();
        locationInfo1.put("SSID", list.get(1));
        System.out.println(list.get(1));
        locationInfo1.put("Band", "Both");
        locationInfo1.put("Security", "WPA3 Personal Mixed (WPA2 + WPA3)");
        locationInfo1.put("Password", "123456798");
        new WirelessQuickViewPage(false).addSsidFromVLAN(locationInfo1);
        
        Map<String, String> locationInfo2 = new HashMap<String, String>();
        locationInfo2.put("SSID", list.get(2));
        System.out.println(list.get(2));
        locationInfo2.put("Band", "Both");
        locationInfo2.put("Security", "WPA3 Personal");
        locationInfo2.put("Password", "123456798");
        new WirelessQuickViewPage(false).addSsidFromVLAN(locationInfo2);
        
        Map<String, String> locationInfo3 = new HashMap<String, String>();
        locationInfo3.put("SSID", list.get(3));
        System.out.println(list.get(3));
        locationInfo3.put("Band", "Both");
        locationInfo3.put("Security", "WPA2 Personal Mixed");
        locationInfo3.put("Password", "123456798");
        new WirelessQuickViewPage(false).addSsidFromVLAN(locationInfo3);
        
        Map<String, String> locationInfo4 = new HashMap<String, String>();
        locationInfo4.put("SSID", list.get(4));
        System.out.println(list.get(4));
        locationInfo4.put("Band", "Both");
        locationInfo4.put("Security", "WPA2 Personal");
        locationInfo4.put("Password", "123456798");
        new WirelessQuickViewPage(false).addSsidFromVLAN(locationInfo4);
        
        Map<String, String> locationInfo5 = new HashMap<String, String>();
        locationInfo5.put("SSID", list.get(5));
        System.out.println(list.get(5));
        locationInfo5.put("Band", "Both");
        locationInfo5.put("Security", "Open");
        new WirelessQuickViewPage(false).addSsidFromVLAN(locationInfo5);
        
        Map<String, String> locationInfo6 = new HashMap<String, String>();
        locationInfo6.put("SSID", list.get(6));
        System.out.println(list.get(6));
        locationInfo6.put("Band", "Both");
        locationInfo6.put("Security", "WPA3 Personal Mixed (WPA2 + WPA3)");
        locationInfo6.put("Password", "123456798");
        new WirelessQuickViewPage(false).addSsidFromVLAN(locationInfo6);
        
        Map<String, String> locationInfo8 = new HashMap<String, String>();
        locationInfo8.put("SSID", "maxssid");
        locationInfo8.put("Band", "Both");
        locationInfo8.put("Security", "WPA2 Personal");
        locationInfo8.put("Password", "123456798");
        assertTrue(new WirelessQuickViewPage(false).addSsidFromVLAN(locationInfo8));
        
        
        new WiredVLANPageForVLANPage(false).AddSSIDVLAN();
        
        int sum = 0;
        while (true) {
            MyCommonAPIs.sleepi(10);
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID apwp20399")
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
                .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp20399 123456798 WPA2PSK aes")
                .equals("true")) {
            result1 = false;
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp20399 123456798 WPA2PSK aes")
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
