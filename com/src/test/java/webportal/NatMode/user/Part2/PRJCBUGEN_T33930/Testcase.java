package webportal.NatMode.user.Part2.PRJCBUGEN_T33930;

import static org.testng.Assert.assertTrue;

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
import webportal.param.CommonDataType;
import webportal.param.WebportalParam;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author dallas
 *
 */
import static org.testng.Assert.assertTrue;

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
import util.APUtils;
import util.Javasocket;
import util.MyCommonAPIs;
import util.RunCommand;
import webportal.param.CommonDataType;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesApSummaryPage;
import webportal.weboperation.DevicesDashPage;
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

   Map<String, String> ssidInfo = new HashMap<String, String>();

   @Feature("NatMode") // It's a folder/component name to make test suite more readable from Jira Test Case.
   @Story("PRJCBUGEN_T33930") // It's a testcase id/link from Jira Test Case but replace - with _.
   @Description("verify whether the basic captive portal ssid with nat mode is pushed or not") // It's a test case title from Jira Test Case.
   @TmsLink("PRJCBUGEN-T33930") // It's a testcase id/link from Jira Test Case.

   @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
   public void test() throws Exception {
       runTest(this);
   }

   @AfterMethod(alwaysRun = true)
   public void tearDown() {
       System.out.println("start to do tearDown");
       new WirelessQuickViewPage().deleteALLSSID();
   }

   // Each step is a single test step from Jira Test Case
   @Step("Test Step 1: Login IM WP success;")
   public void step1() {
       WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
       webportalLoginPage.loginByUserPassword(WebportalParam.loginName, WebportalParam.loginPassword);

       handle.gotoLoction();
   }

   @Step("Test Step 2: Create Nat SSID and connect it to client")
   public void step2() {
       ssidInfo.put("SSID", "apwp14008");
       ssidInfo.put("Security", "WPA2 Personal");
       ssidInfo.put("Password", "12345678");  
       new WirelessQuickViewPage().addSsidNat(ssidInfo);

       new WirelessQuickViewPage().editCaptivePortal(ssidInfo.get("SSID"), "http://www.rediff.com", "sri office", "welcome to sri office");
       MyCommonAPIs.sleepi(120);


       
       assertTrue(new WirelessQuickViewPage().connectClient(ssidInfo), "Client cannot connected.");
   }
   
   
   @Step("Test Step 3: Check whether captive portal page is shown or not;")
   public void step3() {

       assertTrue(
               new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport,
                       "WAFruncaptive PRJCBUGEN-T14477.py www.rediff.com test test").indexOf("finalresult: 1") != -1,
               "Captive portal not take effect.");
       new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
   }

}
   
   