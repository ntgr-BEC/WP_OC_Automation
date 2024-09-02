package webportal.NatMode.user.Dontrun.PRJCBUGEN_T33933;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.WebportalParam;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
*
* @author Ravishankar
*
*/
public class Testcase extends TestCaseBase {

    @Feature("NatMode") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T33933") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, RATELIMIT ON SSID WITH Bridge MODE") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T33933") // It's a testcase id/link from Jira Test Case.

   @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
   public void test() throws Exception {
       runTest(this);
   }

   @AfterMethod(alwaysRun = true)
   public void tearDown() {
       new WirelessQuickViewPage().deleteSsidYes("apwp14479");
       System.out.println("start to do tearDown");
   }

   // Each step is a single test step from Jira Test Case
   @Step("Test Step 1: Login IM WP success;")
   public void step1() {
       WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
       webportalLoginPage.defaultLogin();
       handle.gotoLoction();      
   }

   @Step("Test Step 2: Add WIFI ssid in Bridge and configure rate limit;")
   public void step2() {
       Map<String, String> ssidInfo = new HashMap<String, String>();
       ssidInfo.put("SSID", "apwp14008");
       ssidInfo.put("Security", "WPA2 Personal");
       ssidInfo.put("Password", "12345678");
       new WirelessQuickViewPage().addSsid(ssidInfo);

       new WirelessQuickViewPage().editRateLimit(ssidInfo.get("SSID"), 45.0000, 25.0000); // need modify

       int sum = 0;
       while (true) {
           MyCommonAPIs.sleepi(10);
           if (new Javasocket()
                   .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID apwp14008")
                   .indexOf("true") != -1) {
               break;
           } else if (sum > 30) {
               assertTrue(false, "Client cannot connected.");
               break;
           }
           sum += 1;
       }

       MyCommonAPIs.sleepi(20);

       boolean result1 = true;
       if (!new Javasocket()
               .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp14008 12345678 WPA2PSK aes")
               .equals("true")) {
           result1 = false;
           MyCommonAPIs.sleepi(20);
           if (new Javasocket()
                   .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp140089 12345678 WPA2PSK aes")
                   .equals("true")) {
               result1 = true;
           }
       }

       assertTrue(result1, "Client cannot connected.");

   }

   @Step("Test Step 3: Check speedtest result;")
   public void step3() {
       assertTrue(new APUtils(WebportalParam.ap1IPaddress).getRateLimitStatus(WebportalParam.ap1Model),"RATE LIMT NOT ENABLED");
       assertTrue(new APUtils(WebportalParam.ap1IPaddress).getSsidStat(WebportalParam.ap1Model), "CONFIG FOR NAT NOT PUSHED");
       String str = new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport,
               "WAFruncaptive PRJCBUGEN-T14479.py www.speedtest.net test test");
       if (str.indexOf("ERROR:") == -1 && !str.contains("finalresult: 0")) {
           String downloadSpeed = str.substring(str.indexOf("download-speed: ") + 16, str.indexOf("upload-speed"));
           String uploadSpeed = str.substring(str.indexOf("upload-speed: ") + 14, str.indexOf("finalresult"));
           System.out.println(str);
           System.out.println(downloadSpeed);
           System.out.println(uploadSpeed);
           assertTrue(Float.parseFloat(downloadSpeed) < 25 && Float.parseFloat(downloadSpeed) > 0 && Float.parseFloat(uploadSpeed) < 45
                   && Float.parseFloat(uploadSpeed) > 0, "Speedtest result is error.");
       } else {
           assertTrue(false, "Cannot run speedtest.");
       }
       new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
   }

}
