package webportal.OrganizationWideSSID.PRJCBUGEN_T24683;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.WebDriver;
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
import util.RunCommand;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    Map<String, String> locationInfo = new HashMap<String, String>();
    Random r = new Random();
    int num = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num);
    String organizationName = "Netgear";
    String locationName     = "office";
  

    @Feature("OrganizationWideSSID") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T24683") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Create    org  SSID with ICP") // It's                                                                                                                                                     // Jira
    // Test
    // Case.
    @TmsLink("PRJCBUGEN_T24683") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new OrganizationPage(false).goToOrgSsid(WebportalParam.Organizations);
        new WirelessQuickViewPage(false).deleteOrgSsidYes("apwp14270");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to pro account success.")
    public void step1() {

        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);       
        new OrganizationPage(false).creditAllocationICP(WebportalParam.Organizations);
    }
        
      @Step("Test Step 2: Add organizationWide SSID and set advance rate selection")
      public void step2() {
          
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new OrganizationPage(false).goToOrgSsid(WebportalParam.Organizations);
          locationInfo.put("SSID", "apwp14270");
          locationInfo.put("Security", "WPA2 Personal");
          locationInfo.put("Password", "123456798");
          new OrganizationPage(false).CreateOrgSSId(locationInfo);
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new OrganizationPage(false).goToOrgSsid(WebportalParam.Organizations);
          Map<String, String> icpInfo = new HashMap<String, String>();
          icpInfo.put("Portal Name", "welcome to Automation");
          icpInfo.put("Welcome Headline", "Automation test in BEC");
          icpInfo.put("Captive Portal Logo", "DEFAULT_LOGO");
          icpInfo.put("Desktop Background Image", "DEFAULT_BG");
          icpInfo.put("Landing Page URL", "https://www.rediff.com");
          icpInfo.put("Session Duration", "15 min");
          icpInfo.put("Step Type", "Authentication Method");
          icpInfo.put("Login Modes", "Facebook.");
          new WirelessQuickViewPage(false).enableCaptivePortalType1("apwp14270",icpInfo);
          MyCommonAPIs.sleepi(180);
      }
      
      @Step("Test Step 3: Connect client1 to Oragnization Wide SSID and verify SSID is pushed to both ap's..")
      public void step3() {
          
          new RunCommand().enableSSH4APALL(WebportalParam.ap1IPaddress);
          new RunCommand().enableSSH4APALL(WebportalParam.ap2IPaddress);
          assertTrue(new APUtils(WebportalParam.ap1IPaddress).getSsidStatus1("apwp14270", WebportalParam.ap1Model),"ssid is not pushed to AP");
          assertTrue(new APUtils(WebportalParam.ap2IPaddress).getSsidStatus1("apwp14270", WebportalParam.ap2Model),"ssid is not pushed to AP");
          
          MyCommonAPIs.sleepi(10);
          
          int sum = 0;
          while (true) {
              MyCommonAPIs.sleepi(10);
              if (new Javasocket()
                      .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID apwp14270")
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
                  .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp14270 123456798 WPA2PSK aes")
                  .equals("true")) {
              result1 = false;
              if (new Javasocket()
                      .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp14270 123456798 WPA2PSK aes")
                      .equals("true")) {
                  result1 = true;
              }
          }

          assertTrue(result1, "Client cannot connected.");
          MyCommonAPIs.sleepsync();
          
          assertTrue(
                  new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport,
                          "WAFruncaptive PRJCBUGEN-T16635.py www.rediff.com test test").indexOf("finalresult: 1") != -1,
                  "Captive portal not take effect.");

          new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
          
          int sum1 = 0;
          while (true) {
              MyCommonAPIs.sleepi(10);
              if (new Javasocket()
                      .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID apwp14270")
                      .indexOf("true") != -1) {
                  break;
              } else if (sum1 > 30) {
                  assertTrue(false, "Client cannot connected.");
                  break;
              }
              sum1 += 1;
          }

          boolean result2 = true;
          if (!new Javasocket()
                  .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp14270 123456798 WPA2PSK aes")
                  .equals("true")) {
              result2 = false;
              if (new Javasocket()
                      .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp14270 123456798 WPA2PSK aes")
                      .equals("true")) {
                  result2 = true;
              }
          }

          assertTrue(result1, "Client cannot connected.");

          MyCommonAPIs.sleepsync();
          
          assertTrue(
                  new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport,
                          "WAFruncaptive PRJCBUGEN-T16635.py www.rediff.com test test").indexOf("finalresult: 1") != -1,
                  "Captive portal not take effect.");
          
          new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
          
      }
          
          @Step("Test Step 4: Connect client1 to Oragnization Wide SSID and verify SSID is pushed to both ap's..")
          public void step4() {
          
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new OrganizationPage(false).goToOrgSsid(WebportalParam.Organizations);
          locationInfo.put("SSID", "apwp14271");
          locationInfo.put("Security", "WPA2 Personal");
          locationInfo.put("Password", "123456798");
          new OrganizationPage(false).CreateOrgSSId(locationInfo);
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new OrganizationPage(false).goToOrgSsid(WebportalParam.Organizations);
          Map<String, String> icpInfo = new HashMap<String, String>();
          icpInfo.put("Portal Name", "welcome to Automation");
          icpInfo.put("Welcome Headline", "Automation test in BEC");
          icpInfo.put("Captive Portal Logo", "DEFAULT_LOGO");
          icpInfo.put("Desktop Background Image", "DEFAULT_BG");
          icpInfo.put("Landing Page URL", "https://www.rediff.com");
          icpInfo.put("Session Duration", "15 min");
          icpInfo.put("Step Type", "Authentication Method");
          icpInfo.put("Login Modes", "Facebook.");
          new WirelessQuickViewPage(false).enableCaptivePortalType1("apwp14271",icpInfo);
          MyCommonAPIs.sleepi(180);
          new RunCommand().enableSSH4APALL(WebportalParam.ap1IPaddress);
          new RunCommand().enableSSH4APALL(WebportalParam.ap2IPaddress);
          assertTrue(new APUtils(WebportalParam.ap1IPaddress).getSsidStatus1("apwp14271", WebportalParam.ap1Model),"ssid is not pushed to AP");
          assertTrue(new APUtils(WebportalParam.ap2IPaddress).getSsidStatus1("apwp14271", WebportalParam.ap2Model),"ssid is not pushed to AP");
          
          MyCommonAPIs.sleepi(10);
          
          int sum = 0;
          while (true) {
              MyCommonAPIs.sleepi(10);
              if (new Javasocket()
                      .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID apwp14270")
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
                  .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp14271 123456798 WPA2PSK aes")
                  .equals("true")) {
              result1 = false;
              if (new Javasocket()
                      .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp14271 123456798 WPA2PSK aes")
                      .equals("true")) {
                  result1 = true;
              }
          }

          assertTrue(result1, "Client cannot connected.");
          MyCommonAPIs.sleepsync();
          assertTrue(
                  new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport,
                          "WAFruncaptive PRJCBUGEN-T16635.py www.rediff.com test test").indexOf("finalresult: 1") != -1,
                  "Captive portal not take effect.");
          new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
          
          int sum1 = 0;
          while (true) {
              MyCommonAPIs.sleepi(10);
              if (new Javasocket()
                      .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID apwp14270")
                      .indexOf("true") != -1) {
                  break;
              } else if (sum1 > 30) {
                  assertTrue(false, "Client cannot connected.");
                  break;
              }
              sum1 += 1;
          }

          boolean result2 = true;
          if (!new Javasocket()
                  .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp14271 123456798 WPA2PSK aes")
                  .equals("true")) {
              result2 = false;
              if (new Javasocket()
                      .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp14271 123456798 WPA2PSK aes")
                      .equals("true")) {
                  result2 = true;
              }
          }

          assertTrue(result1, "Client cannot connected.");
          MyCommonAPIs.sleepsync();
          assertTrue(
                  new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport,
                          "WAFruncaptive PRJCBUGEN-T16635.py www.rediff.com test test").indexOf("finalresult: 1") != -1,
                  "Captive portal not take effect.");
          new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
          
          new OrganizationPage(false).goToOrgSsid(WebportalParam.Organizations);
          new WirelessQuickViewPage(false).deleteOrgSsidYes("apwp14271");
          
      }
}