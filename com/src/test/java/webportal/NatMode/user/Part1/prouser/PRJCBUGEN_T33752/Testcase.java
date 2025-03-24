package webportal.NatMode.user.Part1.prouser.PRJCBUGEN_T33752;

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
import webportal.param.CommonDataType;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesApSummaryPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
*
* @author dallas
*
*/
public class Testcase extends TestCaseBase {

   Random              r                        = new Random();
   int                 num                      = r.nextInt(10000000);
   String              mailname                 = "";
   Map<String, String> CaptivePortalPaymentInfo = new CommonDataType().PAYMENT_INFO;
   Map<String, String> ssidInfo                 = new HashMap<String, String>();
   Map<String, String> icpInfo                  = new HashMap<String, String>();

   @Feature("NatMode") // It's a folder/component name to make test suite more readable from Jira Test Case.
   @Story("PRJCBUGEN_T33752") // It's a testcase id/link from Jira Test Case but replace - with _.
   @Description("Verify Icp with Natmode Ssid") // It's a test case title from Jira Test Case.
   @TmsLink("PRJCBUGEN-PRJCBUGEN_T33752") // It's a testcase id/link from Jira Test Case.

   @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
   public void test() throws Exception {
       runTest(this);
   }

   @AfterMethod(alwaysRun = true)
   public void tearDown() {
       System.out.println("start to do tearDown");
       new WirelessQuickViewPage().deleteSsidYes(ssidInfo.get("SSID"));
   }

   // Each step is a single test step from Jira Test Case
   @Step("Test Step 1: Login IM WP success;")
   public void step1() {
       WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
       webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
       new HamburgerMenuPage(false).closeLockedDialog();
       new OrganizationPage(false).openOrg(WebportalParam.Organizations);

       handle.gotoLoction();
       // new DevicesDashPage().checkDeviceInAdminAccount();
   }

   @Step("Test Step 2: Add NatMode WIFI ssid and enable instant captive portal, check client connect wifi;")
   public void step2() {
     
       ssidInfo.put("SSID", "apwp14540");
       ssidInfo.put("Security", "WPA2 Personal");
       ssidInfo.put("Password", "12345678");
       new WirelessQuickViewPage().addSsidNat(ssidInfo);

       icpInfo.put("Portal Name", "test14540");
       icpInfo.put("Welcome Headline", "test14540");
       icpInfo.put("Captive Portal Logo", "DEFAULT_LOGO");
       icpInfo.put("Desktop Background Image", "DEFAULT_BG");
       icpInfo.put("Landing Page URL", "https://www.rediff.com");
       icpInfo.put("Session Duration", "5 min");
       icpInfo.put("Step Type", "Authentication Method");
       icpInfo.put("Login Modes", "Register.");
       new WirelessQuickViewPage().enableCaptivePortalType(ssidInfo.get("SSID"), icpInfo);

       MyCommonAPIs.sleepi(100);

       assertTrue(new WirelessQuickViewPage().connectClient(ssidInfo), "Client cannot connected.");

   }

   @Step("Test Step 3: Check whether captive portal page is shown or not;")
   public void step3() {
       assertTrue(new APUtils(WebportalParam.ap1IPaddress).getNatStatus(WebportalParam.ap1Model), "CONFIG FOR NAT NOT PUSHED");
       MyCommonAPIs.sleepsync();
       MyCommonAPIs.sleepi(80);
       assertTrue(
               new Javasocket()
                       .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport,
                               "WAFruncaptive PRJCBUGEN-T14540.py www.rediff.com " + icpInfo.get("Portal Name") + " test")
                       .indexOf("finalresult: 1") != -1,
               "Captive portal not take effect.");
       new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
   }

}