package webportal.OrganizationWideSSID.PRJCBUGEN_T24676;

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
    @Story("PRJCBUGEN_T24676") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Org ssid with MAC ACL") // It's                                                                                                                                                     // Jira
    // Test
    // Case.
    @TmsLink("PRJCBUGEN_T24676") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new OrganizationPage(false).goToOrgSsid(WebportalParam.Organizations);
        new WirelessQuickViewPage(false).deleteSsidYes("apwp14270");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to pro account success.")
    public void step1() {

        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);        
           
    }
        
      @Step("Test Step 2: Add organizationWide SSID and enable MAC ACl")
      public void step2() {
          
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new OrganizationPage(false).goToOrgSsid(WebportalParam.Organizations);
          locationInfo.put("SSID", "apwp14270");
          locationInfo.put("Security", "WPA2 Personal");
          locationInfo.put("Password", "123456798");
          new OrganizationPage(false).CreateOrgSSId(locationInfo);
          
          Map<String, String> clientInfo = new HashMap<String, String>();
          clientInfo.put("Device Name", WebportalParam.client1name);
          clientInfo.put("MAC Address", WebportalParam.clientwlanmac); 
          new OrganizationPage(false).enablemacACL(clientInfo);
          
          new RunCommand().enableSSH4APALL(WebportalParam.ap1IPaddress);
          new RunCommand().enableSSH4APALL(WebportalParam.ap2IPaddress);
          
          assertTrue(new APUtils(WebportalParam.ap1IPaddress).getSsidStatus1("apwp14270", WebportalParam.ap1Model),"ssid is not pushed to AP");
          assertTrue(new APUtils(WebportalParam.ap2IPaddress).getSsidStatus1("apwp14270", WebportalParam.ap2Model),"ssid is not pushed to AP");
          
      }
      
      @Step("Test Step 3: Connect client1 to Oragnization Wide SSID and after that tray to connect client2.")
      public void step3() {
          
          
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

          new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
          
          MyCommonAPIs.sleepi(60);
          
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new OrganizationPage(false).goToOrgSsid(WebportalParam.Organizations);
          Map<String, String> clientInfo = new HashMap<String, String>();
          clientInfo.put("Device Name", WebportalParam.client1name);
          clientInfo.put("MAC Address", WebportalParam.clientwlanmac); 
          new OrganizationPage(false).denymacACL(clientInfo);
          
//          int sum1 = 0;
//          while (true) {
//              MyCommonAPIs.sleepi(10);
//              if (new Javasocket()
//                      .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID apwp14270")
//                      .indexOf("true") != -1) {
//                  break;
//              } else if (sum1 > 30) {
//                  assertTrue(false, "Client cannot connected.");
//                  break;
//              }
//              sum1 += 1;
//          }

          boolean result2 = true;
          if (!new Javasocket()
                  .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp14270 123456798 WPA2PSK aes")
                  .equals("true")) {
              if (new Javasocket()
                      .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp14270 123456798 WPA2PSK aes")
                      .equals("true")) {
                  result2 = false;
              }
          }

          assertTrue(result2, "Client can not connected.");
          
      }
}