package webportal.ClientIsolationWithDLAN.Enhancement.PRJCBUGEN_T31782;

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
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author  Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    String urls[] = {
            WebportalParam.clientip
    };
    
    Map<String, String> locationInfo = new HashMap<String, String>();
    
    @Feature("ClientIsolationWithDLAN Enhance") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T31782") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, upto 5 IP or device name with Domain can be added on AL") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T31782") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
//        new WirelessQuickViewPage().deleteSsidYes("apwp14480");

    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccount();
    }

     @Step("Test Step 2: Create SSID with Client Isolation enabled")
     public void step2() {
        
        locationInfo.put("SSID", "apwp14480");
        locationInfo.put("Security", "WPA2 Personal");
        locationInfo.put("Password", "12345678");
        new WirelessQuickViewPage().addSsid1(locationInfo);
               
      
       int sum = 0;
       while (true) {
           MyCommonAPIs.sleepi(10);
           if (new Javasocket()
                   .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID apwp14480")
                   .indexOf("true") != -1) {
               break;
           } else if (sum > 20) {
               assertTrue(false, "Client cannot connected.");
               break;
           }
           sum += 1;
       }
       
       MyCommonAPIs.sleepi(20);

       boolean result1 = true;
       if (!new Javasocket()
               .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp14480 12345678 WPA2PSK aes")
               .equals("true")) {
           result1 = false;
           MyCommonAPIs.sleepi(20);
           if (new Javasocket()
                   .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp14480 12345678 WPA2PSK aes")
                   .equals("true")) {
               result1 = true;
           }
       }

       assertTrue(result1, "Client cannot connected.");
          
    }

     
     @Step("Test Step 3: Check other client is reacheble;")
     public void step3() {
         MyCommonAPIs.sleepi(80);
         System.out.println("check result");
         Javasocket Javasocket = new Javasocket();
         while (true) {
         String res = Javasocket.sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFping2 "+ WebportalParam.ap1IPaddress);
         System.out.println(res);
         System.out.println("start assert");
         if (res.indexOf("could not find host") == -1) {
             assertTrue(res.contains(":    Packets: Sent = 4, Received = 0"), "Blacklist not take effect.");
             break;
         }
         } 
                                  
     }
     
     
     @Step("Test Step 4: Check other client is reacheble;")
     public void step4() {
         new WirelessQuickViewPage().AddDomain(locationInfo.get("SSID"), urls);
         MyCommonAPIs.sleepi(30);
         System.out.println("check result");
         Javasocket Javasocket = new Javasocket();
         while (true) {
         String res = Javasocket.sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFping2 "+ WebportalParam.clientip);
         System.out.println(res);
         System.out.println("start assert");
         if (res.indexOf("could not find host") == -1) {
             assertTrue(res.contains(":    Packets: Sent = 4, Received = 4"), "Blacklist not take effect.");
             break;
         }
         } 
                                   
         new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
     }
}
